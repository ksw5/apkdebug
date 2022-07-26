package com.good.gd.ui.utils;

/* loaded from: classes.dex */
public class ActionDispatcher implements ScrollEventListener {
    private Actions actions;

    @Override // com.good.gd.ui.utils.ScrollEventListener
    public void onBottomReached() {
        this.actions.onControlEnable();
    }

    public void setActionHandler(Actions actions) {
        this.actions = actions;
    }
}
