package com.good.gd.apachehttp;

import com.good.gd.apache.http.protocol.HTTP;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final class Consts {
    public static final int CR = 13;
    public static final int HT = 9;
    public static final int LF = 10;
    public static final int SP = 32;
    public static final Charset UTF_8 = Charset.forName(HTTP.UTF_8);
    public static final Charset ASCII = Charset.forName("US-ASCII");
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    private Consts() {
    }
}
