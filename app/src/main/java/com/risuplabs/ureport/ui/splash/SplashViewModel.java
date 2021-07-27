package com.risuplabs.ureport.ui.splash;

import com.risuplabs.ureport.base.BaseViewModel;

import javax.inject.Inject;

public class SplashViewModel extends BaseViewModel {

    final SplashRepository splashRepository;

    @Inject
    public SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }


}
