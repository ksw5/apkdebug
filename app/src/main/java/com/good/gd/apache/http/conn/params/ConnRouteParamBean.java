package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.params.HttpAbstractParamBean;
import com.good.gd.apache.http.params.HttpParams;
import java.net.InetAddress;

/* loaded from: classes.dex */
public class ConnRouteParamBean extends HttpAbstractParamBean {
    public ConnRouteParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setDefaultProxy(HttpHost httpHost) {
        this.params.setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
    }

    public void setForcedRoute(HttpRoute httpRoute) {
        this.params.setParameter(ConnRoutePNames.FORCED_ROUTE, httpRoute);
    }

    public void setLocalAddress(InetAddress inetAddress) {
        this.params.setParameter(ConnRoutePNames.LOCAL_ADDRESS, inetAddress);
    }
}
