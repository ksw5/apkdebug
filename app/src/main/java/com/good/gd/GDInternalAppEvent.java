package com.good.gd;

/* loaded from: classes.dex */
public final class GDInternalAppEvent {
    private final GDAppResultCode _code;
    private final String _message;
    private final GDInternalAppEventType _type;

    public GDInternalAppEvent(String str, GDAppResultCode gDAppResultCode, GDInternalAppEventType gDInternalAppEventType) {
        this._message = str;
        this._code = gDAppResultCode;
        this._type = gDInternalAppEventType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GDInternalAppEvent)) {
            return false;
        }
        GDInternalAppEvent gDInternalAppEvent = (GDInternalAppEvent) obj;
        if (this._code != gDInternalAppEvent._code) {
            return false;
        }
        String str = this._message;
        if (str == null) {
            if (gDInternalAppEvent._message != null) {
                return false;
            }
        } else if (!str.equals(gDInternalAppEvent._message)) {
            return false;
        }
        return this._type == gDInternalAppEvent._type;
    }

    public final GDInternalAppEventType getEventType() {
        return this._type;
    }

    public final String getMessage() {
        return this._message;
    }

    public final GDAppResultCode getResultCode() {
        return this._code;
    }

    public int hashCode() {
        GDAppResultCode gDAppResultCode = this._code;
        int i = 0;
        int hashCode = ((gDAppResultCode == null ? 0 : gDAppResultCode.hashCode()) + 31) * 31;
        String str = this._message;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        GDInternalAppEventType gDInternalAppEventType = this._type;
        if (gDInternalAppEventType != null) {
            i = gDInternalAppEventType.hashCode();
        }
        return hashCode2 + i;
    }

    public String toString() {
        return "GDInternalAppEvent:type=" + this._type + ":code=" + this._code + ":\"" + this._message + "\"";
    }
}
