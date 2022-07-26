package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.params.HttpAbstractParamBean;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class ConnManagerParamBean extends HttpAbstractParamBean {
    public ConnManagerParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setConnectionsPerRoute(ConnPerRouteBean connPerRouteBean) {
        this.params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, connPerRouteBean);
    }

    public void setMaxTotalConnections(int i) {
        this.params.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, i);
    }

    public void setTimeout(long j) {
        this.params.setLongParameter(ConnManagerPNames.TIMEOUT, j);
    }
}
