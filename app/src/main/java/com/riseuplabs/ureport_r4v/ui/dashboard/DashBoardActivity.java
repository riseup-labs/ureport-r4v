package com.riseuplabs.ureport_r4v.ui.dashboard;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseSubmissionActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityDashboardBinding;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.surveyor.data.Submission;
import com.riseuplabs.ureport_r4v.surveyor.task.SubmitSubmissionsTask;
import com.riseuplabs.ureport_r4v.ui.about.AboutActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.riseuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.riseuplabs.ureport_r4v.ui.settings.SettingsActivity;
import com.riseuplabs.ureport_r4v.ui.stories.list.StoriesListActivity;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.riseuplabs.ureport_r4v.utils.ui.ViewCache;

import java.util.List;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class DashBoardActivity extends BaseSubmissionActivity<ActivityDashboardBinding>  {


    int pending = 3;
    private Org org;
    String orgUUID = "";
    String from = "";
    ViewCache cache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected void onStart() {
        super.onStart();
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        cache = getViewCache();
        orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
        if (org == null && !orgUUID.equals("")) {
            try {
                org = SurveyorApplication.get().getOrgService().get(orgUUID,"poll");
            } catch (Exception e) {
                Log.e(TAG, "onViewReady: ", e);
            }
        }

        if (org != null) {
            Log.d(TAG, "onViewReadyPending: " + pending);
            cache.setText(R.id.pendingOpinion, String.valueOf(pending));
            Log.d(TAG, "onViewReadyPending: " + pending);
            if (pending == 0) {
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
            } else {
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 1f, 1f, 1);
            }
        } else {
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
        }

        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_brasil);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_ecuador);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_bolivia_logo);
        }

        binding.stories.setOnClickListener(v ->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Navigator.navigate(this,StoriesListActivity.class);
        });

        binding.results.setOnClickListener(v ->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Navigator.navigate(this,PollsActivity.class);
        });

        binding.settings.setOnClickListener(v ->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Navigator.navigate(this,SettingsActivity.class);
        });

        binding.polls.setOnClickListener(v ->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            prefManager.putString(PrefKeys.POLL_TYPE,"poll");
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Intent intent = new Intent(this,FlowListActivity.class);
            intent.putExtra(IntentConstant.COMING_FROM,"poll");
            startActivity(intent);
        });

        binding.yourRights.setOnClickListener(v->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            prefManager.putString(PrefKeys.POLL_TYPE,"bot");
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Intent intent = new Intent(this,FlowListActivity.class);
            intent.putExtra(IntentConstant.COMING_FROM,"bot");
            startActivity(intent);
        });

        binding.about.setOnClickListener(v->{
            StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().setEnterTransition(null);

        reload();

        if (org != null) {
            pending = SurveyorApplication.get().getSubmissionService().getCompletedCount(org);
            cache.setText(R.id.pendingOpinion, String.valueOf(pending));

            Log.d(TAG, "onResume: " + pending);

            if (pending > 0) {
                if (ConnectivityCheck.isConnected(this)) {
                    doSubmitOnInternetAvailable();
                }
            }

            if (pending == 0) {
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
            } else {
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 1.2f, 1.2f, 1);

            }
        } else {
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
        }

    }

    public void reload() {
        binding.textAppName.setText(R.string.v1_welcome_to_app);
        binding.textSubtitle.setText(R.string.your_voice_matters);
        binding.btnTextStories.setText(R.string.v1_stories);
        binding.btnTextResults.setText(R.string.v1_ureport);
        binding.btnTextSettings.setText(R.string.v1_settings);
        binding.yourRightsText.setText(R.string.your_rights);
        binding.pollsText.setText(R.string.polls);
        binding.aboutText.setText(R.string.about);

        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_brasil);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_ecuador);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_bolivia_logo);
        }
    }

    @Override
    protected List<Submission> getPendingSubmissions() {
        if (getOrg() != null) {
            return SurveyorApplication.get().getSubmissionService().getCompleted(getOrg());
        } else {
            return null;
        }
    }

    @Override
    protected Org getOrg() {
        return org;
    }

    @Override
    protected void refresh() {

    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    public void doSubmitOnInternetAvailable() {

        final List<Submission> pending = getPendingSubmissions();
        final Submission[] asArray = pending.toArray(new Submission[0]);
        final Resources res = getResources();

        SubmitSubmissionsTask task = new SubmitSubmissionsTask(new SubmitSubmissionsTask.Listener() {
            @Override
            public void onProgress(int percent) {
            }

            @Override
            public void onComplete(int total) {

                cancelAlarm(getApplicationContext());

                playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);

                CharSequence toast = res.getQuantityString(R.plurals.submissions_sent, total, total);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
            }

            @Override
            public void onFailure(int numFailed) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_submissions_send), Toast.LENGTH_SHORT).show();
            }
        });

        task.execute(asArray);
    }
}