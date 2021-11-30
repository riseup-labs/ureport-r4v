package com.riseuplabs.ureport_r4v.di;

import com.riseuplabs.ureport_r4v.Splash;
import com.riseuplabs.ureport_r4v.ui.about.AboutActivity;
import com.riseuplabs.ureport_r4v.ui.auth.LoginActivity;
import com.riseuplabs.ureport_r4v.ui.auth.LoginChooserActivity;
import com.riseuplabs.ureport_r4v.ui.auth.ProgramChooserActivity;
import com.riseuplabs.ureport_r4v.ui.dashboard.DashBoardActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.flow.RunFlowActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.flow_list.FlowListActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureAudioActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureLocationActivity;
import com.riseuplabs.ureport_r4v.ui.opinions.media_capture.CaptureVideoActivity;
import com.riseuplabs.ureport_r4v.ui.org.OrgChooseActivity;
import com.riseuplabs.ureport_r4v.ui.registration.RegistrationActivity;
import com.riseuplabs.ureport_r4v.ui.results.poll_title_list.PollTitlesActivity;
import com.riseuplabs.ureport_r4v.ui.results.polls.PollsActivity;
import com.riseuplabs.ureport_r4v.ui.results.result_list.ResultListActivity;
import com.riseuplabs.ureport_r4v.ui.results.search.ResultsSearchActivity;
import com.riseuplabs.ureport_r4v.ui.settings.SettingsActivity;
import com.riseuplabs.ureport_r4v.ui.stories.details.StoryDetailsActivity;
import com.riseuplabs.ureport_r4v.ui.stories.list.StoriesListActivity;
import com.riseuplabs.ureport_r4v.ui.stories.search.StorySearchActivity;

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

    @ContributesAndroidInjector
    abstract ProgramChooserActivity contributesProgramChooserActivity();

    @ContributesAndroidInjector
    abstract AboutActivity contributesAboutActivity();



}
