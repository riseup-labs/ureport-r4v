package com.riseuplabs.ureport_r4v.ui.about;

import com.riseuplabs.ureport_r4v.model.about.ModelAbout;
import com.riseuplabs.ureport_r4v.network.apis.AboutApi;
import com.riseuplabs.ureport_r4v.rx.DataManager;
import com.riseuplabs.ureport_r4v.rx.ResponseListener;

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
