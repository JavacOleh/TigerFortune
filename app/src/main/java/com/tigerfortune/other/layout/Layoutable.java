package com.tigerfortune.other.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.tigerfortune.other.layout.service.LayoutService;
import com.tigerfortune.other.layout.size.LayoutSize;
import com.tigerfortune.other.layout.size.enums.LayoutHeight;
import com.tigerfortune.other.layout.size.enums.LayoutWidth;
import com.tigerfortune.other.util.UiUtil;

public interface Layoutable {
    LayoutService layoutService = LayoutService.getInstance();
    default void applyLayout(AppCompatActivity activity, Integer activityInfo) {
        //Log.i("Enum:", LayoutSize.detectWidthAndHeight(activity).toString());
        if(activityInfo != null)
            activity.setRequestedOrientation(activityInfo);

        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass()); // ← это ключ!
        activity.setContentView(layoutId);
    }
    default void applyLayout(AppCompatActivity activity) {
        applyLayout(activity, null);
    }

    default void applyLayoutFragmentMain(AppCompatActivity activity, Fragment fragment, Integer activityInfo) {
        if(activityInfo != null)
            activity.setRequestedOrientation(activityInfo);
        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass());
        UiUtil.loadFragment(activity, fragment, layoutId);
        //activity.setContentView(layoutId);
    }
    default void applyLayoutFragment(AppCompatActivity activity, Fragment fragment) {
        applyLayoutFragmentMain(activity, fragment, null);
    }

    default int getCellSizeDp(AppCompatActivity activity) {
        var layoutSize = LayoutSize.detect(activity);
        var h = layoutSize.height;
        var w = layoutSize.width;

        // Пример логики
        if (w == LayoutWidth.Middle && h == LayoutHeight.Big) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.High && h == LayoutHeight.High) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.Middle && h == LayoutHeight.High) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.Small || h == LayoutHeight.Small) return UiUtil.dpToPx(activity, 45);

        // Фоллбэк
        return UiUtil.dpToPx(activity, 70);
    }
}
