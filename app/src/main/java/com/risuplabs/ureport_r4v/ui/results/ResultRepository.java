package com.risuplabs.ureport_r4v.ui.results;

import androidx.lifecycle.LiveData;

import com.risuplabs.ureport_r4v.model.results.ModelPollCategory;
import com.risuplabs.ureport_r4v.model.results.ModelPolls;
import com.risuplabs.ureport_r4v.model.results.ModelResults;
import com.risuplabs.ureport_r4v.network.apis.ResultsApi;
import com.risuplabs.ureport_r4v.room.QueryExecutor;
import com.risuplabs.ureport_r4v.room.dao.ResultsDao;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;

import java.util.List;

import javax.inject.Inject;

public class ResultRepository {
    DataManager dataManager;
    ResultsApi resultsApi;
    ResultsDao resultsDao;

    @Inject
    public ResultRepository(DataManager dataManager, ResultsApi resultsApi,ResultsDao resultsDao) {
        this.dataManager = dataManager;
        this.resultsApi = resultsApi;
        this.resultsDao = resultsDao;
    }

    public void get_resultsFromRemote(ResponseListener<ModelResults> responseListener, String url){
        dataManager.performRequest(resultsApi.getResults(url),responseListener);
    }

    public void insertPolls(ModelPolls polls){
        QueryExecutor.databaseWriteExecutor.execute(() -> {
            resultsDao.insertPolls(polls);
        });
    }

    public LiveData<List<ModelPollCategory>> getAllPollsCategoryFromLocal(int org_id) {
        return resultsDao.getAllPollCategories(org_id);
    }

    public LiveData<Integer> getAllPollsCategoryCountFromLocal(int org_id) {
        return resultsDao.getAllPollCategoriesCount(org_id);
    }

    public LiveData<List<ModelPolls>> getPollTitlesFromLocal(String tag,int org_id){
        return resultsDao.getTitles(tag,org_id);
    }

    public LiveData<List<ModelPolls>> getQuestionsFromLocal(int id,int org_id){
        return resultsDao.getQuestions(id,org_id);
    }
}
