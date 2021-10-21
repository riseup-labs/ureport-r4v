package com.risuplabs.ureport_r4v;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.risuplabs.ureport_r4v.base.BaseActivity;
import com.risuplabs.ureport_r4v.databinding.ActivitySplashBinding;
import com.risuplabs.ureport_r4v.ui.auth.ProgramChooserActivity;
import com.risuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport_r4v.ui.splash.SplashViewModel;
import com.risuplabs.ureport_r4v.utils.Navigator;
import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.custom_dialog.LocationChooser;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import javax.inject.Inject;

import zerobranch.androidremotedebugger.AndroidRemoteDebugger;

public class Splash extends BaseActivity<ActivitySplashBinding> {

    @Inject
    SplashViewModel splashViewModel;

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

        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY));
        binding.txtDeveloped.setText(R.string.v1_settings_designed_and_developed);

        AndroidRemoteDebugger.init(
                new AndroidRemoteDebugger.Builder(this)
                        .enabled(true)
                        .enableDuplicateLogging()
                        .excludeUncaughtException()
                        .port(8082)
                        .build()
        );

//        createNotificationChannel();



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

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NotificationChannel";
            String description = "Channel for Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }




}