package com.good.gd.ndkproxy.ui.data;

/* loaded from: classes.dex */
public interface BlockFacade {
    String getAgentInfo();

    String getAgentMinVersion();

    int getConnComplianceTimebomb();

    String getEnrollmentFailureReason();

    int getMaxPwdAttempts();

    void remoteUnlock(long j);
}
