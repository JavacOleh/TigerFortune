package com.tigerfortune.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.R;
import com.tigerfortune.engine.Engine;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.level.LevelAddition;
import com.tigerfortune.dto.level.LevelHandler;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.view.GroundView;
import com.tigerfortune.other.view.MyHSV;

import java.util.ArrayList;
import java.util.List;

public class ActivityLevel extends AppCompatActivity implements Layoutable {
    public ConstraintLayout constraintMain, constraintInside;
    public MyHSV gameScroller;
    public ImageView tigr;
    public GroundView ground;
    public int groundCount;
    public int groundItmWidthInDP;
    public int groundItmBackgroundSRC;
    public List<View> obstacles = new ArrayList<>();
    public List<View> decorates = new ArrayList<>();
    public LevelHandler levelHandler;
    private LevelAddition levelAddition;
    public Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);
        init();
    }

    public void init() {
        constraintMain = findViewById(R.id.constraintMain);
        constraintInside = findViewById(R.id.constraintInside);
        gameScroller = findViewById(R.id.gameScroller);
        tigr = findViewById(R.id.tigr);

        engine = Engine.getInstance();
        engine.onStart();

        levelHandler = new LevelHandler(this);
        levelHandler.buildLandshaft(StaticData.currentLevel - 1);

        ground = findViewById(R.id.ground);
        ground.setData(groundCount, groundItmBackgroundSRC, groundItmWidthInDP);

        levelAddition = new LevelAddition(this);
        levelAddition.init();

    }
}