package com.tigerfortune;

import com.tigerfortune.other.util.ConcurrentUtil;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    public static long waitFor = 100L;
    public static long waitCycl = 300L;
    private Thread thread;
    public List<Runnable> runnables;

    private static Engine engine;

    private Engine() {
        runnables = new ArrayList<>();
        thread = new Thread(() -> {
            do {
                for (int i = 0; i < runnables.size(); i++) {
                    var a = runnables.get(i);
                    a.run();

                    ConcurrentUtil.sleep(waitFor);
                }

                ConcurrentUtil.sleep(waitCycl);
            } while (true);
        });
    }

    public void addToRunnablArr(Runnable runnable) {
        if (runnable != null) {
            runnables.add(runnable);
        }
    }

    public void rmFromRunnablArr(int ind) {
        if (ind > 0 && ind < runnables.size()) {
            runnables.remove(ind);
        }
    }

    public static Engine getInstance() {
        if (engine == null)
            engine = new Engine();

        return engine;
    }

    public void onStart() {
        if (thread != null) {
            if (!thread.isAlive())
                thread.start();
        }
    }
}
