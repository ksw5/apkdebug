package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apachehttp.impl.auth.NegotiateScheme;
import com.good.gd.ndkproxy.GDLog;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class KerberosHandler {
    public static int KRB5KDC_ERR_C_PRINCIPAL_UNKNOWN = -1;
    public static int KRB5KDC_ERR_KEY_EXPIRED = -1;
    public static int KRB5KDC_ERR_NONE = -1;
    public static int KRB5KDC_ERR_SERVICE_EXP = -1;
    public static int KRB5KDC_ERR_S_PRINCIPAL_UNKNOWN = -1;
    public static int KRB5KDC_ERR_TGT_REVOKED = -1;
    public static int KRB5_KDC_ERR_CLIENT_NOT_TRUSTED = -1;
    public static int KRB5_KDC_ERR_WRONG_REALM = -1;
    public static int KRB5_LIBOS_BADPWDMATCH = -1;
    public static int KRB5_PREAUTH_FAILED = -1;
    private static KerberosHandler _instance;
    private ThreadPoolExecutor _executor;
    protected long nativeKerberosAuthPtr = 0;
    private boolean allowKerberosDelegation = false;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class aqdzk implements Runnable {
        private final String dbjc;
        private final boolean jwxax;
        private final String qkduk;
        private int wxau = KerberosHandler.KRB5_PREAUTH_FAILED;

        public aqdzk(String str, String str2, boolean z) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = z;
            GDLog.DBGPRINTF(14, "SetupTGT_Task: create object =" + this + "\n");
        }

        public int dbjc() {
            return this.wxau;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "KerberosHandler::SetupTGT_Task:run() IN\n");
            GDLog.DBGPRINTF(14, "SetupTGT_Task: run object =" + this + "Thread ID = " + Thread.currentThread().getId() + "\n");
            KerberosHandler kerberosHandler = KerberosHandler.this;
            long j = kerberosHandler.nativeKerberosAuthPtr;
            if (j != 0) {
                this.wxau = kerberosHandler.setupTGT(this.dbjc, this.qkduk, this.jwxax, j);
            }
            GDLog.DBGPRINTF(16, "KerberosHandler::SetupTGT_Task:run() OUT: " + this.wxau + "\n");
        }
    }

    /* loaded from: classes.dex */
    private final class ehnkx implements Runnable {
        private boolean dbjc;

        public ehnkx(boolean z) {
            this.dbjc = false;
            this.dbjc = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            KerberosHandler kerberosHandler = KerberosHandler.this;
            kerberosHandler.enableDelegation(kerberosHandler.nativeKerberosAuthPtr, this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    private final class fdyxd implements Runnable {
        private fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            KerberosHandler.this.clearAuthKrbCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class mjbm implements Runnable {
        private final String dbjc;
        private final boolean jwxax;
        private final int qkduk;
        private int wxau = KerberosHandler.KRB5_PREAUTH_FAILED;

        public mjbm(String str, int i, boolean z) {
            this.dbjc = str;
            this.qkduk = i;
            this.jwxax = z;
            GDLog.DBGPRINTF(14, "SetupTGT_Task: create object =" + this + "\n");
        }

        public int dbjc() {
            return this.wxau;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "KerberosHandler::SetupKerberosTicket_Task:run() IN, host=" + this.dbjc + " port=" + this.qkduk + "\n");
            GDLog.DBGPRINTF(14, "SetupKerberosTicket_Task: run object =" + this + "Thread ID = " + Thread.currentThread().getId() + "\n");
            KerberosHandler kerberosHandler = KerberosHandler.this;
            long j = kerberosHandler.nativeKerberosAuthPtr;
            if (j != 0) {
                this.wxau = kerberosHandler.setupKerberosTicket(this.dbjc, this.qkduk, this.jwxax, j);
            }
            GDLog.DBGPRINTF(16, "KerberosHandler::SetupKerberosTicket_Task:run() OUT: " + this.wxau + "\n");
        }
    }

    /* loaded from: classes.dex */
    private final class pmoiy implements Runnable {
        private pmoiy() {
        }

        @Override // java.lang.Runnable
        public void run() {
            KerberosHandler kerberosHandler = KerberosHandler.this;
            kerberosHandler.nativeKerberosAuthPtr = kerberosHandler.ndkInit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class yfdke implements Runnable {
        private yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(16, "KerberosHandler::CheckKrbCache_Task() IN\n");
            KerberosHandler kerberosHandler = KerberosHandler.this;
            long j = kerberosHandler.nativeKerberosAuthPtr;
            if (j != 0) {
                kerberosHandler.checkKrbCache(j);
            }
            GDLog.DBGPRINTF(16, "KerberosHandler::CheckKrbCache_Task() OUT\n");
        }
    }

    private KerberosHandler() {
        this._executor = null;
        GDLog.DBGPRINTF(16, "KerberosHandler::KerberosHandler() IN\n");
        this._executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        try {
            this._executor.submit(new pmoiy()).get();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "KerberosHandler::KerberosHandler() : initialization failed with  " + e, e);
        }
        GDLog.DBGPRINTF(16, "KerberosHandler::KerberosHandler() OUT: nativeKerberosAuthPtr=" + this.nativeKerberosAuthPtr + "\n");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void checkKrbCache(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native void clearAuthKrbCache();

    /* JADX INFO: Access modifiers changed from: private */
    public native void enableDelegation(long j, boolean z);

    private native byte[] generateGssApiData(String str, boolean z, byte[] bArr) throws GDGSSException;

    private native long getGssStatus(long j);

    public static synchronized KerberosHandler getInstance() {
        KerberosHandler kerberosHandler;
        synchronized (KerberosHandler.class) {
            if (_instance == null) {
                _instance = new KerberosHandler();
            }
            kerberosHandler = _instance;
        }
        return kerberosHandler;
    }

    private native void releaseKerberosAuth(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native int setupKerberosTicket(String str, int i, boolean z, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public native int setupTGT(String str, String str2, boolean z, long j);

    private void submitCheckKrbCacheTask() {
        GDLog.DBGPRINTF(16, "KerberosHandler::submitCheckKrbCacheTask() IN\n");
        try {
            this._executor.submit(new yfdke()).get();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "KerberosHandler::submitCheckKrbCacheTask() exception", e);
        }
        GDLog.DBGPRINTF(16, "KerberosHandler::submitCheckKrbCacheTask() OUT\n");
    }

    public synchronized void allowDelegation(boolean z) {
        Future<?> submit;
        ehnkx ehnkxVar = new ehnkx(z);
        synchronized (this._executor) {
            submit = this._executor.submit(ehnkxVar);
        }
        try {
            submit.get();
            this.allowKerberosDelegation = z;
        } catch (InterruptedException e) {
            GDLog.DBGPRINTF(12, "KerberosHandler::allowDelegation() exception", e);
        } catch (ExecutionException e2) {
            GDLog.DBGPRINTF(12, "KerberosHandler::allowDelegation() exception", e2);
        }
    }

    public void checkCache() {
        submitCheckKrbCacheTask();
    }

    public void clearCache() {
        try {
            this._executor.submit(new fdyxd()).get();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "KerberosHandler::clearCache() exception", e);
        }
    }

    public synchronized boolean delegationAllowed() {
        return this.allowKerberosDelegation;
    }

    public synchronized void endInstance() {
        KerberosHandler kerberosHandler = _instance;
        if (kerberosHandler != null) {
            kerberosHandler.releaseKerberosAuth(this.nativeKerberosAuthPtr);
            this.nativeKerberosAuthPtr = 0L;
            _instance = null;
        }
    }

    public void executeGSSTask(NegotiateScheme.Generate_GSS_Kerberos_token_Task generate_GSS_Kerberos_token_Task) {
        GDLog.DBGPRINTF(14, "KerberosHandler executeGSSTask executor queue size = " + this._executor.getQueue().size() + "\n");
        try {
            this._executor.submit(generate_GSS_Kerberos_token_Task).get();
        } catch (InterruptedException e) {
            GDLog.DBGPRINTF(12, "KerberosHandler::executeGSSTask() Interupted Exception" + e + " executor queue size =" + this._executor.getQueue().size() + "\n");
        } catch (Exception e2) {
            GDLog.DBGPRINTF(12, "KerberosHandler::executeGSSTask() exception", e2);
        }
    }

    protected void finalize() throws Throwable {
        GDLog.DBGPRINTF(16, "KerberosHandler::finalize()\n");
        try {
            releaseKerberosAuth(this.nativeKerberosAuthPtr);
            this.nativeKerberosAuthPtr = 0L;
        } finally {
            super.finalize();
        }
    }

    native long ndkInit();

    public int setupKerberosTicket_and_executeGSSTask(String str, int i, boolean z, NegotiateScheme.Generate_GSS_Kerberos_token_Task generate_GSS_Kerberos_token_Task) {
        Future<?> submit;
        Future<?> submit2;
        int i2;
        mjbm mjbmVar = new mjbm(str, i, z);
        GDLog.DBGPRINTF(14, "KerberosHandler setupKerberosTicket_and_executeGSSTask executor queue size = " + this._executor.getQueue().size() + "\n");
        synchronized (this._executor) {
            submit = this._executor.submit(mjbmVar);
            submit2 = this._executor.submit(generate_GSS_Kerberos_token_Task);
        }
        try {
            submit.get();
            i2 = mjbmVar.dbjc();
        } catch (Exception e) {
            int i3 = KRB5_PREAUTH_FAILED;
            GDLog.DBGPRINTF(12, "KerberosHandler::setupKerberosTicket_and_executeGSSTask() exception A", e);
            i2 = i3;
        }
        if (i2 != 0 && i2 != KRB5KDC_ERR_NONE) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupKerberosTicket_and_executeGSSTask() : failed to setup Kerberos Ticket: " + i2 + "\n");
        }
        try {
            submit2.get();
        } catch (InterruptedException e2) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupKerberosTicket_and_executeGSSTask() Interupted Exception" + e2 + " executor queue size =" + this._executor.getQueue().size() + "\n");
        } catch (Exception e3) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupKerberosTicket_and_executeGSSTask() exception B", e3);
        }
        return i2;
    }

    public int setupTGT(String str, String str2, boolean z) {
        aqdzk aqdzkVar = new aqdzk(str, str2, z);
        Future<?> submit = this._executor.submit(aqdzkVar);
        GDLog.DBGPRINTF(14, "KerberosHandler setupTGT executor queue size = " + this._executor.getQueue().size() + "\n");
        try {
            submit.get();
            return aqdzkVar.dbjc();
        } catch (Exception e) {
            int i = KRB5_PREAUTH_FAILED;
            GDLog.DBGPRINTF(12, "KerberosHandler::setupTGT() exception", e);
            return i;
        }
    }

    public void setupTGT_and_executeGSSTask(String str, String str2, boolean z, NegotiateScheme.Generate_GSS_Kerberos_token_Task generate_GSS_Kerberos_token_Task) {
        Future<?> submit;
        Future<?> submit2;
        int i;
        aqdzk aqdzkVar = new aqdzk(str, str2, z);
        GDLog.DBGPRINTF(14, "KerberosHandler setupTGT_and_executeGSSTask executor queue size = " + this._executor.getQueue().size() + "\n");
        synchronized (this._executor) {
            submit = this._executor.submit(aqdzkVar);
            submit2 = this._executor.submit(generate_GSS_Kerberos_token_Task);
        }
        try {
            submit.get();
            i = aqdzkVar.dbjc();
        } catch (Exception e) {
            int i2 = KRB5_PREAUTH_FAILED;
            GDLog.DBGPRINTF(12, "KerberosHandler::setupTGT_and_executeGSSTask() exception A", e);
            i = i2;
        }
        if (i != 0 && i != KRB5KDC_ERR_NONE) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupTGT_and_executeGSSTask() : failed to setup TGT: " + i + "\n");
        }
        try {
            submit2.get();
        } catch (InterruptedException e2) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupTGT_and_executeGSSTask() Interupted Exception" + e2 + " executor queue size =" + this._executor.getQueue().size() + "\n");
        } catch (Exception e3) {
            GDLog.DBGPRINTF(12, "KerberosHandler::setupTGT_and_executeGSSTask() exception B", e3);
        }
    }
}
