package com.tigerfortune.other.layout.manager;


import com.tigerfortune.other.layout.service.LayoutService;

public class LayoutManager {
    private final LayoutService service;

    public LayoutManager(LayoutService service) {
        this.service = service;
    }

    public void registerAll() {
        for (LayoutDefinition def : LayoutDefinition.values()) {
            service.addLayout(def.activityClass, def.layout);
        }
    }
}
