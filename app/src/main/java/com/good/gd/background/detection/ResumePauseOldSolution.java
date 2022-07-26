package com.good.gd.background.detection;

import android.os.Handler;
import android.os.Message;
import com.good.gd.ApplicationContext;
import com.good.gd.background.detection.rule.TrackActivityLifecycleRule;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.utils.GDActivityInfo;
import com.good.gt.ndkproxy.util.ProcessInForegroundChecker;

/* loaded from: classes.dex */
public class ResumePauseOldSolution implements IBackgroundDetector {
    private static final int PAUSE_BACKGROUND_DELAY = 2000;
    private static final int PAUSE_TIMER_VARIANCE = 100;
    private Handler _backgroundHandler;
    private Runnable _backgroundRunnable;
    private boolean _clientIsInBackground;
    private boolean _initialised = false;
    private long _lastPauseRxTime = 0;
    private long _lastResumeRxTime = 0;
    private boolean _shouldNotifyForeground;
    private AppForegroundChangedListener listener;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ResumePauseOldSolution.this.checkBackground();
        }
    }

    private void cancelBackgroundCheck() {
        if (this._initialised) {
            this._backgroundHandler.removeCallbacks(this._backgroundRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBackground() {
        if (this._clientIsInBackground || this._lastPauseRxTime <= this._lastResumeRxTime || System.currentTimeMillis() < (this._lastPauseRxTime + NativeExecutionHandler.DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL) - 100) {
            GDLog.DBGPRINTF(12, BBDAppBackgroundDetector.TAG, "checkBackground Error clientIsInBackground= " + this._clientIsInBackground + " lastPauseRxTime = " + this._lastPauseRxTime + " lastResumeRxTime =" + this._lastResumeRxTime + "\n");
            return;
        }
        GDLog.DBGPRINTF(16, BBDAppBackgroundDetector.TAG, "checkBackground, calling enterBackground()\n");
        this._clientIsInBackground = true;
        this.listener.onBackgroundEntering();
    }

    private void scheduleBackgroundCheck() {
        if (this._initialised) {
            this._backgroundHandler.postDelayed(this._backgroundRunnable, NativeExecutionHandler.DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL);
        }
    }

    private void uiPaused() {
        this._lastPauseRxTime = System.currentTimeMillis();
        GDLog.DBGPRINTF(16, BBDAppBackgroundDetector.TAG, "uiPaused last paused time = " + this._lastPauseRxTime + ", ClientIsInBackground = " + this._clientIsInBackground + "\n");
        if (!this._clientIsInBackground) {
            scheduleBackgroundCheck();
        }
    }

    private void uiResumed() {
        this._lastResumeRxTime = System.currentTimeMillis();
        if (this._clientIsInBackground) {
            this._clientIsInBackground = false;
            this._shouldNotifyForeground = true;
        } else {
            cancelBackgroundCheck();
        }
        AppForegroundChangedListener appForegroundChangedListener = this.listener;
        if (appForegroundChangedListener == null || !this._shouldNotifyForeground) {
            return;
        }
        appForegroundChangedListener.onForegroundEntering();
        this._shouldNotifyForeground = false;
    }

    public void delete() {
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public boolean init(TrackActivityLifecycleRule trackActivityLifecycleRule, ApplicationContext applicationContext, ProcessInForegroundChecker processInForegroundChecker) {
        this._backgroundHandler = new Handler();
        this._backgroundRunnable = new hbfhc();
        boolean z = !processInForegroundChecker.isMyProcessInUIForeground();
        this._clientIsInBackground = z;
        this._initialised = true;
        this._shouldNotifyForeground = !z;
        return true;
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void onForegroundInitForActivity(GDActivityInfo gDActivityInfo) {
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void onNewMessage(Message message) {
        switch (message.what) {
            case 1015:
            case 1017:
                uiPaused();
                return;
            case 1016:
                uiResumed();
                return;
            case 1018:
                uiResumed();
                return;
            default:
                return;
        }
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void subscribe(AppForegroundChangedListener appForegroundChangedListener, AppFocusChangedListener appFocusChangedListener, ActivityResumedAndPausedListener activityResumedAndPausedListener) {
        this.listener = appForegroundChangedListener;
    }
}
