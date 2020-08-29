package com.benmohammad.rearkapp.network;

import android.net.Uri;

import com.benmohammad.rearkapp.network.fetchers.GitHubRepositoryFetcher;
import com.benmohammad.rearkapp.data.stores.GitHubRepositoryStore;
import com.benmohammad.rearkapp.data.stores.NetworkRequestStatusStore;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reark.reark.network.fetchers.Fetcher;

@Module(includes = NetworkModule.class)
public final class FetcherModule {

    @Provides
    @Named("gitHubRepository")
    public Fetcher<Uri> provideGitHubRepositoryFetcher(NetworkApi networkApi,
                                                       NetworkRequestStatusStore networkRequestStatusStore,
                                                       GitHubRepositoryStore gitHubRepositoryStore) {
        return new GitHubRepositoryFetcher(networkApi, networkRequestStatusStore::put, gitHubRepositoryStore);
    }


}
