package com.good.gd.apache.http.conn.routing;

import com.good.gd.apache.http.HttpHost;
import java.net.InetAddress;

/* loaded from: classes.dex */
public interface RouteInfo {

    /* loaded from: classes.dex */
    public enum LayerType {
        PLAIN,
        LAYERED
    }

    /* loaded from: classes.dex */
    public enum TunnelType {
        PLAIN,
        TUNNELLED
    }

    int getHopCount();

    HttpHost getHopTarget(int i);

    LayerType getLayerType();

    InetAddress getLocalAddress();

    HttpHost getProxyHost();

    HttpHost getTargetHost();

    TunnelType getTunnelType();

    boolean isLayered();

    boolean isSecure();

    boolean isTunnelled();
}
