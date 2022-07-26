package com.good.gd.service.activity_timer;

/* loaded from: classes.dex */
public interface ActivityTimer {
    long getTimeActivityOccurred();

    void idleTimerExceeded();

    boolean isSuspended();

    void resumeTimer();

    void setTimeoutValue(long j);

    void userActivityHasOccurred();
}
