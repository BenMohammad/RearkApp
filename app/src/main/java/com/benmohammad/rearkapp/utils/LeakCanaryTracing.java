package com.benmohammad.rearkapp.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryTracing implements LeakTracing {

    private RefWatcher refWatcher;

    private final Application application;

    public LeakCanaryTracing(Application application) {
        this.application = application;
    }

    @Override
    public void traceLeakage(Object reference) {
        refWatcher.watch(reference);
    }

    @Override
    public void init() {
        refWatcher = LeakCanary.install(application);

    }
}
