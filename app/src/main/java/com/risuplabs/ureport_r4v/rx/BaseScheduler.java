package com.risuplabs.ureport_r4v.rx;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

public interface BaseScheduler {
    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}

