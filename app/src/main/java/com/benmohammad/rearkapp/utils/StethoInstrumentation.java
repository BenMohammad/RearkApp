package com.benmohammad.rearkapp.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import static com.facebook.stetho.Stetho.defaultDumperPluginsProvider;
import static com.facebook.stetho.Stetho.defaultInspectorModulesProvider;
import static com.facebook.stetho.Stetho.initialize;
import static com.facebook.stetho.Stetho.newInitializerBuilder;

public class StethoInstrumentation implements Instrumentation {

    @NonNull
    private final Context context;

    public StethoInstrumentation(@NonNull final Context context) {
        this.context = context;
    }

    @Override
    public void init() {
        initStetho();
    }

    @VisibleForTesting
    void initStetho() {
        initialize(
                newInitializerBuilder(context)
                .enableDumpapp(defaultDumperPluginsProvider(context))
                .enableWebKitInspector(defaultInspectorModulesProvider(context))
                .build()
        );
    }
}
