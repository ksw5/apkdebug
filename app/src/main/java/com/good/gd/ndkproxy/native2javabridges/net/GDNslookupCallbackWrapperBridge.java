package com.good.gd.ndkproxy.native2javabridges.net;

import com.good.gd.net.GDNetUtilityImpl;

/* loaded from: classes.dex */
final class GDNslookupCallbackWrapperBridge extends GDNetUtilityImpl.GDNslookupCallbackWrapperHelper {
    GDNslookupCallbackWrapperBridge() {
    }

    private static void onNslookupResponseFailure(Object obj, int i) {
        GDNetUtilityImpl.GDNslookupCallbackWrapperHelper._onNslookupResponseFailure(obj, i);
    }

    private static void onNslookupResponseSuccess(Object obj, String str) {
        GDNetUtilityImpl.GDNslookupCallbackWrapperHelper._onNslookupResponseSuccess(obj, str);
    }
}
