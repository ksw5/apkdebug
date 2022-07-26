package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public interface ClientConnectionManagerFactory {
    ClientConnectionManager newInstance(HttpParams httpParams, SchemeRegistry schemeRegistry);
}
