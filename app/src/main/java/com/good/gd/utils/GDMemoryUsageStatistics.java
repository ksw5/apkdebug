package com.good.gd.utils;

import java.util.Locale;

/* loaded from: classes.dex */
public class GDMemoryUsageStatistics {
    private final long javaAllocatedSize;
    private final long javaFreeSize;
    private final long javaTotalSize;
    private final long nativeHeapAllocatedSize;
    private final long nativeHeapFreeSize;
    private final long nativeHeapTotalSize;

    public GDMemoryUsageStatistics(long j, long j2, long j3, long j4, long j5, long j6) {
        this.javaFreeSize = j;
        this.javaTotalSize = j2;
        this.javaAllocatedSize = j3;
        this.nativeHeapAllocatedSize = j4;
        this.nativeHeapTotalSize = j5;
        this.nativeHeapFreeSize = j6;
    }

    public long getJavaAllocatedSize() {
        return this.javaAllocatedSize;
    }

    public long getJavaFreeSize() {
        return this.javaFreeSize;
    }

    public long getJavaTotalSize() {
        return this.javaTotalSize;
    }

    public long getNativeHeapAllocatedSize() {
        return this.nativeHeapAllocatedSize;
    }

    public long getNativeHeapFreeSize() {
        return this.nativeHeapFreeSize;
    }

    public long getNativeHeapTotalSize() {
        return this.nativeHeapTotalSize;
    }

    public long getTotalAllocatedMemory() {
        return this.nativeHeapAllocatedSize + this.javaAllocatedSize;
    }

    public String toString() {
        String format = String.format(Locale.ENGLISH, "Java runtime memory. Allocated: %d, Total: %d, Free: %d.", Long.valueOf(this.javaAllocatedSize), Long.valueOf(this.javaTotalSize), Long.valueOf(this.javaFreeSize));
        String format2 = String.format(Locale.ENGLISH, "Native heap memory. Allocated: %d, Total: %d, Free: %d.", Long.valueOf(this.nativeHeapAllocatedSize), Long.valueOf(this.nativeHeapTotalSize), Long.valueOf(this.nativeHeapFreeSize));
        String format3 = String.format(Locale.ENGLISH, "Total memory allocated: %d.", Long.valueOf(this.nativeHeapAllocatedSize + this.javaAllocatedSize));
        String str = format + format2 + format3;
        return format3;
    }
}
