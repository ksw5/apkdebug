package com.good.gd.ndkproxy.ui.data.base;

/* loaded from: classes.dex */
public enum BBDUIState {
    STATE_CREATED(0),
    STATE_ACTIVE(1),
    STATE_PAUSED(2),
    STATE_DESTROYED(3);
    
    private int priority;

    BBDUIState(int i) {
        this.priority = i;
    }

    public int getPriority() {
        return this.priority;
    }
}
