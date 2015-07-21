package com.citu.faucet;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by metalgear8019 on 7/20/2015.
 */
public class FaucetGrid {
    public static ImageView[] ivList;
    public static boolean[] faucetStates; // true if faucet is open
    public static Integer[] faucets; // references to images
    public static int gridSize; // size of grid in one dimension (grid is square)
    public static int count; // number of elements, grid size x grid size

    public static void setFaucetGridSize(int size) {
        ivList = new ImageView[size];
        faucetStates = new boolean[size];
        faucets = new Integer[size];
    }

    public static void reverseFaucetState(int position) {
        faucetStates[position] = !faucetStates[position];
    }

    public static int getResourceAt(int position) {
        if (faucetStates[position])
            return R.mipmap.test;
        else
            return R.mipmap.faucet;
    }

    public static void updateFaucetState(int position) {
        if (faucetStates[position])
            faucets[position] = R.mipmap.test;
        else
            faucets[position] = R.mipmap.faucet;
    }

    public static void initAssets(Context mContext, int gridSize) {
        FaucetGrid.gridSize = gridSize;
        FaucetGrid.count = gridSize * gridSize;
        FaucetGrid.setFaucetGridSize(FaucetGrid.count);
        for (int i = 0; i < FaucetGrid.count; i++)
        {
            FaucetGrid.faucetStates[i] = false;
            FaucetGrid.updateFaucetState(i);
            FaucetGrid.ivList[i] = new ImageView(mContext);
        }
    }
}
