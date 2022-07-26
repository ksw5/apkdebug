package com.good.gd.ui.log_upload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.good.gd.GDAndroid;
import com.good.gd.log.GDLogManager;
import com.good.gd.log.GDLogUploadState;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.net.GDConnectivityManager;
import com.good.gd.net.GDNetworkInfo;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class GDLogUploadStateChangesReceiver implements LogUploadStateChangesReceiver {
    private final BroadcastReceiver viewBroadcastReceiver = new hbfhc();
    private final Set<LogUploadStateChangeListener> listeners = new HashSet();

    /* loaded from: classes.dex */
    class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            GDLog.DBGPRINTF(16, "GDLogUploadView.onReceive: " + action + '\n');
            if (GDLogManager.GD_LOG_UPLOAD_CHANGE_ACTION.equals(action)) {
                GDLogUploadStateChangesReceiver.this.notifyLogUploadStateChange();
            } else if (!GDConnectivityManager.GD_CONNECTIVITY_ACTION.equals(action)) {
            } else {
                GDLogUploadStateChangesReceiver.this.notifyNetworkStatusChange();
            }
        }
    }

    public GDLogUploadStateChangesReceiver() {
        registerBroadcastListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLogUploadStateChange() {
        GDLogUploadState uploadState = GDLogManager.getInstance().getUploadState();
        for (LogUploadStateChangeListener logUploadStateChangeListener : this.listeners) {
            logUploadStateChangeListener.updateLogUploadState(uploadState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNetworkStatusChange() {
        LogUploadNetworkState currentLogUploadNetworkState = getCurrentLogUploadNetworkState();
        for (LogUploadStateChangeListener logUploadStateChangeListener : this.listeners) {
            logUploadStateChangeListener.updateNetworkStatus(currentLogUploadNetworkState);
        }
    }

    private void registerBroadcastListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GDLogManager.GD_LOG_UPLOAD_CHANGE_ACTION);
        intentFilter.addAction(GDConnectivityManager.GD_CONNECTIVITY_ACTION);
        GDAndroid.getInstance().registerReceiver(this.viewBroadcastReceiver, new IntentFilter(intentFilter));
    }

    private void unregisterBroadcastListener() {
        GDAndroid.getInstance().unregisterReceiver(this.viewBroadcastReceiver);
    }

    @Override // com.good.gd.ui.log_upload.LogUploadStateChangesReceiver
    public void addListener(LogUploadStateChangeListener logUploadStateChangeListener) {
        if (this.listeners.isEmpty()) {
            registerBroadcastListener();
        }
        this.listeners.add(logUploadStateChangeListener);
    }

    public LogUploadNetworkState getCurrentLogUploadNetworkState() {
        GDNetworkInfo activeNetworkInfo = GDConnectivityManager.getActiveNetworkInfo();
        return LogUploadNetworkState.createLogUploadNetworkState(activeNetworkInfo.isConnected(), activeNetworkInfo.isAvailable(), activeNetworkInfo.getType(), activeNetworkInfo.getTypeName());
    }

    @Override // com.good.gd.ui.log_upload.LogUploadStateChangesReceiver
    public void removeListener(LogUploadStateChangeListener logUploadStateChangeListener) {
        this.listeners.remove(logUploadStateChangeListener);
        if (this.listeners.isEmpty()) {
            unregisterBroadcastListener();
        }
    }
}
