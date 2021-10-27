package com.riseuplabs.ureport_r4v.ui.opinions.flow_list;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.cloudmessaging.CloudMessagingReceiver;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.adapter.FlowDownloadListAdapter;
import com.riseuplabs.ureport_r4v.adapter.FlowListAdapter;
import com.riseuplabs.ureport_r4v.base.BaseSubmissionActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityFlowListBinding;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Flow;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.surveyor.data.Submission;
import com.riseuplabs.ureport_r4v.surveyor.engine.Engine;
import com.riseuplabs.ureport_r4v.surveyor.task.DownloadOrgTask;
import com.riseuplabs.ureport_r4v.surveyor.task.RefreshOrgTask;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.OpinionViewModel;
import com.riseuplabs.ureport_r4v.ui.opinions.flow.RunFlowActivity;
import com.riseuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialog;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogComponent;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogInterface;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;
import com.riseuplabs.ureport_r4v.utils.surveyor.SurveyorIntent;
import com.riseuplabs.ureport_r4v.utils.ui.BlockingProgress;
import com.riseuplabs.ureport_r4v.utils.ui.ViewCache;
import com.vdurmont.semver4j.Semver;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class FlowListActivity extends BaseSubmissionActivity<ActivityFlowListBinding> implements FlowListFragment.Container {

    boolean isOpen = false;
    private Org org;
    ViewCache cache;
    int pending = 0;
    String orgUUID = "invalid";
    FragmentManager fm;
    FragmentTransaction ft;
    public Fragment flowListFragment;
    SwipeRefreshLayout flowlistRefresh;
    RefreshOrgTask rotx;
    public DownloadOrgTask donx;
    private boolean skipFirst = true;
    int from = -1;
    String poll_type = "";
    String notification_flow_id = "";

    private Dialog confirmRefreshDialog;

    @Inject
    OpinionViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flow_list;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        poll_type = prefManager.getString(PrefKeys.POLL_TYPE, "poll");
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra(IntentConstant.SUBMISSION_INTENT, 1) == 0) {
            from = 0;
            if (ConnectivityCheck.isConnected(this)) {
                playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, null);
                onActionSubmit(null);
            } else {
                new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                    @Override
                    public void retry() {
                        submitToServer();
                    }

                    @Override
                    public void cancel() {
                    }
                });
            }
        }

        if (intent != null && intent.getStringExtra(IntentConstant.FLOW_ID) != null) {
            notification_flow_id = intent.getStringExtra(IntentConstant.FLOW_ID);
            refreshFlows(R.string.confirm_latest_flow_download);
        }

        Log.d(TAG, "Notification Flow ID: " + notification_flow_id);

        if (poll_type.equals("poll")) {
            binding.activityName.setText(getResources().getString(R.string.polls));
            binding.activityImage.setImageResource(R.drawable.v2_polls);
        } else {
            binding.activityName.setText(getResources().getString(R.string.your_rights));
            binding.activityImage.setImageResource(R.drawable.v2_your_rights);
        }


        isOpen = true;
//        initAnimation();
        cache = getViewCache();
        cache.setText(R.id.button_pending, NumberFormat.getInstance().format(pending));

        if (pending == 0) {
            binding.pendingSubmission.setVisibility(View.GONE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 0f, 0f, 1);
        } else {
            binding.pendingSubmission.setVisibility(View.VISIBLE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 1f, 1f, 1);
        }

        orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);


        refresh();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        flowListFragment = new FlowListFragment();
        ft.add(R.id.frameLayout, flowListFragment, "myFragmentTag");
        ft.commit();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.submitIcon.setOnClickListener(view -> {

            if (ConnectivityCheck.isConnected(this)) {
                playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view);
                onActionSubmit(view);
            } else {
                new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                    @Override
                    public void retry() {
                        submitToServer();
                    }

                    @Override
                    public void cancel() {
                        // None
                    }
                });
            }
        });

        // 5 Second Timer
        Timer5s();


    }

    @Override
    protected List<Submission> getPendingSubmissions() {
        return SurveyorApplication.get().getSubmissionService().getCompleted(getOrg());
    }

    @Override
    public Org getOrg() {
        return org;
    }

    @Override
    public List<Flow> getListItems() {
        return getOrg().getFlows();
    }

    boolean clickLock = false;

    @Override
    public void onItemClick(Flow flow) {
        if (clickLock) {
            return;
        } else {
            clickLock = true;
            // Unlock after 2 s
            new Handler().postDelayed(() -> clickLock = false, 1000);
        }

        final Dialog dialog3 = new Dialog(this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog3.setContentView(R.layout.submission_dialog);

        String questionString = " Questions";
        if (flow.getQuestionCount() == 1) {
            questionString = " Question";
        }
        NumberFormat nf = NumberFormat.getInstance();
        dialog3.findViewById(R.id.img_start).setVisibility(View.VISIBLE);
        dialog3.findViewById(R.id.img_cancel).setVisibility(View.VISIBLE);
        ((TextView) dialog3.findViewById(R.id.textMainText)).setText(flow.getName());
        ((TextView) dialog3.findViewById(R.id.textSubText)).setText(
                nf.format(flow.getQuestionCount()) + questionString + " " + "(v" + nf.format(flow.getRevision()) + ")");

        final Flow dialog_flow = flow;

        dialog3.findViewById(R.id.button_yes).setOnClickListener(view -> {

            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view);
            Intent intent = new Intent(this, RunFlowActivity.class);
            intent.putExtra(SurveyorIntent.EXTRA_FLOW_UUID, dialog_flow.getUuid());
            intent.putExtra(IntentConstant.COMING_FROM, poll_type);
            startActivity(intent);
            dialog3.dismiss();
        });

        dialog3.findViewById(R.id.button_no).setOnClickListener(view -> {

            playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, view);
            dialog3.dismiss();
        });

        dialog3.show();
    }

    public void refreshFlows(int msgID) {
        if (ConnectivityCheck.isConnected(this)) {
            confirmRefreshOrg(msgID);
            if (flowlistRefresh != null) {
                flowlistRefresh.setRefreshing(false);
            }
        } else {
            new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                @Override
                public void retry() {
                    refreshFlows(R.string.confirm_org_refresh);
                }

                @Override
                public void cancel() {
                    // None
                    ((SwipeRefreshLayout) flowListFragment.getView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
                }
            });

            if (flowlistRefresh != null) {
                flowlistRefresh.setRefreshing(false);
            }
        }
    }

    public void submitToServer() {
        if (ConnectivityCheck.isConnected(this)) {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            doSubmit();
        } else {
            new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                @Override
                public void retry() {
                    submitToServer();
                }

                @Override
                public void cancel() {
                    // None
                }
            });
        }
    }

    public void initAnimation() {
        isOpen = true;
        ObjectAnimator.ofFloat(binding.opinionList, "alpha", 0, 1f).setDuration(500).start();
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", -500, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.opinionList, "translationY", 1000, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", -200, 0).setDuration(1000).start();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isOpen) {
            isOpen = false;
        } else {
            return;
        }
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        binding.headerLayout.setBackgroundColor(Color.parseColor("#00000000"));
        ObjectAnimator.ofFloat(binding.opinionList, "alpha", 1f, 0).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", 0, -500).setDuration(500).start();
        ObjectAnimator.ofFloat(binding.opinionList, "translationY", 0, 1000).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", 0, -200).setDuration(1000).start();
        if (from == 0) {
            Navigator.navigate(FlowListActivity.this, DashBoardActivity.class);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    void Timer5s() {
        try {
            new Handler().postDelayed(() -> {
                if (getOrg() != null) {
                    pending = SurveyorApplication.get().getSubmissionService().getCompletedCount(getOrg());
                    //cache.setVisible(R.id.pending_submission, pending > 0);

                    if (pending == 0) {
                        binding.pendingSubmission.setVisibility(View.GONE);
                        StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 0f, 0f, 1);
                    } else {
                        binding.pendingSubmission.setVisibility(View.VISIBLE);
                        StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 1f, 1f, 1);
                    }

                    if (pending > 0) {
                        // Zoom In
                        StaticMethods.scaleView(
                                cache.getView(R.id.pending_submission),
                                1.0f, 1.0f,
                                1.02f, 1.02f,
                                250);

                        // Zoom Out
                        new Handler().postDelayed(() -> {
                            binding.pendingSubmission.setVisibility(View.VISIBLE);
                            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 1.02f, 1.02f, 1.0f, 1.0f, 200);
                        }, 260);

                    }

                    Timer5s();
                }
            }, 3000);
        } catch (Exception e) {
            //
        }
    }

    @Override
    protected void refresh() {
        if (orgUUID.equals("invalid")) {
            return;
        } // Not Logged In

        if (org == null) {
            try {
                org = SurveyorApplication.get().getOrgService().get(orgUUID, poll_type);
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
                finish();
                return;
            }
        }

        setTitle(org.getName());

        FlowListAdapter adapter = (FlowListAdapter) getViewCache().getListViewAdapter(android.R.id.list);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        pending = SurveyorApplication.get().getSubmissionService().getCompletedCount(getOrg());
        //cache.setVisible(R.id.pending_submission, pending > 0);
        cache.setText(R.id.button_pending, NumberFormat.getInstance().format(pending));

        if (pending == 0) {
            binding.pendingSubmission.setVisibility(View.GONE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 0f, 0f, 1);
        } else {
            binding.pendingSubmission.setVisibility(View.VISIBLE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 1f, 1f, 1);
        }

        if (confirmRefreshDialog == null) {
            if (!org.hasAssets(poll_type)) {
                // if this org doesn't have downloaded assets, ask the user if we can download them now
                if (ConnectivityCheck.isConnected(this)) {
                    confirmRefreshOrg(R.string.confirm_org_download);
                } else {
                    new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                        @Override
                        public void retry() {
                            refresh();
                            //confirmRefreshOrg(R.string.confirm_org_download);
                        }

                        @Override
                        public void cancel() {
                            // None
                        }
                    });
                }
            } else {
                for (Flow flow : org.getFlows()) {
                    if (!Engine.isSpecVersionSupported(flow.getSpecVersion())) {
                        Logger.w("Found flow " + flow.getUuid() + " with unsupported version " + flow.getSpecVersion());

                        Semver flowVersion = new Semver(flow.getSpecVersion(), Semver.SemverType.LOOSE);
                        if (flowVersion.isGreaterThan(Engine.currentSpecVersion())) {
                            // if this flow is a major version ahead of us... user needs to upgrade the app
                            promptToUpgrade();
                            break;
                        } else {
                            // if it is a major version behind, they should refresh the assets


                            if (ConnectivityCheck.isConnected(this)) {
                                confirmRefreshOrg(R.string.confirm_org_download);
                            } else {
                                new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {

                                    @Override
                                    public void retry() {
                                        confirmRefreshOrg(R.string.confirm_org_refresh_old);
                                    }

                                    @Override
                                    public void cancel() {
                                        // None
                                    }
                                });
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (confirmRefreshDialog != null) {
            confirmRefreshDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pending = SurveyorApplication.get().getSubmissionService().getCompletedCount(getOrg());
        cache.setText(R.id.button_pending, String.valueOf(pending));

        if (pending == 0) {
            binding.pendingSubmission.setVisibility(View.GONE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 0f, 0f, 1);
        } else {
            binding.pendingSubmission.setVisibility(View.VISIBLE);
            StaticMethods.scaleView(cache.getView(R.id.pending_submission), 0f, 0f, 1f, 1f, 1);
        }

        if (!skipFirst) {
            Fragment frg = null;
            frg = getSupportFragmentManager().findFragmentByTag("myFragmentTag");
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }
        skipFirst = false;
    }

    public void confirmRefreshOrg(int msgId) {

        binding.refreshRequest.setVisibility(View.GONE);

        new CustomDialog(this).displayCustomDialog(new CustomDialogComponent()
                        .setSubTextVisible(View.GONE)
                        .setMainText(getString(msgId))
                        .setButtonYes(getApplicationContext().getString(R.string.yes))
                        .setButtonNo(getApplicationContext().getString(R.string.no)),
                new CustomDialogInterface() {
                    @Override
                    public void retry() {
                        doRefresh();
                    }

                    @Override
                    public void cancel() {
                        ((SwipeRefreshLayout) flowListFragment.requireView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
                        if (msgId == R.string.confirm_org_download) {
                            binding.refreshRequest.setVisibility(View.VISIBLE);
                        }

                        try {
                            if (msgId == R.string.confirm_org_refresh && getListItems().size() == 0) {
                                binding.refreshRequest.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void doRefresh() {
        final BlockingProgress progressModal = new BlockingProgress(FlowListActivity.this, R.string.one_moment, R.string.refresh_org);
        progressModal.setOnDismissListener(dialogInterface -> {
            ((SwipeRefreshLayout) flowListFragment.requireView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
            rotx.cancel(true);
        });
        progressModal.show();

        rotx = new RefreshOrgTask(new RefreshOrgTask.Listener() {
            @Override
            public void onProgress(int percent) {
                progressModal.setProgress(percent);
            }

            public void onMessage(String message) {
                runOnUiThread(() -> progressModal.setMessage(message));
            }

            @Override
            public void onComplete() {
                progressModal.dismiss();
                List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> poll_flows = new ArrayList<>();

                for (int i = 0; i < getOrg().flowTitles.size(); i++) {
                    for (int j = 0; j < getOrg().flowTitles.get(i).getLabels().size(); j++) {
                        if (getOrg().flowTitles.get(i).getLabels().get(j).name.toLowerCase().equals(poll_type)) {
                            poll_flows.add(getOrg().flowTitles.get(i));
                        }
                    }

                }

                if (poll_flows.size() > 0) {
                    downloadAlert();
                } else {
                    showToast("No flow is available to download");
                }

                ((SwipeRefreshLayout) flowListFragment.getView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
            }

            @Override
            public void onFailure() {
                progressModal.dismiss();
                Toast.makeText(FlowListActivity.this, getString(R.string.error_org_refresh), Toast.LENGTH_SHORT).show();
            }
        });
        rotx.execute(getOrg());
    }

    public void download(List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> flows) {
        final BlockingProgress progressModal = new BlockingProgress(FlowListActivity.this, R.string.one_moment, R.string.refresh_org);
        progressModal.setOnDismissListener(dialogInterface -> {
            ((SwipeRefreshLayout) flowListFragment.requireView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
            donx.cancel(true);
        });
        progressModal.show();

        donx = new DownloadOrgTask(new DownloadOrgTask.Listener() {
            @Override
            public void onProgress(int percent) {
                progressModal.setProgress(percent);
            }

            @Override
            public void onComplete() {
                try {
                    playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);
                    StaticMethods.setLocalUpdateDate(prefManager, "surveyor_last_updated_local");

                } catch (Exception e) {
                    //
                }
                refresh();
                progressModal.dismiss();
                ((SwipeRefreshLayout) flowListFragment.getView().findViewById(R.id.flowRefreshLayout)).setRefreshing(false);
            }

            @Override
            public void onFailure() {
                progressModal.dismiss();
                Toast.makeText(FlowListActivity.this, getString(R.string.error_org_refresh), Toast.LENGTH_SHORT).show();
            }
        }, flows, poll_type);
        donx.execute(getOrg());
    }

    protected void promptToUpgrade() {

        final Dialog dialog1 = new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.submission_dialog);
        dialog1.findViewById(R.id.textSubText).setVisibility(View.GONE);
        ((TextView) dialog1.findViewById(R.id.textMainText)).setText(R.string.unsupported_version);
        ((TextView) dialog1.findViewById(R.id.button_yes_text)).setText(getText(R.string.yes));
        ((TextView) dialog1.findViewById(R.id.button_no_text)).setText(getText(R.string.no));

        dialog1.findViewById(R.id.button_yes).setOnClickListener(view -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view);
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.application_url_first))));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.application_url_second))));
            }

            dialog1.dismiss();
        });

        dialog1.findViewById(R.id.button_no).setOnClickListener(view -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, view);
            dialog1.dismiss();
        });

        dialog1.show();

    }

    @Override
    public boolean requireLogin() {
        return true;
    }

    public void downloadAlert() {

        List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> selected_flows = new ArrayList<>();
        List<com.riseuplabs.ureport_r4v.surveyor.net.responses.Flow> poll_flows = new ArrayList<>();
        List<Flow> pre_selected_flows = new ArrayList<>();

        pre_selected_flows = getListItems();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FlowListActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_download_flow_list, null);
        alertDialog.setView(convertView);
        AlertDialog dialog = alertDialog.create();

        RecyclerView rvOP = convertView.findViewById(R.id.recyclerViewOP);
        RelativeLayout btnDownload = convertView.findViewById(R.id.btnDownload);
        RelativeLayout btnCancel = convertView.findViewById(R.id.btnCancel);
        LinearLayout layout_opinions = convertView.findViewById(R.id.layout_opinions);
        TextView no_flows = convertView.findViewById(R.id.no_flows);


        rvOP.setLayoutManager(new LinearLayoutManager(FlowListActivity.this));
        rvOP.setHasFixedSize(true);

        for (int i = 0; i < getOrg().flowTitles.size(); i++) {
            if(getOrg().flowTitles.get(i).getUuid().equals(notification_flow_id)){
                getOrg().flowTitles.get(i).setSelected(true);
                selected_flows.add(getOrg().flowTitles.get(i));
            }
            for (int j = 0; j < getOrg().flowTitles.get(i).getLabels().size(); j++) {
                if (getOrg().flowTitles.get(i).getLabels().get(j).name.toLowerCase().equals(poll_type)) {
                    Log.d(TAG, "downloadAlert : " + pre_selected_flows.size());
                    for (int k = 0; k < pre_selected_flows.size(); k++) {

                        if (pre_selected_flows.get(k).getUuid().equals(getOrg().flowTitles.get(i).getUuid())) {
                            getOrg().flowTitles.get(i).setSelected(true);
                            selected_flows.add(getOrg().flowTitles.get(i));
                        }
                    }
                    poll_flows.add(getOrg().flowTitles.get(i));
                }
            }

        }

        FlowDownloadListAdapter adapterOP = new FlowDownloadListAdapter(SurveyorApplication.get());
        rvOP.setAdapter(adapterOP);


        if (poll_flows.size() == 0) {
            layout_opinions.setVisibility(View.GONE);
        } else {
            layout_opinions.setVisibility(View.VISIBLE);
        }


        if (getOrg().flowTitles.size() == 0) {
            no_flows.setVisibility(View.VISIBLE);
            btnDownload.setVisibility(View.GONE);
        } else {
            btnDownload.setVisibility(View.VISIBLE);
            no_flows.setVisibility(View.GONE);
        }

        if (getOrg() != null) {
            adapterOP.addItems(poll_flows);
        }

        btnDownload.setOnClickListener(v -> {

            if (adapterOP.getCheckedList() != null) {
                selected_flows.clear();
                selected_flows.addAll(adapterOP.getCheckedList());
            }
            if (selected_flows.size() != 0) {
                dialog.cancel();
                download(selected_flows);
            } else {
                showToast("Please select at least one flow");
            }

        });

        btnCancel.setOnClickListener(v -> {
            dialog.cancel();
        });

        dialog.setCancelable(false);

        if(notification_flow_id.equals("")){
            dialog.show();
        }else{
            selected_flows.addAll(adapterOP.getCheckedList());
            download(selected_flows);
            notification_flow_id = "";
        }


    }

}
