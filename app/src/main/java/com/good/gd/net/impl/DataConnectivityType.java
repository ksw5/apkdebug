package com.good.gd.net.impl;

import com.good.gd.ndkproxy.PasswordType;

/* loaded from: classes.dex */
public enum DataConnectivityType {
    DC_CAPTIVE_PORTAL("CAPTIVE_PORTAL"),
    DC_OPEN("OPEN"),
    DC_UNKNOWN(PasswordType.SMNOTYETSET);
    
    private String mName;

    DataConnectivityType(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }
}
