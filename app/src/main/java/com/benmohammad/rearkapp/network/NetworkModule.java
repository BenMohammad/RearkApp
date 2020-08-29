package com.benmohammad.rearkapp.network;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
public final class NetworkModule {

    @Provides
    @Singleton
    public NetworkApi provideNetworkApi(OkHttpClient client) {
        return new NetworkApi(client);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@Named("networkInterceptors") List<Interceptor> networkInterceptors) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.networkInterceptors().addAll(networkInterceptors);
        return builder.build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}
