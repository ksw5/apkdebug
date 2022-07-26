package com.good.gd.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.good.gd.client.GDClient;
import com.good.gd.machines.icc.GDIccStateManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.INIT_STATE;
import com.good.gd.utils.IccIntentUtils;
import com.good.gt.deviceid.provider.GTDeviceIDProviderService;

/* loaded from: classes.dex */
public class GDIccService extends GTDeviceIDProviderService {
    private static final String TAG = GDIccService.class.getSimpleName();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        Intent dbjc;
        Context qkduk;

        public hbfhc(Intent intent, Context context) {
            this.dbjc = intent;
            this.qkduk = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "GDIccService - GD initialization starting\n");
            GDInit.ensureGDInitialized(GDIccService.this.getApplicationContext(), INIT_STATE.STATE_UNKNOWN);
            GDIccStateManager.getInstance().processIccMessage(this.dbjc, this.qkduk);
        }
    }

    public GDIccService() {
        super("GD Icc Handler Service");
    }

    @Override // com.good.gt.deviceid.provider.GTDeviceIDProviderService, android.app.Service
    public IBinder onBind(Intent intent) {
        GDLog.DBGPRINTF(14, TAG, "onBind()\n");
        onHandleIntent(intent);
        return super.onBind(intent);
    }

    @Override // com.good.gt.deviceid.provider.GTDeviceIDProviderService, android.app.Service
    public void onDestroy() {
        GDLog.DBGPRINTF(14, TAG, "onDestroy()\n");
        super.onDestroy();
    }

    @Override // com.good.gt.deviceid.provider.GTDeviceIDProviderService
    protected void onHandleIntent(Intent intent) {
        GDLog.DBGPRINTF(14, "GDIccService onHandleIntent\n");
        if (!IccIntentUtils.checkForIccIntent(intent)) {
            GDLog.DBGPRINTF(14, "GDIccService onHandleIntent non ICC Intent\n");
        } else if (!IccIntentUtils.checkForExpectedResponseIntent(intent)) {
            GDLog.DBGPRINTF_UNSECURE(14, "GDIccService", "GDIccService.onHandleIntent: intent is not expected. drop\n");
        } else if (GDInit.isInitialized() && GDClient.getInstance().isInitialized()) {
            GDLog.DBGPRINTF(16, "GDIccService GD initialized process ICC\n");
            GDIccStateManager.getInstance().processIccMessage(intent, this);
        } else {
            new Handler(Looper.getMainLooper()).post(new hbfhc(intent, this));
        }
    }

    @Override // com.good.gt.deviceid.provider.GTDeviceIDProviderService, android.app.Service
    public void onStart(Intent intent, int i) {
        GDLog.DBGPRINTF(14, TAG, "onStart()\n");
        super.onStart(intent, i);
    }

    @Override // com.good.gt.deviceid.provider.GTDeviceIDProviderService, android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        GDLog.DBGPRINTF(14, TAG, "onStartCommand()\n");
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        GDLog.DBGPRINTF(14, TAG, "onUnbind()\n");
        return super.onUnbind(intent);
    }

    public GDIccService(String str) {
        super(str);
    }
}
