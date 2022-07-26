package com.good.gd.ndkproxy.enterprise;

import com.good.gd.enterprise.NocServerListProvider;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.webauth.EidConfig;

/* loaded from: classes.dex */
public final class GDEProvisionUtil implements NocServerListProvider {
    private static native String getClientId();

    private static native String getEidHostDiscovery();

    public static native Object[] getListOfAlternativeNocs();

    private static native String getRedirectUri();

    private static native String getScope();

    public static native boolean pinChecksumOk(String str);

    public EidConfig getCurrentEidConfig() {
        return new EidConfig(getEidHostDiscovery(), getClientId(), getRedirectUri(), getScope());
    }

    @Override // com.good.gd.enterprise.NocServerListProvider
    public GDENocServer[] getListOfNocs() {
        return (GDENocServer[]) getListOfAlternativeNocs();
    }

    @Override // com.good.gd.enterprise.NocServerListProvider
    public String getSelectedNocServer() {
        return GDSettings.getConfigOverride();
    }
}
