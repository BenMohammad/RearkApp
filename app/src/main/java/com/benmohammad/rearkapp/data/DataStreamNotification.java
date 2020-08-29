package com.benmohammad.rearkapp.data;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static io.reark.reark.utils.Preconditions.get;

public final class DataStreamNotification<T> {

    public enum Type {
        ONGOING,
        ON_NEXT,
        COMPLETED_WITH_VALUE,
        COMPLETED_WITHOUT_VALUE,
        COMPLETED_WITH_ERROR
    }

    @NonNull
    private final Type type;

    @Nullable
    private final T value;

    @Nullable
    private final String error;

    private DataStreamNotification(@NonNull Type type,
                                   @Nullable T value,
                                   @Nullable String error) {
        this.type = get(type);
        this.value = value;
        this.error = error;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Nullable
    public String getError() {
        return error;
    }

    @NonNull
    public static<T> DataStreamNotification<T> onNext(T value) {
        return new DataStreamNotification<>(Type.ON_NEXT, value, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> ongoing() {
        return new DataStreamNotification<>(Type.ONGOING, null, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> completedWithValue() {
        return new DataStreamNotification<>(Type.COMPLETED_WITH_VALUE, null, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> completedWithoutValue() {
        return new DataStreamNotification<>(Type.COMPLETED_WITHOUT_VALUE, null, null);
    }

    @NonNull
    public static<T> DataStreamNotification<T> completedWithError(@Nullable String error) {
        return new DataStreamNotification<>(Type.COMPLETED_WITH_ERROR, null, error);
    }

    public boolean isOnGoing() {
        return type == Type.ONGOING;
    }

    public boolean isOnNext() {
        return type == Type.ON_NEXT;
    }

    public boolean isCompletedWithValue() {
        return type == Type.COMPLETED_WITH_VALUE;
    }

    public boolean isCompletedWithoutValue() {
        return type == Type.COMPLETED_WITHOUT_VALUE;
    }

    public boolean isCompletedWithError() {
        return type == Type.COMPLETED_WITH_ERROR;
    }

    public boolean isCompletedWithSuccess() {
        return isCompletedWithValue() || isCompletedWithoutValue();
    }

    public boolean isCompleted() {
        return isCompletedWithSuccess() || isCompletedWithError();
    }
}
