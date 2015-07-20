package com.citu.faucet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * Created by metalgear8019 on 7/17/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
        initAssets();
    }

    public int getCount() {
        return FaucetGrid.count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void initAssets() {
        FaucetGrid.count = 16;
        FaucetGrid.setFaucetGridSize(FaucetGrid.count);
        for (int i = 0; i < FaucetGrid.count; i++)
        {
            FaucetGrid.faucetStates[i] = false;
            FaucetGrid.updateFaucetState(i);
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(FaucetGrid.faucets[position]);
        return imageView;
    }
}
