package com.benmohammad.rearkapp.data.stores;

import com.benmohammad.rearkapp.data.DataFunctions;
import com.benmohammad.rearkapp.data.DataLayer;
import com.benmohammad.rearkapp.network.FetcherModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reark.reark.network.fetchers.UriFetcherManager;

@Module(includes = {FetcherModule.class, StoreModule.class})
public final class DataStoreModule {

    @Provides
    public DataFunctions.FetchAndGetGitHubRepository provideFetchAndGetGitHubRepository(DataLayer dataLayer) {
        return dataLayer::fetchAndGetGitHubRepository;
    }

    @Provides
    public DataFunctions.GetGitHubRepository provideGetGitHubRepository(DataLayer dataLayer) {
        return dataLayer::getGitHubRepository;
    }

    @Provides
    public DataFunctions.GetGitHubRepositorySearch provideGetGitHubRepositorySearch(DataLayer dataLayer) {
        return dataLayer::fetchAndGetGitHubRepositorySearch;
    }

    @Provides
    public DataFunctions.GetUserSettings provideGetUserSettings(DataLayer dataLayer) {
        return dataLayer::getUserSettings;
    }

    @Provides
    public DataFunctions.SetUserSettings provideSetUserSettings(DataLayer dataLayer) {
        return dataLayer::setUserSettings;
    }

    @Provides
    @Singleton
    public DataLayer provideDataLayer(UriFetcherManager fetcherManager,
                                      NetworkRequestStatusStore networkRequestStatusStore,
                                      GitHubRepositoryStore gitHubRepositoryStore,
                                      GitHubRepositorySearchStore gitHubRepositorySearchStore,
                                      UserSettingsStore userSettingsStore) {
        return new DataLayer(fetcherManager,
                                networkRequestStatusStore,
                                gitHubRepositoryStore,
                                gitHubRepositorySearchStore,
                                userSettingsStore);
    }
}
