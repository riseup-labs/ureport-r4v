package com.riseuplabs.ureport_r4v.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StaticMethods {

    public static final int NO_SOUND  = -1;

    public static void scaleView(View v, float startX, float startY, float endX, float endY, int duration) {
        Animation anim = new ScaleAnimation(
                startX, endX, // Start and end values for the X axis scaling
                startY, endY, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(duration);
        v.startAnimation(anim);
    }

    public static void playNotification(SharedPrefManager prefManager,Context context, int sound_id, View view){

        if(context == null){return;}
        try {
            if (prefManager.getBoolean(PrefKeys.SOUND,true)) {
                final MediaPlayer mp = MediaPlayer.create(context, sound_id);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.start();
                mp.setOnCompletionListener(mediaPlayer -> mp.release());
            }

            if (view != null && prefManager.getBoolean(PrefKeys.VIBRATION,true)) {

                view.performHapticFeedback(
                        HapticFeedbackConstants.VIRTUAL_KEY,
                        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playNotification(SharedPrefManager prefManager, Context context, int sound_id){
        playNotification(prefManager, context, sound_id, null);
    }

    public static String getLocalUpdateDate(SharedPrefManager prefManager, String tag){
        return prefManager.getString(tag, "");
    }

    public static void setLocalUpdateDate(SharedPrefManager prefManager,String tag) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy hh:mm:ss a", Locale.ENGLISH);
        String date_local = sdf.format(new Date());
        prefManager.putString(tag, date_local);
    }

    public static void setLanguage(Activity context, String language, String country){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.createConfigurationContext(config);
        resources.updateConfiguration(config,resources.getDisplayMetrics());

    }

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static final String getMD5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while(h.length() < 2){
                    h = "0" + h;
                }
                hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
