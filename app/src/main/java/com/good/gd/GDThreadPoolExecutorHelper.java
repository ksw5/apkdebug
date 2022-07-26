package com.good.gd;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class GDThreadPoolExecutorHelper {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    public static final Executor GD_THREAD_POOL_EXECUTOR;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final int MAXIMUM_POOL_SIZE;
    private static final int QUEUE_CAPACITY = 128;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;

    /* loaded from: classes.dex */
    static class hbfhc implements ThreadFactory {
        private final AtomicInteger dbjc = new AtomicInteger(1);

        hbfhc() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "GDAsyncTask #" + this.dbjc.getAndIncrement());
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = availableProcessors;
        int max = Math.max(2, Math.min(availableProcessors - 1, 4));
        CORE_POOL_SIZE = max;
        int i = (availableProcessors * 2) + 1;
        MAXIMUM_POOL_SIZE = i;
        hbfhc hbfhcVar = new hbfhc();
        sThreadFactory = hbfhcVar;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        sPoolWorkQueue = linkedBlockingQueue;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(max, i, 30L, TimeUnit.SECONDS, linkedBlockingQueue, hbfhcVar);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        GD_THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }
}
