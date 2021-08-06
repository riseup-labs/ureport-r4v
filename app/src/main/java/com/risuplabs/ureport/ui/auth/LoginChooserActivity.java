package com.risuplabs.ureport.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.greysonparrelli.permiso.Permiso;
import com.risuplabs.ureport.R;
import com.risuplabs.ureport.base.BaseActivity;
import com.risuplabs.ureport.base.BaseSurveyorActivity;
import com.risuplabs.ureport.databinding.ActivityLoginChooserBinding;
import com.risuplabs.ureport.surveyor.net.responses.Token;
import com.risuplabs.ureport.surveyor.task.FetchOrgsTask;
import com.risuplabs.ureport.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport.utils.AppConstant;
import com.risuplabs.ureport.utils.Navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.risuplabs.ureport.utils.StaticMethods.playNotification;

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
        });

        binding.register.setOnClickListener(v -> {
            List<Token> tokens = new ArrayList<>();
            tokens.add(new Token(AppConstant.TOKEN,new Token.OrgReference(AppConstant.ORG_UUID,"Offline Login")));
            fetchOrgsAndLogin("guest", tokens,AppConstant.GUEST);
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

    protected void fetchOrgsAndLogin(final String email, final List<Token> tokens, String from) {
        new FetchOrgsTask(new FetchOrgsTask.Listener() {
            @Override
            public void onComplete(Set<String> orgUUIDs) {
                login(email, orgUUIDs,from);
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
}