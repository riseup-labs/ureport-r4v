package com.risuplabs.ureport_r4v.ui.about;

import com.risuplabs.ureport_r4v.model.about.ModelAbout;
import com.risuplabs.ureport_r4v.model.story.ModelStories;
import com.risuplabs.ureport_r4v.network.apis.AboutApi;
import com.risuplabs.ureport_r4v.network.apis.StoriesApi;
import com.risuplabs.ureport_r4v.room.dao.StoriesDao;
import com.risuplabs.ureport_r4v.rx.DataManager;
import com.risuplabs.ureport_r4v.rx.ResponseListener;

import javax.inject.Inject;

public class AboutRepository {

    DataManager dataManager;
    AboutApi aboutApi;

    @Inject
    public AboutRepository(DataManager dataManager, AboutApi aboutApi) {
        this.dataManager = dataManager;
        this.aboutApi = aboutApi;
    }

    public void get_about(ResponseListener<ModelAbout> responseListener, String url){
        dataManager.performRequest(aboutApi.getAbout(url),responseListener);
    }

}
