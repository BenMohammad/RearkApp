package com.benmohammad.rearkapp.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class AbstractViewModel {

    private static final String TAG = AbstractViewModel.class.getSimpleName();

    @Nullable
    private CompositeDisposable compositeDisposable;

    public void subscribeToDataStore() {
        Log.v(TAG, "subscribe to DataStore");
        if(isSubscribed()) {
            compositeDisposable = new CompositeDisposable();
            subscribeToDataStoreInternal(compositeDisposable);
        }
    }

    public void dispose() {
        Log.v(TAG, "dispose");
        if(isSubscribed()) {
            Log.e(TAG, "Disposing without calling unsubscribeFromDataStore");

            unsubscribeFromDataStore();
        }
    }

    public abstract void subscribeToDataStoreInternal(@NonNull final CompositeDisposable compositeDisposable);

    public void unsubscribeFromDataStore() {
        Log.v(TAG, "unsubscribeToDataStore");

        if(isSubscribed()) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    private boolean isSubscribed() {
        return compositeDisposable != null;
    }
}
