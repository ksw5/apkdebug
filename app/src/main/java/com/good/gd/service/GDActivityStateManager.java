package com.good.gd.service;

import android.os.Handler;
import com.good.gd.background.detection.AppForegroundChangedListener;
import com.good.gd.background.detection.BackgroundState;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.dlp_util.GDCustomROMDLPUtil;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.security.malware.AntiDebuggingChecker;
import com.good.gd.security.malware.DeviceIntegrityChecker;
import com.good.gd.security.malware.SystemPropertiesChecker;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.INIT_STATE;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.IccManagerState;
import com.good.gt.icc.IccManagerStateListener;
import com.good.gt.ndkproxy.util.GTUtils;

/* loaded from: classes.dex */
public class GDActivityStateManager implements IccManagerStateListener, AppForegroundChangedListener {
    private static final int WAIT_RESPONSE_DELAY = 2000;
    private static GDActivityStateManager _instance;
    private Handler _backgroundHandler;
    private Runnable _delayedForegroundRunnable;
    private Runnable _waitResponseRunnable;
    private ICCController mIccController;
    private boolean _initialised = false;
    private BackgroundState _clientState = BackgroundState.UNKNOWN;
    private boolean _pendingEnteringForeground = false;
    private boolean _waitingResponseState = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDActivityStateManager.this.inBackground();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean isAuthorized = GDClient.getInstance().isAuthorized();
            GDLog.DBGPRINTF(14, "GDActivityStateManager: delayedEnterForeground\n");
            GDActivityStateManager.this.applicationEnteringForeground(isAuthorized);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDActivityStateManager.this.delayedEnterForeground();
        }
    }

    private GDActivityStateManager(INIT_STATE init_state) {
        checkAndInit(init_state);
        ICCController iccController = GDInit.getIccController();
        this.mIccController = iccController;
        iccController.addIccManagerStateListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applicationEnteringForeground(boolean z) {
        AntiDebuggingChecker.getInstance().applicationEnteringForeground();
        DeviceIntegrityChecker.getInstance().applicationEnteringForeground();
        SystemPropertiesChecker.getInstance().applicationEnteringForeground();
        GDLibraryUI.getInstance().applicationEnteringForeground(z);
    }

    private void checkAndInit(INIT_STATE init_state) {
        if (this._initialised || GDContext.getInstance().getApplicationContext() == null) {
            return;
        }
        GDLog.DBGPRINTF(16, "GDActivityStateManager: checkInit initialzing()\n");
        this._backgroundHandler = new Handler();
        this._delayedForegroundRunnable = new hbfhc();
        this._waitResponseRunnable = new yfdke();
        if (init_state == INIT_STATE.STATE_BACKGROUND) {
            this._clientState = BackgroundState.BACKGROUND;
        } else {
            this._backgroundHandler.postDelayed(new fdyxd(), 500L);
        }
        this._initialised = true;
    }

    public static synchronized GDActivityStateManager createInstance(INIT_STATE init_state) {
        GDActivityStateManager gDActivityStateManager;
        synchronized (GDActivityStateManager.class) {
            if (_instance == null) {
                _instance = new GDActivityStateManager(init_state);
            }
            gDActivityStateManager = _instance;
        }
        return gDActivityStateManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delayedEnterForeground() {
        if (this._initialised) {
            this._backgroundHandler.post(this._delayedForegroundRunnable);
        }
    }

    private void endWaitingResponseState() {
        this._waitingResponseState = false;
        GDLog.DBGPRINTF(14, "GDActivityStateManager: endWaitingResponseState\n");
        if (this._initialised) {
            this._backgroundHandler.removeCallbacks(this._waitResponseRunnable);
        }
    }

    private void enterBackground() {
        GDLog.DBGPRINTF(16, "GDActivityStateManager: enterBackground()\n");
        this._clientState = BackgroundState.BACKGROUND;
        this._pendingEnteringForeground = false;
        endWaitingResponseState();
        GDLibraryUI.getInstance().applicationEnteringBackground();
        AntiDebuggingChecker.getInstance().applicationEnteringBackground();
        DeviceIntegrityChecker.getInstance().applicationEnteringBackground();
        SystemPropertiesChecker.getInstance().applicationEnteringBackground();
        UniversalActivityController.getInstance().enteringBackground();
    }

    private void enterWaitingResponseState() {
        this._waitingResponseState = true;
        GDLog.DBGPRINTF(14, "GDActivityStateManager: enterWaitingResponseState\n");
        checkAndInit(INIT_STATE.STATE_UNKNOWN);
        if (this._initialised) {
            this._backgroundHandler.postDelayed(this._waitResponseRunnable, NativeExecutionHandler.DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL);
        }
    }

    public static synchronized GDActivityStateManager getInstance() {
        synchronized (GDActivityStateManager.class) {
            GDActivityStateManager gDActivityStateManager = _instance;
            if (gDActivityStateManager != null) {
                return gDActivityStateManager;
            }
            GDLog.DBGPRINTF(12, "GDActivityStateManager::getInstance is NULL\n");
            return null;
        }
    }

    private void uiResumed() {
        boolean z;
        boolean z2;
        synchronized (this) {
            GDLog.DBGPRINTF(14, "GDActivityStateManager: uiResumed: _clientIsInBackground=" + this._clientState + "\n");
            z = true;
            z2 = false;
            if (this._clientState == BackgroundState.FOREGROUND) {
                z = false;
            } else {
                this._clientState = BackgroundState.FOREGROUND;
                GDActivityTimerProvider.getInstance().getActivityTimer().userActivityHasOccurred();
                boolean isAuthorized = GDClient.getInstance().isAuthorized();
                IccManagerState iccManagerState = ICCControllerFactory.getICCController(null).getIccManagerState();
                GDLog.DBGPRINTF(14, "GDActivityStateManager: uiResumed: Authed = " + isAuthorized + ", state = " + iccManagerState.toString() + "\n");
                switch (iccManagerState.getState()) {
                    case 0:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        z2 = isAuthorized;
                        break;
                    case 1:
                    case 2:
                        this._pendingEnteringForeground = true;
                        z = false;
                        z2 = isAuthorized;
                        break;
                    case 3:
                    default:
                        z = false;
                        z2 = isAuthorized;
                        break;
                    case 4:
                        enterWaitingResponseState();
                        z = false;
                        z2 = isAuthorized;
                        break;
                }
            }
        }
        if (z) {
            applicationEnteringForeground(z2);
        }
        GDCustomROMDLPUtil.clearClipBoardIfRequired();
    }

    public boolean foregroundStateIsUnknown() {
        return this._clientState == BackgroundState.UNKNOWN;
    }

    public boolean inBackground() {
        if (this._clientState == BackgroundState.BACKGROUND) {
            return true;
        }
        if (!GTUtils.isMyProcessInUIForeground()) {
            enterBackground();
        }
        return this._clientState == BackgroundState.BACKGROUND;
    }

    public boolean inForeground() {
        return this._clientState == BackgroundState.FOREGROUND;
    }

    public boolean isIccManagerStateIdle() {
        IccManagerState iccManagerState = this.mIccController.getIccManagerState();
        return iccManagerState.getState() == 0 || iccManagerState.getState() == 5 || iccManagerState.getState() == 4 || iccManagerState.getState() == 1 || iccManagerState.getState() == 9;
    }

    public void onActivityPaused() {
    }

    public void onActivityResumed() {
        this._clientState = BackgroundState.FOREGROUND;
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onBackgroundEntering() {
        enterBackground();
    }

    public void onFocusGained() {
        uiResumed();
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onForegroundEntering() {
        uiResumed();
    }

    @Override // com.good.gt.icc.IccManagerStateListener
    public void onStateUpdate(IccManagerState iccManagerState) {
        GDLog.DBGPRINTF(14, "GDActivityStateManager: onStateUpdate - new State =" + iccManagerState.toString() + "\n");
        switch (iccManagerState.getState()) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                if (!this._pendingEnteringForeground) {
                    return;
                }
                this._pendingEnteringForeground = false;
                delayedEnterForeground();
                return;
            case 1:
            case 2:
                if (!this._waitingResponseState) {
                    return;
                }
                endWaitingResponseState();
                this._pendingEnteringForeground = true;
                return;
            case 3:
            case 4:
            default:
                return;
        }
    }
}
