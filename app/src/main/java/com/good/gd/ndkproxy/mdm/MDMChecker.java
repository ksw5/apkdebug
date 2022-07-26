package com.good.gd.ndkproxy.mdm;

import android.content.pm.PackageManager;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.MDMConsumer.MDMConsumer;
import com.good.gt.MDMConsumer.MDMConsumerListener;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class MDMChecker implements MDMConsumerListener {
    private static String _enrollmentErrorReason;
    private static MDMChecker _instance;
    private byte[][] _currentCerts;
    private long[] _currentIDs;
    private AtomicReference<MDMResult> _notifySync;

    /* loaded from: classes.dex */
    public class MDMResult {
        byte[] _certData;
        long _certID;
        boolean _exceptionThrown;
        boolean _matched;

        public MDMResult(boolean z, long j, byte[] bArr, boolean z2) {
            this._matched = z;
            this._certID = j;
            this._certData = bArr;
            this._exceptionThrown = z2;
        }
    }

    private MDMChecker() {
        ndkInit();
    }

    public static synchronized MDMChecker getInstance() {
        MDMChecker mDMChecker;
        synchronized (MDMChecker.class) {
            if (_instance == null) {
                _instance = new MDMChecker();
            }
            mDMChecker = _instance;
        }
        return mDMChecker;
    }

    public static String getMdmEnrollmentErrorReason() {
        return _enrollmentErrorReason;
    }

    private native void nativeOnMDMConsumerResult(boolean z, long j, byte[] bArr, boolean z2);

    private native void ndkInit();

    protected boolean enrollmentKeyAvailable(String str, String str2, String str3, String str4) {
        GDLog.DBGPRINTF(16, "MDMChecker::enrollmentKeyAvailable\n");
        MDMConsumer.getInstance().setEnrollmentKey(str, str2, str3, str4);
        return true;
    }

    public void init() {
        MDMComplianceStatusReporter.getInstance().init();
    }

    @Override // com.good.gt.MDMConsumer.MDMConsumerListener
    public void onResult(boolean z, int i, boolean z2, String str) {
        byte[] bArr;
        long j;
        GDLog.DBGPRINTF(16, "MDMChecker::onResult on=" + z + " exc=" + z2 + "enrollementErrorReason=" + str);
        synchronized (this._notifySync) {
            if (z && i >= 0) {
                byte[][] bArr2 = this._currentCerts;
                if (i < bArr2.length) {
                    long[] jArr = this._currentIDs;
                    if (i < jArr.length) {
                        byte[] bArr3 = bArr2[i];
                        j = jArr[i];
                        bArr = bArr3;
                        _enrollmentErrorReason = str;
                        this._notifySync.set(new MDMResult(z, j, bArr, z2));
                        this._notifySync.notify();
                    }
                }
            }
            bArr = null;
            j = -1;
            _enrollmentErrorReason = str;
            this._notifySync.set(new MDMResult(z, j, bArr, z2));
            this._notifySync.notify();
        }
    }

    protected synchronized void triggerMDMCheck(byte[][] bArr, long[] jArr, String str) {
        GDLog.DBGPRINTF(14, "MDMChecker::triggerMDMCheck\n");
        if (this._notifySync == null) {
            this._notifySync = new AtomicReference<>();
        }
        MDMResult mDMResult = new MDMResult(false, -1L, null, false);
        synchronized (this._notifySync) {
            try {
                if (!triggerMDMCheckAsync(bArr, jArr, str)) {
                    try {
                        if (GDContext.getInstance().getApplicationContext().getPackageManager().getPackageInfo(str, 0) != null) {
                            GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck - Binding to Good Agent Service failed but Good Agent installed\n");
                            mDMResult._exceptionThrown = true;
                        } else {
                            GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck - Good Agent not installed\n");
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck - Good Agent not installed\n");
                    }
                } else {
                    while (this._notifySync.get() == null) {
                        this._notifySync.wait();
                    }
                    MDMResult mDMResult2 = this._notifySync.get();
                    try {
                        this._notifySync.set(null);
                        mDMResult = mDMResult2;
                    } catch (SecurityException e2) {
                        mDMResult = mDMResult2;
                        GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck - Permission Denial Binding to Good Agent Service\n");
                        mDMResult._exceptionThrown = true;
                        nativeOnMDMConsumerResult(mDMResult._matched, mDMResult._certID, mDMResult._certData, mDMResult._exceptionThrown);
                    } catch (Exception e3) {
                        mDMResult = mDMResult2;
                        e = e3;
                        GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck " + e.toString());
                        StackTraceElement[] stackTrace = e.getStackTrace();
                        int length = stackTrace.length;
                        for (int i = 0; i < length; i++) {
                            GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck " + stackTrace[i] + "\n");
                        }
                        mDMResult._exceptionThrown = true;
                        nativeOnMDMConsumerResult(mDMResult._matched, mDMResult._certID, mDMResult._certData, mDMResult._exceptionThrown);
                    }
                }
            } catch (SecurityException e4) {
            } catch (Exception e5) {
                e = e5;
            }
        }
        nativeOnMDMConsumerResult(mDMResult._matched, mDMResult._certID, mDMResult._certData, mDMResult._exceptionThrown);
    }

    protected synchronized boolean triggerMDMCheckAsync(byte[][] bArr, long[] jArr, String str) {
        boolean validate;
        int length = bArr.length;
        this._currentCerts = new byte[length];
        for (int i = 0; i < length; i++) {
            int length2 = bArr[i].length;
            byte[][] bArr2 = this._currentCerts;
            bArr2[i] = new byte[length2];
            System.arraycopy(bArr[i], 0, bArr2[i], 0, bArr[i].length);
        }
        long[] jArr2 = new long[jArr.length];
        this._currentIDs = jArr2;
        System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
        if (bArr.length == 0) {
            GDLog.DBGPRINTF(13, "MDMChecker:: No GC Provided Certificates\n");
        }
        validate = MDMConsumer.getInstance().validate(bArr, this, str);
        if (!validate) {
            GDLog.DBGPRINTF(12, "MDMChecker::triggerMDMCheck error validating MDM agent\n");
        }
        GDLog.DBGPRINTF(16, "MDMChecker::triggerMDMCheckAsync " + validate);
        return validate;
    }
}
