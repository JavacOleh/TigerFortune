package com.tigerfortune.engine.addition.end;

import static com.tigerfortune.engine.addition.collect.TigerCollectAddition.latestEarnedCoins;

import android.util.Log;
import android.view.ViewGroup;

import com.tigerfortune.activity.LoadingActivity;
import com.tigerfortune.activity.MainActivity;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.enemy.EnemySnake;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.UiUtil;

// TODO: 02.09.2025 Не работает и bottom margin здесь не причём.
public class TigerExitBySnakeAddition extends TigerAddition implements Runnable{
    private static TigerExitBySnakeAddition instance;

    private TigerExitBySnakeAddition(Tiger tiger) {
        super(tiger);
    }

    public static TigerExitBySnakeAddition getInstance(Tiger tiger) {
        if (instance == null)
            instance = new TigerExitBySnakeAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
        var closestSnake = getClosestSnake();
        if (closestSnake == null)
            return;

        var isCloseToSnake = isCloseToSnake(closestSnake);


        if (isCloseToSnake) {
            LoadingActivity.redirectClass = MainActivity.class;
            UiUtil.loadActivityFinishCurrent(tiger.levelActivity, LoadingActivity.redirectClass);

            var userService = UserService.getInstance(tiger.levelActivity);
            userService.setCurrentLevel(StaticData.currentLevel);

            var collectedCoins = userService.getCollectedCoins();
            userService.setCollectedCoins(collectedCoins - latestEarnedCoins);
            latestEarnedCoins = 0;
        }

    }

    // TODO: 02.09.2025 Почему-то метод ниже возвращает всегда нулл 
    public EnemySnake getClosestSnake() {
        EnemySnake closest = null;
        var tigrParams = ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams());

        for (EnemySnake snake : tiger.levelActivity.snakes) {
            var entity = snake.getEntityInited();
            var max = snake.getSnakeMovement().maxDistance;
            var min = snake.getSnakeMovement().minDistance;
            if (tigrParams.leftMargin + tigrParams.width < entity.getX() + max && tigrParams.leftMargin > entity.getX() + min) {
                closest = snake;
                break;
            }
        }

        return closest;
    }

    public boolean isCloseToSnake(EnemySnake enemySnake) {
        var tigrParams = ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams());

        //боттом марджин здесь не причём.
        return  tigrParams.bottomMargin == enemySnake.getEntityInited().getY() &&
                tigrParams.leftMargin > enemySnake.getSnakeMovement().current_entity_pos &&
                tigrParams.leftMargin
                        + tigrParams.width
                        < enemySnake.getSnakeMovement().current_entity_pos
                        + enemySnake.getEntityInited().getWidth();
    }
}
