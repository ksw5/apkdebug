package com.good.gd.jvxom;

import com.good.gd.error.GDError;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.util.WindowControl;

/* loaded from: classes.dex */
public class yfdke extends WindowControl {
    public void dbjc() {
        com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCDI", "");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                finishNative();
            }
        } catch (Exception e) {
            com.good.gd.qkz.hbfhc.dbjc("DCDI", "", e);
        }
    }

    public boolean jwxax() {
        boolean switchWindow2;
        com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCDI", "");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCDI", "");
                switchWindow2 = WindowControl.switchWindow2();
            }
            return switchWindow2;
        } catch (Exception e) {
            com.good.gd.qkz.hbfhc.dbjc("DCDI", "", e);
            return false;
        }
    }

    public void qkduk() throws Exception {
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (GDError e) {
            com.good.gd.qkz.hbfhc.dbjc("DCDI", "", e);
            throw e;
        } catch (Throwable th) {
            com.good.gd.qkz.hbfhc.dbjc("DCDI", "", th);
        }
    }

    public boolean wxau() {
        boolean switchWindow1;
        com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCDI", "");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCDI", "");
                switchWindow1 = WindowControl.switchWindow1();
            }
            return switchWindow1;
        } catch (Exception e) {
            com.good.gd.qkz.hbfhc.dbjc("DCDI", "", e);
            return false;
        }
    }
}
