package com.benmohammad.rearkapp.network;

import android.net.Uri;

import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearch;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearchResults;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GitHubService {

    Uri REPOSITORY_SEARCH = Uri.parse("github/search");
    Uri REPOSITORY = Uri.parse("github/repository");

    @GET("/search/repositories")
    Single<GitHubRepositorySearchResults> search(@QueryMap Map<String, String> search);

    @GET("/repositories/{id}")
    Single<GitHubRepository> getRepository(@Path("id") Integer id);
}
