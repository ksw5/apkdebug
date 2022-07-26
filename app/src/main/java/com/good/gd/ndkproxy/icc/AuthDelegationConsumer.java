package com.good.gd.ndkproxy.icc;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Looper;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.ErrorUtils;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.AuthDelegationClient;
import com.good.gt.icc.AuthDelegationClientEventsListener;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTInteger;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;

/* loaded from: classes.dex */
public final class AuthDelegationConsumer implements AuthDelegationClientEventsListener, AppControl {
    private static AuthDelegationConsumer _instance;
    private final AuthDelegationClient _authDelegationClient;
    private boolean _registered = false;

    public AuthDelegationConsumer() {
        AuthDelegationClient authDelegationClient = ICCControllerFactory.getICCController(this).getAuthDelegationClient();
        this._authDelegationClient = authDelegationClient;
        try {
            authDelegationClient.setClientAuthDelegationListener(this);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, "Auth Delegation Consumer - listener error: " + e.toString() + "\n");
        }
    }

    private native void authDelegationResponse(byte[] bArr, String str, boolean z, int i);

    private native void authSendFailed(String str, int i);

    private void checkForegroundEvent(boolean z) {
        if (Build.VERSION.SDK_INT < 29 || !z || Looper.getMainLooper().isCurrentThread()) {
            return;
        }
        try {
            GDLog.DBGPRINTF(13, "BG Activity restriction delay ...\n");
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            GDLog.DBGPRINTF(12, "checkForegroundEvent failed\n");
        }
    }

    public static synchronized AuthDelegationConsumer getInstance() {
        AuthDelegationConsumer authDelegationConsumer;
        synchronized (AuthDelegationConsumer.class) {
            if (_instance == null) {
                _instance = new AuthDelegationConsumer();
            }
            authDelegationConsumer = _instance;
        }
        return authDelegationConsumer;
    }

    private native void lockNow();

    private native void ndkInit();

    private void registerAuthDelegateComplianceBroadcastReceiver() {
        GDLog.DBGPRINTF(16, "registerAuthDelegateComplianceBroadcastReceiver\n");
        GTBaseContext.getInstance().getApplicationContext().registerReceiver(new ComplianceBroadcastReceiver(), new IntentFilter("com.blackberry.bbd.intent.action.ACTION_COMPLIANCE_BROADCAST"));
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    public void enforceAuthDelegate() {
        lockNow();
    }

    public void initialize() throws Exception {
        try {
            ndkInit();
        } catch (Exception e) {
            throw new Exception("AuthDelegationConsumer: Cannot initialize native peer", e);
        }
    }

    @Override // com.good.gt.icc.AuthDelegationClientEventsListener
    public void onAuthDelegationError(String str, int i) {
        authSendFailed(str, i);
    }

    @Override // com.good.gt.icc.AuthDelegationClientEventsListener
    public void onAuthDelegationResponse(byte[] bArr, String str, boolean z, int i, String str2) {
        GDLog.DBGPRINTF(16, "onAuthDelegationResponse: nativeAppId " + str + ", success " + z + " errorCode " + i + "errorMessage " + str2 + "\n");
        authDelegationResponse(bArr, str, z, i);
    }

    @Override // com.good.gt.icc.AuthDelegationClientEventsListener
    public void onReadyToSendAuthDelegationRequest(GTInteger gTInteger, GTInteger gTInteger2) {
        gTInteger.setValue(GDIccManager.liflu().qkduk());
        if (gTInteger2 != null) {
            gTInteger2.setValue(GDIccManager.liflu().jwxax());
        }
    }

    public int sendAuthRequestTo(String str, boolean z, boolean z2) {
        if (GDContext.getInstance().getApplicationContext().getPackageName().equals(str)) {
            ErrorUtils.throwGDErrorForProgrammingError("My native bundle ID = " + GDContext.getInstance().getApplicationContext().getPackageName() + " Auth delegate bundle ID = " + str + " Error cannot auth delegate to yourself. Is programming / configuration error");
        }
        int i = 1;
        if (!this._registered) {
            registerAuthDelegateComplianceBroadcastReceiver();
            this._registered = true;
        }
        try {
            if (GDActivityStateManager.getInstance().inBackground() && !z) {
                GDLog.DBGPRINTF(14, "Auth Delegation Consumer skipped auth request (in background)\n");
                return i;
            }
            checkForegroundEvent(z2);
            this._authDelegationClient.sendAuthRequest(str);
            i = 0;
            return i;
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, "Auth Delegation Consumer - error: " + e.toString() + "\n");
            int ordinal = e.getMajor().ordinal();
            if (ordinal == i) {
                return 3;
            }
            if (ordinal == 11) {
                return 4;
            }
            if (ordinal == 14) {
                return i;
            }
            return 2;
        }
    }
}
