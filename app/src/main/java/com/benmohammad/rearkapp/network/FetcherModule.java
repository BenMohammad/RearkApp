package com.benmohammad.rearkapp.network;

import android.net.Uri;

import com.benmohammad.rearkapp.data.stores.GitHubRepositorySearchStore;
import com.benmohammad.rearkapp.network.fetchers.GitHubRepositoryFetcher;
import com.benmohammad.rearkapp.data.stores.GitHubRepositoryStore;
import com.benmohammad.rearkapp.data.stores.NetworkRequestStatusStore;
import com.benmohammad.rearkapp.network.fetchers.GitHubRepositorySearchFetcher;

import java.util.Arrays;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reark.reark.network.fetchers.Fetcher;
import io.reark.reark.network.fetchers.UriFetcherManager;

@Module(includes = NetworkModule.class)
public final class FetcherModule {

    @Provides
    @Named("gitHubRepository")
    public Fetcher<Uri> provideGitHubRepositoryFetcher(NetworkApi networkApi,
                                                       NetworkRequestStatusStore networkRequestStatusStore,
                                                       GitHubRepositoryStore gitHubRepositoryStore) {
        return new GitHubRepositoryFetcher(networkApi, networkRequestStatusStore::put, gitHubRepositoryStore);
    }

    @Provides
    @Named("gitHubRepositorySearch")
    public Fetcher<Uri> provideGitHubRepositorySearchFetcher(NetworkApi networkApi,
                                                             NetworkRequestStatusStore networkRequestStatusStore,
                                                             GitHubRepositoryStore gitHubRepositoryStore,
                                                             GitHubRepositorySearchStore gitHubRepositorySearchStore) {
        return new GitHubRepositorySearchFetcher(networkApi,
                networkRequestStatusStore::put,
                gitHubRepositoryStore,
                gitHubRepositorySearchStore);
    }

    @Provides
    public UriFetcherManager provideUriFetcherManager(@Named("gitHubRepository") Fetcher<Uri> gitHubRepositoryFetcher,
                                                      @Named("gitHubRepositorySearch") Fetcher<Uri> gitHubRepositorySearch) {
        return new UriFetcherManager.Builder()
                .fetchers(Arrays.asList(gitHubRepositoryFetcher, gitHubRepositorySearch))
                .build();
    }


}
