package com.good.gd.ndkproxy.ui;

import android.content.res.Resources;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.ui.data.GDProvisionEventFactory;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIProvisionUpdateEvent;

/* loaded from: classes.dex */
public class GDEnterpriseProvisionUI {
    private static GDEnterpriseProvisionUI _instance;
    private final Resources r = Resources.getSystem();

    private GDEnterpriseProvisionUI() {
        try {
            GDLog.DBGPRINTF(16, "GDEnterpriseProvisionUI: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLock) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDEnterpriseProvisionUI: Cannot initialize C++ peer", e);
        }
    }

    public static synchronized GDEnterpriseProvisionUI getInstance() {
        GDEnterpriseProvisionUI gDEnterpriseProvisionUI;
        synchronized (GDEnterpriseProvisionUI.class) {
            if (_instance == null) {
                _instance = new GDEnterpriseProvisionUI();
            }
            gDEnterpriseProvisionUI = _instance;
        }
        return gDEnterpriseProvisionUI;
    }

    private void updateProgress(int i, long j) {
        GDLog.DBGPRINTF(16, "GDEnterpriseProvisionUI.updateProvisionUI(" + i + ")\n");
        BBDUIProvisionUpdateEvent makeProvisionUpdateInstruction = GDProvisionEventFactory.makeProvisionUpdateInstruction(i);
        if (makeProvisionUpdateInstruction != null) {
            BBDUIEventManager.sendUpdateEvent(makeProvisionUpdateInstruction, BBDUIDataStore.getInstance().getUIData(j));
        } else {
            GDLog.DBGPRINTF(16, "GDEnterpriseProvisionUI.updateProvisionUI skip\n");
        }
    }

    native void ndkInit();
}
