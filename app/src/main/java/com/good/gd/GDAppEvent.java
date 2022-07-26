package com.good.gd;

/* loaded from: classes.dex */
public final class GDAppEvent {
    private final GDAppResultCode _code;
    private final String _message;
    private final GDAppEventType _type;

    public GDAppEvent(String str, GDAppResultCode gDAppResultCode, GDAppEventType gDAppEventType) {
        this._message = str;
        this._code = gDAppResultCode;
        this._type = gDAppEventType;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GDAppEvent)) {
            return false;
        }
        GDAppEvent gDAppEvent = (GDAppEvent) obj;
        if (this._code != gDAppEvent._code) {
            return false;
        }
        String str = this._message;
        if (str == null) {
            if (gDAppEvent._message != null) {
                return false;
            }
        } else if (!str.equals(gDAppEvent._message)) {
            return false;
        }
        return this._type == gDAppEvent._type;
    }

    public final GDAppEventType getEventType() {
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
        GDAppEventType gDAppEventType = this._type;
        if (gDAppEventType != null) {
            i = gDAppEventType.hashCode();
        }
        return hashCode2 + i;
    }

    public String toString() {
        return "GDAppEvent:type=" + this._type + ":code=" + this._code + ":\"" + this._message + "\"";
    }
}
