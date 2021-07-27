package com.risuplabs.ureport.base;

import androidx.lifecycle.ViewModel;

import com.risuplabs.ureport.rx.SingleLiveEvent;

public class BaseViewModel extends ViewModel {

    public final String TAG = this.getClass().getSimpleName();
    public SingleLiveEvent<Boolean> loadingStatus=new SingleLiveEvent<>();


}
