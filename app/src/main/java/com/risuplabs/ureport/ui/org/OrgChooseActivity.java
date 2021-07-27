package com.risuplabs.ureport.ui.org;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.base.BaseSurveyorActivity;
import com.risuplabs.ureport.databinding.ActivityOrgChooseBinding;
import com.risuplabs.ureport.di.SurveyorApplication;
import com.risuplabs.ureport.surveyor.data.Org;
import com.risuplabs.ureport.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport.utils.surveyor.Logger;
import com.risuplabs.ureport.utils.surveyor.SurveyorIntent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import static com.risuplabs.ureport.utils.StaticMethods.playNotification;

public class OrgChooseActivity extends BaseSurveyorActivity<ActivityOrgChooseBinding> implements OrgListFragment.Container{

    @Inject
    OrgViewModel orgViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_org_choose;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        if (!isLoggedIn()) {
            return;
        }

        List<Org> orgs = orgViewModel.getOrgs();

        String saved_OrgUUID = prefManager.getString(SurveyorPreferences.SAVED_UUID);
        if(!saved_OrgUUID.equals("")){
            // ORG_UUID Already Defined: Skip Selection
            for(int i = 0; i < orgs.size(); i++){
                String currentUUID = orgs.get(i).getUuid();
                if(currentUUID.equals(saved_OrgUUID)){
                    showOrg(orgs.get(i));
                    overridePendingTransition(0, 0);
                    finish();
                    return;
                }
            }
        }

        // if we don't have any orgs, take us back to the login screen
        if (orgs == null || orgs.size() == 0) {
            logout(R.string.error_no_orgs);
            overridePendingTransition(0, 0);
            finish();
        }

        // if we have access to a single org, then skip this entire activity
        else if (orgs.size() == 1) {
            Logger.d("One org found, shortcutting chooser to: " + orgs.get(0).getName());
            showOrg(orgs.get(0));
            overridePendingTransition(0, 0);
            finish();
        } else {

            // this holds our org list fragment which shows all available orgs
            setContentView(R.layout.activity_org_choose);

            if (savedInstanceState == null) {
                Fragment fragment = new OrgListFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_container, fragment).commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        playNotification(prefManager, getApplicationContext(), R.raw.button_click_no);
        logout();
        super.onBackPressed();
    }


    private void showOrg(Org org) {
        prefManager.putString(SurveyorPreferences.SAVED_UUID,org.getUuid());
        Intent intent = new Intent(OrgChooseActivity.this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onItemClick(Org org) {
        showOrg(org);
    }
}