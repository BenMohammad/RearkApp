package com.benmohammad.rearkapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benmohammad.rearkapp.R;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.viewmodels.RepositoriesViewModel;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reark.reark.utils.RxViewBinder;

import static io.reark.reark.utils.Preconditions.checkNotNull;
import static io.reark.reark.utils.Preconditions.get;


public class RepositoriesView extends FrameLayout {


    private TextView statusText;
    private Observable<String> searchStringObservable;
    private RecyclerView repositoriesListView;
    private RepositoriesAdapter repositoriesAdapter;

    public RepositoriesView(Context context) {
        super(context);
    }

    public RepositoriesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        EditText editText = findViewById(R.id.repositories_search);
        searchStringObservable = RxTextView.textChanges(editText).map(CharSequence::toString);
        statusText = findViewById(R.id.repositories_status_text);
        repositoriesAdapter = new RepositoriesAdapter(Collections.emptyList());
        repositoriesListView = findViewById(R.id.repositories_list_view);
        repositoriesListView.setHasFixedSize(true);
        repositoriesListView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoriesListView.setAdapter(repositoriesAdapter);
    }

    private void setRepositories(@NonNull final List<GitHubRepository> repositories) {
        checkNotNull(repositories);
        checkNotNull(repositoriesAdapter);
        repositoriesAdapter.set(repositories);
    }

    private void setNetworkRequestStatus(@NonNull final RepositoriesViewModel.ProgressStatus networkRequestStatus) {
        checkNotNull(networkRequestStatus);
        setNetworkRequestStatusText(getLoadingStatusString(networkRequestStatus));
    }

    @NonNull
    private static String getLoadingStatusString(RepositoriesViewModel.ProgressStatus networkRequestStatus) {
        switch(networkRequestStatus) {
            case LOADING:
                return "Loading..";
            case ERROR:
                return "Error occurred";
            case IDLE:
            default:
                return "";
        }
    }

    private void setNetworkRequestStatusText(@NonNull final String networkRequestStatusText) {
        checkNotNull(networkRequestStatusText);
        checkNotNull(statusText);
        statusText.setText(networkRequestStatusText);
    }

    public static class ViewBinder extends RxViewBinder {
        private final RepositoriesView view;
        private final RepositoriesViewModel viewModel;

        public ViewBinder(@NonNull final RepositoriesView view,
                          @NonNull final RepositoriesViewModel viewModel) {
            this.view = get(view);
            this.viewModel = get(viewModel);
        }

        @Override
        protected void bindInternal(@NonNull CompositeDisposable compositeDisposable) {
            compositeDisposable.add(viewModel.getRepositories()
                        .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::setRepositories));

            compositeDisposable.add(viewModel.getNetworkRequestStatusText()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::setNetworkRequestStatus));

            compositeDisposable.add(view.searchStringObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewModel::setSearchString));

            compositeDisposable.add(Completable.fromAction(() -> view.repositoriesAdapter.setOnClickListener(this::repositoriesAdapterOnClick))
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }

        private void repositoriesAdapterOnClick(View clickedView) {
            final int itemPosition = view.repositoriesListView.getChildAdapterPosition(clickedView);
            GitHubRepository repository = view.repositoriesAdapter.getItem(itemPosition);
            viewModel.selectRepository(repository);
        }
    }


}
