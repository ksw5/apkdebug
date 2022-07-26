package com.good.gd.ndkproxy.icc;

import com.good.gd.ndkproxy.GDLog;
import com.good.gt.icc.ICCController;

/* loaded from: classes.dex */
public final class IccControllerJniEntry {
    private final ICCController _ICCController;

    public IccControllerJniEntry(ICCController iCCController) {
        this._ICCController = iCCController;
        ndkInit();
    }

    private boolean canSendRequest(String str) {
        GDLog.DBGPRINTF(16, "JniEntryProvider.canSendRequest() IN\n");
        boolean canSendRequest = this._ICCController.canSendRequest(str);
        GDLog.DBGPRINTF(16, "IccControllerJniEntry.canSendRequest() OUT: " + canSendRequest + "\n");
        return canSendRequest;
    }

    public static native boolean checkReauthInProgress();

    private native void ndkInit();

    private void processPendingRequests() {
        this._ICCController.processPendingRequests();
    }

    private void registerCurrentAuthDelegate(String str) {
        this._ICCController.registerCurrentAuthDelegate(str);
    }

    private void shutdownICC() {
        ICCController iCCController = this._ICCController;
        if (iCCController != null) {
            iCCController.shutDownICC();
        }
    }
}
