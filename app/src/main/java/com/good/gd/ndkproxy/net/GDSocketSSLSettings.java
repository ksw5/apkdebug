package com.good.gd.ndkproxy.net;

/* loaded from: classes.dex */
public interface GDSocketSSLSettings {
    boolean convertToSSL(String str);

    void disableHostVerification();

    void disablePeerVerification();
}
