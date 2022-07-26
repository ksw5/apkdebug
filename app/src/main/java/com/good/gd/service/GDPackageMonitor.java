package com.good.gd.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDPackageMonitor extends GDBroadcastMonitor {
    public static void initializeInstance() throws Exception {
        GDLog.DBGPRINTF(16, "GDPackageMonitor: initializeInstance\n");
        GDBroadcastMonitor._instance = new GDPackageMonitor();
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected boolean checkReceivedIntent(Intent intent) {
        return intent.getAction().equals("android.intent.action.PACKAGE_ADDED") || intent.getAction().equals("android.intent.action.PACKAGE_REMOVED") || intent.getAction().equals("android.intent.action.PACKAGE_REPLACED");
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected Bundle createMessage(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putString("action", intent.getAction());
        bundle.putInt("Uid", intent.getIntExtra("android.intent.extra.UID", 0));
        bundle.putString("Package_Name", intent.getData().toString().substring(8));
        return bundle;
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected void processMessage(Message message) {
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected IntentFilter registerBroadcastIntents() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        return intentFilter;
    }
}
