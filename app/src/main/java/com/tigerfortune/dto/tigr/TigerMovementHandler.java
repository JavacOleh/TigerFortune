package com.tigerfortune.dto.tigr;

import static com.tigerfortune.dto.StaticData.*;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.ViewGroup;

import com.tigerfortune.engine.addition.jump.JumpAnimator;
import com.tigerfortune.other.util.UiUtil;

public class TigerMovementHandler {
    Tiger tiger;
    public boolean isJumping = false;
    public boolean isOnGround = true;

    public TigerMovementHandler(Tiger tiger) {
        this.tiger = tiger;
    }

    public void moveRight() {
        // Получаем ширину земли (GroundView)
        int groundWidth = tiger.levelActivity.ground.getWidth();

        // Получаем параметры и текущую позицию тигра
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int currentStart = params.leftMargin;

        // Получаем текущий правый край тигра
        int tigerRightEdge = tiger.view.getRight();

        // Проверяем, не у края ли земля
        if (tigerRightEdge >= groundWidth - tiger.levelActivity.groundItmWidthInDP || isCloseToObstacheByRight) { // добавим небольшой запас
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
            if (viewPositionX > tiger.levelActivity.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.levelActivity) - marginScrollRight) {
                tiger.scrollTo(tiger.levelActivity.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.levelActivity) / 20);
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
            if (isCloseToObstacheByLeft)
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
            if (viewPositionX < tiger.levelActivity.gameScroller.getScrollX() + marginScrollLeft) {
                // Перемещаем прокрутку влево
                tiger.levelActivity.gameScroller.smoothScrollTo(viewPositionX, 0);
            }
        });

        animator.start();
//        if (!isJumping) {
//            animator.start();
//        }
    }

    public void jump() {
        if (!isOnGround) return; // уже прыгает, не даем повторно прыгнуть

        JumpAnimator jumpAnimator = new JumpAnimator(tiger);
        jumpAnimator.run();;
    }

}
