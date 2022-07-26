package com.good.gd.ndkproxy.utility;

import android.os.Bundle;
import com.good.gd.ndkproxy.attestationApi.IAttestationAPI;

/* loaded from: classes.dex */
public class UtilityHWAC extends UtilityHAA {
    public UtilityHWAC(IAttestationAPI iAttestationAPI) {
        super(iAttestationAPI);
    }

    protected native void UtilS4(Bundle bundle);

    @Override // com.good.gd.ndkproxy.utility.UtilityHAA
    protected native void ndkInit();

    @Override // com.good.gd.ndkproxy.utility.UtilityHAA, com.good.gd.ndkproxy.utility.ResultSender
    public void sendResult(Bundle bundle) {
        UtilS4(bundle);
    }
}
