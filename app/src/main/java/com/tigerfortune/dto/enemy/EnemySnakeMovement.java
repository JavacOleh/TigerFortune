package com.tigerfortune.dto.enemy;

import android.animation.ValueAnimator;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tigerfortune.dto.EntityInited;
import com.tigerfortune.other.util.DoOnce;

public class EnemySnakeMovement {
    private DoOnce doOnce = new DoOnce();
    public int distanceInBothSides = 400;
    public long moverDuration = 1400;
    public int maxDistance;
    public int minDistance;
    public int current_entity_pos;
    public EntityInited enemy;

    public EnemySnakeMovement(EntityInited enemy, ImageView enemyView) {
        this.enemy = enemy;
        this.enemyView = enemyView;
    }

    public ImageView enemyView;

    public ValueAnimator mover;
    private boolean movingRight = true;
    public void onStartMove() {
        doOnce.actionOnce(() -> {
            maxDistance = enemy.getX() + distanceInBothSides;
            minDistance = enemy.getX() - distanceInBothSides;

            mover = new ValueAnimator();
            mover.setDuration(moverDuration);
            mover.setIntValues(current_entity_pos, minDistance);

            mover.addUpdateListener(animation -> {

                int currentPos = (int) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) enemyView.getLayoutParams();

                params.leftMargin = currentPos;
                enemyView.setLayoutParams(params);

                if (currentPos <= minDistance && movingRight) {
                    movingRight = false;
                    enemyView.setScaleX(-1);
                    mover.setIntValues(minDistance, maxDistance);
                } else if (currentPos >= maxDistance && !movingRight) {
                    movingRight = true;
                    enemyView.setScaleX(1);
                    mover.setIntValues(maxDistance, minDistance);
                }

                current_entity_pos = ((ViewGroup.MarginLayoutParams) enemyView.getLayoutParams()).leftMargin;
            });

            mover.setRepeatCount(ValueAnimator.INFINITE);
            mover.setRepeatMode(ValueAnimator.RESTART);
            mover.start();
        });


    }
}
