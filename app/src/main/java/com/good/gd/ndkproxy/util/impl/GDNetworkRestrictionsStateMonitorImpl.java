package com.good.gd.ndkproxy.util.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gt.context.GTBaseContext;
import com.good.gt.ndkproxy.util.GTLog;

/* loaded from: classes.dex */
public final class GDNetworkRestrictionsStateMonitorImpl extends Handler {
    public static final String INTENT_ACTION_FOR_NETWORK_RESTRICTIONS_CHANGE = "com.blackberry.endpoint.core_services.utils.android.network.NETWORK_RESTRICTIONS_CHANGE";
    private static final GDNetworkRestrictionsStateMonitorImpl mInstance = new GDNetworkRestrictionsStateMonitorImpl(Looper.getMainLooper());
    private final int NETWORK_RESTRICTIONS_CHANGE_MESSAGE = 1;
    private final ConnectivityManager connectivityManager = (ConnectivityManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("connectivity");
    private boolean isInitialised = false;
    private BroadcastReceiver mNetworkRestrictionChangeObserver;

    /* loaded from: classes.dex */
    class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GTLog.DBGPRINTF(16, "NetworkRestrictionsChangeObserver.onReceive\n");
            GDNetworkRestrictionsStateMonitorImpl.this.sendEmptyMessage(1);
        }
    }

    private GDNetworkRestrictionsStateMonitorImpl(Looper looper) {
        super(looper);
    }

    public static GDNetworkRestrictionsStateMonitorImpl getSharedInstance() {
        return mInstance;
    }

    public ConnectivityManager getConnectivityManager() {
        return this.connectivityManager;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        GDLocalBroadcastManager.getInstance().sendBroadcast(new Intent(INTENT_ACTION_FOR_NETWORK_RESTRICTIONS_CHANGE));
    }

    public void initialise() {
        if (this.isInitialised) {
            return;
        }
        this.mNetworkRestrictionChangeObserver = new hbfhc();
        GTBaseContext.getInstance().getApplicationContext().registerReceiver(this.mNetworkRestrictionChangeObserver, new IntentFilter("android.net.conn.RESTRICT_BACKGROUND_CHANGED"));
        this.isInitialised = true;
    }
}
