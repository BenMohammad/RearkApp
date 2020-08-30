package com.benmohammad.rearkapp.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static io.reark.reark.utils.Preconditions.get;

public class GitHubOwner {

    @SerializedName("avatar_url")
    @NonNull
    private final String avatarUrl;

    public GitHubOwner(@NonNull final String avatarUrl) {
        this.avatarUrl = get(avatarUrl);
    }

    @NonNull
    public static GitHubOwner empty() {
        return new GitHubOwner("");
    }

    public boolean isSome() {
        return !avatarUrl.isEmpty();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String toString() {
        return "GitHubOwner{" +
                "avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubOwner that = (GitHubOwner) o;
        return avatarUrl.equals(that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return avatarUrl.hashCode();
    }
}
