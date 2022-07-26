package com.good.gd.support;

import java.util.ArrayList;

/* loaded from: classes.dex */
public interface GDConnectedApplicationSupportListener {
    void onApplicationActivationComplete(GDConnectedApplication gDConnectedApplication);

    void onApplicationsConnected(ArrayList<GDConnectedApplication> arrayList);
}
