package com.good.gd.enterprise;

import com.good.gd.ndkproxy.enterprise.GDENocServer;

/* loaded from: classes.dex */
public interface NocServerListProvider {
    GDENocServer[] getListOfNocs();

    String getSelectedNocServer();
}
