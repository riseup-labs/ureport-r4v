package com.risuplabs.ureport_r4v.ui.auth;

import com.risuplabs.ureport_r4v.network.apis.SurveyorApi;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;
import com.risuplabs.ureport_r4v.surveyor.net.responses.TokenResults;

import javax.inject.Inject;

public class LoginRepository {

    DataManager dataManager;
    SurveyorApi surveyorApi;

    @Inject
    public LoginRepository(DataManager dataManager, SurveyorApi surveyorApi) {
        this.dataManager = dataManager;
        this.surveyorApi = surveyorApi;
    }

    public void login(ResponseListener<TokenResults> responseListener, String username, String password, String role){
        dataManager.performRequest(surveyorApi.authenticate(username,password,role),responseListener);
    }


}
