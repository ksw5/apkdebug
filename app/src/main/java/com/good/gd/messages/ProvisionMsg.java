package com.good.gd.messages;

/* loaded from: classes.dex */
public class ProvisionMsg {
    public String email;
    public String nonce;
    public String pin;
    public String server;

    public ProvisionMsg(String str, String str2) {
        this.email = null;
        this.pin = null;
        this.nonce = null;
        this.server = null;
        this.email = str;
        this.pin = str2;
    }

    public String toString() {
        return "ProvisionMsg";
    }

    public ProvisionMsg(String str, String str2, String str3) {
        this.email = null;
        this.pin = null;
        this.nonce = null;
        this.server = null;
        this.email = str;
        this.pin = str2;
        this.server = str3;
    }

    public ProvisionMsg(String str, String str2, String str3, String str4) {
        this.email = null;
        this.pin = null;
        this.nonce = null;
        this.server = null;
        this.email = str;
        this.pin = str2;
        this.nonce = str4;
        this.server = str3;
    }
}
