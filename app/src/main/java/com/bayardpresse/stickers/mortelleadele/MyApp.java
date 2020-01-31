package com.bayardpresse.stickers.mortelleadele;

import android.app.Application;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;
import com.bayardpresse.android.R;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Batch.setConfig(new Config("DEV5DC15302BF169FA02064B6CBF57"));
        Batch.Push.setNotificationsColor(0xFF00FF00);
        Batch.Push.setSmallIconResourceId(R.drawable.ic_small_notif);
        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
    }
}
