package com.risuplabs.ureport.ui.opinions;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport.base.BaseViewModel;
import com.risuplabs.ureport.network.utils.ApiResponse;
import com.risuplabs.ureport.rx.ResponseListener;
import com.risuplabs.ureport.surveyor.net.responses.Flow;
import com.risuplabs.ureport.surveyor.net.responses.FlowResponse;
import com.risuplabs.ureport.surveyor.net.responses.TokenResults;
import com.risuplabs.ureport.ui.auth.LoginRepository;

import java.util.List;

import javax.inject.Inject;

public class OpinionViewModel extends BaseViewModel {

    OpinionRepository opinionRepository;
    public MutableLiveData<ApiResponse<FlowResponse>> flowsFromRemote = new MutableLiveData<>();

    @Inject
    public OpinionViewModel(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    public void getFlows() {
        opinionRepository.getFlows(new ResponseListener<FlowResponse>() {
            @Override
            public void onStart() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onFinish() {
                loadingStatus.setValue(false);
            }

            @Override
            public void onResponse(ApiResponse<FlowResponse> apiResponse) {
                if(apiResponse.data != null) {
                    flowsFromRemote.setValue(apiResponse);
                }else{
                    Log.d(TAG, "onResponse: "+apiResponse.statusCode);
                }
            }
        });
    }
    
}
