package com.benmohammad.rearkapp.data.stores;

import com.benmohammad.rearkapp.data.DataLayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class StoreModule {

    @Provides
    @Singleton
    public NetworkRequestStatusStore provideNetworkRequestStatusStore() {
        return new NetworkRequestStatusStore();
    }

    @Provides
    @Singleton
    public GitHubRepositoryStore provideGitHubRepositoryStore() {
        return new GitHubRepositoryStore();
    }

    @Provides
    @Singleton
    public GitHubRepositorySearchStore provideGitHubRepositorySearchStore() {
        return new GitHubRepositorySearchStore();
    }

    @Provides
    @Singleton
    public UserSettingsStore provideUserSettingsStore() {
        return new UserSettingsStore(DataLayer.DEFAULT_USER_ID);
    }
}
