package com.good.gd.ndkproxy.icc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.good.gd.ndkproxy.GDClipboardCryptoUtil;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class ComplianceBroadcastReceiver extends BroadcastReceiver {
    private boolean validateBroadcastMessage(String str) {
        int indexOf;
        String decryptString = GDClipboardCryptoUtil.decryptString(str);
        if (!decryptString.isEmpty() && (indexOf = decryptString.indexOf(":")) != -1) {
            try {
                if (GDIccManager.liflu().qkduk() == Integer.parseInt(decryptString.substring(0, indexOf))) {
                    try {
                        if (SystemClock.elapsedRealtime() <= Long.parseLong(decryptString.substring(indexOf + 1, decryptString.length())) + 5000) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            } catch (NumberFormatException e2) {
            }
        }
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        GDLog.DBGPRINTF(16, "ComplianceBroadcastReceiver received Compliance Broadcast\n");
        if (validateBroadcastMessage(intent.getStringExtra("extra_info"))) {
            GDLog.DBGPRINTF(16, "ComplianceBroadcastReceiver received valid\n");
            AuthDelegationConsumer.getInstance().enforceAuthDelegate();
        }
    }
}
