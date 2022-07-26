package com.good.gd.ndkproxy.ui;

import android.os.Handler;
import android.os.HandlerThread;
import com.good.gd.backgroundexecution.GDBackgroundExecutionManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDAppLockUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDAppLockUpdateEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.UIEventType;

/* loaded from: classes.dex */
public class GDLibraryUI {
    private static HandlerThread _applicationStateHandlerThread;
    private static GDLibraryUI _instance = null;
    private final Handler _applicationStateExecutionHandler = new Handler(_applicationStateHandlerThread.getLooper());

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "GDLibraryUI.applicationEnteringBackground run IN\n");
            GDLibraryUI.this.enteringBackground();
            GDBackgroundExecutionManager.getInstance().notifyGDForegroundEvent(false);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        final /* synthetic */ boolean dbjc;

        yfdke(boolean z) {
            this.dbjc = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "GDLibraryUI.applicationEnteringForeground run IN\n");
            GDBackgroundExecutionManager.getInstance().notifyGDForegroundEvent(true);
            GDLibraryUI.this.enteringForeground(this.dbjc);
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("ApplicationStateHandlerThread");
        _applicationStateHandlerThread = handlerThread;
        handlerThread.start();
    }

    private GDLibraryUI() {
    }

    public static synchronized GDLibraryUI getInstance() {
        GDLibraryUI gDLibraryUI;
        synchronized (GDLibraryUI.class) {
            if (_instance == null) {
                _instance = new GDLibraryUI();
            }
            gDLibraryUI = _instance;
        }
        return gDLibraryUI;
    }

    private boolean openWipeOrResetUI(long j) {
        GDLog.DBGPRINTF(16, "GDLibraryUI.openWipeOrResetUI()\n");
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_OPEN_WIPE_OR_RESET_DIALOG).build(), BBDUIDataStore.getInstance().getUIData(j));
        return true;
    }

    public void applicationEnteringBackground() {
        GDLog.DBGPRINTF(16, "GDLibraryUI.applicationEnteringBackground\n");
        synchronized (this._applicationStateExecutionHandler) {
            this._applicationStateExecutionHandler.post(new hbfhc());
        }
    }

    public void applicationEnteringForeground(boolean z) {
        GDLog.DBGPRINTF(16, "GDLibraryUI.applicationEnteringForeground, authed = " + z + "\n");
        NetworkStateMonitor.getInstance().foregroundEventNetworkStateUpdate();
        synchronized (this._applicationStateExecutionHandler) {
            this._applicationStateExecutionHandler.post(new yfdke(z));
        }
    }

    public void applicationRestart() {
        restart();
    }

    public String createLocalizedStringWithKey(int i) {
        return "";
    }

    native void enteringBackground();

    native void enteringForeground(boolean z);

    public boolean finalUpdateInterAppLockUI(long j, String str, String str2, String str3, String str4, int i) {
        GDLog.DBGPRINTF(16, "GDLibraryUI.finalUpdateInterAppLockUI(" + str + ", " + str2 + ", " + str3 + ", " + str4 + ", " + i + ")\n");
        BBDUIEventManager.sendUpdateEvent(new BBDAppLockUpdateEventBuilder().setBundleId(str).setName(str2).setVersion(str3).setDownloadLocation(str4).setFailureReason(i).build(), BBDUIDataStore.getInstance().getUIData(j));
        return true;
    }

    public void initialize() throws Exception {
        try {
            ndkInit();
        } catch (Exception e) {
            throw new Exception("GDLibraryUI: Cannot initialize C++ peer", e);
        }
    }

    public native boolean isAppVersionEntitled();

    public native boolean isMigrationInProgress();

    native boolean nativePasswordsRequiredForCertificates();

    native void nativePresentCertificatePasswordUI();

    native void ndkInit();

    public boolean passwordsRequiredForCertificates() {
        return nativePasswordsRequiredForCertificates();
    }

    public void presentCertificatePasswordUI() {
        nativePresentCertificatePasswordUI();
    }

    native void restart();

    public boolean updateInterAppLockUI(long j, String str, String str2, String str3, int i) {
        GDLog.DBGPRINTF(16, "GDLibraryUI.updateInterAppLockUI(" + str + ", " + str2 + ", " + str3 + ", " + i + ")\n");
        BBDAppLockUpdateEvent build = new BBDAppLockUpdateEventBuilder().setBundleId(str).setName(str2).setVersion(str3).setFailureReason(i).build();
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(j);
        GDLog.DBGPRINTF(16, "GDLibraryUI.updateInterAppLockUI(" + uIData + ")\n");
        BBDUIEventManager.sendUpdateEvent(build, uIData);
        return true;
    }
}
