package com.risuplabs.ureport.ui.splash;

import com.risuplabs.ureport.rx.DataManager;

import javax.inject.Inject;

public class SplashRepository {

    final DataManager dataManager;

    @Inject
    public SplashRepository(DataManager dataManager) {
        this.dataManager = dataManager;

    }


}
