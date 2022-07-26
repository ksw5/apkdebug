package com.good.gd.utils;

import android.util.Log;

/* loaded from: classes.dex */
public class GDNDKLibraryLoader {
    private static Object _ndkLibraryControl = new Object();
    private static boolean _ndkLibraryIsLoaded = false;

    /* loaded from: classes.dex */
    static class hbfhc extends Thread {
        hbfhc() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                System.loadLibrary("gdndk");
                synchronized (GDNDKLibraryLoader._ndkLibraryControl) {
                    boolean unused = GDNDKLibraryLoader._ndkLibraryIsLoaded = true;
                    GDNDKLibraryLoader._ndkLibraryControl.notify();
                }
            } catch (Error e) {
                Log.e("GDLog", "Cannot load good dynamics library", e);
                throw new RuntimeException("Cannot load good dynamics library", e);
            }
        }
    }

    public static void loadNDKLibrary() {
        synchronized (_ndkLibraryControl) {
            if (_ndkLibraryIsLoaded) {
                return;
            }
            new hbfhc().start();
            synchronized (_ndkLibraryControl) {
                try {
                    if (!_ndkLibraryIsLoaded) {
                        Log.i("GDLog", "GDInit: Waiting for loadNDKLibThread to complete\n");
                        _ndkLibraryControl.wait();
                    }
                } catch (InterruptedException e) {
                    Log.e("GDLog", "GDInit: loadNDKLibThread failed to complete\n", e);
                }
            }
        }
    }
}
