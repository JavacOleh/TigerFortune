package com.tigerfortune.other.user;


public class User {
    private static User instance;
    public boolean soundState, effectsState;
    public int currentLevel;
    public int collectedCoins;

    private User() {}

    public static User getInstance() {
        if(instance == null)
            instance = new User();
        return instance;
    }

    public static User getDefaultUser() {
        var user = new User();
        user.currentLevel = 0;
        user.soundState = false;
        user.effectsState = false;
        user.collectedCoins = 0;

        return user;
    }
}
