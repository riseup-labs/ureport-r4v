package com.risuplabs.ureport_r4v.ui.org;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.base.BaseSurveyorActivity;
import com.risuplabs.ureport_r4v.databinding.ActivityOrgChooseBinding;
import com.risuplabs.ureport_r4v.surveyor.data.Org;
import com.risuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport_r4v.ui.registration.RegistrationActivity;
import com.risuplabs.ureport_r4v.utils.AppConstant;
import com.risuplabs.ureport_r4v.utils.IntentConstant;
import com.risuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport_r4v.utils.surveyor.Logger;

import java.util.List;

import javax.inject.Inject;

import static com.risuplabs.ureport_r4v.utils.StaticMethods.playNotification;

public class OrgChooseActivity extends BaseSurveyorActivity<ActivityOrgChooseBinding> implements OrgListFragment.Container{


    String from;

    @Inject
    OrgViewModel orgViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_org_choose;
    }

    @Override
    public void onViewReady(@Nullable Bundle savedInstanceState) {

        from = getIntent().getStringExtra(IntentConstant.COMING_FROM);

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
        Log.d(TAG, "showOrg: "+org.getUuid());

        if(from.equals(AppConstant.GUEST)){
            startActivity(new Intent(this, RegistrationActivity.class));
        }else if (from.equals(AppConstant.USER)){
            Intent intent = new Intent(OrgChooseActivity.this, FlowListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onItemClick(Org org) {
        showOrg(org);
    }

    @Override
    public boolean requireLogin() {
        return false;
    }
}