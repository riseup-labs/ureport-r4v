package com.risuplabs.ureport.di;

import com.risuplabs.ureport.Splash;
import com.risuplabs.ureport.ui.auth.LoginActivity;
import com.risuplabs.ureport.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport.ui.opinions.flow.RunFlowActivity;
import com.risuplabs.ureport.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport.ui.opinions.media_capture.CaptureAudioActivity;
import com.risuplabs.ureport.ui.opinions.media_capture.CaptureLocationActivity;
import com.risuplabs.ureport.ui.opinions.media_capture.CaptureVideoActivity;
import com.risuplabs.ureport.ui.org.OrgChooseActivity;
import com.risuplabs.ureport.ui.results.poll_title_list.PollTitlesActivity;
import com.risuplabs.ureport.ui.results.polls.PollsActivity;
import com.risuplabs.ureport.ui.results.result_list.ResultListActivity;
import com.risuplabs.ureport.ui.settings.SettingsActivity;
import com.risuplabs.ureport.ui.stories.details.StoryDetailsActivity;
import com.risuplabs.ureport.ui.stories.list.StoriesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract DashBoardActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract Splash contributesSplash();

    @ContributesAndroidInjector
    abstract StoriesListActivity contributesStoriesList();

    @ContributesAndroidInjector
    abstract StoryDetailsActivity contributesStoryDetailsActivity();

    @ContributesAndroidInjector
    abstract ResultListActivity contributesResultListActivity();

    @ContributesAndroidInjector
    abstract PollTitlesActivity contributesPollTitlesActivity();

    @ContributesAndroidInjector
    abstract PollsActivity contributesPollsActivity();

    @ContributesAndroidInjector
    abstract SettingsActivity contributesSettingsActivity();

    @ContributesAndroidInjector
    abstract FlowListActivity contributesFlowListActivity();

    @ContributesAndroidInjector
    abstract LoginActivity contributesLoginActivity();

    @ContributesAndroidInjector
    abstract OrgChooseActivity contributesOrgChooseActivity();

    @ContributesAndroidInjector
    abstract RunFlowActivity contributesRunFlowActivity();

    @ContributesAndroidInjector
    abstract CaptureVideoActivity contributesCaptureVideoActivity();

    @ContributesAndroidInjector
    abstract CaptureAudioActivity contributesCaptureAudioActivity();

    @ContributesAndroidInjector
    abstract CaptureLocationActivity contributesCaptureLocationActivity();





}
