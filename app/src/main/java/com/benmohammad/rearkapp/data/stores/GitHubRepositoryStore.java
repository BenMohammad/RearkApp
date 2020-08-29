package com.benmohammad.rearkapp.data.stores;

import com.benmohammad.rearkapp.pojo.GitHubRepository;

import io.reark.reark.data.stores.MemoryStore;

public class GitHubRepositoryStore extends MemoryStore<Integer, GitHubRepository, GitHubRepository> {

    public GitHubRepositoryStore() {
        super((oldItem, newItem) -> new GitHubRepository(oldItem).overwrite(newItem),
                GitHubRepository::getId,
                repository -> repository != null ? repository : GitHubRepository.none(),
                GitHubRepository::none);
    }
}
