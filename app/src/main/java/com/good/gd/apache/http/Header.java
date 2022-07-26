package com.good.gd.apache.http;

/* loaded from: classes.dex */
public interface Header {
    HeaderElement[] getElements() throws ParseException;

    String getName();

    String getValue();
}
