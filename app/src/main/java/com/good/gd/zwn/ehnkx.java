package com.good.gd.zwn;

import com.good.gd.zwn.vzw;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public abstract class ehnkx implements Callable<Integer> {
    final aqdzk dbjc;
    private String qkduk;
    protected ExecutorService wxau;
    private int jwxax = 1011;
    private final Class ztwf = getClass();

    /* JADX INFO: Access modifiers changed from: package-private */
    public ehnkx(aqdzk aqdzkVar) {
        this.dbjc = aqdzkVar;
    }

    @Override // java.util.concurrent.Callable
    public Integer call() throws Exception {
        try {
            if (true != jcpqe()) {
                return Integer.valueOf(this.jwxax);
            }
            this.qkduk = qkduk();
            int jwxax = jwxax();
            this.jwxax = jwxax;
            boolean z = jwxax == liflu();
            String str = "END_HISTORICAL_POST";
            com.good.gd.kloes.hbfhc.jwxax(this.ztwf, this instanceof orlrx ? str : "END_GET_CONFIG Request completed with response code : " + this.jwxax);
            if (!z) {
                Class cls = this.ztwf;
                Locale locale = Locale.US;
                if (!(this instanceof orlrx)) {
                    str = "END_GET_CONFIG Failed with response: %s";
                }
                com.good.gd.kloes.hbfhc.wxau(cls, String.format(locale, str, this.qkduk));
            }
            if ((this instanceof fdyxd) && true != mjbm.dbjc(this.qkduk)) {
                com.good.gd.kloes.hbfhc.dbjc(this.ztwf, "GET Response is Invalid");
                z = false;
            }
            if (true != z && 400 == this.jwxax && (this instanceof orlrx)) {
                com.good.gd.dvql.fdyxd qkduk = mjbm.qkduk(this.qkduk);
                if ((qkduk != null && "InvalidGzipPayload".equals(qkduk.dbjc())) && this.dbjc != null) {
                    com.good.gd.kloes.hbfhc.dbjc(this.ztwf, "GZIP in request was corrupt.");
                    this.dbjc.dbjc(true, this.jwxax, this.qkduk);
                    return Integer.valueOf(this.jwxax);
                }
            }
            aqdzk aqdzkVar = this.dbjc;
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(z, this.jwxax, this.qkduk);
            }
            return Integer.valueOf(this.jwxax);
        } catch (Exception e) {
            String localizedMessage = e.getLocalizedMessage();
            this.jwxax = 1000;
            this.qkduk = localizedMessage;
            aqdzk aqdzkVar2 = this.dbjc;
            if (aqdzkVar2 != null) {
                aqdzkVar2.dbjc(false, 1000, localizedMessage);
            }
            return Integer.valueOf(this.jwxax);
        }
    }

    public abstract Map<String, String> dbjc();

    public void dbjc(int i) {
        this.jwxax = i;
    }

    public abstract void dbjc(vzw.fdyxd fdyxdVar, vzw.yfdke yfdkeVar);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void dbjc(boolean z, vzw.fdyxd fdyxdVar);

    protected abstract boolean jcpqe() throws Exception;

    public abstract boolean jsgtu();

    protected abstract int jwxax();

    public abstract int liflu();

    public abstract int lqox();

    protected abstract String qkduk();

    public abstract void tlske();

    public abstract void wuird();

    public String wxau() {
        return this.qkduk;
    }

    public int ztwf() {
        return this.jwxax;
    }

    public void dbjc(String str) {
        this.qkduk = str;
    }
}
