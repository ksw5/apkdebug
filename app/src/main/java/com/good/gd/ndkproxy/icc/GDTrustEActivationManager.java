package com.good.gd.ndkproxy.icc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.GDInternalActivityImpl;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.BBDGraphicsUtils;

/* loaded from: classes.dex */
public class GDTrustEActivationManager {
    private static GDTrustEActivationManager instance;
    private String applicationAddress;
    private String applicationId;
    private String applicationName;
    private String applicationVersion;

    private GDTrustEActivationManager() {
    }

    public static GDTrustEActivationManager getInstance() {
        if (instance == null) {
            synchronized (GDTrustEActivationManager.class) {
                instance = new GDTrustEActivationManager();
            }
        }
        return instance;
    }

    public static boolean isTrustedAuthenticator() {
        return GDDefaultAppEventListener.getInstance().isTrustedAuthenticator();
    }

    private native void ndkInit();

    public void closeInternalActivity() {
        GDLog.DBGPRINTF(12, "GDTrustEActivationManager: closeInternalActivity()");
        GDInternalActivityImpl mo295getInternalActivity = UniversalActivityController.getInstance().mo295getInternalActivity();
        if (mo295getInternalActivity != null) {
            mo295getInternalActivity.finish();
        }
    }

    public Bitmap getApplicationIcon() {
        PackageManager packageManager;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext == null) {
            packageManager = null;
        } else {
            packageManager = applicationContext.getPackageManager();
        }
        if (packageManager == null) {
            return null;
        }
        try {
            String str = this.applicationAddress;
            if (str.endsWith(".IccReceivingActivity")) {
                str = str.substring(0, str.length() - 21);
            }
            return BBDGraphicsUtils.convertToBitmap(packageManager.getApplicationIcon(str));
        } catch (PackageManager.NameNotFoundException e) {
            throw new Error("This case is impossible as requesting application is always installed:\n" + e.getMessage());
        }
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public String getApplicationVersion() {
        return this.applicationVersion;
    }

    public Bitmap getThisApplicationIcon() {
        PackageManager packageManager;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext == null) {
            packageManager = null;
        } else {
            packageManager = applicationContext.getPackageManager();
        }
        if (packageManager == null) {
            return null;
        }
        try {
            return BBDGraphicsUtils.convertToBitmap(packageManager.getApplicationIcon(applicationContext.getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            throw new Error("This case is impossible as requesting application is always installed:\n" + e.getMessage());
        }
    }

    public void initialize() {
        ndkInit();
    }

    public void moveTaskToBack() {
        GDLog.DBGPRINTF(16, "GDTrustEActivationManager: moveTaskToBack()");
        if (UniversalActivityController.getInstance().getContentActivity() != null) {
            GDLog.DBGPRINTF(16, "GDTrustEActivationManager: moveTaskToBack(content activity) successfully: " + UniversalActivityController.getInstance().getContentActivity().moveTaskToBack(true));
        } else if (UniversalActivityController.getInstance().getCurrentActivity() == null) {
            GDLog.DBGPRINTF(16, "GDTrustEActivationManager: moveTaskToBack() unable to move to back");
        } else {
            GDLog.DBGPRINTF(16, "GDTrustEActivationManager: moveTaskToBack(current activity) successfully: " + UniversalActivityController.getInstance().getCurrentActivity().moveTaskToBack(true));
        }
    }

    public void onCancelTrustedActivation() {
        closeInternalActivity();
    }

    public void setActivationInfo(String str, String str2, String str3, String str4) {
        this.applicationId = str;
        this.applicationAddress = str2;
        this.applicationName = str3;
        this.applicationVersion = str4;
    }
}
