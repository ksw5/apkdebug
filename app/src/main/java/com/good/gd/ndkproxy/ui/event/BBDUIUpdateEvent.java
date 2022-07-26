package com.good.gd.ndkproxy.ui.event;

/* loaded from: classes.dex */
public class BBDUIUpdateEvent {
    private Runnable mRunnable;
    private boolean mSuccess;
    private String mText;
    private String mTitle;
    private UIEventType mType;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BBDUIUpdateEvent(UIEventType uIEventType) {
        this.mType = uIEventType;
    }

    public Runnable getAcknowledgeCb() {
        return this.mRunnable;
    }

    public String getText() {
        return this.mText;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public UIEventType getType() {
        return this.mType;
    }

    public boolean isSuccessful() {
        return this.mSuccess;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setAcknowledgeCb(Runnable runnable) {
        this.mRunnable = runnable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSuccessful(boolean z) {
        this.mSuccess = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setText(String str) {
        this.mText = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTitle(String str) {
        this.mTitle = str;
    }

    public boolean shouldShowPopup() {
        return (this.mTitle == null && this.mText == null) ? false : true;
    }

    public String toString() {
        return "[success=" + this.mSuccess + ";\"" + this.mTitle + "\";\"" + this.mText + "\"]";
    }
}
