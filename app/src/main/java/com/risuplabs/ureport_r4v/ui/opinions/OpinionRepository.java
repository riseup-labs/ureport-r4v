package com.risuplabs.ureport_r4v.ui.opinions;

import com.risuplabs.ureport_r4v.network.apis.SurveyorApi;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;
import com.risuplabs.ureport_r4v.surveyor.net.responses.FlowResponse;

import javax.inject.Inject;

public class OpinionRepository {

    DataManager dataManager;
    SurveyorApi surveyorApi;

    @Inject
    public OpinionRepository(DataManager dataManager, SurveyorApi surveyorApi) {
        this.dataManager = dataManager;
        this.surveyorApi = surveyorApi;
    }

    public void getFlows(ResponseListener<FlowResponse> responseListener){
        dataManager.performRequest(surveyorApi.getFlows(),responseListener);
    }

}
