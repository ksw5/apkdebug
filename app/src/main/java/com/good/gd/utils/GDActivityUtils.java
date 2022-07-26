package com.good.gd.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import com.good.gd.ExpandableListActivity;
import com.good.gd.FragmentActivity;
import com.good.gd.GDIccReceivingActivity;
import com.good.gd.GDStateListener;
import com.good.gd.ListActivity;
import com.good.gd.PreferenceActivity;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui.GDInternalActivity;
import com.good.gt.ndkproxy.icc.IccActivity;

/* loaded from: classes.dex */
public class GDActivityUtils {
    public static boolean checkImplementsGDStateListener(Context context) {
        return context instanceof GDStateListener;
    }

    public static boolean checkIsGDActivity(Activity activity) {
        if ((activity instanceof com.good.gd.Activity) || (activity instanceof ExpandableListActivity) || (activity instanceof ListActivity) || (activity instanceof PreferenceActivity) || (activity instanceof IccActivity) || (activity instanceof GDIccReceivingActivity)) {
            return true;
        }
        try {
            return activity instanceof FragmentActivity;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public static boolean checkIsGDInternalActivity(Activity activity) {
        return activity instanceof GDInternalActivity;
    }

    public static void configureActivityScreenCapture(Activity activity, boolean z) {
        setScreenCaptureStatus(activity, z);
    }

    public static boolean hasFeaturePC(Context context) {
        if (Build.VERSION.SDK_INT >= 27) {
            return context.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        }
        return false;
    }

    public static boolean isApplicationDebuggable() {
        return (GDContext.getInstance().getApplicationContext().getApplicationInfo().flags & 2) != 0;
    }

    private static void setScreenCaptureStatus(Activity activity, boolean z) {
        Window window = activity.getWindow();
        GDLog.DBGPRINTF(16, "GDActivityUtils::setScreenCaptureStatus isDisabled =" + z + " Windows = " + window + "\n");
        if (window != null) {
            while (window.getContainer() != null) {
                window = window.getContainer();
            }
            if (z) {
                GDLog.DBGPRINTF(16, "GDActivityUtils::setScreenCaptureStatus isDisabled =" + z + " Windows = " + window + "\n");
                window.addFlags(8192);
                return;
            }
            window.clearFlags(8192);
        }
    }
}
