package com.good.gd.ndkproxy.attestationApi;

import com.blackberry.attestation.AttestationBuilder;
import com.blackberry.attestation.AttestationClient;
import com.blackberry.attestation.AttestationFailureListener;
import com.blackberry.attestation.AttestationSuccessListener;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLangInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.utility.UtilityAA;
import com.good.gd.utils.GDAttestationLogger;

/* loaded from: classes.dex */
public class GDAttestationAPI extends IAttestationAPI {
    public static final String ACTIVATION = "act";
    public static final String PERIODIC = "per";
    private static GDAttestationAPI activationAttestationInstance;
    private static GDAttestationAPI periodicAttestationInstance;
    protected AttestationClient client;
    protected UtilityAA utility;

    /* loaded from: classes.dex */
    class hbfhc implements AttestationFailureListener {
        hbfhc() {
        }

        @Override // com.blackberry.attestation.AttestationFailureListener
        public void onFailure(byte[] bArr, Exception exc) {
            GDLog.DBGPRINTF(14, "GAGAA F");
            if (exc == null) {
                GDAttestationAPI.this.utility.sendResult(null);
            } else {
                GDAttestationAPI.this.utility.sendResult("");
            }
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements AttestationSuccessListener {
        yfdke() {
        }

        @Override // com.blackberry.attestation.AttestationSuccessListener
        public void onSuccess(byte[] bArr, String str) {
            GDLog.DBGPRINTF(14, "GAGAA S");
            GDAttestationAPI.this.utility.sendResult(str);
        }
    }

    protected GDAttestationAPI(String str) {
        this.client = null;
        this.utility = new UtilityAA(this, str);
        this.client = getAttestationBuilder(GDAttestationAPI.class.getClassLoader()).setLogger(GDAttestationLogger.getInstance()).createClient(GDContext.getInstance().getApplicationContext());
    }

    public static synchronized void createActivationInstance() {
        synchronized (GDAttestationAPI.class) {
            if (activationAttestationInstance == null) {
                activationAttestationInstance = new GDAttestationAPI(ACTIVATION);
            }
        }
    }

    public static synchronized void createPeriodicInstance() {
        synchronized (GDAttestationAPI.class) {
            if (periodicAttestationInstance == null) {
                periodicAttestationInstance = new GDAttestationAPI(PERIODIC);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static AttestationBuilder getAttestationBuilder(ClassLoader classLoader) {
        try {
            Class<?> loadClass = classLoader.loadClass(GDLangInterface.lookup("IZjhKbOZOJlJsuMa179zKhbxeb+GNnfpnRHxuXi/pISfhGZNPm8cSBTUdvD/KFcndK0nfLwNTF9smZDo1hlKUQ=="));
            GDLog.DBGPRINTF(14, "GAGAA LEIR");
            return (AttestationBuilder) loadClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            GDLog.DBGPRINTF(13, "GAGAA LNEIR");
            return null;
        }
    }

    @Override // com.good.gd.ndkproxy.attestationApi.IAttestationAPI
    public void startAttestation(String str, int i) {
        this.client.attest(str.getBytes()).addOnSuccessListener(new yfdke()).addOnFailureListener(new hbfhc());
    }
}
