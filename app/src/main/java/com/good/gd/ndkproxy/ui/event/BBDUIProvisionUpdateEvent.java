package com.good.gd.ndkproxy.ui.event;

import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class BBDUIProvisionUpdateEvent extends BBDUIUpdateEvent {
    private int mPercent;
    private String mProgressString;
    private String mProgressStringLocalizationKey;
    private int mState;

    public BBDUIProvisionUpdateEvent(int i, int i2, String str) {
        super(UIEventType.UI_PROVISION_PROGRESS_UPDATE);
        this.mState = i;
        this.mPercent = i2;
        this.mProgressStringLocalizationKey = str;
        this.mProgressString = GDLocalizer.getLocalizedString(str);
        setSuccessful(true);
    }

    @Override // com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent
    public String getText() {
        return this.mProgressStringLocalizationKey;
    }

    @Override // com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent
    public boolean isSuccessful() {
        return true;
    }

    @Override // com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent
    public String toString() {
        return "[state=" + this.mState + ";" + this.mPercent + "%;" + this.mProgressString + "]";
    }
}
