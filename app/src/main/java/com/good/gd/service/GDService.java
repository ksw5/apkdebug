package com.good.gd.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.INIT_STATE;

/* loaded from: classes.dex */
public class GDService extends Service {
    private GDServiceHelper _serviceHelper;

    private void waitServiceInitialisation() {
        synchronized (this) {
            while (!this._serviceHelper.isInited()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted during service thread initialization: ", e);
                }
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        GDLog.DBGPRINTF(16, "GDService.onBind()\n");
        waitServiceInitialisation();
        GDLog.DBGPRINTF(16, "GDService.onBind: GDService is initialized\n");
        return this._serviceHelper.getMessenger().getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        GDLog.DBGPRINTF(16, "GDService.onCreate()\n");
        GDInit.ensureGDInitialized(getApplicationContext(), INIT_STATE.STATE_UNKNOWN);
        if (!GDInit.isInitialized()) {
            GDLog.DBGPRINTF(12, "******* GDService.onCreate: initialization failed, service not started!\n");
            stopSelf();
        } else if (this._serviceHelper != null) {
        } else {
            GDServiceHelper gDServiceHelper = (GDServiceHelper) GDContext.getInstance().getDynamicsService("gd_service_helper");
            this._serviceHelper = gDServiceHelper;
            gDServiceHelper.initHelperThread(this);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        GDLog.DBGPRINTF(13, "GDService.onDestroy: initialized\n");
        this._serviceHelper = null;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        GDLog.DBGPRINTF(16, "GDService.onRebind()\n");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        GDLog.DBGPRINTF(16, "GDService.onStartCommand(" + intent + ", " + i + ", " + i2 + ")\n");
        waitServiceInitialisation();
        GDLog.DBGPRINTF(16, "GDService.onStartCommand: GDService is initialized, returning\n");
        return 2;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        GDLog.DBGPRINTF(16, "GDService.onUnbind()\n");
        return true;
    }
}
