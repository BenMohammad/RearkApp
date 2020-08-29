package com.benmohammad.rearkapp.data;

import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.benmohammad.rearkapp.network.GitHubService;
import com.benmohammad.rearkapp.network.fetchers.GitHubRepositoryFetcher;
import com.benmohammad.rearkapp.network.fetchers.GitHubRepositorySearchFetcher;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;
import com.benmohammad.rearkapp.pojo.UserSettings;

import java.util.UUID;

import io.reactivex.Observable;
import io.reark.reark.data.DataStreamNotification;
import io.reark.reark.data.stores.interfaces.StoreInterface;
import io.reark.reark.data.utils.DataLayerUtils;
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

    @NonNull
    public Observable<DataStreamNotification<GitHubRepositorySearch>> fetchAndGetGitHubRepositorySearch(@NonNull final String searchString) {
        checkNotNull(searchString);
        Log.d(TAG, "fetchAndGetGitHubRepositorySearch(" + searchString + ")");
        int listenerId = fetchGitHubRepositorySearch(searchString);
        return getGitHubRepositorySearch(listenerId, searchString);
    }

    @NonNull
    private Observable<DataStreamNotification<GitHubRepositorySearch>> getGitHubRepositorySearch(
            @Nullable Integer listenerId, @NonNull final String searchString) {
        checkNotNull(searchString);
        Log.d(TAG, "getGitHubRepositorySearch(" + searchString + ")");
        final Observable<NetworkRequestStatus> networkRequestStatusObservable =
                networkRequestStatusStore
                .getOnceAndStream(GitHubRepositorySearchFetcher.getUniqueId(searchString).hashCode())
                .filter(NetworkRequestStatus::isSome)
                .filter(status -> status.forListener(listenerId));

        final Observable<GitHubRepositorySearch> gitHubRepositorySearchObservable =
                gitHubRepositorySearchStore
                .getOnceAndStream(searchString)
                .filter(GitHubRepositorySearch::isSome);

        return DataLayerUtils.createDataStreamNotificationObservable(
                networkRequestStatusObservable, gitHubRepositorySearchObservable);
    }

    protected abstract int fetchGitHubRepositorySearch(@NonNull final  String searchString);

    @NonNull
    public Observable<DataStreamNotification<GitHubRepository>> fetchAndGetGitHubRepository(
            @NonNull final Integer repositoryId) {
        checkNotNull(repositoryId);
        Log.d(TAG, "fetchAndGetGitHubRepository(" + repositoryId + ")");
        int listenerId = fetchGitHubRepository(repositoryId);
        return getGitHubRepository(listenerId, repositoryId);
    }

    @NonNull
    public Observable<GitHubRepository> getGitHubRepository(@NonNull final Integer repositoryId) {
        return gitHubRepositoryStore
                .getOnceAndStream(repositoryId)
                .filter(GitHubRepository::isSome);
    }

    @NonNull
    public Observable<DataStreamNotification<GitHubRepository>> getGitHubRepository(
            @Nullable Integer listenerId, @NonNull final Integer  repositoryId) {
        checkNotNull(repositoryId);
        Log.d(TAG, "getGitHubRepository(" + repositoryId + ")");
        final Observable<NetworkRequestStatus> networkRequestStatusObservable =
                networkRequestStatusStore
                .getOnceAndStream(GitHubRepositoryFetcher.getUniqueId(repositoryId).hashCode())
                .filter(NetworkRequestStatus::isSome)
                .filter(status -> status.forListener(listenerId));

        final Observable<GitHubRepository> gitHubRepositoryObservable =
                gitHubRepositoryStore
                .getOnceAndStream(repositoryId)
                .filter(GitHubRepository::isSome);

        return DataLayerUtils.createDataStreamNotificationObservable(
                networkRequestStatusObservable, gitHubRepositoryObservable);
    }

    protected abstract int fetchGitHubRepository(@NonNull final Integer repositoryId);

    @NonNull
    public Observable<UserSettings> getUserSettings() {
        return userSettingsStore
                .getOnceAndStream(DEFAULT_USER_ID)
                .filter(UserSettings::isSome);
    }

    public void setUserSettings(@NonNull final UserSettings userSettings) {
        checkNotNull(userSettings);
        userSettingsStore.put(userSettings);
    }

}
