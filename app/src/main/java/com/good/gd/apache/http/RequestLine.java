package com.good.gd.apache.http;

/* loaded from: classes.dex */
public interface RequestLine {
    String getMethod();

    ProtocolVersion getProtocolVersion();

    String getUri();
}
