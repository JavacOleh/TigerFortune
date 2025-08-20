package com.tigerfortune.other.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import com.tigerfortune.R;
import com.tigerfortune.other.user.UserService;
import com.tigerfortune.other.util.UiUtil;

public class MusicFabric {
    private static MusicFabric musicFabric;
    private UserService userService;
    private AppCompatActivity context;

    // MediaPlayer для фоновой музыки и для эффектов
    private MediaPlayer backgroundMusicPlayer;
    private MediaPlayer effectPlayer;

    private static final long stopEffectDelay = 200L;

    // Конструктор
    private MusicFabric(AppCompatActivity context) {
        this.context = context;
        // Инициализация MediaPlayer'ов
        backgroundMusicPlayer = new MediaPlayer();
        effectPlayer = new MediaPlayer();
    }

    public static MusicFabric getInstance(AppCompatActivity context) {
        if (musicFabric == null)
            musicFabric = new MusicFabric(context);
        return musicFabric;
    }

    // Включить или выключить музыку
    public void toggleSound() {
        userService = UserService.getInstance(context);
        if (userService.getSoundState()) {
            // Если звук включен, запускаем фоновую музыку
            startBackgroundMusic(R.raw.arcade_loop_simple, true);
        } else {
            // Если звук выключен, останавливаем фоновую музыку
            stopBackgroundMusic();
        }
    }

    // Воспроизвести звук эффекта (монета)
    public void playCoinTake() {
        userService = UserService.getInstance(context);
        if (userService.getEffectState()) {
            playEffect(R.raw.coin_pickup_sound);
        }
    }

    // Воспроизвести звук эффекта (суши)
    public void playSushiTake() {
        userService = UserService.getInstance(context);
        if (userService.getEffectState()) {
            playEffect(R.raw.coin_pickup_sound_v2);
        }
    }

    // Метод для воспроизведения фоновой музыки
    private void startBackgroundMusic(int resId, boolean looping) {
        try {
            // Сбросить и настроить новый MediaPlayer
            backgroundMusicPlayer.reset();
            backgroundMusicPlayer.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + resId));
            backgroundMusicPlayer.prepare();
            backgroundMusicPlayer.setLooping(looping);
            backgroundMusicPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Остановить фоновую музыку
    private void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.release();
        }
    }

    // Метод для воспроизведения звукового эффекта
    private void playEffect(int resId) {
        try {
            effectPlayer.reset();
            effectPlayer.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + resId));
            effectPlayer.prepare();
            effectPlayer.start();

            // Остановить эффект через установленную задержку
            UiUtil.mainThread.postDelayed(() -> {
                if (effectPlayer.isPlaying()) {
                    effectPlayer.stop();
                    effectPlayer.release();
                }
            }, stopEffectDelay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
