package com.good.gd.ndkproxy.sub;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public final class GDSubContainerEvent {
    private static final int POLICY_MESSAGE_LOCKOUT_USER = 13;
    private static final int POLICY_MESSAGE_SECURITY_MESSAGE = 4;
    private static final int POLICY_MESSAGE_WIPE = 14;
    private String mAddress;
    private String mPolicyMessage;
    private GDSubContainerEventType mType;

    /* loaded from: classes.dex */
    enum GDSubContainerEventType {
        SECURITY_POLICY,
        WIPE,
        LOCKOUT_USER
    }

    public GDSubContainerEvent(String str, String str2, int i) {
        GDLog.DBGPRINTF(16, "GDSubContainerEvent::GDSubContainerEvent() ++ type = " + i + " address = " + str2 + " policyMessage = " + str + " \n");
        this.mAddress = str2;
        this.mPolicyMessage = str;
        if (i == 4) {
            this.mType = GDSubContainerEventType.SECURITY_POLICY;
        } else if (i == 13) {
            this.mType = GDSubContainerEventType.LOCKOUT_USER;
        } else if (i == 14) {
            this.mType = GDSubContainerEventType.WIPE;
        }
        GDLog.DBGPRINTF(16, "GDSubContainerEvent::GDSubContainerEvent() -- this.type = " + this.mType + " \n");
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getPolicyMessage() {
        return this.mPolicyMessage;
    }

    public GDSubContainerEventType getType() {
        return this.mType;
    }
}
