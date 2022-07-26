package com.good.gd.machines.doze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ReflectionUtils;

/* loaded from: classes.dex */
public class GDDozeLightHelper extends BroadcastReceiver {
    private static final String IDLE_LIGHT_MODE_FIELD_NAME = "ACTION_LIGHT_DEVICE_IDLE_MODE_CHANGED";
    private static final String IDLE_LIGHT_MODE_METHOD_NAME = "isLightDeviceIdleMode";
    GDDozeLightMonitorControl mListener;
    private String mActionValue = null;
    private PowerManager mPowerManager = (PowerManager) GDContext.getInstance().getApplicationContext().getSystemService("power");

    public GDDozeLightHelper(GDDozeLightMonitorControl gDDozeLightMonitorControl) {
        this.mListener = null;
        this.mListener = gDDozeLightMonitorControl;
        if (!ReflectionUtils.canUseReflectionInAndroidPorLater() || !initReflection()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(this.mActionValue);
        GDContext.getInstance().getApplicationContext().registerReceiver(this, intentFilter);
    }

    private boolean checkIsInLightDozeMode() {
        boolean z;
        if (!ReflectionUtils.canUseReflectionInAndroidPorLater()) {
            z = false;
        } else {
            try {
                z = ((Boolean) ReflectionUtils.invokeMethod(PowerManager.class, this.mPowerManager, IDLE_LIGHT_MODE_METHOD_NAME, null, new Object[0])).booleanValue();
            } catch (ReflectionUtils.MethodAccessException e) {
                GDLog.DBGPRINTF(14, "GDDozeLightHelperReceiver.checkIsInLightDozeMode MethodAccessException\n");
                z = false;
            }
        }
        if (!z || !this.mPowerManager.isIgnoringBatteryOptimizations(GDContext.getInstance().getApplicationContext().getPackageName())) {
            return z;
        }
        return false;
    }

    private void handleLightDozeChange(boolean z) {
        GDDozeLightMonitorControl gDDozeLightMonitorControl;
        if (!z || (gDDozeLightMonitorControl = this.mListener) == null) {
            return;
        }
        gDDozeLightMonitorControl.onDozeLightModeDetected();
    }

    private boolean initReflection() {
        try {
            String str = (String) ReflectionUtils.getFieldValue(PowerManager.class, this.mPowerManager, IDLE_LIGHT_MODE_FIELD_NAME);
            this.mActionValue = str;
            if (str == null) {
                return false;
            }
            if (str.isEmpty()) {
                return false;
            }
            return ReflectionUtils.hasMethod(PowerManager.class, IDLE_LIGHT_MODE_METHOD_NAME, null);
        } catch (ReflectionUtils.FieldAccessException e) {
            GDLog.DBGPRINTF(14, "GDDozeLightHelper reflection failed. Ignore\n");
            return false;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !intent.getAction().equalsIgnoreCase(this.mActionValue)) {
            return;
        }
        boolean checkIsInLightDozeMode = checkIsInLightDozeMode();
        GDLog.DBGPRINTF(14, "GDDozeLightHelper.onReceive inLightDoze = " + checkIsInLightDozeMode + "\n");
        handleLightDozeChange(checkIsInLightDozeMode);
    }
}
