package com.risuplabs.ureport_r4v.ui.dashboard;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.risuplabs.ureport_r4v.base.BaseSubmissionActivity;
import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.surveyor.data.Org;
import com.risuplabs.ureport_r4v.surveyor.data.Submission;
import com.risuplabs.ureport_r4v.surveyor.task.SubmitSubmissionsTask;
import com.risuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport_r4v.ui.results.result_list.ResultListActivity;
import com.risuplabs.ureport_r4v.ui.settings.SettingsActivity;
import com.risuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport_r4v.utils.ui.ViewCache;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.adapter.CustomScrollAdapter;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityMainBinding;
import com.risuplabs.ureport_r4v.model.dashboard.ModelDashboardListRV;
import com.risuplabs.ureport_r4v.ui.stories.list.StoriesListActivity;
import com.risuplabs.ureport_r4v.utils.StaticMethods;

import java.util.ArrayList;
import java.util.List;

import static com.risuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class DashBoardActivity extends BaseSubmissionActivity<ActivityMainBinding> implements CustomScrollAdapter.ClickListener {

    List<ModelDashboardListRV> dashboardLists = new ArrayList<>();
    CustomScrollAdapter scrollAdapter;
    int selectedButton = 0;
    int previousButton = 0;
    int pending;
    boolean sound_token = false;
    int last_card = 0;
    View lastBottomView = null;
    ConstraintLayout selected_button = null, previous_button = null;
    private Org org;
    String orgUUID = "";
    String from = "";
    ViewCache cache;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {
        initDashboard();
        cache = getViewCache();
        orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
        if(org == null && !orgUUID.equals("")) {
            try {
                org = SurveyorApplication.get().getOrgService().get(orgUUID);
            } catch (Exception e){
                Log.e(TAG, "onViewReady: ",e);
            }
        }

        if(org != null) {
            Log.d(TAG, "onViewReadyPending: "+pending);
            cache.setText(R.id.pendingOpinion, String.valueOf(pending));
            Log.d(TAG, "onViewReadyPending: "+pending);
            if(pending == 0){
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
            }else{
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 1f, 1f, 1);
            }
        }else{
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
        }
    }

    boolean clickLock = false;

    @Override
    public void onItemClick(int position, View v) {

        if (clickLock) {
            return;
        } else {
            clickLock = true;
            // Unlock after 2 s
            new Handler().postDelayed(() -> clickLock = false, 1500);
        }

        ImageView imageSun = findViewById(R.id.imageSun);
        View bgColor = v.findViewById(R.id.bgColor);
        ImageView cardImage = v.findViewById(R.id.cardImage);
        View bgShade = v.findViewById(R.id.bg_shadow);
        TextView activityName = v.findViewById(R.id.activityName);

        Intent intent = null;

        switch (position) {

            case 0:
                intent = new Intent(this, StoriesListActivity.class);
                break;
            case 1:
                intent = new Intent(this, ResultListActivity.class);
                break;
            case 2:
                intent = new Intent(this, FlowListActivity.class);
                break;
            case 3:
                intent = new Intent(this, SettingsActivity.class);

        }

        Pair<View, String> p1 = Pair.create((View) cardImage, cardImage.getTransitionName());
        Pair<View, String> p2 = Pair.create((View) bgShade, bgShade.getTransitionName());
        Pair<View, String> p3 = Pair.create((View) activityName, activityName.getTransitionName());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(DashBoardActivity.this, p1, p2, p3);

        Fade fade = new Fade();
        fade.excludeChildren(cardImage, true);
        fade.excludeChildren(bgShade, true);
        fade.excludeChildren(activityName, true);
        fade.excludeChildren(imageSun, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        startActivity(intent, options.toBundle());

    }

    void initDashboard() {

        dashboardLists.add(new ModelDashboardListRV(getString(R.string.v1_stories), R.drawable.v1_card_bg_stories, R.drawable.v1_header_image_stories, R.drawable.v1_bg_shade_stories));
        dashboardLists.add(new ModelDashboardListRV(getString(R.string.v1_ureport), R.drawable.v1_card_bg_stories, R.drawable.v1_header_image_results, R.drawable.v1_bg_shade_results));
        dashboardLists.add(new ModelDashboardListRV(getString(R.string.v1_survey), R.drawable.v1_card_bg_stories, R.drawable.v1_header_image_opinion, R.drawable.v1_bg_shade_stories));
        dashboardLists.add(new ModelDashboardListRV(getString(R.string.v1_settings), R.drawable.v1_card_bg_stories, R.drawable.v1_header_image_settings, R.drawable.v1_bg_shade_results));

        scrollAdapter = new CustomScrollAdapter(dashboardLists);
        scrollAdapter.setOnItemClickListener(this);
        binding.scrollView.setAdapter(scrollAdapter);

        if(getIntent().getStringExtra(IntentConstant.COMING_FROM) != null){
            from = getIntent().getStringExtra(IntentConstant.COMING_FROM);
            if(from.equals("opinions")){
                binding.scrollView.scrollToPosition(2);
                setBottomBar(2);
            }
        }

        binding.scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());

        setButtonClickLisener(binding.btnStories, 0);
        setButtonClickLisener(binding.btnOpinions, 1);
        setButtonClickLisener(binding.btnResults, 2);
        setButtonClickLisener(binding.btnSettings, 3);

        StaticMethods.scaleView(binding.btnStories.getViewById(R.id.btn_imageS), 1f, 1f, 1.2f, 1.2f, 0);
        StaticMethods.scaleView(binding.btnStories.getViewById(R.id.btn_textS), 1f, 1f, 1.2f, 1.2f, 0);

        binding.scrollView.addOnItemChangedListener((viewHolder, i) -> animateButtonClick(i));

        binding.scrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                sound_token = true;
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // Animate Bottom Bars
                setBottomBar(i);
                last_card = i;
            }

            @Override
            public void onScroll(float v, int i, int i1, @Nullable RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ViewHolder t1) {
                //
                if (sound_token) {
                    StaticMethods.playNotification(prefManager, getApplicationContext(), R.raw.swipe_sound, viewHolder.itemView);
                    sound_token = false;
                }
            }
        });


    }

    void setButtonClickLisener(ConstraintLayout button, final int id) {
        button.setOnClickListener(view -> {
            if (selectedButton == id) {
                onItemClick(id, binding.scrollView.getViewHolder(id).itemView);
            }
            binding.scrollView.smoothScrollToPosition(id);
        });
    }

    void animateButtonClick(final int position) {

        changeBackgroundColor(position);

        if (selected_button == null || previous_button == null) {
            return;
        }

        if (selectedButton == position) { // Same Button or No Click Event
            return;
        }

        animatePrevButton(previous_button);
        animateSelectedButton(selected_button);

        // Opinions Number Circle
        if (selected_button == binding.btnResults && pending > 0) {
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 1f, 1f, 1.2f, 1.2f, 300);
        }

        if (previous_button == binding.btnResults && pending > 0) {
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 1.2f, 1.2f, 1f, 1f, 150);
        }

        selectedButton = position;
    }

    /**
     * Change Dashboard Background Color with Animation
     *
     * @param from
     * @param to
     */
    public void colorChangeAnimator(int from, int to) {
//        if(gotoSurveyor){
//            findViewById(R.id.root_layout).setBackgroundColor(Color.argb(255,255,247,239));
//            return;
//        }

        ValueAnimator colorAnim = ObjectAnimator.ofInt(findViewById(R.id.root_layout), "backgroundColor", from, to);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(0);
        colorAnim.start();
    }

    /**
     * Select / Move Bottom Bar in the Dashboard
     *
     * @param num
     */
    public void setBottomBar(int num) {

        if (lastBottomView == null) {
            lastBottomView = findViewById(R.id.bottom_bar_1);
        }

        View newView = null;

        if (num == 0) {
            newView = findViewById(R.id.bottom_bar_1);
        }
        if (num == 1) {
            newView = findViewById(R.id.bottom_bar_2);
        }
        if (num == 2) {
            newView = findViewById(R.id.bottom_bar_3);
        }
        if (num == 3) {
            newView = findViewById(R.id.bottom_bar_4);
        }

        int colorGrey = Color.argb(255, 214, 214, 214);
        int colorBlack = Color.argb(255, 0, 0, 0);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(lastBottomView, "backgroundColor", colorBlack, colorGrey);
        colorAnim.setDuration(500);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(0);
        colorAnim.start();

        ValueAnimator colorAnim2 = ObjectAnimator.ofInt(newView, "backgroundColor", colorGrey, colorBlack);
        colorAnim2.setDuration(500);
        colorAnim2.setEvaluator(new ArgbEvaluator());
        colorAnim2.setRepeatCount(0);
        colorAnim2.start();

        lastBottomView = newView;
    }

    private void animatePrevButton(ConstraintLayout previous_button) {
        if (previous_button == binding.btnStories) {
            ((TextView) previous_button.getViewById(R.id.btn_textS)).setTextColor(Color.rgb(97, 97, 97));
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_imageS), 1.2f, 1.2f, 1f, 1f, 150);
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_textS), 1.2f, 1.2f, 1f, 1f, 150);
        } else if (previous_button == binding.btnOpinions) {
            ((TextView) previous_button.getViewById(R.id.btn_textO)).setTextColor(Color.rgb(97, 97, 97));
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_imageO), 1.2f, 1.2f, 1f, 1f, 150);
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_textO), 1.2f, 1.2f, 1f, 1f, 150);
        } else if (previous_button == binding.btnResults) {
            ((TextView) previous_button.getViewById(R.id.btn_textR)).setTextColor(Color.rgb(97, 97, 97));
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_imageR), 1.2f, 1.2f, 1f, 1f, 150);
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_textR), 1.2f, 1.2f, 1f, 1f, 150);
        } else if (previous_button == binding.btnSettings) {
            ((TextView) previous_button.getViewById(R.id.btn_textSe)).setTextColor(Color.rgb(97, 97, 97));
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_imageSe), 1.2f, 1.2f, 1f, 1f, 150);
            StaticMethods.scaleView(previous_button.getViewById(R.id.btn_textSe), 1.2f, 1.2f, 1f, 1f, 150);
        }
    }

    private void animateSelectedButton(ConstraintLayout selected_button) {
        if (selected_button == binding.btnStories) {
            ((TextView) selected_button.getViewById(R.id.btn_textS)).setTextColor(Color.rgb(33, 33, 33));
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_imageS), 1f, 1f, 1.2f, 1.2f, 300);
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_textS), 1f, 1f, 1.2f, 1.2f, 300);
        } else if (selected_button == binding.btnOpinions) {
            ((TextView) selected_button.getViewById(R.id.btn_textO)).setTextColor(Color.rgb(33, 33, 33));
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_imageO), 1f, 1f, 1.2f, 1.2f, 300);
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_textO), 1f, 1f, 1.2f, 1.2f, 300);
        } else if (selected_button == binding.btnResults) {
            ((TextView) selected_button.getViewById(R.id.btn_textR)).setTextColor(Color.rgb(33, 33, 33));
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_imageR), 1f, 1f, 1.2f, 1.2f, 300);
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_textR), 1f, 1f, 1.2f, 1.2f, 300);
        } else if (selected_button == binding.btnSettings) {
            ((TextView) selected_button.getViewById(R.id.btn_textSe)).setTextColor(Color.rgb(33, 33, 33));
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_imageSe), 1f, 1f, 1.2f, 1.2f, 300);
            StaticMethods.scaleView(selected_button.getViewById(R.id.btn_textSe), 1f, 1f, 1.2f, 1.2f, 300);
        }
    }

    private void changeBackgroundColor(int position) {

        int newColor = 0, prevColor = 0;

        switch (position) {
            case 0:
                selected_button = binding.btnStories;
                newColor = Color.argb(255, 239, 255, 247);
                break;
            case 1:
                selected_button = binding.btnOpinions;
                newColor = Color.argb(255, 255, 253, 241);
                break;
            case 2:
                selected_button = binding.btnResults;
                newColor = Color.argb(255, 255, 247, 239);
                break;
            case 3:
                selected_button = binding.btnSettings;
                newColor = Color.argb(255, 249, 253, 255);
                break;
        }
        switch (selectedButton) {
            case 0:
                previous_button = binding.btnStories;
                previousButton = 0;
                prevColor = Color.argb(255, 239, 255, 247);
                break;
            case 1:
                previous_button = binding.btnOpinions;
                previousButton = 1;
                prevColor = Color.argb(255, 255, 253, 241);
                break;
            case 2:
                previous_button = binding.btnResults;
                previousButton = 2;
                prevColor = Color.argb(255, 255, 247, 239);
                break;
            case 3:
                previous_button = binding.btnSettings;
                previousButton = 3;
                prevColor = Color.argb(255, 249, 253, 255);
                break;
        }

        // Animate Color
//        colorChangeAnimator(prevColor, newColor);


    }

    /**
     * Checks for Pending Submission and Redraws Texts for Langauge Change
     */
    @Override
    public void onResume(){
        super.onResume();
        getWindow().setEnterTransition(null);

        reload();

        if(org != null) {
            pending =SurveyorApplication.get().getSubmissionService().getCompletedCount(org);
            cache.setText(R.id.pendingOpinion, String.valueOf(pending));

            Log.d(TAG, "onResume: "+pending);

            if(pending > 0){
                if(ConnectivityCheck.isConnected(this)){
                    doSubmitOnInternetAvailable();
                }
            }

            if(pending == 0){
                StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
            }else{
                if(selectedButton == 2){
                    StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 1.2f, 1.2f, 1);
                }else{
                    StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 1f, 1f, 1);
                }
            }
        }else{
            StaticMethods.scaleView(findViewById(R.id.pendingOpinion), 0f, 0f, 0f, 0f, 1);
        }

    }

    public void reload(){
        binding.textAppName.setText(R.string.v1_welcome_to_app);
        binding.textSubtitle.setText(R.string.your_voice_matters);
        binding.btnTextS.setText(R.string.v1_stories);
        binding.btnTextO.setText(R.string.v1_ureport);
        binding.btnTextR.setText(R.string.v1_survey);
        binding.btnTextSe.setText(R.string.v1_settings);
        dashboardLists.get(0).setName(getString(R.string.v1_stories));
        dashboardLists.get(1).setName(getString(R.string.v1_ureport));
        dashboardLists.get(2).setName(getString(R.string.v1_survey));
        dashboardLists.get(3).setName(getString(R.string.v1_settings));
        scrollAdapter.notifyDataSetChanged();
    }

    @Override
    protected List<Submission> getPendingSubmissions() {
        if(getOrg() != null) {
            return SurveyorApplication.get().getSubmissionService().getCompleted(getOrg());
        }else{
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