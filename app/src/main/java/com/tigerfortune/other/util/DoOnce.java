package com.tigerfortune.other.util;

public class DoOnce {
    public boolean hasDon = false;

    public void actionOnce(Runnable runnable) {
        if (runnable != null && !hasDon) {
            runnable.run();

            hasDon = true;
        }
    }
}
