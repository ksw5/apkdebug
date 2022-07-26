package com.good.gd.ndkproxy.native2javabridges.pki;

import com.good.gd.pki.CredentialsProfile;

/* loaded from: classes.dex */
final class CredentialsProfileStateBridge {
    CredentialsProfileStateBridge() {
    }

    private static String getStateClassString() {
        return CredentialsProfile.State.class.getName().replace(".", "/");
    }
}
