package com.good.gd.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.good.gd.client.GDClient;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;
import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.service.activity_timer.ActivityTimerBase;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class hbfhc extends ActivityTimerBase {
    private static String liflu = "";
    private static hbfhc lqox;
    private PendingIntent wxau;
    private AlarmManager ztwf;
    private final yfdke dbjc = new yfdke(this, null);
    private final Handler qkduk = new Handler();
    private final Runnable jwxax = new RunnableC0029hbfhc();

    /* renamed from: com.good.gd.service.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0029hbfhc implements Runnable {
        RunnableC0029hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "User didn't interact with the warning dialog for idle timer expiration. Posting idletimerExceeded event \n");
            GDClient.getInstance().triggerIdleTimerExceeded();
            hbfhc.this.suspendTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class yfdke extends BroadcastReceiver {
        private yfdke() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(16, "GDActivityTimer idle timeout onReceive IN\n");
            int currentAuthType = GDAuthManager.getCurrentAuthType();
            if (currentAuthType == 0 || currentAuthType == 1 || currentAuthType == 5) {
                GDLog.DBGPRINTF(16, "GDActivityTimer idle timeout onReceive received in " + currentAuthType + " state\n");
                hbfhc.this.suspendTimer();
            } else if (GDLibraryUI.getInstance().isMigrationInProgress()) {
                GDLog.DBGPRINTF(16, "GDActivityTimer idle timeout onReceive received during migration, suspending the timer\n");
                hbfhc.this.suspendTimer();
            } else if (GDActivitySupport.isLocked()) {
                GDLog.DBGPRINTF(13, "GDActivityTimer idle timeout onReceive received in locked state\n");
                hbfhc.this.suspendTimer();
            } else {
                String action = intent.getAction();
                GDLog.DBGPRINTF(16, "GDActivityTimer idle timeout onReceive action = " + action + "\n");
                if (!hbfhc.liflu.equals(action)) {
                    return;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime() - ((ActivityTimerBase) hbfhc.this)._timeActivityOccurred;
                GDLog.DBGPRINTF(16, "GDActivityTimer idle timeout onReceive interval = " + elapsedRealtime + "\n");
                if (((ActivityTimerBase) hbfhc.this)._suspended || elapsedRealtime < ((ActivityTimerBase) hbfhc.this)._timeoutMillisecs) {
                    if (((ActivityTimerBase) hbfhc.this)._suspended || elapsedRealtime + 1000 < ((ActivityTimerBase) hbfhc.this)._timeoutMillisecs) {
                        if (((ActivityTimerBase) hbfhc.this)._suspended) {
                            return;
                        }
                        GDLog.DBGPRINTF(13, "GDActivityTimer idle timeout not within correct time range\n");
                        hbfhc.this.postTimer();
                        return;
                    }
                    GDLog.DBGPRINTF(14, "GDActivityTimer idle timeout with grace period\n");
                    hbfhc.this.idleTimerExceeded();
                    return;
                }
                GDLog.DBGPRINTF(14, "GDActivityTimer idle timeout\n");
                hbfhc.this.idleTimerExceeded();
            }
        }

        /* synthetic */ yfdke(hbfhc hbfhcVar, RunnableC0029hbfhc runnableC0029hbfhc) {
            this();
        }
    }

    private hbfhc() {
        checkAndInit();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase
    protected void checkAndInit() {
        if (this.ztwf != null || GDContext.getInstance().getApplicationContext() == null) {
            return;
        }
        liflu = "com.good.gd idleTimer_" + GDContext.getInstance().getApplicationContext().getPackageName();
        Intent intent = new Intent(liflu);
        intent.putExtra("TIMER_NAME", "gdtimer");
        this.wxau = PendingIntent.getBroadcast(GDContext.getInstance().getApplicationContext(), 0, intent, 335544320);
        AlarmManager alarmManager = (AlarmManager) GDContext.getInstance().getApplicationContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.ztwf = alarmManager;
        alarmManager.cancel(this.wxau);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(liflu);
        GDContext.getInstance().getApplicationContext().registerReceiver(this.dbjc, intentFilter);
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase, com.good.gd.service.activity_timer.ActivityTimer
    public void idleTimerExceeded() {
        super.idleTimerExceeded();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase
    protected void postTimer() {
        GDLog.DBGPRINTF(16, "GDActivityTimer postTimer enabled = " + this._enabled + " suspended = " + this._suspended + "\n");
        if (!this._enabled || this._suspended) {
            return;
        }
        checkAndInit();
        this.ztwf.cancel(this.wxau);
        long j = this._timeActivityOccurred + this._timeoutMillisecs;
        GDLog.DBGPRINTF(16, "GDActivityTimer postTimer setExact time = " + j + "\n");
        this.ztwf.setExact(2, j, this.wxau);
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase, com.good.gd.service.activity_timer.ActivityTimer
    public void resumeTimer() {
        this._suspended = false;
        this._timeActivityOccurred = SystemClock.elapsedRealtime();
        postTimer();
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase
    protected void suspendTimer() {
        this._suspended = true;
        AlarmManager alarmManager = this.ztwf;
        if (alarmManager != null) {
            alarmManager.cancel(this.wxau);
        }
    }

    @Override // com.good.gd.service.activity_timer.ActivityTimerBase, com.good.gd.service.activity_timer.ActivityTimer
    public void userActivityHasOccurred() {
        boolean z;
        GDLog.DBGPRINTF(16, "GDActivityTimer::userActivityHasOccurred\n");
        this.qkduk.removeCallbacks(this.jwxax);
        if (this._timeActivityOccurred == 0 || !this._enabled || SystemClock.elapsedRealtime() <= this._timeActivityOccurred + this._timeoutMillisecs) {
            z = false;
        } else {
            if (!this._suspended) {
                GDLog.DBGPRINTF(13, "GDActivityTimer::checkMissedAlarmWhileInDoze - Alarm Missing so skip user interaction\n");
            }
            z = true;
        }
        if (!z) {
            this._timeActivityOccurred = SystemClock.elapsedRealtime();
            postTimer();
        }
    }

    public static synchronized hbfhc qkduk() {
        hbfhc hbfhcVar;
        synchronized (hbfhc.class) {
            if (lqox == null) {
                lqox = new hbfhc();
            }
            hbfhcVar = lqox;
        }
        return hbfhcVar;
    }
}
