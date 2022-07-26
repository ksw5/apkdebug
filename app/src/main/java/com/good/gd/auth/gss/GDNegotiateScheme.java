package com.good.gd.auth.gss;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDNegotiateScheme {
    private boolean isClosed = false;
    private long nativeHandle;

    /* loaded from: classes.dex */
    public enum GssStatusCode {
        STATUS_GSS_S_COMPLETE,
        STATUS_GSS_S_CALL_INACCESSIBLE_READ,
        STATUS_GSS_S_CALL_INACCESSIBLE_WRITE,
        STATUS_GSS_S_CALL_BAD_STRUCTURE,
        STATUS_GSS_S_BAD_MECH,
        STATUS_GSS_S_BAD_NAME,
        STATUS_GSS_S_BAD_NAMETYPE,
        STATUS_GSS_S_BAD_BINDINGS,
        STATUS_GSS_S_BAD_STATUS,
        STATUS_GSS_S_BAD_SIG,
        STATUS_GSS_S_BAD_MIC,
        STATUS_GSS_S_NO_CRED,
        STATUS_GSS_S_NO_CONTEXT,
        STATUS_GSS_S_DEFECTIVE_TOKEN,
        STATUS_GSS_S_DEFECTIVE_CREDENTIAL,
        STATUS_GSS_S_CREDENTIALS_EXPIRED,
        STATUS_GSS_S_CONTEXT_EXPIRED,
        STATUS_GSS_S_FAILURE,
        STATUS_GSS_S_BAD_QOP,
        STATUS_GSS_S_UNAUTHORIZED,
        STATUS_GSS_S_UNAVAILABLE,
        STATUS_GSS_S_DUPLICATE_ELEMENT,
        STATUS_GSS_S_NAME_NOT_MN,
        STATUS_GSS_S_CONTINUE_NEEDED,
        STATUS_GSS_S_DUPLICATE_TOKEN,
        STATUS_GSS_S_OLD_TOKEN,
        STATUS_GSS_S_UNSEQ_TOKEN,
        STATUS_GSS_S_GAP_TOKEN,
        STATUS_UNKNOWN
    }

    @Deprecated
    /* loaded from: classes.dex */
    public enum NegotiateMechanism {
        NEGOTIATE_MECHANISM_SPNEGO("SPNEGO"),
        NEGOTIATE_MECHANISM_KRB5("KRB5");
        
        private String mechanism;

        NegotiateMechanism(String str) {
            this.mechanism = str;
        }

        public String getMechanism() {
            return this.mechanism;
        }
    }

    public GDNegotiateScheme() {
        this.nativeHandle = 0L;
        GDLog.DBGPRINTF(16, "GDNegotiateScheme::GDNegotiateScheme().\n");
        ndkInit();
        this.nativeHandle = createNativeHandle();
    }

    private native long createNativeHandle();

    private native void deleteNativeHandle(long j);

    private native byte[] native_generateGssApiData(long j, byte[] bArr, String str, String str2, boolean z);

    private native byte[] native_generateGssApiDataLegacy(long j, byte[] bArr, String str, boolean z);

    private native GssStatusCode native_getGssApiStatus(long j);

    private native boolean native_gssContextEstablishmentInitiated(long j);

    private native void ndkInit();

    public void closeScheme() {
        long j = this.nativeHandle;
        if (j != 0) {
            deleteNativeHandle(j);
            this.nativeHandle = 0L;
        } else {
            GDLog.DBGPRINTF(16, "GDNegotiateScheme::closeScheme() No valid nativeHandle.\n");
        }
        this.isClosed = true;
    }

    protected void finalize() throws Throwable {
        GDLog.DBGPRINTF(16, "GDNegotiateScheme::finalize()\n");
        if (!this.isClosed) {
            closeScheme();
        }
        super.finalize();
    }

    public String generateGssApiData(String str, String str2, boolean z) {
        long j = this.nativeHandle;
        if (j != 0) {
            byte[] native_generateGssApiDataLegacy = native_generateGssApiDataLegacy(j, str.getBytes(), str2, z);
            if (native_generateGssApiDataLegacy != null) {
                return new String(native_generateGssApiDataLegacy);
            }
        } else {
            GDLog.DBGPRINTF(16, "GDNegotiateScheme::generateGssApiData() No valid nativeHandle.\n");
        }
        return "";
    }

    public GssStatusCode getGssApiStatus() {
        long j = this.nativeHandle;
        if (j != 0) {
            return native_getGssApiStatus(j);
        }
        GDLog.DBGPRINTF(16, "GDNegotiateScheme::getGssApiStatus() No valid nativeHandle.\n");
        return GssStatusCode.STATUS_UNKNOWN;
    }

    public boolean gssContextEstablishmentInitiated() {
        long j = this.nativeHandle;
        if (j != 0) {
            return native_gssContextEstablishmentInitiated(j);
        }
        GDLog.DBGPRINTF(16, "GDNegotiateScheme::gssContextEstablishmentInitiated() No valid nativeHandle.\n");
        return false;
    }

    @Deprecated
    public String generateGssApiData(String str, NegotiateMechanism negotiateMechanism, String str2, boolean z) {
        long j = this.nativeHandle;
        if (j != 0) {
            byte[] native_generateGssApiData = native_generateGssApiData(j, str.getBytes(), negotiateMechanism.getMechanism(), str2, z);
            if (native_generateGssApiData != null) {
                return new String(native_generateGssApiData);
            }
        } else {
            GDLog.DBGPRINTF(16, "GDNegotiateScheme::generateGssApiData1() No valid nativeHandle.\n");
        }
        return "";
    }
}
