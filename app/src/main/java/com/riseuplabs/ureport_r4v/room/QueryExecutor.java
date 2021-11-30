package com.riseuplabs.ureport_r4v.room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class QueryExecutor {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


}

