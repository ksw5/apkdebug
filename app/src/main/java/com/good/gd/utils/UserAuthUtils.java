package com.good.gd.utils;

import android.content.Context;
import com.good.gd.R;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;

/* loaded from: classes.dex */
public class UserAuthUtils {
    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    public static boolean canAuthorizeAutonomously(Context context) {
        GDLog.DBGPRINTF(16, "GDUserAuthUtils::canAuthorizeAutonomously\n");
        boolean z = false;
        if (!prepareStartup(context)) {
            return false;
        }
        if (!GDAuthManager.getInstance().isAuthTypeNoPass() && !GDAuthManager.getInstance().isBackgroundAuthAllowed()) {
            z = true;
        }
        boolean z2 = !z;
        GDLog.DBGPRINTF(16, "GDUserAuthUtils::canAuthorizeAutonomously " + z2 + "\n");
        return z2;
    }

    public static boolean isActivated() {
        GDLog.DBGPRINTF(16, "GDUserAuthUtils::isActivated\n");
        if (!prepareStartup(GDContext.getInstance().getApplicationContext())) {
            return false;
        }
        boolean isActivated = GDActivitySupport.isActivated();
        GDLog.DBGPRINTF(16, "GDUserAuthUtils::isActivated" + isActivated + "\n");
        return isActivated;
    }

    public static boolean isAuthorised() {
        boolean isAuthorised = GDActivitySupport.isAuthorised();
        GDLog.DBGPRINTF(16, "GDUserAuthUtils::isAuthorised() -- authed =" + isAuthorised + "\n");
        return isAuthorised;
    }

    public static boolean isUserAuthRequired() {
        GDInit.checkInitialized();
        boolean isUserAuthRequired = GDActivitySupport.isUserAuthRequired();
        GDLog.DBGPRINTF(14, "GDUserAuthUtils::isUserAuthRequired() -- auth required =" + isUserAuthRequired + "\n");
        return isUserAuthRequired;
    }

    public static boolean isWiped() {
        return GDActivitySupport.isWiped();
    }

    private static boolean prepareStartup(Context context) {
        if (!GDDeviceInfo.getInstance().isInitialized()) {
            GDContext.getInstance().setContext(context);
            try {
                GDTEEManager.createInstance().Init();
                GDSettings.initialize();
                GDLocalizer.createInstance(R.raw.class);
                GDDeviceInfo.getInstance().initialize();
                return true;
            } catch (Exception e) {
                GDLog.DBGPRINTF(12, "UserAuthUtils.isActivated()", e);
                return false;
            }
        }
        return true;
    }
}
