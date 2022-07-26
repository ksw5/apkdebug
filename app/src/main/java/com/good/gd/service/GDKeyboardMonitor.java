package com.good.gd.service;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDKeyboardMonitor extends GDBroadcastMonitor {
    public static void initializeInstance() throws Exception {
        GDLog.DBGPRINTF(16, "GDKeyboardMonitor: initializeInstance\n");
        GDBroadcastMonitor._instance = new GDKeyboardMonitor();
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected boolean checkReceivedIntent(Intent intent) {
        return intent.getAction().equals("android.intent.action.INPUT_METHOD_CHANGED");
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected Bundle createMessage(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putString("action", intent.getAction());
        return bundle;
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected void processMessage(Message message) {
        if (message.getData().getString("action").equals("android.intent.action.INPUT_METHOD_CHANGED")) {
            GDKeyboardControl.getInstance().defaultSystemKeyboardChanged();
        }
    }

    @Override // com.good.gd.service.GDBroadcastMonitor
    protected IntentFilter registerBroadcastIntents() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.INPUT_METHOD_CHANGED");
        return intentFilter;
    }
}
