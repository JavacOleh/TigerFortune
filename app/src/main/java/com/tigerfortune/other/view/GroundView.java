package com.tigerfortune.other.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tigerfortune.other.util.UiUtil;

public class GroundView extends LinearLayout {
    private Context context;
    private int groundCount;
    private int backgroundSRC;
    private int widthInDP;

    public GroundView(Context context) {
        super(context);
        init(context, null);
    }

    public GroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        setOrientation(HORIZONTAL); // Горизонтальное расположение элементов
        setPadding(0, 0, 0, 0); // Убираем все паддинги
        setWeightSum(0f); // Убираем дополнительное пространство, которое может быть распределено между элементами
    }

    public void setData(int groundCount, int backgroundSRC, int widthInDP) {
        this.groundCount = groundCount;
        this.backgroundSRC = backgroundSRC;
        this.widthInDP = widthInDP;

        // Динамически добавляем ImageView элементы
        createImageViews();
    }

    private void createImageViews() {
        // Преобразуем dp в пиксели
        int widthInPixels = UiUtil.dpToPx(widthInDP);

        // Удаляем все существующие элементы в контейнере
        removeAllViews();

        // Создаем нужное количество ImageView
        for (int i = 0; i < groundCount; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(backgroundSRC);

            // Устанавливаем ширину в пикселях
            LayoutParams params = new LayoutParams(widthInPixels, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0); // Убираем маргины (отступы между элементами)
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // Добавляем ImageView в контейнер
            addView(imageView);
        }
    }
}
