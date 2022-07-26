package com.good.gd.icc.impl;

import com.good.gd.icc.GDServiceListener;
import com.good.gt.icc.IccServicesServer;

/* loaded from: classes.dex */
public interface GDIccProviderInterface {
    void checkAuthorized();

    IccServicesServer getServicesServer();

    void setApplicationListener(GDServiceListener gDServiceListener);
}
