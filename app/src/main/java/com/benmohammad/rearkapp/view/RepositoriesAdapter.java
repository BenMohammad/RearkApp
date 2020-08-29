package com.benmohammad.rearkapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.benmohammad.rearkapp.R;
import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesAdapter extends Adapter<RepositoriesAdapter.RepositoryViewHolder> {

    private final List<GitHubRepository> gitHubRepositories = new ArrayList<>(0);
    private OnClickListener onClickListener;

    public RepositoriesAdapter(List<GitHubRepository> gitHubRepositories) {
        this.gitHubRepositories.addAll(gitHubRepositories);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public GitHubRepository getItem(int position) {
        return gitHubRepositories.get(position);
    }


    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_item, parent, false);
        return new RepositoryViewHolder(v, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        holder.titleTextView.setText(gitHubRepositories.get(position).getName());
        Glide.with(holder.avatarImageView.getContext())
                .load(gitHubRepositories.get(position).getOwner().getAvatarUrl())
                .fitCenter()
                .into(holder.avatarImageView);
    }

    @Override
    public int getItemCount() {
        return gitHubRepositories.size();
    }

    public void set(List<GitHubRepository> gitHubRepositories) {
        this.gitHubRepositories.clear();
        this.gitHubRepositories.addAll(gitHubRepositories);
        notifyDataSetChanged();
    }

    public static class RepositoryViewHolder extends ViewHolder {
        @NonNull
        public final ImageView avatarImageView;

        @NonNull
        public final TextView titleTextView;

        public RepositoryViewHolder(View view, OnClickListener onClickListener) {
            super(view);
            avatarImageView = view.findViewById(R.id.avatar_image_view);
            titleTextView = view.findViewById(R.id.repository_name);
            view.setOnClickListener(onClickListener);

        }
    }
}
