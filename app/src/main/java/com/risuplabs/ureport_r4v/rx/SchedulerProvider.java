package com.risuplabs.ureport_r4v.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerProvider implements BaseScheduler {
    @Override
    public Scheduler io() {
        return Schedulers.newThread();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}

