package com.good.gd.icc.impl;

import com.good.gd.icc.GDServiceClientListener;
import com.good.gt.icc.IccServicesClient;

/* loaded from: classes.dex */
public interface GDIccConsumerInterface {
    void checkAuthorized();

    IccServicesClient getServicesClient();

    void setApplicationListener(GDServiceClientListener gDServiceClientListener);
}
