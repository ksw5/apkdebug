package com.good.gd.ndkproxy.utility;

import com.good.gd.ndkproxy.attestationApi.IAttestationAPI;

/* loaded from: classes.dex */
public class UtilityAA {
    private IAttestationAPI implementation;
    private String nativeImplName;

    public UtilityAA(IAttestationAPI iAttestationAPI, String str) {
        this.implementation = iAttestationAPI;
        this.nativeImplName = str;
        ndkInit(str);
    }

    private void UtilA(String str, int i) {
        this.implementation.startAttestation(str, i);
    }

    private native void UtilB(String str, String str2);

    public static native void UtilC(boolean z);

    private native void ndkInit(String str);

    public void sendResult(String str) {
        UtilB(str, this.nativeImplName);
    }
}
