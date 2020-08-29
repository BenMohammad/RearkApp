package com.benmohammad.rearkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.benmohammad.rearkapp.data.DataFunctions;
import com.benmohammad.rearkapp.pojo.UserSettings;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_REPOSITORY_ID = 154919874;

    @Inject
    DataFunctions.SetUserSettings setUserSettings;

    public MainActivity() {
        RxGitHubApp.getInstance().getGraph().inject(this);

        setUserSettings.call(new UserSettings(DEFAULT_REPOSITORY_ID));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}