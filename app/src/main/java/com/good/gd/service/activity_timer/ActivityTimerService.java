package com.good.gd.service.activity_timer;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.SystemClock;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;
import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.service.GDActivityTimerProvider;

/* loaded from: classes.dex */
public class ActivityTimerService extends JobService {
    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        GDLog.DBGPRINTF(16, "ActivityTimerService::onStartJob \n");
        int currentAuthType = GDAuthManager.getCurrentAuthType();
        if (currentAuthType == 0 || currentAuthType == 1 || currentAuthType == 5) {
            GDLog.DBGPRINTF(16, "ActivityTimerService::onStartJob idle timeout received in " + currentAuthType + " state\n");
            return false;
        } else if (GDLibraryUI.getInstance().isMigrationInProgress()) {
            GDLog.DBGPRINTF(16, "ActivityTimerService::onStartJob idle timeout received during migration, suspending the timer\n");
            return false;
        } else if (GDActivitySupport.isLocked()) {
            GDLog.DBGPRINTF(13, "ActivityTimerService::onStartJob idle timeout received in locked state\n");
            return false;
        } else {
            ActivityTimer activityTimer = GDActivityTimerProvider.getInstance().getActivityTimer();
            GDLog.DBGPRINTF(13, "ActivityTimerService::onStartJob idle timeout interval = " + (SystemClock.elapsedRealtime() - activityTimer.getTimeActivityOccurred()) + "\n");
            if (!activityTimer.isSuspended()) {
                GDLog.DBGPRINTF(13, "ActivityTimerService::onStartJob exceeded idle timeout \n");
                activityTimer.idleTimerExceeded();
            }
            return false;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        GDLog.DBGPRINTF(16, "ActivityTimerService::onStopJob \n");
        return false;
    }
}
