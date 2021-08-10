package com.risuplabs.ureport_r4v.ui.opinions;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport_r4v.base.BaseViewModel;
import com.risuplabs.ureport_r4v.network.utils.ApiResponse;
import com.risuplabs.ureport_r4v.rx.ResponseListener;
import com.risuplabs.ureport_r4v.surveyor.net.responses.FlowResponse;

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
