package com.good.gd.ndkproxy.enterprise;

import android.os.Message;
import com.good.gd.client.GDClient;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.native2javabridges.utils.UIDataResult;
import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.ndkproxy.ui.data.ProvisionManager;

/* loaded from: classes.dex */
public final class GDEProvisionManager implements ProvisionManager {
    private static GDEProvisionManager _instance;

    private GDEProvisionManager() {
        try {
            GDLog.DBGPRINTF(16, "GDEProvisionManager: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLock) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDEProvisionManager: Cannot initialize C++ peer", e);
        }
    }

    public static synchronized GDEProvisionManager getInstance() {
        GDEProvisionManager gDEProvisionManager;
        synchronized (GDEProvisionManager.class) {
            if (_instance == null) {
                _instance = new GDEProvisionManager();
            }
            gDEProvisionManager = _instance;
        }
        return gDEProvisionManager;
    }

    native void ndkInit();

    native void start(String str, String str2, String str3);

    native void startDelayed(String str, String str2, String str3, int i);

    @Override // com.good.gd.ndkproxy.ui.data.ProvisionManager
    public native UIDataResult startProvision(long j, String str, String str2);

    @Override // com.good.gd.ndkproxy.ui.data.ProvisionManager
    public native UIDataResult startProvisionOverBcp(long j, String str, String str2, String str3);

    @Override // com.good.gd.ndkproxy.ui.data.ProvisionManager
    public native void startProvisionUsingAuthCode(String str, String str2);

    @Override // com.good.gd.ndkproxy.ui.data.ProvisionManager
    public void startProvisioningProcedure(ProvisionMsg provisionMsg) {
        startProvisioningProcedure(provisionMsg.email, provisionMsg.pin, provisionMsg.nonce);
    }

    public void startProvisioningProcedure(String str, String str2, String str3) {
        GDLog.DBGPRINTF(14, "GDEProvisionManager : applicationEnteringForeground\n");
        GDLibraryUI.getInstance().applicationEnteringForeground(GDClient.getInstance().isAuthorized());
        GDLog.DBGPRINTF(16, "GDEProvisionManager.startProvisioningProcedure(" + str + ", " + str2 + ")\n");
        startDelayed(str, str2, str3, 1000);
    }

    public void startProvisioningProcedure(Message message) {
        Object obj = message.obj;
        if (!(obj instanceof ProvisionMsg)) {
            GDLog.DBGPRINTF(13, "GDEProvisionManager: MSG_CLIENT_START_PROVISIONING is received with invalid inner message, ignoring.\n");
        } else {
            startProvisioningProcedure((ProvisionMsg) obj);
        }
    }
}
