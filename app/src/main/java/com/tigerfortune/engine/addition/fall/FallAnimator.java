package com.tigerfortune.engine.addition.fall;

import static com.tigerfortune.dto.StaticData.*;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.common.ViewCoordinates;
import com.tigerfortune.engine.addition.jump.JumpAnimator;
import com.tigerfortune.other.util.DoOnce;
import com.tigerfortune.other.util.UiUtil;

public class FallAnimator implements Runnable {
    public Tiger tiger;
    public ValueAnimator downAnimator;
    private static DoOnce doOnce = new DoOnce();
    private static DoOnce doOnce2 = new DoOnce();

    public FallAnimator(Tiger tiger) {
        this.tiger = tiger;
        var tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();

        downAnimator = ValueAnimator.ofInt(tigerLayoutParams.bottomMargin, groundPos);
        downAnimator.setDuration(fallDuration); // время спуска


        downAnimator.addUpdateListener(animation -> {
            if (!tiger.tigerMovementHandler.isOnGround) {

                int value = (int) animation.getAnimatedValue();

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();

                groundPos = TigerFallAddition.setClosestGroundPos(tiger.levelActivity.obstacles, tiger, tiger.levelActivity.levelHandler.getPositionsByType("obstacles"));

                if (layoutParams.bottomMargin > groundPos) {
                    layoutParams.bottomMargin = value;
                    tiger.view.setLayoutParams(layoutParams);
                } else {
                    // Завершаем анимацию, если тигр на земле
                    if (downAnimator.isRunning()) {
                        mainThread.post(downAnimator::end); // Останавливаем анимацию
                    }
                }
            }
        });


        downAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                tiger.tigerMovementHandler.isOnGround = true;
                doOnce.hasDon = false;
                JumpAnimator.doOnce.hasDon = false;
            }
        });
    }

    @Override
    public void run() {
        var tigerLayoutParams = (ConstraintLayout.LayoutParams) tiger.view.getLayoutParams();
        tiger.tigerMovementHandler.isOnGround = (tigerLayoutParams.bottomMargin == groundPos);

        if (!tiger.tigerMovementHandler.isOnGround) {
            if (!doOnce.hasDon && !tiger.tigerMovementHandler.isJumping) {
                doOnce.actionOnce(() -> mainThread.post(downAnimator::start));
            }
        } else {
            if (doOnce.hasDon) {
                mainThread.post(downAnimator::end);
            }
        }
    }
}
