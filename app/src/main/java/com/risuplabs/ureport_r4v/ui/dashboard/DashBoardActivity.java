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
import androidx.core.app.NavUtils;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.risuplabs.ureport_r4v.base.BaseSubmissionActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityDashboardBinding;
import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.surveyor.data.Org;
import com.risuplabs.ureport_r4v.surveyor.data.Submission;
import com.risuplabs.ureport_r4v.surveyor.task.SubmitSubmissionsTask;
import com.risuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.risuplabs.ureport_r4v.ui.results.result_list.ResultListActivity;
import com.risuplabs.ureport_r4v.ui.settings.SettingsActivity;
import com.risuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;
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

        binding.stories.setOnClickListener(v ->{
            Navigator.navigate(this,StoriesListActivity.class);
        });

        binding.results.setOnClickListener(v ->{
            Navigator.navigate(this,PollsActivity.class);
        });

        binding.settings.setOnClickListener(v ->{
            Navigator.navigate(this,SettingsActivity.class);
        });

        binding.polls.setOnClickListener(v ->{
            Intent intent = new Intent(this,FlowListActivity.class);
            intent.putExtra(IntentConstant.COMING_FROM,"poll");
            startActivity(intent);
        });

        binding.yourRights.setOnClickListener(v->{
            Intent intent = new Intent(this,FlowListActivity.class);
            intent.putExtra(IntentConstant.COMING_FROM,"bot");
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