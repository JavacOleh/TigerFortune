package com.tigerfortune.other.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class UiUtil {
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

    public static void loadActivityFinishCurrent(AppCompatActivity currentActivity, Class<? extends AppCompatActivity> newActivity) {
        Intent intent = new Intent(currentActivity, newActivity);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density + 0.5f);
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
