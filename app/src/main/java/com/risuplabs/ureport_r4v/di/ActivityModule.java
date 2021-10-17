package com.risuplabs.ureport_r4v.di;

import com.risuplabs.ureport_r4v.Splash;
import com.risuplabs.ureport_r4v.ui.auth.LoginActivity;
import com.risuplabs.ureport_r4v.ui.auth.LoginChooserActivity;
import com.risuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport_r4v.ui.opinions.flow.RunFlowActivity;
import com.risuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport_r4v.ui.opinions.media_capture.CaptureAudioActivity;
import com.risuplabs.ureport_r4v.ui.opinions.media_capture.CaptureLocationActivity;
import com.risuplabs.ureport_r4v.ui.opinions.media_capture.CaptureVideoActivity;
import com.risuplabs.ureport_r4v.ui.org.OrgChooseActivity;
import com.risuplabs.ureport_r4v.ui.registration.RegistrationActivity;
import com.risuplabs.ureport_r4v.ui.results.poll_title_list.PollTitlesActivity;
import com.risuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.risuplabs.ureport_r4v.ui.results.result_list.ResultListActivity;
import com.risuplabs.ureport_r4v.ui.results.search.ResultsSearchActivity;
import com.risuplabs.ureport_r4v.ui.settings.SettingsActivity;
import com.risuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.risuplabs.ureport_r4v.ui.stories.list.StoriesListActivity;
import com.risuplabs.ureport_r4v.ui.stories.search.StorySearchActivity;

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
    abstract LoginChooserActivity contributesLoginChooserActivity();

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

    @ContributesAndroidInjector
    abstract RegistrationActivity contributesRegistrationActivity();

    @ContributesAndroidInjector
    abstract StorySearchActivity contributesStorySearchActivity();

    @ContributesAndroidInjector
    abstract ResultsSearchActivity contributesResultsSearchActivity();





}
