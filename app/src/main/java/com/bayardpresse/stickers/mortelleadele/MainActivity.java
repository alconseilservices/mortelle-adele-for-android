package com.bayardpresse.stickers.mortelleadele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Batch.setConfig(new Config("DEV5DA07AF6957E135A8EEFD055ADE"));
        getApplication().registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
        startPacksListActivity();
    }

    private void startPacksListActivity() {
        Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
        this.startActivity(intent);
    }
}
