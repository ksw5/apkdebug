package com.good.gd.net;

/* loaded from: classes.dex */
public class GDNetworkInfo {
    private final boolean mIsAvailable;
    private final boolean mIsBlocked;
    private final boolean mIsCaptivePortal;
    private final boolean mIsConnected;
    private final boolean mIsPushChannelAvailable;
    private final boolean mIsUnknownOutage;
    private final int mNetworkType;
    private final String mTypeName;

    public GDNetworkInfo(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, String str) {
        this.mIsConnected = z;
        this.mIsAvailable = z2;
        this.mIsPushChannelAvailable = z3;
        this.mIsBlocked = z4;
        this.mIsCaptivePortal = z5;
        this.mIsUnknownOutage = z6;
        this.mNetworkType = i;
        this.mTypeName = str;
    }

    public int getType() {
        return this.mNetworkType;
    }

    public String getTypeName() {
        return this.mTypeName;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    public boolean isBlocked() {
        return this.mIsBlocked;
    }

    public boolean isCaptivePortal() {
        return this.mIsCaptivePortal;
    }

    public boolean isConnected() {
        return this.mIsConnected;
    }

    public boolean isPushChannelAvailable() {
        return this.mIsPushChannelAvailable;
    }

    public boolean isUnknownOutage() {
        return this.mIsUnknownOutage;
    }
}
