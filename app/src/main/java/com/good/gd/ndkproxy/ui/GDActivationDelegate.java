package com.good.gd.ndkproxy.ui;

import com.good.gd.messages.ActivationMsg;

/* loaded from: classes.dex */
public final class GDActivationDelegate {
    public static void handleClientActivationRequest(ActivationMsg activationMsg) {
        handleClientActivationRequest(activationMsg._delegateAppId, activationMsg._nonce);
    }

    private static native void handleClientActivationRequest(String str, String str2);
}
