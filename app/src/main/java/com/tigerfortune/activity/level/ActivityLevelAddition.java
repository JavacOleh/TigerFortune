package com.tigerfortune.activity.level;

import static com.tigerfortune.dto.StaticData.*;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;

import com.tigerfortune.dto.tigr.Tigr;

public class ActivityLevelAddition {
    private ActivityLevel activityLevel;
    private Tigr tigr;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            if (isLeftPressed) {
                tigr.getTigrMovementHandler().moveLeft();
            } else if (isRightPressed) {
                tigr.getTigrMovementHandler().moveRight();
            }
            handler.postDelayed(this, moveInterval);
        }
    };

    public ActivityLevelAddition(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
        tigr = new Tigr(activityLevel.tigr, speed, animationDuration, jumpHeight, activityLevel);

        activityLevel.engine.addToRunnablArr(tigr.getTigrMovementHandler()::onFallUpdat);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        var constraintMain = activityLevel.constraintMain;

        constraintMain.setOnTouchListener((v, event) -> {
            float x = event.getX();
            float y = event.getY();
            float width = v.getWidth();
            float height = v.getHeight();

            float leftThreshold = rightAndLeftHitBoxes; // граница слева
            float rightThreshold = width - rightAndLeftHitBoxes; // граница справа

            var yChk = y < height / 1.5; // условие для прыжка (например, верхняя часть экрана)
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    // Обработка движения


                    if (x < leftThreshold) {
                        // Левая часть
                        if (!isLeftPressed) {
                            isLeftPressed = true;
                            isRightPressed = false;
                            handler.post(moveRunnable);
                        }
                    } else if (x > rightThreshold) {
                        // Правая часть
                        if (!isRightPressed) {
                            isRightPressed = true;
                            isLeftPressed = false;
                            handler.post(moveRunnable);
                        }
                    } else {
                        // Верхняя половина — прыжок
                        tigr.getTigrMovementHandler().jump(); // вызываем прыжок
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isLeftPressed = false;
                    isRightPressed = false;
                    handler.removeCallbacks(moveRunnable);
                    break;
            }
            return true;
        });
    }
}