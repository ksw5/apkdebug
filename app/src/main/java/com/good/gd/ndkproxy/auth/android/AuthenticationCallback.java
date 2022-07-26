package com.good.gd.ndkproxy.auth.android;

import javax.crypto.Cipher;

/* loaded from: classes.dex */
public interface AuthenticationCallback {
    void onAuthenticationError(int i, CharSequence charSequence);

    void onAuthenticationFailed();

    void onAuthenticationHelp(int i, CharSequence charSequence);

    void onAuthenticationSucceeded(Cipher cipher);
}
