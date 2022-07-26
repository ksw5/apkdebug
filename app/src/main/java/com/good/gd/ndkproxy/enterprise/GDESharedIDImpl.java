package com.good.gd.ndkproxy.enterprise;

import com.good.gd.context.GDContext;
import com.good.gd.error.GDNotAuthorizedError;

/* loaded from: classes.dex */
public class GDESharedIDImpl {
    public static String getDynamicsSharedUserID() throws GDNotAuthorizedError {
        GDContext.getInstance().checkAuthorized();
        return nativeGetSharedUserID();
    }

    private static native String nativeGetSharedUserID();
}
