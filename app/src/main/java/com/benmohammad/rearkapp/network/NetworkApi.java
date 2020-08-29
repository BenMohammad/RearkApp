package com.benmohammad.rearkapp.network;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.pojo.GitHubRepository;
import com.benmohammad.rearkapp.pojo.GitHubRepositorySearchResults;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.reark.reark.utils.Preconditions.checkNotNull;

public class NetworkApi {

    @NonNull
    private final GitHubService gitHubService;

    public NetworkApi(@NonNull final OkHttpClient client) {
        checkNotNull(client, "client cannot be null");
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com")
                .client(client)
                .build();

        gitHubService = retrofit.create(GitHubService.class);
    }

    @NonNull
    public Single<List<GitHubRepository>> search(Map<String, String> search) {
        return gitHubService.search(search)
                .map(GitHubRepositorySearchResults::getItems);
    }

    @NonNull
    public Single<GitHubRepository> getRepository(int id) {
        return gitHubService.getRepository(id);
    }


}
