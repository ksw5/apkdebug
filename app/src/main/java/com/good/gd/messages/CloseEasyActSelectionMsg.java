package com.good.gd.messages;

/* loaded from: classes.dex */
public class CloseEasyActSelectionMsg {
    private boolean isDelegateListEmpty;

    public CloseEasyActSelectionMsg(boolean z) {
        this.isDelegateListEmpty = false;
        this.isDelegateListEmpty = z;
    }

    public boolean isDelegateListEmpty() {
        return this.isDelegateListEmpty;
    }

    public String toString() {
        return "CloseEasyActSelectionMsg";
    }
}
