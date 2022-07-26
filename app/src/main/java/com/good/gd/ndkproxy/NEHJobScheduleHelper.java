package com.good.gd.ndkproxy;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class NEHJobScheduleHelper {
    private static final int SERVICE_JOB_ID = 100;

    public static void cancelJob() {
        ((JobScheduler) GTBaseContext.getInstance().getApplicationContext().getSystemService("jobscheduler")).cancel(100);
        GDLog.DBGPRINTF(16, "NEHJobScheduleHelper: job is canceled \n");
    }

    private static JobInfo getJobInfo(long j, long j2) {
        Context applicationContext = GTBaseContext.getInstance().getApplicationContext();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong(ExecutionService.EXTRA_TASK_ID, j2);
        JobInfo.Builder builder = new JobInfo.Builder(100, new ComponentName(applicationContext, ExecutionService.class));
        builder.setMinimumLatency(j);
        builder.setExtras(persistableBundle);
        return builder.build();
    }

    public static void scheduleJob(long j, long j2) {
        GDLog.DBGPRINTF(16, "NEHJobScheduleHelper: schedule a job for task: " + j2 + " \n");
        int schedule = ((JobScheduler) GTBaseContext.getInstance().getApplicationContext().getSystemService("jobscheduler")).schedule(getJobInfo(j, j2));
        if (schedule == 0) {
            GDLog.DBGPRINTF(12, "NEHJobScheduleHelper: failed to schedule a job \n");
        } else if (schedule != 1) {
        } else {
            GDLog.DBGPRINTF(16, "NEHJobScheduleHelper: successfully scheduled a job \n");
        }
    }
}
