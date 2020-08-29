package com.benmohammad.rearkapp.data;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;
import com.benmohammad.rearkapp.pojo.UserSettings;

import io.reactivex.Observable;
import io.reark.reark.data.DataStreamNotification;

public class DataFunctions {

    public interface GetUserSettings {
        @NonNull
        Observable<UserSettings> call();
    }

    public interface SetUserSettings {
        void call(@NonNull final UserSettings userSettings);
    }

    public interface GetGitHubRepository {
        @NonNull
        Observable<GitHubRepository> call(int repositoryId);
    }

    public interface FetchAndGetGitHubRepository {
        @NonNull
        Observable<DataStreamNotification<GitHubRepository>> call(int repositoryId);
    }

    public interface GetGitHubRepositorySearch {
        @NonNull
        Observable<DataStreamNotification<GitHubRepositorySearch>> call(@NonNull final String search);
    }
}
