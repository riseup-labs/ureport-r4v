package com.risuplabs.ureport.ui.org;

import com.risuplabs.ureport.base.BaseViewModel;
import com.risuplabs.ureport.di.SurveyorApplication;
import com.risuplabs.ureport.surveyor.data.Org;
import com.risuplabs.ureport.utils.pref_manager.SurveyorPreferences;
import com.risuplabs.ureport.utils.surveyor.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class OrgViewModel extends BaseViewModel {

    OrgRepository orgRepository;

    @Inject
    public OrgViewModel(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    public List<Org> getOrgs() {
        return orgRepository.getOrgs();
    }
}
