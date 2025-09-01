package com.tigerfortune.dto.enemy;

import android.view.View;

import com.tigerfortune.dto.EntityInited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EnemySnake {
    View view;
    EnemySnakeMoveAnimator snakeMoveAnimator;
    EnemySnakeMovement snakeMovement;
    EntityInited entityInited;
}
