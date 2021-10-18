package com.risuplabs.ureport_r4v.network.data;

import com.risuplabs.ureport_r4v.di.SurveyorApplication;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;
import com.risuplabs.ureport_r4v.utils.pref_manager.SharedPrefManager;

import javax.inject.Inject;

public class ApiConstants {

    //Base
    public static final String SR_BASE_URL="https://"+ new SharedPrefManager(SurveyorApplication.get()).getString(PrefKeys.ORG_LABEL)+".ureport.in/api/v1/";
    public static final String SURVEYOR_BASE_URL="https://rapidpro.ilhasoft.mobi";
    public static final String PROXY_SURVEYOR_BASE_URL="https://ureport-offline.rultest.com";

    //Stories
    public static final String STORIES="stories/org/";

    //Polls
    public static final String POLLS="polls/org/";


}
