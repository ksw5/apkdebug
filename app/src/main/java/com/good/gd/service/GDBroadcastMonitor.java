package com.good.gd.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.good.gd.context.GDContext;

/* loaded from: classes.dex */
public abstract class GDBroadcastMonitor extends Handler {
    protected static GDBroadcastMonitor _instance;

    /* loaded from: classes.dex */
    private class yfdke extends BroadcastReceiver {
        private yfdke() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (GDBroadcastMonitor.this.checkReceivedIntent(intent)) {
                GDBroadcastMonitor.this.handleIntent(intent);
            }
        }
    }

    private Message createGDPackageMonitorMessage(Intent intent) {
        Message message = new Message();
        message.setData(createMessage(intent));
        return message;
    }

    public static GDBroadcastMonitor getInstance() {
        GDBroadcastMonitor gDBroadcastMonitor = _instance;
        if (gDBroadcastMonitor != null) {
            return gDBroadcastMonitor;
        }
        throw new RuntimeException("GDPackageMonitor not initialized");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIntent(Intent intent) {
        sendMessage(createGDPackageMonitorMessage(intent));
    }

    protected abstract boolean checkReceivedIntent(Intent intent);

    protected abstract Bundle createMessage(Intent intent);

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        processMessage(message);
    }

    protected abstract void processMessage(Message message);

    protected abstract IntentFilter registerBroadcastIntents();

    public void startMonitoring() {
        GDContext.getInstance().getApplicationContext().registerReceiver(new yfdke(), registerBroadcastIntents());
    }
}
