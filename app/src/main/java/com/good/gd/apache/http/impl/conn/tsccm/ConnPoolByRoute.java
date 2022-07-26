package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.ConnectionPoolTimeoutException;
import com.good.gd.apache.http.conn.params.ConnManagerParams;
import com.good.gd.apache.http.conn.params.ConnPerRoute;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.params.HttpParams;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/* loaded from: classes.dex */
public class ConnPoolByRoute extends AbstractConnPool {
    private final ConnPerRoute connPerRoute;
    protected Queue<BasicPoolEntry> freeConnections;
    private final Log log = LogFactory.getLog(ConnPoolByRoute.class);
    protected final int maxTotalConnections;
    protected final ClientConnectionOperator operator;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    protected Queue<WaitingThread> waitingThreads;

    /* loaded from: classes.dex */
    class hbfhc implements PoolEntryRequest {
        final /* synthetic */ WaitingThreadAborter dbjc;
        final /* synthetic */ Object jwxax;
        final /* synthetic */ HttpRoute qkduk;

        hbfhc(WaitingThreadAborter waitingThreadAborter, HttpRoute httpRoute, Object obj) {
            this.dbjc = waitingThreadAborter;
            this.qkduk = httpRoute;
            this.jwxax = obj;
        }

        @Override // com.good.gd.apache.http.impl.conn.tsccm.PoolEntryRequest
        public void abortRequest() {
            ConnPoolByRoute.this.poolLock.lock();
            try {
                this.dbjc.abort();
            } finally {
                ConnPoolByRoute.this.poolLock.unlock();
            }
        }

        @Override // com.good.gd.apache.http.impl.conn.tsccm.PoolEntryRequest
        public BasicPoolEntry getPoolEntry(long j, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
            return ConnPoolByRoute.this.getEntryBlocking(this.qkduk, this.jwxax, j, timeUnit, this.dbjc);
        }
    }

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, HttpParams httpParams) {
        if (clientConnectionOperator != null) {
            this.operator = clientConnectionOperator;
            this.freeConnections = createFreeConnQueue();
            this.waitingThreads = createWaitingThreadQueue();
            this.routeToPool = createRouteToPoolMap();
            this.maxTotalConnections = ConnManagerParams.getMaxTotalConnections(httpParams);
            this.connPerRoute = ConnManagerParams.getMaxConnectionsPerRoute(httpParams);
            return;
        }
        throw new IllegalArgumentException("Connection operator may not be null");
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool routeSpecificPool, ClientConnectionOperator clientConnectionOperator) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Creating new connection [" + routeSpecificPool.getRoute() + "]");
        }
        BasicPoolEntry basicPoolEntry = new BasicPoolEntry(clientConnectionOperator, routeSpecificPool.getRoute(), this.refQueue);
        this.poolLock.lock();
        try {
            routeSpecificPool.createdEntry(basicPoolEntry);
            this.numConnections++;
            this.issuedConnections.add(basicPoolEntry.getWeakRef());
            return basicPoolEntry;
        } finally {
            this.poolLock.unlock();
        }
    }

    protected Queue<BasicPoolEntry> createFreeConnQueue() {
        return new LinkedList();
    }

    protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        return new HashMap();
    }

    protected Queue<WaitingThread> createWaitingThreadQueue() {
        return new LinkedList();
    }

    @Override // com.good.gd.apache.http.impl.conn.tsccm.AbstractConnPool
    public void deleteClosedConnections() {
        this.poolLock.lock();
        try {
            Iterator<BasicPoolEntry> it = this.freeConnections.iterator();
            while (it.hasNext()) {
                BasicPoolEntry next = it.next();
                if (!next.getConnection().isOpen()) {
                    it.remove();
                    deleteEntry(next);
                }
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteEntry(BasicPoolEntry basicPoolEntry) {
        HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Deleting connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            closeConnection(basicPoolEntry.getConnection());
            RouteSpecificPool routePool = getRoutePool(plannedRoute, true);
            routePool.deleteEntry(basicPoolEntry);
            this.numConnections--;
            if (routePool.isUnused()) {
                this.routeToPool.remove(plannedRoute);
            }
            this.idleConnHandler.remove(basicPoolEntry.getConnection());
        } finally {
            this.poolLock.unlock();
        }
    }

    protected void deleteLeastUsedEntry() {
        try {
            this.poolLock.lock();
            BasicPoolEntry remove = this.freeConnections.remove();
            if (remove != null) {
                deleteEntry(remove);
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("No free connection to delete.");
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // com.good.gd.apache.http.impl.conn.tsccm.AbstractConnPool
    public void freeEntry(BasicPoolEntry basicPoolEntry, boolean z, long j, TimeUnit timeUnit) {
        HttpRoute plannedRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Freeing connection [" + plannedRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            if (this.isShutDown) {
                closeConnection(basicPoolEntry.getConnection());
                return;
            }
            this.issuedConnections.remove(basicPoolEntry.getWeakRef());
            RouteSpecificPool routePool = getRoutePool(plannedRoute, true);
            if (z) {
                routePool.freeEntry(basicPoolEntry);
                this.freeConnections.add(basicPoolEntry);
                this.idleConnHandler.add(basicPoolEntry.getConnection(), j, timeUnit);
            } else {
                routePool.dropEntry();
                this.numConnections--;
            }
            notifyWaitingThread(routePool);
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getConnectionsInPool(HttpRoute httpRoute) {
        this.poolLock.lock();
        int i = 0;
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, false);
            if (routePool != null) {
                i = routePool.getEntryCount();
            }
            return i;
        } finally {
            this.poolLock.unlock();
        }
    }

    protected BasicPoolEntry getEntryBlocking(HttpRoute httpRoute, Object obj, long j, TimeUnit timeUnit, WaitingThreadAborter waitingThreadAborter) throws ConnectionPoolTimeoutException, InterruptedException {
        BasicPoolEntry basicPoolEntry = null;
        Date date = j > 0 ? new Date(System.currentTimeMillis() + timeUnit.toMillis(j)) : null;
        this.poolLock.lock();
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, true);
            WaitingThread waitingThread = null;
            while (basicPoolEntry == null) {
                if (!this.isShutDown) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Total connections kept alive: " + this.freeConnections.size());
                        this.log.debug("Total issued connections: " + this.issuedConnections.size());
                        this.log.debug("Total allocated connection: " + this.numConnections + " out of " + this.maxTotalConnections);
                    }
                    basicPoolEntry = getFreeEntry(routePool, obj);
                    if (basicPoolEntry != null) {
                        break;
                    }
                    boolean z = routePool.getCapacity() > 0;
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Available capacity: " + routePool.getCapacity() + " out of " + routePool.getMaxEntries() + " [" + httpRoute + "][" + obj + "]");
                    }
                    if (z && this.numConnections < this.maxTotalConnections) {
                        basicPoolEntry = createEntry(routePool, this.operator);
                    } else if (z && !this.freeConnections.isEmpty()) {
                        deleteLeastUsedEntry();
                        basicPoolEntry = createEntry(routePool, this.operator);
                    } else {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Need to wait for connection [" + httpRoute + "][" + obj + "]");
                        }
                        if (waitingThread == null) {
                            waitingThread = newWaitingThread(this.poolLock.newCondition(), routePool);
                            waitingThreadAborter.setWaitingThread(waitingThread);
                        }
                        routePool.queueThread(waitingThread);
                        this.waitingThreads.add(waitingThread);
                        boolean await = waitingThread.await(date);
                        routePool.removeThread(waitingThread);
                        this.waitingThreads.remove(waitingThread);
                        if (!await && date != null && date.getTime() <= System.currentTimeMillis()) {
                            throw new ConnectionPoolTimeoutException("Timeout waiting for connection");
                        }
                    }
                } else {
                    throw new IllegalStateException("Connection pool shut down.");
                }
            }
            return basicPoolEntry;
        } finally {
            this.poolLock.unlock();
        }
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool routeSpecificPool, Object obj) {
        this.poolLock.lock();
        BasicPoolEntry basicPoolEntry = null;
        boolean z = false;
        while (!z) {
            try {
                basicPoolEntry = routeSpecificPool.allocEntry(obj);
                if (basicPoolEntry != null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Getting free connection [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                    }
                    this.freeConnections.remove(basicPoolEntry);
                    if (!this.idleConnHandler.remove(basicPoolEntry.getConnection())) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Closing expired free connection [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                        }
                        closeConnection(basicPoolEntry.getConnection());
                        routeSpecificPool.dropEntry();
                        this.numConnections--;
                    } else {
                        this.issuedConnections.add(basicPoolEntry.getWeakRef());
                        z = true;
                    }
                } else {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("No free connections [" + routeSpecificPool.getRoute() + "][" + obj + "]");
                    }
                    z = true;
                }
            } finally {
                this.poolLock.unlock();
            }
        }
        return basicPoolEntry;
    }

    protected RouteSpecificPool getRoutePool(HttpRoute httpRoute, boolean z) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.routeToPool.get(httpRoute);
            if (routeSpecificPool == null && z) {
                routeSpecificPool = newRouteSpecificPool(httpRoute);
                this.routeToPool.put(httpRoute, routeSpecificPool);
            }
            return routeSpecificPool;
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override // com.good.gd.apache.http.impl.conn.tsccm.AbstractConnPool
    protected void handleLostEntry(HttpRoute httpRoute) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routePool = getRoutePool(httpRoute, true);
            routePool.dropEntry();
            if (routePool.isUnused()) {
                this.routeToPool.remove(httpRoute);
            }
            this.numConnections--;
            notifyWaitingThread(routePool);
        } finally {
            this.poolLock.unlock();
        }
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute httpRoute) {
        return new RouteSpecificPool(httpRoute, this.connPerRoute.getMaxForRoute(httpRoute));
    }

    protected WaitingThread newWaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        return new WaitingThread(condition, routeSpecificPool);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x006e A[Catch: all -> 0x0077, TRY_LEAVE, TryCatch #0 {all -> 0x0077, blocks: (B:24:0x0007, B:26:0x000d, B:28:0x0015, B:29:0x0037, B:11:0x006e, B:3:0x003c, B:5:0x0044, B:7:0x004c, B:8:0x0053, B:19:0x005c, B:21:0x0064), top: B:23:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void notifyWaitingThread(RouteSpecificPool r4) {
        /*
            r3 = this;
            java.util.concurrent.locks.Lock r0 = r3.poolLock
            r0.lock()
            if (r4 == 0) goto L3c
            boolean r0 = r4.hasThread()     // Catch: java.lang.Throwable -> L77
            if (r0 == 0) goto L3c
            com.good.gd.apache.commons.logging.Log r0 = r3.log     // Catch: java.lang.Throwable -> L77
            boolean r0 = r0.isDebugEnabled()     // Catch: java.lang.Throwable -> L77
            if (r0 == 0) goto L37
            com.good.gd.apache.commons.logging.Log r0 = r3.log     // Catch: java.lang.Throwable -> L77
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L77
            r1.<init>()     // Catch: java.lang.Throwable -> L77
            java.lang.String r2 = "Notifying thread waiting on pool ["
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L77
            com.good.gd.apache.http.conn.routing.HttpRoute r2 = r4.getRoute()     // Catch: java.lang.Throwable -> L77
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L77
            java.lang.String r2 = "]"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L77
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L77
            r0.debug(r1)     // Catch: java.lang.Throwable -> L77
        L37:
            com.good.gd.apache.http.impl.conn.tsccm.WaitingThread r4 = r4.nextThread()     // Catch: java.lang.Throwable -> L77
            goto L5b
        L3c:
            java.util.Queue<com.good.gd.apache.http.impl.conn.tsccm.WaitingThread> r4 = r3.waitingThreads     // Catch: java.lang.Throwable -> L77
            boolean r4 = r4.isEmpty()     // Catch: java.lang.Throwable -> L77
            if (r4 != 0) goto L5c
            com.good.gd.apache.commons.logging.Log r4 = r3.log     // Catch: java.lang.Throwable -> L77
            boolean r4 = r4.isDebugEnabled()     // Catch: java.lang.Throwable -> L77
            if (r4 == 0) goto L53
            com.good.gd.apache.commons.logging.Log r4 = r3.log     // Catch: java.lang.Throwable -> L77
            java.lang.String r0 = "Notifying thread waiting on any pool"
            r4.debug(r0)     // Catch: java.lang.Throwable -> L77
        L53:
            java.util.Queue<com.good.gd.apache.http.impl.conn.tsccm.WaitingThread> r4 = r3.waitingThreads     // Catch: java.lang.Throwable -> L77
            java.lang.Object r4 = r4.remove()     // Catch: java.lang.Throwable -> L77
            com.good.gd.apache.http.impl.conn.tsccm.WaitingThread r4 = (com.good.gd.apache.http.impl.conn.tsccm.WaitingThread) r4     // Catch: java.lang.Throwable -> L77
        L5b:
            goto L6c
        L5c:
            com.good.gd.apache.commons.logging.Log r4 = r3.log     // Catch: java.lang.Throwable -> L77
            boolean r4 = r4.isDebugEnabled()     // Catch: java.lang.Throwable -> L77
            if (r4 == 0) goto L6b
            com.good.gd.apache.commons.logging.Log r4 = r3.log     // Catch: java.lang.Throwable -> L77
            java.lang.String r0 = "Notifying no-one, there are no waiting threads"
            r4.debug(r0)     // Catch: java.lang.Throwable -> L77
        L6b:
            r4 = 0
        L6c:
            if (r4 == 0) goto L71
            r4.wakeup()     // Catch: java.lang.Throwable -> L77
        L71:
            java.util.concurrent.locks.Lock r4 = r3.poolLock
            r4.unlock()
            return
        L77:
            r4 = move-exception
            java.util.concurrent.locks.Lock r0 = r3.poolLock
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.impl.conn.tsccm.ConnPoolByRoute.notifyWaitingThread(com.good.gd.apache.http.impl.conn.tsccm.RouteSpecificPool):void");
    }

    @Override // com.good.gd.apache.http.impl.conn.tsccm.AbstractConnPool
    public PoolEntryRequest requestPoolEntry(HttpRoute httpRoute, Object obj) {
        return new hbfhc(new WaitingThreadAborter(), httpRoute, obj);
    }

    @Override // com.good.gd.apache.http.impl.conn.tsccm.AbstractConnPool
    public void shutdown() {
        this.poolLock.lock();
        try {
            super.shutdown();
            Iterator<BasicPoolEntry> it = this.freeConnections.iterator();
            while (it.hasNext()) {
                it.remove();
                closeConnection(it.next().getConnection());
            }
            Iterator<WaitingThread> it2 = this.waitingThreads.iterator();
            while (it2.hasNext()) {
                it2.remove();
                it2.next().wakeup();
            }
            this.routeToPool.clear();
        } finally {
            this.poolLock.unlock();
        }
    }
}
