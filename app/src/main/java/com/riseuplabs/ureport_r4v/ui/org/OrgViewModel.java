package com.riseuplabs.ureport_r4v.ui.org;

import com.riseuplabs.ureport_r4v.base.BaseViewModel;
import com.riseuplabs.ureport_r4v.surveyor.data.Org;

import java.util.List;

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
