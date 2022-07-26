package com.good.gd.messages;

/* loaded from: classes.dex */
public class CSRMsg {
    public String _csr;
    public String _delegateAppId;
    public String _token;
    public String _upn;

    public CSRMsg(String str, String str2, String str3, String str4) {
        this._delegateAppId = null;
        this._token = null;
        this._csr = null;
        this._upn = null;
        this._delegateAppId = str;
        this._csr = str2;
        this._upn = str3;
        this._token = str4;
    }

    public String toString() {
        return "CSRMsg";
    }
}
