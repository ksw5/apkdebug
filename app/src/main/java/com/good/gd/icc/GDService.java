package com.good.gd.icc;

import com.good.gd.icc.impl.GDServiceImpl;

/* loaded from: classes.dex */
public final class GDService {
    public static final String GDFrontRequestMethod = "FRONT_REQ";
    public static final String GDFrontRequestService = "com.good.gd.icc";

    private GDService() {
    }

    public static void bringToFront(String str) throws GDServiceException {
        GDServiceImpl.getInstance().bringToFront(str);
    }

    public static void replyTo(String str, Object obj, GDICCForegroundOptions gDICCForegroundOptions, String[] strArr, String str2) throws GDServiceException {
        GDServiceImpl.getInstance().replyTo(str, obj, gDICCForegroundOptions, strArr, str2);
    }

    public static void setServiceListener(GDServiceListener gDServiceListener) throws GDServiceException {
        GDServiceImpl.getInstance().setServiceListener(gDServiceListener);
    }
}
