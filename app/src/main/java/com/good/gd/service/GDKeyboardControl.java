package com.good.gd.service;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDDLPControl;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDKeyboardControl {
    private static GDKeyboardControl _instance;

    private GDKeyboardControl() {
    }

    private boolean checkIsSystemKeyboard() {
        if (GDDLPControl.getInstance().getScreenCaptureControl().isScreenCaptureDisabled()) {
            String string = Settings.Secure.getString(GDContext.getInstance().getApplicationContext().getContentResolver(), "default_input_method");
            if (string != null && !string.isEmpty()) {
                String str = string.split("/")[0];
                if (str != null && !str.isEmpty()) {
                    try {
                        ApplicationInfo applicationInfo = GDContext.getInstance().getApplicationContext().getPackageManager().getApplicationInfo(str, 0);
                        if (applicationInfo != null) {
                            return (applicationInfo.flags & 129) != 0;
                        }
                        GDLog.DBGPRINTF(16, "GDKeyboardControl: Keyboard not found. Can happen in AfW Managed Profile");
                        return false;
                    } catch (PackageManager.NameNotFoundException e) {
                        GDLog.DBGPRINTF(16, "GDKeyboardControl: Keyboard not found. Can happen in AfW Managed Profile");
                        return false;
                    }
                }
                GDLog.DBGPRINTF(16, "GDKeyboardControl: No Package Name. Should never happen");
                return false;
            }
            GDLog.DBGPRINTF(16, "GDKeyboardControl: Keyboard not set. Should never happen");
            return false;
        }
        return true;
    }

    public static GDKeyboardControl getInstance() {
        GDKeyboardControl gDKeyboardControl = _instance;
        if (gDKeyboardControl != null) {
            return gDKeyboardControl;
        }
        throw new RuntimeException("GDKeyboardControl not initialized");
    }

    public static void initializeInstance() throws Exception {
        GDLog.DBGPRINTF(16, "GDKeyboardControl: initializeInstance\n");
        _instance = new GDKeyboardControl();
    }

    public void checkSystemKeybord() {
        if (!checkIsSystemKeyboard()) {
            GDLog.DBGPRINTF(16, "GDKeyboardControl: Default Keyboard is non system keyboard");
        }
    }

    public void defaultSystemKeyboardChanged() {
        if (!checkIsSystemKeyboard()) {
            GDLog.DBGPRINTF(16, "GDKeyboardControl: Default Keyboard has been changed to non system keyboard");
        } else {
            GDLog.DBGPRINTF(16, "GDKeyboardControl: Default Keyboard has been changed to a system keyboard");
        }
    }
}
