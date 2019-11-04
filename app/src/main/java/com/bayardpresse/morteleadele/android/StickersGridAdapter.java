package com.bayardpresse.morteleadele.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bayardpresse.morteleadele.android.model.Pack;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class StickersGridAdapter extends BaseAdapter {

    private Context context;
    private Pack pack;

    public StickersGridAdapter(Context context, Pack pack) {
        super();
        this.context = context;
        this.pack = pack;
    }

    @Override
    public int getCount() {
        return pack.stickersNames.length;
    }

    @Override
    public Object getItem(int position) {
        return pack.stickersNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;
        int resID = context.getResources().getIdentifier(pack.stickersNames[position].toLowerCase(),
                "drawable", context.getPackageName());

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setAdjustViewBounds(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ImageView iv = (ImageView) v;
                        Context ctx = v.getContext();
                        InputStream ais = context.getAssets().open("1/objet_001_512px.webp");
                        File targetFile = new File(context.getCacheDir(), "objet_001_512px.webp");
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
                }
            });
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(resID);
        imageView.setTag(resID);
        return imageView;
    }
}
