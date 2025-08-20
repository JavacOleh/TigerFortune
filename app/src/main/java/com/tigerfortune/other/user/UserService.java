package com.tigerfortune.other.user;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class UserService {
    public static final String FILE_NAME = "user.txt";
    private static UserService userService;
    private User user;
    private Activity activity;

    private UserService(Activity activity) {
        this.activity = activity;
        user = User.getInstance();
        loadUser();
    }

    public static UserService getInstance(Activity activity) {
        if (userService == null)
            userService = new UserService(activity);
        return userService;
    }

    private void saveUser() {
        try (FileOutputStream fos = activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             DataOutputStream dos = new DataOutputStream(fos)) {

            dos.writeBoolean(user.soundState);
            dos.writeBoolean(user.effectsState);
            dos.writeInt(user.currentLevel);
            dos.writeInt(user.collectedCoins);

            dos.flush();
        } catch (IOException e) {
            Log.e("UserService", "Error saving user: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void loadUser() {
        try (FileInputStream fis = activity.openFileInput(FILE_NAME);
             DataInputStream dis = new DataInputStream(fis)) {

            if (fis.available() > 0) {
                user.soundState = dis.readBoolean();
                user.effectsState = dis.readBoolean();
                user.currentLevel = dis.readInt();
                user.collectedCoins = dis.readInt();
            } else {
                // Если файл пустой, задаем значение по умолчанию
                user = User.getDefaultUser();
                saveUser();
            }
        } catch (IOException e) {
            Log.e("UserService", "Error loading user: " + Arrays.toString(e.getStackTrace()));
            saveUser();
        }
    }

    public void resetUser() {
        loadUser();
        user.collectedCoins = 0;
        user.currentLevel = 0;
        saveUser();
    }

    public void setCollectedCoins(int collectedCoins) {
        user.collectedCoins = collectedCoins;
        saveUser();
    }
    public void setCurrentLevel(int level) {
        user.currentLevel = level;
        saveUser();
    }
    public void setSoundState(boolean state) {
        user.soundState = state;
        saveUser();
    }
    public void setEffectState(boolean state) {
        user.effectsState = state;
        saveUser();
    }

    public int getCollectedCoins() {
        loadUser();
        return user.collectedCoins;
    }
    public int getCurrentLevel() {
        loadUser();
        return user.currentLevel;
    }
    public boolean getSoundState() {
        loadUser();
        return user.soundState;
    }
    public boolean getEffectState() {
        loadUser();
        return user.effectsState;
    }
}
