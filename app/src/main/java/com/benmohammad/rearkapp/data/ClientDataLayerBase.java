package com.benmohammad.rearkapp.data;

import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benmohammad.rearkapp.network.fetchers.GitHubRepositoryFetcher;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;
import com.benmohammad.rearkapp.pojo.UserSettings;

import java.util.UUID;

import io.reactivex.Observable;
import io.reark.reark.data.DataStreamNotification;
import io.reark.reark.data.stores.interfaces.StoreInterface;
import io.reark.reark.pojo.NetworkRequestStatus;

import static io.reark.reark.utils.Preconditions.checkNotNull;
import static io.reark.reark.utils.Preconditions.get;

public abstract class ClientDataLayerBase extends DataLayerBase {

    private static final String TAG = ClientDataLayerBase.class.getSimpleName();
    public static final int DEFAULT_USER_ID = 0;

    @NonNull
    protected final StoreInterface<Integer, UserSettings, UserSettings> userSettingsStore;

    protected ClientDataLayerBase(@NonNull final StoreInterface<Integer, NetworkRequestStatus, NetworkRequestStatus> networkRequestStatusStore,
                                  @NonNull final StoreInterface<Integer, GitHubRepository, GitHubRepository> gitHubRepositoryStore,
                                  @NonNull final StoreInterface<String, GitHubRepositorySearch, GitHubRepositorySearch> gitHubRepositorySearchStore,
                                  @NonNull final StoreInterface<Integer, UserSettings, UserSettings> userSettingsStore) {
        super(networkRequestStatusStore, gitHubRepositoryStore, gitHubRepositorySearchStore);
        this.userSettingsStore = get(userSettingsStore);
    }

    protected static int createListenerId() {
        return UUID.randomUUID().hashCode();
    }

//    @NonNull
//    public Observable<DataStreamNotification<GitHubRepositorySearch>> fetchAndGetGitHubRepositorySearch(@NonNull final String searchString) {
//        checkNotNull(searchString);
//        Log.d(TAG, "fetchAndGetGitHubRepositorySearch(" + searchString + ")");
//        int listenerId = fetchGitHubRepositorySearch(searchString);
//        return getGitHubRepositorySearch(listenerId, searchString);
//    }
//
//    @NonNull
//    private Observable<DataStreamNotification<GitHubRepositorySearch>> getGitHubRepositorySearch(
//            @Nullable Integer listenerId, @NonNull final String searchString) {
//        checkNotNull(searchString);
//        Log.d(TAG, "getGitHubRepositorySearch(" + searchString + ")");
//        final Observable<NetworkRequestStatus> networkRequestStatusObservable =
//                networkRequestStatusStore
//                .getOnceAndStream()
//    }

    protected abstract int fetchGitHubRepositorySearch(@NonNull final  String searchString);


}
