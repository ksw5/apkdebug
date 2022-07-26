package com.good.gd.utils;

import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import com.good.gd.ndkproxy.GDLog;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class GDMemoryProfiling {
    public static void dumpMemory(String str, String str2) {
        String absolutePath = new File(str, str2).getAbsolutePath();
        GDLog.DBGPRINTF(16, "GDMemoryProfiling: Saving memory dump to file - ", absolutePath, "\n");
        try {
            Debug.dumpHprofData(absolutePath);
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, "GDMemoryProfiling: Saving memory dump to file - Exception.\n");
            e.printStackTrace();
        }
        GDLog.DBGPRINTF(16, "GDMemoryProfiling: Saving memory dump to file - DONE.\n");
    }

    public static String getInternalMemoryInfo() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        String memoryValue = getMemoryValue("Total Internal Memory: ", (statFs.getBlockCount() * statFs.getBlockSize()) / 1024);
        return memoryValue + ", " + getMemoryValue("Free Internal Memory: ", (statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1024);
    }

    public static synchronized GDMemoryUsageStatistics getMemoryUsageStatistics() {
        GDMemoryUsageStatistics gDMemoryUsageStatistics;
        synchronized (GDMemoryProfiling.class) {
            Runtime runtime = Runtime.getRuntime();
            long j = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long j2 = j - freeMemory;
            GDLog.DBGPRINTF(16, "GDMemoryProfiling: ", String.format(Locale.ENGLISH, "Java runtime memory. Allocated: %d, Total: %d, Free: %d.\n", Long.valueOf(j2), Long.valueOf(j), Long.valueOf(freeMemory)));
            long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
            long nativeHeapSize = Debug.getNativeHeapSize();
            long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();
            GDLog.DBGPRINTF(16, "GDMemoryProfiling: ", String.format(Locale.ENGLISH, "Native heap memory. Allocated: %d, Total: %d, Free: %d.\n", Long.valueOf(nativeHeapAllocatedSize), Long.valueOf(nativeHeapSize), Long.valueOf(nativeHeapFreeSize)));
            GDLog.DBGPRINTF(16, "GDMemoryProfiling: ", String.format(Locale.ENGLISH, "Total memory allocated: %d.\n", Long.valueOf(nativeHeapAllocatedSize + j2)));
            gDMemoryUsageStatistics = new GDMemoryUsageStatistics(freeMemory, j, j2, nativeHeapAllocatedSize, nativeHeapSize, nativeHeapFreeSize);
        }
        return gDMemoryUsageStatistics;
    }

    private static String getMemoryValue(String str, double d) {
        String concat;
        double d2 = d / 1024.0d;
        double d3 = d / 1048576.0d;
        double d4 = d / 1.073741824E9d;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (d4 > 1.0d) {
            concat = decimalFormat.format(d4).concat(" TB");
        } else if (d3 > 1.0d) {
            concat = decimalFormat.format(d3).concat(" GB");
        } else if (d2 > 1.0d) {
            concat = decimalFormat.format(d2).concat(" MB");
        } else {
            concat = decimalFormat.format(d).concat(" MB");
        }
        return str + " " + concat;
    }

    public static String getRAMInfo() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
            String readLine = randomAccessFile.readLine();
            String readLine2 = randomAccessFile.readLine();
            randomAccessFile.close();
            Pattern compile = Pattern.compile("(\\d+)");
            Matcher matcher = compile.matcher(readLine);
            String str = "";
            String str2 = str;
            while (matcher.find()) {
                str2 = matcher.group(1);
            }
            Matcher matcher2 = compile.matcher(readLine2);
            while (matcher2.find()) {
                str = matcher2.group(1);
            }
            double parseDouble = Double.parseDouble(str2);
            double parseDouble2 = Double.parseDouble(str);
            String memoryValue = getMemoryValue("Total RAM: ", parseDouble);
            return memoryValue + ", " + getMemoryValue("Free RAM: ", parseDouble2);
        } catch (IOException e) {
            return "No memory info";
        }
    }
}
