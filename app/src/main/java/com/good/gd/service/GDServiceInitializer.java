package com.good.gd.service;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.LogUtils;

/* loaded from: classes.dex */
public abstract class GDServiceInitializer {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc extends GDServiceInitializer {
        hbfhc() {
        }

        @Override // com.good.gd.service.GDServiceInitializer
        public boolean startGDService(ServiceConnection serviceConnection) {
            GDLog.DBGPRINTF(16, "GDServiceInitializer_AndroidN.startGDService\n");
            Intent intent = new Intent(GDContext.getInstance().getApplicationContext(), GDService.class);
            try {
                GDContext.getInstance().getApplicationContext().startService(intent);
                boolean bindService = GDContext.getInstance().getApplicationContext().bindService(intent, serviceConnection, 0);
                if (!bindService) {
                    GDLog.DBGPRINTF(12, "GDServiceInitializer_AndroidN.startGDService bindService failed\n");
                }
                return bindService;
            } catch (IllegalStateException e) {
                GDLog.DBGPRINTF(12, "GDServiceInitializer_AndroidN.startGDService exception\n", e);
                LogUtils.logStackTrace(12, e);
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class yfdke extends GDServiceInitializer {
        yfdke() {
        }

        @Override // com.good.gd.service.GDServiceInitializer
        public boolean startGDService(ServiceConnection serviceConnection) {
            GDLog.DBGPRINTF(16, "GDServiceInitializer_AndroidO.startGDService\n");
            boolean bindService = GDContext.getInstance().getApplicationContext().bindService(new Intent(GDContext.getInstance().getApplicationContext(), GDService.class), serviceConnection, 1);
            if (!bindService) {
                GDLog.DBGPRINTF(12, "GDServiceInitializer_AndroidO.startGDService bindService failed\n");
            }
            return bindService;
        }
    }

    public static GDServiceInitializer create() {
        if (Build.VERSION.SDK_INT >= 26) {
            return new yfdke();
        }
        return new hbfhc();
    }

    public abstract boolean startGDService(ServiceConnection serviceConnection);
}
