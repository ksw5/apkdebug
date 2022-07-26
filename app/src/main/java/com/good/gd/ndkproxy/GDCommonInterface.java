package com.good.gd.ndkproxy;

import com.good.gd.security.malware.RootedRulesInterface;
import java.util.Map;

/* loaded from: classes.dex */
public class GDCommonInterface {
    RootedRulesInterface mInterface;

    public GDCommonInterface(RootedRulesInterface rootedRulesInterface) {
        this.mInterface = rootedRulesInterface;
        ndkInit();
    }

    private String method_A(long j) {
        return this.mInterface.checkPlatformIntegrity(j);
    }

    private String method_B(Map<String, Map<String, String>> map, long j) {
        return this.mInterface.checkSystemPropertiesForCompliance(map, j);
    }

    private String method_C(byte[][] bArr, byte[][] bArr2, long j) {
        return this.mInterface.checkApplicationsForMalware(bArr, bArr2, j);
    }

    private void method_D(int i, int i2, int i3, int i4) {
        this.mInterface.setupOffsets(i, i2, i3, i4);
    }

    private String method_E(byte[][] bArr, long j) {
        return this.mInterface.checkStat(bArr, j);
    }

    private String method_F(long j) {
        return this.mInterface.enforceAntiDebugging(j);
    }

    private String method_G(double d, double d2, long j) {
        return this.mInterface.checkEntropyAndImage(d, d2, j);
    }

    native void jdNative();

    native void ndkInit();
}
