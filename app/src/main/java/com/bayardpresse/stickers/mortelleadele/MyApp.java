package com.bayardpresse.stickers.mortelleadele;

import android.app.Application;
import android.graphics.BitmapFactory;

import com.batch.android.Batch;
import com.batch.android.BatchActivityLifecycleHelper;
import com.batch.android.Config;
import com.bayardpresse.android.R;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Batch.setConfig(new Config("DEV5DC15302BF169FA02064B6CBF57")); // dev
        // Batch.setConfig(new Config("5DC15302BEE52642400FB488F1459C")); // live
        Batch.Push.setNotificationsColor(0xFF00FF00);
        Batch.Push.setSmallIconResourceId(R.drawable.ic_small_notif);
        Batch.Push.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));
        registerActivityLifecycleCallbacks(new BatchActivityLifecycleHelper());
    }
}
