package com.good.gd.ndkproxy.attestationApi;

import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.attestationApi.IAttestationAPI;
import com.good.gd.ndkproxy.utility.ResultSender;
import com.good.gd.ndkproxy.utility.UtilityHAA;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class GDHWAttestationAPI extends IAttestationAPI {
    static final String ATTESTATION_RESPONSE = "attestationResponse";
    static final String ERROR = "error";
    static final String NONCE = "nonce";
    static final String SECURITY_PATCH_LEVEL = "securityPatchLevel";
    private static GDHWAttestationAPI instance;
    private static final Object lockObject = new Object();
    private final ConcurrentHashMap<String, Data> additionalDataMap = new ConcurrentHashMap<>();
    public final ResultSender utility = createResultSender();

    /* loaded from: classes.dex */
    public static class Data implements AdditionalData {
        private final Object certChain;
        private final String nonce;

        public Data(Object obj, String str) {
            this.certChain = obj;
            this.nonce = str;
        }

        public Object getCertChain() {
            return this.certChain;
        }

        public String getNonce() {
            return this.nonce;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class hbfhc extends Exception {
        private final yfdke dbjc;

        public hbfhc(yfdke yfdkeVar) {
            this.dbjc = yfdkeVar;
        }

        public yfdke dbjc() {
            return this.dbjc;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum yfdke {
        NONE(0),
        CERTIFICATE_ARRAY_IS_NULL_OR_WRONG_TYPE(1),
        CERTIFICATE_ARRAY_IS_EMPTY(2),
        CERTIFICATE_ARRAY_FAIL_TO_ENCODE(3);
        
        private final int dbjc;

        yfdke(int i) {
            this.dbjc = i;
        }
    }

    private String convert(Object obj) throws hbfhc {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof Certificate[]) {
            Certificate[] certificateArr = (Certificate[]) obj;
            if (certificateArr.length > 0) {
                try {
                    for (Certificate certificate : certificateArr) {
                        String encodeToString = Base64.encodeToString(certificate.getEncoded(), 2);
                        sb.append("-----BEGIN CERTIFICATE-----\n");
                        sb.append(encodeToString);
                        sb.append("\n-----END CERTIFICATE-----\n");
                    }
                    return sb.toString();
                } catch (CertificateEncodingException e) {
                    GDLog.DBGPRINTF(12, "GHAA SA FFFManager BBB " + Log.getStackTraceString(e) + "\n");
                    throw new hbfhc(yfdke.CERTIFICATE_ARRAY_FAIL_TO_ENCODE);
                }
            }
            throw new hbfhc(yfdke.CERTIFICATE_ARRAY_IS_EMPTY);
        }
        GDLog.DBGPRINTF(12, "GHAA FKSIN\n");
        throw new hbfhc(yfdke.CERTIFICATE_ARRAY_IS_NULL_OR_WRONG_TYPE);
    }

    public static synchronized void createInstance() {
        synchronized (GDHWAttestationAPI.class) {
            if (instance == null) {
                instance = new GDHWAttestationAPI();
            }
        }
    }

    protected ResultSender createResultSender() {
        return new UtilityHAA(this);
    }

    @Override // com.good.gd.ndkproxy.attestationApi.IAttestationAPI
    public void setAdditionalData(AdditionalData additionalData) {
        if (additionalData instanceof Data) {
            Data data = (Data) additionalData;
            this.additionalDataMap.put(data.nonce, data);
        }
    }

    @Override // com.good.gd.ndkproxy.attestationApi.IAttestationAPI
    public void startAttestation(String str, int i) {
        synchronized (lockObject) {
            Data data = this.additionalDataMap.get(str);
            if (data != null) {
                yfdke yfdkeVar = yfdke.NONE;
                String str2 = "";
                try {
                    str2 = convert(data.getCertChain());
                } catch (hbfhc e) {
                    yfdkeVar = e.dbjc();
                }
                Bundle bundle = new Bundle();
                bundle.putString("nonce", str);
                bundle.putString(ATTESTATION_RESPONSE, Base64.encodeToString(str2.getBytes(), 2));
                bundle.putInt("error", yfdkeVar.dbjc);
                bundle.putString(SECURITY_PATCH_LEVEL, Build.VERSION.SECURITY_PATCH);
                this.utility.sendResult(bundle);
            } else {
                GDLog.DBGPRINTF(13, "GHAA SSA\n");
            }
        }
    }
}
