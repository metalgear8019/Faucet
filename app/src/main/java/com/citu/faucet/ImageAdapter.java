package com.citu.faucet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by metalgear8019 on 7/17/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Faucet.count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Context getContext() { return mContext; }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int dimen = parent.getWidth()/Faucet.gridSize;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setLayoutParams(new GridView.LayoutParams(dimen, dimen));
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        imageView.setBackgroundResource(Faucet.resources[position]);
        Faucet.ivList[position] = imageView;
        return imageView;
    }
}
