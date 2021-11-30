package com.riseuplabs.ureport_r4v.ui.about;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.riseuplabs.ureport_r4v.base.BaseViewModel;
import com.riseuplabs.ureport_r4v.model.about.ModelAbout;
import com.riseuplabs.ureport_r4v.network.utils.ApiResponse;
import com.riseuplabs.ureport_r4v.rx.ResponseListener;

import javax.inject.Inject;

public class AboutViewModel extends BaseViewModel {

    AboutRepository aboutRepository;
    public MutableLiveData<ApiResponse<ModelAbout>> response=new MutableLiveData<>();

    @Inject
    public AboutViewModel(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public void getAbout(String url) {
        aboutRepository.get_about(new ResponseListener<ModelAbout>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onResponse(ApiResponse<ModelAbout> apiResponse) {

                if (apiResponse!=null && apiResponse.data!=null){
                    response.setValue(apiResponse);
                }
                loadingStatus.setValue(false);
                assert apiResponse != null;
                Log.d("ERROR_CODE", "onResponse: "+ apiResponse.statusCode);

            }
        },url);
    }
    
}
