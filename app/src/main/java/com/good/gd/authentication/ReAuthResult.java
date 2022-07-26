package com.good.gd.authentication;

import android.util.SparseArray;
import java.util.EnumSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public enum ReAuthResult {
    Success(1),
    ErrorFailedAuth(2),
    ErrorUserCancelled(3),
    ErrorExpired(4),
    ErrorInProgress(5),
    ErrorNotSupported(6),
    ErrorInvalidRequest(7),
    ErrorUnknown(8);
    
    private static final SparseArray<ReAuthResult> intCodeToEnum = new SparseArray<>();
    private int _code;

    static {
        Iterator it = EnumSet.allOf(ReAuthResult.class).iterator();
        while (it.hasNext()) {
            ReAuthResult reAuthResult = (ReAuthResult) it.next();
            intCodeToEnum.put(reAuthResult.getCode(), reAuthResult);
        }
    }

    ReAuthResult(int i) {
        this._code = i;
    }

    public static ReAuthResult get(int i) {
        return intCodeToEnum.get(i);
    }

    public int getCode() {
        return this._code;
    }
}
