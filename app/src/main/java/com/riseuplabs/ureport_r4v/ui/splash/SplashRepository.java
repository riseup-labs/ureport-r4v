package com.riseuplabs.ureport_r4v.ui.splash;

import com.riseuplabs.ureport_r4v.rx.DataManager;

import javax.inject.Inject;

public class SplashRepository {

    final DataManager dataManager;

    @Inject
    public SplashRepository(DataManager dataManager) {
        this.dataManager = dataManager;

    }


}
