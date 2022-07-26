package com.good.gd.ndkproxy.attestationApi;

import android.content.Context;
import android.content.pm.PackageManager;
import com.blackberry.attestation.AttestationBuilder;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLangInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.utility.UtilityAA;

/* loaded from: classes.dex */
public class AttestationHelper {
    public static final String ApiKey = GDLangInterface.lookup("IZjhKbOZOJlJsuMa179zKpPCCu5K66BG95F8BFUo7Q8mh4rHOQSKKg5mOEbMVAtO");
    private static final Object lockObject = new Object();
    private static boolean isAttestationObjectsCreated = false;

    protected static AttestationBuilder getAttestationBuilder(ClassLoader classLoader) {
        return GDAttestationAPI.getAttestationBuilder(classLoader);
    }

    public static void initialize() {
        synchronized (lockObject) {
            boolean isLibraryPresented = isLibraryPresented();
            boolean z = true;
            if (!isAttestationObjectsCreated) {
                GDLog.DBGPRINTF(14, "GAGAA CJO");
                if (isLibraryPresented) {
                    GDAttestationAPI.createPeriodicInstance();
                    GDAttestationAPI.createActivationInstance();
                }
                GDHWAttestationAPI.createInstance();
                GDHWActivationAttestation.createInstance();
                isAttestationObjectsCreated = true;
            }
            boolean manifestContainsSafetyNetAPIKey = manifestContainsSafetyNetAPIKey();
            if (!manifestContainsSafetyNetAPIKey || !isLibraryPresented) {
                z = false;
            }
            UtilityAA.UtilC(z);
            if (!manifestContainsSafetyNetAPIKey && isLibraryPresented) {
                GDLog.DBGPRINTF(13, GDLangInterface.lookup("VqLv8QC8XfjdUXInJs7gausKCDsXI/731ts1lVsCSzs5Xcf0YR0FzEls31npK6tYjz+K+fRYLx7IQUBs+ht2LA=="));
            } else if (manifestContainsSafetyNetAPIKey && !isLibraryPresented) {
                GDLog.DBGPRINTF(13, GDLangInterface.lookup("VqLv8QC8XfjdUXInJs7gakXNgrW8wo6DyGFpovfx4lhCXV900wjLH0eY9XdEB0EfnoDrWNQZQn56tWhVlFXNmsTj8ydItHdAqr54xYI1DQg="));
            } else if (!manifestContainsSafetyNetAPIKey && !isLibraryPresented) {
                GDLog.DBGPRINTF(14, GDLangInterface.lookup("yk6kpStFftmA6xNYHEz1c41PbCVB88iftsFvqIgllEWio5XaaZPBsHU+/NEsL+T3"));
            }
        }
    }

    public static boolean isLibraryPresented() {
        ClassLoader classLoader = IAttestationAPI.class.getClassLoader();
        return classLoader != null && isLibraryPresented(classLoader);
    }

    public static boolean manifestContainsSafetyNetAPIKey() {
        try {
            Context applicationContext = GDContext.getInstance().getApplicationContext();
            return !applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128).metaData.getString(ApiKey).isEmpty();
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            return false;
        }
    }

    protected static boolean isLibraryPresented(ClassLoader classLoader) {
        return getAttestationBuilder(classLoader) != null;
    }
}
