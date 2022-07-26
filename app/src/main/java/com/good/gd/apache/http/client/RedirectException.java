package com.good.gd.apache.http.client;

import com.good.gd.apache.http.ProtocolException;

/* loaded from: classes.dex */
public class RedirectException extends ProtocolException {
    private static final long serialVersionUID = 4418824536372559326L;

    public RedirectException() {
    }

    public RedirectException(String str) {
        super(str);
    }

    public RedirectException(String str, Throwable th) {
        super(str, th);
    }
}
