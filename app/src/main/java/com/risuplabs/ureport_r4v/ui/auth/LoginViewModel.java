package com.risuplabs.ureport_r4v.ui.auth;

import androidx.lifecycle.MutableLiveData;

import com.risuplabs.ureport_r4v.base.BaseViewModel;
import com.risuplabs.ureport_r4v.network.utils.ApiResponse;
import com.risuplabs.ureport_r4v.rx.ResponseListener;
import com.risuplabs.ureport_r4v.surveyor.net.responses.TokenResults;

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
