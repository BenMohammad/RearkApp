package com.benmohammad.rearkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benmohammad.rearkapp.ChooseRepositoryActivity;
import com.benmohammad.rearkapp.R;
import com.benmohammad.rearkapp.RxGitHubApp;
import com.benmohammad.rearkapp.utils.ApplicationInstrumentation;
import com.benmohammad.rearkapp.view.RepositoriesView;
import com.benmohammad.rearkapp.viewmodels.RepositoriesViewModel;

import javax.inject.Inject;

public class RepositoriesFragment extends Fragment {

    @Inject
    RepositoriesViewModel repositoriesViewModel;

    @Inject
    ApplicationInstrumentation instrumentation;

    private RepositoriesView.ViewBinder viewBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxGitHubApp.getInstance().getGraph().inject(this);
        repositoriesViewModel.getSelectRepository()
                .subscribe(repository -> ((ChooseRepositoryActivity) getActivity()).chooseRepository(repository.getId()));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repositories_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = new RepositoriesView.ViewBinder(
                (RepositoriesView) view.findViewById(R.id.repositories_view),
                repositoriesViewModel);
        repositoriesViewModel.subscribeToDataStore();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewBinder.bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewBinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        repositoriesViewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repositoriesViewModel.dispose();
        instrumentation.getLeakTracing().traceLeakage(this);
    }
}
