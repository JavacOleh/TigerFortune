package com.tigerfortune.engine.addition.jump;

import static com.tigerfortune.dto.StaticData.*;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;

import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.fall.TigerFallAddition;
import com.tigerfortune.other.util.DoOnce;

public class JumpAnimator implements Runnable {
    public Tiger tiger;
    public ValueAnimator upAnimator;
    public static DoOnce doOnce = new DoOnce();

    public JumpAnimator(Tiger tiger) {
        this.tiger = tiger;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int originalMarginBottom = layoutParams.bottomMargin;
        upAnimator = ValueAnimator.ofInt(originalMarginBottom, originalMarginBottom + jumpHeight);
        upAnimator.setDuration(jumpDuration); // время подъема
        upAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();


            layoutParams.bottomMargin = value;
            tiger.view.setLayoutParams(layoutParams);
        });

        upAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                //start down animator?
                groundPos = TigerFallAddition.setClosestGroundPos(tiger.levelActivity.obstacles, tiger, tiger.levelActivity.levelHandler.getPositionsByType("obstacles"));
                tiger.tigerMovementHandler.isJumping = false;
            }
        });
    }

    @Override
    public void run() {
        if (tiger.tigerMovementHandler.isOnGround) {
            if (!doOnce.hasDon) {
                doOnce.actionOnce(() -> mainThread.post(upAnimator::start));

                tiger.tigerMovementHandler.isJumping = true;
                tiger.tigerMovementHandler.isOnGround = false;
            }
        }
    }
}
