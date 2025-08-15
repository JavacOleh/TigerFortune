package com.tigerfortune.engine.addition.move;

import static com.tigerfortune.engine.addition.fall.TigerFallAddition.pogreshnostHorL;

import android.view.View;
import android.view.ViewGroup;

import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
/*
Todo: Работает всё-таки криво:
levelHandler.addObstacle(350, 70, 300, 120, R.drawable.brick); если это так добавить то оно не воспринимает это к сожалению.
 */
public class TigerMoveAddition extends TigerAddition implements Runnable{
    public static int pogreshnostXl = 0;
    public static int pogreshnostXr = pogreshnostHorL / 2 * -1;
    public static int pogreshnostY = 5;
    private static TigerMoveAddition instance;
    public int ind;
    public View view;

    private TigerMoveAddition(Tiger tiger) {
        super(tiger);
    }

    public static TigerMoveAddition getInstance(Tiger tiger) {
        if(instance == null)
            instance = new TigerMoveAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
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
}
