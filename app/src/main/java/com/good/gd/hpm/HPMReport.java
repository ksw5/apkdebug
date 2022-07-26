package com.good.gd.hpm;

import com.good.gd.ndkproxy.hpm.HPMReportImpl;

/* loaded from: classes.dex */
public final class HPMReport {
    static HPMReport _instance;
    HPMReportImpl _impl = new HPMReportImpl();

    private HPMReport() {
    }

    public static synchronized HPMReport getInstance() {
        HPMReport hPMReport;
        synchronized (HPMReport.class) {
            if (_instance == null) {
                _instance = new HPMReport();
            }
            hPMReport = _instance;
        }
        return hPMReport;
    }

    public void connectionErrorReport(String str, int i, String str2, boolean z, String str3, String str4) {
        if (!str2.contains("Cmd=Ping")) {
            this._impl.httpReport(str != null ? str : "", i, str2, 0L, true, 0, "", z, str3, str4 != null ? str4 : "");
        }
    }

    public void httpReport(String str, int i, String str2, long j, boolean z, int i2, String str3, boolean z2, String str4, String str5) {
        if (!str2.contains("Cmd=Ping")) {
            this._impl.httpReport(str != null ? str : "", i, str2, j, z, i2, str3 != null ? str3 : "", z2, str4, str5 != null ? str5 : "");
        }
    }
}
