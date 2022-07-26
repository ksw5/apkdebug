package com.good.gd.machines;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.good.gd.backgroundexecution.GDBackgroundExecutionControl;
import com.good.gd.backgroundexecution.GDBackgroundExecutionHelper;
import com.good.gd.context.GDContext;
import com.good.gd.datasaver.GDDataSaverManager;
import com.good.gd.machines.datasaver.GDDataSaverManagerImpl;
import com.good.gd.machines.doze.GDDozeLightMonitorControl;
import com.good.gd.machines.doze.GDDozeLightMonitorImpl;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.ndkproxy.push.PushFactory;
import com.good.gd.net.GDConnectivityManagerImpl;
import com.good.gd.net.impl.DataConnectivityCheckObserver;
import com.good.gd.net.impl.DataConnectivityCheckResult;
import com.good.gd.net.impl.DataConnectivityCheckTask;
import com.good.gd.utils.INIT_STATE;

/* loaded from: classes.dex */
public class GDBackgroundExecutionManagerImpl implements GDBackgroundExecutionControl, DataConnectivityCheckObserver {
    private static final long BACKGROUND_DOZE_ALARM_MANAGER_MINIMUM_INTERVAL = 120000;
    private static final long BACKGROUND_DOZE_WAKELOCK_INTERVAL = 30000;
    private static final String WAKE_LOCK_TAG = "BBDYNSDK:GDBackgroundExecutionManagerImpl";
    private STATE mCurrentState;
    private STATE mPreviousState;
    private boolean mIsInForeground = true;
    private long mTimeEnteringState = 0;
    private long mTimeLastAppRequest = -1;
    private long delayToRefreshNetworkState = 500;
    private int mClientSocketCount = 0;
    private boolean mStateProcessed = false;
    private PowerManager mPowerManager = (PowerManager) GDContext.getInstance().getApplicationContext().getSystemService("power");
    private PowerManager.WakeLock wakeLock = this.mPowerManager.newWakeLock(1, WAKE_LOCK_TAG);
    private GDDataSaverManager mDataSaverManager = new GDDataSaverManager(new GDDataSaverManagerImpl());
    private GDDozeLightMonitorControl mDozeLightMonitor = new GDDozeLightMonitorImpl();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum STATE {
        STATE_UNKNOWN,
        STATE_FOREGROUND,
        STATE_BACKGROUND,
        STATE_BACKGROUND_DOZE
    }

    /* loaded from: classes.dex */
    static /* synthetic */ class hbfhc {
        static final /* synthetic */ int[] dbjc;

        static {
            int[] iArr = new int[STATE.values().length];
            dbjc = iArr;
            try {
                STATE state = STATE.STATE_UNKNOWN;
                iArr[0] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                int[] iArr2 = dbjc;
                STATE state2 = STATE.STATE_BACKGROUND;
                iArr2[2] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                int[] iArr3 = dbjc;
                STATE state3 = STATE.STATE_BACKGROUND_DOZE;
                iArr3[3] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                int[] iArr4 = dbjc;
                STATE state4 = STATE.STATE_FOREGROUND;
                iArr4[1] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* loaded from: classes.dex */
    private class yfdke extends BroadcastReceiver {
        private yfdke() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean isDeviceIdleMode = GDBackgroundExecutionManagerImpl.this.mPowerManager.isDeviceIdleMode();
            boolean isIgnoringBatteryOptimizations = GDBackgroundExecutionManagerImpl.this.mPowerManager.isIgnoringBatteryOptimizations(GDContext.getInstance().getApplicationContext().getPackageName());
            GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager: Idle Mode Receiver isDeviceIdle = " + isDeviceIdleMode + ", isMyPackageExempt = " + isIgnoringBatteryOptimizations + "\n");
            if (isDeviceIdleMode && !isIgnoringBatteryOptimizations) {
                GDBackgroundExecutionManagerImpl.this.setCurrentState(STATE.STATE_BACKGROUND_DOZE);
            } else if (isDeviceIdleMode && isIgnoringBatteryOptimizations) {
                GDBackgroundExecutionManagerImpl.this.setCurrentState(STATE.STATE_BACKGROUND);
            } else if (!isDeviceIdleMode) {
                NetworkStateMonitor.getInstance().refreshNetworkStateDelayed(GDBackgroundExecutionManagerImpl.this.delayToRefreshNetworkState);
                if (GDBackgroundExecutionManagerImpl.this.mIsInForeground) {
                    GDBackgroundExecutionManagerImpl.this.setCurrentState(STATE.STATE_FOREGROUND);
                } else {
                    GDBackgroundExecutionManagerImpl.this.setCurrentState(STATE.STATE_BACKGROUND);
                }
            }
            GDBackgroundExecutionManagerImpl.this.processState(false);
        }

        /* synthetic */ yfdke(GDBackgroundExecutionManagerImpl gDBackgroundExecutionManagerImpl, hbfhc hbfhcVar) {
            this();
        }
    }

    public GDBackgroundExecutionManagerImpl(INIT_STATE init_state) {
        STATE state = STATE.STATE_UNKNOWN;
        this.mCurrentState = state;
        this.mPreviousState = state;
        yfdke yfdkeVar = new yfdke(this, null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        GDContext.getInstance().getApplicationContext().registerReceiver(yfdkeVar, intentFilter);
        NetworkStateMonitor.getInstance().subscribeDataSaverControl(this.mDataSaverManager);
        if (init_state != INIT_STATE.STATE_BACKGROUND || !checkIsInDozeMode()) {
            return;
        }
        setCurrentState(STATE.STATE_BACKGROUND_DOZE);
        processState(true);
    }

    private void acquireWakeLock() {
        synchronized (this.wakeLock) {
            if (!this.wakeLock.isHeld()) {
                GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager: attempt to acquire wake lock\n");
                this.wakeLock.acquire(BACKGROUND_DOZE_WAKELOCK_INTERVAL);
                GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager: acquired wake lock\n");
            }
        }
    }

    private boolean checkIsInDozeMode() {
        boolean isDeviceIdleMode = this.mPowerManager.isDeviceIdleMode();
        boolean isIgnoringBatteryOptimizations = this.mPowerManager.isIgnoringBatteryOptimizations(GDContext.getInstance().getApplicationContext().getPackageName());
        GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager: checkIsInDozeMode isDeviceIdle = " + isDeviceIdleMode + ", isMyPackageExempt = " + isIgnoringBatteryOptimizations + "\n");
        return isDeviceIdleMode && !isIgnoringBatteryOptimizations;
    }

    private void configureGDNotInDoze() {
        releaseWakeLock();
        resetClientSocketCount();
        if (this.mPreviousState == STATE.STATE_BACKGROUND_DOZE) {
            GDConnectivityManagerImpl.drainSocketPool();
        }
        connectPushConnection();
        this.mTimeLastAppRequest = -1L;
        NativeExecutionHandler.getInstance();
        NativeExecutionHandler.setAlarmManagerMinimumInterval(NativeExecutionHandler.DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL);
    }

    private void connectPushConnection() {
        GDBackgroundExecutionHelper.connectPushConnection();
    }

    private synchronized void decrementClientSocketCount() {
        this.mClientSocketCount--;
    }

    private void disconnectPushConnection() {
        GDBackgroundExecutionHelper.disconnectPushConnection();
    }

    private synchronized int getClientSocketCount() {
        return this.mClientSocketCount;
    }

    private synchronized STATE getCurrentState() {
        GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager getState = " + helperStateToString(this.mCurrentState) + "\n");
        return this.mCurrentState;
    }

    private String helperStateToString(STATE state) {
        switch (state.ordinal()) {
            case 0:
                return "STATE_UNKNOWN";
            case 1:
                return "STATE_FOREGROUND";
            case 2:
                return "STATE_BACKGROUND";
            case 3:
                return "STATE_BACKGROUND_DOZE";
            default:
                return "null";
        }
    }

    private synchronized void incrementClientSocketCount() {
        this.mClientSocketCount++;
    }

    private boolean isPushConnectionConnected() {
        return GDBackgroundExecutionHelper.isPushConnectionConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void processState(boolean z) {
        int ordinal = getCurrentState().ordinal();
        if (ordinal != 1) {
            if (ordinal != 2) {
                if (ordinal == 3) {
                    if (NativeExecutionHandler.getInstance() != null) {
                        NativeExecutionHandler.NativeExecutionState nativeExecutionState = NativeExecutionHandler.getInstance().getNativeExecutionState();
                        GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager currentTime =" + nativeExecutionState.currentTime + " lastProcessTime = " + nativeExecutionState.lastProcessTime + " lastAlarmTime = " + nativeExecutionState.lastAlarmTime + " timeBehind = " + nativeExecutionState.timeBehindDesiredAlarm + " Execution Size = " + nativeExecutionState.executionQueueSize + " Time since last request = " + this.mTimeLastAppRequest + " aIsAppRequest = " + z + "\n");
                    }
                    if (z) {
                        acquireWakeLock();
                        NativeExecutionHandler.setAlarmManagerMinimumInterval(BACKGROUND_DOZE_ALARM_MANAGER_MINIMUM_INTERVAL);
                        DataConnectivityCheckTask.submit(this);
                        if (isPushConnectionConnected()) {
                            PushFactory.getPushConnection().testConnectionIntegrity();
                        } else {
                            connectPushConnection();
                        }
                        this.mTimeLastAppRequest = SystemClock.elapsedRealtime();
                    } else if (!this.mStateProcessed) {
                        GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager processState STATE_BACKGROUND_DOZE\n");
                        this.mStateProcessed = true;
                        disconnectPushConnection();
                        if (this.mPreviousState != STATE.STATE_BACKGROUND_DOZE) {
                            GDConnectivityManagerImpl.drainSocketPool();
                        }
                    }
                    this.mDozeLightMonitor.onDozeModeChangedEvent(GDDozeLightMonitorControl.Mode.FULL_ENABLED);
                }
            } else if (!this.mStateProcessed) {
                this.mStateProcessed = true;
                GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager processState STATE_BACKGROUND\n");
                if (checkIsInDozeMode()) {
                    setCurrentState(STATE.STATE_BACKGROUND_DOZE);
                    processState(z);
                } else {
                    configureGDNotInDoze();
                    this.mDozeLightMonitor.onDozeModeChangedEvent(GDDozeLightMonitorControl.Mode.DISABLED);
                }
            }
        } else if (!this.mStateProcessed) {
            GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager processState STATE_FOREGROUND\n");
            this.mStateProcessed = true;
            configureGDNotInDoze();
            this.mDozeLightMonitor.onDozeModeChangedEvent(GDDozeLightMonitorControl.Mode.DISABLED);
        }
    }

    private void releaseWakeLock() {
        synchronized (this.wakeLock) {
            if (this.wakeLock.isHeld()) {
                GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager: attempt to release wake lock\n");
                this.wakeLock.release();
                GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager: released wake lock\n");
            }
        }
    }

    private synchronized void resetClientSocketCount() {
        this.mClientSocketCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setCurrentState(STATE state) {
        this.mTimeEnteringState = SystemClock.elapsedRealtime();
        GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager setState previous State = " + helperStateToString(this.mCurrentState) + " newState = " + helperStateToString(state) + " TimeEnteringState = " + this.mTimeEnteringState + " StateProcessed = " + this.mStateProcessed + "\n");
        STATE state2 = this.mCurrentState;
        if (state != state2) {
            this.mPreviousState = state2;
            this.mStateProcessed = false;
            this.mCurrentState = state;
        }
    }

    @Override // com.good.gd.net.impl.DataConnectivityCheckObserver
    public void dataConnectivityCheckResult(DataConnectivityCheckResult dataConnectivityCheckResult) {
        GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager dataConnectivityCheck result = " + dataConnectivityCheckResult.isAvailable() + "\n");
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyClosingGDSocketEvent() {
        decrementClientSocketCount();
        if (getCurrentState() == STATE.STATE_BACKGROUND_DOZE && getClientSocketCount() == 0) {
            GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager notifyClosingGDSocketEvent release LOCK\n");
            releaseWakeLock();
        }
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyCreatingGDSocketEvent() {
        if (getCurrentState() == STATE.STATE_BACKGROUND_DOZE) {
            GDLog.DBGPRINTF(14, "GDBackgroundExecutionManager notifyCreatingGDSocket\n");
        }
        incrementClientSocketCount();
        processState(true);
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyGDForegroundEvent(boolean z) {
        this.mIsInForeground = z;
        GDLog.DBGPRINTF(16, "GDBackgroundExecutionManager notifyGDForegroundEvent isInForeground = " + z + "\n");
        this.mDataSaverManager.onGDForegroundEvent(z);
        this.mDozeLightMonitor.onGDForegroundEvent(z);
        if (z) {
            setCurrentState(STATE.STATE_FOREGROUND);
            processState(false);
            return;
        }
        setCurrentState(STATE.STATE_BACKGROUND);
    }
}
