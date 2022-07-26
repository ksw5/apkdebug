package com.good.gd.npgvd;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.apache.http.protocol.HTTP;
import java.util.HashMap;

/* loaded from: classes.dex */
public class vzw extends com.good.gd.zwn.hbfhc {
    @Override // com.good.gd.zwn.hbfhc
    public HashMap<String, String> dbjc() {
        HashMap<String, String> dbjc = super.dbjc();
        int lqox = BlackberryAnalyticsCommon.rynix().lqox();
        if (lqox == 2) {
            dbjc.put(HTTP.CONTENT_TYPE, "application/vnd.bberry.analytics.events.dynamics.2");
            dbjc.put("X-BBRY-Timestamp", String.valueOf(System.currentTimeMillis()));
        } else if (lqox == 3 || lqox == 4) {
            dbjc.put(HTTP.CONTENT_TYPE, "application/vnd.bbry.analytics.events.3");
            dbjc.put("X-BBRY-Timestamp", String.valueOf(System.currentTimeMillis()));
        }
        return dbjc;
    }
}
