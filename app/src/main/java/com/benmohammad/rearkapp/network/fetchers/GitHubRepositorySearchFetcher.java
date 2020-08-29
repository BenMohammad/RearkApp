package com.benmohammad.rearkapp.network.fetchers;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.network.NetworkApi;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;

import io.reactivex.functions.Consumer;
import io.reark.reark.data.stores.interfaces.StorePutInterface;
import io.reark.reark.pojo.NetworkRequestStatus;

import static io.reark.reark.utils.Preconditions.get;

public class GitHubRepositorySearchFetcher extends AppFetcherBase<Uri> {

    private final static String TAG = GitHubRepositorySearchFetcher.class.getSimpleName();

    @NonNull
    private final StorePutInterface<GitHubRepository> gitHubRepositoryStore;

    @NonNull
    private final StorePutInterface<GitHubRepositorySearch> gitHubRepositorySearchStore;

    public GitHubRepositorySearchFetcher(@NonNull final NetworkApi networkApi,
                                         @NonNull final Consumer<NetworkRequestStatus> updateNetworkRequestStatus,
                                         @NonNull final StorePutInterface<GitHubRepository> gitHubRepositoryStore,
                                         @NonNull final StorePutInterface<GitHubRepositorySearch> gitHubRepositorySearchStore) {
        super(networkApi, updateNetworkRequestStatus);
        this.gitHubRepositoryStore = get(gitHubRepositoryStore);
        this.gitHubRepositorySearchStore = get(gitHubRepositorySearchStore);
    }

    @Override
    public void fetch(@NonNull Intent intent, int listenerId) {

    }

    @NonNull
    @Override
    public Uri getServiceUri() {
        return null;
    }
}
