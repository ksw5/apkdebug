package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.conn.routing.HttpRoute;

/* loaded from: classes.dex */
public interface ConnPerRoute {
    int getMaxForRoute(HttpRoute httpRoute);
}
