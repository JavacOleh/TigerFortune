package com.tigerfortune.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerfortune.R;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.util.UiUtil;

public class MenuActivity extends AppCompatActivity implements Layoutable {
    TextView restart, settings, backToMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        restart = findViewById(R.id.buttonRestart);
        settings = findViewById(R.id.buttonSettings);
        backToMainMenu = findViewById(R.id.buttonExit);

        restart.setOnClickListener(v -> {
            LoadingActivity.redirectClass = LevelActivity.class;
            UiUtil.loadActivityFinishCurrent(this, LoadingActivity.class);
        });

        backToMainMenu.setOnClickListener(v ->
                UiUtil.loadActivityFinishCurrent(this, MainActivity.class));

        settings.setOnClickListener(v ->
                UiUtil.loadActivityPauseCurrent(this, SettingsActivity.class));
    }
}