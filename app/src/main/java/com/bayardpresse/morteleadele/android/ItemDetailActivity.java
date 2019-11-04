package com.bayardpresse.morteleadele.android;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import com.bayardpresse.morteleadele.android.model.PackStore;
import com.bayardpresse.morteleadele.android.model.StickerPack;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class ItemDetailActivity extends AppCompatActivity {

    private static final int ADD_PACK = 200;
    static final String CONSUMER_WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    public static final String EXTRA_STICKER_PACK_ID = "sticker_pack_id";
    public static final String EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority";
    public static final String EXTRA_STICKER_PACK_NAME = "sticker_pack_name";

    private StickerPack pack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pack = PackStore.getPackById(getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
        setContentView(R.layout.activity_item_detail);

        setTitle(pack.name);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        GridView stickersGrid = findViewById(R.id.stickers_grid);
        stickersGrid.setAdapter(new StickersGridAdapter(getBaseContext(), pack));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stickers_detail_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_addwas_action) {
            if (!WhitelistCheck.isWhatsAppConsumerAppInstalled(getPackageManager())) {
                Toast.makeText(this, R.string.whatspp_not_installed, Toast.LENGTH_LONG).show();
                return false;
            }
            Intent intent = new Intent();
            intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
            intent.putExtra(EXTRA_STICKER_PACK_ID, pack.identifier);
            intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
            intent.putExtra(EXTRA_STICKER_PACK_NAME, pack.name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage(CONSUMER_WHATSAPP_PACKAGE_NAME);
            try {
                startActivityForResult(intent, ADD_PACK);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Oups !!!", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
