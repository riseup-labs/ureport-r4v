package com.risuplabs.ureport_r4v.ui.stories;

import androidx.lifecycle.LiveData;
import com.risuplabs.ureport_r4v.model.story.ModelStory;
import com.risuplabs.ureport_r4v.model.story.ModelStories;
import com.risuplabs.ureport_r4v.network.apis.StoriesApi;
import com.risuplabs.ureport_r4v.room.QueryExecutor;
import com.risuplabs.ureport_r4v.room.dao.StoriesDao;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;

import java.util.List;

import javax.inject.Inject;

public class StoryRepository {

    DataManager dataManager;
    StoriesApi storiesApi;
    StoriesDao storiesDao;
    private static final String TAG = "StoryRepository";

    @Inject
    public StoryRepository(DataManager dataManager, StoriesApi storiesApi,StoriesDao storiesDao) {
        this.dataManager = dataManager;
        this.storiesApi = storiesApi;
        this.storiesDao = storiesDao;
    }


    public void get_stories(ResponseListener<ModelStories> responseListener,String url){
        dataManager.performRequest(storiesApi.getStories(url),responseListener);
    }

    public void insertStory(ModelStory story){
        QueryExecutor.databaseWriteExecutor.execute(() -> {
            storiesDao.insertStory(story);
        });
    }

    public LiveData<List<ModelStory>> getAllStoriesFromLocal(int org_id) {
        return storiesDao.getAllStories(org_id);
    }

    public LiveData<Integer> getStoriesCount(int org_id){
        return storiesDao.getStoriesCount(org_id);
    }

}

