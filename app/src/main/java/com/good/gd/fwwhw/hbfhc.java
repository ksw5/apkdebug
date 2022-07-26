package com.good.gd.fwwhw;

import com.blackberry.analytics.analyticsengine.fdyxd;
import com.good.gd.whhmi.ehnkx;
import com.good.gd.whhmi.yfdke;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public final class hbfhc {
    private static String dbjc = String.format(Locale.getDefault(), "%s%s:%d", "https://", "receiver.analytics.blackberry.com", 443);

    public static void dbjc(String str, int i) {
        if (!yfdke.qkduk(str)) {
            dbjc = ehnkx.dbjc(str, i);
            com.good.gd.kloes.hbfhc.jwxax("hbfhc", "[WAN IP] Setting Base Url and Port.");
            com.good.gd.kloes.ehnkx.dbjc("hbfhc", "[WAN IP] Base Url and Port: " + dbjc);
        }
    }

    public static String jwxax() {
        String str = dbjc;
        if (yfdke.qkduk(str)) {
            return null;
        }
        return str + "/ecs/analytics/api/v2/wanIP";
    }

    public static String qkduk() {
        String ztwf = com.good.gd.rutan.hbfhc.tlske().ztwf();
        if (yfdke.qkduk(ztwf)) {
            return null;
        }
        String dbjc2 = ehnkx.dbjc(ztwf);
        return (dbjc2 + "/" + ((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ugfcv()) + "/graph/v1/services/sis/score";
    }

    public static void dbjc(String str) {
        if (!yfdke.qkduk(str)) {
            dbjc = ehnkx.dbjc(str);
            com.good.gd.kloes.hbfhc.jwxax("hbfhc", "[WAN IP] Setting Base Url.");
            com.good.gd.kloes.ehnkx.dbjc("hbfhc", "[WAN IP] Base Url: " + dbjc);
        }
    }

    public static String dbjc() {
        String jwxax = com.good.gd.rutan.hbfhc.tlske().jwxax();
        int wxau = com.good.gd.rutan.hbfhc.tlske().wxau();
        if (yfdke.qkduk(jwxax)) {
            return null;
        }
        return ehnkx.dbjc(jwxax, wxau) + (yfdke.lqox() ? "/discovery.json" : "/.well-known/discovery");
    }

    public static void dbjc(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        for (String str : map.keySet()) {
            com.good.gd.kloes.ehnkx.qkduk("hbfhc", String.format("[%s] = %s", str, map.get(str)));
        }
    }
}
