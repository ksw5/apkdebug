package com.good.gd.apache.http.client;

import com.good.gd.apache.http.ProtocolException;

/* loaded from: classes.dex */
public class NonRepeatableRequestException extends ProtocolException {
    private static final long serialVersionUID = 82685265288806048L;

    public NonRepeatableRequestException() {
    }

    public NonRepeatableRequestException(String str) {
        super(str);
    }
}
