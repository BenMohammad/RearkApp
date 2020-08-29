package com.benmohammad.rearkapp.data;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.network.GitHubService;
import com.benmohammad.rearkapp.data.stores.GitHubRepositorySearchStore;
import com.benmohammad.rearkapp.data.stores.GitHubRepositoryStore;
import com.benmohammad.rearkapp.data.stores.NetworkRequestStatusStore;
import com.benmohammad.rearkapp.data.stores.UserSettingsStore;

import io.reark.reark.network.fetchers.Fetcher;
import io.reark.reark.network.fetchers.UriFetcherManager;

import static io.reark.reark.utils.Preconditions.get;

public class DataLayer extends ClientDataLayerBase {

    private static final String TAG = DataLayer.class.getSimpleName();
    private final UriFetcherManager fetcherManager;

    public DataLayer(@NonNull final UriFetcherManager fetcherManager,
                     @NonNull final NetworkRequestStatusStore networkRequestStatusStore,
                     @NonNull final GitHubRepositoryStore gitHubRepositoryStore,
                     @NonNull final GitHubRepositorySearchStore gitHubRepositorySearchStore,
                     @NonNull final UserSettingsStore userSettingsStore) {
        super(networkRequestStatusStore,
                gitHubRepositoryStore,
                gitHubRepositorySearchStore,
                userSettingsStore);

        this.fetcherManager = get(fetcherManager);
    }

    @Override
    protected int fetchGitHubRepositorySearch(@NonNull String searchString) {
        Log.d(TAG, "fetchGitHubRepositorySearch(" + get(searchString)+ ")");
        int listenerId = createListenerId();
        Intent intent = new Intent();
        intent.putExtra("serviceUriString", GitHubService.REPOSITORY_SEARCH.toString());
        intent.putExtra("searchString", searchString);
        Fetcher<Uri> fetcher = fetcherManager.findFetcher(GitHubService.REPOSITORY_SEARCH);
        if(fetcher != null) {
            fetcher.fetch(intent, listenerId);
        }

        return listenerId;
    }

    @Override
    protected int fetchGitHubRepository(@NonNull Integer repositoryId) {
        Log.d(TAG, "fetchGitHubRepository(" + get(repositoryId) + ")");
        int listenerId = createListenerId();
        Intent intent = new Intent();
        intent.putExtra("serviceUriString", GitHubService.REPOSITORY.toString());
        intent.putExtra("repositoryId", repositoryId);
        Fetcher<Uri> fetcher = fetcherManager.findFetcher(GitHubService.REPOSITORY);
        if(fetcher != null) {
            fetcher.fetch(intent, listenerId);
        }

        return listenerId;
    }
}
