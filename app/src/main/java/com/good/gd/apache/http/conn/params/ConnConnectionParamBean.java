package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.params.HttpAbstractParamBean;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class ConnConnectionParamBean extends HttpAbstractParamBean {
    public ConnConnectionParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setMaxStatusLineGarbage(int i) {
        this.params.setIntParameter(ConnConnectionPNames.MAX_STATUS_LINE_GARBAGE, i);
    }
}
