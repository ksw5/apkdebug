package com.good.gd.utils;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDDisplayUtils {
    private static boolean checkOtherDisplaysSecure(Activity activity) {
        Display[] displays;
        DisplayManager displayManager = (DisplayManager) activity.getApplicationContext().getSystemService(DisplayManager.class);
        if (displayManager != null) {
            for (Display display : displayManager.getDisplays()) {
                printDisplayInfo(display);
                if (secureFlagMissing(display)) {
                    return false;
                }
            }
            return true;
        }
        GDLog.DBGPRINTF(12, "GDDisplayUtils: DisplayManager is null\n");
        return true;
    }

    private static boolean hasProtectedBuffersEnabled(Display display) {
        return (display.getFlags() & 1) != 0;
    }

    private static boolean isMainDisplay(Display display) {
        return display.getDisplayId() == 0;
    }

    public static boolean isSecureDisplayOutput(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        if (isMainDisplay(defaultDisplay)) {
            return true;
        }
        if (secureFlagMissing(defaultDisplay)) {
            GDLog.DBGPRINTF(16, "GDDisplayUtils: default display - non-secure\n");
            printDisplayInfo(defaultDisplay);
            return false;
        } else if (hasProtectedBuffersEnabled(defaultDisplay) || checkOtherDisplaysSecure(activity)) {
            return true;
        } else {
            GDLog.DBGPRINTF(16, "GDDisplayUtils: other display - non-secure\n");
            return false;
        }
    }

    private static void printDisplayInfo(Display display) {
        GDLog.DBGPRINTF(16, "GDDisplayUtils: " + display + "\n");
        GDLog.DBGPRINTF(16, "GDDisplayUtils: flags: " + display.getFlags() + "\n");
    }

    private static boolean secureFlagMissing(Display display) {
        return (display.getFlags() & 2) == 0;
    }
}
