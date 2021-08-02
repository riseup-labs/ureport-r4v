package com.risuplabs.ureport.ui.registration;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.base.BaseSubmissionActivity;
import com.risuplabs.ureport.databinding.ActivityRegistrationBinding;
import com.risuplabs.ureport.di.SurveyorApplication;
import com.risuplabs.ureport.surveyor.data.Org;
import com.risuplabs.ureport.surveyor.data.Submission;
import com.risuplabs.ureport.surveyor.net.responses.Flow;
import com.risuplabs.ureport.surveyor.task.DownloadOrgTask;
import com.risuplabs.ureport.utils.StaticMethods;
import com.risuplabs.ureport.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport.utils.surveyor.Logger;
import com.risuplabs.ureport.utils.ui.BlockingProgress;

import java.util.ArrayList;
import java.util.List;

import static com.risuplabs.ureport.utils.StaticMethods.playNotification;

public class RegistrationActivity extends BaseSubmissionActivity<ActivityRegistrationBinding> {

    public DownloadOrgTask donx;
    private Org org;
    String orgUUID = "invalid";
    List<com.risuplabs.ureport.surveyor.net.responses.Flow> registration_flows = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        orgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
        refresh();
        registration_flows.add(new Flow("47d8dccc-35e8-4d91-9f9c-c315c3daeaa3"));
        download(registration_flows);


    }

    @Override
    public boolean requireLogin() {
        return false;
    }

    public void download(List<Flow> flows) {
        final BlockingProgress progressModal = new BlockingProgress(RegistrationActivity.this, R.string.one_moment, R.string.refresh_org);
        progressModal.setOnDismissListener(dialogInterface -> {
            donx.cancel(true);
        });
        progressModal.show();

        donx = new DownloadOrgTask(new DownloadOrgTask.Listener() {
            @Override
            public void onProgress(int percent) {
                progressModal.setProgress(percent);
            }

            @Override
            public void onComplete() {
                try {
                    playNotification(prefManager, getApplicationContext(), R.raw.sync_complete);
                    StaticMethods.setLocalUpdateDate(prefManager, "surveyor_last_updated_local");

                } catch (Exception e) {
                    //
                }
                progressModal.dismiss();
            }

            @Override
            public void onFailure() {
                progressModal.dismiss();
                Toast.makeText(RegistrationActivity.this, getString(R.string.error_org_refresh), Toast.LENGTH_SHORT).show();
            }
        },flows);
        donx.execute(getOrg());
    }

    @Override
    protected List<Submission> getPendingSubmissions() {
        return null;
    }

    @Override
    protected Org getOrg() {
        return org;
    }

    @Override
    protected void refresh() {
        if (orgUUID.equals("invalid")) {
            return;
        } // Not Logged In

        if (org == null) {
            try {
                org = SurveyorApplication.get().getOrgService().get(orgUUID);
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
//                showBugReportDialog();
                finish();
                return;
            }
        }
    }
}