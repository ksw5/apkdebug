package com.good.gd.background.detection;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.good.gd.ApplicationContext;
import com.good.gd.background.detection.rule.TrackActivityLifecycleRule;
import com.good.gd.background.detection.rule.TrackActivityRule;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDActivityInfo;
import com.good.gt.ndkproxy.util.ProcessInForegroundChecker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class BackgroundLifecycleCallbacks implements IBackgroundDetector, Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    public static long DEFAULT_TIMEOUT = 700;
    private static final int DETECTOR_LOG = 16;
    private static final String TAG = "BgDetectorCb";
    public static long TIMEOUT;
    private Set<String> activityInfoSet;
    private ActivityResumedAndPausedListener activityResumedAndPausedListener;
    private ApplicationContext applicationContext;
    private Runnable backgroundEnteringRunnable;
    private Handler backgroundHandler;
    private Handler focusHandler;
    private Runnable focusLostRunnable;
    private boolean isActivityChangingConfigurations;
    private boolean isFocused;
    private String lastFocusedActivityName;
    private AppForegroundChangedListener listener;
    private AppFocusChangedListener listenerFocusChanged;
    private List<String> notPausedActivities;
    private ProcessInForegroundChecker processInForegroundChecker;
    private TrackActivityLifecycleRule trackingRule;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BackgroundLifecycleCallbacks.this.listenerFocusChanged == null || BackgroundLifecycleCallbacks.this.lastFocusedActivityName == null) {
                return;
            }
            GDLog.DBGPRINTF(13, BackgroundLifecycleCallbacks.TAG, "Application has lost focus\n");
            BackgroundLifecycleCallbacks.this.isFocused = false;
            if (BackgroundLifecycleCallbacks.this.processInForegroundChecker.isMyProcessInUIForeground()) {
                BackgroundLifecycleCallbacks.this.listenerFocusChanged.onFocusLost();
                return;
            }
            GDLog.DBGPRINTF(13, BackgroundLifecycleCallbacks.TAG, "Tried to lost focus but entered background\n");
            if (BackgroundLifecycleCallbacks.this.listener == null) {
                return;
            }
            BackgroundLifecycleCallbacks.this.listener.onBackgroundEntering();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean isMyProcessInUIForeground = BackgroundLifecycleCallbacks.this.processInForegroundChecker.isMyProcessInUIForeground();
            if (BackgroundLifecycleCallbacks.this.activityInfoSet.size() <= 0) {
                if (isMyProcessInUIForeground) {
                    GDLog.DBGPRINTF(13, BackgroundLifecycleCallbacks.TAG, "There is no activity references, but process still in foreground\n");
                }
                if (BackgroundLifecycleCallbacks.this.listener == null) {
                    return;
                }
                BackgroundLifecycleCallbacks.this.listener.onBackgroundEntering();
                return;
            }
            GDLog.DBGPRINTF(13, BackgroundLifecycleCallbacks.TAG, "Activity started during entering background, return\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BackgroundLifecycleCallbacks() {
        this(DEFAULT_TIMEOUT);
    }

    private void checkMissingStart(GDActivityInfo gDActivityInfo) {
        String activityName = gDActivityInfo.getActivityName();
        if (!this.activityInfoSet.contains(activityName)) {
            GDLog.DBGPRINTF(13, TAG, "Missed activity start\n");
            this.activityInfoSet.add(activityName);
        }
    }

    private void checkMissingStop(GDActivityInfo gDActivityInfo) {
        String activityName = gDActivityInfo.getActivityName();
        if (this.activityInfoSet.contains(activityName)) {
            GDLog.DBGPRINTF(14, TAG, "Missed activity stop\n");
            this.activityInfoSet.remove(activityName);
            if (!this.activityInfoSet.isEmpty() || this.isActivityChangingConfigurations) {
                return;
            }
            postDelayed(this.backgroundHandler, this.backgroundEnteringRunnable, TAG, TIMEOUT);
        }
    }

    private boolean gainedFocus() {
        return this.notPausedActivities.isEmpty() && this.listenerFocusChanged != null && !this.isFocused;
    }

    private boolean isEnteringForeground() {
        return this.activityInfoSet.size() == 1 && !this.isActivityChangingConfigurations && this.listener != null;
    }

    private void postDelayed(Handler handler, Runnable runnable, String str, long j) {
        handler.postAtTime(runnable, str, SystemClock.uptimeMillis() + j);
    }

    public void delete() {
        ((Application) this.applicationContext.getApplicationContext()).unregisterActivityLifecycleCallbacks(this);
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public boolean init(TrackActivityLifecycleRule trackActivityLifecycleRule, ApplicationContext applicationContext, ProcessInForegroundChecker processInForegroundChecker) {
        this.applicationContext = applicationContext;
        this.processInForegroundChecker = processInForegroundChecker;
        try {
            ((Application) applicationContext.getApplicationContext()).registerActivityLifecycleCallbacks(this);
            this.trackingRule = trackActivityLifecycleRule;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        checkMissingStop(new GDActivityInfo(activity));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (!this.trackingRule.shouldTrackActivityLifecycle(activity)) {
            return;
        }
        ActivityResumedAndPausedListener activityResumedAndPausedListener = this.activityResumedAndPausedListener;
        if (activityResumedAndPausedListener != null) {
            activityResumedAndPausedListener.onActivityPaused();
        }
        this.notPausedActivities.remove(activity.toString());
        checkMissingStart(new GDActivityInfo(activity));
        postDelayed(this.focusHandler, this.focusLostRunnable, activity.getLocalClassName(), TIMEOUT);
        this.lastFocusedActivityName = activity.getLocalClassName();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (!this.trackingRule.shouldTrackActivityLifecycle(activity)) {
            return;
        }
        checkMissingStart(new GDActivityInfo(activity));
        this.focusHandler.removeCallbacksAndMessages(activity.getLocalClassName());
        if (gainedFocus()) {
            this.isFocused = true;
            this.listenerFocusChanged.onFocused();
        }
        this.notPausedActivities.add(activity.toString());
        ActivityResumedAndPausedListener activityResumedAndPausedListener = this.activityResumedAndPausedListener;
        if (activityResumedAndPausedListener != null) {
            activityResumedAndPausedListener.onActivityResumed();
        }
        this.lastFocusedActivityName = null;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (!this.trackingRule.shouldTrackActivityLifecycle(activity)) {
            return;
        }
        String activityName = new GDActivityInfo(activity).getActivityName();
        boolean add = this.activityInfoSet.add(activityName);
        GDLog.DBGPRINTF(16, TAG, "onActivityStarted: " + activityName + ", size: " + this.activityInfoSet.size() + "\n");
        this.backgroundHandler.removeCallbacksAndMessages(TAG);
        if (!add || !isEnteringForeground()) {
            return;
        }
        this.listener.onForegroundEntering();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (!this.trackingRule.shouldTrackActivityLifecycle(activity)) {
            return;
        }
        String activityName = new GDActivityInfo(activity).getActivityName();
        if (!this.activityInfoSet.contains(activityName)) {
            GDLog.DBGPRINTF(12, TAG, "Something went wrong, background state is not handled correctly\n");
        } else {
            this.activityInfoSet.remove(activityName);
        }
        String str = this.lastFocusedActivityName;
        if (str != null && str.equals(activity.getLocalClassName())) {
            this.lastFocusedActivityName = null;
        }
        GDLog.DBGPRINTF(16, TAG, "onActivityStopped: " + activityName + ", size: " + this.activityInfoSet.size() + "\n");
        this.focusHandler.removeCallbacksAndMessages(activity.getLocalClassName());
        this.isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (!this.activityInfoSet.isEmpty() || this.isActivityChangingConfigurations) {
            return;
        }
        postDelayed(this.backgroundHandler, this.backgroundEnteringRunnable, TAG, TIMEOUT);
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void onForegroundInitForActivity(GDActivityInfo gDActivityInfo) {
        this.activityInfoSet.add(gDActivityInfo.getActivityName());
        GDLog.DBGPRINTF(16, TAG, "onForegroundInitForActivity: " + gDActivityInfo.getActivityName() + ", size: " + this.activityInfoSet.size() + "\n");
        if (isEnteringForeground()) {
            this.listener.onForegroundEntering();
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void onNewMessage(Message message) {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        if (i == 20) {
            GDLog.DBGPRINTF(13, TAG, "Entering background because of onTrimMemory event\n");
            AppForegroundChangedListener appForegroundChangedListener = this.listener;
            if (appForegroundChangedListener == null) {
                return;
            }
            appForegroundChangedListener.onBackgroundEntering();
        }
    }

    @Override // com.good.gd.background.detection.IBackgroundDetector
    public void subscribe(AppForegroundChangedListener appForegroundChangedListener, AppFocusChangedListener appFocusChangedListener, ActivityResumedAndPausedListener activityResumedAndPausedListener) {
        this.listener = appForegroundChangedListener;
        this.listenerFocusChanged = appFocusChangedListener;
        this.activityResumedAndPausedListener = activityResumedAndPausedListener;
    }

    BackgroundLifecycleCallbacks(long j) {
        this.activityInfoSet = new HashSet();
        this.isActivityChangingConfigurations = false;
        this.notPausedActivities = new ArrayList();
        this.isFocused = false;
        TIMEOUT = j;
        this.focusHandler = new Handler();
        this.backgroundHandler = new Handler();
        this.focusLostRunnable = new hbfhc();
        this.backgroundEnteringRunnable = new yfdke();
        this.trackingRule = new TrackActivityRule();
    }
}
