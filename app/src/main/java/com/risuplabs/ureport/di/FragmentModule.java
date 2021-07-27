package com.risuplabs.ureport.di;

import com.risuplabs.ureport.ui.opinions.flow_list.FlowListFragment;
import com.risuplabs.ureport.ui.org.OrgListFragment;
import com.risuplabs.ureport.ui.results.poll_title_list.PollTitlesFragment;
import com.risuplabs.ureport.ui.results.result_list.ResultListFragment;
import com.risuplabs.ureport.ui.stories.list.StoriesListFragment;

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
