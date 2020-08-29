package com.benmohammad.rearkapp.viewmodels;

import android.provider.ContactsContract;

import com.benmohammad.rearkapp.data.DataFunctions;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    public RepositoriesViewModel provideRepositoriesViewModel(DataFunctions.GetGitHubRepositorySearch repositorySearch,
                                                          DataFunctions.GetGitHubRepository repositoryRepository) {
        return new RepositoriesViewModel(repositorySearch, repositoryRepository);
    }

    @Provides RepositoryViewModel provideRepositoryViewModel(DataFunctions.GetUserSettings getUserSettings,
                                                             DataFunctions.FetchAndGetGitHubRepository fetchAndGetGitHubRepository) {
        return new RepositoryViewModel(getUserSettings, fetchAndGetGitHubRepository);
    }
}
