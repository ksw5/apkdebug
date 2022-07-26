package com.good.gd.net;

import com.good.gd.ApplicationContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.net.GDNetUtility;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDNetUtilityImpl {
    private static ApplicationContext applicationContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class GDNslookupCallbackWrapper {
        private final GDNetUtility.GDNslookupCallback dbjc;

        public GDNslookupCallbackWrapper(GDNetUtility.GDNslookupCallback gDNslookupCallback) {
            this.dbjc = gDNslookupCallback;
        }
    }

    /* loaded from: classes.dex */
    public static class GDNslookupCallbackWrapperHelper {
        /* JADX INFO: Access modifiers changed from: protected */
        public static void _onNslookupResponseFailure(Object obj, int i) {
            if (obj instanceof GDNslookupCallbackWrapper) {
                GDNslookupCallbackWrapper gDNslookupCallbackWrapper = (GDNslookupCallbackWrapper) obj;
                if (gDNslookupCallbackWrapper == null) {
                    throw null;
                }
                GDNetUtilityImpl.applicationContext.getClientHandler().post(new yfdke(gDNslookupCallbackWrapper, i));
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public static void _onNslookupResponseSuccess(Object obj, String str) {
            if (obj instanceof GDNslookupCallbackWrapper) {
                GDNslookupCallbackWrapper gDNslookupCallbackWrapper = (GDNslookupCallbackWrapper) obj;
                if (gDNslookupCallbackWrapper == null) {
                    throw null;
                }
                GDNetUtilityImpl.applicationContext.getClientHandler().post(new hbfhc(gDNslookupCallbackWrapper, str));
            }
        }
    }

    public static void initialize(ApplicationContext applicationContext2) {
        applicationContext = applicationContext2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject nslookupResponseHelper(String str) {
        if (str.length() > 0) {
            try {
                return new JSONObject(str);
            } catch (JSONException e) {
                GDLog.DBGPRINTF(12, "GDNetUtility.nslookupResponseHelper " + e);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void ndkGDNslookup(String str, int i, GDNetUtility.GDNslookupCallback gDNslookupCallback) {
        ndkGDNslookup(str, i, new GDNslookupCallbackWrapper(gDNslookupCallback));
    }

    native void ndkGDNslookup(String str, int i, GDNslookupCallbackWrapper gDNslookupCallbackWrapper);

    /* JADX INFO: Access modifiers changed from: package-private */
    public native void ndkInit();

    public void nslookup(String str, GDNetUtility.GDNslookupType gDNslookupType, GDNetUtility.GDNslookupCallback gDNslookupCallback) {
        ndkGDNslookup(str, gDNslookupType.ordinal(), new GDNslookupCallbackWrapper(gDNslookupCallback));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public native void setNDKWebProxyAppliedByApp(boolean z);
}
