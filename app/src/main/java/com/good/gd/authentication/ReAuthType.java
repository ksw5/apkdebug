package com.good.gd.authentication;

import android.util.SparseArray;
import java.util.EnumSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public enum ReAuthType {
    None(0),
    NoPassword(1),
    Password(2),
    Biometric(3),
    GracePeriod(4),
    TrustedAuthenticator(5);
    
    private static final SparseArray<ReAuthType> intCodeToEnum = new SparseArray<>();
    private int _code;

    static {
        Iterator it = EnumSet.allOf(ReAuthType.class).iterator();
        while (it.hasNext()) {
            ReAuthType reAuthType = (ReAuthType) it.next();
            intCodeToEnum.put(reAuthType.getCode(), reAuthType);
        }
    }

    ReAuthType(int i) {
        this._code = i;
    }

    public static ReAuthType get(int i) {
        return intCodeToEnum.get(i);
    }

    public int getCode() {
        return this._code;
    }
}
