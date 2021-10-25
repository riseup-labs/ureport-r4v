package com.riseuplabs.ureport_r4v.ui.opinions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.riseuplabs.ureport_r4v.base.BaseViewModel;
import com.riseuplabs.ureport_r4v.model.surveyor.ModelSurvey;
import com.riseuplabs.ureport_r4v.network.utils.ApiResponse;
import com.riseuplabs.ureport_r4v.surveyor.net.responses.FlowResponse;

import java.util.List;

import javax.inject.Inject;

public class OpinionViewModel extends BaseViewModel {

    OpinionRepository opinionRepository;
    public MutableLiveData<ApiResponse<FlowResponse>> flowsFromRemote = new MutableLiveData<>();

    @Inject
    public OpinionViewModel(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    public void insertStory(ModelSurvey survey){
        opinionRepository.insertSurvey(survey);
    }

    public LiveData<List<ModelSurvey>> getAllSurveysFromLocal(int org_id){
        return opinionRepository.getAllSurveysFromLocal(org_id);
    }

    public LiveData<Integer> getSurveyCount(int org_id){
        return opinionRepository.getSurveyCount(org_id);
    }
    
}
