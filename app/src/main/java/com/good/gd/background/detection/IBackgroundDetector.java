package com.good.gd.background.detection;

import android.os.Message;
import com.good.gd.ApplicationContext;
import com.good.gd.background.detection.rule.TrackActivityLifecycleRule;
import com.good.gd.utils.GDActivityInfo;
import com.good.gt.ndkproxy.util.ProcessInForegroundChecker;

/* loaded from: classes.dex */
public interface IBackgroundDetector {
    boolean init(TrackActivityLifecycleRule trackActivityLifecycleRule, ApplicationContext applicationContext, ProcessInForegroundChecker processInForegroundChecker);

    void onForegroundInitForActivity(GDActivityInfo gDActivityInfo);

    void onNewMessage(Message message);

    void subscribe(AppForegroundChangedListener appForegroundChangedListener, AppFocusChangedListener appFocusChangedListener, ActivityResumedAndPausedListener activityResumedAndPausedListener);
}
