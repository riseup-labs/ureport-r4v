package com.risuplabs.ureport;

import androidx.annotation.Nullable;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.risuplabs.ureport.base.BaseActivity;
import com.risuplabs.ureport.databinding.ActivitySplashBinding;
import com.risuplabs.ureport.network.data.ApiConstants;
import com.risuplabs.ureport.notification.ReminderBroadcast;
import com.risuplabs.ureport.ui.auth.LoginActivity;
import com.risuplabs.ureport.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport.ui.org.OrgChooseActivity;
import com.risuplabs.ureport.ui.splash.SplashViewModel;
import com.risuplabs.ureport.utils.Navigator;
import com.risuplabs.ureport.utils.StaticMethods;
import com.risuplabs.ureport.utils.custom_dialog.LocationChooser;
import com.risuplabs.ureport.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport.utils.pref_manager.SharedPrefManager;

import java.util.Calendar;
import java.util.Locale;

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

        if(prefManager.getString(PrefKeys.ORG_LABEL,"").equals("")){
            LocationChooser.showDialog(this,prefManager);
        }else{
            Navigator.navigate(this,DashBoardActivity.class);
            finish();
        }

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