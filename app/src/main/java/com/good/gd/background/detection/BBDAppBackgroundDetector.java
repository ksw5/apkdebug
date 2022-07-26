package com.good.gd.background.detection;

import android.os.Message;
import com.good.gd.ApplicationContext;
import com.good.gd.background.detection.event.EventEnqueueStrategy;
import com.good.gd.background.detection.event.EventQueueStrategy;
import com.good.gd.background.detection.event.SkipEventStrategy;
import com.good.gd.background.detection.rule.TrackActivityLifecycleRule;
import com.good.gd.background.detection.rule.TrackActivityRule;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDActivityInfo;
import com.good.gt.ndkproxy.util.GDProcessInForegroundChecker;
import com.good.gt.ndkproxy.util.ProcessInForegroundChecker;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BBDAppBackgroundDetector implements AppForegroundChangedListener, AppFocusChangedListener, ActivityResumedAndPausedListener {
    public static final String TAG = "BgDetector";
    private static ApplicationContext applicationContext;
    private static BBDAppBackgroundDetector instance;
    private IBackgroundDetector impl;
    private boolean onceCall = true;
    private EventQueueStrategy pendingEventsStrategy = new EventEnqueueStrategy();
    private HashSet<AppForegroundChangedListener> fgListeners = new HashSet<>();
    private HashSet<AppFocusChangedListener> focusListeners = new HashSet<>();
    private HashSet<ActivityResumedAndPausedListener> rpListeners = new HashSet<>();

    private BBDAppBackgroundDetector() {
    }

    public static synchronized BBDAppBackgroundDetector createInstance(TrackActivityLifecycleRule trackActivityLifecycleRule) {
        BBDAppBackgroundDetector bBDAppBackgroundDetector;
        synchronized (BBDAppBackgroundDetector.class) {
            if (instance == null) {
                BBDAppBackgroundDetector bBDAppBackgroundDetector2 = new BBDAppBackgroundDetector();
                instance = bBDAppBackgroundDetector2;
                bBDAppBackgroundDetector2.initImpl(trackActivityLifecycleRule, applicationContext, new GDProcessInForegroundChecker());
            }
            bBDAppBackgroundDetector = instance;
        }
        return bBDAppBackgroundDetector;
    }

    public static synchronized BBDAppBackgroundDetector getInstance() {
        BBDAppBackgroundDetector bBDAppBackgroundDetector;
        synchronized (BBDAppBackgroundDetector.class) {
            if (instance == null) {
                BBDAppBackgroundDetector bBDAppBackgroundDetector2 = new BBDAppBackgroundDetector();
                instance = bBDAppBackgroundDetector2;
                bBDAppBackgroundDetector2.initImpl(new TrackActivityRule(), applicationContext, new GDProcessInForegroundChecker());
            }
            bBDAppBackgroundDetector = instance;
        }
        return bBDAppBackgroundDetector;
    }

    private void initImpl(TrackActivityLifecycleRule trackActivityLifecycleRule, ApplicationContext applicationContext2, ProcessInForegroundChecker processInForegroundChecker) {
        BackgroundLifecycleCallbacks backgroundLifecycleCallbacks = new BackgroundLifecycleCallbacks();
        this.impl = backgroundLifecycleCallbacks;
        if (!backgroundLifecycleCallbacks.init(trackActivityLifecycleRule, applicationContext2, processInForegroundChecker)) {
            ResumePauseOldSolution resumePauseOldSolution = new ResumePauseOldSolution();
            this.impl = resumePauseOldSolution;
            resumePauseOldSolution.init(trackActivityLifecycleRule, applicationContext2, processInForegroundChecker);
        }
        this.impl.subscribe(this, this, this);
    }

    public static synchronized void initializeBackgroundDetector(ApplicationContext applicationContext2) {
        synchronized (BBDAppBackgroundDetector.class) {
            applicationContext = applicationContext2;
        }
    }

    private synchronized void notifyBackground() {
        Iterator<AppForegroundChangedListener> it = this.fgListeners.iterator();
        while (it.hasNext()) {
            it.next().onBackgroundEntering();
        }
    }

    private void notifyCorrespondingListeners(Event event) {
        switch (event.ordinal()) {
            case 0:
                notifyForeground();
                return;
            case 1:
                notifyBackground();
                return;
            case 2:
                notifyFocusGained();
                return;
            case 3:
                notifyFocusLost();
                return;
            case 4:
                notifyPaused();
                return;
            case 5:
                notifyResumed();
                return;
            default:
                return;
        }
    }

    private synchronized void notifyFocusGained() {
        Iterator<AppFocusChangedListener> it = this.focusListeners.iterator();
        while (it.hasNext()) {
            it.next().onFocused();
        }
    }

    private synchronized void notifyFocusLost() {
        Iterator<AppFocusChangedListener> it = this.focusListeners.iterator();
        while (it.hasNext()) {
            it.next().onFocusLost();
        }
    }

    private synchronized void notifyForeground() {
        Iterator<AppForegroundChangedListener> it = this.fgListeners.iterator();
        while (it.hasNext()) {
            it.next().onForegroundEntering();
        }
    }

    private synchronized void notifyPaused() {
        Iterator<ActivityResumedAndPausedListener> it = this.rpListeners.iterator();
        while (it.hasNext()) {
            it.next().onActivityPaused();
        }
    }

    private synchronized void notifyResumed() {
        Iterator<ActivityResumedAndPausedListener> it = this.rpListeners.iterator();
        while (it.hasNext()) {
            it.next().onActivityResumed();
        }
    }

    private void tryNotifyEvent(Event event) {
        this.pendingEventsStrategy.enqueueEvent(event);
        notifyCorrespondingListeners(event);
    }

    public synchronized void addActivityResumedAndPausedListener(ActivityResumedAndPausedListener activityResumedAndPausedListener) {
        this.rpListeners.add(activityResumedAndPausedListener);
    }

    public synchronized void addAppFocusChangedListener(AppFocusChangedListener appFocusChangedListener) {
        this.focusListeners.add(appFocusChangedListener);
    }

    public synchronized void addForegroundChangeListener(AppForegroundChangedListener appForegroundChangedListener) {
        this.fgListeners.add(appForegroundChangedListener);
    }

    public void handleClientMessage(Message message) {
        this.impl.onNewMessage(message);
    }

    public void notifyForegroundInitForActivity(GDActivityInfo gDActivityInfo) {
        if (this.onceCall) {
            this.impl.onForegroundInitForActivity(gDActivityInfo);
            this.onceCall = false;
        }
    }

    @Override // com.good.gd.background.detection.ActivityResumedAndPausedListener
    public void onActivityPaused() {
        tryNotifyEvent(Event.PAUSED);
    }

    @Override // com.good.gd.background.detection.ActivityResumedAndPausedListener
    public void onActivityResumed() {
        tryNotifyEvent(Event.RESUMED);
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onBackgroundEntering() {
        GDLog.DBGPRINTF(16, TAG, "onBackgroundEntering\n");
        tryNotifyEvent(Event.ENTERING_BACKGROUND);
    }

    @Override // com.good.gd.background.detection.AppFocusChangedListener
    public void onFocusLost() {
        GDLog.DBGPRINTF(16, TAG, "onFocusLost\n");
        tryNotifyEvent(Event.FOCUS_LOST);
    }

    @Override // com.good.gd.background.detection.AppFocusChangedListener
    public void onFocused() {
        GDLog.DBGPRINTF(16, TAG, "onFocused\n");
        tryNotifyEvent(Event.FOCUS_GAINED);
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onForegroundEntering() {
        GDLog.DBGPRINTF(16, TAG, "onForegroundEntering\n");
        tryNotifyEvent(Event.ENTERING_FOREGROUND);
    }

    public synchronized void removeActivityResumedAndPausedListener(ActivityResumedAndPausedListener activityResumedAndPausedListener) {
        this.rpListeners.remove(activityResumedAndPausedListener);
    }

    public synchronized void removeAppFocusChangedListener(AppFocusChangedListener appFocusChangedListener) {
        this.focusListeners.remove(appFocusChangedListener);
    }

    public void removeAppStateListener(AppStateListener appStateListener) {
        removeActivityResumedAndPausedListener(appStateListener);
        removeAppFocusChangedListener(appStateListener);
        removeForegroundChangeListener(appStateListener);
    }

    public synchronized void removeForegroundChangeListener(AppForegroundChangedListener appForegroundChangedListener) {
        this.fgListeners.remove(appForegroundChangedListener);
    }

    public void setAppStateListener(AppStateListener appStateListener) {
        addForegroundChangeListener(appStateListener);
        addAppFocusChangedListener(appStateListener);
        addActivityResumedAndPausedListener(appStateListener);
        Event lastEvent = this.pendingEventsStrategy.getLastEvent();
        if (lastEvent != null) {
            GDLog.DBGPRINTF(14, TAG, "setAppStateListener " + lastEvent + "\n");
            if (this.pendingEventsStrategy.hasPendingForegroundEvent()) {
                appStateListener.onForegroundEntering();
                int ordinal = lastEvent.ordinal();
                if (ordinal == 1) {
                    appStateListener.onBackgroundEntering();
                } else if (ordinal == 2) {
                    appStateListener.onFocused();
                } else if (ordinal == 3) {
                    appStateListener.onFocusLost();
                } else if (ordinal != 5) {
                    GDLog.DBGPRINTF(13, TAG, "setAppStateListener event skipped " + lastEvent + "\n");
                } else {
                    appStateListener.onActivityResumed();
                }
            }
        }
        this.pendingEventsStrategy = new SkipEventStrategy();
    }
}
