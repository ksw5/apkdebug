package com.good.gd.ndkproxy.auth.android;

import android.os.CancellationSignal;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public interface BBDAndroidAuthenticator {
    void authenticate(Cipher cipher, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback);
}
