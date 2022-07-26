package com.good.gd.ndkproxy.sub;

import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.GDInterDeviceContainerControl;
import com.good.gt.interdevice_icc.InterDeviceUtils;

/* loaded from: classes.dex */
public class GDSubContainerEventHandler {
    private static GDSubContainerEventHandler _instance;

    private GDSubContainerEventHandler() {
    }

    public static synchronized GDSubContainerEventHandler getInstance() {
        GDSubContainerEventHandler gDSubContainerEventHandler;
        synchronized (GDSubContainerEventHandler.class) {
            if (_instance == null) {
                _instance = new GDSubContainerEventHandler();
            }
            gDSubContainerEventHandler = _instance;
        }
        return gDSubContainerEventHandler;
    }

    public boolean handleMessage(Object obj) {
        if (obj != null) {
            GDSubContainerEvent gDSubContainerEvent = (GDSubContainerEvent) obj;
            GDLog.DBGPRINTF(16, "GDSubContainerEventHandler.handleMessage() type = " + gDSubContainerEvent.getType() + " Policy Message = " + gDSubContainerEvent.getPolicyMessage() + " \n");
            GDInterDeviceContainerControl.getInstance().sendUpdatePolicy(gDSubContainerEvent.getPolicyMessage(), InterDeviceUtils.createSpecificNodeGDWearAddress(GDContext.getInstance().getApplicationContext().getPackageName(), gDSubContainerEvent.getAddress()));
            return true;
        }
        return false;
    }

    public void initialize() throws Exception {
        try {
            ndkInit();
        } catch (Exception e) {
            throw new Exception("GDSubContainerEventHandler: Cannot initialize C++ peer", e);
        }
    }

    native void ndkInit();
}
