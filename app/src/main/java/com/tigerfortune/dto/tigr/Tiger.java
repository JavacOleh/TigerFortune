package com.tigerfortune.dto.tigr;

import android.widget.ImageView;

import com.tigerfortune.activity.LevelActivity;
import com.tigerfortune.engine.addition.TigerAddition;

public class Tiger {
    public ImageView view;
    public double speed;
    public int animationDuration;
    public int jumpHeight;
    public LevelActivity levelActivity;
    public TigerMovementHandler tigerMovementHandler;
    public TigerAddition tigerAddition;

    public Tiger(ImageView view, double speed, int animationDuration, int jumpHeight, LevelActivity levelActivity) {
        this.view = view;
        this.speed = speed;
        this.animationDuration = animationDuration;
        this.jumpHeight = jumpHeight;
        this.levelActivity = levelActivity;
        tigerAddition = new TigerAddition(this);
        tigerMovementHandler = new TigerMovementHandler(this);
    }

    public void scrollTo(int x) {
        levelActivity.gameScroller.post(() -> {
            // Прокрутить на определённое количество пикселей (по оси X)
            levelActivity.gameScroller.scrollTo(x, 0); // Например, прокрутить на 1000 пикселей вправо
        });
    }

}
