package com.benmohammad.rearkapp.pojo;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.reark.reark.utils.Preconditions.get;

public class GitHubRepositorySearch {

    @NonNull
    private String search;

    @NonNull
    private List<Integer> items;

    public GitHubRepositorySearch(@NonNull final String search, @NonNull final List<Integer> items) {
        this.search = get(search);
        this.items = new ArrayList<>(items);
    }

    @NonNull
    public static GitHubRepositorySearch none() {
        return new GitHubRepositorySearch("", Collections.emptyList());
    }

    public boolean isSome() {
        return !search.isEmpty();
    }

    @NonNull
    public String getSearch() {
        return search;
    }

    @NonNull
    public List<Integer> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GitHubRepositorySearch that = (GitHubRepositorySearch) o;
        if(!search.equals(that.search)) return false;
        return items.equals(that.items);
    }


    @Override
    public int hashCode() {
        int result = search.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }
}
