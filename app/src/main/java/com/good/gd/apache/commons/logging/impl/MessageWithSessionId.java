package com.good.gd.apache.commons.logging.impl;

/* loaded from: classes.dex */
public class MessageWithSessionId {
    public String message;
    public String sessionId;

    public MessageWithSessionId(String str, String str2) {
        this.sessionId = str;
        this.message = str2;
    }

    public String toString() {
        return this.message;
    }
}
