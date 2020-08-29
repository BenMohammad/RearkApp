package com.benmohammad.rearkapp.stores;

import com.benmohammad.rearkapp.data.DataLayer;
import com.benmohammad.rearkapp.data.DataLayerBase;
import com.benmohammad.rearkapp.pojo.UserSettings;

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
