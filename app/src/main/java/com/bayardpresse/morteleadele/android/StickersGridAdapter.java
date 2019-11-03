package com.bayardpresse.morteleadele.android;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bayardpresse.morteleadele.android.model.Pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
                        Bitmap stickerBmp = BitmapFactory.decodeResource(v.getContext().getResources(), (Integer) v.getTag());
                        ContextWrapper wrapper = new ContextWrapper(v.getContext());
                        File file = wrapper.getDir("images", Context.MODE_PRIVATE);
                        file = new File(file, "image.webp");
                        OutputStream stream = new FileOutputStream(file);
                        stickerBmp.compress(Bitmap.CompressFormat.WEBP, 100, stream);
                        stream.flush();
                        stream.close();
                        Uri fileUri = Uri.parse(file.getAbsolutePath());
                        FileInputStream fis = new FileInputStream(fileUri.getPath());
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        fis.close();
                        file = new File(v.getContext().getCacheDir() + "/thestickers.webp");
                        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, new FileOutputStream(file));
                        Uri contentUri = FileProvider.getUriForFile(v.getContext(), BuildConfig.FILE_PROVIDER_AUTHORITY, file);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        v.getContext().startActivity(Intent.createChooser(shareIntent, "Partager le sticker"));
                    } catch (java.io.IOException e) {
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
