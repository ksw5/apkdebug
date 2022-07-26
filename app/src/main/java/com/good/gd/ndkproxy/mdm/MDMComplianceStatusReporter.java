package com.good.gd.ndkproxy.mdm;

import com.good.gt.MDMProvider.ComplianceStatus;
import com.good.gt.MDMProvider.MDMProviderControl;

/* loaded from: classes.dex */
public class MDMComplianceStatusReporter {
    private static MDMComplianceStatusReporter _instance;

    private MDMComplianceStatusReporter() {
        ndkInit();
    }

    public static synchronized MDMComplianceStatusReporter getInstance() {
        MDMComplianceStatusReporter mDMComplianceStatusReporter;
        synchronized (MDMComplianceStatusReporter.class) {
            if (_instance == null) {
                _instance = new MDMComplianceStatusReporter();
            }
            mDMComplianceStatusReporter = _instance;
        }
        return mDMComplianceStatusReporter;
    }

    private native void ndkInit();

    protected void complianceStatusUpdate(String str, boolean z, String str2) {
        MDMProviderControl.getInstance().notifyComplianceChange(new ComplianceStatus(str, z, str2));
    }

    public void init() {
    }
}
