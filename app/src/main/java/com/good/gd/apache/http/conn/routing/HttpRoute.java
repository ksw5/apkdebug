package com.good.gd.apache.http.conn.routing;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.routing.RouteInfo;
import java.net.InetAddress;

/* loaded from: classes.dex */
public final class HttpRoute implements RouteInfo, Cloneable {
    private final LayerType layered;
    private final InetAddress localAddress;
    private final HttpHost[] proxyChain;
    private final boolean secure;
    private final HttpHost targetHost;
    private final TunnelType tunnelled;

    private HttpRoute(InetAddress inetAddress, HttpHost httpHost, HttpHost[] httpHostArr, boolean z, TunnelType tunnelType, LayerType layerType) {
        if (httpHost != null) {
            if (tunnelType == TunnelType.TUNNELLED && httpHostArr == null) {
                throw new IllegalArgumentException("Proxy required if tunnelled.");
            }
            tunnelType = tunnelType == null ? TunnelType.PLAIN : tunnelType;
            layerType = layerType == null ? LayerType.PLAIN : layerType;
            this.targetHost = httpHost;
            this.localAddress = inetAddress;
            this.proxyChain = httpHostArr;
            this.secure = z;
            this.tunnelled = tunnelType;
            this.layered = layerType;
            return;
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    private static HttpHost[] toChain(HttpHost httpHost) {
        if (httpHost == null) {
            return null;
        }
        return new HttpHost[]{httpHost};
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public final boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        int i = 0;
        if (!(obj instanceof HttpRoute)) {
            return false;
        }
        HttpRoute httpRoute = (HttpRoute) obj;
        boolean equals = this.targetHost.equals(httpRoute.targetHost);
        InetAddress inetAddress = this.localAddress;
        InetAddress inetAddress2 = httpRoute.localAddress;
        boolean z2 = equals & (inetAddress == inetAddress2 || (inetAddress != null && inetAddress.equals(inetAddress2)));
        HttpHost[] httpHostArr = this.proxyChain;
        HttpHost[] httpHostArr2 = httpRoute.proxyChain;
        boolean z3 = z2 & (httpHostArr == httpHostArr2 || !(httpHostArr == null || httpHostArr2 == null || httpHostArr.length != httpHostArr2.length));
        if (this.secure != httpRoute.secure || this.tunnelled != httpRoute.tunnelled || this.layered != httpRoute.layered) {
            z = false;
        }
        boolean z4 = z & z3;
        if (z4 && httpHostArr != null) {
            while (z4) {
                HttpHost[] httpHostArr3 = this.proxyChain;
                if (i >= httpHostArr3.length) {
                    break;
                }
                z4 = httpHostArr3[i].equals(httpRoute.proxyChain[i]);
                i++;
            }
        }
        return z4;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final int getHopCount() {
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr == null) {
            return 1;
        }
        return 1 + httpHostArr.length;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final HttpHost getHopTarget(int i) {
        if (i >= 0) {
            int hopCount = getHopCount();
            if (i >= hopCount) {
                throw new IllegalArgumentException("Hop index " + i + " exceeds route length " + hopCount);
            }
            if (i < hopCount - 1) {
                return this.proxyChain[i];
            }
            return this.targetHost;
        }
        throw new IllegalArgumentException("Hop index must not be negative: " + i);
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final LayerType getLayerType() {
        return this.layered;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final InetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final HttpHost getProxyHost() {
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr == null) {
            return null;
        }
        return httpHostArr[0];
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final TunnelType getTunnelType() {
        return this.tunnelled;
    }

    public final int hashCode() {
        int hashCode = this.targetHost.hashCode();
        InetAddress inetAddress = this.localAddress;
        if (inetAddress != null) {
            hashCode ^= inetAddress.hashCode();
        }
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr != null) {
            hashCode ^= httpHostArr.length;
            for (HttpHost httpHost : httpHostArr) {
                hashCode ^= httpHost.hashCode();
            }
        }
        if (this.secure) {
            hashCode ^= 286331153;
        }
        return (hashCode ^ this.tunnelled.hashCode()) ^ this.layered.hashCode();
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final boolean isLayered() {
        return this.layered == LayerType.LAYERED;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final boolean isSecure() {
        return this.secure;
    }

    @Override // com.good.gd.apache.http.conn.routing.RouteInfo
    public final boolean isTunnelled() {
        return this.tunnelled == TunnelType.TUNNELLED;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder((getHopCount() * 30) + 50);
        sb.append("HttpRoute[");
        InetAddress inetAddress = this.localAddress;
        if (inetAddress != null) {
            sb.append(inetAddress);
            sb.append("->");
        }
        sb.append('{');
        if (this.tunnelled == TunnelType.TUNNELLED) {
            sb.append('t');
        }
        if (this.layered == LayerType.LAYERED) {
            sb.append('l');
        }
        if (this.secure) {
            sb.append('s');
        }
        sb.append("}->");
        HttpHost[] httpHostArr = this.proxyChain;
        if (httpHostArr != null) {
            for (HttpHost httpHost : httpHostArr) {
                sb.append(httpHost);
                sb.append("->");
            }
        }
        sb.append(this.targetHost);
        sb.append(']');
        return sb.toString();
    }

    private static HttpHost[] toChain(HttpHost[] httpHostArr) {
        if (httpHostArr == null || httpHostArr.length < 1) {
            return null;
        }
        for (HttpHost httpHost : httpHostArr) {
            if (httpHost == null) {
                throw new IllegalArgumentException("Proxy chain may not contain null elements.");
            }
        }
        HttpHost[] httpHostArr2 = new HttpHost[httpHostArr.length];
        System.arraycopy(httpHostArr, 0, httpHostArr2, 0, httpHostArr.length);
        return httpHostArr2;
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost[] httpHostArr, boolean z, TunnelType tunnelType, LayerType layerType) {
        this(inetAddress, httpHost, toChain(httpHostArr), z, tunnelType, layerType);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean z, TunnelType tunnelType, LayerType layerType) {
        this(inetAddress, httpHost, toChain(httpHost2), z, tunnelType, layerType);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, boolean z) {
        this(inetAddress, httpHost, (HttpHost[]) null, z, TunnelType.PLAIN, LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost) {
        this((InetAddress) null, httpHost, (HttpHost[]) null, false, TunnelType.PLAIN, LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean z) {
        this(inetAddress, httpHost, toChain(httpHost2), z, z ? TunnelType.TUNNELLED : TunnelType.PLAIN, z ? LayerType.LAYERED : LayerType.PLAIN);
        if (httpHost2 != null) {
            return;
        }
        throw new IllegalArgumentException("Proxy host may not be null.");
    }
}
