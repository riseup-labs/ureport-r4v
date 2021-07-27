package com.risuplabs.ureport.rx;

import com.risuplabs.ureport.network.utils.ApiResponse;

public interface ResponseListener<T> {

    void onStart();

    void onFinish();

    void onResponse(ApiResponse<T> apiResponse);

}
