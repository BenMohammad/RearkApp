package com.benmohammad.rearkapp;

import android.app.Application;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.injections.Graph;
import com.benmohammad.rearkapp.utils.ApplicationInstrumentation;

import javax.inject.Inject;

public class RxGitHubApp extends Application {

    private static final String TAG = RxGitHubApp.class.getSimpleName();

    private static RxGitHubApp instance;

    private Graph graph;

    @Inject
    ApplicationInstrumentation instrumentation;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        graph = Graph.Initializer.init(this);
        getGraph().inject(this);
        instrumentation.init();
    }

    @NonNull
    public static RxGitHubApp getInstance() {
        return instance;
    }

    @NonNull
    public  Graph getGraph() {
        return graph;
    }
}
