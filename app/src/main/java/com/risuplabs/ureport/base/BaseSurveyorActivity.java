package com.risuplabs.ureport.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;
import com.risuplabs.ureport.R;
import com.risuplabs.ureport.di.SurveyorApplication;
import com.risuplabs.ureport.ui.auth.LoginActivity;
import com.risuplabs.ureport.ui.org.OrgChooseActivity;
import com.risuplabs.ureport.ui.registration.RegistrationActivity;
import com.risuplabs.ureport.utils.AppConstant;
import com.risuplabs.ureport.utils.IntentConstant;
import com.risuplabs.ureport.utils.ui.ViewCache;
import com.risuplabs.ureport.utils.pref_manager.SharedPrefManager;
import com.risuplabs.ureport.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport.utils.surveyor.Logger;
import com.risuplabs.ureport.utils.surveyor.SurveyorIntent;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.risuplabs.ureport.utils.StaticMethods.playNotification;

public abstract class BaseSurveyorActivity<T extends ViewDataBinding> extends PermisoActivity {

    public final String TAG = this.getClass().getSimpleName();

    public T binding;
    private ViewCache m_viewCache;


    public abstract @LayoutRes
    int getLayoutId();

    @Inject
    public SharedPrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        performDependencyInjection();
        performDatabinding();
        onViewReady(savedInstanceState);

        // if we're on an activity that requires a logged in user, and we aren't, redirect to login activity
        if (requireLogin() && !isLoggedIn()) {
            logout();
        }


    }

    private void performDatabinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    protected boolean isLoggedIn() {
        return !TextUtils.isEmpty(getUsername());
    }

    protected String getUsername() {
        return prefManager.getString(SurveyorPreferences.AUTH_USERNAME, null);
    }

    public SurveyorApplication getSurveyor() {
        return (SurveyorApplication) getApplication();
    }

    protected void showToast(String resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public boolean requireLogin() {
        return true;
    }

    protected void logout() {
        logout(-1);
    }

    protected void logout(int errorResId) {
        Logger.d("Logging out with error " + errorResId);

        prefManager.clearPreference(SurveyorPreferences.AUTH_USERNAME);
        prefManager.clearPreference(SurveyorPreferences.PREV_USERNAME);
        prefManager.clearPreference(SurveyorPreferences.SAVED_UUID);
        prefManager.setPreference(SurveyorPreferences.AUTH_ORGS, Collections.<String>emptySet());

        try {
            getSurveyor().getSubmissionService().clearAll();
        } catch (IOException e) {
            Logger.e("Unable to clear submissions", e);
        }

        Intent intent = new Intent(this, LoginActivity.class);

        if (errorResId != -1) {
            intent.putExtra(SurveyorIntent.EXTRA_ERROR, getString(errorResId));
        }
        startActivity(intent);
    }

    public void showRationaleDialog(int body, Permiso.IOnRationaleProvided callback) {
        Permiso.getInstance().showRationaleInDialog(getString(R.string.title_permissions), getString(body), null, callback);
    }

    public void showBugReportDialog() {
        showConfirmDialog(R.string.confirm_bug_report, new ConfirmationListener() {
            @Override
            public void onConfirm() {
                sendBugReport();
            }
        });
    }

    private void sendBugReport() {
        try {
            Uri outputUri = getSurveyor().generateLogDump();

            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo(getString(R.string.support_email))
                    .setSubject("Surveyor Bug Report")
                    .setText("Please include what you were doing prior to sending this report and specific details on the error you encountered.")
                    .setStream(outputUri)
                    .setChooserTitle("Send Email")
                    .startChooser();

        } catch (IOException e) {
            Logger.e("Failed to generate bug report", e);
        }
    }

    protected AlertDialog showConfirmDialog(int msgResId, final ConfirmationListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder.setMessage(msgResId)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int id) {
                        listener.onConfirm();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int id) {
                        dialog1.cancel();
                    }
                }).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void login(String email, Set<String> orgUUIDs, String from) {
        Logger.d("Logging in as " + email + " with access to orgs " + TextUtils.join(",", orgUUIDs));

        // save email which we'll need for submissions later
        prefManager.putString(SurveyorPreferences.AUTH_USERNAME, email);
        prefManager.putString(SurveyorPreferences.PREV_USERNAME, email);

        prefManager.setPreference(SurveyorPreferences.AUTH_ORGS, orgUUIDs);

        // let the user pick an org...
        Intent intent = new Intent(this, OrgChooseActivity.class);
        intent.putExtra(IntentConstant.COMING_FROM, from);
        startActivity(intent);

        // play sound

        playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);

        // we don't want to go back to the view that sent us here (i.e. login or create account)
        finish();
    }


    public abstract void onViewReady(@Nullable Bundle savedInstanceState);

    protected interface ConfirmationListener {
        void onConfirm();
    }

    public ViewCache getViewCache() {
        if (m_viewCache == null) {
            m_viewCache = new ViewCache(this, findViewById(android.R.id.content));
        }
        return m_viewCache;
    }

}
