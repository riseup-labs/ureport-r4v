package com.risuplabs.ureport_r4v.ui.opinions;

import androidx.lifecycle.LiveData;

import com.risuplabs.ureport_r4v.model.surveyor.ModelSurvey;
import com.risuplabs.ureport_r4v.network.apis.SurveyorApi;
import com.risuplabs.ureport_r4v.room.QueryExecutor;
import com.risuplabs.ureport_r4v.room.dao.SurveyDao;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;
import com.risuplabs.ureport_r4v.surveyor.net.responses.FlowResponse;

import java.util.List;

import javax.inject.Inject;

public class OpinionRepository {

    DataManager dataManager;
    SurveyorApi surveyorApi;
    SurveyDao surveyDao;

    @Inject
    public OpinionRepository(DataManager dataManager, SurveyorApi surveyorApi,SurveyDao surveyDao) {
        this.dataManager = dataManager;
        this.surveyorApi = surveyorApi;
        this.surveyDao = surveyDao;
    }

    public void insertSurvey(ModelSurvey survey){
        QueryExecutor.databaseWriteExecutor.execute(() -> {
            surveyDao.insertSurvey(survey);
        });
    }

    public LiveData<List<ModelSurvey>> getAllSurveysFromLocal(int org_id) {
        return surveyDao.getAllSurvey(org_id);
    }

    public LiveData<Integer> getSurveyCount(int org_id){
        return surveyDao.getSurveyCount(org_id);
    }

}
