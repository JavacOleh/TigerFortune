package com.tigerfortune.dto.tigr;

import android.widget.ImageView;

import com.tigerfortune.activity.level.ActivityLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class Tigr {
    ImageView view;
    double speed;
    int animationDuration;
    int jumpHeight;
    ActivityLevel activityLevel;
    TigrMovementHandler tigrMovementHandler;

    public Tigr(ImageView view, double speed, int animationDuration, int jumpHeight, ActivityLevel activityLevel) {
        this.view = view;
        this.speed = speed;
        this.animationDuration = animationDuration;
        this.jumpHeight = jumpHeight;
        this.activityLevel = activityLevel;
        tigrMovementHandler = new TigrMovementHandler(this);
    }

    public float getPosXmin() {
        return view.getX() + tigrMovementHandler.getMarginStart();
    }

    public float getPosXmax() {
        return view.getX() + view.getWidth() - tigrMovementHandler.getMarginEnd();
    }

    public float getPosYmin() {
        return view.getY() + tigrMovementHandler.getMarginTop();
    }

    public float getPosYmax() {
        return view.getY() + view.getHeight() - tigrMovementHandler.getMarginBottom();
    }

    public void scrollTo(int x) {
        activityLevel.gameScroller.post(() -> {
            // Прокрутить на определённое количество пикселей (по оси X)
            activityLevel.gameScroller.scrollTo(x, 0); // Например, прокрутить на 1000 пикселей вправо
        });
    }
}
