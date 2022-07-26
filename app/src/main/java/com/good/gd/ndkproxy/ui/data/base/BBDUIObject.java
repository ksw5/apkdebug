package com.good.gd.ndkproxy.ui.data.base;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public abstract class BBDUIObject {
    private long mHandle;
    private String mLocalizableBottomButtonKey;
    private String mLocalizableMessageKey;
    private String mLocalizableTitleKey;
    private BBUIType mUIType;
    private BBDUIUpdateEvent mUpdateEvent = null;
    private boolean mUserActionRequired = false;
    private BBDUIState mState = BBDUIState.STATE_CREATED;

    public BBDUIObject(BBUIType bBUIType, long j) {
        this.mHandle = j;
        this.mUIType = bBUIType;
        cacheStaticData();
    }

    private void cacheStaticData() {
        this.mLocalizableTitleKey = BBDUIHelper.getLocalizableTitle(this.mHandle);
        this.mLocalizableMessageKey = BBDUIHelper.getLocalizableMessage(this.mHandle);
        this.mLocalizableBottomButtonKey = BBDUIHelper.getLocalizableBottomButton(this.mHandle);
    }

    public final void bottomButton() {
        BBDUIHelper.bottomButton(this.mHandle);
    }

    public final void cancel() {
        BBDUIHelper.cancel(this.mHandle);
    }

    protected final synchronized BBDUIState currentState() {
        return this.mState;
    }

    public final BBUIType getBBDUIType() {
        return this.mUIType;
    }

    public long getCoreHandle() {
        return this.mHandle;
    }

    public String getLocalizedBottomButton() {
        return GDLocalizer.getLocalizedString(this.mLocalizableBottomButtonKey);
    }

    public String getLocalizedMessage() {
        return GDLocalizer.getLocalizedString(this.mLocalizableMessageKey);
    }

    public String getLocalizedTitle() {
        return GDLocalizer.getLocalizedString(this.mLocalizableTitleKey);
    }

    public BBDUIUpdateEvent getUpdateData() {
        return this.mUpdateEvent;
    }

    public final boolean hasBottomButton() {
        return BBDUIHelper.hasBottomButton(this.mHandle);
    }

    public final boolean hasCancelButton() {
        return BBDUIHelper.hasCancelButton(this.mHandle);
    }

    public final boolean hasHelpButton() {
        return BBDUIHelper.hasHelpButton(this.mHandle);
    }

    public final boolean hasOkButton() {
        return BBDUIHelper.hasOkButton(this.mHandle);
    }

    public final void help() {
        BBDUIHelper.help(this.mHandle);
    }

    public final synchronized boolean isActive() {
        return this.mState == BBDUIState.STATE_ACTIVE;
    }

    public abstract boolean isModal();

    public boolean isPlaceholder() {
        return false;
    }

    public boolean isUserActionRequired() {
        return this.mUserActionRequired;
    }

    public final void ok() {
        BBDUIHelper.ok(this.mHandle);
    }

    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        GDLog.DBGPRINTF(16, "BBDUI.onMessage " + bBDUIMessageType + "\n");
        switch (bBDUIMessageType.ordinal()) {
            case 12:
                bottomButton();
                return;
            case 13:
                cancel();
                return;
            case 14:
                ok();
                return;
            case 15:
            default:
                GDLog.DBGPRINTF(16, "BBDUI.onMessage - wrong message chain\n");
                return;
            case 16:
                help();
                return;
        }
    }

    public void onStateActive() {
    }

    public final void onStateChanged(BBDUIState bBDUIState) {
        synchronized (this) {
            if (this.mState == bBDUIState) {
                return;
            }
            if (bBDUIState == BBDUIState.STATE_PAUSED && this.mState != BBDUIState.STATE_ACTIVE) {
                return;
            }
            this.mState = bBDUIState;
            int ordinal = bBDUIState.ordinal();
            if (ordinal == 1) {
                onStateActive();
            } else if (ordinal == 2) {
                onStatePaused();
            } else if (ordinal != 3) {
            } else {
                onStateDestroyed();
            }
        }
    }

    public void onStateDestroyed() {
    }

    public void onStatePaused() {
    }

    public void resetUpdateData() {
        this.mUpdateEvent = null;
    }

    public void setUpdateData(BBDUIUpdateEvent bBDUIUpdateEvent) {
        this.mUpdateEvent = bBDUIUpdateEvent;
    }

    public void setUserActionRequired(boolean z) {
        this.mUserActionRequired = z;
    }
}
