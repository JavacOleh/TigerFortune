package com.tigerfortune.engine;

import static com.tigerfortune.dto.StaticData.finalSpeed;
import static com.tigerfortune.dto.StaticData.groundPos;
import static com.tigerfortune.dto.StaticData.speed;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.tigr.Tigr;
import com.tigerfortune.other.util.DoOnce;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: 05.08.2025, 6:54 криво но работает: onMoveRightAndLeftUpdate
public class TigrAdditionToEngine {
    Tigr tiger;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private static DoOnce doJumpOnce = new DoOnce();
    private static DoOnce doFallOnce = new DoOnce();

    //onFallUpdate
    public static int pogreshnostHorL = 200;
    public static int pogreshnostHorR = 0;

    //onMoveRightAndLeftUpdate
    public static int pogreshnostXl = 0;
    public static int pogreshnostXr = pogreshnostHorL / 2 * -1;
    public static int pogreshnostY = 5;

    public TigrAdditionToEngine(Tigr tiger) {
        this.tiger = tiger;
    }
    private void onFallUpdate(View view) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        ViewGroup.MarginLayoutParams paramsV = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        int marginStart = params.leftMargin;
        var y = params.bottomMargin;
        var top = paramsV.bottomMargin + view.getHeight();


        int tigerWidth = tiger.view.getWidth();
        var isOnPlatformHorzintollay =
                (marginStart <= paramsV.leftMargin + view.getWidth() - tigerWidth + pogreshnostHorL) //Левая граница
                        &&
                        (marginStart + tigerWidth >= paramsV.leftMargin + pogreshnostHorR); //Правая граница
        var isOnPlatformVertically = y >= top;

        if (isOnPlatformVertically && isOnPlatformHorzintollay) {
            //up aminator and do codе bеlow onAnimationЕnd
//            doJumpOnce.actionOnce(() -> {
//                StaticData.groundPos = top;
//
//                handler.post(() -> {
//                    if (tiger.tigrMovementHandler.isOnGround)
//                        tiger.getTigrMovementHandler().jump();
//                });
//            });
            StaticData.groundPos = top;
            handler.post(() -> {
                params.bottomMargin = groundPos;
                tiger.view.setLayoutParams(params);
            });

            doFallOnce.hasDon = false;
        } else if (!isOnPlatformHorzintollay) {
            StaticData.groundPos = 0;
            if (y != groundPos) {

                //down aminator and do codе bеlow onAnimationЕnd
//                doFallOnce.actionOnce(() -> {
//                    handler.post(() -> {
//                        params.bottomMargin = groundPos;
//                        tiger.view.setLayoutParams(params);
//                    });
//                });
                handler.post(() -> {
                    params.bottomMargin = groundPos;
                    tiger.view.setLayoutParams(params);
                });
                doJumpOnce.hasDon = false;
            }
        }
    }
    private void onMoveRightAndLeftUpdate(View view) {
        //Тигр
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        var tigrRendX = params.leftMargin + tiger.view.getWidth();
        var tigrStartX = params.leftMargin;

        var tigrStartY = params.bottomMargin;
        var tigrRendY = params.bottomMargin + tiger.view.getHeight();

        //Вьюшка
        ViewGroup.MarginLayoutParams paramsV = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        var viewStartY = paramsV.bottomMargin;
        var viewWendY = paramsV.bottomMargin + view.getHeight();

        var viewStartX = paramsV.leftMargin;
        var vieWendX = paramsV.leftMargin + view.getWidth();

        boolean isClosHorizontallyFromLeft = tigrRendX >= viewStartX && tigrRendX <= vieWendX + pogreshnostXl;
        boolean isClosHorizontallyFromRight = tigrStartX >= viewStartX && tigrStartX <= vieWendX + pogreshnostXr;
        boolean isUnderObstache = tigrRendY < viewStartY;
        boolean isOnObstache = tigrStartY >= viewStartY;


        if (isClosHorizontallyFromLeft) {
            StaticData.isCloseToObstacheByRight = !isUnderObstache && !isOnObstache;
            setSpeed(StaticData.isCloseToObstacheByRight);
        } else {
            StaticData.isCloseToObstacheByRight = false;
            setSpeed(StaticData.isCloseToObstacheByRight);
        }

        if (isClosHorizontallyFromRight) {
            StaticData.isCloseToObstacheByLeft = !isUnderObstache && !isOnObstache;
            setSpeed(StaticData.isCloseToObstacheByLeft);
        } else {
            StaticData.isCloseToObstacheByLeft = false;
            setSpeed(StaticData.isCloseToObstacheByLeft);
        }
    }

    private static void setSpeed(boolean a) {
        if (a)
            speed = 0;
        else
            speed = finalSpeed;
    }


    public void onUpdate() {

        var obstacles = tiger.activityLevel.obstacles;


        obstacles.forEach(view -> {
            executor.execute(() -> onFallUpdate(view));
            executor.execute(() -> onMoveRightAndLeftUpdate(view));
        });
    }
}
