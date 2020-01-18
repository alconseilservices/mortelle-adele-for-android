package com.bayardpresse.stickers.mortelleadele;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bayardpresse.android.BuildConfig;
import com.bayardpresse.stickers.mortelleadele.model.Sticker;
import com.bayardpresse.stickers.mortelleadele.model.StickerPack;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StickersGridAdapter extends BaseAdapter {

    private static FirebaseAnalytics mFirebaseAnalytics;
    private Context context;
    private StickerPack pack;

    public StickersGridAdapter(Context context, StickerPack pack) {
        super();
        this.context = context;
        this.pack = pack;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public int getCount() {
        return pack.getStickers().size();
    }

    @Override
    public Object getItem(int position) {
        return pack.getStickers().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ImageView imageView = null;
        try {
            Sticker sticker = pack.getStickers().get(position);
            Drawable drawable = Drawable.createFromStream(context.getAssets().open(pack.identifier + "/" + sticker.imageFileName), sticker.imageFileName.replace(".webp", ""));
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setAdjustViewBounds(true);
                imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        try {
                            ImageView iv = (ImageView) v;
                            Context ctx = v.getContext();

                            AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                            animation1.setDuration(3000);
                            animation1.setStartOffset(50);
                            animation1.setFillAfter(true);
                            iv.startAnimation(animation1);

                            Sticker _sticker = (Sticker) iv.getTag();
                            InputStream ais = context.getAssets().open(pack.identifier + "/" + _sticker.imageFileName.replace("webp", "png"));
                            File targetFile = new File(context.getCacheDir(), _sticker.imageFileName.replace("webp", "png"));
                            FileUtils.copyInputStreamToFile(ais, targetFile);
                            Uri uri = FileProvider.getUriForFile(context, BuildConfig.FILE_PROVIDER_AUTHORITY, targetFile);
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("image/png");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Intent chooserIntent = Intent.createChooser(shareIntent, "Partager le sticker");
                            chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            Bundle bundle = new Bundle();
                            bundle.putString("title", "pack : #" + pack.name + "# - #" + _sticker.imageFileName + "#");
                            bundle.putString("action", "clic : partage sticker");
                            mFirebaseAnalytics.logEvent("partage", bundle);

                            ctx.startActivity(chooserIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageDrawable(drawable);
            imageView.setTag(sticker);
            return imageView;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageView;
    }
}
