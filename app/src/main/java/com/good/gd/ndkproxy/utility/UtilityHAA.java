package com.good.gd.ndkproxy.utility;

import android.os.Bundle;
import com.good.gd.ndkproxy.attestationApi.GDHWAttestationAPI;
import com.good.gd.ndkproxy.attestationApi.IAttestationAPI;

/* loaded from: classes.dex */
public class UtilityHAA implements ResultSender {
    private final IAttestationAPI impl;

    public UtilityHAA(IAttestationAPI iAttestationAPI) {
        this.impl = iAttestationAPI;
        ndkInit();
    }

    private native void UtilS3(Bundle bundle);

    protected void UtilS1(Object obj, String str, int i) {
        this.impl.setAdditionalData(new GDHWAttestationAPI.Data(obj, str));
        this.impl.startAttestation(str, i);
    }

    protected native void ndkInit();

    @Override // com.good.gd.ndkproxy.utility.ResultSender
    public void sendResult(Bundle bundle) {
        UtilS3(bundle);
    }
}
