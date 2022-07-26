package com.good.gd.ndkproxy.mam;

import com.good.gd.mam.GDMobileApplicationManagement;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class GDMobileApplicationManagementImpl {
    public static final int CTP_MODE_NOC = 0;
    public static final int CTP_MODE_UEM_AND_NOC = 1;
    public static final int GDMamType_AppResource = 7;
    public static final int GDMamType_BundleVersions = 1;
    public static final int GDMamType_DownloadUrl = 4;
    public static final int GDMamType_EntitledApps = 0;
    public static final int GDMamType_GetReviews = 5;
    public static final int GDMamType_Icon = 2;
    public static final int GDMamType_Screenshots = 3;
    public static final int GDMamType_SubmitReview = 6;
    private static int s_minimumRequestedFreshness = 5;

    /* loaded from: classes.dex */
    static class fdyxd implements Runnable {
        final /* synthetic */ int dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ String lqox;
        final /* synthetic */ int qkduk;
        final /* synthetic */ int wxau;
        final /* synthetic */ String ztwf;

        fdyxd(int i, int i2, int i3, int i4, String str, String str2) {
            this.dbjc = i;
            this.qkduk = i2;
            this.jwxax = i3;
            this.wxau = i4;
            this.ztwf = str;
            this.lqox = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDMobileApplicationManagement.onCtpResponseInternal(this.dbjc, this.qkduk, this.jwxax, this.wxau, this.ztwf, this.lqox);
        }
    }

    /* loaded from: classes.dex */
    static class hbfhc implements Runnable {
        final /* synthetic */ int dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ int qkduk;
        final /* synthetic */ String wxau;
        final /* synthetic */ int ztwf;

        hbfhc(int i, int i2, String str, String str2, int i3) {
            this.dbjc = i;
            this.qkduk = i2;
            this.jwxax = str;
            this.wxau = str2;
            this.ztwf = i3;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDMobileApplicationManagementImpl._getCtpResponse(this.dbjc, this.qkduk, this.jwxax, this.wxau, this.ztwf);
        }
    }

    /* loaded from: classes.dex */
    static class yfdke implements Runnable {
        final /* synthetic */ int dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ int lqox;
        final /* synthetic */ int qkduk;
        final /* synthetic */ String wxau;
        final /* synthetic */ ByteBuffer ztwf;

        yfdke(int i, int i2, int i3, String str, ByteBuffer byteBuffer, int i4) {
            this.dbjc = i;
            this.qkduk = i2;
            this.jwxax = i3;
            this.wxau = str;
            this.ztwf = byteBuffer;
            this.lqox = i4;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDMobileApplicationManagement.onCtpResourceInternal(this.dbjc, this.qkduk, this.jwxax, this.wxau, this.ztwf, this.lqox);
        }
    }

    private static native void __clearCache();

    private static native boolean __requestCacheWithParameters(long j, long j2, int i);

    private static native void __setFakeDataOn(boolean z);

    private static native int __test(int i);

    private static native int _getCTPHandlerMode();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void _getCtpResponse(int i, int i2, String str, String str2, int i3);

    private static native int _getNextRequestNumber();

    public static boolean _requestCacheWithParameters(long j, long j2, int i, int i2) {
        s_minimumRequestedFreshness = i2;
        return __requestCacheWithParameters(j, j2, i);
    }

    public static void _setFakeDataOn(boolean z) {
        __setFakeDataOn(z);
    }

    private static native boolean _setSetting(String str, String str2);

    public static int _test(int i) {
        return __test(i);
    }

    public static void clearCache() {
        __clearCache();
    }

    public static int getCTPHandlerMode() {
        return _getCTPHandlerMode();
    }

    public static synchronized int getCtpResponse(int i, String str, String str2, int i2) {
        int _getNextRequestNumber;
        synchronized (GDMobileApplicationManagementImpl.class) {
            _getNextRequestNumber = _getNextRequestNumber();
            new Thread(new hbfhc(_getNextRequestNumber, i, str, str2, i2)).start();
        }
        return _getNextRequestNumber;
    }

    public static int getMinimumRequestedFreshness() {
        return s_minimumRequestedFreshness;
    }

    private static synchronized void onCtpResource(int i, int i2, int i3, String str, ByteBuffer byteBuffer, int i4) {
        synchronized (GDMobileApplicationManagementImpl.class) {
            new Thread(new yfdke(i, i2, i3, str, byteBuffer, i4)).start();
        }
    }

    private static synchronized void onCtpResponse(int i, int i2, int i3, int i4, String str, String str2) {
        synchronized (GDMobileApplicationManagementImpl.class) {
            new Thread(new fdyxd(i, i2, i3, i4, str, str2)).start();
        }
    }

    public static boolean setSetting(String str, String str2) {
        return _setSetting(str, str2);
    }
}
