package com.good.gd.ndkproxy.ui.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class CloseBiometryDialogCheckerSystemBroadcastReceiver implements CloseBiometryDialogChecker {
    private SystemBroadcastReceiver closeBiometricDialogSystemIntentReceiver = new SystemBroadcastReceiver();
    boolean hasReceivedRecentlySystemIntentToClose;

    /* loaded from: classes.dex */
    private class SystemBroadcastReceiver extends BroadcastReceiver {
        private SystemBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "CloseBiometryDialogCheckerSystemBroadcastReceiver::BroadcastReceiver::ACTION_CLOSE_SYSTEM_DIALOGS\n");
            CloseBiometryDialogCheckerSystemBroadcastReceiver.this.hasReceivedRecentlySystemIntentToClose = true;
        }
    }

    public CloseBiometryDialogCheckerSystemBroadcastReceiver() {
        this.hasReceivedRecentlySystemIntentToClose = false;
        this.hasReceivedRecentlySystemIntentToClose = false;
    }

    @Override // com.good.gd.ndkproxy.ui.data.CloseBiometryDialogChecker
    public void activate() {
        resetState();
        GTBaseContext.getInstance().getApplicationContext().registerReceiver(this.closeBiometricDialogSystemIntentReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    @Override // com.good.gd.ndkproxy.ui.data.CloseBiometryDialogChecker
    public void deactivate() {
        GTBaseContext.getInstance().getApplicationContext().unregisterReceiver(this.closeBiometricDialogSystemIntentReceiver);
    }

    @Override // com.good.gd.ndkproxy.ui.data.CloseBiometryDialogChecker
    public boolean needToCloseBiometryDialog() {
        return !this.hasReceivedRecentlySystemIntentToClose;
    }

    public void resetState() {
        this.hasReceivedRecentlySystemIntentToClose = false;
    }
}
