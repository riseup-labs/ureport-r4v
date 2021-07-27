package com.risuplabs.ureport.ui.opinions;

import com.risuplabs.ureport.network.apis.SurveyorApi;
import com.risuplabs.ureport.rx.DataManager;
import com.risuplabs.ureport.rx.ResponseListener;
import com.risuplabs.ureport.surveyor.net.responses.Flow;
import com.risuplabs.ureport.surveyor.net.responses.FlowResponse;

import java.util.List;

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
