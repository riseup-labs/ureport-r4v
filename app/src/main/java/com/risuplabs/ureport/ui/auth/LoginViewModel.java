package com.risuplabs.ureport.ui.auth;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport.base.BaseViewModel;
import com.risuplabs.ureport.model.story.ModelStories;
import com.risuplabs.ureport.network.utils.ApiResponse;
import com.risuplabs.ureport.rx.ResponseListener;
import com.risuplabs.ureport.surveyor.net.responses.TokenResults;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    LoginRepository loginRepository;
    public MutableLiveData<ApiResponse<TokenResults>> tokenResults = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void login(String username, String password, String role) {
        loginRepository.login(new ResponseListener<TokenResults>() {
            @Override
            public void onStart() {
                loadingStatus.setValue(true);
            }

            @Override
            public void onFinish() {
                loadingStatus.setValue(false);
            }

            @Override
            public void onResponse(ApiResponse<TokenResults> apiResponse) {
                tokenResults.setValue(apiResponse);
            }
        }, username, password, role);
    }
}
