package com.good.gd;

import android.util.SparseArray;
import com.good.gd.ndkproxy.GDAppResultCodeNDK;
import java.util.EnumSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public enum GDAppResultCode {
    GDErrorNone(0),
    GDErrorActivationFailed(GDAppResultCodeNDK.GDErrorActivationFailed),
    GDErrorProvisioningFailed(GDAppResultCodeNDK.GDErrorProvisioningFailed),
    GDErrorPushConnectionTimeout(GDAppResultCodeNDK.GDErrorPushConnectionTimeout),
    GDErrorAppDenied(GDAppResultCodeNDK.GDErrorAppDenied),
    GDErrorAppVersionNotEntitled(GDAppResultCodeNDK.GDErrorAppVersionNotEntitled),
    GDErrorIdleLockout(GDAppResultCodeNDK.GDErrorIdleLockout),
    GDErrorBlocked(GDAppResultCodeNDK.GDErrorBlocked),
    GDErrorWiped(GDAppResultCodeNDK.GDErrorWiped),
    GDErrorRemoteLockout(GDAppResultCodeNDK.GDErrorRemoteLockout),
    GDErrorPasswordChangeRequired(GDAppResultCodeNDK.GDErrorPasswordChangeRequired),
    GDErrorSecurityError(-100),
    GDErrorProgrammaticActivationNoNetwork(GDAppResultCodeNDK.GDErrorProgrammaticActivationNoNetwork),
    GDErrorProgrammaticActivationCredentialsFailed(GDAppResultCodeNDK.GDErrorProgrammaticActivationCredentialsFailed),
    GDErrorProgrammaticActivationServerCommsFailed(GDAppResultCodeNDK.GDErrorProgrammaticActivationServerCommsFailed),
    GDErrorProgrammaticActivationUnknown(GDAppResultCodeNDK.GDErrorProgrammaticActivationUnknown),
    GDErrorProgrammaticActivationAttestationFailed(GDAppResultCodeNDK.GDErrorProgrammaticActivationAttestationFailed);
    
    private static final SparseArray<GDAppResultCode> intCodeToEnum = new SparseArray<>();
    private int _code;

    static {
        Iterator it = EnumSet.allOf(GDAppResultCode.class).iterator();
        while (it.hasNext()) {
            GDAppResultCode gDAppResultCode = (GDAppResultCode) it.next();
            intCodeToEnum.put(gDAppResultCode.getCode(), gDAppResultCode);
        }
    }

    GDAppResultCode(int i) {
        this._code = i;
    }

    public static GDAppResultCode get(int i) {
        return intCodeToEnum.get(i);
    }

    public int getCode() {
        return this._code;
    }
}
