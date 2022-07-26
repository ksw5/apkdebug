package com.good.gd.ndkproxy.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import com.good.gd.ApplicationContext;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.GDNetworkProtection;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.datasaver.GDDataSaverManager;
import com.good.gd.datasaver.GDDataSaverObserver;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.push.PushConnectionStatusChangedListener;
import com.good.gd.ndkproxy.util.GDNetworkStateMonitor;
import com.good.gd.ndkproxy.util.IGDActiveNetworkStateMonitor;
import com.good.gd.ndkproxy.util.impl.NetworkStateMonitorSettings;
import com.good.gd.net.GDConnectivityManager;
import com.good.gd.net.GDNetNetworkProtection;
import com.good.gd.net.GDNetworkInfo;
import com.good.gd.net.WifiConnectionListener;
import com.good.gd.net.impl.DataConnectivityCheckObserver;
import com.good.gd.net.impl.DataConnectivityCheckResult;
import com.good.gd.net.impl.DataConnectivityCheckTask;
import com.good.gd.net.impl.DataConnectivityType;
import com.good.gd.push.PushConnection;

/* loaded from: classes.dex */
public class NetworkStateMonitor extends Handler implements DataConnectivityCheckObserver, GDDataSaverObserver, PushConnectionStatusChangedListener {
    private static NetworkStateMonitor _instance = null;
    private static HandlerThread _networkStatusMonitorThread = null;
    private static final long mDataConnectivityCheckDelta = 15000;
    private static boolean mFirstRun;
    private static long mLastDataConnectivityCheckTimeStamp = 0;
    private IGDActiveNetworkStateMonitor activeNetworkStateMonitor;
    private BroadcastReceiver mConnReceiver;
    private ContainerState mContainerState;
    private Context mContext = null;
    private DataConnectivityCheckResult mLastDataConnectivityCheckResult;
    private GDSocketEventsListener mSocketEventsListener;
    private WifiConnectionListener mWifiConnectionListener;

    /* loaded from: classes.dex */
    class NetworkStateMonitorBroadcastReceiver extends BroadcastReceiver {
        NetworkStateMonitorBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(16, "GDNetworkStateReceiver.onReceive:\n");
            NetworkStateMonitor.this.sendMessage(new Message());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class NetworkStatusCache {
        private static NetworkStatusCache jcpqe = new NetworkStatusCache(false, false, false, false, -1, null, false);
        private final boolean dbjc;
        private final boolean jwxax;
        private final boolean liflu;
        private final String lqox;
        private final boolean qkduk;
        private final boolean wxau;
        private final int ztwf;

        public static NetworkStatusCache getCachedStatus() {
            return jcpqe;
        }

        public static void setStatus(NetworkStatusCache networkStatusCache) {
            jcpqe = networkStatusCache;
        }

        private NetworkStatusCache(boolean z, boolean z2, boolean z3, boolean z4, int i, String str, boolean z5) {
            this.dbjc = z;
            this.qkduk = z2;
            this.jwxax = z3;
            this.wxau = z4;
            this.ztwf = i;
            this.lqox = str;
            this.liflu = z5;
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("NetworkStatusMonitor");
        _networkStatusMonitorThread = handlerThread;
        handlerThread.start();
    }

    private NetworkStateMonitor() {
        super(_networkStatusMonitorThread.getLooper());
        mFirstRun = true;
    }

    public static NetworkStateMonitor getInstance() {
        if (_instance == null) {
            _instance = new NetworkStateMonitor();
            GDNetworkProtection.getInstance().setNetworkConfigProvider(GDNetNetworkProtection.getInstance());
        }
        return _instance;
    }

    private void logCaptivePortalInfo() {
        String string;
        Context context = this.mContext;
        if (context == null || (string = Settings.Global.getString(context.getContentResolver(), "captive_portal_http_url")) == null) {
            return;
        }
        GDLog.DBGPRINTF(14, "NetworkStateMonitor default captive portal = " + string + "\n");
    }

    private void sendBroadcasts() {
        GDLog.DBGPRINTF(14, "NetworkStateMonitor.sendBroadcasts\n");
        GDLocalBroadcastManager.getInstance().sendBroadcast(new Intent(GDConnectivityManager.GD_CONNECTIVITY_ACTION));
    }

    private void setStatus(NetworkStatusCache networkStatusCache) {
        GDLog.DBGPRINTF(16, "NetworkStateMonitor.SetStatus called with New Status: connected = " + networkStatusCache.qkduk + " available = " + networkStatusCache.jwxax + " dataSaverEnforced = " + networkStatusCache.liflu + " networkTypeName = " + networkStatusCache.lqox + " networkType = " + networkStatusCache.ztwf + "\n");
        NetworkStatusCache.setStatus(networkStatusCache);
    }

    private void startDataConnectivityCheckTask(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - mLastDataConnectivityCheckTimeStamp;
        if (z || j > mDataConnectivityCheckDelta) {
            GDLog.DBGPRINTF(16, "NetworkStateMonitor foregroundEventNetworkStateUpdate start\n");
            mLastDataConnectivityCheckTimeStamp = currentTimeMillis;
            DataConnectivityCheckTask.submit(this);
        }
    }

    private void updateDataConnectivityCheckResult() {
        synchronized (this) {
            if (this.mLastDataConnectivityCheckResult != null) {
                NetworkStateMonitorBase.getInstance().updateConnectivityCheckResult(this.mLastDataConnectivityCheckResult.getFormattedTime(), this.mLastDataConnectivityCheckResult.getURLString(), this.mLastDataConnectivityCheckResult.getResultString());
            }
        }
    }

    private void updateNetworkStatus() {
        GDLog.DBGPRINTF(14, "NetworkStateMonitor.updateNetworkStatus IN\n");
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        NetworkStateMonitorBase.getInstance().updateStatus(cachedStatus.qkduk, cachedStatus.dbjc, cachedStatus.wxau, cachedStatus.jwxax);
        sendBroadcasts();
        GDLog.DBGPRINTF(16, "NetworkStateMonitor.updateNetworkStatus OUT\n");
    }

    @Override // com.good.gd.net.impl.DataConnectivityCheckObserver
    public void dataConnectivityCheckResult(DataConnectivityCheckResult dataConnectivityCheckResult) {
        this.mLastDataConnectivityCheckResult = dataConnectivityCheckResult;
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        this.mWifiConnectionListener.setIsWifiConnected(cachedStatus.dbjc);
        boolean isAvailable = dataConnectivityCheckResult.isAvailable();
        setStatus(new NetworkStatusCache(cachedStatus.dbjc, isAvailable, isAvailable, cachedStatus.wxau, cachedStatus.ztwf, cachedStatus.lqox, cachedStatus.liflu));
        GDLog.DBGPRINTF(16, "isWalledGardenCheckTask dataConnectivity hasNetwork - : " + isAvailable + "\n");
        updateDataConnectivityCheckResult();
        updateNetworkStatus();
    }

    public void foregroundEventNetworkStateUpdate() {
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        if (!cachedStatus.liflu) {
            if (cachedStatus.qkduk || !cachedStatus.dbjc) {
                return;
            }
            startDataConnectivityCheckTask(false);
            return;
        }
        refreshNetworkStateDelayed(0L);
    }

    public GDNetworkInfo getNetworkInfo() {
        ContainerState containerState = this.mContainerState;
        boolean z = containerState != null && containerState.isAuthorized() && PushConnection.getInstance().isConnected();
        DataConnectivityCheckResult dataConnectivityCheckResult = this.mLastDataConnectivityCheckResult;
        boolean z2 = dataConnectivityCheckResult != null && dataConnectivityCheckResult.getResultString().equals(DataConnectivityType.DC_CAPTIVE_PORTAL.getName());
        DataConnectivityCheckResult dataConnectivityCheckResult2 = this.mLastDataConnectivityCheckResult;
        boolean z3 = dataConnectivityCheckResult2 != null && dataConnectivityCheckResult2.getResultString().equals(DataConnectivityType.DC_UNKNOWN.getName());
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        GDLog.DBGPRINTF(16, "NetworkStateMonitor.getNetworkInfo : connected = " + cachedStatus.qkduk + " available = " + cachedStatus.jwxax + " pushChannelAvailable = " + z + " isCaptivePortal = " + z2 + " isUnknownOutage = " + z3 + " dataSaverEnforced = " + cachedStatus.liflu + " networkTypeName = " + cachedStatus.lqox + " networkType = " + cachedStatus.ztwf + "\n");
        return new GDNetworkInfo(cachedStatus.qkduk, cachedStatus.jwxax, z, cachedStatus.liflu, z2, z3, cachedStatus.ztwf, cachedStatus.lqox);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        NetworkCapabilities networkCapabilities;
        boolean z;
        boolean z2;
        boolean z3;
        String str;
        int i;
        boolean z4;
        GDLog.DBGPRINTF(16, "NetworkStateMonitor:handleMessage CONNECTIVITY_ACTION\n");
        NetworkInfo networkInfo = this.activeNetworkStateMonitor.getActiveNetworkInfo(false).getNetworkInfo();
        Network activeNetwork = GDNetworkStateMonitor.getConnectivityManager().getActiveNetwork();
        if ((activeNetwork != null ? GDNetworkStateMonitor.getConnectivityManager().getNetworkCapabilities(activeNetwork) : null) != null) {
            GDLog.DBGPRINTF(16, "NetworkStateMonitor: NetworkCapabilities.isCaptivePortal=" + networkCapabilities.hasCapability(17) + "\n");
        }
        if (networkInfo != null) {
            z = networkInfo.isConnected();
            z4 = "WIFI".equals(networkInfo.getTypeName());
            z2 = "MOBILE".equals(networkInfo.getTypeName());
            z3 = networkInfo.isAvailable() && !NetworkStatusCache.getCachedStatus().liflu;
            int type = networkInfo.getType();
            String typeName = networkInfo.getTypeName();
            GDLog.DBGPRINTF(16, "NetworkStateMonitor: isConnected=" + z + " isAvailable=" + z3 + " isWifi=" + z4 + "\n");
            if (z && z3 && z4) {
                startDataConnectivityCheckTask(true);
                DataConnectivityCheckResult dataConnectivityCheckResult = this.mLastDataConnectivityCheckResult;
                if (dataConnectivityCheckResult != null && !dataConnectivityCheckResult.isAvailable()) {
                    return;
                }
            }
            str = typeName;
            i = type;
        } else {
            GDLog.DBGPRINTF(16, "NetworkStateMonitor: no active network\n");
            z = false;
            z2 = false;
            z3 = false;
            str = null;
            i = -1;
            z4 = false;
        }
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        if (!mFirstRun && cachedStatus.jwxax == z3 && cachedStatus.dbjc == z4 && cachedStatus.wxau == z2 && cachedStatus.qkduk == z) {
            return;
        }
        mFirstRun = false;
        this.mWifiConnectionListener.setIsWifiConnected(z4);
        setStatus(new NetworkStatusCache(z4, z, z3, z2, i, str, cachedStatus.liflu));
        GDLog.DBGPRINTF(16, "GDNetworkStateReceiver handleMessage isConnected =" + z + " mIsWifi = " + z4 + " mIsCellular = " + z2 + " isAvailable = " + z3 + "\n");
        updateNetworkStatus();
    }

    public void initialize(ApplicationContext applicationContext, ContainerState containerState, WifiConnectionListener wifiConnectionListener) {
        this.mContext = applicationContext.getApplicationContext();
        this.mContainerState = containerState;
        this.mWifiConnectionListener = wifiConnectionListener;
        this.mSocketEventsListener = GDSocketEventsListenerHolder.getListener();
        this.mConnReceiver = new NetworkStateMonitorBroadcastReceiver();
        GDNetworkStateMonitor.initialise();
        NetworkStateMonitorSettings networkStateMonitorSettings = new NetworkStateMonitorSettings();
        networkStateMonitorSettings.executeWalledGardenCheckWhenNetworkChanged = false;
        this.activeNetworkStateMonitor = GDNetworkStateMonitor.createActiveNetworkStateMonitor(networkStateMonitorSettings);
        GDNetworkStateMonitor.addWalledGardenCheckListener(this);
        this.mContext.registerReceiver(this.mConnReceiver, new IntentFilter(this.activeNetworkStateMonitor.getIntentActionForThisInstance()));
        GDLocalBroadcastManager.getInstance().registerReceiver(this.mConnReceiver, new IntentFilter(this.activeNetworkStateMonitor.getIntentActionForThisInstance()));
        GDNetworkProtection.getInstance().setNetworkConfigProvider(GDNetNetworkProtection.getInstance());
        logCaptivePortalInfo();
    }

    public boolean isCellularConnected() {
        return NetworkStatusCache.getCachedStatus().wxau;
    }

    public boolean isNetworkAvailable() {
        return NetworkStatusCache.getCachedStatus().jwxax;
    }

    public boolean isWiFiConnected() {
        return NetworkStatusCache.getCachedStatus().dbjc;
    }

    public boolean isWifiNetworkStateReady() {
        return !NetworkStatusCache.getCachedStatus().dbjc || this.mLastDataConnectivityCheckResult != null;
    }

    @Override // com.good.gd.datasaver.GDDataSaverObserver
    public void onDataSaverEnforcedEvent(boolean z) {
        NetworkStatusCache cachedStatus = NetworkStatusCache.getCachedStatus();
        this.mWifiConnectionListener.setIsWifiConnected(cachedStatus.dbjc);
        if (cachedStatus.liflu != z) {
            GDLog.DBGPRINTF(14, "onDataSaverEnforcedEvent: update DS state to " + z + "\n");
            setStatus(new NetworkStatusCache(cachedStatus.dbjc, cachedStatus.qkduk, cachedStatus.jwxax, cachedStatus.wxau, cachedStatus.ztwf, cachedStatus.lqox, z));
            if (z) {
                GDLog.DBGPRINTF(16, "onDataSaverEnforcedEvent: clean up connections \n");
                this.mSocketEventsListener.onDataSaverEnforce();
            } else {
                GDLog.DBGPRINTF(16, "onDataSaverEnforcedEvent: wait for system to update connectivity info \n");
                long currentTimeMillis = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() < 100 + currentTimeMillis) {
                        NetworkInfo networkInfo = this.activeNetworkStateMonitor.getActiveNetworkInfo(false).getNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            GDLog.DBGPRINTF(16, "onDataSaverEnforcedEvent: network connected \n");
                            break;
                        } else {
                            try {
                                Thread.sleep(25L);
                            } catch (InterruptedException e) {
                                GDLog.DBGPRINTF(12, "onDataSaverEnforcedEvent: InterruptedException \n");
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
            handleMessage(new Message());
            return;
        }
        GDLog.DBGPRINTF(16, "onDataSaverEnforcedEvent: ignore same DS state \n");
    }

    @Override // com.good.gd.ndkproxy.push.PushConnectionStatusChangedListener
    public void onPushConnectionStatusChanged() {
        sendBroadcasts();
    }

    public void refreshNetworkStateDelayed(long j) {
        GDLog.DBGPRINTF(16, "NetworkStateMonitor.refreshNetworkState\n");
        sendMessageDelayed(new Message(), j);
    }

    public void refreshNetworkStateIfNotConnected(long j) {
        GDLog.DBGPRINTF(16, "NetworkStateMonitor.refreshNetworkStateIfNotConnected\n");
        if (!NetworkStatusCache.getCachedStatus().qkduk) {
            sendMessageDelayed(new Message(), j);
        }
    }

    public void subscribeDataSaverControl(GDDataSaverManager gDDataSaverManager) {
        gDDataSaverManager.addObserver(this);
    }
}
