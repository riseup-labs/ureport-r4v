package com.risuplabs.ureport_r4v.ui.results.polls;

import androidx.annotation.Nullable;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.adapter.PollsAdapter;
import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityPollsBinding;
import com.risuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.risuplabs.ureport_r4v.utils.DateFormatUtils;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.text.ParseException;

import javax.inject.Inject;

import static com.risuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class PollsActivity extends BaseActivity<ActivityPollsBinding> {

    int id = 0;
    int org_id = 0;
    PollsAdapter mAdapter;

    @Inject
    ResultsViewModel viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_polls;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        Bundle b = getIntent().getBundleExtra(IntentConstant.INTENT_DATA);
        id = b.getInt(IntentConstant.POLL_ID);

        org_id = prefManager.getInt(PrefKeys.ORG_ID);

        initAnimation();
        initRecyclerView();

        viewModel.getQuestionsFromLocal(id,org_id).observe(this, response ->{
            Log.d(TAG, "onViewReadyQuestion: "+response.get(0).questions.size());
            mAdapter.addItems(response.get(0).questions);
            binding.activityName.setText(b.getString(IntentConstant.ACTIVITY_NAME));
            try {
                mAdapter.setPollDate(DateFormatUtils.getPollDate(response.get(0).poll_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        mAdapter = new PollsAdapter(this,binding.pollSummary);
        binding.recyclerView.setAdapter(mAdapter);
    }

    public void initAnimation(){
        ObjectAnimator.ofFloat(findViewById(R.id.bgColor), "translationY", -500, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(findViewById(R.id.headerLayout), "alpha",  0, 1f).setDuration(1000).start();
        ObjectAnimator.ofFloat(findViewById(R.id.uCard), "translationY", 1000, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(findViewById(R.id.backButton), "translationX", -200, 0).setDuration(1000).start();
    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        super.onBackPressed();
    }


}