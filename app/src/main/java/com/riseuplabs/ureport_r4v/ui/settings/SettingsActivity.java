package com.riseuplabs.ureport_r4v.ui.settings;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.riseuplabs.ureport_r4v.BuildConfig;
import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivitySettingsBinding;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.io.IOException;

import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class SettingsActivity extends BaseSurveyorActivity<ActivitySettingsBinding> {

    boolean isOpen = false;
    private Org org;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onStart() {
        super.onStart();
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        binding.versionName.setText("Ver : "+BuildConfig.VERSION_NAME);

        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
            binding.activityImage.setImageResource(R.drawable.uv_brasil);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
            binding.activityImage.setImageResource(R.drawable.uv_ecuador);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
            binding.activityImage.setImageResource(R.drawable.uv_bolivia);
        }

        if (!isLoggedIn()) {
            binding.textLogout.setText(R.string.login);
        }

        selectedLanguageButton(prefManager.getString(PrefKeys.SELECTED_LANGUAGE, "es"), prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
        selectedCountryButton(prefManager.getInt(PrefKeys.ORG_ID,37));

        selectSoundState(prefManager.getBoolean(PrefKeys.SOUND, true));
        selectVibrationState(prefManager.getBoolean(PrefKeys.VIBRATION, true));

        binding.layerLogout.setOnClickListener(v -> {
            playNotification(prefManager, this, R.raw.button_click_yes, v);
            if (!isLoggedIn()) {
                logout(-2);
            } else {
                logout(-3);
                binding.textLogout.setText(R.string.login);
            }

        });

        binding.btnSoundOn.setOnClickListener(v -> {
            setSound_State(true);
            selectSoundState(true);
            playNotification(prefManager, this, R.raw.setting_button_change, v);
        });

        binding.btnSoundOff.setOnClickListener(v -> {
            setSound_State(false);
            selectSoundState(false);
            playNotification(prefManager, this, R.raw.setting_button_change, v);
        });

        binding.btnVibrationOn.setOnClickListener(v -> {
            setVibration_State(true);
            selectVibrationState(true);
            playNotification(prefManager, this, R.raw.setting_button_change, v);
        });

        binding.btnVibrationOff.setOnClickListener(v -> {
            setVibration_State(false);
            selectVibrationState(false);
            playNotification(prefManager, this, R.raw.setting_button_change, v);
        });

        binding.btnEnglish.setOnClickListener(v -> {
            setLanguage("en", "", v);
        });

        binding.btnSpanish.setOnClickListener(v -> {
            setLanguage("es", "rBO", v);
        });

        binding.btnPortugues.setOnClickListener(v -> {
            setLanguage("pt", "rBR", v);
        });

        binding.btnCountryBrazil.setOnClickListener(v -> {
            unsubscribeCurrentTopic(prefManager.getString(PrefKeys.ORG_LABEL));
            try {
                setCountry(AppConstant.BRAZIL_ORG_ID, v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.btnCountryEcuador.setOnClickListener(v -> {
            unsubscribeCurrentTopic(prefManager.getString(PrefKeys.ORG_LABEL));
            try {
                setCountry(AppConstant.ECUADOR_ORG_ID, v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.btnCountryBolivia.setOnClickListener(v -> {
            unsubscribeCurrentTopic(prefManager.getString(PrefKeys.ORG_LABEL));
            try {
                setCountry(AppConstant.BOLIVIA_ORG_ID, v);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.imgRiseup.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://riseuplabs.com"));
            startActivity(browserIntent);
        });

        binding.backButton.setOnClickListener(v->{
            onBackPressed();
        });

    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no, findViewById(R.id.backButton));
        binding.headerLayout.setBackgroundColor(Color.parseColor("#00000000"));
        ObjectAnimator.ofFloat(binding.storyList, "alpha", 1f, 0).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", 0, -500).setDuration(500).start();
        ObjectAnimator.ofFloat(binding.storyList, "translationY", 0, 1000).setDuration(750).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", 0, -200).setDuration(1000).start();
        super.onBackPressed();
    }

    public void initAnimation() {
        isOpen = true;
        ObjectAnimator.ofFloat(binding.storyList, "alpha", 0, 1f).setDuration(1500).start();
        ObjectAnimator.ofFloat(binding.bgColor, "translationY", -500, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.storyList, "translationY", 2000, 0).setDuration(1000).start();
        ObjectAnimator.ofFloat(binding.backButton, "translationX", -200, 0).setDuration(1000).start();

        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.activityName.setText(R.string.v1_settings);
    }

    void setVibration_State(boolean vibration_state) {
        prefManager.putBoolean(PrefKeys.VIBRATION, vibration_state);
    }

    void setSound_State(boolean sound_state) {
        prefManager.putBoolean(PrefKeys.SOUND, sound_state);
    }

    void selectVibrationState(boolean state) {

        binding.btnVibrationOn.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnVibrationOff.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));

        binding.btnVibrationOn.setTextColor(Color.BLACK);
        binding.btnVibrationOff.setTextColor(Color.BLACK);

        if (state) {
            binding.btnVibrationOn.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnVibrationOn.setTextColor(Color.WHITE);
        } else {
            binding.btnVibrationOff.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnVibrationOff.setTextColor(Color.WHITE);
        }
    }

    void selectSoundState(boolean state) {

        binding.btnSoundOn.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnSoundOff.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));

        binding.btnSoundOn.setTextColor(Color.BLACK);
        binding.btnSoundOff.setTextColor(Color.BLACK);

        if (state) {
            binding.btnSoundOn.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnSoundOn.setTextColor(Color.WHITE);
        } else {
            binding.btnSoundOff.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnSoundOff.setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    public void setLanguage(String language, String country, View v) {
        prefManager.putString(PrefKeys.SELECTED_LANGUAGE, language);
        prefManager.putString(PrefKeys.SELECTED_COUNTRY, country);
        StaticMethods.setLanguage(this, language, country);

        selectedLanguageButton(language, country);
        playNotification(prefManager, this, R.raw.setting_button_change, v);

    }

    public void selectedLanguageButton(String language, String country) {
        binding.btnEnglish.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnPortugues.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnSpanish.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));

        binding.btnPortugues.setTextColor(Color.BLACK);
        binding.btnSpanish.setTextColor(Color.BLACK);
        binding.btnEnglish.setTextColor(Color.BLACK);

        switch (language) {
            case "en":
                binding.btnEnglish.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
                binding.btnEnglish.setTextColor(Color.WHITE);
                break;
            case "es":
                binding.btnSpanish.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
                binding.btnSpanish.setTextColor(Color.WHITE);
                break;
            case "pt":
                binding.btnPortugues.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
                binding.btnPortugues.setTextColor(Color.WHITE);
                break;
        }

        reload();
    }

    public void setCountry(int org_id, View v) throws IOException {
        prefManager.putInt(PrefKeys.ORG_ID, org_id);
        selectedCountryButton(org_id);
        playNotification(prefManager, this, R.raw.setting_button_change, v);

    }

    public void selectedCountryButton(int org_id) {

        binding.btnCountryEcuador.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnCountryBolivia.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));
        binding.btnCountryBrazil.setBackground(getResources().getDrawable(R.drawable.v3_dialog_button_grey));

        binding.btnCountryEcuador.setTextColor(Color.BLACK);
        binding.btnCountryBolivia.setTextColor(Color.BLACK);
        binding.btnCountryBrazil.setTextColor(Color.BLACK);

        if (org_id == AppConstant.BRAZIL_ORG_ID) {
            prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BRAZIL_LABEL);
            subscribeCurrentTopic();
            binding.btnCountryBrazil.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnCountryBrazil.setTextColor(Color.WHITE);
        } else if (org_id == AppConstant.ECUADOR_ORG_ID) {
            prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.ECUADOR_LABEL);
            subscribeCurrentTopic();
            binding.btnCountryEcuador.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnCountryEcuador.setTextColor(Color.WHITE);
        } else if (org_id == AppConstant.BOLIVIA_ORG_ID) {
            prefManager.putString(PrefKeys.ORG_LABEL, AppConstant.BOLIVIA_LABEL);
            subscribeCurrentTopic();
            binding.btnCountryBolivia.setBackground(getResources().getDrawable(R.drawable.v1_card_bg_reports));
            binding.btnCountryBolivia.setTextColor(Color.WHITE);
        }

    }

    public void reload() {
        binding.activityName.setText(R.string.v1_settings);
        binding.txtChangeLanguage.setText(R.string.v1_change_language);
        binding.txtCountryLanguage.setText(R.string.select_your_location);
        binding.txtChangeSound.setText(R.string.v1_sound);
        binding.btnSoundOn.setText(R.string.v1_on);
        binding.btnSoundOff.setText(R.string.v1_off);
        binding.txtChangeVibration.setText(R.string.v1_vibration);
        binding.btnVibrationOn.setText(R.string.v1_on);
        binding.btnVibrationOff.setText(R.string.v1_off);
        binding.textLogout.setText(R.string.v1_logout);
        binding.txtDeveloped.setText(R.string.v1_settings_designed_and_developed);

        if (!isLoggedIn()) {
            binding.textLogout.setText(R.string.login);
        }

    }

    public void unsubscribeCurrentTopic(String label){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(label)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "unSubscribed from "+label;
                        if (!task.isSuccessful()) {
                            msg = "Subscribed failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
    }

    public void subscribeCurrentTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic(prefManager.getString(PrefKeys.ORG_LABEL))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to "+prefManager.getString(PrefKeys.ORG_LABEL);
                        if (!task.isSuccessful()) {
                            msg = "Subscribed failed";
                        }
                        Log.d(TAG, msg);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }
}