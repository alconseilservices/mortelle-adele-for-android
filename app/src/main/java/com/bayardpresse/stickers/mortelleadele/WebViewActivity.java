package com.bayardpresse.stickers.mortelleadele;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.batch.android.Batch;
import com.bayardpresse.android.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class WebViewActivity extends AppCompatActivity {

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static final String ARG_URL = "url";
    public static final String ARG_TITLE = "title";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Batch.onNewIntent(this, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        mFirebaseAnalytics.setCurrentScreen(this, "CGU", null);

        setTitle(getIntent().getStringExtra(ARG_TITLE));

        Toolbar toolbar = findViewById(R.id.cgu_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        WebView wv = findViewById(R.id.wbv_browser);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.loadUrl(getIntent().getStringExtra(ARG_URL));
    }
}
