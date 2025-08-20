package com.tigerfortune.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tigerfortune.R;
import com.tigerfortune.other.layout.Layoutable;
import com.tigerfortune.other.sound.MusicFabric;
import com.tigerfortune.other.user.UserService;

public class SettingsActivity extends AppCompatActivity implements Layoutable {
    ImageView soundToggler, effectsToggler;
    private UserService userService;
    private MusicFabric musicFabric;
    public static boolean soundState, effectsState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        userService = UserService.getInstance(this);
        soundState = userService.getSoundState();
        effectsState = userService.getEffectState();
        musicFabric = MusicFabric.getInstance(this);

        soundToggler = findViewById(R.id.check_box_sound);
        effectsToggler = findViewById(R.id.check_box_effects);

        soundToggler.setImageResource(soundState ? R.drawable.check_box_off : R.drawable.check_box_on);
        effectsToggler.setImageResource(effectsState ? R.drawable.check_box_off : R.drawable.check_box_on);

        soundToggler.setOnClickListener(v -> toggleSound());
        effectsToggler.setOnClickListener(v -> toggleEffects());
    }

    public void toggleSound() {
        soundState = !soundState;
        soundToggler.setImageResource(soundState ? R.drawable.check_box_off : R.drawable.check_box_on);
        userService.setSoundState(soundState);
        musicFabric.toggleSound();
    }

    public void toggleEffects() {
        effectsState = !effectsState;
        effectsToggler.setImageResource(effectsState ? R.drawable.check_box_off : R.drawable.check_box_on);
        userService.setEffectState(effectsState);
    }
}