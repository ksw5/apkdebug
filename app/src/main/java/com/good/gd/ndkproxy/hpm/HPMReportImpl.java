package com.good.gd.ndkproxy.hpm;

/* loaded from: classes.dex */
public final class HPMReportImpl {
    private native void nativeHttpReport(String str, int i, String str2, long j, boolean z, int i2, String str3, boolean z2, String str4, String str5);

    public void httpReport(String str, int i, String str2, long j, boolean z, int i2, String str3, boolean z2, String str4, String str5) {
        nativeHttpReport(str, i, str2, j, z, i2, str3, z2, str4, str5);
    }
}
