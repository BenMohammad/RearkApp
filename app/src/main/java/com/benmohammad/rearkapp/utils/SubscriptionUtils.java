package com.benmohammad.rearkapp.utils;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static io.reark.reark.utils.Preconditions.checkNotNull;

public final class SubscriptionUtils {

    private SubscriptionUtils(){}

    @NonNull
    public static Disposable subscribeTextViewText(@NonNull final Observable<String> observable,
                                                   @NonNull final TextView textView) {
        return subscribeTextViewText(observable, textView, AndroidSchedulers.mainThread());
    }
    @NonNull
    public static Disposable subscribeTextViewText(@NonNull final Observable<String> observable,
                                                   @NonNull final TextView textView,
                                                   @NonNull final Scheduler scheduler) {
        checkNotNull(observable);
        checkNotNull(textView);
        checkNotNull(scheduler);

        return observable
                .observeOn(scheduler)
                .subscribe(textView::setText,
                        error -> {
                    textView.setText(error.toString());
                    textView.setBackgroundColor(Color.RED);
                        });
    }
}
