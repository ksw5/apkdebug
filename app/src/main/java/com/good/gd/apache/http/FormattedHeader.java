package com.good.gd.apache.http;

import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public interface FormattedHeader extends Header {
    CharArrayBuffer getBuffer();

    int getValuePos();
}
