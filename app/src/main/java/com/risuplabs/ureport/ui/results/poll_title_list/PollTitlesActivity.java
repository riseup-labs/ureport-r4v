package com.risuplabs.ureport.ui.results.poll_title_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.base.BaseActivity;
import com.risuplabs.ureport.databinding.ActivityPollTitlesBinding;

import static com.risuplabs.ureport.utils.StaticMethods.playNotification;

public class PollTitlesActivity extends BaseActivity<ActivityPollTitlesBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_poll_titles;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        ObjectAnimator.ofFloat(binding.storyList, "alpha",  0, 1f).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", -500, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.storyList, "translationY", 1000, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", -200, 0).setDuration(1000).start();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        binding.headerLayout.setBackgroundColor(Color.parseColor("#00000000"));
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", 0, -500).setDuration(500).start();
        ObjectAnimator.ofFloat(binding.storyList, "alpha",  1f, 0).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.storyList, "translationY", 0, 1000).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", 0, -200).setDuration(1000).start();
        super.onBackPressed();
    }
}