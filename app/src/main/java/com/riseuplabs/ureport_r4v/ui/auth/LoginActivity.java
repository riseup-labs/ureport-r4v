package com.riseuplabs.ureport_r4v.ui.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.riseuplabs.ureport_r4v.R;
import com.riseuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.riseuplabs.ureport_r4v.databinding.ActivityLoginBinding;
import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.network.data.ApiConstants;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.Token;
import com.riseuplabs.ureport_r4v.surveyor.task.FetchOrgsTask;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.utils.AppConstant;
import com.riseuplabs.ureport_r4v.utils.StaticMethods;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialog;
import com.riseuplabs.ureport_r4v.utils.custom_dialog.CustomDialogInterface;
import com.riseuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import static com.riseuplabs.ureport_r4v.utils.ConnectivityCheck.isConnected;
import static com.riseuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class LoginActivity extends BaseSurveyorActivity<ActivityLoginBinding> {

    boolean fromDashboard = false;
    boolean clickLock = false;

    @Inject
    LoginViewModel viewModel;

    @Override
    protected void onStart() {
        super.onStart();
        StaticMethods.setLanguage(this,prefManager.getString(PrefKeys.SELECTED_LANGUAGE,"es"),prefManager.getString(PrefKeys.SELECTED_COUNTRY,"rBO"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        binding.backButton.setOnClickListener(v->{
            onBackPressed();
        });

        try {
            fromDashboard = this.getIntent().getExtras().getBoolean("fromDashboard", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

        Log.d(TAG, "onViewReady: " + prefManager.getString(SurveyorPreferences.PREV_USERNAME));
        binding.email.setText(prefManager.getString(SurveyorPreferences.PREV_USERNAME));
        binding.emailSignInButton.setOnClickListener(v -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes);
            hideKeyboard(this);
            attemptLogin();
        });

        if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BRAZIL_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_brasil);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.ECUADOR_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_ecuador);
        }else if(prefManager.getString(PrefKeys.ORG_LABEL).equals(AppConstant.BOLIVIA_LABEL)){
            binding.programLogo.setImageResource(R.drawable.v2_bolivia_logo);
        }

//        binding.textView3.setOnClickListener(v->{
//            binding.email.setText("robiul@riseuplabs.com");
//            binding.password.setText("robiul@riseuplabs");
//        });

    }

    public void attemptLogin() {

        // Show No Internet Popup
        if (!isConnected(this)) {
            new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {
                @Override
                public void retry() {
                    attemptLogin();
                }

                @Override
                public void cancel() {
                }
            });
            return;
        }

        // Reset errors.
        binding.email.setError(null);
        binding.password.setError(null);

        // Store values at the time of the login attempt.
        final String email = binding.email.getText().toString();
        final String password = binding.password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            binding.password.setError(getString(R.string.error_invalid_password));
            focusView = binding.password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            binding.email.setError(getString(R.string.error_field_required));
            focusView = binding.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            binding.email.setError(getString(R.string.error_invalid_email));
            focusView = binding.email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            viewModel.login(email, password, "S");
            viewModel.tokenResults.observe(this, response -> {
                if (response.statusCode != null) {
                    switch (response.statusCode) {
                        case "200":{
                            SurveyorApplication.get().setPreference(SurveyorPreferences.HOST, ApiConstants.SURVEYOR_BASE_URL);
                            SurveyorApplication.get().onTembaHostChanged();
                            List<Token> tokens = response.data.getTokens();
                            fetchOrgsAndLogin(email, tokens, AppConstant.USER);
                            break;
                        }
                        case "403":
                            setErrorMessage(getString(R.string.error_invalid_login));
                            break;
                        case "404":
                            setErrorMessage(getString(R.string.error_server_not_found));
                            break;
                        case "500":
                            setErrorMessage(getString(R.string.error_server_failure));
                            break;
                        default:
                            setErrorMessage(getString(R.string.error_network));
                    }
                }
            });

        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    protected void fetchOrgsAndLogin(final String email, final List<Token> tokens, String from) {
        new FetchOrgsTask(new FetchOrgsTask.Listener() {
            @Override
            public void onComplete(Set<String> orgUUIDs) {
                showProgress(false);
                login(email, orgUUIDs,from);
            }

            @Override
            public void onFailure() {
                setErrorMessage(getString(R.string.error_fetching_org));
                showProgress(false);
            }
        },"poll").execute(tokens.toArray(new Token[0]));
    }

    private void setErrorMessage(String message) {
        TextView errorBox = findViewById(R.id.text_error_message);
        if (message != null) {
            showProgress(false);
            errorBox.setVisibility(View.VISIBLE);
            errorBox.setText(message);
        } else {
            showProgress(false);
            errorBox.setVisibility(View.GONE);
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.loginForm.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public boolean requireLogin() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fromDashboard) {
            findViewById(R.id.btnSkipLogin).performClick();
            finish();
            return;
        }
        finish();
        super.onBackPressed();
    }

    public void skipLlogin(View view){
        if(clickLock){
            return;
        }else{
            clickLock = true;
            // Unlock after 2 s
            new Handler().postDelayed(() -> clickLock = false, 1000);
        }

        playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, view);
        overridePendingTransition(0,0);

        Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}