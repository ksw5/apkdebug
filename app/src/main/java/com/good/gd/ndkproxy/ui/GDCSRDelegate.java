package com.good.gd.ndkproxy.ui;

import android.content.pm.PackageManager;
import com.good.gd.GDAndroid;
import com.good.gd.context.GDContext;
import com.good.gd.icc.GDServiceException;
import com.good.gd.icc.csr.CertificateSigningRequestConsumer;
import com.good.gd.messages.CSRMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;

/* loaded from: classes.dex */
public class GDCSRDelegate {
    private static GDCSRDelegate _instance;

    public static synchronized GDCSRDelegate getInstance() {
        GDCSRDelegate gDCSRDelegate;
        synchronized (GDCSRDelegate.class) {
            if (_instance == null) {
                _instance = new GDCSRDelegate();
            }
            gDCSRDelegate = _instance;
        }
        return gDCSRDelegate;
    }

    public void handleCertificateSigningRequest(CSRMsg cSRMsg) {
        try {
            String applicationId = GDAndroid.getInstance().getApplicationId();
            String appVersion = GDAndroid.getInstance().getAppVersion();
            PackageManager packageManager = GDContext.getInstance().getApplicationContext().getPackageManager();
            String str = (String) packageManager.getPackageInfo(GDContext.getInstance().getApplicationContext().getPackageName(), 0).applicationInfo.loadLabel(packageManager);
            try {
                GDLog.DBGPRINTF(16, "GDCSRDelegate.: calling sendCertificateSigningRequest: \n");
                CertificateSigningRequestConsumer.getInstance().sendCertificateSigningRequest(cSRMsg._delegateAppId, applicationId, str, appVersion, GDEActivationManager.getInstance().getAppAddress(), cSRMsg._csr, cSRMsg._upn, cSRMsg._token);
            } catch (GDServiceException e) {
                GDLog.DBGPRINTF(16, "GDCSRDelegate.: GDServiceException \n" + e.getMessage());
            }
        } catch (PackageManager.NameNotFoundException e2) {
            GDLog.DBGPRINTF(16, "GDCSRDelegate.: GDServiceException NameNotFoundException \n" + e2.getMessage());
        }
    }
}
