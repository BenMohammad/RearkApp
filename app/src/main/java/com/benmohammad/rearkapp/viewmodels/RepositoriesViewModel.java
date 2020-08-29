package com.benmohammad.rearkapp.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.data.DataFunctions.GetGitHubRepository;
import com.benmohammad.rearkapp.data.DataFunctions.GetGitHubRepositorySearch;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reark.reark.data.DataStreamNotification;
import io.reark.reark.utils.RxUtils;
import io.reark.reark.viewmodels.AbstractViewModel;

import static io.reark.reark.utils.Preconditions.checkNotNull;
import static io.reark.reark.utils.Preconditions.get;

public class RepositoriesViewModel extends AbstractViewModel {

    private static final String TAG = RepositoriesViewModel.class.getSimpleName();

    public enum ProgressStatus {
        LOADING, ERROR, IDLE
    }

    private static final int MAX_REPOSITORIES_DISPLAYED = 5;
    private static final int SEARCH_INPUT_DELAY = 500;

    @NonNull
    private final GetGitHubRepositorySearch getGitHubRepositorySearch;

    @NonNull
    private final GetGitHubRepository getGitHubRepository;

    @NonNull
    private final PublishSubject<String> searchString = PublishSubject.create();

    @NonNull final PublishSubject<GitHubRepository> selectRepository = PublishSubject.create();

    @NonNull final BehaviorSubject<List<GitHubRepository>> repositories = BehaviorSubject.create();

    @NonNull final BehaviorSubject<ProgressStatus> networkRequestStatusText = BehaviorSubject.create();

    public RepositoriesViewModel(@NonNull final GetGitHubRepositorySearch getGitHubRepositorySearch,
                                 @NonNull final GetGitHubRepository getGitHubRepository) {
        this.getGitHubRepositorySearch = get(getGitHubRepositorySearch);
        this.getGitHubRepository = get(getGitHubRepository);
    }

    @NonNull
    public Observable<GitHubRepository> getSelectRepository() {
        return selectRepository.hide();
    }

    @NonNull
    public Observable<List<GitHubRepository>> getRepositories() {
        return repositories.hide();
    }

    @NonNull
    public Observable<ProgressStatus> getNetworkRequestStatusText() {
        return networkRequestStatusText.hide();
    }

    public void setSearchString(@NonNull final String searchString) {
        checkNotNull(searchString);
        this.searchString.onNext(searchString);
    }

    public void selectRepository(@NonNull final GitHubRepository repository) {
        checkNotNull(repository);
        this.selectRepository.onNext(repository);
    }

    @NonNull
    static Function<DataStreamNotification<GitHubRepositorySearch>, ProgressStatus> toProgressStatus(){
        return notification -> {
            if(notification.isOngoing()) {
                return ProgressStatus.LOADING;
            } else if(notification.isCompletedWithError()){
                return ProgressStatus.ERROR;
            } else {
                return ProgressStatus.IDLE;
            }
        };
    }

    @Override
    public void subscribeToDataStoreInternal(@NonNull CompositeDisposable compositeDisposable) {
        checkNotNull(compositeDisposable);
        Log.v(TAG, "subscribeToDataStoreInternal");

        ConnectableObservable<DataStreamNotification<GitHubRepositorySearch>> repositorySearchSource =
                searchString
                .debounce(SEARCH_INPUT_DELAY, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter(value -> value.length() > 2)
                .doOnNext(value -> Log.d(TAG, "Searching string"))
                .switchMap(getGitHubRepositorySearch::call)
                .publish();

        compositeDisposable.add(repositorySearchSource
        .map(toProgressStatus())
        .doOnNext(progressStatus -> Log.d(TAG, "Progress status:" + progressStatus.name()))
        .subscribe(this::setNetworkStatusText));

        compositeDisposable.add(repositorySearchSource
        .filter(DataStreamNotification::isOnNext)
        .map(DataStreamNotification::getValue)
        .map(GitHubRepositorySearch::getItems)
        .flatMap(toGithubRepositoryList())
        .doOnNext(list -> Log.d(TAG, "Publishing " + list.size() + "repos from the viewModel"))
        .subscribe(repositories::onNext));

        compositeDisposable.add(repositorySearchSource.connect());
    }

    @NonNull
    Function<List<Integer>, Observable<List<GitHubRepository>>> toGithubRepositoryList() {
        return repositoryIds -> Observable.fromIterable(repositoryIds)
                .take(MAX_REPOSITORIES_DISPLAYED)
                .map(this::getGithubRepositoryObservable)
                .toList()
                .toObservable()
                .flatMap(RxUtils::toObservableList);
    }

    @NonNull
    Observable<GitHubRepository> getGithubRepositoryObservable(@NonNull final Integer repositoryId) {
        checkNotNull(repositoryId);

        return getGitHubRepository
                .call(repositoryId)
                .doOnNext((repository -> Log.v(TAG, "Received repo: " + repository.getId())));
    }

    void setNetworkStatusText(@NonNull final ProgressStatus status) {
        checkNotNull(status);
        networkRequestStatusText.onNext(status);
    }
}
