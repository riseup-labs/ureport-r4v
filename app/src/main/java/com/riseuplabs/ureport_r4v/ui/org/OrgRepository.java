package com.riseuplabs.ureport_r4v.ui.org;

import com.riseuplabs.ureport_r4v.di.SurveyorApplication;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;
import com.riseuplabs.ureport_r4v.utils.pref_manager.SurveyorPreferences;
import com.riseuplabs.ureport_r4v.utils.surveyor.Logger;

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
        Set<String> orgUUIDs = prefManager.getStringSet(SurveyorPreferences.AUTH_ORGS, Collections.emptySet());
        List<Org> orgs = new ArrayList<>(orgUUIDs.size());

        for (String uuid : orgUUIDs) {
            try {
                orgs.add(SurveyorApplication.get().getOrgService().get(uuid,"poll"));
            } catch (Exception e) {
                Logger.e("Unable to load org", e);
            }
        }
        return orgs;
    }

}
