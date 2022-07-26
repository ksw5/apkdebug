package com.good.gd.net;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class GDNetUtility {
    public static final int GDNSLOOKUP_ERR_CANT_PERFORM_SERVICE = 500;
    @Deprecated
    public static final int GDNSLOOKUP_ERR_HOST_NOT_LISTED = 101;
    public static final int GDNSLOOKUP_ERR_INTERNAL_ERROR = 104;
    public static final int GDNSLOOKUP_ERR_NETWORK_ERROR = 102;
    public static final int GDNSLOOKUP_ERR_PARAMETER_ERROR = 105;
    public static final int GDNSLOOKUP_ERR_PARSING_RESPONSE_ERROR = 103;
    public static final int GDNSLOOKUP_ERR_TIMEOUT = 100;
    private static GDNetUtility instance;
    private GDNetUtilityImpl impl = new GDNetUtilityImpl();

    /* loaded from: classes.dex */
    public enum GDNetUtilityErr {
        GDNslookupErrTimeout(100),
        GDNslookupErrNetworkError(102),
        GDNslookupErrParsingResponseError(103),
        GDNslookupErrInternalError(104),
        GDNslookupErrParameterError(105),
        GDNetUtilityErrCouldNotPerformService(500);

        GDNetUtilityErr(int i) {
        }

        public static GDNetUtilityErr fromInt(int i) {
            if (i == 100) {
                return GDNslookupErrTimeout;
            }
            if (i == 102) {
                return GDNslookupErrNetworkError;
            }
            if (i == 103) {
                return GDNslookupErrParsingResponseError;
            }
            if (i == 104) {
                return GDNslookupErrInternalError;
            }
            if (i == 105) {
                return GDNslookupErrParameterError;
            }
            if (i == 500) {
                return GDNetUtilityErrCouldNotPerformService;
            }
            return GDNslookupErrInternalError;
        }
    }

    /* loaded from: classes.dex */
    public interface GDNslookupCallback {
        void onNslookupResponseFailure(GDNetUtilityErr gDNetUtilityErr);

        void onNslookupResponseSuccess(JSONObject jSONObject);
    }

    /* loaded from: classes.dex */
    public enum GDNslookupType {
        GDNslookupCNAME,
        GDNslookupARECORD
    }

    private GDNetUtility() {
        try {
            GDLog.DBGPRINTF(16, "GDNetUtility: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLockApi) {
                this.impl.ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDNetUtility: Cannot initialize C++ peer\n", e);
        }
    }

    public static synchronized GDNetUtility getInstance() {
        GDNetUtility gDNetUtility;
        synchronized (GDNetUtility.class) {
            if (instance == null) {
                instance = new GDNetUtility();
            }
            gDNetUtility = instance;
        }
        return gDNetUtility;
    }

    public void nslookup(String str, GDNslookupType gDNslookupType, GDNslookupCallback gDNslookupCallback) {
        this.impl.ndkGDNslookup(str, gDNslookupType.ordinal(), gDNslookupCallback);
    }

    public void setWebProxyAppliedByApp(boolean z) {
        GDLog.DBGPRINTF(16, String.format("GDNetUtility::setWebProxyAppliedByApp invoked with value : %b\n", Boolean.valueOf(z)));
        this.impl.setNDKWebProxyAppliedByApp(z);
    }
}
