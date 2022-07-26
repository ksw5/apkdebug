package com.good.gd.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.policy.HyperVigilant;

/* loaded from: classes.dex */
public class GDUSBMonitor extends Handler {
    private static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    private static final String USB_CONNECTED = "connected";
    private static final String USB_FUNCTION_ADB = "adb";
    private static GDUSBMonitor _instance;
    private boolean outstandingCheckRequest = false;
    private boolean isConnected = true;
    private boolean isADBEnabled = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDUSBMonitor.this.handleReceivedAction(intent);
        }
    }

    private GDUSBMonitor() {
    }

    public static GDUSBMonitor getInstance() {
        GDUSBMonitor gDUSBMonitor = _instance;
        if (gDUSBMonitor != null) {
            return gDUSBMonitor;
        }
        throw new RuntimeException("GDUSBMonitor not initialized");
    }

    private void handleConnectionStatus(boolean z, boolean z2, boolean z3) {
        if (z == this.isConnected && z2 == this.isADBEnabled && (!z3 || !z)) {
            return;
        }
        GDLog.DBGPRINTF(16, "GDUSBMonitor: handleConnectionStatus: currentConnected = " + this.isConnected + " currentADBEnabled = " + this.isADBEnabled + " newConnected =" + z + " newADBEnabled = " + z2 + "\n");
        this.isConnected = z;
        this.isADBEnabled = z2;
        if (!z) {
            GDLog.DBGPRINTF(16, "GDUSBMonitor: USB DISCONNECTION EVENT\n");
            HyperVigilant.disengageHyperVigilantMode();
        } else if (z2) {
            GDLog.DBGPRINTF(16, "GDUSBMonitor: USB CONNECTION EVENT ADB Enabled\n");
            HyperVigilant.engageHyperVigilantMode(2);
        } else {
            GDLog.DBGPRINTF(16, "GDUSBMonitor: USB CONNECTION EVENT ADB Not Enabled\n");
            HyperVigilant.engageHyperVigilantMode(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceivedAction(Intent intent) {
        GDLog.DBGPRINTF(12, "GDUSBMonitor: received broadcast intent: " + intent + "\n");
        synchronized (this) {
            if (intent.getAction().equals(ACTION_USB_STATE) || intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                if (!this.outstandingCheckRequest) {
                    Boolean bool = false;
                    Boolean bool2 = false;
                    if (intent.getAction().equals(ACTION_USB_STATE)) {
                        bool = Boolean.valueOf(intent.getBooleanExtra(USB_CONNECTED, false));
                        bool2 = Boolean.valueOf(intent.getBooleanExtra(USB_FUNCTION_ADB, false) ? true : isADBEnabled());
                    } else if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                        if (intent.getIntExtra("plugged", 0) == 2) {
                            bool = true;
                        }
                        bool2 = Boolean.valueOf(isADBEnabled());
                    }
                    Message obtainMessage = obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("usb_connected", bool.booleanValue());
                    bundle.putBoolean("adb_enabled", bool2.booleanValue());
                    obtainMessage.setData(bundle);
                    sendMessage(obtainMessage);
                    this.outstandingCheckRequest = true;
                }
            }
        }
    }

    public static void initializeInstance() throws Exception {
        _instance = new GDUSBMonitor();
    }

    private boolean isADBEnabled() {
        try {
            return Settings.Global.getInt(GDContext.getInstance().getApplicationContext().getContentResolver(), "adb_enabled") == 1;
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            synchronized (this) {
                this.outstandingCheckRequest = false;
            }
            Bundle data = message.getData();
            if (data == null) {
                return;
            }
            handleConnectionStatus(data.getBoolean("usb_connected", false), data.getBoolean("adb_enabled", false), false);
        } catch (Exception e) {
            GDLog.DBGPRINTF(13, "GDUSBMonitor: " + e);
        }
    }

    public void startMonitoring() {
        if (GDDeviceInfo.getInstance().isSimulator()) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_USB_STATE);
        Intent registerReceiver = GDContext.getInstance().getApplicationContext().registerReceiver(new hbfhc(), intentFilter);
        boolean z = false;
        Boolean bool = false;
        Boolean valueOf = Boolean.valueOf(isADBEnabled());
        Boolean bool2 = false;
        if (registerReceiver != null) {
            bool = Boolean.valueOf(registerReceiver.getBooleanExtra(USB_CONNECTED, false));
            bool2 = Boolean.valueOf(registerReceiver.getBooleanExtra(USB_FUNCTION_ADB, false));
        }
        if (valueOf.booleanValue() || bool2.booleanValue()) {
            z = true;
        }
        handleConnectionStatus(bool.booleanValue(), Boolean.valueOf(z).booleanValue(), true);
    }
}
