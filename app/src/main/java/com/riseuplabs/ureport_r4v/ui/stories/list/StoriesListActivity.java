package com.riseuplabs.ureport_r4v.ui.stories.list;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityStoriesBinding;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;

public class StoriesListActivity extends BaseActivity<ActivityStoriesBinding> {

    boolean isOpen = false;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stories;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        isOpen = true;

        binding.activityName.setText(R.string.v1_stories);

//        ObjectAnimator.ofFloat(binding.storyList, "alpha",  0, 1f).setDuration(500).start();
//        ObjectAnimator.ofFloat(binding.bgColor, "translationY", -500, 0).setDuration(1000).start();
//        ObjectAnimator.ofFloat(binding.storyList, "translationY", 1000, 0).setDuration(1000).start();
//        ObjectAnimator.ofFloat(binding.backButton, "translationX", -200, 0).setDuration(1000).start();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        isOpen = true;
    }

    @Override
    public void onBackPressed() {
        if(isOpen){ isOpen = false; }else{ return; }
        StaticMethods.playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        binding.headerLayout.setBackgroundColor(Color.parseColor("#00000000"));
//        ObjectAnimator.ofFloat(binding.storyList, "alpha",  1f, 0).setDuration(750).start();
//        ObjectAnimator.ofFloat(binding.bgColor, "translationY", 0, -500).setDuration(500).start();
//        ObjectAnimator.ofFloat(binding.storyList, "translationY", 0, 1000).setDuration(750).start();
//        ObjectAnimator.ofFloat(binding.backButton, "translationX", 0, -200).setDuration(1000).start();
        super.onBackPressed();
    }



}