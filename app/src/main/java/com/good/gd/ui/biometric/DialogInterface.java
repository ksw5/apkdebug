package com.good.gd.ui.biometric;

import android.app.Activity;
import android.content.Context;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;

/* loaded from: classes.dex */
public interface DialogInterface {
    void deregister();

    void dismissDialog();

    void dismissFingerprintDialog();

    void init(Activity activity, DialogArgs dialogArgs);

    boolean isDialogShowing();

    void onAuthenticationSuccess();

    void registerForActivationCallback(GDFingerprintActivationAuthenticator.Callback callback);

    void registerForUnlockCallback(GDFingerprintUnlockAuthenticator.Callback callback);

    void setAuthenticator(GDFingerprintAuthenticator gDFingerprintAuthenticator);

    void setUiData(BBDUIObject bBDUIObject);

    boolean showDialog(Context context, DialogArgs dialogArgs);

    boolean showFingerprintDialog();
}
