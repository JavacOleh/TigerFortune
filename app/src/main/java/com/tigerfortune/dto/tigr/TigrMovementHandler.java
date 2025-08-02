package com.tigerfortune.dto.tigr;

import static com.tigerfortune.dto.StaticData.*;

import android.animation.ValueAnimator;
import android.view.ViewGroup;

import com.tigerfortune.other.util.UiUtil;

public class TigrMovementHandler {
    Tigr tiger;
    boolean isJumping = false;

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
        if (tigerRightEdge >= groundWidth - 50) { // добавим небольшой запас
            // Тигр уже у границы земли, не двигаем
            return;
        }

        // Создаем анимацию для движения вправо
        ValueAnimator animator = ValueAnimator.ofInt(currentStart, currentStart + speed);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();

            // Обновляем позицию тигра
            params.leftMargin = value;
            tiger.view.setLayoutParams(params);

            // Обновляем правый край после перемещения
            int viewPositionX = tiger.view.getRight();

            // Пример: скроллим, если тигр у края
            if (viewPositionX > tiger.activityLevel.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.activityLevel)) {
                tiger.scrollTo(tiger.activityLevel.gameScroller.getScrollX() + UiUtil.getScreenWidth(tiger.activityLevel) / 20);
            }
        });

        if (!isJumping) {
            animator.start();
        }
    }

    public void moveLeft() {
        // Получаем текущие параметры
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams();
        int currentMarginLeft = params.leftMargin;

        int targetMarginLeft = currentMarginLeft - speed; // или другую логику для движения назад

        // Ограничения по границам
        if (targetMarginLeft < 0) {
            targetMarginLeft = 0;
        }

        // Анимация изменения margin
        ValueAnimator animator = ValueAnimator.ofInt(currentMarginLeft, targetMarginLeft);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(animation -> {
            int newMarginLeft = (int) animation.getAnimatedValue();

            // Обновляем margin
            params.leftMargin = newMarginLeft;
            tiger.view.setLayoutParams(params);

            // Обновляем прокрутку, чтобы она соответствовала позиции view
            // Предполагаем, что view внутри ScrollView
            int viewPositionX = tiger.view.getLeft();

            // Если view перемещается внутрь ScrollView, прокрутка должна соответствовать
            // Например, чтобы view было видно, прокручиваем так, чтобы view было в центре или в нужной позиции
            if (viewPositionX < tiger.activityLevel.gameScroller.getScrollX()) {
                // Перемещаем прокрутку влево
                tiger.activityLevel.gameScroller.smoothScrollTo(viewPositionX, 0);
            }
        });

        if (!isJumping) {
            animator.start();
        }
    }

    public void jump() {
        if (isJumping) return; // уже прыгает, не даем повторно прыгнуть

        isJumping = true;
        // Высота прыжка, например, 200 пикселей

        // Анимация подъема
        ValueAnimator upAnimator = ValueAnimator.ofFloat(0, -jumpHeight);
        upAnimator.setDuration(300); // время подъема
        upAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            tiger.view.setTranslationY(value);
        });

        // Анимация спуска (возврата на место)
        ValueAnimator downAnimator = ValueAnimator.ofFloat(-jumpHeight, 0);
        downAnimator.setDuration(300); // время спуска
        downAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            tiger.view.setTranslationY(value);
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
            }
        });

        upAnimator.start();
    }

    public int getMarginStart() {
        if (tiger.view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams()).getMarginStart();
        }
        return 0;
    }

    public int getMarginEnd() {
        if (tiger.view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams()).getMarginEnd();
        }
        return 0;
    }

    public int getMarginTop() {
        if (tiger.view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams()).topMargin;
        }
        return 0;
    }

    public int getMarginBottom() {
        if (tiger.view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams()).bottomMargin;
        }
        return 0;
    }
}
