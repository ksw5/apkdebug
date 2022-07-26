package com.good.gd.ndkproxy.ui.data.dialog;

import android.app.Activity;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BBDUIState;

/* loaded from: classes.dex */
public abstract class DialogUI extends BBDUIObject {
    private boolean mDynamicsBackgroundEnabled;

    public DialogUI(BBUIType bBUIType, long j) {
        super(bBUIType, j);
        this.mDynamicsBackgroundEnabled = true;
    }

    public final void close() {
        onStateChanged(BBDUIState.STATE_PAUSED);
    }

    public boolean isDynamicsBackgroundEnabled() {
        return this.mDynamicsBackgroundEnabled;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public final boolean isModal() {
        return true;
    }

    public abstract boolean showDialog(Activity activity);

    public DialogUI(BBUIType bBUIType, long j, boolean z) {
        super(bBUIType, j);
        this.mDynamicsBackgroundEnabled = true;
        this.mDynamicsBackgroundEnabled = z;
    }
}
