package com.good.gd.ndkproxy.ui.event;

import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;

/* loaded from: classes.dex */
public class BBDUIObjectMsg {
    private Object mData;
    private BBDUIUpdateEvent mEvent;
    private BBDUIObject mTarget;
    private BBDUIMessageType mType;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BBDUIObjectMsg(BBDUIObject bBDUIObject, BBDUIMessageType bBDUIMessageType, Object obj) {
        this.mTarget = bBDUIObject;
        this.mType = bBDUIMessageType;
        this.mData = obj;
    }

    public Object getData() {
        return this.mData;
    }

    public BBDUIObject getTarget() {
        return this.mTarget;
    }

    public BBDUIMessageType getType() {
        return this.mType;
    }

    public BBDUIUpdateEvent getUpdateEvent() {
        return this.mEvent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BBDUIObjectMsg(BBDUIObject bBDUIObject, BBDUIUpdateEvent bBDUIUpdateEvent) {
        this.mTarget = bBDUIObject;
        this.mEvent = bBDUIUpdateEvent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BBDUIObjectMsg(BBDUIUpdateEvent bBDUIUpdateEvent) {
        this.mEvent = bBDUIUpdateEvent;
    }
}
