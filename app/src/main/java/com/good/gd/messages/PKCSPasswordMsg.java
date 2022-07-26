package com.good.gd.messages;

/* loaded from: classes.dex */
public class PKCSPasswordMsg {
    public String password;
    public String uuid;

    public PKCSPasswordMsg(String str, String str2) {
        this.uuid = str;
        this.password = str2;
    }

    public String toString() {
        return "PKCSPasswordMsg";
    }
}
