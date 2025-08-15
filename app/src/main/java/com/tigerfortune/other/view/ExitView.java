package com.tigerfortune.other.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.tigerfortune.R;

public class ExitView extends View {

    private Bitmap exitPortalLayoutBitmap;
    private Bitmap exitPortalCenterBitmap;
    private Paint paint;

    private float angle1 = 0f; // Вращение для внешнего круга (по часовой)
    private float angle2 = 0f; // Вращение для центрального круга (против часовой)

    private ValueAnimator animator1;
    private ValueAnimator animator2;

    private boolean isAnimationStarted = false; // флаг для запуска анимации один раз

    public ExitView(Context context) {
        super(context);
        init();
    }

    public ExitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Загружаем изображения
        exitPortalLayoutBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.exit_portal_layout);
        exitPortalCenterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.exit_portal_center);

        // Инициализация Paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation(); // Запускаем анимацию при появлении
    }

    public void startAnimation() {
        if (isAnimationStarted) return;
        isAnimationStarted = true;

        // Создаем первый анимационный цикл (по часовой)
        animator1 = ValueAnimator.ofFloat(0f, 360f);
        animator1.setDuration(5000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setInterpolator(null); // линейный интерполятор
        animator1.addUpdateListener(animation -> {
            angle1 = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator1.start();

        // Создаем второй анимационный цикл (против часовой)
        animator2 = ValueAnimator.ofFloat(0f, -360f);
        animator2.setDuration(5000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(null);
        animator2.addUpdateListener(animation -> {
            angle2 = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator2.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) {
            return;
        }

        float centerX = width / 2f;
        float centerY = height / 2f;

        float outerRadius = width / 2f;
        float innerRadius = outerRadius * 0.75f;

        // Вращающийся внешний круг
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(angle1);
        // Масштабируем Canvas так, чтобы изображение растянулось по размеру круга
        float scaleX = (outerRadius * 2) / exitPortalLayoutBitmap.getWidth();
        float scaleY = (outerRadius * 2) / exitPortalLayoutBitmap.getHeight();
        canvas.scale(scaleX, scaleY);
        // Рисуем изображение по центру
        canvas.drawBitmap(exitPortalLayoutBitmap, -exitPortalLayoutBitmap.getWidth() / 2f, -exitPortalLayoutBitmap.getHeight() / 2f, null);
        canvas.restore();

        // Вращающийся внутренний круг
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(angle2);
        float innerScaleX = (innerRadius * 2) / exitPortalCenterBitmap.getWidth();
        float innerScaleY = (innerRadius * 2) / exitPortalCenterBitmap.getHeight();
        canvas.scale(innerScaleX, innerScaleY);
        canvas.drawBitmap(exitPortalCenterBitmap, -exitPortalCenterBitmap.getWidth() / 2f, -exitPortalCenterBitmap.getHeight() / 2f, null);
        canvas.restore();
    }
}
