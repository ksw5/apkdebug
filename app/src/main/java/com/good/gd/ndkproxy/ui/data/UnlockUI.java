package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDLoginView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class UnlockUI extends BaseUI {
    private String mLocalizablePasswordEyeKey;
    private final PasswordUnlock passwordUnlock;

    public UnlockUI(long j, PasswordUnlock passwordUnlock) {
        super(BBUIType.UI_UNLOCK, j);
        this.passwordUnlock = passwordUnlock;
        this.mLocalizablePasswordEyeKey = BBDUILocalizationHelper.getLocalizablePasswordEyeKey(this);
    }

    private void requestChangeKeyboardVisibility(boolean z) {
        BBDUIEventManager.sendHostUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_KEYBOARD_VISIBILITY).successful(z).build());
    }

    private void submitPassword(LoginMsg loginMsg) {
        this.passwordUnlock.handleClientUnlockRequest(getCoreHandle(), loginMsg);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDLoginView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLocalizedPasswordEyeText() {
        return GDLocalizer.getLocalizedString(this.mLocalizablePasswordEyeKey);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        GDLog.DBGPRINTF(16, "UnlockUI.onMessage IN: " + bBDUIMessageType + "\n");
        if (bBDUIMessageType.ordinal() != 3) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            submitPassword((LoginMsg) obj);
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        super.onStateActive();
        requestChangeKeyboardVisibility(true);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        requestChangeKeyboardVisibility(false);
    }

    public UnlockUI(BBUIType bBUIType, long j, PasswordUnlock passwordUnlock) {
        super(bBUIType, j);
        this.passwordUnlock = passwordUnlock;
    }
}
