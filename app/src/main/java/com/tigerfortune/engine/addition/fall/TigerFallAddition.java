package com.tigerfortune.engine.addition.fall;

import static com.tigerfortune.dto.StaticData.groundPos;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.dto.Entity;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
import com.tigerfortune.engine.addition.common.ViewCoordinates;
import com.tigerfortune.engine.addition.jump.JumpAnimator;
import com.tigerfortune.other.util.DoOnce;

import java.util.List;

public class TigerFallAddition extends TigerAddition implements Runnable {
    public static int pogreshnostHorL = 200;
    public static int pogreshnostY = 5;
    private static TigerFallAddition instance;
    private static DoOnce doOnce = new DoOnce();
    private static DoOnce doOnce2 = new DoOnce();
    public View view;
    public int index;
    public int viewTop;
    private List<Entity> obstaclesEntities;
    private List<View> obstacles;
    public static boolean check = true;
    ViewCoordinates viewCoordinates;

    private TigerFallAddition(Tiger tiger, List<View> obstacles) {
        super(tiger);
        obstaclesEntities = tiger.levelActivity.levelHandler.getPositionsByType("obstacles");
        this.obstacles = obstacles;
    }

    public static TigerFallAddition getInstance(Tiger tiger, List<View> obstacles) {
        if (instance == null)
            instance = new TigerFallAddition(tiger, obstacles);
        return instance;
    }

    //TODO: Наконец-то я более менее это оптимизировал на методе setClosestGroundPos все и держится здесь
    @Override
    public void run() {
        if (check) {
            viewCoordinates = new ViewCoordinates(view, tiger);
            if (obstaclesEntities != null) {
                viewTop = obstaclesEntities.get(index < obstaclesEntities.size() ? index : 0).getY();
            }
            check = false;
        }

        if ((viewCoordinates.isTigerAndElOnSameGround() || !viewCoordinates.isTigerUnderEl()) && viewCoordinates.isAlthoughOnePixelInElHorizontally()) {
            check = false;
            var tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
            JumpAnimator.doOnce.hasDon = false;

            doOnce.actionOnce(() -> {

                groundPos = setClosestGroundPos(obstacles, tiger, obstaclesEntities);//viewTop + view.getHeight(); //tigerLayoutParams.bottomMargin;
                //tiger.tigerMovementHandler.isOnGround = true;

                //viewCoordinates.bottomView = groundPos;
                doOnce2.hasDon = false;
            });
            viewCoordinates.start = tigerLayoutParams.leftMargin + tiger.view.getWidth() / 2;
            //tiger.tigerMovementHandler.isOnGround = viewCoordinates.isTigerAndElOnSameGround();

        } else if (!viewCoordinates.isTigerAndElOnSameGround()){
            check = true;
            doOnce2.actionOnce(() -> {
                groundPos = setClosestGroundPos(obstacles, tiger, obstaclesEntities);//groundPos = 0; //tiger.levelActivity.ground.getTop()
                //tiger.tigerMovementHandler.isOnGround = false;
            });

            doOnce.hasDon = false;
        }
    }

    public static int setClosestGroundPos(List<View> obstacles, Tiger tiger, List<Entity> obstaclesPositions) {
        for (int i = 0; i < obstacles.size(); i++) {
            var view = obstacles.get(i);
            var viewCoordinates = new ViewCoordinates(view, tiger);
            int viewTop = 0;

            if (obstaclesPositions != null) {
                viewTop = obstaclesPositions.get(i < obstaclesPositions.size() ? i : obstaclesPositions.size() - 1).getY();
            }

            if ((viewCoordinates.isTigerAndElOnSameGround() || !viewCoordinates.isTigerUnderEl()) && viewCoordinates.isAlthoughOnePixelInElHorizontally()) {
                return viewTop + view.getHeight();
            }
        }
        return 0;
    }
}
