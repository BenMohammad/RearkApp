package com.benmohammad.rearkapp.injections;

import android.app.Application;

import com.benmohammad.rearkapp.MainActivity;
import com.benmohammad.rearkapp.RxGitHubApp;
import com.benmohammad.rearkapp.data.stores.DataStoreModule;
import com.benmohammad.rearkapp.data.stores.InstrumentationModule;
import com.benmohammad.rearkapp.fragments.RepositoriesFragment;
import com.benmohammad.rearkapp.fragments.RepositoryFragment;
import com.benmohammad.rearkapp.viewmodels.RepositoriesViewModel;
import com.benmohammad.rearkapp.viewmodels.RepositoryViewModel;
import com.benmohammad.rearkapp.viewmodels.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, InstrumentationModule.class,
        DataStoreModule.class, ViewModelModule.class})
public interface Graph {

    void inject(MainActivity mainActivity);

    void inject(RepositoriesViewModel repositoriesViewModel);

    void inject(RepositoryViewModel repositoryViewModel);

    void inject(RepositoryFragment repositoryFragment);

    void inject(RepositoriesFragment repositoriesFragment);

    void inject(RxGitHubApp rxGitHubApp);

    final class Initializer {
        public static Graph init(Application application) {
            return DaggerGraph.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}
