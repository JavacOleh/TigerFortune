package com.tigerfortune.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerfortune.R;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.engine.Engine;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.UiUtil;

public class MainActivity extends AppCompatActivity implements Layoutable {
    TextView play, settings, reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        play = findViewById(R.id.buttonPlay);
        reset = findViewById(R.id.buttonResetResults);
        settings = findViewById(R.id.buttonSETTINGS);

        play.setOnClickListener(view -> {
            LoadingActivity.redirectClass = LevelActivity.class;
            UiUtil.loadActivityFinishCurrent(this, LoadingActivity.class);
        });

        reset.setOnClickListener(v -> {
            StaticData.currentLevel = 0;
            UserService.getInstance(this).resetUser();
        });

        settings.setOnClickListener(v -> UiUtil.loadActivityPauseCurrent(this, SettingsActivity.class));

        Engine.getInstance().rmFromRunnablArr(1);
    }
}