package com.good.gd.machines.doze;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Display;
import androidx.core.app.NotificationCompat;
import com.good.gd.GDAndroid;
import com.good.gd.GDStateAction;
import com.good.gd.backgroundexecution.GDBackgroundExecutionHelper;
import com.good.gd.context.GDContext;
import com.good.gd.machines.doze.GDDozeLightMonitorControl;
import com.good.gd.machines.powermanagment.BBDStandbyBucketsManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.net.impl.DataConnectivityCheckObserver;
import com.good.gd.net.impl.DataConnectivityCheckResult;
import com.good.gd.net.impl.DataConnectivityCheckTask;
import com.good.gd.net.impl.DataConnectivityType;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class GDDozeLightMonitorImpl implements GDDozeLightMonitorControl, DataConnectivityCheckObserver {
    private static final String ACTION_TIMEOUT_PREFIX = "GDDozeLightMonitor.intent.action.ACTION_TIMEOUT";
    private static final long DOZE_LIGHT_POLLING_INTERVAL = 300000;
    private static final long INEXACT_ALARM_WINDOW_INTERVAL = 600000;
    private static final long NETWORK_REFRESH_DELAY = 500;
    private GDDozeLightHelper dozeLightHelper;
    private boolean isCharging;
    private boolean isInitialized;
    private boolean isScreenOn;
    private BBDStandbyBucketsManager standbyBucketsManager;
    private PendingIntent timeoutIntent;
    private BroadcastReceiver gdSDKStateReceiver = null;
    private Mode mode = Mode.DISABLED;
    private boolean isInFullDozeMode = false;
    private boolean isInForeground = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ehnkx extends BroadcastReceiver {
        private final GDDozeLightMonitorImpl dbjc;

        public ehnkx(GDDozeLightMonitorImpl gDDozeLightMonitorImpl) {
            this.dbjc = gDDozeLightMonitorImpl;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: charging receiver\n");
            this.dbjc.onChargingChanged(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd extends BroadcastReceiver {
        fdyxd() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: alarm receiver\n");
            GDDozeLightMonitorImpl.this.updateState();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (!action.equals(GDStateAction.GD_STATE_AUTHORIZED_ACTION)) {
                    return;
                }
                GDLog.DBGPRINTF(14, "GDDozeLightMonitor: state action - " + action + "\n");
                if (GDDozeLightMonitorImpl.this.isInitialized) {
                    return;
                }
                GDDozeLightMonitorImpl.this.onAuthorized();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class pmoiy extends BroadcastReceiver {
        private final GDDozeLightMonitorImpl dbjc;

        public pmoiy(GDDozeLightMonitorImpl gDDozeLightMonitorImpl) {
            this.dbjc = gDDozeLightMonitorImpl;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: screen receiver\n");
            this.dbjc.onScreenOnOffChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDAndroid.getInstance().unregisterReceiver(GDDozeLightMonitorImpl.this.gdSDKStateReceiver);
            GDDozeLightMonitorImpl.this.gdSDKStateReceiver = null;
        }
    }

    public GDDozeLightMonitorImpl() {
        registerGDStateReceiver();
    }

    private void handleDozeLightModeDetected() {
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor: handle Doze Light mode\n");
        resetInternalConnectivities();
        this.mode = Mode.LIGHT_ENABLED;
    }

    private void initAlarm(Context context) {
        String str = "GDDozeLightMonitor.intent.action.ACTION_TIMEOUT_" + context.getPackageName();
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(str), 335544320);
        this.timeoutIntent = broadcast;
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(broadcast);
        fdyxd fdyxdVar = new fdyxd();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        context.registerReceiver(fdyxdVar, intentFilter);
    }

    private void initChargingState(Context context) {
        onChargingChanged(context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")));
    }

    private boolean isTargetingAndroidSOrAbove() {
        return GTBaseContext.getInstance().getApplicationContext().getApplicationInfo().targetSdkVersion > 30 && ((Build.VERSION.SDK_INT > 30) || (Build.VERSION.PREVIEW_SDK_INT != 0));
    }

    private void logState() {
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor:updateState  isInFullDozeMode=" + this.isInFullDozeMode + " isInForeground=" + this.isInForeground + " isCharging=" + this.isCharging + " isScreenOn=" + this.isScreenOn + "\n");
        this.standbyBucketsManager.printStandbyBucketInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthorized() {
        initialize();
        updateState();
        unregisterGDStateReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChargingChanged(Intent intent) {
        int intExtra = intent.getIntExtra("status", -1);
        this.isCharging = intExtra == 2 || intExtra == 5;
        updateState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenOnOffChanged() {
        updateState();
    }

    private void registerChargingReceiver(Context context) {
        ehnkx ehnkxVar = new ehnkx(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        context.registerReceiver(ehnkxVar, intentFilter);
    }

    private void registerGDStateReceiver() {
        this.gdSDKStateReceiver = new hbfhc();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GDStateAction.GD_STATE_AUTHORIZED_ACTION);
        GDAndroid.getInstance().registerReceiver(this.gdSDKStateReceiver, intentFilter);
    }

    private void registerScreenOnOffReceiver(Context context) {
        pmoiy pmoiyVar = new pmoiy(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(pmoiyVar, intentFilter);
    }

    private void resetInternalConnectivities() {
        GDBackgroundExecutionHelper.disconnectPushConnection();
        GDBackgroundExecutionHelper.drainSocketPoolAsync();
    }

    private void startMonitoring() {
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor: start monitoring\n");
        AlarmManager alarmManager = (AlarmManager) GDContext.getInstance().getApplicationContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (isTargetingAndroidSOrAbove()) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: set an inexact alarm \n");
            alarmManager.setWindow(2, SystemClock.elapsedRealtime() + DOZE_LIGHT_POLLING_INTERVAL, INEXACT_ALARM_WINDOW_INTERVAL, this.timeoutIntent);
            return;
        }
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor: set an exact alarm \n");
        alarmManager.setExact(2, SystemClock.elapsedRealtime() + DOZE_LIGHT_POLLING_INTERVAL, this.timeoutIntent);
    }

    private void stopMonitoring() {
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor: stop monitoring\n");
        AlarmManager alarmManager = (AlarmManager) GDContext.getInstance().getApplicationContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        PendingIntent pendingIntent = this.timeoutIntent;
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
        this.mode = Mode.DISABLED;
    }

    private void unregisterGDStateReceiver() {
        new Handler().postDelayed(new yfdke(), 0L);
    }

    private void updateScreenState() {
        Display[] displays;
        boolean z = false;
        for (Display display : ((DisplayManager) GDContext.getInstance().getApplicationContext().getSystemService("display")).getDisplays()) {
            if (display.getDisplayId() == 0) {
                if (display.getState() == 2) {
                    z = true;
                }
                this.isScreenOn = z;
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateState() {
        if (!this.isInitialized) {
            return;
        }
        updateScreenState();
        logState();
        if (this.isInFullDozeMode) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: in full Doze mode\n");
            stopMonitoring();
        } else if (!this.isCharging && !this.isScreenOn) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: try connecting...\n");
            DataConnectivityCheckTask.submit(this);
        } else {
            if (this.mode == Mode.LIGHT_ENABLED) {
                GDLog.DBGPRINTF(14, "GDDozeLightMonitor: out of Doze Light by user activity\n");
                NetworkStateMonitor.getInstance().refreshNetworkStateDelayed(NETWORK_REFRESH_DELAY);
                GDBackgroundExecutionHelper.connectPushConnection();
            } else {
                NetworkStateMonitor.getInstance().refreshNetworkStateIfNotConnected(NETWORK_REFRESH_DELAY);
            }
            stopMonitoring();
        }
    }

    @Override // com.good.gd.net.impl.DataConnectivityCheckObserver
    public void dataConnectivityCheckResult(DataConnectivityCheckResult dataConnectivityCheckResult) {
        GDLog.DBGPRINTF(14, "GDDozeLightMonitor: connectivity=" + dataConnectivityCheckResult.getResultString() + "\n");
        DataConnectivityType result = dataConnectivityCheckResult.getResult();
        if (DataConnectivityType.DC_OPEN == result) {
            if (this.mode == Mode.LIGHT_ENABLED) {
                this.mode = Mode.DISABLED;
                GDLog.DBGPRINTF(14, "GDDozeLightMonitor: network restored by maintenance window\n");
                GDBackgroundExecutionHelper.connectPushConnection();
                return;
            }
            this.mode = Mode.DISABLED;
        } else if (DataConnectivityType.DC_CAPTIVE_PORTAL == result) {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: captive portal\n");
            handleDozeLightModeDetected();
        } else {
            GDLog.DBGPRINTF(14, "GDDozeLightMonitor: Doze Light mode detected\n");
            handleDozeLightModeDetected();
        }
        startMonitoring();
    }

    public void initialize() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        this.dozeLightHelper = new GDDozeLightHelper(this);
        this.standbyBucketsManager = new BBDStandbyBucketsManager(applicationContext);
        initAlarm(applicationContext);
        initChargingState(applicationContext);
        registerChargingReceiver(applicationContext);
        updateScreenState();
        registerScreenOnOffReceiver(applicationContext);
        this.isInitialized = true;
    }

    @Override // com.good.gd.machines.doze.GDDozeLightMonitorControl
    public void onDozeLightModeDetected() {
        if (GDBackgroundExecutionHelper.isPushConnectionConnected()) {
            updateState();
        } else {
            handleDozeLightModeDetected();
        }
    }

    @Override // com.good.gd.machines.doze.GDDozeLightMonitorControl
    public void onDozeModeChangedEvent(Mode mode) {
        this.isInFullDozeMode = mode == Mode.FULL_ENABLED;
        updateState();
    }

    @Override // com.good.gd.machines.doze.GDDozeLightMonitorControl
    public void onGDForegroundEvent(boolean z) {
        this.isInForeground = z;
        updateState();
    }
}
