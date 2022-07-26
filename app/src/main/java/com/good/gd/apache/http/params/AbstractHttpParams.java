package com.good.gd.apache.http.params;

/* loaded from: classes.dex */
public abstract class AbstractHttpParams implements HttpParams {
    @Override // com.good.gd.apache.http.params.HttpParams
    public boolean getBooleanParameter(String str, boolean z) {
        Object parameter = getParameter(str);
        return parameter == null ? z : ((Boolean) parameter).booleanValue();
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public double getDoubleParameter(String str, double d) {
        Object parameter = getParameter(str);
        return parameter == null ? d : ((Double) parameter).doubleValue();
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public int getIntParameter(String str, int i) {
        Object parameter = getParameter(str);
        return parameter == null ? i : ((Integer) parameter).intValue();
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public long getLongParameter(String str, long j) {
        Object parameter = getParameter(str);
        return parameter == null ? j : ((Long) parameter).longValue();
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public boolean isParameterFalse(String str) {
        return !getBooleanParameter(str, false);
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public boolean isParameterTrue(String str) {
        return getBooleanParameter(str, false);
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams setBooleanParameter(String str, boolean z) {
        setParameter(str, z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams setDoubleParameter(String str, double d) {
        setParameter(str, new Double(d));
        return this;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams setIntParameter(String str, int i) {
        setParameter(str, new Integer(i));
        return this;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams setLongParameter(String str, long j) {
        setParameter(str, new Long(j));
        return this;
    }
}
