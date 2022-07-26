package com.good.gd.ndkproxy.ui.event;

/* loaded from: classes.dex */
public class BBDAppLockUpdateEventBuilder {
    private BBDAppLockUpdateEvent mEvent;

    public BBDAppLockUpdateEventBuilder() {
        this.mEvent = null;
        this.mEvent = new BBDAppLockUpdateEvent();
    }

    public BBDAppLockUpdateEvent build() {
        return this.mEvent;
    }

    public BBDAppLockUpdateEventBuilder setBundleId(String str) {
        this.mEvent.setBundleId(str);
        return this;
    }

    public BBDAppLockUpdateEventBuilder setDownloadLocation(String str) {
        this.mEvent.setDownloadLocation(str);
        return this;
    }

    public BBDAppLockUpdateEventBuilder setFailureReason(int i) {
        this.mEvent.setFailureReason(i);
        return this;
    }

    public BBDAppLockUpdateEventBuilder setName(String str) {
        this.mEvent.setName(str);
        return this;
    }

    public BBDAppLockUpdateEventBuilder setVersion(String str) {
        this.mEvent.setVersion(str);
        return this;
    }
}
