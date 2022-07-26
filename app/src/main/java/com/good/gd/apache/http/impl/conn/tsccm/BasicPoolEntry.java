package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.impl.conn.AbstractPoolEntry;
import java.lang.ref.ReferenceQueue;

/* loaded from: classes.dex */
public class BasicPoolEntry extends AbstractPoolEntry {
    private final BasicPoolEntryRef reference;

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, ReferenceQueue<Object> referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        if (httpRoute != null) {
            this.reference = new BasicPoolEntryRef(this, referenceQueue);
            return;
        }
        throw new IllegalArgumentException("HTTP route may not be null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final OperatedClientConnection getConnection() {
        return this.connection;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final HttpRoute getPlannedRoute() {
        return this.route;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final BasicPoolEntryRef getWeakRef() {
        return this.reference;
    }
}
