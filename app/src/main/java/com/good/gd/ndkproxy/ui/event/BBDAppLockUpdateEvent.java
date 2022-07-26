package com.good.gd.ndkproxy.ui.event;

/* loaded from: classes.dex */
public class BBDAppLockUpdateEvent extends BBDUIUpdateEvent {
    private int failureReason;
    private String bundleId = "";
    private String name = "";
    private String version = "";
    private String downloadLocation = "";

    /* JADX INFO: Access modifiers changed from: package-private */
    public BBDAppLockUpdateEvent() {
        super(UIEventType.UI_UPDATE_INTERAPP_LOCK);
    }

    public String getBundleId() {
        return this.bundleId;
    }

    public String getDownloadLocation() {
        return this.downloadLocation;
    }

    public int getFailureReason() {
        return this.failureReason;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setBundleId(String str) {
        this.bundleId = str;
    }

    public void setDownloadLocation(String str) {
        this.downloadLocation = str;
    }

    public void setFailureReason(int i) {
        this.failureReason = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
