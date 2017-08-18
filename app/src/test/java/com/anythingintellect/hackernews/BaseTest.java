package com.anythingintellect.hackernews;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseTest {

    @BeforeClass
    public static void rxSetup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(@NonNull Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.computation();
            }
        });
    }

    @AfterClass
    public static void rxTearDown() {
        RxAndroidPlugins.reset();
    }

}
