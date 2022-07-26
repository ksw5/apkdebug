package com.good.gd.apache.http.client.methods;

import com.good.gd.apache.http.conn.ClientConnectionRequest;
import com.good.gd.apache.http.conn.ConnectionReleaseTrigger;
import java.io.IOException;

/* loaded from: classes.dex */
public interface AbortableHttpRequest {
    void abort();

    void setConnectionRequest(ClientConnectionRequest clientConnectionRequest) throws IOException;

    void setReleaseTrigger(ConnectionReleaseTrigger connectionReleaseTrigger) throws IOException;
}
