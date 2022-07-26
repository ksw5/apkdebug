package com.good.gd.service.activity_timer;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class JobScheduleHelper {
    private static final int ACTIVITY_TIMER_JOB_ID = 1;

    public static void cancelJobActivityTimerJob() {
        GDLog.DBGPRINTF(14, "JobScheduleHelper::cancelJobActivityTimerJob \n");
        ((JobScheduler) GTBaseContext.getInstance().getApplicationContext().getSystemService("jobscheduler")).cancel(1);
    }

    private static JobInfo getJobInfo(long j) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(GTBaseContext.getInstance().getApplicationContext(), ActivityTimerService.class));
        builder.setMinimumLatency(j);
        return builder.build();
    }

    public static void scheduleActivityTimerJob(long j) {
        GDLog.DBGPRINTF(14, "JobScheduleHelper::scheduleActivityTimerJob \n");
        int schedule = ((JobScheduler) GTBaseContext.getInstance().getApplicationContext().getSystemService("jobscheduler")).schedule(getJobInfo(j));
        if (schedule == 0) {
            GDLog.DBGPRINTF(12, "JobScheduleHelper::scheduleActivityTimerJob failed to schedule a job \n");
        } else if (schedule != 1) {
        } else {
            GDLog.DBGPRINTF(14, "JobScheduleHelper::scheduleActivityTimerJob successfully scheduled a job \n");
        }
    }
}
