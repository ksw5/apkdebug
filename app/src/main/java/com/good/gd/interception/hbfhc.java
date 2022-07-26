package com.good.gd.interception;

import com.good.gd.GDServiceDetail;
import com.good.gd.GDServiceType;
import com.good.gd.interception.IntentData;
import com.good.gd.ndkproxy.sharedstore.GDSharedStoreManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
class hbfhc {
    private static final GDServiceDetail dbjc;
    private static final String[] jwxax;
    private static final GDServiceDetail qkduk;
    private static final Map<String, IntentData.ServiceInfo> wxau;

    static {
        GDServiceDetail gDServiceDetail = new GDServiceDetail("com.good.gdservice.open-url.watchdox", GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion, GDServiceType.GD_SERVICE_TYPE_APPLICATION);
        dbjc = gDServiceDetail;
        GDServiceDetail gDServiceDetail2 = new GDServiceDetail("com.blackberry.gdservice.open-catalog", GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion, GDServiceType.GD_SERVICE_TYPE_APPLICATION);
        qkduk = gDServiceDetail2;
        String[] strArr = {"com.blackberry.ema", "com.blackberry.ema.enterprise", "com.good.gdgma", "com.good.goodaccess.enterprise"};
        jwxax = strArr;
        HashMap hashMap = new HashMap();
        hashMap.put("watchdox:\\/\\/(.*)\\/ngdox\\/?\\/.*", new IntentData.ServiceInfo(gDServiceDetail, "open", null, true));
        hashMap.put("uemappstore:\\/\\/(?i:openappdetails\\?.*androidid=).*", new IntentData.ServiceInfo(gDServiceDetail2, "open", strArr, false));
        wxau = Collections.unmodifiableMap(hashMap);
    }

    private hbfhc() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static hbfhc dbjc() {
        return new hbfhc();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IntentData.ServiceInfo dbjc(String str) {
        for (String str2 : wxau.keySet()) {
            if (Pattern.matches(str2, str)) {
                return wxau.get(str2);
            }
        }
        return null;
    }
}
