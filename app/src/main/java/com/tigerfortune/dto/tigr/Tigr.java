package com.tigerfortune.dto.tigr;

import android.widget.ImageView;

import com.tigerfortune.activity.ActivityLevel;
import com.tigerfortune.engine.TigrAdditionToEngine;

import lombok.Getter;

@Getter
public class Tigr {
    public ImageView view;
    public double speed;
    public int animationDuration;
    public int jumpHeight;
    public ActivityLevel activityLevel;
    public TigrMovementHandler tigrMovementHandler;
    public TigrAdditionToEngine tigrAdditionToEngine;

    public Tigr(ImageView view, double speed, int animationDuration, int jumpHeight, ActivityLevel activityLevel) {
        this.view = view;
        this.speed = speed;
        this.animationDuration = animationDuration;
        this.jumpHeight = jumpHeight;
        this.activityLevel = activityLevel;
        tigrAdditionToEngine = new TigrAdditionToEngine(this);
        tigrMovementHandler = new TigrMovementHandler(this);
    }

    public void scrollTo(int x) {
        activityLevel.gameScroller.post(() -> {
            // Прокрутить на определённое количество пикселей (по оси X)
            activityLevel.gameScroller.scrollTo(x, 0); // Например, прокрутить на 1000 пикселей вправо
        });
    }

}
