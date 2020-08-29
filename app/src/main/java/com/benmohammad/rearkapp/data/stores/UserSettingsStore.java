package com.benmohammad.rearkapp.data.stores;

import com.benmohammad.rearkapp.pojo.UserSettings;

import io.reark.reark.data.stores.MemoryStore;

public class UserSettingsStore extends MemoryStore<Integer, UserSettings, UserSettings> {

    public UserSettingsStore(final int userId) {
        super(__ -> userId,
                user -> user != null ? user : UserSettings.none(),UserSettings::none);
    }
}
