package com.benmohammad.rearkapp.injections;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public ContentResolver contentResolver(@ForApplication Context context) {
        return context.getContentResolver();
    }
}
