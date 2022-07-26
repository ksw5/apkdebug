package com.good.gd.utils;

/* loaded from: classes.dex */
public class GDDebuggableChecker implements DebuggableChecker {
    @Override // com.good.gd.utils.DebuggableChecker
    public boolean isApplicationDebuggable() {
        return GDActivityUtils.isApplicationDebuggable();
    }
}
