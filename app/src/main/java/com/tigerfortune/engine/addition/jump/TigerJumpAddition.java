package com.tigerfortune.engine.addition.jump;

import static com.tigerfortune.dto.StaticData.finalJumpHeight;
import static com.tigerfortune.dto.StaticData.jumpHeight;
import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.tigerfortune.R;
import com.tigerfortune.dto.tigr.Tiger;
import com.tigerfortune.engine.addition.TigerAddition;
import com.tigerfortune.engine.addition.common.ViewCoordinates;
import com.tigerfortune.other.util.DoOnce;
import com.tigerfortune.other.util.UiUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class TigerJumpAddition extends TigerAddition implements Runnable {
    private int pogreshonstY = 5;
    private static Map<ImageView, Boolean> viewsWhereItsPossibleToEbnutsaGolovoj = new LinkedHashMap<>();
    public View view;
    private static TigerJumpAddition instance;
    private static DoOnce addSushiOnce = new DoOnce();
    private static DoOnce setJumpHeight = new DoOnce();

    private TigerJumpAddition(Tiger tiger) {
        super(tiger);
    }

    public static TigerJumpAddition getInstance(Tiger tiger) {
        if (instance == null)
            instance = new TigerJumpAddition(tiger);
        return instance;
    }

    @Override
    public void run() {
        if (view instanceof ImageView view1) {
            var coords = new ViewCoordinates(view1, tiger);

            var neededDrawable = ContextCompat.getDrawable(tiger.levelActivity, R.drawable.ebnis_golovoj_s_podnizu);

            if (UiUtil.isDrawablesSame(view1.getDrawable(), neededDrawable)) {
                if (!viewsWhereItsPossibleToEbnutsaGolovoj.containsKey(view1))
                    viewsWhereItsPossibleToEbnutsaGolovoj.put(view1, true);

                var check = viewsWhereItsPossibleToEbnutsaGolovoj.get(view1);

                setJumpMaxHeight(view1, coords);

                if (coords.isTigerInElHorizontally() && coords.isTigerUnderElButCloseToBottom(pogreshonstY)) {
                    addSushiOnce.actionOnce(() -> {
                        if (Boolean.TRUE.equals(check)) {
                            //Можно ебнуться головой
                            mainThread.post(() -> tiger.levelActivity.levelHandler.addFood(view1));
                            viewsWhereItsPossibleToEbnutsaGolovoj.remove(view1);
                            viewsWhereItsPossibleToEbnutsaGolovoj.put(view1, false);
                        }
                    });
                } else {
                    addSushiOnce.hasDon = false;
                }
            }else {
                setJumpMaxHeight(view1, coords);
            }
        }
    }

    public void setJumpMaxHeight(ImageView view1, ViewCoordinates coordinates) {
        if (coordinates.isTigerInElHorizontally() && coordinates.isTigerUnderEl())
            setJumpHeight.actionOnce(() -> jumpHeight = jumpHeight - view1.getHeight() + pogreshonstY);
        else {
            setJumpHeight.hasDon = false;
            jumpHeight = finalJumpHeight;
        }
    }
}
