package com.good.gd.service.activity_timer;

import android.os.SystemClock;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class ActivityTimerAndroidS extends ActivityTimerBase {
    @Override // com.good.gd.service.activity_timer.ActivityTimerBase
    protected void postTimer() {
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::postTimer enabled = " + this._enabled + " suspended = " + this._suspended + "\n");
        if (!this._enabled || this._suspended) {
            return;
        }
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::postTimer time = " + this._timeoutMillisecs + "\n");
        JobScheduleHelper.scheduleActivityTimerJob(this._timeoutMillisecs);
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase, com.good.gd.service.activity_timer.ActivityTimer
    public void resumeTimer() {
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::resumeTimer post timer, timeActivityOccurred = " + SystemClock.elapsedRealtime() + "\n");
        this._suspended = false;
        this._timeActivityOccurred = SystemClock.elapsedRealtime();
        postTimer();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase
    protected void suspendTimer() {
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::suspendTimer \n");
        this._suspended = true;
        JobScheduleHelper.cancelJobActivityTimerJob();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase, com.good.gd.service.activity_timer.ActivityTimer
    public void userActivityHasOccurred() {
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::userActivityHasOccurred \n");
        if (!this._enabled || this._suspended) {
            return;
        }
        GDLog.DBGPRINTF(14, "ActivityTimerAndroidS::userActivityHasOccurred post timer, timeActivityOccurred = " + SystemClock.elapsedRealtime() + " \n");
        this._timeActivityOccurred = SystemClock.elapsedRealtime();
        postTimer();
    }
}
