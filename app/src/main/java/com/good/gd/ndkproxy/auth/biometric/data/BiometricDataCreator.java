package com.good.gd.ndkproxy.auth.biometric.data;

import android.os.Build;
import com.good.gd.ndkproxy.auth.biometric.data.manager.BiometricManagerAndroidQ;
import com.good.gd.ndkproxy.auth.biometric.data.manager.BiometricManagerAndroidR;

/* loaded from: classes.dex */
public class BiometricDataCreator {
    private static BiometricDataCreator instance;
    private BiometricDataProvider biometricDataProvider;

    private BiometricDataCreator() {
    }

    private BiometricDataProvider createDataProvider() throws Exception {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            return new BiometricManagerAndroidR();
        }
        if (i >= 29) {
            return new BiometricManagerAndroidQ();
        }
        return new FingerprintManagerBasedProvider();
    }

    public static BiometricDataCreator getInstance() {
        if (instance == null) {
            instance = new BiometricDataCreator();
        }
        return instance;
    }

    public BiometricDataProvider getBiometricDataProvider() throws Exception {
        if (this.biometricDataProvider == null) {
            this.biometricDataProvider = createDataProvider();
        }
        return this.biometricDataProvider;
    }
}
