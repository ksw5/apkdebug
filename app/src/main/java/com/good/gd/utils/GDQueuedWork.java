package com.good.gd.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class GDQueuedWork {
    private static final ConcurrentLinkedQueue<Runnable> sPendingWorkFinishers = new ConcurrentLinkedQueue<>();
    private static ExecutorService sSingleThreadExecutor = null;

    public static void add(Runnable runnable) {
        sPendingWorkFinishers.add(runnable);
    }

    public static boolean hasPendingWork() {
        return !sPendingWorkFinishers.isEmpty();
    }

    public static void remove(Runnable runnable) {
        sPendingWorkFinishers.remove(runnable);
    }

    public static ExecutorService singleThreadExecutor() {
        ExecutorService executorService;
        synchronized (GDQueuedWork.class) {
            if (sSingleThreadExecutor == null) {
                sSingleThreadExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = sSingleThreadExecutor;
        }
        return executorService;
    }

    public static void waitToFinish() {
        while (true) {
            Runnable poll = sPendingWorkFinishers.poll();
            if (poll != null) {
                poll.run();
            } else {
                return;
            }
        }
    }
}
