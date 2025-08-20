package com.tigerfortune;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.tigerfortune.other.util.UiUtil;

public class Main extends Application {
    public static Main instance;
    private int activityReferences = 0;
    private boolean isChangingConfigurations = false;
    private boolean isAppInBackground = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStarted(Activity activity) {
                if (++activityReferences == 1 && !isChangingConfigurations) {
                    // Приложение пришло на передний план
                    if (isAppInBackground) {
                        // Здесь ты можешь сделать действия, если нужно перезапустить приложение
                        // Например, перезапуск с нуля
                        // Но для этого можно просто не делать ничего, если требуется только переинициализация
                        isAppInBackground = false;
                    }
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                isChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isChangingConfigurations) {
                    isAppInBackground = true;
                    UiUtil.restartApp(activity);
                    // Здесь можно вызвать завершение приложения, если нужно
                    // Например, принудительно завершить процесс:
                }
            }

            // Остальные методы жизненного цикла можно оставить пустыми
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
            @Override public void onActivityResumed(Activity activity) {}
            @Override public void onActivityPaused(Activity activity) {}
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override public void onActivityDestroyed(Activity activity) {}
        });
    }
}
