package com.riseuplabs.ureport_r4v;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.riseuplabs.ureport_r4v.base.BaseActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivitySplashBinding;
import com.riseuplabs.ureport_r4v.ui.auth.ProgramChooserActivity;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.riseuplabs.ureport_r4v.ui.splash.SplashViewModel;
import com.riseuplabs.ureport_r4v.utils.IntentConstant;
import com.riseuplabs.ureport_r4v.utils.Navigator;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import javax.inject.Inject;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class Splash extends BaseActivity<ActivitySplashBinding> {

    @Inject
    SplashViewModel splashViewModel;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
        binding.txtDeveloped.setText(R.string.v1_settings_designed_and_developed);


        AndroidRemoteDebugger.init(
                new AndroidRemoteDebugger.Builder(this)
                        .enabled(false)
                        .enableDuplicateLogging()
                        .excludeUncaughtException()
                        .port(8082)
                        .build()
        );

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("flow_id") != null) {
            Intent intent = new Intent(this, FlowListActivity.class);
            intent.putExtra(IntentConstant.FLOW_ID, getIntent().getExtras().getString("flow_id"));
            prefManager.putString(PrefKeys.POLL_TYPE, getIntent().getExtras().getString("flow_type"));
            startActivity(intent);
            finish();
        }else{
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Log.d(TAG, "onViewReady: "+prefManager.getString(PrefKeys.ORG_LABEL,""));
                if(prefManager.getString(PrefKeys.LOGIN,"").equals("")){
                    StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
                    Navigator.navigate(this, ProgramChooserActivity.class);
                    finish();
                }else{
                    StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
                    Navigator.navigate(this,DashBoardActivity.class);
                    finish();
                }
            },2000);
        }



    }





}