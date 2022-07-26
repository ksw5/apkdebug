package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import java.lang.ref.ReferenceQueue;

/* loaded from: classes.dex */
public class RefQueueWorker implements Runnable {
    private final Log log = LogFactory.getLog(RefQueueWorker.class);
    protected final RefQueueHandler refHandler;
    protected final ReferenceQueue<?> refQueue;
    protected volatile Thread workerThread;

    public RefQueueWorker(ReferenceQueue<?> referenceQueue, RefQueueHandler refQueueHandler) {
        if (referenceQueue != null) {
            if (refQueueHandler != null) {
                this.refQueue = referenceQueue;
                this.refHandler = refQueueHandler;
                return;
            }
            throw new IllegalArgumentException("Handler must not be null.");
        }
        throw new IllegalArgumentException("Queue must not be null.");
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.workerThread == null) {
            this.workerThread = Thread.currentThread();
        }
        while (this.workerThread == Thread.currentThread()) {
            try {
                this.refHandler.handleReference(this.refQueue.remove());
            } catch (InterruptedException e) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug(toString() + " interrupted", e);
                }
            }
        }
    }

    public void shutdown() {
        Thread thread = this.workerThread;
        if (thread != null) {
            this.workerThread = null;
            thread.interrupt();
        }
    }

    public String toString() {
        return "RefQueueWorker::" + this.workerThread;
    }
}
