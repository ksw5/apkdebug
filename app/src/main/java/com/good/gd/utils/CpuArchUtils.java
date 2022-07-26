package com.good.gd.utils;

import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class CpuArchUtils {
    public static void checkCPUArch() throws Exception {
        String[] strArr = {"armv5", "armv6", "mips"};
        String property = System.getProperty("os.arch");
        for (int i = 0; i < 3; i++) {
            String str = strArr[i];
            if (property.regionMatches(0, str, 0, str.length())) {
                GDLog.DBGPRINTF(12, "Unsupported CPU Arch " + property + "GD can't run on this architecture");
                if (GDDeviceInfo.getInstance().isSimulator()) {
                    throw new Exception("Error: GD does not support the CPU Arch this version of Android Emulator is based on. Use API level 24 or above emulator");
                }
                throw new Exception("Error: GD does not support the CPU Arch of this device");
            }
        }
    }
}
