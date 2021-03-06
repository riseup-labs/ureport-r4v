package com.riseuplabs.ureport_r4v.di;

import com.riseuplabs.ureport_r4v.ui.about.AboutViewModel;
import com.riseuplabs.ureport_r4v.ui.auth.LoginViewModel;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashboardViewModel;
import com.riseuplabs.ureport_r4v.ui.opinions.OpinionViewModel;
import com.riseuplabs.ureport_r4v.ui.org.OrgViewModel;
import com.riseuplabs.ureport_r4v.ui.results.ResultsViewModel;
import com.riseuplabs.ureport_r4v.ui.splash.SplashViewModel;
import com.riseuplabs.ureport_r4v.ui.stories.StoryViewModel;

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

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel.class)
    abstract AboutViewModel bindAboutViewModel(AboutViewModel aboutViewModel);



}

