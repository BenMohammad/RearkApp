package com.benmohammad.rearkapp.data;

import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;

import io.reark.reark.data.stores.interfaces.StoreInterface;
import io.reark.reark.pojo.NetworkRequestStatus;

import static io.reark.reark.utils.Preconditions.get;

public abstract class DataLayerBase {

    @NonNull final StoreInterface<Integer, NetworkRequestStatus, NetworkRequestStatus> networkRequestStatusStore;

    @NonNull final StoreInterface<Integer, GitHubRepository, GitHubRepository> gitHubRepositoryStore;

    @NonNull final StoreInterface<String, GitHubRepositorySearch, GitHubRepositorySearch> gitHubRepositorySearchStore;

    protected DataLayerBase(@NonNull final StoreInterface<Integer, NetworkRequestStatus, NetworkRequestStatus> networkRequestStatusStore,
                            @NonNull final StoreInterface<Integer, GitHubRepository, GitHubRepository> gitHubRepositoryStore,
                            @NonNull final StoreInterface<String, GitHubRepositorySearch, GitHubRepositorySearch> gitHubRepositorySearchStore) {
        this.networkRequestStatusStore = get(networkRequestStatusStore);
        this.gitHubRepositoryStore = get(gitHubRepositoryStore);
        this.gitHubRepositorySearchStore = get(gitHubRepositorySearchStore);
    }
}
