package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.ClientConnectionRequest;
import com.good.gd.apache.http.conn.ManagedClientConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.routing.RouteTracker;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class SingleClientConnManager implements ClientConnectionManager {
    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected boolean alwaysShutDown;
    protected ClientConnectionOperator connOperator;
    protected long connectionExpiresTime;
    protected volatile boolean isShutDown;
    protected long lastReleaseTime;
    private final Log log = LogFactory.getLog(getClass());
    protected ConnAdapter managedConn;
    protected SchemeRegistry schemeRegistry;
    protected PoolEntry uniquePoolEntry;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public class ConnAdapter extends AbstractPooledConnAdapter {
        protected ConnAdapter(PoolEntry poolEntry, HttpRoute httpRoute) {
            super(SingleClientConnManager.this, poolEntry);
            markReusable();
            poolEntry.route = httpRoute;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public class PoolEntry extends AbstractPoolEntry {
        protected PoolEntry() {
            super(SingleClientConnManager.this.connOperator, null);
        }

        protected void close() throws IOException {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.close();
            }
        }

        protected void shutdown() throws IOException {
            shutdownEntry();
            if (this.connection.isOpen()) {
                this.connection.shutdown();
            }
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements ClientConnectionRequest {
        final /* synthetic */ HttpRoute dbjc;
        final /* synthetic */ Object qkduk;

        hbfhc(HttpRoute httpRoute, Object obj) {
            this.dbjc = httpRoute;
            this.qkduk = obj;
        }

        @Override // com.good.gd.apache.http.conn.ClientConnectionRequest
        public void abortRequest() {
        }

        @Override // com.good.gd.apache.http.conn.ClientConnectionRequest
        public ManagedClientConnection getConnection(long j, TimeUnit timeUnit) {
            return SingleClientConnManager.this.getConnection(this.dbjc, this.qkduk);
        }
    }

    public SingleClientConnManager(HttpParams httpParams, SchemeRegistry schemeRegistry) {
        if (schemeRegistry != null) {
            this.schemeRegistry = schemeRegistry;
            this.connOperator = createConnectionOperator(schemeRegistry);
            this.uniquePoolEntry = new PoolEntry();
            this.managedConn = null;
            this.lastReleaseTime = -1L;
            this.alwaysShutDown = false;
            this.isShutDown = false;
            return;
        }
        throw new IllegalArgumentException("Scheme registry must not be null.");
    }

    protected final void assertStillUp() throws IllegalStateException {
        if (!this.isShutDown) {
            return;
        }
        throw new IllegalStateException("Manager is shut down.");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void closeExpiredConnections() {
        if (System.currentTimeMillis() >= this.connectionExpiresTime) {
            closeIdleConnections(0L, TimeUnit.MILLISECONDS);
        }
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        assertStillUp();
        if (timeUnit != null) {
            if (this.managedConn != null || !this.uniquePoolEntry.connection.isOpen()) {
                return;
            }
            if (this.lastReleaseTime > System.currentTimeMillis() - timeUnit.toMillis(j)) {
                return;
            }
            try {
                this.uniquePoolEntry.close();
                return;
            } catch (IOException e) {
                this.log.debug("Problem closing idle connection.", e);
                return;
            }
        }
        throw new IllegalArgumentException("Time unit must not be null.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }

    public ManagedClientConnection getConnection(HttpRoute httpRoute, Object obj) {
        boolean z;
        if (httpRoute != null) {
            assertStillUp();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Get connection for route " + httpRoute);
            }
            if (this.managedConn != null) {
                revokeConnection();
            }
            closeExpiredConnections();
            boolean z2 = true;
            boolean z3 = false;
            if (this.uniquePoolEntry.connection.isOpen()) {
                RouteTracker routeTracker = this.uniquePoolEntry.tracker;
                if (routeTracker == null || !routeTracker.toRoute().equals(httpRoute)) {
                    z = false;
                    z3 = true;
                } else {
                    z = false;
                }
            } else {
                z = true;
            }
            if (!z3) {
                z2 = z;
            } else {
                try {
                    this.uniquePoolEntry.shutdown();
                } catch (IOException e) {
                    this.log.debug("Problem shutting down connection.", e);
                }
            }
            if (z2) {
                this.uniquePoolEntry = new PoolEntry();
            }
            ConnAdapter connAdapter = new ConnAdapter(this.uniquePoolEntry, httpRoute);
            this.managedConn = connAdapter;
            return connAdapter;
        }
        throw new IllegalArgumentException("Route may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0076, code lost:
        if (r10 > 0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a4, code lost:
        r8.connectionExpiresTime = Long.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a7, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009a, code lost:
        r8.connectionExpiresTime = r12.toMillis(r10) + r8.lastReleaseTime;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0098, code lost:
        if (r10 <= 0) goto L30;
     */
    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void releaseConnection(ManagedClientConnection r9, long r10, TimeUnit r12) {
        /*
            r8 = this;
            r8.assertStillUp()
            boolean r0 = r9 instanceof com.good.gd.apache.http.impl.conn.SingleClientConnManager.ConnAdapter
            if (r0 == 0) goto Lc5
            com.good.gd.apache.commons.logging.Log r0 = r8.log
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L27
            com.good.gd.apache.commons.logging.Log r0 = r8.log
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Releasing connection "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r9)
            java.lang.String r1 = r1.toString()
            r0.debug(r1)
        L27:
            com.good.gd.apache.http.impl.conn.SingleClientConnManager$ConnAdapter r9 = (com.good.gd.apache.http.impl.conn.SingleClientConnManager.ConnAdapter) r9
            com.good.gd.apache.http.impl.conn.AbstractPoolEntry r0 = r9.poolEntry
            if (r0 != 0) goto L2e
            return
        L2e:
            com.good.gd.apache.http.conn.ClientConnectionManager r0 = r9.getManager()
            if (r0 == 0) goto L3f
            if (r0 != r8) goto L37
            goto L3f
        L37:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Connection not obtained from this manager."
            r9.<init>(r10)
            throw r9
        L3f:
            r0 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r2 = 0
            r4 = 0
            boolean r5 = r9.isOpen()     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            if (r5 == 0) goto L69
            boolean r5 = r8.alwaysShutDown     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            if (r5 != 0) goto L57
            boolean r5 = r9.isMarkedReusable()     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            if (r5 != 0) goto L69
        L57:
            com.good.gd.apache.commons.logging.Log r5 = r8.log     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            boolean r5 = r5.isDebugEnabled()     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            if (r5 == 0) goto L66
            com.good.gd.apache.commons.logging.Log r5 = r8.log     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
            java.lang.String r6 = "Released connection open but not reusable."
            r5.debug(r6)     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
        L66:
            r9.shutdown()     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7b
        L69:
            r9.detach()
            r8.managedConn = r4
            long r4 = java.lang.System.currentTimeMillis()
            r8.lastReleaseTime = r4
            int r9 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r9 <= 0) goto La4
            goto L9a
        L79:
            r5 = move-exception
            goto La8
        L7b:
            r5 = move-exception
            com.good.gd.apache.commons.logging.Log r6 = r8.log     // Catch: java.lang.Throwable -> L79
            boolean r6 = r6.isDebugEnabled()     // Catch: java.lang.Throwable -> L79
            if (r6 == 0) goto L8b
            com.good.gd.apache.commons.logging.Log r6 = r8.log     // Catch: java.lang.Throwable -> L79
            java.lang.String r7 = "Exception shutting down released connection."
            r6.debug(r7, r5)     // Catch: java.lang.Throwable -> L79
        L8b:
            r9.detach()
            r8.managedConn = r4
            long r4 = java.lang.System.currentTimeMillis()
            r8.lastReleaseTime = r4
            int r9 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r9 <= 0) goto La4
        L9a:
            long r9 = r12.toMillis(r10)
            long r11 = r8.lastReleaseTime
            long r9 = r9 + r11
            r8.connectionExpiresTime = r9
            goto La7
        La4:
            r8.connectionExpiresTime = r0
        La7:
            return
        La8:
            r9.detach()
            r8.managedConn = r4
            long r6 = java.lang.System.currentTimeMillis()
            r8.lastReleaseTime = r6
            int r9 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r9 <= 0) goto Lc1
            long r9 = r12.toMillis(r10)
            long r11 = r8.lastReleaseTime
            long r9 = r9 + r11
            r8.connectionExpiresTime = r9
            goto Lc4
        Lc1:
            r8.connectionExpiresTime = r0
        Lc4:
            throw r5
        Lc5:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Connection class mismatch, connection not obtained from this manager."
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.impl.conn.SingleClientConnManager.releaseConnection(com.good.gd.apache.http.conn.ManagedClientConnection, long, java.util.concurrent.TimeUnit):void");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public final ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object obj) {
        return new hbfhc(httpRoute, obj);
    }

    protected void revokeConnection() {
        if (this.managedConn == null) {
            return;
        }
        this.log.warn(MISUSE_MESSAGE);
        this.managedConn.detach();
        try {
            this.uniquePoolEntry.shutdown();
        } catch (IOException e) {
            this.log.debug("Problem while shutting down connection.", e);
        }
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionManager
    public void shutdown() {
        this.isShutDown = true;
        ConnAdapter connAdapter = this.managedConn;
        if (connAdapter != null) {
            connAdapter.detach();
        }
        try {
            PoolEntry poolEntry = this.uniquePoolEntry;
            if (poolEntry != null) {
                poolEntry.shutdown();
            }
        } catch (IOException e) {
            this.log.debug("Problem while shutting down manager.", e);
        } finally {
            this.uniquePoolEntry = null;
        }
    }
}
