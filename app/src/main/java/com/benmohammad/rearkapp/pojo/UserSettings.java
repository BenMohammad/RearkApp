package com.benmohammad.rearkapp.pojo;

import androidx.annotation.NonNull;

import java.util.Objects;

public class UserSettings {

    private final int selectedRepositoryId;

    public UserSettings(int selectedRepositoryId) {
        this.selectedRepositoryId = selectedRepositoryId;
    }

    @NonNull
    public static UserSettings none() {
        return new UserSettings(-1);
    }

    public boolean isSome() {
        return selectedRepositoryId != -1;
    }

    public boolean isNone() {
        return !isSome();
    }

    public int getSelectedRepositoryId() {
        return selectedRepositoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSettings that = (UserSettings) o;
        return selectedRepositoryId == that.selectedRepositoryId;
    }

    @Override
    public int hashCode() {
        return selectedRepositoryId;
    }
}
