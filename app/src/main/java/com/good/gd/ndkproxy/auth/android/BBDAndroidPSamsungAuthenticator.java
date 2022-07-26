package com.good.gd.ndkproxy.auth.android;

import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import com.good.gd.ndkproxy.GDLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public class BBDAndroidPSamsungAuthenticator extends BBDAndroidPAuthenticator {
    private boolean isSemTypeFingerprint() {
        int i;
        BiometricPrompt biometricPrompt = getBiometricPrompt();
        if (biometricPrompt == null) {
            return true;
        }
        try {
            Field declaredField = BiometricPrompt.class.getDeclaredField("SEM_TYPE_FINGERPRINT");
            declaredField.setAccessible(true);
            Integer num = (Integer) declaredField.get(biometricPrompt);
            if (num == null) {
                i = -1;
            } else {
                GDLog.DBGPRINTF(16, "SamsungBiometric::sem_type_fingerprint = " + num + "\n");
                i = num.intValue();
            }
            try {
                Method declaredMethod = BiometricPrompt.class.getDeclaredMethod("semGetBiometricType", new Class[0]);
                declaredMethod.setAccessible(true);
                Integer num2 = (Integer) declaredMethod.invoke(biometricPrompt, new Object[0]);
                if (num2 == null) {
                    return true;
                }
                GDLog.DBGPRINTF(16, "SamsungBiometric::semGetBiometricType() = " + num2 + "\n");
                return num2.intValue() == i;
            } catch (Error | Exception e) {
                GDLog.DBGPRINTF(13, "SamsungBiometric::sem_type_fingerprint not able to get method\n");
                return true;
            }
        } catch (Error | Exception e2) {
            GDLog.DBGPRINTF(13, "SamsungBiometric::sem_type_fingerprint not able to get field\n");
            return true;
        }
    }

    @Override // com.good.gd.ndkproxy.auth.android.BBDAndroidPAuthenticator, com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator
    public void authenticate(Cipher cipher, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback) {
        if (isSemTypeFingerprint()) {
            super.authenticate(cipher, cancellationSignal, authenticationCallback);
        } else {
            authenticationCallback.onAuthenticationError(-1, "Selected biometric option is not supported");
        }
    }
}
