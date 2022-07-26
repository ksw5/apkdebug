package com.good.gd;

import java.util.Map;

/* loaded from: classes.dex */
public abstract class GDTrust {
    public static final int GDTrustErrGeneral = -4;
    public static final int GDTrustPasswordDoesNotMatch = -1;
    public static final int GDTrustRemoteLocked = -2;
    public static final int GDTrustSuccess = 0;
    public static final int GDTrustWiped = -3;

    public abstract int changePassword(byte[] bArr, byte[] bArr2);

    public abstract int performTrustAction(GDTrustAction gDTrustAction);

    public abstract Map securityPolicy();

    public abstract int unlockWithPassword(byte[] bArr);
}
