package com.good.gd.ndkproxy;

import android.os.SystemClock;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class NDKExecutionEvent implements Runnable, Comparable<NDKExecutionEvent> {
    final long dbjc;
    final long qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NDKExecutionEvent(long j, long j2) {
        this.dbjc = j;
        this.qkduk = SystemClock.elapsedRealtime() + j2;
    }

    @Override // java.lang.Comparable
    public int compareTo(NDKExecutionEvent nDKExecutionEvent) {
        return Long.compare(this.qkduk, nDKExecutionEvent.qkduk);
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (NativeExecutionHandler.nativeLock) {
            NativeExecutionHandler.executeNDKFunction(this.dbjc);
        }
    }
}
