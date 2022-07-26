package com.good.gd.zwn;

import java.security.SecureRandom;
import java.util.Locale;

/* loaded from: classes.dex */
public class pmoiy {
    public int dbjc;
    private boolean jwxax;
    private int liflu;
    private int lqox;
    public int qkduk;
    public int wxau;
    public int ztwf;

    public pmoiy(int i, int i2, boolean z, int i3, int i4) {
        this.dbjc = i;
        this.qkduk = i2;
        this.jwxax = z;
        this.wxau = i3;
        this.ztwf = i4;
        dbjc();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dbjc(pmoiy pmoiyVar) {
        return pmoiyVar != null && pmoiyVar.dbjc == this.dbjc && pmoiyVar.qkduk == this.qkduk && pmoiyVar.wxau == this.wxau && pmoiyVar.ztwf == this.ztwf && pmoiyVar.jwxax == this.jwxax;
    }

    public boolean jwxax() {
        return this.jwxax;
    }

    public int qkduk() {
        return this.liflu;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "NetworkTaskConfiguration get[i/w]: [%d/%d] post[i/w]: [%d/%d] delay for get:[%d] delay for post:[%d] is valid: %b & userCorrelation: %b", Integer.valueOf(this.dbjc), Integer.valueOf(this.qkduk), Integer.valueOf(this.wxau), Integer.valueOf(this.ztwf), Integer.valueOf(this.lqox), Integer.valueOf(this.liflu), Boolean.valueOf(ztwf()), Boolean.valueOf(this.jwxax));
    }

    public int wxau() {
        return this.lqox;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean ztwf() {
        return (this.dbjc == -1 || this.qkduk == -1 || this.wxau == -1 || this.ztwf == -1 || this.lqox == -1 || this.liflu == -1) ? false : true;
    }

    public void dbjc() {
        int i = this.qkduk;
        this.lqox = i > 0 ? new SecureRandom().nextInt(i) + this.dbjc : this.dbjc;
        int i2 = this.ztwf;
        this.liflu = i2 > 0 ? new SecureRandom().nextInt(i2) + this.wxau : this.wxau;
    }

    public pmoiy(int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
        this.dbjc = i;
        this.qkduk = i2;
        this.jwxax = z;
        this.wxau = i3;
        this.ztwf = i4;
        this.lqox = i5;
        this.liflu = i6;
    }
}
