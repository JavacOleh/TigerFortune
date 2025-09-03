package com.tigerfortune.dto.level;

import static com.tigerfortune.dto.StaticData.defautlLevel;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.R;
import com.tigerfortune.activity.LevelActivity;
import com.tigerfortune.dto.EntityInited;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.enemy.EnemySnake;
import com.tigerfortune.dto.enemy.EnemySnakeMoveAnimator;
import com.tigerfortune.dto.enemy.EnemySnakeMovement;
import com.tigerfortune.dto.level.levels.Level1;
import com.tigerfortune.other.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

public class LevelHandler {
    public List<Runnable> landshaftBuilders;
    public LevelActivity levelActivity;

    public LevelHandler(LevelActivity levelActivity) {
        this.levelActivity = levelActivity;
        initLandshaftBuilders();
    }

    private void initLandshaftBuilders() {
        landshaftBuilders = new ArrayList<>();

        //0
        landshaftBuilders.add(new Level1(this));

        //1
    }

    public void buildLandshaft(int level) {
        var ind = Math.max(defautlLevel, Math.min(level, landshaftBuilders.size() - 1));
        StaticData.currentLevel = ind;
        var a = landshaftBuilders.get(ind);

        if (a != null)
            a.run();
    }


    public void addDecorate(int x, int y, int width, int height, int res_id) {
        ImageView decorate = new ImageView(levelActivity);
        decorate.setImageResource(res_id);

        // Устанавливаем параметры для вьюшки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                width, height);//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота

        int decorateId = View.generateViewId();
        decorate.setId(decorateId); // Присваиваем уникальный id

        // Устанавливаем положение (X, Y) для препятствия
        layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

        // Устанавливаем отступы, если необходимо
        layoutParams.leftToLeft = R.id.constraintInside; //ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftMargin = UiUtil.dpToPx(x);  // Позиция по оси X
        layoutParams.bottomMargin = UiUtil.dpToPx(y);   // Позиция по оси Y

        // Применяем параметры
        decorate.setScaleType(ImageView.ScaleType.FIT_XY);
        decorate.setLayoutParams(layoutParams);

        // Добавляем препятствие в layout
        levelActivity.constraintInside.addView(decorate);

        // Сохраняем добавленный элемент в список препятствий
        var entity = new EntityInited();
        entity.setY(UiUtil.dpToPx(y));
        entity.setX(UiUtil.dpToPx(x));
        entity.setWidth(width);
        entity.setHeight(height);
        entity.setResId(res_id);
        putPositionToMap("decorates", entity);
        levelActivity.decorates.add(decorate);
        levelActivity.tiger.bringToFront();
    }


    public void addSnakeEnemy(int x, int y, int width, int height, int res_id, Integer distanceInBothSides, Long moverDuration) {
        ImageView enemy = new ImageView(levelActivity);
        enemy.setImageResource(res_id);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                width, height);//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота

        int obstacleId = View.generateViewId();
        enemy.setId(obstacleId); // Присваиваем уникальный id

        // Устанавливаем положение (X, Y) для препятствия
        layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

        // Устанавливаем отступы, если необходимо
        layoutParams.leftToLeft = R.id.constraintInside;
        layoutParams.leftMargin = UiUtil.dpToPx(x);  // Позиция по оси X
        layoutParams.bottomMargin = UiUtil.dpToPx(y);   // Позиция по оси Y

        enemy.setScaleType(ImageView.ScaleType.FIT_XY);
        // Применяем параметры
        enemy.setLayoutParams(layoutParams);

        // Добавляем препятствие в layout
        levelActivity.constraintInside.addView(enemy);

        var entity = new EntityInited();
        entity.setY(UiUtil.dpToPx(y));
        entity.setX(UiUtil.dpToPx(x));
        entity.setWidth(width);
        entity.setHeight(height);
        entity.setResId(res_id);


        levelActivity.tiger.bringToFront();

        //Start movement
        EnemySnakeMoveAnimator snakeMoveAnimator = new EnemySnakeMoveAnimator(enemy);
        EnemySnakeMovement shakeMovement = new EnemySnakeMovement(entity, enemy);

        if (distanceInBothSides != null)
            shakeMovement.distanceInBothSides = distanceInBothSides;

        if (moverDuration != null)
            shakeMovement.moverDuration = moverDuration;

        snakeMoveAnimator.onStartUpdateAnimation();
        shakeMovement.onStartMove();

        levelActivity.snakes.add(new EnemySnake(
                enemy,
                snakeMoveAnimator,
                shakeMovement,
                entity
        ));

        enemy.bringToFront();
        levelActivity.tiger.bringToFront();
    }

    public void addObstacle(int x, int y, int width, int height, int res_id) {
        ImageView obstacle = new ImageView(levelActivity);
        obstacle.setImageResource(res_id);

        // Устанавливаем параметры для вьюшки
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                width, height);//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота

        int obstacleId = View.generateViewId();
        obstacle.setId(obstacleId); // Присваиваем уникальный id

        // Устанавливаем положение (X, Y) для препятствия
        layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

        // Устанавливаем отступы, если необходимо
        layoutParams.leftToLeft = R.id.constraintInside;
        layoutParams.leftMargin = UiUtil.dpToPx(x);  // Позиция по оси X
        layoutParams.bottomMargin = UiUtil.dpToPx(y);   // Позиция по оси Y

        obstacle.setScaleType(ImageView.ScaleType.FIT_XY);
        // Применяем параметры
        obstacle.setLayoutParams(layoutParams);

        // Добавляем препятствие в layout
        levelActivity.constraintInside.addView(obstacle);

        // Сохраняем добавленный элемент в список препятствий
        var entity = new EntityInited();
        entity.setY(UiUtil.dpToPx(y));
        entity.setX(UiUtil.dpToPx(x));
        entity.setWidth(width);
        entity.setHeight(height);
        entity.setResId(res_id);

        putPositionToMap("obstacles", entity);
        levelActivity.obstacles.add(obstacle);
        levelActivity.tiger.bringToFront();
    }

    public ArrayList<EntityInited> getPositionsByType(String entityType) {
        var map = levelActivity.entityMap;

        return switch (entityType) {
            case "obstacles", "decorates" -> levelActivity.entityMap.get(entityType);
            default -> null;
        };
    }

    public void putPositionToMap(String entityType, EntityInited entityInited) {
        var map = levelActivity.entityMap;

        if (map.containsKey(entityType)) {
            var a = map.get(entityType);
            a.add(entityInited);

        } else {
            var a = new ArrayList<EntityInited>();
            a.add(entityInited);
            map.put(entityType, a);
        }
    }

    public void addEbnutsaGolovojEl(int x, int y, int width, int height) {
        addObstacle(x, y, width, height, R.drawable.ebnis_golovoj_s_podnizu);
    }

    public void addFood(ImageView view) {
        if (view != null && levelActivity.constraintInside.getHeight() - view.getTop() > 200) {
            ImageView food = new ImageView(levelActivity);
            food.setImageResource(R.drawable.sushi);
            food.setId(levelActivity.getResources().getIdentifier("bonus_sushi", "id", levelActivity.getPackageName()));

            // Устанавливаем параметры для вьюшки
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    view.getWidth() - (view.getWidth() / 4), view.getHeight());//UiUtil.dpToPx(width), UiUtil.dpToPx(height)); // Ширина и высота
            ConstraintLayout.LayoutParams viewLayoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();

            // Устанавливаем положение (X, Y) для препятствия
            layoutParams.bottomToTop = R.id.ground; // Привязка к ID элемента ground

            // Устанавливаем отступы, если необходимо
            layoutParams.leftToLeft = R.id.constraintInside;
            layoutParams.leftMargin = view.getLeft() + ((view.getWidth() / 4) / 2); //UiUtil.dpToPx(x);  // Позиция по оси X
            layoutParams.bottomMargin = viewLayoutParams.bottomMargin + view.getHeight();   // Позиция по оси Y

            food.setScaleType(ImageView.ScaleType.FIT_XY);
            // Применяем параметры
            food.setLayoutParams(layoutParams);

            // Добавляем препятствие в layout
            levelActivity.constraintInside.addView(food);

            // Сохраняем добавленный элемент в список препятствий
            levelActivity.collectable.add(food);
            levelActivity.tiger.bringToFront();
        }
    }


}
