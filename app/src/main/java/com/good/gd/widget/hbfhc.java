package com.good.gd.widget;

import com.good.gd.widget.GDDLPWidgetUtil;

/* loaded from: classes.dex */
class hbfhc implements Runnable {
    final /* synthetic */ GDDLPWidgetUtil.mjbm.hbfhc dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc(GDDLPWidgetUtil.mjbm.hbfhc hbfhcVar) {
        this.dbjc = hbfhcVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        GDDLPWidgetUtil.mjbm mjbmVar = GDDLPWidgetUtil.mjbm.this;
        String str = mjbmVar.dbjc;
        mjbmVar.dbjc = null;
        GDDLPWidgetUtil.this.qkduk.setText(str);
    }
}
