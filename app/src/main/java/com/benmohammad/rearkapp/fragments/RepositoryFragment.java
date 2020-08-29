package com.benmohammad.rearkapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    }
}
