package com.good.gd.ooyuj;

import android.util.Base64;
import com.blackberry.security.jwt.BBDJWTokenCallback;
import com.blackberry.security.jwt.BBDJWTokenCallbackHandler;
import com.good.gd.kloes.ehnkx;
import com.good.gd.utility.GDUtilityImpl;
import com.good.gd.zwn.mjbm;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.nxoj.hbfhc {
    private GDUtilityImpl jcpqe;
    private final Class dbjc = hbfhc.class;
    private int qkduk = 3;
    private ConcurrentHashMap<String, yfdke> jwxax = new ConcurrentHashMap<>();
    private long wxau = 900000;
    private ScheduledExecutorService ztwf = Executors.newSingleThreadScheduledExecutor();
    private ConcurrentHashMap<String, List<fdyxd>> lqox = new ConcurrentHashMap<>();
    private int[] liflu = {10, 20, 30};

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class fdyxd {
        private boolean dbjc;
        private com.good.gd.bmuat.hbfhc qkduk;

        private fdyxd(hbfhc hbfhcVar) {
        }

        void dbjc(boolean z) {
            this.dbjc = z;
        }

        boolean qkduk() {
            return this.dbjc;
        }

        /* synthetic */ fdyxd(hbfhc hbfhcVar, C0020hbfhc c0020hbfhc) {
            this(hbfhcVar);
        }

        com.good.gd.bmuat.hbfhc dbjc() {
            return this.qkduk;
        }

        void dbjc(com.good.gd.bmuat.hbfhc hbfhcVar) {
            this.qkduk = hbfhcVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.ooyuj.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0020hbfhc implements BBDJWTokenCallback {
        final /* synthetic */ int dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String qkduk;
        final /* synthetic */ String wxau;
        final /* synthetic */ boolean ztwf;

        /* renamed from: com.good.gd.ooyuj.hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0021hbfhc implements Runnable {
            final /* synthetic */ BBDJWTokenCallbackHandler.BBDJWTStatus dbjc;
            final /* synthetic */ String jwxax;
            final /* synthetic */ Integer qkduk;

            /* renamed from: com.good.gd.ooyuj.hbfhc$hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
            /* loaded from: classes.dex */
            class RunnableC0022hbfhc implements Runnable {
                final /* synthetic */ int dbjc;
                final /* synthetic */ int qkduk;

                RunnableC0022hbfhc(int i, int i2) {
                    this.dbjc = i;
                    this.qkduk = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, String.format("[EID] Started retry [%d/%d].", Integer.valueOf(this.dbjc), Integer.valueOf(hbfhc.this.liflu.length)));
                    Class cls = hbfhc.this.dbjc;
                    C0020hbfhc c0020hbfhc = C0020hbfhc.this;
                    com.good.gd.kloes.hbfhc.wxau(cls, String.format("[EID] Started retry [%d/%d] with delay (%d seconds) to get EID Token for Client Id: %s, Scope: %s and Resource Server: %s", Integer.valueOf(this.dbjc), Integer.valueOf(hbfhc.this.liflu.length), Integer.valueOf(this.qkduk), c0020hbfhc.qkduk, c0020hbfhc.jwxax, c0020hbfhc.wxau));
                    C0020hbfhc c0020hbfhc2 = C0020hbfhc.this;
                    hbfhc.this.dbjc(this.dbjc, c0020hbfhc2.qkduk, c0020hbfhc2.jwxax, c0020hbfhc2.wxau, c0020hbfhc2.ztwf);
                }
            }

            RunnableC0021hbfhc(BBDJWTokenCallbackHandler.BBDJWTStatus bBDJWTStatus, Integer num, String str) {
                this.dbjc = bBDJWTStatus;
                this.qkduk = num;
                this.jwxax = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                Class cls = hbfhc.this.dbjc;
                Object[] objArr = new Object[2];
                BBDJWTokenCallbackHandler.BBDJWTStatus bBDJWTStatus = this.dbjc;
                objArr[0] = bBDJWTStatus == null ? "" : bBDJWTStatus.toString();
                objArr[1] = this.qkduk;
                com.good.gd.kloes.hbfhc.wxau(cls, String.format("[EID] Received EID Token response with Status: %s and Error Code: %d", objArr));
                ehnkx.jwxax(hbfhc.this.dbjc, "[EID] Received EID Token response: " + this.jwxax);
                boolean equals = this.dbjc.equals(BBDJWTokenCallbackHandler.BBDJWTStatus.BBDJWTStatusOK);
                if (!equals) {
                    C0020hbfhc c0020hbfhc = C0020hbfhc.this;
                    if (c0020hbfhc.dbjc != hbfhc.this.qkduk) {
                        int[] iArr = hbfhc.this.liflu;
                        C0020hbfhc c0020hbfhc2 = C0020hbfhc.this;
                        int i = c0020hbfhc2.dbjc;
                        int i2 = iArr[i];
                        hbfhc.dbjc(hbfhc.this);
                        hbfhc.this.ztwf.schedule(new RunnableC0022hbfhc(i + 1, i2), i2, TimeUnit.SECONDS);
                        return;
                    }
                }
                synchronized (this) {
                    String qkduk = hbfhc.this.qkduk(C0020hbfhc.this.qkduk, C0020hbfhc.this.jwxax, C0020hbfhc.this.wxau);
                    if (equals) {
                        yfdke yfdkeVar = new yfdke(hbfhc.this, null);
                        yfdkeVar.dbjc(System.currentTimeMillis());
                        yfdkeVar.dbjc(this.jwxax);
                        hbfhc.this.jwxax.put(qkduk, yfdkeVar);
                    }
                    List list = (List) hbfhc.this.lqox.get(qkduk);
                    if (list == null) {
                        com.good.gd.kloes.hbfhc.wxau(hbfhc.this.dbjc, "[EID] Cannot find any EID Token request associated with given ClientId, Scope and Resource Server");
                        return;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        fdyxd fdyxdVar = (fdyxd) it.next();
                        if (fdyxdVar != null) {
                            com.good.gd.bmuat.hbfhc dbjc = fdyxdVar.dbjc();
                            if (dbjc != null) {
                                if (C0020hbfhc.this.ztwf) {
                                    if (equals) {
                                        dbjc.dbjc(this.jwxax);
                                    } else {
                                        dbjc.dbjc(this.dbjc.toString(), this.qkduk.intValue());
                                    }
                                    it.remove();
                                } else if (true != fdyxdVar.qkduk()) {
                                    if (equals) {
                                        dbjc.dbjc(this.jwxax);
                                    } else {
                                        dbjc.dbjc(this.dbjc.toString(), this.qkduk.intValue());
                                    }
                                    it.remove();
                                }
                            } else {
                                it.remove();
                            }
                        }
                    }
                    if (list.isEmpty()) {
                        hbfhc.this.lqox.remove(qkduk);
                    }
                }
            }
        }

        C0020hbfhc(int i, String str, String str2, String str3, boolean z) {
            this.dbjc = i;
            this.qkduk = str;
            this.jwxax = str2;
            this.wxau = str3;
            this.ztwf = z;
        }

        @Override // com.blackberry.security.jwt.BBDJWTokenCallback
        public void BBDJWTCallback(String str, BBDJWTokenCallbackHandler.BBDJWTStatus bBDJWTStatus, Integer num) {
            hbfhc.dbjc(hbfhc.this);
            hbfhc.this.ztwf.execute(new RunnableC0021hbfhc(bBDJWTStatus, num, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class yfdke {
        private long dbjc;
        private String qkduk;

        private yfdke(hbfhc hbfhcVar) {
        }

        public void dbjc(long j) {
            this.dbjc = j;
        }

        public long qkduk() {
            return this.dbjc;
        }

        /* synthetic */ yfdke(hbfhc hbfhcVar, C0020hbfhc c0020hbfhc) {
            this(hbfhcVar);
        }

        public String dbjc() {
            return this.qkduk;
        }

        public void dbjc(String str) {
            this.qkduk = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String qkduk(String str, String str2, String str3) {
        String str4 = str + str2 + str3;
        com.good.gd.cqlap.hbfhc dbjc = com.good.gd.cqlap.hbfhc.dbjc();
        byte[] bytes = str4.getBytes();
        if (dbjc != null) {
            return Base64.encodeToString(bytes, 2);
        }
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x009f A[Catch: all -> 0x0138, TryCatch #0 {, blocks: (B:25:0x006f, B:27:0x0073, B:28:0x007a, B:31:0x0086, B:33:0x0092, B:38:0x009f, B:41:0x00a7, B:44:0x00af, B:46:0x00b8, B:47:0x00bb, B:49:0x00bd, B:51:0x00c9, B:53:0x00d9, B:54:0x00e0, B:56:0x00e8, B:57:0x00f6, B:59:0x00fc, B:60:0x0136, B:62:0x010e, B:63:0x0113, B:65:0x0119, B:69:0x0126, B:72:0x012d, B:74:0x00f1), top: B:24:0x006f }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00bd A[Catch: all -> 0x0138, TryCatch #0 {, blocks: (B:25:0x006f, B:27:0x0073, B:28:0x007a, B:31:0x0086, B:33:0x0092, B:38:0x009f, B:41:0x00a7, B:44:0x00af, B:46:0x00b8, B:47:0x00bb, B:49:0x00bd, B:51:0x00c9, B:53:0x00d9, B:54:0x00e0, B:56:0x00e8, B:57:0x00f6, B:59:0x00fc, B:60:0x0136, B:62:0x010e, B:63:0x0113, B:65:0x0119, B:69:0x0126, B:72:0x012d, B:74:0x00f1), top: B:24:0x006f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dbjc(String r11, String r12, String r13, boolean r14, com.good.gd.bmuat.hbfhc r15) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ooyuj.hbfhc.dbjc(java.lang.String, java.lang.String, java.lang.String, boolean, com.good.gd.bmuat.hbfhc):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(int i, String str, String str2, String str3, boolean z) {
        if (i > this.qkduk) {
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[EID] All Retries completed.");
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("[EID] All Retries completed to get EID Token for Client Id: %s, Scope: %s and Resource Server: %s", str, str2, str3));
            return;
        }
        if (this.jcpqe == null) {
            this.jcpqe = com.blackberry.analytics.analyticsengine.yfdke.dbjc();
        }
        if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt()) {
            com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[EID] Cannot fetch EID Token as EID infrastructure is not activated.");
            ConcurrentHashMap<String, List<fdyxd>> concurrentHashMap = this.lqox;
            if (concurrentHashMap != null && true != concurrentHashMap.isEmpty()) {
                for (Map.Entry<String, List<fdyxd>> entry : this.lqox.entrySet()) {
                    List<fdyxd> value = entry.getValue();
                    if (value != null && true != value.isEmpty()) {
                        Iterator<fdyxd> it = value.iterator();
                        while (it.hasNext()) {
                            fdyxd next = it.next();
                            if (next != null) {
                                com.good.gd.bmuat.hbfhc dbjc = next.dbjc();
                                if (dbjc != null) {
                                    dbjc.dbjc(mjbm.qkduk(1029), 1029);
                                }
                                it.remove();
                            }
                        }
                    }
                }
                this.lqox.clear();
            }
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[EID] All Retries completed.");
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("[EID] All Retries completed to get EID Token for Client Id: %s, Scope: %s and Resource Server: %s", str, str2, str3));
            return;
        }
        this.jcpqe.getEIDToken(str, str2, new C0020hbfhc(i, str, str2, str3, z), z, str3);
    }

    public void dbjc(String str, String str2, String str3) {
        synchronized (this) {
            if (this.jwxax == null) {
                return;
            }
            if (com.good.gd.whhmi.yfdke.qkduk(str)) {
                com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[EID] Client Id cannot be null or empty.");
            } else if (com.good.gd.whhmi.yfdke.qkduk(str2)) {
                com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[EID] Scope cannot be null or empty.");
            } else if (str3 == null) {
                com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[EID] Resource Server cannot be null.");
            } else {
                String qkduk = qkduk(str, str2, str3);
                if (true == com.good.gd.whhmi.yfdke.qkduk(qkduk)) {
                    return;
                }
                synchronized (this) {
                    com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("[EID] Reset EID Token for Client Id: %s, Scope: %s and Resource Server: %s", str, str2, str3));
                    this.jwxax.remove(qkduk);
                }
            }
        }
    }

    static /* synthetic */ void dbjc(hbfhc hbfhcVar) {
        ScheduledExecutorService scheduledExecutorService = hbfhcVar.ztwf;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ehnkx.jwxax(hbfhcVar.dbjc, "[EID] Executor Service is either null or shutdown. Restarting!!");
            hbfhcVar.ztwf = Executors.newSingleThreadScheduledExecutor();
        }
    }
}
