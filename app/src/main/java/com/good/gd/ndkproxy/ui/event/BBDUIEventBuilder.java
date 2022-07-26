package com.good.gd.ndkproxy.ui.event;

/* loaded from: classes.dex */
public class BBDUIEventBuilder {
    private BBDUIUpdateEvent mEvent;

    public BBDUIEventBuilder(UIEventType uIEventType) {
        this.mEvent = null;
        this.mEvent = new BBDUIUpdateEvent(uIEventType);
    }

    public BBDUIEventBuilder addAcknowledge(Runnable runnable) {
        this.mEvent.setAcknowledgeCb(runnable);
        return this;
    }

    public BBDUIEventBuilder addText(String str) {
        this.mEvent.setText(str);
        return this;
    }

    public BBDUIEventBuilder addTitle(String str) {
        this.mEvent.setTitle(str);
        return this;
    }

    public BBDUIUpdateEvent build() {
        return this.mEvent;
    }

    public BBDUIEventBuilder successful(boolean z) {
        this.mEvent.setSuccessful(z);
        return this;
    }
}
