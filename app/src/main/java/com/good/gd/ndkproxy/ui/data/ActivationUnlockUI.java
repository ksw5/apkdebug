package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.messages.LoginMsg;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.ActivationUnlockBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDActivationLoginView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class ActivationUnlockUI extends ActivationUnlockBaseUI {
    private boolean _isCsrUnlock;
    private final ApplicationLifecycleListener applicationLifecycleListener;
    private final ContainerState containerState;
    private final PasswordUnlock passwordUnlock;

    public ActivationUnlockUI(long j, BBUIType bBUIType, String str, String str2, boolean z, PasswordUnlock passwordUnlock, ContainerState containerState, ApplicationLifecycleListener applicationLifecycleListener) {
        super(j, bBUIType, str, str2);
        this.passwordUnlock = passwordUnlock;
        this.containerState = containerState;
        this.applicationLifecycleListener = applicationLifecycleListener;
        this._isCsrUnlock = z;
    }

    private void handleCancelActivation(boolean z) {
        if (z) {
            if (this._isCsrUnlock) {
                processCertificateSigningRequestResult(false, getCoreHandle());
            } else {
                processActivationRequestResult(false, getCoreHandle());
            }
        }
        cancel();
    }

    private static native void processActivationRequestResult(boolean z, long j);

    private static native void processCertificateSigningRequestResult(boolean z, long j);

    private void requestChangeKeyboardVisibility(boolean z) {
        BBDUIEventManager.sendHostUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_KEYBOARD_VISIBILITY).successful(z).build());
    }

    private void submitPasswordAndNotify(LoginMsg loginMsg) {
        this.passwordUnlock.handleClientUnlockRequest(getCoreHandle(), loginMsg);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDActivationLoginView(context, viewInteractor, this, viewCustomizer, this.containerState, this.applicationLifecycleListener);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal == 3) {
            submitPasswordAndNotify((LoginMsg) obj);
        } else if (ordinal != 13) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            handleCancelActivation(true);
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

    public ActivationUnlockUI(long j, String str, String str2, boolean z, PasswordUnlock passwordUnlock, ContainerState containerState, ApplicationLifecycleListener applicationLifecycleListener) {
        this(j, BBUIType.UI_ACTIVATION_UNLOCK, str, str2, z, passwordUnlock, containerState, applicationLifecycleListener);
    }
}
