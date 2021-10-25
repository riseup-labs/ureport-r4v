package com.riseuplabs.ureport_r4v.rx;

import com.riseuplabs.ureport_r4v.network.utils.ApiResponse;

public interface ResponseListener<T> {

    void onStart();

    void onFinish();

    void onResponse(ApiResponse<T> apiResponse);

}
