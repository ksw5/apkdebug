package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.http.conn.routing.HttpRoute;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class BasicPoolEntryRef extends WeakReference<BasicPoolEntry> {
    private final HttpRoute route;

    public BasicPoolEntryRef(BasicPoolEntry basicPoolEntry, ReferenceQueue<Object> referenceQueue) {
        super(basicPoolEntry, referenceQueue);
        if (basicPoolEntry != null) {
            this.route = basicPoolEntry.getPlannedRoute();
            return;
        }
        throw new IllegalArgumentException("Pool entry must not be null.");
    }

    public final HttpRoute getRoute() {
        return this.route;
    }
}
