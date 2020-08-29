package com.benmohammad.rearkapp.data.stores;

import io.reark.reark.data.stores.MemoryStore;
import io.reark.reark.pojo.NetworkRequestStatus;

public class NetworkRequestStatusStore extends MemoryStore<Integer, NetworkRequestStatus, NetworkRequestStatus> {

    public NetworkRequestStatusStore() {
        super(status -> status.getUri().hashCode(),
                status -> status == null ? NetworkRequestStatus.none() : status, NetworkRequestStatus::none);
    }
}
