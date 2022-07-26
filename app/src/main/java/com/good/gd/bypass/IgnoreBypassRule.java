package com.good.gd.bypass;

import android.app.Activity;
import android.content.pm.PackageManager;
import com.good.gd.background.detection.rule.TrackActivityLifecycleRule;
import com.good.gd.context.GDContextAPI;
import com.good.gt.ndkproxy.util.GTLog;

/* loaded from: classes.dex */
public class IgnoreBypassRule implements TrackActivityLifecycleRule {
    private GDBypassAbility bypass;

    public IgnoreBypassRule(GDContextAPI gDContextAPI) {
        this.bypass = (GDBypassAbility) gDContextAPI.getDynamicsService("dynamics_bypass_service");
    }

    @Override // com.good.gd.background.detection.rule.TrackActivityLifecycleRule
    public boolean shouldTrackActivityLifecycle(Activity activity) {
        boolean isBypassActivity;
        try {
            isBypassActivity = this.bypass.isBypassActivity(activity.getPackageManager().getActivityInfo(activity.getComponentName(), 128).name);
        } catch (PackageManager.NameNotFoundException e) {
            GTLog.DBGPRINTF(12, IgnoreBypassRule.class.getSimpleName(), "Cannot check if activity is bypass. Using the second solution.\n");
            isBypassActivity = this.bypass.isBypassActivity(activity.getComponentName().getClassName());
        }
        return !(isBypassActivity & this.bypass.isBypassAllowed());
    }
}
