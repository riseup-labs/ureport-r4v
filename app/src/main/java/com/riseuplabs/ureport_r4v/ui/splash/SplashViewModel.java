package com.riseuplabs.ureport_r4v.ui.splash;

import com.riseuplabs.ureport_r4v.base.BaseViewModel;

import javax.inject.Inject;

public class SplashViewModel extends BaseViewModel {

    final SplashRepository splashRepository;

    @Inject
    public SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }


}
