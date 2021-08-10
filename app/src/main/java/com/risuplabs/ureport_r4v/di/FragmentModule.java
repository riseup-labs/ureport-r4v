package com.risuplabs.ureport_r4v.di;

import com.risuplabs.ureport_r4v.ui.opinions.flow_list.FlowListFragment;
import com.risuplabs.ureport_r4v.ui.org.OrgListFragment;
import com.risuplabs.ureport_r4v.ui.results.poll_title_list.PollTitlesFragment;
import com.risuplabs.ureport_r4v.ui.results.result_list.ResultListFragment;
import com.risuplabs.ureport_r4v.ui.stories.list.StoriesListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract StoriesListFragment contributesStoriesListFragment();

    @ContributesAndroidInjector
    abstract ResultListFragment contributesResultListFragment();

    @ContributesAndroidInjector
    abstract PollTitlesFragment contributesPollTitlesFragment();

    @ContributesAndroidInjector
    abstract OrgListFragment contributesOrgListFragment();

    @ContributesAndroidInjector
    abstract FlowListFragment contributesFlowListFragment();



}
