package com.tigerfortune.dto.enemy;

import android.widget.ImageView;

import com.tigerfortune.R;
import com.tigerfortune.other.util.ConcurrentUtil;
import com.tigerfortune.other.util.DoOnce;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class EnemySnakeMoveAnimator {
    public static final List<Integer> ids = new LinkedList<>(
            List.of(
                    R.drawable.snake_default,
                    R.drawable.snake_item2,
                    R.drawable.snake_item2,
                    R.drawable.snake_item3
            )
    );
    private int index = 0;
    public long sleepDuration = 200L;
    public ImageView enemyView;
    private DoOnce doOnce = new DoOnce();
    private Thread thread;

    public EnemySnakeMoveAnimator(ImageView enemyView) {
        this.enemyView = enemyView;
    }

    public void onStartUpdateAnimation() {
        doOnce.actionOnce(() -> {
            thread = new Thread(() -> {
                do {
                    enemyView.setImageResource(ids.get(index));
                    enemyView.invalidate();

                    updateIndex();

                    ConcurrentUtil.sleep(sleepDuration);
                }while (true);
            });

            thread.start();
        });
    }

    private void updateIndex() {
        if (index + 1 < ids.size())
            index++;
        else
            index = 0;
    }
}
