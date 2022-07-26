package com.good.gd.support;

/* loaded from: classes.dex */
public class GDConnectedApplication {
    private String mAddress;
    private String mDisplayName;
    private GDConnectedApplicationState mState;
    private GDConnectedApplicationType mType;

    public String getAddress() {
        return this.mAddress;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public GDConnectedApplicationState getState() {
        return this.mState;
    }

    public GDConnectedApplicationType getType() {
        return this.mType;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public void setDisplayName(String str) {
        this.mDisplayName = str;
    }

    public void setState(GDConnectedApplicationState gDConnectedApplicationState) {
        this.mState = gDConnectedApplicationState;
    }

    public void setType(GDConnectedApplicationType gDConnectedApplicationType) {
        this.mType = gDConnectedApplicationType;
    }
}
