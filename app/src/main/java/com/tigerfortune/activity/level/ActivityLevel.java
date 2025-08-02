package com.tigerfortune.activity.level;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.R;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.view.GroundView;
import com.tigerfortune.other.view.MyHSV;

public class ActivityLevel extends AppCompatActivity implements Layoutable {
    public ConstraintLayout constraintMain, constraintInside;
    public MyHSV gameScroller;
    public ImageView tigr;
    public GroundView ground;
    public final int groundCount = 10;
    public final int groundItmWidthInDP = 100;
    public final int groundItmBackgroundSRC = R.drawable.background_ground1_item;
    private ActivityLevelAddition activityLevelAddition;

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

        ground = findViewById(R.id.ground);
        ground.setData(groundCount, groundItmBackgroundSRC, groundItmWidthInDP);

        activityLevelAddition = new ActivityLevelAddition(this);
        activityLevelAddition.init();
    }
}