package com.risuplabs.ureport.ui.auth;

import com.risuplabs.ureport.network.apis.SurveyorApi;
import com.risuplabs.ureport.rx.DataManager;
import com.risuplabs.ureport.rx.ResponseListener;
import com.risuplabs.ureport.surveyor.net.responses.TokenResults;

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
