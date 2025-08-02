package com.tigerfortune.other.layout.service;

import android.content.Context;
import android.util.Log;

import com.tigerfortune.other.layout.data.Layout;
import com.tigerfortune.other.layout.manager.LayoutDefinition;
import com.tigerfortune.other.layout.manager.LayoutManager;
import com.tigerfortune.other.layout.size.LayoutSize;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class LayoutService {
    private static LayoutService instance;
    private static LayoutManager layoutManager;
    private final Map<Class<?>, Map<LayoutSize, Integer>> layoutMap = new LinkedHashMap<>();

    private LayoutService() {
    }

    public static LayoutService getInstance() {
        if (instance == null) {
            instance = new LayoutService();

            if (layoutManager == null)
                layoutManager = new LayoutManager(instance);
            layoutManager.registerAll();
        }
        return instance;
    }

    public void addLayout(Class<?> clazz, Layout layout) {
        layoutMap
                .computeIfAbsent(clazz, k -> new HashMap<>())
                .put(layout.layoutSize, layout.id);
    }

    public Integer getLayout(Class<?> clazz, LayoutSize layoutSize) {
        Map<LayoutSize, Integer> layouts = layoutMap.get(clazz);
        return layouts != null ? layouts.get(layoutSize) : null;
    }

    public Integer getLayoutForCurrent(Context context, Class<?> clazz) {
        LayoutSize currentSize = LayoutSize.detect(context);
        Log.i("enum: ", "Trying exact layout: " + currentSize);

        Integer layoutId = getLayout(clazz, currentSize);
        if (layoutId != null) {
            return layoutId;
        }

        Log.w("enum: ", "Exact layout not found. Trying closest match.");

        // Получаем все доступные варианты для этого класса
        Map<LayoutSize, Integer> layouts = layoutMap.get(clazz);
        if (layouts != null && !layouts.isEmpty()) {
            LayoutSize closestSize = null;
            int minDistance = Integer.MAX_VALUE;

            for (LayoutSize size : layouts.keySet()) {
                int dist = currentSize.distanceTo(size);
                if (dist < minDistance) {
                    minDistance = dist;
                    closestSize = size;
                }
            }

            if (closestSize != null) {
                layoutId = layouts.get(closestSize);
                LayoutDefinition matched = LayoutDefinition.getByClassAndSize(clazz, closestSize);
                if (matched != null) {
                    Log.w("enum: ", "Using closest layout for " + matched.deviceName + " → " + closestSize);
                } else {
                    Log.w("enum: ", "Using closest layout → " + closestSize);
                }
                return layoutId;
            }
        }

        Log.e("enum: ", "No layout found at all. Returning fallback.");
        return layouts != null && !layouts.isEmpty()
                ? layouts.values().iterator().next()
                : null;
    }

}
