package com.good.gd.apache.http.conn;

import java.io.IOException;

/* loaded from: classes.dex */
public interface ConnectionReleaseTrigger {
    void abortConnection() throws IOException;

    void releaseConnection() throws IOException;
}
