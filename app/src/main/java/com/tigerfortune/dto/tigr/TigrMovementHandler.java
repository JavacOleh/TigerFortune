package com.tigerfortune.dto.tigr;

import static com.tigerfortune.dto.StaticData.*;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;

import com.tigerfortune.dto.StaticData;
import com.tigerfortune.other.util.UiUtil;

public class TigrMovementHandler {
    Tigr tiger;
    public boolean isJumping = false;
    public boolean isOnGround = true;
    public ValueAnimator downAnimator;
    public ValueAnimator upAnimator;

    public TigrMovementHandler(Tigr tiger) {
        this.tiger = tiger;
    }

    public void moveRight() {
        // Получаем ширину земли (GroundView)
        int groundWidth = tiger.activityLevel.ground.getWidth();

        // Получаем параметры и текущую позицию тигра
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int currentStart = params.leftMargin;

        // Получаем текущий правый край тигра
        int tigerRightEdge = tiger.view.getRight();

        // Проверяем, не у края ли земля
        if (tigerRightEdge >= groundWidth - tiger.activityLevel.groundItmWidthInDP || isCloseToObstacheByRight) { // добавим небольшой запас
            // Тигр уже у границы земли, не двигаем
            return;
        }

        // Создаем анимацию для движения вправо
        ValueAnimator animator = ValueAnimator.ofInt(currentStart, currentStart + speed);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(animation -> {
            if (isCloseToObstacheByRight)
                return;
            int value = (int) animation.getAnimatedValue();

            // Обновляем позицию тигра
            params.leftMargin = value;
            tiger.view.setLayoutParams(params);

            // Обновляем правый край после перемещения
            int viewPositionX = tiger.view.getRight();

            // Пример: скроллим, если тигр у края
            if (viewPositionX > tiger.activityLevel.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.activityLevel) - marginScrollRight) {
                tiger.scrollTo(tiger.activityLevel.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.activityLevel) / 20);
            }
        });

        animator.start();
//        if (!isJumping) {
//            animator.start();
//        }
    }

    public void moveLeft() {
        // Получаем текущие параметры
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int currentMarginLeft = params.leftMargin;

        int targetMarginLeft = currentMarginLeft - speed; // или другую логику для движения назад

        // Ограничения по границам
        if (targetMarginLeft < 0 || isCloseToObstacheByLeft) {
            return;
        }

        //Log.i("isCloseToObstacheByLeft", String.valueOf(isCloseToObstacheByLeft));

        // Анимация изменения margin
        ValueAnimator animator = ValueAnimator.ofInt(currentMarginLeft, targetMarginLeft);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(animation -> {
            if(isCloseToObstacheByLeft)
                return;

            int newMarginLeft = (int) animation.getAnimatedValue();

            // Обновляем margin
            params.leftMargin = newMarginLeft;
            tiger.view.setLayoutParams(params);

            // Обновляем прокрутку, чтобы она соответствовала позиции view
            // Предполагаем, что view внутри ScrollView
            int viewPositionX = tiger.view.getLeft();

            // Если view перемещается внутрь ScrollView, прокрутка должна соответствовать
            // Например, чтобы view было видно, прокручиваем так, чтобы view было в центре или в нужной позиции
            if (viewPositionX < tiger.activityLevel.gameScroller.getScrollX() + marginScrollLeft) {
                // Перемещаем прокрутку влево
                tiger.activityLevel.gameScroller.smoothScrollTo(viewPositionX, 0);
            }
        });

        animator.start();
//        if (!isJumping) {
//            animator.start();
//        }
    }

    public void jump() {
        if (isJumping || !isOnGround) return; // уже прыгает, не даем повторно прыгнуть

        isJumping = true;
        isOnGround = false;
        // Получаем текущие LayoutParams
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int originalMarginBottom = layoutParams.bottomMargin;

        // Анимация подъема (изменение marginBottom)
        upAnimator = ValueAnimator.ofInt(originalMarginBottom, originalMarginBottom + jumpHeight);
        upAnimator.setDuration(300); // время подъема
        upAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            layoutParams.bottomMargin = value;
            tiger.view.setLayoutParams(layoutParams);
        });

        // Анимация спуска (возврат на место)
        downAnimator = ValueAnimator.ofInt(originalMarginBottom + jumpHeight, originalMarginBottom);
        downAnimator.setDuration(300); // время спуска
        downAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();

            if (value >= StaticData.groundPos) {
                if (!isOnGround) {
                    layoutParams.bottomMargin = value;
                    tiger.view.setLayoutParams(layoutParams);
                }
            }
        });

        // Объединяем анимации
        upAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                downAnimator.start();
            }
        });

        downAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                isJumping = false; // прыжок завершен
                isOnGround = true;
            }
        });

        upAnimator.start();
    }

}
