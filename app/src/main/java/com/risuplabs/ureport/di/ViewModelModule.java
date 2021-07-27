package com.risuplabs.ureport.di;

import com.risuplabs.ureport.ui.auth.LoginViewModel;
import com.risuplabs.ureport.ui.dashboard.DashboardViewModel;
import com.risuplabs.ureport.ui.opinions.OpinionViewModel;
import com.risuplabs.ureport.ui.org.OrgViewModel;
import com.risuplabs.ureport.ui.results.ResultsViewModel;
import com.risuplabs.ureport.ui.splash.SplashViewModel;
import com.risuplabs.ureport.ui.stories.StoryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel.class)
    abstract DashboardViewModel bindMainActivityVM(DashboardViewModel dashboardViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract SplashViewModel bindSplashVM(SplashViewModel splashViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel.class)
    abstract StoryViewModel bindStoryVM(StoryViewModel storyViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ResultsViewModel.class)
    abstract ResultsViewModel bindResultVM(ResultsViewModel resultsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract LoginViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OrgViewModel.class)
    abstract OrgViewModel bindOrgViewModel(OrgViewModel orgViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OpinionViewModel.class)
    abstract OpinionViewModel bindOpinionViewModel(OpinionViewModel opinionViewModel);



}

