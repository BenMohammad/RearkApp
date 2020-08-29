package com.benmohammad.rearkapp.network.fetchers;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.network.GitHubService;
import com.benmohammad.rearkapp.network.NetworkApi;
import com.benmohammad.rearkapp.pojo.GitHubRepository;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import io.reark.reark.data.stores.interfaces.StorePutInterface;
import io.reark.reark.pojo.NetworkRequestStatus;
import io.reark.reark.utils.Log;

import static io.reark.reark.utils.Preconditions.checkNotNull;

public class GitHubRepositoryFetcher extends AppFetcherBase<Uri> {

    private static final String TAG = GitHubRepositoryFetcher.class.getSimpleName();
    @NonNull
    private final StorePutInterface<GitHubRepository> gitHubRepositoryStore;

    public GitHubRepositoryFetcher(@NonNull final NetworkApi networkApi,
                                   @NonNull final Consumer<NetworkRequestStatus> updateNetworkRequestStatus,
                                   @NonNull final StorePutInterface<GitHubRepository> gitHubRepositoryStore) {
        super(networkApi, updateNetworkRequestStatus);
        checkNotNull(gitHubRepositoryStore);
        this.gitHubRepositoryStore = gitHubRepositoryStore;
    }

    @Override
    public synchronized void fetch(@NonNull Intent intent, int listenerId) {
        checkNotNull(intent);
        if(!intent.hasExtra("repositoryId")) {
            Log.e(TAG, "Missing required fetch parameters" );
            return;
        }

        int repositoryId = intent.getIntExtra("repositoryId", 0);
        final String uri = getUniqueId(repositoryId);

        addListener(repositoryId, listenerId);

        if(isOngoingRequest(repositoryId)) {
            Log.d(TAG, "Found an ongoing for Repository: " + repositoryId);
            return;
        }

        Log.d(TAG, "fetch(" + repositoryId + ")");
        Disposable disposable = createNetworkObservable(repositoryId)
                .subscribeOn(Schedulers.computation())
                .flatMap(gitHubRepositoryStore::put)
                .doOnSubscribe(__ -> startRequest(repositoryId, uri))
                .doOnError(doOnError(repositoryId, uri))
                .doOnSuccess(updated -> completeRequest(repositoryId, uri, updated))
                .subscribe(Functions.emptyConsumer(),
                        Log.onError(TAG, "Error fetching GitHub Service: "+ repositoryId));
    }

    @NonNull
    private Single<GitHubRepository> createNetworkObservable(int repositoryId) {
        return getNetworkApi().getRepository(repositoryId);
    }

    @NonNull
    @Override
    public Uri getServiceUri() {
        return GitHubService.REPOSITORY;
    }

    @NonNull
    public static String getUniqueId(int repositoryId) {
        return GitHubRepository.class + "/" + repositoryId;
    }
}
