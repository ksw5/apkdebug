package com.good.gd.containerstate.ndkproxy;

import com.good.gd.utils.GDNDKLibraryLoader;

/* loaded from: classes.dex */
public class GDActivitySupport {
    public static final int CONTAINER_STATE_LOCKED = 0;
    public static final int CONTAINER_STATE_UNLOCKED = 1;
    public static final int CONTAINER_STATE_WIPED = 2;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    public static native int getContainerState();

    public static native boolean isActivated();

    public static boolean isAuthDelegating() {
        return isDelegatingAuth();
    }

    public static boolean isAuthorised() {
        return getContainerState() == 1;
    }

    public static native boolean isBackgrondUnlocked();

    public static boolean isContainerBackgroundUnlocked() {
        return isBackgrondUnlocked();
    }

    public static native boolean isContainerCurrentlyLocked();

    public static native boolean isCurrentlyBlockedByPolicy();

    public static native boolean isCurrentlyBlockedByPolicyOrLocally();

    public static native boolean isDelegatingAuth();

    public static native boolean isLocked();

    public static native boolean isProvisioned();

    public static native boolean isRemoteLocked();

    public static native boolean isStartupSuccessful();

    public static native boolean isUnlockingTUP2();

    public static native boolean isUserAuthRequired();

    public static boolean isWiped() {
        return getContainerState() == 2;
    }
}
