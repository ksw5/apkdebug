package com.good.gd.zwn;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.zwn.mjbm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class vzw {
    private final yfdke jsgtu;
    protected final ehnkx liflu;
    private boolean lqox;
    private final ScheduledExecutorService qkduk;
    private int tlske;
    private boolean wuird;
    protected int wxau;
    private boolean ztwf;
    protected final String dbjc = getClass().getSimpleName();
    protected final int jwxax = mjbm.hbfhc.dbjc.length;
    private final fdyxd mvf = new hbfhc();
    private long jcpqe = mjbm.hbfhc.dbjc[0];

    /* loaded from: classes.dex */
    public abstract class fdyxd {
        private long dbjc;

        public fdyxd(vzw vzwVar) {
        }

        void dbjc(long j) {
            this.dbjc = j;
        }

        public abstract void dbjc(boolean z);

        long dbjc() {
            return this.dbjc;
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends fdyxd {
        hbfhc() {
            super(vzw.this);
        }

        @Override // com.good.gd.zwn.vzw.fdyxd
        public void dbjc(boolean z) {
            vzw.this.wuird = z;
            if (true != vzw.this.wuird) {
                vzw.qkduk(vzw.this);
            } else {
                vzw vzwVar = vzw.this;
                vzwVar.tlske = vzwVar.liflu.ztwf();
            }
            vzw.dbjc(vzw.this, dbjc());
            vzw.this.dbjc();
        }
    }

    /* loaded from: classes.dex */
    public interface yfdke {
        void dbjc(boolean z, int i, String str);
    }

    public vzw(ScheduledExecutorService scheduledExecutorService, ehnkx ehnkxVar, yfdke yfdkeVar) {
        this.qkduk = scheduledExecutorService;
        this.liflu = ehnkxVar;
        this.jsgtu = yfdkeVar;
    }

    static /* synthetic */ void qkduk(vzw vzwVar) {
        try {
            try {
                vzwVar.tlske = ((Integer) vzwVar.qkduk.schedule(vzwVar.liflu, vzwVar.jcpqe, TimeUnit.SECONDS).get()).intValue();
            } catch (Exception e) {
                com.good.gd.kloes.hbfhc.qkduk(null, "Error while executing network request:\tCause: " + e.getCause() + "\n\tMessage: " + e.getMessage());
                vzwVar.tlske = -1;
            }
        } finally {
            vzwVar.liflu.tlske();
        }
    }

    protected void jwxax() {
        throw null;
    }

    protected void qkduk() {
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void wxau() {
        this.jsgtu.dbjc((!this.liflu.jsgtu() && this.ztwf) || this.lqox, this.liflu.ztwf(), this.liflu.wxau());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc() {
        if (!this.liflu.jsgtu() && this.wxau != this.jwxax && !this.ztwf) {
            if (401 == this.liflu.ztwf()) {
                qkduk();
            }
            this.mvf.dbjc(System.currentTimeMillis());
            this.liflu.dbjc(this.mvf, this.jsgtu);
        } else if (this.jsgtu == null) {
        } else {
            wxau();
        }
    }

    static /* synthetic */ void dbjc(vzw vzwVar, long j) {
        if (vzwVar.wuird) {
            ehnkx ehnkxVar = vzwVar.liflu;
            if (ehnkxVar instanceof com.good.gd.zwn.fdyxd) {
                if (4 == BlackberryAnalyticsCommon.rynix().ztwf()) {
                    vzwVar.wxau = vzwVar.jwxax;
                    return;
                }
            } else if ((ehnkxVar instanceof orlrx) && 4 == BlackberryAnalyticsCommon.rynix().lqox()) {
                vzwVar.wxau = vzwVar.jwxax;
                return;
            }
        }
        int ztwf = vzwVar.liflu.ztwf();
        String wxau = vzwVar.liflu.wxau();
        boolean z = true != vzwVar.wuird && ztwf == vzwVar.liflu.liflu();
        vzwVar.ztwf = z;
        if (z && (vzwVar.liflu instanceof com.good.gd.zwn.fdyxd) && !mjbm.dbjc(wxau)) {
            vzwVar.ztwf = false;
        }
        vzwVar.wxau++;
        com.good.gd.kloes.hbfhc.dbjc(vzwVar.dbjc, String.format(Locale.getDefault(), "Try [%d/%d] finished with code %d (%s) in %d msec and delay: %d and result: %b", Integer.valueOf(vzwVar.wxau), Integer.valueOf(vzwVar.jwxax), Integer.valueOf(vzwVar.tlske), mjbm.qkduk(vzwVar.tlske), Long.valueOf(System.currentTimeMillis() - j), Long.valueOf(vzwVar.jcpqe), Boolean.valueOf(vzwVar.ztwf)));
        if (404 == ztwf) {
            vzwVar.jwxax();
        }
        if (400 == ztwf && (vzwVar.liflu instanceof orlrx)) {
            com.good.gd.dvql.fdyxd qkduk = mjbm.qkduk(wxau);
            if (qkduk == null || !"InvalidGzipPayload".equals(qkduk.dbjc())) {
                return;
            }
            vzwVar.lqox = true;
            vzwVar.wxau = vzwVar.jwxax;
            com.good.gd.kloes.hbfhc.jwxax(vzwVar.dbjc, "Invalid GZIP request, stopping remaining retries.");
        } else if (1021 == ztwf) {
            vzwVar.wxau = vzwVar.jwxax;
        } else {
            int i = vzwVar.tlske;
            long j2 = 0;
            if (i != 429 && i != 503) {
                int i2 = vzwVar.wxau;
                int[] iArr = mjbm.hbfhc.dbjc;
                if (i2 >= iArr.length) {
                    i2 = iArr.length - 1;
                }
                j2 = mjbm.hbfhc.dbjc[i2];
            } else {
                String str = vzwVar.liflu.dbjc().get("Retry-After");
                if (str != null && !str.isEmpty()) {
                    try {
                        j2 = Long.parseLong(str);
                    } catch (NumberFormatException e) {
                        try {
                            long currentTimeMillis = System.currentTimeMillis() - new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").parse(str).getTime();
                            if (currentTimeMillis >= 0) {
                                j2 = currentTimeMillis;
                            }
                        } catch (ParseException e2) {
                        }
                    }
                } else {
                    int i3 = vzwVar.wxau;
                    int[] iArr2 = mjbm.hbfhc.dbjc;
                    if (i3 >= iArr2.length) {
                        i3 = iArr2.length - 1;
                    }
                    j2 = mjbm.hbfhc.dbjc[i3];
                }
            }
            vzwVar.jcpqe = j2;
        }
    }
}
