package com.good.gd.ndkproxy.attestationApi;

import com.good.gd.ndkproxy.utility.ResultSender;
import com.good.gd.ndkproxy.utility.UtilityHWAC;

/* loaded from: classes.dex */
public class GDHWActivationAttestation extends GDHWAttestationAPI {
    private static GDHWActivationAttestation instance;

    public static synchronized void createInstance() {
        synchronized (GDHWActivationAttestation.class) {
            if (instance == null) {
                instance = new GDHWActivationAttestation();
            }
        }
    }

    @Override // com.good.gd.ndkproxy.attestationApi.GDHWAttestationAPI
    protected ResultSender createResultSender() {
        return new UtilityHWAC(this);
    }
}
