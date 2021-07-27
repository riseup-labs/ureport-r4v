package com.risuplabs.ureport.ui.results;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport.base.BaseViewModel;
import com.risuplabs.ureport.model.results.ModelPollCategory;
import com.risuplabs.ureport.model.results.ModelPolls;
import com.risuplabs.ureport.model.results.ModelResults;
import com.risuplabs.ureport.network.utils.ApiResponse;
import com.risuplabs.ureport.rx.ResponseListener;

import java.util.List;

import javax.inject.Inject;

public class ResultsViewModel extends BaseViewModel {

    ResultRepository resultRepository;
    public MutableLiveData<ApiResponse<ModelResults>> response = new MutableLiveData<>();

    @Inject
    public ResultsViewModel(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public void getResults(String url){
        resultRepository.get_resultsFromRemote(new ResponseListener<ModelResults>() {
            @Override
            public void onStart() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onFinish() {
                loadingStatus.setValue(false);
            }

            @Override
            public void onResponse(ApiResponse<ModelResults> apiResponse) {

                if(apiResponse != null && apiResponse.data != null ){
                    response.setValue(apiResponse);
                }
                loadingStatus.setValue(false);
                assert apiResponse != null;
                Log.d("ERROR_CODE", "onResponse: "+ apiResponse.statusCode);

            }
        },url);
    }

    public void insertPolls(ModelPolls polls){
        resultRepository.insertPolls(polls);
    }

    public LiveData<List<ModelPollCategory>> getAllCategoriesFromLocal(int org_id){
        return resultRepository.getAllPollsCategoryFromLocal(org_id);
    }

    public LiveData<Integer> getAllCategoriesCountFromLocal(int org_id){
        return resultRepository.getAllPollsCategoryCountFromLocal(org_id);
    }

    public LiveData<List<ModelPolls>> getPollTitlesFromLocal(String tag,int org_id){
        return resultRepository.getPollTitlesFromLocal(tag,org_id);
    }

    public LiveData<List<ModelPolls>> getQuestionsFromLocal(int id,int org_id){
        return resultRepository.getQuestionsFromLocal(id,org_id);
    }

}
