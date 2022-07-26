package com.good.gd.ndkproxy.icc;

import android.content.pm.PackageManager;
import android.widget.Toast;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.OnSideChannelDataSentListener;
import com.good.gt.utils.IccAppInfo;

/* loaded from: classes.dex */
public class GDOnSideChannelDataSentListener implements OnSideChannelDataSentListener {
    private void showWarningToast(String str) {
        try {
            PackageManager packageManager = GTBaseContext.getInstance().getApplicationContext().getPackageManager();
            str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            GDLog.DBGPRINTF(12, "Should not happen");
        }
        String format = String.format(GDLocalizer.getLocalizedString("<app name> does not fully support the version of Android on this device."), str);
        if (format.length() > 0) {
            Toast.makeText(GTBaseContext.getInstance().getApplicationContext(), format, 1).show();
        }
    }

    @Override // com.good.gt.icc.OnSideChannelDataSentListener
    public void onSideChannelDataSent(IccAppInfo iccAppInfo, int i) {
        if (i == 6) {
            showWarningToast(iccAppInfo.getPackageName());
        }
    }
}
