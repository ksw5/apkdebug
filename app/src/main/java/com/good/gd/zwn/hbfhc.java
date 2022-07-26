package com.good.gd.zwn;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.apache.http.protocol.HTTP;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class hbfhc implements gioey {
    public HashMap<String, String> dbjc() {
        HashMap<String, String> hashMap = new HashMap<>();
        if (BlackberryAnalyticsCommon.rynix().lqox() == 1) {
            hashMap.put(HTTP.CONTENT_TYPE, "application/vnd.good.analytics.ecs.appusage.2");
        }
        hashMap.put("Accept", "application/json; charset=utf-8");
        hashMap.put(HTTP.CONTENT_ENCODING, "gzip");
        hashMap.put("X-BBRY-Datapoint-ID", mjbm.lqox());
        return hashMap;
    }
}
