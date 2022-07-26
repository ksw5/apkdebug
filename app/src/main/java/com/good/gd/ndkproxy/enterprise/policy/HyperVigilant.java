package com.good.gd.ndkproxy.enterprise.policy;

/* loaded from: classes.dex */
public final class HyperVigilant {
    public static final int ENHANCED = 2;
    public static final int STANDARD = 1;

    public static native void disengageHyperVigilantMode();

    public static native void engageHyperVigilantMode(int i);
}
