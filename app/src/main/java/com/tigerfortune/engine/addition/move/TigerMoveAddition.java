package com.tigerfortune.engine.addition.move;

import static com.tigerfortune.engine.addition.fall.TigerFallAddition.pogreshnostHorL;

import android.view.View;
import android.view.ViewGroup;

import com.tigerfortune.dto.Entity;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;

import java.util.List;

/*
Todo: Не работает нормально, потому отключён:
 */
public class TigerMoveAddition extends TigerAddition implements Runnable {
    private List<Entity> obstaclesEntities;
    private static Entity closestEntityByRight;
    private static Entity closestEntityByLeft;
    public static int pogreshnostXl = 0;
    public static int pogreshnostXr = pogreshnostHorL / 2 * -1;
    public static int pogreshnostY = 5;
    private static TigerMoveAddition instance;
    public int ind;
    public View view;

    private TigerMoveAddition(Tiger tiger) {
        super(tiger);
        obstaclesEntities = tiger.levelActivity.levelHandler.getPositionsByType("obstacles");
    }

    public static TigerMoveAddition getInstance(Tiger tiger) {
        if (instance == null)
            instance = new TigerMoveAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
        synchronized (this) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
            closestEntityByRight = getClosestEntity(0, params.leftMargin + params.width, false);
            closestEntityByLeft = getClosestEntity(0, params.leftMargin, true);
            StaticData.isCloseToObstacheByRight = closestEntityByRight != null;
            StaticData.isCloseToObstacheByLeft = closestEntityByLeft != null;
        }

//
//        //Тигр
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
//        var tigrRendX = params.leftMargin + tiger.view.getWidth();
//        var tigrStartX = params.leftMargin;
//
//        var tigrStartY = params.bottomMargin;
//        var tigrRendY = params.bottomMargin + tiger.view.getHeight();
//
//        //Вьюшка
//        ViewGroup.MarginLayoutParams paramsV = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
//        var viewStartY = paramsV.bottomMargin;
//        var viewWendY = paramsV.bottomMargin + view.getHeight();
//
//        var viewStartX = paramsV.leftMargin;
//        var vieWendX = paramsV.leftMargin + view.getWidth();
//
//        boolean isClosHorizontallyFromLeft = tigrRendX >= viewStartX && tigrRendX <= vieWendX + pogreshnostXl;
//        boolean isClosHorizontallyFromRight = tigrStartX >= viewStartX && tigrStartX <= vieWendX + pogreshnostXr;
//        boolean isUnderObstache = tigrRendY < viewStartY;
//        boolean isOnObstache = tigrStartY >= viewStartY;
//
//
//        if (isClosHorizontallyFromLeft) {
//            StaticData.isCloseToObstacheByRight = !isUnderObstache && !isOnObstache;
//            setSpeed(StaticData.isCloseToObstacheByRight);
//        } else {
//            StaticData.isCloseToObstacheByRight = false;
//            setSpeed(StaticData.isCloseToObstacheByRight);
//        }
//
//        if (isClosHorizontallyFromRight) {
//            StaticData.isCloseToObstacheByLeft = !isUnderObstache && !isOnObstache;
//            setSpeed(StaticData.isCloseToObstacheByLeft);
//        } else {
//            StaticData.isCloseToObstacheByLeft = false;
//            setSpeed(StaticData.isCloseToObstacheByLeft);
//        }
    }

    private Entity getClosestEntity(int pogreshnost, int leftOrRight, boolean isForLeft) {
        for (int i = 0; i < obstaclesEntities.size(); i++) {
            var entity = obstaclesEntities.get(i);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
            var isClose = isForLeft
                    ? params.leftMargin < entity.getX() + entity.getWidth() && params.leftMargin > entity.getX() //entity.getX() - leftOrRight > pogreshnost
                    : params.leftMargin + params.width < entity.getX() + entity.getWidth() && params.leftMargin + params.width > entity.getX();
            var isNotOnObstache = !(params.bottomMargin > entity.getY());
            var isHitsVerticallyObstache = params.bottomMargin + tiger.view.getHeight() > entity.getY();

            if (isClose && (isNotOnObstache && isHitsVerticallyObstache)) {
                return entity;
            }
        }

        return null;
    }
}
