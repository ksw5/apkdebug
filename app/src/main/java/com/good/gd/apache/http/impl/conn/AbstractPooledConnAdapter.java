package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractPooledConnAdapter extends AbstractClientConnAdapter {
    protected volatile AbstractPoolEntry poolEntry;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPooledConnAdapter(ClientConnectionManager clientConnectionManager, AbstractPoolEntry abstractPoolEntry) {
        super(clientConnectionManager, abstractPoolEntry.connection);
        this.poolEntry = abstractPoolEntry;
    }

    protected final void assertAttached() {
        if (this.poolEntry != null) {
            return;
        }
        throw new IllegalStateException("Adapter is detached.");
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void close() throws IOException {
        if (this.poolEntry != null) {
            this.poolEntry.shutdownEntry();
        }
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        if (wrappedConnection != null) {
            wrappedConnection.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.conn.AbstractClientConnAdapter
    public void detach() {
        super.detach();
        this.poolEntry = null;
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public HttpRoute getRoute() {
        assertAttached();
        if (this.poolEntry.tracker == null) {
            return null;
        }
        return this.poolEntry.tracker.toRoute();
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public Object getState() {
        assertAttached();
        return this.poolEntry.getState();
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        assertAttached();
        this.poolEntry.layerProtocol(httpContext, httpParams);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException {
        assertAttached();
        this.poolEntry.open(httpRoute, httpContext, httpParams);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void setState(Object obj) {
        assertAttached();
        this.poolEntry.setState(obj);
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void shutdown() throws IOException {
        if (this.poolEntry != null) {
            this.poolEntry.shutdownEntry();
        }
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        if (wrappedConnection != null) {
            wrappedConnection.shutdown();
        }
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void tunnelProxy(HttpHost httpHost, boolean z, HttpParams httpParams) throws IOException {
        assertAttached();
        this.poolEntry.tunnelProxy(httpHost, z, httpParams);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void tunnelTarget(boolean z, HttpParams httpParams) throws IOException {
        assertAttached();
        this.poolEntry.tunnelTarget(z, httpParams);
    }
}
