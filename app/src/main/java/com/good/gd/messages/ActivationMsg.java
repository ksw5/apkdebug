package com.good.gd.messages;

/* loaded from: classes.dex */
public class ActivationMsg {
    public String _delegateAppId;
    public String _nonce;

    public ActivationMsg(String str, String str2) {
        this._delegateAppId = null;
        this._nonce = null;
        this._delegateAppId = str;
        this._nonce = str2;
    }

    public String toString() {
        return "ActivationMsg";
    }
}
