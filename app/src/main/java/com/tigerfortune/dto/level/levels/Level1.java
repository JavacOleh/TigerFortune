package com.tigerfortune.dto.level.levels;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.tigerfortune.R;
import com.tigerfortune.dto.level.LevelHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Level1 implements Runnable {
    LevelHandler levelHandler;

    @Override
    public void run() {
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable drawable = levelHandler.activityLevel.getResources().getDrawable(R.drawable.kust);
        int widthPx = drawable.getIntrinsicWidth();
        int heightPx = drawable.getIntrinsicHeight();


        levelHandler.addDecorate(0, 0, widthPx / 2, heightPx/ 2, R.drawable.kust);

//        drawable = levelHandler.activityLevel.getResources().getDrawable(R.drawable.brick);
//        widthPx = drawable.getIntrinsicWidth();
//        heightPx = drawable.getIntrinsicHeight();
        levelHandler.addObstacle(400, 50, 300, 120, R.drawable.brick);

        levelHandler.activityLevel.groundCount = 10;
        levelHandler.activityLevel.groundItmWidthInDP = 100;
        levelHandler.activityLevel.groundItmBackgroundSRC = R.drawable.background_ground1_item;

        levelHandler.activityLevel.constraintMain.setBackgroundColor(Color.parseColor("#5B94FF"));
    }
}
