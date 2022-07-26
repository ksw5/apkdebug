package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.routing.RouteTracker;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractPoolEntry {
    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected volatile HttpRoute route;
    protected volatile Object state;
    protected volatile RouteTracker tracker;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        if (clientConnectionOperator != null) {
            this.connOperator = clientConnectionOperator;
            this.connection = clientConnectionOperator.createConnection();
            this.route = httpRoute;
            this.tracker = null;
            return;
        }
        throw new IllegalArgumentException("Connection operator may not be null");
    }

    public Object getState() {
        return this.state;
    }

    public void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (httpParams != null) {
            if (this.tracker != null && this.tracker.isConnected()) {
                if (this.tracker.isTunnelled()) {
                    if (!this.tracker.isLayered()) {
                        this.connOperator.updateSecureConnection(this.connection, this.tracker.getTargetHost(), httpContext, httpParams);
                        this.tracker.layerProtocol(this.connection.isSecure());
                        return;
                    }
                    throw new IllegalStateException("Multiple protocol layering not supported.");
                }
                throw new IllegalStateException("Protocol layering without a tunnel not supported.");
            }
            throw new IllegalStateException("Connection not open.");
        }
        throw new IllegalArgumentException("Parameters must not be null.");
    }

    public void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (httpRoute != null) {
            if (httpParams != null) {
                if (this.tracker != null && this.tracker.isConnected()) {
                    throw new IllegalStateException("Connection already open.");
                }
                this.tracker = new RouteTracker(httpRoute);
                HttpHost proxyHost = httpRoute.getProxyHost();
                this.connOperator.openConnection(this.connection, proxyHost != null ? proxyHost : httpRoute.getTargetHost(), httpRoute.getLocalAddress(), httpContext, httpParams);
                RouteTracker routeTracker = this.tracker;
                if (routeTracker == null) {
                    throw new IOException("Request aborted");
                }
                if (proxyHost == null) {
                    routeTracker.connectTarget(this.connection.isSecure());
                    return;
                } else {
                    routeTracker.connectProxy(proxyHost, this.connection.isSecure());
                    return;
                }
            }
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        throw new IllegalArgumentException("Route must not be null.");
    }

    public void setState(Object obj) {
        this.state = obj;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void shutdownEntry() {
        this.tracker = null;
    }

    public void tunnelProxy(HttpHost httpHost, boolean z, HttpParams httpParams) throws IOException {
        if (httpHost != null) {
            if (httpParams != null) {
                if (this.tracker != null && this.tracker.isConnected()) {
                    this.connection.update(null, httpHost, z, httpParams);
                    this.tracker.tunnelProxy(httpHost, z);
                    return;
                }
                throw new IllegalStateException("Connection not open.");
            }
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        throw new IllegalArgumentException("Next proxy must not be null.");
    }

    public void tunnelTarget(boolean z, HttpParams httpParams) throws IOException {
        if (httpParams != null) {
            if (this.tracker != null && this.tracker.isConnected()) {
                if (!this.tracker.isTunnelled()) {
                    this.connection.update(null, this.tracker.getTargetHost(), z, httpParams);
                    this.tracker.tunnelTarget(z);
                    return;
                }
                throw new IllegalStateException("Connection is already tunnelled.");
            }
            throw new IllegalStateException("Connection not open.");
        }
        throw new IllegalArgumentException("Parameters must not be null.");
    }
}
