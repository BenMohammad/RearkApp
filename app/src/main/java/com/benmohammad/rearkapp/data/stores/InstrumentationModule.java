package com.benmohammad.rearkapp.data.stores;

import android.app.Application;
import android.content.Context;

import com.benmohammad.rearkapp.injections.ForApplication;
import com.benmohammad.rearkapp.utils.ApplicationInstrumentation;
import com.benmohammad.rearkapp.utils.DebugApplicationInstrumentation;
import com.benmohammad.rearkapp.utils.Instrumentation;
import com.benmohammad.rearkapp.utils.LeakCanaryTracing;
import com.benmohammad.rearkapp.utils.LeakTracing;
import com.benmohammad.rearkapp.utils.StethoInstrumentation;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

@Module
public class InstrumentationModule {

    @Provides
    @Singleton
    public ApplicationInstrumentation provideInstrumentation(LeakTracing leakTracing, @Named("networkInstrumentation")Instrumentation networkInstrumentation) {
        return new DebugApplicationInstrumentation(leakTracing, networkInstrumentation);
    }

    @Provides
    @Singleton
    public LeakTracing provideLeakTracing(Application application) {
        return new LeakCanaryTracing(application);
    }

    @Provides
    @Singleton
    @Named("networkInstrumentation")
    public Instrumentation provideStethoInstrumention(@ForApplication Context context) {
        return new StethoInstrumentation(context);
    }

    @Provides
    @Singleton
    @Named("networkInterceptors")
    public List<Interceptor> provideNetworkInterceptors() {
        return Collections.singletonList(new StethoInterceptor());
    }
}
