package com.tigerfortune.engine.addition;

import static com.tigerfortune.dto.StaticData.earnedCoins;
import static com.tigerfortune.dto.StaticData.finalSpeed;
import static com.tigerfortune.dto.StaticData.speed;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.animation.ValueAnimator;

import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.Engine;
import com.tigerfortune.engine.addition.collect.TigerCollectAddition;
import com.tigerfortune.engine.addition.fall.FallAnimator;
import com.tigerfortune.engine.addition.fall.TigerFallAddition;
import com.tigerfortune.engine.addition.jump.TigerJumpAddition;
import com.tigerfortune.engine.addition.move.TigerMoveAddition;
import com.tigerfortune.other.util.ConcurrentUtil;
import com.tigerfortune.other.util.DoOnce;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: 05.08.2025, 7:34 криво но работает: onMoveRightAndLeftUpdate
public class TigerAddition {
    protected Tiger tiger;
    protected ExecutorService executor = Executors.newCachedThreadPool();
    private static DoOnce startUpdatersOnce = new DoOnce();
    protected static ValueAnimator downAnimator;

    public TigerAddition(Tiger tiger) {
        this.tiger = tiger;

    }

    protected static void setSpeed(boolean a) {
        if (a)
            speed = 0;
        else
            speed = finalSpeed;
    }


    public void onUpdate() {
        var obstacles = tiger.levelActivity.obstacles;
        var collectable = tiger.levelActivity.collectable;

        //Обновление коинов
        mainThread.post(() -> tiger.levelActivity.coins_text.setText(String.valueOf(earnedCoins)));
        var fallAnimator = new FallAnimator(tiger);
        fallAnimator.run();


        startUpdatersOnce.actionOnce(() -> {
            executor.execute(() -> {
                do {
                    for (int i = 0; i < obstacles.size(); i++) {
                        var view = obstacles.get(i);
                        TigerFallAddition tigerFallAddition = TigerFallAddition.getInstance(tiger, obstacles);
                        tigerFallAddition.view = view;
                        tigerFallAddition.index = i;
                        tigerFallAddition.run();
                    }

                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });

            executor.execute(() -> {
                do {
                    obstacles.forEach(view -> {
                        var tigrMoveAddition = TigerMoveAddition.getInstance(tiger);
                        tigrMoveAddition.view = view;
                        tigrMoveAddition.run();
                    });
                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });

            executor.execute(() -> {
                do {
                    obstacles.forEach(view -> {
                        var tigrJumpAddition = TigerJumpAddition.getInstance(tiger);
                        tigrJumpAddition.view = view;
                        tigrJumpAddition.run();
                    });
                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });

            executor.execute(() -> {
                do {
                    collectable.forEach(view -> {
                        var tigerCollectAddition = TigerCollectAddition.getInstance(tiger);
                        tigerCollectAddition.view = view;
                        tigerCollectAddition.run();
                    });
                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });
        });
    }
}
