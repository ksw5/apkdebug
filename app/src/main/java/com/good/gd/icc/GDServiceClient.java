package com.good.gd.icc;

import com.good.gd.icc.impl.GDServiceClientImpl;

/* loaded from: classes.dex */
public final class GDServiceClient {
    private GDServiceClient() {
    }

    public static void bringToFront(String str) throws GDServiceException {
        GDServiceClientImpl.getInstance().bringToFront(str);
    }

    public static String sendTo(String str, String str2, String str3, String str4, Object obj, String[] strArr, GDICCForegroundOptions gDICCForegroundOptions) throws GDServiceException {
        return GDServiceClientImpl.getInstance().sendTo(str, str2, str3, str4, obj, strArr, gDICCForegroundOptions);
    }

    public static void setServiceClientListener(GDServiceClientListener gDServiceClientListener) throws GDServiceException {
        GDServiceClientImpl.getInstance().setServiceClientListener(gDServiceClientListener);
    }
}
