package com.good.gd.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ui.utils.ApplicationInfoProvider;

/* loaded from: classes.dex */
public class GDApplicationInfoProvider implements ApplicationInfoProvider {
    @Override // com.good.gd.ui.utils.ApplicationInfoProvider
    public String getApplicationId() {
        return GDClient.getInstance().getApplicationId();
    }

    @Override // com.good.gd.ui.utils.ApplicationInfoProvider
    public String getApplicationName() {
        return GDApplicationUtils.getApplicationName(GDContext.getInstance().getApplicationContext());
    }

    @Override // com.good.gd.ui.utils.ApplicationInfoProvider
    public String getApplicationVersion() {
        return GDClient.getInstance().getApplicationVersion();
    }

    @Override // com.good.gd.ui.utils.ApplicationInfoProvider
    public String getClientBundleVersion() {
        String str;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        try {
            str = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return str == null ? "" : str;
    }

    @Override // com.good.gd.ui.utils.ApplicationInfoProvider
    public String getClientVersion() {
        return GDDeviceInfo.getInstance().getClientVersion();
    }
}
