package com.good.gd.whhmi;

import com.good.gd.apache.http.HttpHost;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes.dex */
public final class ehnkx {
    public static String dbjc(String str, int i) {
        URL url;
        if (yfdke.qkduk(str)) {
            return null;
        }
        String trim = str.trim();
        try {
            if (!trim.startsWith("https://") && !trim.startsWith("http://")) {
                url = new URL("https://" + trim);
            } else {
                url = new URL(trim);
            }
            String host = url.getHost();
            int port = url.getPort();
            if (host != null && !host.trim().isEmpty()) {
                if (port > 0) {
                    return dbjc(trim);
                }
                if (i > 0) {
                    if (trim.endsWith("/")) {
                        trim = trim.substring(0, trim.length() - 1);
                    }
                    trim = trim + ":" + i;
                }
                return dbjc(trim);
            }
            return null;
        } catch (MalformedURLException e) {
            com.good.gd.kloes.hbfhc.wxau("ehnkx", "URL is not in valid format.");
            return null;
        }
    }

    public static String dbjc(String str) {
        boolean z;
        String str2;
        int i;
        if (yfdke.qkduk(str)) {
            return null;
        }
        try {
            String trim = str.trim();
            boolean z2 = false;
            if (!trim.startsWith("https://") && !trim.startsWith("http://")) {
                trim = "https://" + trim;
                z = false;
            } else {
                z = true;
            }
            URL url = new URL(trim);
            String host = url.getHost();
            String protocol = url.getProtocol();
            int port = url.getPort();
            if (host == null || host.trim().isEmpty()) {
                return null;
            }
            if (port > 0) {
                z2 = true;
            }
            if (z || z2) {
                if (!z) {
                    protocol = port == 443 ? "https" : HttpHost.DEFAULT_SCHEME_NAME;
                }
                if (z2) {
                    str2 = protocol;
                    i = port;
                } else if ("https".equals(protocol)) {
                    str2 = protocol;
                    i = 443;
                } else {
                    str2 = protocol;
                    i = 8080;
                }
            } else {
                str2 = "https";
                i = 443;
            }
            return new URL(str2, host, i, "", null).toString();
        } catch (MalformedURLException e) {
            com.good.gd.kloes.hbfhc.wxau("ehnkx", "Failed to retrofit the provided URL, Provided URL is not in valid format.");
            return null;
        }
    }
}
