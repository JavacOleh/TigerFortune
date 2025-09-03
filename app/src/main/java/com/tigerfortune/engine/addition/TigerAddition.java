package com.tigerfortune.engine.addition;

import static com.tigerfortune.dto.StaticData.finalSpeed;
import static com.tigerfortune.dto.StaticData.speed;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.util.Log;
import android.view.View;

import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.Engine;
import com.tigerfortune.engine.addition.collect.TigerCollectAddition;
import com.tigerfortune.engine.addition.end.TigerExitBySnakeAddition;
import com.tigerfortune.engine.addition.fall.FallAnimator;
import com.tigerfortune.engine.addition.fall.TigerFallAddition;
import com.tigerfortune.engine.addition.jump.TigerJumpAddition;
import com.tigerfortune.engine.addition.move.TigerMoveAddition;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.ConcurrentUtil;
import com.tigerfortune.other.util.DoOnce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: 01.09.2025 Вроде работает..
public class TigerAddition {
    protected Tiger tiger;
    protected ExecutorService executor = Executors.newCachedThreadPool();
    private static DoOnce startUpdatersOnce = new DoOnce();

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
        mainThread.post(() -> tiger.levelActivity.coins_text.
                setText(String.valueOf
                        (UserService.getInstance(tiger.levelActivity).getCollectedCoins())));
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
                    List<View> copy;
                    synchronized (this) {
                        copy = new ArrayList<>(collectable);
                    }
                    copy.forEach(view -> {
                        var tigerCollectAddition = TigerCollectAddition.getInstance(tiger);
                        tigerCollectAddition.view = view;
                        tigerCollectAddition.run();
                    });
//                        collectable.forEach(view -> {
//                            var tigerCollectAddition = TigerCollectAddition.getInstance(tiger);
//                            tigerCollectAddition.view = view;
//                            tigerCollectAddition.run();
//                        });
                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });

            executor.execute(() -> {
                do {
                    var tigerExitBySnake = TigerExitBySnakeAddition.getInstance(tiger);
                    tigerExitBySnake.run();
                    ConcurrentUtil.sleep(Engine.waitFor);
                } while (true);
            });
        });
    }
}
