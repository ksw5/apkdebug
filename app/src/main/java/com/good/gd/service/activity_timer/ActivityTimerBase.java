package com.good.gd.service.activity_timer;

import android.os.SystemClock;
import com.good.gd.client.GDClient;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public abstract class ActivityTimerBase implements ActivityTimer {
    protected static final int IDLETIMEOUT_CANCEL_PERIOD = -1;
    protected long _timeActivityOccurred = 0;
    protected long _timeoutMillisecs = 0;
    protected boolean _enabled = false;
    protected boolean _suspended = false;

    protected void checkAndInit() {
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public long getTimeActivityOccurred() {
        return this._timeActivityOccurred;
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public void idleTimerExceeded() {
        GDLog.DBGPRINTF(14, "ActivityTimerBase::idleTimerExceeded \n");
        GDClient.getInstance().triggerIdleTimerExceeded();
        suspendTimer();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public boolean isSuspended() {
        return this._suspended;
    }

    protected abstract void postTimer();

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public abstract void resumeTimer();

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public void setTimeoutValue(long j) {
        GDLog.DBGPRINTF(16, "ActivityTimerBase::setTimeoutValue " + j + "\n");
        checkAndInit();
        if (j == -1) {
            GDLog.DBGPRINTF(16, "ActivityTimerBase::setTimeoutValue suspendTimer as we are in NO_PASS mode\n");
            this._enabled = false;
            suspendTimer();
            return;
        }
        this._timeoutMillisecs = j * 1000;
        if (this._timeActivityOccurred == 0) {
            this._timeActivityOccurred = SystemClock.elapsedRealtime();
        }
        this._enabled = this._timeoutMillisecs > 0;
        this._suspended = false;
        postTimer();
    }

    protected abstract void suspendTimer();

    @Override // com.good.gd.service.activity_timer.ActivityTimer
    public abstract void userActivityHasOccurred();
}
