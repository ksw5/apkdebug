package com.good.gd.ndkproxy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.good.gd.ndkproxy.ExecutionService;
import com.good.gt.context.GTBaseContext;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/* loaded from: classes.dex */
public class NativeExecutionHandler extends Handler {
    public static final int CODE_ALARM_FIRED = 1;
    public static final int CODE_JOB_SERVICE_EVENT = 2;
    public static final int CODE_NEW_EVENT = 0;
    public static final long DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL = 2000;
    private static String INTENT_ACTION_TIMEOUT = "com.good.gd.ndkproxy.NativeExecutionHandler.timeout";
    private static final long WAKELOCK_MAX_INTERVAL = 5000;
    private static String WAKE_LOCK_TAG = "com.good.gd.ndkproxy.NativeExecutionHandler";
    static NativeExecutionHandler _instance = null;
    private static long alarmManageMinimumInterval = 2000;
    public static final Object nativeLock = new Object();
    public static final Object nativeLockApi = new Object();
    public static final Object nativeLockGT = new Object();
    private AlarmManager alarmManager;
    private Queue<NDKExecutionEvent> eventQueue;
    private PendingIntent timeoutIntent;
    private PowerManager.WakeLock wakeLock;
    private long lastProcessLoopTime = 0;
    private long lastAlarmTime = 0;
    private long eventAlarmTime = 0;

    /* loaded from: classes.dex */
    public class NativeExecutionState {
        public long currentTime;
        public long executionQueueSize;
        public long lastAlarmTime;
        public long lastProcessTime;
        public long timeBehindDesiredAlarm;

        public NativeExecutionState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(16, "NEH: alarm receiver\n");
            NativeExecutionHandler.this.lastAlarmTime = SystemClock.elapsedRealtime();
            NativeExecutionHandler.this.sendAlarmFiredMessage();
            NativeExecutionHandler.this.acquireWakeLock();
        }
    }

    private NativeExecutionHandler() {
        throw new RuntimeException("NativeExecutionHandler must be constructed with an explicit Looper");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acquireWakeLock() {
        synchronized (this.wakeLock) {
            if (!this.wakeLock.isHeld()) {
                GDLog.DBGPRINTF(16, "NEH: attempt to acquire wake lock\n");
                this.wakeLock.acquire(WAKELOCK_MAX_INTERVAL);
                GDLog.DBGPRINTF(16, "NEH: acquired wake lock\n");
            }
        }
    }

    public static NativeExecutionHandler createInstance(Context context, Looper looper) {
        if (_instance == null) {
            _instance = new NativeExecutionHandler(context, looper);
        }
        return _instance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native void executeNDKFunction(long j);

    private synchronized long getAlarmManagerMinimumInterval() {
        return alarmManageMinimumInterval;
    }

    public static NativeExecutionHandler getInstance() {
        return _instance;
    }

    private void initialize(Context context) {
        synchronized (nativeLock) {
            ndkInit();
        }
        this.eventQueue = new PriorityBlockingQueue();
        INTENT_ACTION_TIMEOUT += "_" + GTBaseContext.getInstance().getApplicationContext().getPackageName();
        this.timeoutIntent = PendingIntent.getBroadcast(context, 0, new Intent(INTENT_ACTION_TIMEOUT), 335544320);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.alarmManager = alarmManager;
        alarmManager.cancel(this.timeoutIntent);
        hbfhc hbfhcVar = new hbfhc();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTENT_ACTION_TIMEOUT);
        context.registerReceiver(hbfhcVar, intentFilter);
        WAKE_LOCK_TAG += "_" + GTBaseContext.getInstance().getApplicationContext().getPackageName();
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, WAKE_LOCK_TAG);
        this.wakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        setAlarmManagerMinimumInterval(alarmManageMinimumInterval);
        initComplete();
    }

    private boolean isTargetingAndroidSAndAbove() {
        return GTBaseContext.getInstance().getApplicationContext().getApplicationInfo().targetSdkVersion > 30 && ((Build.VERSION.SDK_INT > 30) || (Build.VERSION.PREVIEW_SDK_INT != 0));
    }

    private void processEventQueue(boolean z, ExecutionService.FinishedCallback finishedCallback) {
        NDKExecutionEvent peek;
        GDLog.DBGPRINTF(16, "NEH: processEventQueue() IN \n");
        long j = -1;
        long j2 = -1;
        while (true) {
            peek = this.eventQueue.peek();
            if (peek == null) {
                break;
            }
            j = peek.qkduk - SystemClock.elapsedRealtime();
            j2 = peek.qkduk;
            if (j > 0) {
                GDLog.DBGPRINTF(16, "NEH.process " + peek.dbjc + " timeout " + j + "\n");
                break;
            }
            this.eventQueue.remove(peek);
            if (z) {
                acquireWakeLock();
            }
            GDLog.DBGPRINTF(16, "NEH.process run " + peek.dbjc + "\n");
            peek.run();
            GDLog.DBGPRINTF(16, "NEH.process done " + peek.dbjc + "\n");
        }
        if (j <= 0) {
            GDLog.DBGPRINTF(16, "NEH: no more events\n");
            releaseWakeLock();
        } else if (j < getAlarmManagerMinimumInterval()) {
            if (!hasMessages(0, peek)) {
                GDLog.DBGPRINTF(16, "NEH: Handler message in " + j + "ms\n");
                if (z) {
                    acquireWakeLock();
                }
                sendEventQueueMessage(peek, j);
            }
        } else {
            if (this.eventAlarmTime != j2) {
                this.eventAlarmTime = j2;
                if (isTargetingAndroidSAndAbove()) {
                    GDLog.DBGPRINTF(16, "NEH: Schedule a job for " + j + "ms \n");
                    if (finishedCallback != null) {
                        GDLog.DBGPRINTF(16, "NEH: processEventQueue() call onJobFinished \n");
                        finishedCallback.onJobFinished();
                        finishedCallback = null;
                    }
                    NEHJobScheduleHelper.cancelJob();
                    NEHJobScheduleHelper.scheduleJob(j, peek.dbjc);
                } else {
                    GDLog.DBGPRINTF(16, "NEH: AlarmManager set for " + j + "ms\n");
                    this.alarmManager.cancel(this.timeoutIntent);
                    this.alarmManager.setExact(2, SystemClock.elapsedRealtime() + j, this.timeoutIntent);
                }
            }
            releaseWakeLock();
        }
        this.lastProcessLoopTime = SystemClock.elapsedRealtime();
        if (finishedCallback != null) {
            GDLog.DBGPRINTF(16, "NEH: processEventQueue() call onJobFinished \n");
            finishedCallback.onJobFinished();
        }
        GDLog.DBGPRINTF(16, "NEH: processEventQueue() OUT \n");
    }

    private void releaseWakeLock() {
        synchronized (this.wakeLock) {
            if (this.wakeLock.isHeld()) {
                this.wakeLock.release();
                GDLog.DBGPRINTF(16, "NEH: released wake lock\n");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAlarmFiredMessage() {
        sendEmptyMessage(1);
    }

    private void sendEventQueueMessage(NDKExecutionEvent nDKExecutionEvent, long j) {
        sendMessageDelayed(obtainMessage(0, nDKExecutionEvent), j);
    }

    public static synchronized void setAlarmManagerMinimumInterval(long j) {
        synchronized (NativeExecutionHandler.class) {
            GDLog.DBGPRINTF(16, "NEH: setAlarmManagerMinimumInterval = " + j + "\n");
            alarmManageMinimumInterval = j;
        }
    }

    public void deleteFromExecutionQueue(long j) {
        GDLog.DBGPRINTF(13, "NEH.deleteFromExecutionQueue: " + j + "\n");
        NDKExecutionEvent nDKExecutionEvent = null;
        for (NDKExecutionEvent nDKExecutionEvent2 : this.eventQueue) {
            if (nDKExecutionEvent2.dbjc == j) {
                this.eventQueue.remove(nDKExecutionEvent2);
                nDKExecutionEvent = nDKExecutionEvent2;
            }
        }
        if (nDKExecutionEvent != null) {
            removeMessages(0, nDKExecutionEvent);
        } else {
            GDLog.DBGPRINTF(13, "NEH.deleteFromExecutionQueue: event is not in queue\n");
        }
    }

    public NativeExecutionState getNativeExecutionState() {
        NativeExecutionState nativeExecutionState = new NativeExecutionState();
        nativeExecutionState.currentTime = SystemClock.elapsedRealtime();
        nativeExecutionState.lastAlarmTime = this.lastAlarmTime;
        nativeExecutionState.lastProcessTime = this.lastProcessLoopTime;
        nativeExecutionState.executionQueueSize = this.eventQueue.size();
        if (this.eventQueue.peek() != null) {
            nativeExecutionState.timeBehindDesiredAlarm = nativeExecutionState.currentTime - this.eventQueue.peek().qkduk;
        }
        Iterator<NDKExecutionEvent> it = this.eventQueue.iterator();
        while (it.hasNext()) {
            GDLog.DBGPRINTF(16, "NEH: eventQueue Time = " + it.next().qkduk + "\n");
        }
        return nativeExecutionState;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        ExecutionService.FinishedCallback finishedCallback;
        int i = message.what;
        boolean z = true;
        if (i == 1) {
            setAlarmManagerMinimumInterval(DEFAULT_ALARM_MANAGER_MINIMUM_INTERVAL);
        } else if (i == 2) {
            GDLog.DBGPRINTF(16, "NEH: handleMessage() received message from ExecutionService \n");
            z = false;
            finishedCallback = (ExecutionService.FinishedCallback) message.obj;
            processEventQueue(z, finishedCallback);
        }
        finishedCallback = null;
        processEventQueue(z, finishedCallback);
    }

    native void initComplete();

    native void ndkInit();

    public void putIntoExecutionQueue(int i, long j) {
        GDLog.DBGPRINTF(16, "NEH.putIntoQueue: delay=" + i + "ms id=" + j + "\n");
        NDKExecutionEvent nDKExecutionEvent = new NDKExecutionEvent(j, i);
        this.eventQueue.add(nDKExecutionEvent);
        sendEventQueueMessage(nDKExecutionEvent, 0L);
    }

    public void sendProcessQueueMessage(ExecutionService.FinishedCallback finishedCallback) {
        GDLog.DBGPRINTF(16, "NEH: sendProcessQueueMessage() \n");
        sendMessage(obtainMessage(2, finishedCallback));
    }

    public NativeExecutionHandler(Context context, Looper looper) {
        super(looper);
        initialize(context);
    }
}
