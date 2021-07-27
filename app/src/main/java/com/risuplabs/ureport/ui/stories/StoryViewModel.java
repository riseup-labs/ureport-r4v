package com.risuplabs.ureport.ui.stories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport.base.BaseViewModel;
import com.risuplabs.ureport.model.story.ModelStory;
import com.risuplabs.ureport.model.story.ModelStories;
import com.risuplabs.ureport.network.utils.ApiResponse;
import com.risuplabs.ureport.rx.ResponseListener;

import java.util.List;

import javax.inject.Inject;

public class StoryViewModel extends BaseViewModel {

    StoryRepository storyRepository;
    public MutableLiveData<ApiResponse<ModelStories>> response=new MutableLiveData<>();

    @Inject
    public StoryViewModel(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public void getStories(String url) {
        storyRepository.get_stories(new ResponseListener<ModelStories>() {
            @Override
            public void onStart() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onFinish() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onResponse(ApiResponse<ModelStories> apiResponse) {

                if (apiResponse!=null && apiResponse.data!=null){
                    response.setValue(apiResponse);
                }
                loadingStatus.setValue(true);
                assert apiResponse != null;
                Log.d("ERROR_CODE", "onResponse: "+ apiResponse.statusCode);

            }
        },url);
    }

    public void insertStory(ModelStory story){
        storyRepository.insertStory(story);
    }

    public LiveData<List<ModelStory>> getAllStoriesFromLocal(int org_id){
        return storyRepository.getAllStoriesFromLocal(org_id);
    }

    public LiveData<Integer> getStoriesCount(int org_id){
        return storyRepository.getStoriesCount(org_id);
    }

}
