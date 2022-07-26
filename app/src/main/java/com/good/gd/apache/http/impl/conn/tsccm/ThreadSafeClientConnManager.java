package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.ClientConnectionRequest;
import com.good.gd.apache.http.conn.ConnectionPoolTimeoutException;
import com.good.gd.apache.http.conn.ManagedClientConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.impl.conn.DefaultClientConnectionOperator;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ThreadSafeClientConnManager implements ClientConnectionManager {
    protected ClientConnectionOperator connOperator;
    protected final AbstractConnPool connectionPool;
    private final Log log = LogFactory.getLog(ThreadSafeClientConnManager.class);
    protected SchemeRegistry schemeRegistry;

    /* loaded from: classes.dex */
    class hbfhc implements ClientConnectionRequest {
        final /* synthetic */ PoolEntryRequest dbjc;
        final /* synthetic */ HttpRoute qkduk;

        hbfhc(PoolEntryRequest poolEntryRequest, HttpRoute httpRoute) {
            this.dbjc = poolEntryRequest;
            this.qkduk = httpRoute;
        }

        @Override // com.good.gd.apache.http.conn.ClientConnectionRequest
        public void abortRequest() {
            this.dbjc.abortRequest();
        }

        @Override // com.good.gd.apache.http.conn.ClientConnectionRequest
        public ManagedClientConnection getConnection(long j, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
            if (this.qkduk != null) {
                if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
                    ThreadSafeClientConnManager.this.log.debug("ThreadSafeClientConnManager.getConnection: " + this.qkduk + ", timeout = " + j);
                }
                return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, this.dbjc.getPoolEntry(j, timeUnit));
            }
            throw new IllegalArgumentException("Route may not be null.");
        }
    }

    public ThreadSafeClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        if (httpParams != null) {
            this.schemeRegistry = schemeRegistry;
            this.connOperator = createConnectionOperator(schemeRegistry);
            this.connectionPool = createConnectionPool(httpParams);
            return;
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void closeExpiredConnections() {
        this.connectionPool.closeExpiredConnections();
        this.connectionPool.deleteClosedConnections();
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        this.connectionPool.closeIdleConnections(j, timeUnit);
        this.connectionPool.deleteClosedConnections();
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    protected AbstractConnPool createConnectionPool(HttpParams httpParams) {
        ConnPoolByRoute connPoolByRoute = new ConnPoolByRoute(this.connOperator, httpParams);
        connPoolByRoute.enableConnectionGC();
        return connPoolByRoute;
    }

    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        return ((ConnPoolByRoute) this.connectionPool).getConnectionsInPool(httpRoute);
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void releaseConnection(ManagedClientConnection managedClientConnection, long j, TimeUnit timeUnit) {
        BasicPoolEntry basicPoolEntry;
        boolean z;
        BasicPoolEntry basicPoolEntry2;
        boolean isMarkedReusable;
        if (managedClientConnection instanceof BasicPooledConnAdapter) {
            BasicPooledConnAdapter basicPooledConnAdapter = (BasicPooledConnAdapter) managedClientConnection;
            if (basicPooledConnAdapter.getPoolEntry() != null && basicPooledConnAdapter.getManager() != this) {
                throw new IllegalArgumentException("Connection not obtained from this manager.");
            }
            try {
                try {
                    if (basicPooledConnAdapter.isOpen() && !basicPooledConnAdapter.isMarkedReusable()) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Released connection open but not marked reusable.");
                        }
                        basicPooledConnAdapter.shutdown();
                    }
                    basicPoolEntry2 = (BasicPoolEntry) basicPooledConnAdapter.getPoolEntry();
                    isMarkedReusable = basicPooledConnAdapter.isMarkedReusable();
                    basicPooledConnAdapter.detach();
                } catch (IOException e) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Exception shutting down released connection.", e);
                    }
                    BasicPoolEntry basicPoolEntry3 = (BasicPoolEntry) basicPooledConnAdapter.getPoolEntry();
                    boolean isMarkedReusable2 = basicPooledConnAdapter.isMarkedReusable();
                    basicPooledConnAdapter.detach();
                    if (basicPoolEntry3 == null) {
                        return;
                    }
                    basicPoolEntry = basicPoolEntry3;
                    z = isMarkedReusable2;
                }
                if (basicPoolEntry2 != null) {
                    basicPoolEntry = basicPoolEntry2;
                    z = isMarkedReusable;
                    this.connectionPool.freeEntry(basicPoolEntry, z, j, timeUnit);
                    return;
                }
                return;
            } catch (Throwable th) {
                BasicPoolEntry basicPoolEntry4 = (BasicPoolEntry) basicPooledConnAdapter.getPoolEntry();
                boolean isMarkedReusable3 = basicPooledConnAdapter.isMarkedReusable();
                basicPooledConnAdapter.detach();
                if (basicPoolEntry4 != null) {
                    this.connectionPool.freeEntry(basicPoolEntry4, isMarkedReusable3, j, timeUnit);
                }
                throw th;
            }
        }
        throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object obj) {
        return new hbfhc(this.connectionPool.requestPoolEntry(httpRoute, obj), httpRoute);
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void shutdown() {
        this.connectionPool.shutdown();
    }

    public int getConnectionsInPool() {
        int i;
        synchronized (this.connectionPool) {
            i = this.connectionPool.numConnections;
        }
        return i;
    }
}
