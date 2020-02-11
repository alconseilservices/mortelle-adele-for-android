package com.bayardpresse.stickers.mortelleadele;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bayardpresse.android.R;
import com.bayardpresse.stickers.mortelleadele.model.PackStore;
import com.bayardpresse.stickers.mortelleadele.model.StickerPack;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private static FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());
        mFirebaseAnalytics.setCurrentScreen(this, "PacksList", null);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Batch.onNewIntent(this, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stickers_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_cgu_action) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(WebViewActivity.ARG_URL, "http://applications-enfants.bayam.fr/page/cgu-stickers-mortelle-adele-application.html");
            intent.putExtra(WebViewActivity.ARG_TITLE, "CGU");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("title", "CGU");
            bundle.putString("action", "clic : onglet");
            mFirebaseAnalytics.logEvent("navigation", bundle);
            context.startActivity(intent);
            return true;
        } else if (id == R.id.menu_item_mortelleadele_action) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(WebViewActivity.ARG_URL, "https://www.mortelleadele.com/");
            intent.putExtra(WebViewActivity.ARG_TITLE, getResources().getString(R.string.sticker_detail_menu_item_site_mortelle_adele));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("title", "Site Web");
            bundle.putString("action", "clic : onglet");
            mFirebaseAnalytics.logEvent("navigation", bundle);
            context.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, PackStore.fetchPacks(this.getApplicationContext())));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<StickerPack> mValues;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StickerPack item = (StickerPack) view.getTag();
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.identifier);

                Bundle bundle = new Bundle();
                bundle.putString("title", "pack : " + item.name);
                bundle.putString("action", "clic : acces pack");
                mFirebaseAnalytics.logEvent("navigation", bundle);
                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<StickerPack> items) {
            mValues = items;
            mParentActivity = parent;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Context ctx = mParentActivity.getApplicationContext();
            StickerPack pack = mValues.get(position);
            try {
                Drawable drawable = Drawable.createFromStream(
                        ctx.getAssets().open(pack.identifier + "/" + pack.getStickers().get(0).imageFileName),
                        pack.getStickers().get(0).imageFileName);
                holder.mPackLogoView.setImageDrawable(drawable);
                holder.mPackNameView.setText(pack.name);
                holder.mPackSizeView.setText("" + (mValues.get(position).getTotalSize()/1024) + " kB");
                holder.itemView.setTag(mValues.get(position));
                holder.itemView.setOnClickListener(mOnClickListener);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mPackLogoView;
            final TextView mPackNameView;
            final TextView mPackSizeView;

            ViewHolder(View view) {
                super(view);
                mPackLogoView = (ImageView) view.findViewById(R.id.pack_logo);
                mPackNameView = (TextView) view.findViewById(R.id.pack_name);
                mPackSizeView = (TextView) view.findViewById(R.id.pack_size);
            }
        }
    }
}
