package com.tigerfortune.dto.level.levels;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.tigerfortune.R;
import com.tigerfortune.dto.level.LevelHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Level3 implements Runnable {
    LevelHandler levelHandler;

    @Override
    public void run() {
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable drawable = levelHandler.levelActivity.getResources().getDrawable(R.drawable.kust);
        int widthPx = drawable.getIntrinsicWidth();
        int heightPx = drawable.getIntrinsicHeight();


        levelHandler.addDecorate(0, 0, widthPx / 2, heightPx/ 2, R.drawable.kust);
        levelHandler.addDecorate(0, 0, widthPx / 2, heightPx/ 2, R.drawable.kust);

//        drawable = levelHandler.activityLevel.getResources().getDrawable(R.drawable.brick);
//        widthPx = drawable.getIntrinsicWidth();
//        heightPx = drawable.getIntrinsicHeight();
        levelHandler.addObstacle(300, 50, 300, 120, R.drawable.brick);
        levelHandler.addObstacle(430, 100, 300, 120, R.drawable.brick);
        levelHandler.addObstacle(560, 150, 300, 120, R.drawable.brick);
        levelHandler.addObstacle(680, 200, 300, 120, R.drawable.brick);

        levelHandler.addEbnutsaGolovojEl(780, 200, 300, 120);

        Log.i("TopPositions", String.valueOf(levelHandler.getPositionsByType("obstacles").size()));


        levelHandler.levelActivity.groundCount = 13;
        levelHandler.levelActivity.groundItmWidthInDP = 100;
        levelHandler.levelActivity.groundItmBackgroundSRC = R.drawable.background_ground1_item;

        levelHandler.levelActivity.constraintMain.setBackgroundColor(Color.parseColor("#5B94FF"));
    }
}
