package com.good.gd.support;

import android.content.Context;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.support.impl.GDConnectedApplicationSupportImpl;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class GDConnectedApplicationSupport {
    private static GDConnectedApplicationSupportImpl _implInstance;
    private static GDConnectedApplicationSupport _instance;

    private GDConnectedApplicationSupport(GDConnectedApplicationSupportListener gDConnectedApplicationSupportListener) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupport: Create Instance\n");
        _implInstance = GDConnectedApplicationSupportImpl.createInstance(gDConnectedApplicationSupportListener);
    }

    public static GDConnectedApplicationSupport createInstance(Context context, GDConnectedApplicationSupportListener gDConnectedApplicationSupportListener) {
        if (_instance == null) {
            GTBaseContext.getInstance().setContext(context);
            _instance = new GDConnectedApplicationSupport(gDConnectedApplicationSupportListener);
        }
        return _instance;
    }

    public static GDConnectedApplicationSupport getInstance() {
        return _instance;
    }

    public boolean isConnectedApplicationActivationAllowed() {
        return _implInstance.isConnectedApplicationActivationAllowed();
    }

    public void queryConnectedApplications() {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupport: queryConnectedDevices\n");
        _implInstance.queryConnectedApplications();
    }

    public void removeConnectedApplication(String str) {
        _implInstance.removeConnectedApplication(str);
    }

    public GDConnectedApplicationState startConnectedApplicationActivation(String str, Context context) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupport: startConnectedApplicationActivation\n");
        return _implInstance.startConnectedApplicationActivation(str, context);
    }
}
