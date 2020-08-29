package com.benmohammad.rearkapp.data.stores;

import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;

import io.reark.reark.data.stores.MemoryStore;

public class GitHubRepositorySearchStore extends MemoryStore<String, GitHubRepositorySearch, GitHubRepositorySearch> {

    public GitHubRepositorySearchStore() {
        super(GitHubRepositorySearch::getSearch,
                search -> search != null ? search : GitHubRepositorySearch.none(),
                GitHubRepositorySearch::none);
    }
}
