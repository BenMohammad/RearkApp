package com.benmohammad.rearkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.benmohammad.rearkapp.MainActivity;
import com.benmohammad.rearkapp.R;
import com.benmohammad.rearkapp.RxGitHubApp;
import com.benmohammad.rearkapp.utils.ApplicationInstrumentation;
import com.benmohammad.rearkapp.view.RepositoryView;
import com.benmohammad.rearkapp.view.RepositoryView.ViewBinder;
import com.benmohammad.rearkapp.viewmodels.RepositoryViewModel;

import javax.inject.Inject;

public class RepositoryFragment extends Fragment {

    @Inject
    RepositoryViewModel viewModel;

    @Inject
    ApplicationInstrumentation instrumentation;

    private ViewBinder repositoryViewBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxGitHubApp.getInstance().getGraph().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repository_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repositoryViewBinder = new ViewBinder(
                (RepositoryView) view.findViewById(R.id.repository_view), viewModel);
        viewModel.subscribeToDataStore();
        view.findViewById(R.id.repository_fragment_choose_repository_button).setOnClickListener(
                e -> ((MainActivity) getActivity()).chooseRepository());
    }

    @Override
    public void onResume() {
        super.onResume();
        repositoryViewBinder.bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        repositoryViewBinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.dispose();
        instrumentation.getLeakTracing().traceLeakage(this);
    }
}
