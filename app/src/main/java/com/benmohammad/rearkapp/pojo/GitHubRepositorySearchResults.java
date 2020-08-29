package com.benmohammad.rearkapp.pojo;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GitHubRepositorySearchResults {

    @NonNull
    private List<GitHubRepository> items;

    public GitHubRepositorySearchResults(@NonNull final List<GitHubRepository> items) {
        this.items = new ArrayList<>(items);
    }

    @NonNull
    public List<GitHubRepository> getItems() {
        return Collections.unmodifiableList(items);
    }
}
