package com.good.gd.ujgjo;

import java.io.File;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class orlrx implements com.good.gd.zwn.yfdke {
    final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;
    final /* synthetic */ yfdke qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public orlrx(yfdke yfdkeVar, com.good.gd.zwn.aqdzk aqdzkVar) {
        this.qkduk = yfdkeVar;
        this.dbjc = aqdzkVar;
    }

    @Override // com.good.gd.zwn.yfdke
    public void dbjc(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.dbjc(this.qkduk.ugfcv, "Historical File compression failed.");
        com.good.gd.kloes.ehnkx.wxau(this.qkduk.ugfcv, String.format("Historical File : %s", file.getName()));
        this.qkduk.qkduk.qkduk(file.getAbsolutePath());
        yfdke.dbjc(this.qkduk, "GZIP Compression failed, Scheduling the next file.", this.dbjc, aqdzkVar, this, true);
    }

    @Override // com.good.gd.zwn.yfdke
    public void qkduk(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.wxau(this.qkduk.ugfcv, "Historical File compression successful.");
        com.good.gd.kloes.ehnkx.qkduk(this.qkduk.ugfcv, String.format("Historical File : %s", file.getName()));
        this.qkduk.dbjc(file, aqdzkVar);
    }
}
