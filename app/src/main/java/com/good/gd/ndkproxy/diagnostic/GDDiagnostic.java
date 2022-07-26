package com.good.gd.ndkproxy.diagnostic;

import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.context.GDContext;
import com.good.gd.diagnostic.GDDiagnosticReachabilityListener;
import com.good.gd.diagnostic.GDDiagnosticReachabilityResult;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDNDKLibraryLoader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class GDDiagnostic {
    private GDDiagnosticReachabilityListener mCommsResultListener = null;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    public GDDiagnostic() {
        ndkInit();
    }

    private native int _checkApplicationServerReachability(String str, String str2, boolean z, boolean z2, int i);

    private native int[] _checkManagementConsoleReachability(int i);

    private native byte[] getPolicyDiagnosticString();

    private synchronized String getPolicyString() {
        String str;
        try {
            str = new String(getPolicyDiagnosticString(), HTTP.UTF_8);
        } catch (Exception e) {
            str = null;
        }
        return str;
    }

    private native void ndkInit();

    public Integer checkApplicationServerReachability(String str, int i, boolean z, boolean z2, int i2) {
        return Integer.valueOf(_checkApplicationServerReachability(str, Integer.toString(i), z, z2, i2));
    }

    public List<Integer> checkManagementConsoleReachability(int i) {
        int[] _checkManagementConsoleReachability = _checkManagementConsoleReachability(i);
        ArrayList arrayList = new ArrayList();
        if (_checkManagementConsoleReachability != null) {
            for (int i2 : _checkManagementConsoleReachability) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return arrayList;
    }

    public String getCurrentSettings() {
        if (GDContext.getInstance().isWiped()) {
            return "";
        }
        GDContext.getInstance().checkAuthorized();
        return getPolicyString();
    }

    protected boolean handleMessage(Object obj) {
        if ((obj != null) && (this.mCommsResultListener != null)) {
            GDDiagnosticResult gDDiagnosticResult = (GDDiagnosticResult) obj;
            GDDiagnosticReachabilityResult gDDiagnosticReachabilityResult = new GDDiagnosticReachabilityResult(gDDiagnosticResult.getResult(), gDDiagnosticResult.getRequestID());
            GDLog.DBGPRINTF(16, "GDDiagnostics.handleMessage() requestID = " + gDDiagnosticReachabilityResult.getRequestID() + ", result string = " + gDDiagnosticReachabilityResult.getResult() + " \n");
            this.mCommsResultListener.onReachabilityResult(gDDiagnosticReachabilityResult);
            return true;
        }
        return false;
    }

    public void setReachabilityListener(GDDiagnosticReachabilityListener gDDiagnosticReachabilityListener) {
        this.mCommsResultListener = gDDiagnosticReachabilityListener;
    }
}
