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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;

import static io.reark.reark.utils.Preconditions.checkNotNull;

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
    }
}
