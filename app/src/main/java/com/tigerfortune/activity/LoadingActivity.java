package com.tigerfortune.activity;


import static com.tigerfortune.other.util.UiUtil.mainThread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.util.UiUtil;

public class LoadingActivity extends AppCompatActivity implements Layoutable {
    public static Class<? extends AppCompatActivity> redirectClass = MainActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (redirectClass == LevelActivity.class)
            mainThread.post(() -> UiUtil.restartAppAndStartActivity(this, LevelActivity.class));
        else
            mainThread.postDelayed(() -> UiUtil.loadActivityFinishCurrent(this, redirectClass), 500L);
    }
}