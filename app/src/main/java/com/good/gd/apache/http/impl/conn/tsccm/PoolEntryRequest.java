package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.http.conn.ConnectionPoolTimeoutException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public interface PoolEntryRequest {
    void abortRequest();

    BasicPoolEntry getPoolEntry(long j, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException;
}
