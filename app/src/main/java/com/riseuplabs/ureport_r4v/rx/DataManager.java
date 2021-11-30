package com.riseuplabs.ureport_r4v.rx;

import com.riseuplabs.ureport_r4v.network.utils.ApiResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DataManager {
    final BaseScheduler scheduler;

    @Inject
    public DataManager(BaseScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public <T> void performRequest(Observable<T> observable, ResponseListener<T> responseListener) {
        observable.subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        responseListener.onStart();
                    }

                    @Override
                    public void onNext(T t) {
                        responseListener.onResponse(new ApiResponse<>(ResponseStatus.SUCCESS,t,null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        responseListener.onResponse(new ApiResponse<>(ResponseStatus.ERROR,null,e));
                    }

                    @Override
                    public void onComplete() {
                        responseListener.onFinish();
                    }
                });
    }

}
