package com.good.gd.ndkproxy;

import com.good.gd.utils.GDMemoryProfiling;

/* loaded from: classes.dex */
public class GDRamInfo {

    /* loaded from: classes.dex */
    static class hbfhc extends Thread {
        hbfhc() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                GDRamInfo.setRamInfo(GDMemoryProfiling.getRAMInfo());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void getRAMInfo() {
        synchronized (GDRamInfo.class) {
            new hbfhc().start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void setRamInfo(String str);
}
