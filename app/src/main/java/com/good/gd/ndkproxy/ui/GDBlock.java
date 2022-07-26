package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.mdm.MDMChecker;
import com.good.gd.ndkproxy.ui.data.BlockFacade;

/* loaded from: classes.dex */
public final class GDBlock implements BlockFacade {
    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public native String getAgentInfo();

    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public native String getAgentMinVersion();

    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public native int getConnComplianceTimebomb();

    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public String getEnrollmentFailureReason() {
        return MDMChecker.getMdmEnrollmentErrorReason();
    }

    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public native int getMaxPwdAttempts();

    public native boolean isMDMBlockActive();

    @Override // com.good.gd.ndkproxy.ui.data.BlockFacade
    public native void remoteUnlock(long j);
}
