package com.tigerfortune.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tigerfortune.R;
import com.tigerfortune.engine.Engine;
import com.tigerfortune.dto.StaticData;
import com.tigerfortune.dto.level.ZhestyListener;
import com.tigerfortune.dto.level.LevelHandler;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.sound.MusicFabric;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.OutlineTextView;
import com.tigerfortune.other.util.UiUtil;
import com.tigerfortune.other.view.ExitView;
import com.tigerfortune.other.view.GroundView;
import com.tigerfortune.other.view.MyHSV;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LevelActivity extends AppCompatActivity implements Layoutable {
    public ConstraintLayout constraintMain, constraintInside;
    public MyHSV gameScroller;
    public ImageView tiger;
    public GroundView ground;
    public ExitView exitView;
    public OutlineTextView coins_text;
    public MusicFabric musicFabric;
    public ImageButton pause_button;
    public int groundCount;
    public int groundItmWidthInDP;
    public int groundItmBackgroundSRC;
    public Map<String, ArrayList<Integer>> entityTopPosMap = new LinkedHashMap<>();
    public List<View> obstacles = new ArrayList<>();
    public List<View> collectable = new ArrayList<>();
    public List<View> decorates = new ArrayList<>();
    public LevelHandler levelHandler;
    private ZhestyListener zhestyListener;
    public UserService userService;
    public Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);
        init();
        musicFabric = MusicFabric.getInstance(this);
        userService = UserService.getInstance(this);
        coins_text.setText(String.valueOf(userService.getCollectedCoins()));
        pause_button.setOnClickListener(v -> UiUtil.loadActivityFinishCurrent(this, MenuActivity.class));

//        tiger.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                // Убираем слушателя после выполнения изменений
//                tiger.getViewTreeObserver().removeOnPreDrawListener(this);
//
//                // Изменяем leftMargin только после того, как вьюшка будет готова
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tiger.getLayoutParams();
//                layoutParams.leftMargin = 100;  // Применяем отступ
//                tiger.setLayoutParams(layoutParams);  // Обновляем параметры
//
//                return true;  // Возвращаем true, чтобы продолжить отрисовку
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        StaticData.allowedToJump = false;
        new Handler(Looper.getMainLooper()).postDelayed(() -> StaticData.allowedToJump = true, 1500L);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tiger.getLayoutParams();
        layoutParams.leftMargin = UiUtil.dpToPx(100);
        tiger.setLayoutParams(layoutParams);
        musicFabric.toggleSound();
    }


    public void init() {
        constraintMain = findViewById(R.id.constraintMain);
        constraintInside = findViewById(R.id.constraintInside);
        gameScroller = findViewById(R.id.gameScroller);
        tiger = findViewById(R.id.tiger);
        coins_text = findViewById(R.id.coins_text);
        pause_button = findViewById(R.id.pause_button);
        exitView = findViewById(R.id.exit_portal);
        exitView.startAnimation();

        engine = Engine.getInstance();
        engine.onStart();

        levelHandler = new LevelHandler(this);
        levelHandler.buildLandshaft(StaticData.currentLevel);

        ground = findViewById(R.id.ground);
        ground.setData(groundCount, groundItmBackgroundSRC, groundItmWidthInDP);

        zhestyListener = new ZhestyListener(this);
        zhestyListener.init();
    }
}