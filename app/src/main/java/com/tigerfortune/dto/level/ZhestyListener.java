package com.tigerfortune.dto.level;

import static com.tigerfortune.dto.StaticData.*;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;

import com.tigerfortune.activity.LevelActivity;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.dto.tigr.TigrAnimatorHandler;
import com.tigerfortune.engine.addition.end.TigerExitByPortalAddition;

public class ZhestyListener {
    private LevelActivity levelActivity;
    public TigrAnimatorHandler animatorHandler;
    public Tiger tiger;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private final Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            if (isLeftPressed) {
                tiger.tigerMovementHandler.moveLeft();
                TigerExitByPortalAddition.getInstance(tiger).run();
                animatorHandler.onAnimate("left");
            } else if (isRightPressed) {
                tiger.tigerMovementHandler.moveRight();
                TigerExitByPortalAddition.getInstance(tiger).run();
                animatorHandler.onAnimate("right");
            }

            mainThread.postDelayed(this, moveInterval);
        }
    };

    public ZhestyListener(LevelActivity levelActivity) {
        this.levelActivity = levelActivity;
        tiger = new Tiger(levelActivity.tiger, speed, animationDuration, jumpHeight, levelActivity);
        animatorHandler = new TigrAnimatorHandler(tiger);
        levelActivity.engine.addToRunnablArr(tiger.tigerAddition::onUpdate);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        var constraintMain = levelActivity.constraintMain;

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
                            mainThread.post(moveRunnable);
                        }
                    } else if (x > rightThreshold) {
                        // Правая часть
                        if (!isRightPressed) {
                            isRightPressed = true;
                            isLeftPressed = false;
                            mainThread.post(moveRunnable);
                        }
                    } else {
                        // Верхняя половина — прыжок
                        tiger.tigerMovementHandler.jump();
                        animatorHandler.onAnimate("jump");
//                        if(allowedToJump) {
//                            tiger.tigerMovementHandler.jump(); // вызываем прыжок
//                            tiger.tigerMovementHandler.isOnGround = false;
//                            tiger.tigerMovementHandler.isJumping = true;
//
//                            allowedToJump = false;
//
//                            new Handler(Looper.getMainLooper()).postDelayed(() -> allowedToJump = true, fallDuration);
//                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isLeftPressed = false;
                    isRightPressed = false;
                    mainThread.removeCallbacks(moveRunnable);
                    break;
            }
            return true;
        });
    }
}