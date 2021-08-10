package com.risuplabs.ureport_r4v.ui.auth;

import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.greysonparrelli.permiso.Permiso;
import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityLoginChooserBinding;
import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.network.data.ApiConstants;
import com.risuplabs.ureport_r4v.surveyor.net.responses.Token;
import com.risuplabs.ureport_r4v.surveyor.task.FetchOrgsTask;
import com.risuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport_r4v.utils.AppConstant;
import com.risuplabs.ureport_r4v.utils.Navigator;
import com.risuplabs.ureport_r4v.utils.custom_dialog.CustomDialog;
import com.risuplabs.ureport_r4v.utils.custom_dialog.CustomDialogInterface;
import com.risuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.risuplabs.ureport_r4v.utils.ConnectivityCheck.isConnected;
import static com.risuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class LoginChooserActivity extends BaseSurveyorActivity<ActivityLoginChooserBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_chooser;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (!resultSet.areAllPermissionsGranted()) {
                    finish();
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                LoginChooserActivity.this.showRationaleDialog(R.string.permission_storage, callback);
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        binding.shelterLogin.setOnClickListener(v -> {
            Navigator.navigate(this,LoginActivity.class);
            finish();
        });

        binding.register.setOnClickListener(v -> {
            attempt_registration();
        });

        binding.skipLogin.setOnClickListener(v -> {
            playNotification(prefManager, getApplicationContext(), R.raw.button_click_yes, v);
            overridePendingTransition(0,0);
            Intent intent = new Intent(LoginChooserActivity.this, DashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(0,0);
        });

    }

    private void attempt_registration(){
        if (!isConnected(this)) {
            new CustomDialog(this).displayNoInternetDialog(new CustomDialogInterface() {
                @Override
                public void retry() {
                    attempt_registration();
                }
                @Override
                public void cancel() {
                }
            });
        }else{
            got_to_reg_flow();
        }
    }

    private void got_to_reg_flow(){
        binding.pbRegister.setVisibility(View.VISIBLE);
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(AppConstant.TOKEN,new Token.OrgReference(AppConstant.ORG_UUID,"Offline Login")));
        SurveyorApplication.get().setPreference(SurveyorPreferences.HOST, ApiConstants.PROXY_SURVEYOR_BASE_URL);
        SurveyorApplication.get().onTembaHostChanged();
        fetchOrgsAndLogin("", tokens,AppConstant.GUEST);
    }

    protected void fetchOrgsAndLogin(final String email, final List<Token> tokens, String from) {
        new FetchOrgsTask(new FetchOrgsTask.Listener() {
            @Override
            public void onComplete(Set<String> orgUUIDs) {
                login(email, orgUUIDs,from);
                binding.pbRegister.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onFailure() {

                Toast.makeText(LoginChooserActivity.this, getString(R.string.error_fetching_org), Toast.LENGTH_SHORT).show();
            }
        }).execute(tokens.toArray(new Token[0]));
    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}