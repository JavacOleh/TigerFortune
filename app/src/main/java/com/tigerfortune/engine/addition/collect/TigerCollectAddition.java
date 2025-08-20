package com.tigerfortune.engine.addition.collect;

import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.tigerfortune.R;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
import com.tigerfortune.engine.addition.common.ViewCoordinates;
import com.tigerfortune.other.sound.MusicFabric;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.UiUtil;

public class TigerCollectAddition extends TigerAddition implements Runnable {
    private static TigerCollectAddition instance;
    public View view;
    private MusicFabric musicFabric;

    private TigerCollectAddition(Tiger tiger) {
        super(tiger);
        musicFabric = MusicFabric.getInstance(tiger.levelActivity);
    }

    public static TigerCollectAddition getInstance(Tiger tiger) {
        if (instance == null)
            instance = new TigerCollectAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
        if (view instanceof ImageView view1) {
            ViewCoordinates viewCoordinates = new ViewCoordinates(view1, tiger);

            var coin = ContextCompat.getDrawable(tiger.levelActivity, R.drawable.coin);
            var sushi = ContextCompat.getDrawable(tiger.levelActivity, R.drawable.sushi);
            var userService = UserService.getInstance(tiger.levelActivity);


            if (viewCoordinates.isTigerInElHorizontally() && (viewCoordinates.isTigerAndElOnSameGround())) {
                if (UiUtil.isDrawablesSame(view1.getDrawable(), coin)) {
                    musicFabric.playCoinTake();
                    userService.setCollectedCoins(userService.getCollectedCoins() + 1);
                } else if (UiUtil.isDrawablesSame(view1.getDrawable(), sushi)) {
                    musicFabric.playSushiTake();
                    userService.setCollectedCoins(userService.getCollectedCoins() + 10);
                }

                mainThread.post(() -> {
                    tiger.levelActivity.coins_text.setText(String
                            .valueOf(userService.getCollectedCoins()));
                    tiger.levelActivity.collectable.remove(view);
                    tiger.levelActivity.constraintInside.removeView(view);
                });
            }
        }
    }

}