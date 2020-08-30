package com.benmohammad.rearkapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.benmohammad.rearkapp.data.DataFunctions;
import com.benmohammad.rearkapp.fragments.RepositoryFragment;
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

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RepositoryFragment())
                    .commit();
        }
    }

    public void chooseRepository() {
        Log.d(TAG, "chooseRepository");
        Intent intent = new Intent(this, ChooseRepositoryActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult");
        if (data == null) {
            Log.d(TAG, "no data from ActivityResult");
            return;
        }
        final int repositoryId = data.getIntExtra("repositoryId", 0);
        if (repositoryId == 0) {
            Log.e(TAG, "Invalid repositoryId from onActivityResult");
            return;
        }
        Log.d(TAG, "New RepositoryId: " + repositoryId);
        setUserSettings.call(new UserSettings(repositoryId));
    }
}