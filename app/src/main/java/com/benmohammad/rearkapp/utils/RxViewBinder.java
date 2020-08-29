package com.benmohammad.rearkapp.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class RxViewBinder {

    @Nullable
    private CompositeDisposable compositeDisposable;

    public void bind() {
        unbind();
        compositeDisposable = new CompositeDisposable();
        bindInternal(compositeDisposable);
    }

    public void unbind() {
        if(compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    protected abstract void bindInternal(@NonNull final CompositeDisposable compositeDisposable);
}
