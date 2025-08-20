package com.tigerfortune.other.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.tigerfortune.R;

public class MusicService {
    private static MediaPlayer backgroundPlayer;
    private static MediaPlayer effectPlayer;
    private static Context context;

    public MusicService(Context context) {
        MusicService.context = context.getApplicationContext();  // Используем applicationContext для предотвращения утечек памяти
    }

    // Запуск фоновой музыки
    public void startBackgroundMusic(int resId, boolean looping) {
        if (backgroundPlayer == null) {
            backgroundPlayer = MediaPlayer.create(context, resId);
            backgroundPlayer.setLooping(looping);
        }
        if (!backgroundPlayer.isPlaying()) {
            backgroundPlayer.start();
        }
    }

    // Остановка фоновой музыки
    public void stopBackgroundMusic() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
            backgroundPlayer.release();
            backgroundPlayer = null;
        }
    }

    // Запуск звукового эффекта
    public void playEffect(int resId) {
        if (effectPlayer == null) {
            effectPlayer = MediaPlayer.create(context, resId);
        } else {
            effectPlayer.reset();
            effectPlayer = MediaPlayer.create(context, resId);  // Создаем новый эффект, если предыдущий был завершен
        }
        effectPlayer.start();
    }

    // Остановка звукового эффекта
    public void stopEffect() {
        if (effectPlayer != null && effectPlayer.isPlaying()) {
            effectPlayer.stop();
            effectPlayer.release();
            effectPlayer = null;
        }
    }
}
