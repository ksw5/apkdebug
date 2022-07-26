package com.good.gd;

import com.good.gd.context.GDContext;
import com.good.gd.mam.GDEntitlement;
import com.good.gd.mam.GDMobileApplicationManagement;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class GDMamWrapper {
    private static GDMamWrapper _instance;
    private Map<Integer, String> mPendingMamRequests = new HashMap();
    private Map<Integer, GDEntitlementVersionsRequestCallback> mPendingCallbacks = new HashMap();

    private GDMamWrapper() {
    }

    private static void checkEntitlementId(String str) {
        if (str != null) {
            if (str.trim().length() == 0) {
                throw new IllegalArgumentException("The argument 'entitlementId' cannot be empty");
            }
            return;
        }
        throw new IllegalArgumentException("The argument 'entitlementId' cannot be null");
    }

    public static synchronized GDMamWrapper getInstance() {
        GDMamWrapper gDMamWrapper;
        synchronized (GDMamWrapper.class) {
            if (_instance == null) {
                _instance = new GDMamWrapper();
            }
            gDMamWrapper = _instance;
        }
        return gDMamWrapper;
    }

    public int getEntitlementVersions(String str, GDEntitlementVersionsRequestCallback gDEntitlementVersionsRequestCallback) {
        GDLog.DBGPRINTF(16, "GDMamWrapper::getEntitlementVersions: entitlementId:" + str + "\n");
        if (gDEntitlementVersionsRequestCallback != null) {
            GDContext.getInstance().checkAuthorized();
            checkEntitlementId(str);
            int entitlements = GDMobileApplicationManagement.getEntitlements(Integer.MAX_VALUE);
            if (entitlements >= 0) {
                GDLog.DBGPRINTF(16, "GDMamWrapper::getEntitlementVersions: request successfully made. requestId: " + entitlements + "\n");
                this.mPendingMamRequests.put(Integer.valueOf(entitlements), str);
                this.mPendingCallbacks.put(Integer.valueOf(entitlements), gDEntitlementVersionsRequestCallback);
            } else {
                GDLog.DBGPRINTF(12, "GDMamWrapper::getEntitlementVersions: Failed to request getEntitlementVersions\n");
            }
            return entitlements;
        }
        throw new RuntimeException("GDEntitlementVersionsRequestCallback is null");
    }

    public void onGetEntitlementVersionsResponse(int i, int i2, List<GDEntitlement> list) {
        List<GDVersion> list2;
        GDLog.DBGPRINTF(16, "GDMamWrapper::onGetEntitlementVersionsResponse: requestId:" + i + " status: " + i2 + "\n");
        String str = this.mPendingMamRequests.get(Integer.valueOf(i));
        this.mPendingMamRequests.remove(Integer.valueOf(i));
        GDEntitlementVersionsRequestCallback gDEntitlementVersionsRequestCallback = this.mPendingCallbacks.get(Integer.valueOf(i));
        this.mPendingCallbacks.remove(Integer.valueOf(i));
        if (str != null && gDEntitlementVersionsRequestCallback != null && i2 == 0 && list != null) {
            GDLog.DBGPRINTF(16, "GDMamWrapper::onGetEntitlementVersionsResponse: requestId:" + i + " Found request in pending requests.\n");
            for (GDEntitlement gDEntitlement : list) {
                String entitlementIdentifier = gDEntitlement.getEntitlementIdentifier();
                if (entitlementIdentifier.equals(str)) {
                    GDLog.DBGPRINTF(16, "GDMamWrapper::onGetEntitlementVersionsResponse: requestId:" + i + " Found catalog versions for " + entitlementIdentifier + "\n");
                    list2 = gDEntitlement.getEntitlementVersions();
                    break;
                }
            }
        } else {
            GDLog.DBGPRINTF(13, "GDMamWrapper::onGetEntitlementVersionsResponse status=" + i2 + "\n");
        }
        list2 = null;
        if (list2 == null) {
            list2 = new ArrayList<>();
        }
        if (gDEntitlementVersionsRequestCallback != null) {
            gDEntitlementVersionsRequestCallback.onReceivedEntitlementVersions(i, i2, list2);
        }
    }
}
