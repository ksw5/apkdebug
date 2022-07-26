package com.good.gd.ndkproxy;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes.dex */
public class ExecutionService extends JobService {
    public static final String EXTRA_TASK_ID = "extra_task_id";
    private final int RETRY_JOB_INTERVAL_MILLIS = 1000;
    private Handler handler;
    private HandlerThread handlerThread;

    /* loaded from: classes.dex */
    public interface FinishedCallback {
        void onJobFinished();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        final /* synthetic */ JobParameters dbjc;

        hbfhc(JobParameters jobParameters) {
            this.dbjc = jobParameters;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(14, "ExecutionService::run() retry job execution \n");
            ExecutionService.this.onStartJob(this.dbjc);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements FinishedCallback {
        final /* synthetic */ JobParameters dbjc;

        yfdke(JobParameters jobParameters) {
            this.dbjc = jobParameters;
        }

        @Override // com.good.gd.ndkproxy.ExecutionService.FinishedCallback
        public void onJobFinished() {
            GDLog.DBGPRINTF(14, "ExecutionService::onJobFinished \n");
            ExecutionService.this.jobFinished(this.dbjc, false);
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        GDLog.DBGPRINTF(14, "ExecutionService::onCreate() \n");
        HandlerThread handlerThread = new HandlerThread("ExecutionServiceThread");
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        GDLog.DBGPRINTF(14, "ExecutionService::onDestroy() \n");
        this.handlerThread.quit();
        this.handlerThread = null;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        GDLog.DBGPRINTF(14, "ExecutionService::onStartJob execute job, task id " + jobParameters.getExtras().getLong(EXTRA_TASK_ID) + " \n");
        if (NativeExecutionHandler.getInstance() == null) {
            GDLog.DBGPRINTF(13, "ExecutionService::onStartJob NativeExecutionHandler is NOT initialized, wait for initialization... \n");
            this.handler.postDelayed(new hbfhc(jobParameters), 1000L);
            return true;
        }
        NativeExecutionHandler.getInstance().sendProcessQueueMessage(new yfdke(jobParameters));
        GDLog.DBGPRINTF(14, "ExecutionService::onStartJob return true \n");
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        GDLog.DBGPRINTF(14, "ExecutionService::onStopJob() \n");
        return false;
    }
}
