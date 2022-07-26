package com.good.gd.utils;

import com.good.gd.error.GDError;
import com.good.gd.error.GDInitializationError;
import com.good.gd.error.GDMissingDependancyError;
import com.good.gd.error.GDNotAuthorizedError;

/* loaded from: classes.dex */
public class ErrorUtils {
    public static final String CONTAINER_WIPED_MSG = "container has been wiped";

    public static void throwGDErrorForProgrammingError(String str) throws GDError {
        throw new GDError("SDK Programming Error: " + str);
    }

    public static void throwGDInitializationError(String str) throws GDInitializationError {
        throw new GDInitializationError(str);
    }

    public static void throwGDInterDeviceIncompatibleVersionError() throws GDError {
        throw new GDError("Error: The GD Android Wearable Framework App (built for Android Wear device) & GD Android SDK App must be based on the same version of Good Dynamics,There is currently a mismatch of library versionsEnsure that both applications are built on libraries from the same SDK release");
    }

    public static void throwGDMissingGooglePlayServicesError() throws GDMissingDependancyError {
        throw new GDMissingDependancyError("GooglePlayServices library is a required dependancy to be able to useSubContainer APIs, this app does not include GooglePlayServices. Include the library as part of your appproject" + Thread.currentThread().getStackTrace().toString());
    }

    public static void throwGDNotAuthorizedError() throws GDNotAuthorizedError {
        throw new GDNotAuthorizedError("Error GD Not authorized. The GD Authorization process is started by calling GDAndroid.activityInit() orGDAndroid.authorize(). Ensure authorization process has completed successfully (Your GDAppEventListener orGDStateListener instance will be notified) before calling other GD APIs. The stack Trace which caused this Error is " + Thread.currentThread().getStackTrace().toString());
    }
}
