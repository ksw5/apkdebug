package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.HttpConnection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class IdleConnectionHandler {
    private final Log log = LogFactory.getLog(IdleConnectionHandler.class);
    private final Map<HttpConnection, hbfhc> connectionToTimes = new HashMap();

    /* loaded from: classes.dex */
    private static class hbfhc {
        private final long dbjc;
        private final long qkduk;

        hbfhc(long j, long j2, TimeUnit timeUnit) {
            this.dbjc = j;
            if (j2 > 0) {
                this.qkduk = j + timeUnit.toMillis(j2);
            } else {
                this.qkduk = Long.MAX_VALUE;
            }
        }
    }

    public void add(HttpConnection httpConnection, long j, TimeUnit timeUnit) {
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        if (this.log.isDebugEnabled()) {
            this.log.debug("Adding connection at: " + valueOf);
        }
        this.connectionToTimes.put(httpConnection, new hbfhc(valueOf.longValue(), j, timeUnit));
    }

    public void closeExpiredConnections() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for expired connections, now: " + currentTimeMillis);
        }
        Iterator<HttpConnection> it = this.connectionToTimes.keySet().iterator();
        while (it.hasNext()) {
            HttpConnection next = it.next();
            hbfhc hbfhcVar = this.connectionToTimes.get(next);
            if (hbfhcVar.qkduk <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection, expired @: " + hbfhcVar.qkduk);
                }
                it.remove();
                try {
                    next.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }

    public void closeIdleConnections(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking for connections, idleTimeout: " + currentTimeMillis);
        }
        Iterator<HttpConnection> it = this.connectionToTimes.keySet().iterator();
        while (it.hasNext()) {
            HttpConnection next = it.next();
            Long valueOf = Long.valueOf(this.connectionToTimes.get(next).dbjc);
            if (valueOf.longValue() <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection, connection time: " + valueOf);
                }
                it.remove();
                try {
                    next.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }

    public boolean remove(HttpConnection httpConnection) {
        hbfhc remove = this.connectionToTimes.remove(httpConnection);
        if (remove != null) {
            return System.currentTimeMillis() <= remove.qkduk;
        }
        this.log.warn("Removing a connection that never existed!");
        return true;
    }

    public void removeAll() {
        this.connectionToTimes.clear();
    }
}
