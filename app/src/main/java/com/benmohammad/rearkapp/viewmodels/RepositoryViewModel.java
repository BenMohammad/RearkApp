package com.benmohammad.rearkapp.viewmodels;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.data.DataFunctions;
import com.benmohammad.rearkapp.data.DataStreamNotification;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.UserSettings;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subjects.BehaviorSubject;

import static com.benmohammad.rearkapp.data.DataFunctions.*;
import static io.reark.reark.utils.Preconditions.checkNotNull;
import static io.reark.reark.utils.Preconditions.get;

public class RepositoryViewModel extends AbstractViewModel {

    @NonNull final GetUserSettings getUserSettings;

    @NonNull
    private final FetchAndGetGitHubRepository fetchAndGetGitHubRepository;

    @NonNull
    private final BehaviorSubject<GitHubRepository> repository = BehaviorSubject.create();

    public RepositoryViewModel(@NonNull final GetUserSettings getUserSettings,
                               @NonNull final FetchAndGetGitHubRepository fetchAndGetGitHubRepository) {
        this.getUserSettings = get(getUserSettings);
        this.fetchAndGetGitHubRepository = get(fetchAndGetGitHubRepository);
    }
    @Override
    public void subscribeToDataStoreInternal(@NonNull final CompositeDisposable compositeDisposable) {
        checkNotNull(compositeDisposable);
        compositeDisposable.add(getUserSettings.call()
                .map(UserSettings::getSelectedRepositoryId)
                .switchMap(fetchAndGetGitHubRepository::call)
                .filter(DataStreamNotification::isOnNext)
                .map(DataStreamNotification::getValue)
                .subscribe(repository::onNext));
    }

    @NonNull
    public Observable<GitHubRepository> getRepository() {
        return repository.hide();
    }
}
