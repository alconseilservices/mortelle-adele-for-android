package com.bayardpresse.morteleadele.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bayardpresse.morteleadele.android.model.Sticker;
import com.bayardpresse.morteleadele.android.model.StickerPack;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StickersGridAdapter extends BaseAdapter {

    private Context context;
    private StickerPack pack;

    public StickersGridAdapter(Context context, StickerPack pack) {
        super();
        this.context = context;
        this.pack = pack;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
                            shareIntent.setType("image/*");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Intent chooserIntent = Intent.createChooser(shareIntent, "Partager le sticker");
                            chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
