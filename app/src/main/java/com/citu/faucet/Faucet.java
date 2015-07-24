package com.citu.faucet;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by metalgear8019 on 7/20/2015.
 */
public class Faucet {
    public static TextView hud; // scores heads-up display
    public static ImageView[] ivList; // list of ImageView references
    public static boolean[] faucetStates; // true if faucet is open
    public static Integer[] resources; // references to image resources
    public static int gridSize; // size of grid in one dimension (grid is square)
    public static int count; // number of elements, grid size x grid size
    public static int level; // (value - 1) * 20 + 100 = totalScore
    public static int totalScore; // winning condition per level
    private static int currentScore; // game over if 0 or negative
    private static int openFaucets = 0; // number of open faucets
    private static int generatedPos; // randomized position

    public static int getScore() { return currentScore; }

    public static boolean replenish() {
        boolean hasWon = false;
        currentScore += (openFaucets + 1) * level;
        if (currentScore > totalScore) {
            currentScore = totalScore;
            hasWon = true;
        }
        updateHUD();
        return hasWon;
    }

    public static boolean drain() {
        boolean hasLost = false;
        currentScore -= openFaucets * level;
        if (currentScore <= 0) {
            currentScore = 0;
            hasLost = true;
        }
        updateHUD();
        return hasLost;
    }

    public static void updateHUD() {
        hud.setText("Score: " + currentScore + "/" + totalScore);
        hud.invalidate();
    }

    public static void generatePos() {
        generatedPos = (int) (Math.random() * count);
    }

    public static int getGeneratedPos() {
        return generatedPos;
    }

    public static int getNumOpenFaucets() {
        return openFaucets;
    }

    public static void setFaucetGridSize(int size) {
        ivList = new ImageView[size];
        faucetStates = new boolean[size];
        resources = new Integer[size];
    }

    public static void openFaucetState(int position) {
        if(!faucetStates[position])
            reverseFaucetState(position);
    }

    public static void reverseFaucetState(int position) {
        faucetStates[position] = !faucetStates[position];
        if (faucetStates[position])
            openFaucets++;
        else
            openFaucets--;
    }

    public static int getResourceAt(int position) {
        if (faucetStates[position])
            return R.drawable.faucet_animation;
        else
            return R.mipmap.ic_faucet;
    }

    public static void updateFaucetState(int position) {
        if (faucetStates[position]) {
            resources[position] = R.drawable.faucet_animation;
            ivList[position].setBackgroundResource(resources[position]);
            ((AnimationDrawable) ivList[position].getBackground()).start();
        }
        else {
            if((null != ivList[position].getBackground()))
                ((AnimationDrawable) ivList[position].getBackground()).stop();
            resources[position] = R.mipmap.ic_faucet;
            ivList[position].setBackgroundResource(resources[position]);
        }
        ivList[position].invalidate();
    }

    public static void initAssets(Context mContext, int gridSize, int level) {
        Faucet.level = level;
        Faucet.gridSize = gridSize;
        Faucet.count = gridSize * gridSize;
        Faucet.totalScore = 100 + (level - 1) * 20;
        Faucet.currentScore = (int) (totalScore * 0.2);
        Faucet.setFaucetGridSize(Faucet.count);
        for (int i = 0; i < Faucet.count; i++)
        {
            Faucet.faucetStates[i] = false;
            Faucet.ivList[i] = new ImageView(mContext);
            Faucet.updateFaucetState(i);
        }
    }

    public static int randomSleepTime(int baseDuration) {
        int bias = count - openFaucets;
        return (int) (Math.floor((Math.random() * baseDuration)/bias + (baseDuration/bias)));
    }
}
