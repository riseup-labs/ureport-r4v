package com.riseuplabs.ureport_r4v.base;

import androidx.lifecycle.ViewModel;

import com.riseuplabs.ureport_r4v.rx.SingleLiveEvent;

public class BaseViewModel extends ViewModel {

    public final String TAG = this.getClass().getSimpleName();
    public SingleLiveEvent<Boolean> loadingStatus=new SingleLiveEvent<>();


}
