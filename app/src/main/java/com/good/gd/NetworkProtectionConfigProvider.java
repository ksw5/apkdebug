package com.good.gd;

/* loaded from: classes.dex */
public interface NetworkProtectionConfigProvider {
    String getConnectivityActionIntentAction();

    boolean isCaptivePortal();

    boolean isUnknownOutage();
}
