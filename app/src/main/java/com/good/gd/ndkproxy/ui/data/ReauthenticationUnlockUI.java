package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDReauthUnlockView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class ReauthenticationUnlockUI extends BaseUI {
    private final ApplicationLifecycleListener applicationLifecycleListener;
    private final ContainerState containerState;
    private long endTime;
    private boolean isEnforced;
    private final PasswordUnlock passwordUnlock;
    private long startTime;
    private String text;
    private String title;

    public ReauthenticationUnlockUI(long j, String str, String str2, boolean z, long j2, long j3, ContainerState containerState, PasswordUnlock passwordUnlock, ApplicationLifecycleListener applicationLifecycleListener) {
        super(BBUIType.UI_REAUTH_UNLOCK, j);
        this.title = str;
        this.text = str2;
        this.startTime = j2;
        this.endTime = j3;
        this.isEnforced = z;
        this.containerState = containerState;
        this.passwordUnlock = passwordUnlock;
        this.applicationLifecycleListener = applicationLifecycleListener;
    }

    private void requestChangeKeyboardVisibility(boolean z) {
        BBDUIEventManager.sendHostUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_KEYBOARD_VISIBILITY).successful(z).build());
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDReauthUnlockView(context, viewInteractor, this, viewCustomizer, this.containerState, this.applicationLifecycleListener);
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public String getText() {
        return this.text;
    }

    public long getTimeout() {
        return this.endTime - this.startTime;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isEnforced() {
        return this.isEnforced;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        if (bBDUIMessageType.ordinal() != 3) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            this.passwordUnlock.handleClientUnlockRequest(getCoreHandle(), (LoginMsg) obj);
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
}
