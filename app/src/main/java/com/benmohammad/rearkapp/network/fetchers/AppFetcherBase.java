package com.benmohammad.rearkapp.network.fetchers;

import androidx.annotation.NonNull;

import com.benmohammad.rearkapp.network.NetworkApi;

import io.reactivex.functions.Consumer;
import io.reark.reark.network.fetchers.FetcherBase;
import io.reark.reark.pojo.NetworkRequestStatus;

import static io.reark.reark.utils.Preconditions.get;

public abstract class AppFetcherBase<T> extends FetcherBase<T> {

    @NonNull
    private final NetworkApi networkApi;

    protected AppFetcherBase(@NonNull final NetworkApi networkApi,
                             @NonNull final Consumer<NetworkRequestStatus> updateNetworkRequestStatus) {
        super(updateNetworkRequestStatus);

        this.networkApi = get(networkApi);
    }

    @NonNull
    public NetworkApi getNetworkApi() {
        return networkApi;
    }
}
