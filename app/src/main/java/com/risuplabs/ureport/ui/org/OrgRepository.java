package com.risuplabs.ureport.ui.org;

import com.risuplabs.ureport.di.SurveyorApplication;
import com.risuplabs.ureport.surveyor.data.Org;
import com.risuplabs.ureport.utils.pref_manager.SharedPrefManager;
import com.risuplabs.ureport.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport.utils.surveyor.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class OrgRepository {


    SharedPrefManager prefManager;

    @Inject
    public OrgRepository(SharedPrefManager prefManager) {
        this.prefManager = prefManager;
    }

    public List<Org> getOrgs() {
        Set<String> orgUUIDs = prefManager.getStringSet(SurveyorPreferences.AUTH_ORGS, Collections.<String>emptySet());
        List<Org> orgs = new ArrayList<>(orgUUIDs.size());

        for (String uuid : orgUUIDs) {
            try {
                orgs.add(SurveyorApplication.get().getOrgService().get(uuid));
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
            }
        }
        return orgs;
    }

}
