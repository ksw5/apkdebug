package com.good.gd.utils;

/* loaded from: classes.dex */
public class GDEnterpriseModeChecker implements EnterpriseModeChecker {
    @Override // com.good.gd.utils.EnterpriseModeChecker
    public boolean enterpriseSimulationModeEnabled() {
        return GDInit.enterpriseSimulationModeEnabled();
    }

    public boolean isEnterpriseModeEnabled() {
        return GDInit.isEnterpriseModeEnabled();
    }
}
