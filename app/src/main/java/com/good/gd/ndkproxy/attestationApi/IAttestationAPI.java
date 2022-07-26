package com.good.gd.ndkproxy.attestationApi;

/* loaded from: classes.dex */
public abstract class IAttestationAPI {

    /* loaded from: classes.dex */
    public interface AdditionalData {
    }

    public void setAdditionalData(AdditionalData additionalData) {
    }

    public abstract void startAttestation(String str, int i);
}
