package com.benmohammad.rearkapp.network.fetchers;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.network.GitHubService;
import com.benmohammad.rearkapp.network.NetworkApi;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import io.reark.reark.data.stores.interfaces.StorePutInterface;
import io.reark.reark.pojo.NetworkRequestStatus;

import static io.reark.reark.utils.Log.*;
import static io.reark.reark.utils.Preconditions.checkNotNull;
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
        checkNotNull(intent);

        if(!intent.hasExtra("searchString")) {
            Log.e(TAG, "Missing required fetch parameters");
            return;
        }

        String searchString = intent.getStringExtra("searchString");
        String uri = getUniqueId(searchString);
        int requestId = searchString.hashCode();

        addListener(requestId, listenerId);

        if(isOngoingRequest(requestId)) {
            Log.d(TAG, "Found onGoing request for repository:" + searchString);
            return;
        }

        Log.d(TAG, "fetch(" + searchString + ")");

        Disposable disposable = createNetworkObservable(searchString)
                .subscribeOn(Schedulers.computation())
                .flatMapObservable(Observable::fromIterable)
                .doOnNext(gitHubRepositoryStore::put)
                .map(GitHubRepository::getId)
                .toList()
                .map(idList -> new GitHubRepositorySearch(searchString, idList))
                .flatMap(gitHubRepositorySearchStore::put)
                .doOnSubscribe(__ -> startRequest(requestId, uri))
                .doOnSuccess(updated -> completeRequest(requestId, uri, updated))
                .doOnError(doOnError(requestId, uri))
                .subscribe(Functions.emptyConsumer(),
                        onError(TAG, "Error fetching Github repository search for: '" + searchString + "'"));

        addRequest(requestId, disposable);
    }

    @NonNull
    private Single<List<GitHubRepository>> createNetworkObservable(@NonNull final String searchString) {
        return getNetworkApi().search(Collections.singletonMap("q", searchString));
    }



    @NonNull
    @Override
    public Uri getServiceUri() {
        return GitHubService.REPOSITORY_SEARCH;
    }

    @NonNull
    public static String getUniqueId(String search) {
        return GitHubRepository.class + "/" + search;
    }
}
