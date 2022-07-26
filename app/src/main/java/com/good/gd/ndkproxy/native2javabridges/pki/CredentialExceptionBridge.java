package com.good.gd.ndkproxy.native2javabridges.pki;

import com.good.gd.pki.CredentialException;

/* loaded from: classes.dex */
final class CredentialExceptionBridge extends CredentialException {
    public CredentialExceptionBridge(Code code) {
        super(code);
    }

    private static String getCodeClassName() {
        return Code.class.getName().replace(".", "/");
    }
}
