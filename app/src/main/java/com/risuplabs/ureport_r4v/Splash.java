package com.risuplabs.ureport_r4v;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

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
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        AndroidRemoteDebugger.init(
                new AndroidRemoteDebugger.Builder(this)
                        .enabled(true)
                        .enableDuplicateLogging()
                        .excludeUncaughtException()
                        .port(8082)
                        .build()
        );

        createNotificationChannel();

        setLanguage();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(prefManager.getString(PrefKeys.ORG_LABEL,"").equals("")){
                Navigator.navigate(Splash.this, ProgramChooserActivity.class);
                finish();
            }else{
                Navigator.navigate(Splash.this,DashBoardActivity.class);
                finish();
            }
        },2000);

    }

    void setLanguage(){
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE),prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"en"));
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