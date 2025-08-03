package com.tigerfortune.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.util.UiUtil;

public class ActivityMain extends AppCompatActivity implements Layoutable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        UiUtil.loadActivityFinishCurrent(this, ActivityLevel.class);
    }
}