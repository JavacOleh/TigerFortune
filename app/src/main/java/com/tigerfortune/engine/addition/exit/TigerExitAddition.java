package com.tigerfortune.engine.addition.exit;
import android.view.ViewGroup;
import com.tigerfortune.activity.LoadingActivity;
import com.tigerfortune.activity.MainActivity;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
import com.tigerfortune.engine.addition.common.ViewCoordinates;
import com.tigerfortune.other.util.UiUtil;

public class TigerExitAddition extends TigerAddition implements Runnable {
    private static TigerExitAddition instance;

    private TigerExitAddition(Tiger tiger) {
        super(tiger);
    }

    public static TigerExitAddition getInstance(Tiger tiger) {
        if (instance == null)
            instance = new TigerExitAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
        var tigrParams = ((ViewGroup.MarginLayoutParams) tiger.view.getLayoutParams());
        var exitParams = ((ViewGroup.MarginLayoutParams) tiger.levelActivity.exitView.getLayoutParams());
        var tigerStartX = tigrParams.leftMargin;
        var exitStartX = tiger.levelActivity.ground.getWidth() - tiger.levelActivity.exitView.getWidth();
        var exitTop = tiger.levelActivity.exitView.getTop();

        var coords = new ViewCoordinates(tiger.levelActivity.exitView, tiger);

        if (tigerStartX >= exitStartX && (tigrParams.bottomMargin <= exitParams.bottomMargin + exitParams.height)) {
            LoadingActivity.redirectClass = MainActivity.class;
            UiUtil.loadActivityFinishCurrent(tiger.levelActivity, LoadingActivity.redirectClass);
        }
    }

}