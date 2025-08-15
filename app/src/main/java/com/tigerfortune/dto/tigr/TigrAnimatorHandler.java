package com.tigerfortune.dto.tigr;

import android.os.Looper;
import android.util.Log;

import com.tigerfortune.R;
import com.tigerfortune.other.util.ConcurrentUtil;
import com.tigerfortune.other.util.UiUtil;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TigrAnimatorHandler {

    private final static List<Integer> movementViews = List.of(
            R.drawable.tigr_run_default,
            R.drawable.tigr_run_1,
            R.drawable.tigr_run_2,
            R.drawable.tigr_run_3
    );

    private final static List<Integer> jumpViews = List.of(
            R.drawable.tigr_jump_default,
            R.drawable.tigr_jump_1,
            R.drawable.tigr_jump_2,
            R.drawable.tigr_jump_3
    );

    private Tiger tiger;
    private static int counter = 0;  // Индекс текущего кадра анимации

    public void onAnimate(String variant) {
        switch (variant) {
            case "left" -> {
                tiger.view.setImageResource(movementViews.get(counter));
                tiger.view.setScaleX(-1);
            }

            case "right" -> {
                tiger.view.setImageResource(movementViews.get(counter));
                tiger.view.setScaleX(1);
            }

            case "jump" -> tiger.view.setImageResource(jumpViews.get(counter));

            default -> onDefault();
        }

        tiger.view.invalidate();
        increaseCounter();
        UiUtil.mainThread.postDelayed(this::onDefault, 1000L);
    }

    private void onDefault() {
        tiger.view.setImageResource(R.drawable.tigr_default);
        tiger.view.setScaleX(1);
        counter = 0;
    }

    // Увеличиваем счетчик
    private void increaseCounter() {
        if (counter > movementViews.size() - 2) {
            counter = 0;  // Сбрасываем на первый кадр, если достигли конца списка
        }else
            counter++;
    }
}
