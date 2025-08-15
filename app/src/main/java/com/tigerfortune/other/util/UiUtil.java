package com.tigerfortune.other.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class UiUtil {
    public static final Handler mainThread = new Handler(Looper.getMainLooper());
    public static void update_element_inRecyclerView(int ind, RecyclerView recyclerView) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(ind);
        if (viewHolder != null) {
            View gameView = viewHolder.itemView;

            gameView.invalidate();
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // Получаем размеры экрана
        context.getSystemService(WindowManager.class).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    public static void loadFragment(AppCompatActivity activity, Fragment fragment, int fragmentContainer) {
        if (activity != null) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(fragmentContainer, fragment); //R.id.fragment_load_container or else
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }

    //    public static void loadActivityFinishCurrent(AppCompatActivity currentActivity, Class<? extends AppCompatActivity> newActivity) {
//        Intent intent = new Intent(currentActivity, newActivity);
//        currentActivity.startActivity(intent);
//        currentActivity.finish();
//    }
    public static void loadActivityFinishCurrent(AppCompatActivity currentActivity, Class<? extends AppCompatActivity> newActivity) {
        Intent intent = new Intent(currentActivity, newActivity);
        // Очистка стека активностей и запуск новой активности
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        currentActivity.finish();

    }
    public static void restartAppAndStartActivity(Context context, Class<? extends AppCompatActivity> targetActivity) {
        // Получаем Intent для перезапуска приложения
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Очищаем стек активностей
            context.startActivity(intent); // Запускаем перезапуск приложения
        }

        // Перезапуск с нужной активностью
        Intent targetIntent = new Intent(context, targetActivity);
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Очищаем старые активности и создаем новый стек
        context.startActivity(targetIntent); // Запускаем целевую активность

        // Закрываем приложение
        System.exit(0); // Завершаем процесс приложения
    }

    public static void restartApp(Context context) {
        // Перезапуск приложения
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
        System.exit(0);  // Завершаем процесс приложения
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public static boolean isDrawablesSame(Drawable drawable1, Drawable drawable2) {
        if (drawable1 != null && drawable2 != null) {
            // Проверяем, что оба drawable - это BitmapDrawable
            if (drawable1 instanceof BitmapDrawable && drawable2 instanceof BitmapDrawable) {
                Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();

                // Сравниваем битмапы (можно использовать метод .sameAs())
                return bitmap1.sameAs(bitmap2);
            }
        }
        return false;
    }

    public static void loadActivityPauseCurrent(AppCompatActivity currentActivity, Class<? extends AppCompatActivity> newActivity) {
        Intent intent = new Intent(currentActivity, newActivity);
        currentActivity.startActivity(intent);
    }

    public static void loadActivityFromFragment(Fragment fragment, Class<? extends AppCompatActivity> activityClass) {
        if (fragment == null || fragment.getContext() == null) return;

        Intent intent = new Intent(fragment.getContext(), activityClass);
        fragment.startActivity(intent);
    }

}
