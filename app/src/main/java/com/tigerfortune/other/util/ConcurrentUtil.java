package com.tigerfortune.other.util;

import java.lang.System;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentUtil {
    public static long awaitMillis = 100L;
    public static long sleepMillis = 100L;
    public static Thread thread;

    public static void await(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
            Thread.sleep(awaitMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void await(Runnable runnable) {
        try {
            runnable.run();
            Thread.sleep(awaitMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void await(AtomicInteger value, int badValue) {
        while (value.get() == badValue) {
            // Можно добавить паузу или ожидание, чтобы избежать перегрузки процессора
            try {
                Thread.sleep(awaitMillis); // Ждем, пока значение не изменится
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
