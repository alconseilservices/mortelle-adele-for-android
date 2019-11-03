package com.bayardpresse.morteleadele.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bayardpresse.morteleadele.android.model.Pack;

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
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(resID);
        return imageView;
    }
}
