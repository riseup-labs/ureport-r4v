package com.risuplabs.ureport.rx;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

public interface BaseScheduler {
    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}

