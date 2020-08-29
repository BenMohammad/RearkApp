package com.benmohammad.rearkapp.pojo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

import io.reark.reark.pojo.OverwritablePojo;

public class GitHubRepository extends OverwritablePojo<GitHubRepository> {

    private static final String TAG = GitHubRepository.class.getSimpleName();
    private final int id;

    @NonNull
    private String name;

    @SerializedName("stargazers_count")
    private int stargazerCount;

    @SerializedName("forks_count")
    private int forksCount;

    @NonNull
    @SerializedName("owner")
    private GitHubOwner owner;


    public GitHubRepository(int id,
                            @NonNull final String name,
                            int stargazersCount,
                            int forksCount,
                            @NonNull final GitHubOwner owner) {
        this.id = id;
        this.name = name;
        this.stargazerCount = stargazersCount;
        this.forksCount = forksCount;
        this.owner = owner;
    }

    @NonNull
    public static GitHubRepository none() {
        return new GitHubRepository(-1, "", -1, -1, GitHubOwner.empty());
    }

    public boolean isSome() {
        return id != -1;
    }

    @NonNull
    @Override
    protected Class<GitHubRepository> getTypeParameterClass() {
        return GitHubRepository.class;
    }

    @Override
    protected boolean isEmpty(@NonNull Field field, @NonNull OverwritablePojo<GitHubRepository> pojo) {
        try {
            if(field.get(pojo) instanceof GitHubOwner) {
                return false;
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Failed to get at:" + field.getName(), e);
        }
        return super.isEmpty(field, pojo);
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getStargazerCount() {
        return stargazerCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    @NonNull
    public GitHubOwner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "GitHubRepository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stargazerCount=" + stargazerCount +
                ", forksCount=" + forksCount +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GitHubRepository that = (GitHubRepository) o;
        if(id != that.id) return false;
        if(stargazerCount != that.stargazerCount) return false;
        if(forksCount != that.forksCount) return false;
        if(!name.equals(that.name)) return false;
        return owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + stargazerCount;
        result = 31 * result + forksCount;
        result = 31 * result + owner.hashCode();
        return result;
    }
}
