package com.tigerfortune.dto.level;

import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.R;
import com.tigerfortune.activity.ActivityLevel;
import com.tigerfortune.dto.level.levels.Level1;
import com.tigerfortune.other.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

public class LevelHandler {
    public List<Runnable> landshaftBuilders;
    public ActivityLevel activityLevel;

    public LevelHandler(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
        initLandshaftBuilders();
    }

    private void initLandshaftBuilders() {
        landshaftBuilders = new ArrayList<>();

        //0
        landshaftBuilders.add(new Level1(this));

        //1
    }

    public void buildLandshaft(int level) {
        var a = landshaftBuilders.get(level);

        if(a != null)
            a.run();
    }

    public void addDecorate(int x, int y, int width, int height, int res_id) {
        ImageView obstacle = new ImageView(activityLevel);
        obstacle.setImageResource(res_id);

        // Устанавливаем параметры для вьюшки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                width, height);//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота

        // Устанавливаем положение (X, Y) для препятствия
        layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

        // Устанавливаем отступы, если необходимо
        layoutParams.leftToLeft = R.id.constraintInside; //ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftMargin = UiUtil.dpToPx(x);  // Позиция по оси X
        layoutParams.bottomMargin =  UiUtil.dpToPx(y);   // Позиция по оси Y

        // Применяем параметры
        obstacle.setScaleType(ImageView.ScaleType.FIT_XY);
        obstacle.setLayoutParams(layoutParams);

        // Добавляем препятствие в layout
        activityLevel.constraintInside.addView(obstacle);

        // Сохраняем добавленный элемент в список препятствий
        activityLevel.decorates.add(obstacle);
        activityLevel.tigr.bringToFront();
    }

    public void addObstacle(int x, int y, int width, int height, int res_id) {
        ImageView obstacle = new ImageView(activityLevel);
        obstacle.setImageResource(res_id);

        // Устанавливаем параметры для вьюшки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                width, height);//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота

        // Устанавливаем положение (X, Y) для препятствия
        layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

        // Устанавливаем отступы, если необходимо
        layoutParams.leftToLeft = R.id.constraintInside;
        layoutParams.leftMargin = UiUtil.dpToPx(x);  // Позиция по оси X
        layoutParams.bottomMargin =  UiUtil.dpToPx(y);   // Позиция по оси Y

        obstacle.setScaleType(ImageView.ScaleType.FIT_XY);
        // Применяем параметры
        obstacle.setLayoutParams(layoutParams);

        // Добавляем препятствие в layout
        activityLevel.constraintInside.addView(obstacle);

        // Сохраняем добавленный элемент в список препятствий
        activityLevel.obstacles.add(obstacle);
        activityLevel.tigr.bringToFront();
    }
}
