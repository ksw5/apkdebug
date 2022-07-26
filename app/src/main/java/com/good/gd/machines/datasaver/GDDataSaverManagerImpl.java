package com.good.gd.machines.datasaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import com.good.gd.context.GDContext;
import com.good.gd.datasaver.GDDataSaverControl;
import com.good.gd.datasaver.GDDataSaverObserver;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.service.GDActivityStateManager;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class GDDataSaverManagerImpl implements GDDataSaverControl {
    private final ConnectivityManager connMgr = (ConnectivityManager) GDContext.getInstance().getApplicationContext().getSystemService("connectivity");
    private final Map<GDDataSaverObserver, Object> observers = new HashMap();

    /* loaded from: classes.dex */
    public static class DataSaverPreferenceBroadcastReceiver extends BroadcastReceiver {
        private final GDDataSaverManagerImpl gdDataSaverManager;

        public DataSaverPreferenceBroadcastReceiver(GDDataSaverManagerImpl gDDataSaverManagerImpl) {
            this.gdDataSaverManager = gDDataSaverManagerImpl;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(16, "Broadcast received in DataSaverPreferenceBroadcastReceiver\n");
            this.gdDataSaverManager.notifyPreferencesChange();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class yfdke extends AsyncTask<Void, Void, Void> {
        private final Map<GDDataSaverObserver, Object> dbjc;
        private final ConnectivityManager qkduk;

        @Override // android.os.AsyncTask
        protected Void doInBackground(Void[] voidArr) {
            boolean queryDataSaverState = GDDataSaverManagerImpl.queryDataSaverState(this.qkduk);
            boolean z = GDActivityStateManager.getInstance() == null || GDActivityStateManager.getInstance().inBackground();
            if (!queryDataSaverState || !z) {
                GDDataSaverManagerImpl.notifyObservers(this.dbjc, false);
                return null;
            }
            GDDataSaverManagerImpl.notifyObservers(this.dbjc, true);
            return null;
        }

        private yfdke(Map<GDDataSaverObserver, Object> map, ConnectivityManager connectivityManager) {
            this.dbjc = map;
            this.qkduk = connectivityManager;
        }
    }

    public GDDataSaverManagerImpl() {
        registerPreferenceBroadcastReceiver();
    }

    private void notifyObservers(boolean z) {
        notifyObservers(this.observers, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPreferencesChange() {
        new yfdke(this.observers, this.connMgr).execute(new Void[0]);
    }

    private boolean queryDataSaverState() {
        return queryDataSaverState(this.connMgr);
    }

    private void registerPreferenceBroadcastReceiver() {
        DataSaverPreferenceBroadcastReceiver dataSaverPreferenceBroadcastReceiver = new DataSaverPreferenceBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
        GDContext.getInstance().getApplicationContext().registerReceiver(dataSaverPreferenceBroadcastReceiver, intentFilter);
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void addObserver(GDDataSaverObserver gDDataSaverObserver) {
        this.observers.put(gDDataSaverObserver, new Object());
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void onGDForegroundEvent(boolean z) {
        GDLog.DBGPRINTF(16, "GDDataSaverManagerImpl.onGDForegroundEvent(" + z + ")\n");
        if (!z && queryDataSaverState()) {
            notifyObservers(true);
        } else {
            notifyObservers(false);
        }
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void removeObserver(GDDataSaverObserver gDDataSaverObserver) {
        this.observers.remove(gDDataSaverObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyObservers(Map<GDDataSaverObserver, Object> map, boolean z) {
        GDLog.DBGPRINTF(16, "GDDataSaverManagerImpl.notifyObservers(" + z + ")\n");
        for (GDDataSaverObserver gDDataSaverObserver : map.keySet()) {
            gDDataSaverObserver.onDataSaverEnforcedEvent(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean queryDataSaverState(ConnectivityManager connectivityManager) {
        return connectivityManager.isActiveNetworkMetered() && connectivityManager.getRestrictBackgroundStatus() == 3;
    }
}
