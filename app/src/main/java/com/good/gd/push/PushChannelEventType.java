package com.good.gd.push;

import android.util.SparseArray;
import java.util.EnumSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public enum PushChannelEventType {
    None(0),
    Open(1),
    Close(2),
    Error(3),
    Message(4),
    PingFail(5);
    
    private static final SparseArray<PushChannelEventType> intCodeToEnum = new SparseArray<>();
    private int _code;

    static {
        Iterator it = EnumSet.allOf(PushChannelEventType.class).iterator();
        while (it.hasNext()) {
            PushChannelEventType pushChannelEventType = (PushChannelEventType) it.next();
            intCodeToEnum.put(pushChannelEventType.getCode(), pushChannelEventType);
        }
    }

    PushChannelEventType(int i) {
        this._code = i;
    }

    public static PushChannelEventType get(int i) {
        return intCodeToEnum.get(i);
    }

    public int getCode() {
        return this._code;
    }
}
