package com.tigerfortune.other.layout.manager;


import com.tigerfortune.activity.LevelActivity;
import com.tigerfortune.activity.LoadingActivity;
import com.tigerfortune.R;
import com.tigerfortune.activity.MainActivity;
import com.tigerfortune.other.layout.data.Layout;
import com.tigerfortune.other.layout.size.LayoutSize;
import com.tigerfortune.other.layout.size.enums.LayoutHeight;
import com.tigerfortune.other.layout.size.enums.LayoutWidth;

import java.util.ArrayList;
import java.util.List;


public enum LayoutDefinition {
    /*
    // === Loading ===
    Load(LoadActivity.class, R.layout.activity_load, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 1 ===
    Step1(Step1Activity.class, R.layout.activity_step1, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 2 ===
    Step2_SmallPhone(Step2Activity.class, R.layout.activity_step2_smallphone, "SmallPhone", LayoutHeight.Small, LayoutWidth.Small),
    Step2(Step2Activity.class, R.layout.activity_step2, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 3 ===
    Step3(Step3Activity.class, R.layout.activity_step3, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 4 ===
    Step4(Step4Activity.class, R.layout.activity_step4, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 5 ===
    Step5(Step5Activity.class, R.layout.activity_step5, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 6 ===
    Step6(Step6Activity.class, R.layout.activity_step6, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 7 ===
    Step7(Step7Activity .class, R.layout.activity_step7, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 8 ===
    Step8(Step8Activity .class, R.layout.activity_step8, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Home Screen ===
    Home_screen(HomeScreenActivity.class, R.layout.activity_homescreen, "All Screens",LayoutHeight.High, LayoutWidth.Middle);
     */
    Main_SmallScreen(MainActivity.class, R.layout.activity_main, "basic", LayoutHeight.Small, LayoutWidth.Small),
    Loading_SmallScreen(LoadingActivity.class, R.layout.activity_loading, "basic", LayoutHeight.Small, LayoutWidth.Small),
    Level_SmallScreen(LevelActivity.class, R.layout.level_activity, "basic", LayoutHeight.Small, LayoutWidth.Small);
    public final Class<?> activityClass;
    public Layout layout;
    public final String deviceName;

    LayoutDefinition(Class<?> activityClass, int layoutId, String deviceName, LayoutHeight h, LayoutWidth w) {
        this.activityClass = activityClass;
        this.layout = new Layout(layoutId, new LayoutSize(h, w));
        this.deviceName = deviceName;
    }

    public static List<LayoutDefinition> filterByDeviceName(String name) {
        List<LayoutDefinition> result = new ArrayList<>();
        for (LayoutDefinition def : values()) {
            if (def.deviceName.toLowerCase().contains(name.toLowerCase())) {
                result.add(def);
            }
        }
        return result;
    }

    public static LayoutDefinition getByClassAndSize(Class<?> clazz, LayoutSize size) {
        for (LayoutDefinition def : values()) {
            if (def.activityClass.equals(clazz) && def.layout.layoutSize.equals(size)) {
                return def;
            }
        }
        return null;
    }
    public static void changeLayout(Class<?> targetActivityClass, int newLayoutId) {
        for (LayoutDefinition def : values()) {
            if (def.activityClass.equals(targetActivityClass)) {
                def.layout = new Layout(newLayoutId, def.layout.layoutSize); // Меняем макет на новый
            }
        }
    }

    public static void changeLayoutByName(String name, int newLayoutId) {
        for (LayoutDefinition def : values()) {
            if (def.deviceName.equalsIgnoreCase(name)) {
                def.layout = new Layout(newLayoutId, def.layout.layoutSize); // Меняем макет на новый
            }
        }
    }
}
