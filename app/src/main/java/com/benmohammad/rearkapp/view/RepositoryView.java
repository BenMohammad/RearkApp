package com.benmohammad.rearkapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.R;
import com.benmohammad.rearkapp.glide.SerialTarget;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.viewmodels.RepositoryViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reark.reark.utils.RxViewBinder;

import static io.reark.reark.utils.Preconditions.checkNotNull;
import static io.reark.reark.utils.Preconditions.get;

public class RepositoryView extends FrameLayout {

    private TextView titleTextView;
    private TextView stargazersTextView;
    private TextView forksTextView;
    private ImageView avatarImageView;

    @NonNull
    private final SerialTarget<GlideDrawable> request = new SerialTarget<>();


    public RepositoryView(Context context) {
        super(context);
    }

    public RepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleTextView = findViewById(R.id.widget_layout_title);
        stargazersTextView = findViewById(R.id.widget_layout_stargazers);
        forksTextView = findViewById(R.id.widget_layout_forks);
        avatarImageView = findViewById(R.id.widget_avatar_image_view);
    }

    private void setRepository(@NonNull final GitHubRepository repository) {
        checkNotNull(repository);

        titleTextView.setText(repository.getName());
        stargazersTextView.setText("stars: " + repository.getStargazerCount());
        forksTextView.setText("forks: " +repository.getForksCount());
        request.set(Glide.with(getContext())
            .load(repository.getOwner().getAvatarUrl())
                .fitCenter()
                .crossFade()
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(avatarImageView)
        );
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        request.onDestroy();
    }

    public static class ViewBinder extends RxViewBinder {
        RepositoryView view;
        RepositoryViewModel viewModel;

        public ViewBinder(@NonNull final RepositoryView view,
                          @NonNull final RepositoryViewModel viewModel) {
            this.view = get(view);
            this.viewModel = viewModel;
        }

        @Override
        protected void bindInternal(@NonNull CompositeDisposable compositeDisposable) {
            compositeDisposable.add(viewModel.getRepository()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::setRepository));
        }
    }
}
