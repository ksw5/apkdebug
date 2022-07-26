package com.good.gd.ujgjo;

import com.blackberry.bis.core.SIS.BISPreUnlockStorage.BISPreUnlockStorageManager;
import java.util.ArrayDeque;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class aqdzk implements Runnable {
    final /* synthetic */ yfdke dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aqdzk(yfdke yfdkeVar) {
        this.dbjc = yfdkeVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        BISPreUnlockStorageManager sbesx = com.blackberry.bis.core.yfdke.sbesx();
        ArrayDeque<String> ztwf = sbesx.ztwf("crashArray");
        ArrayDeque<String> ztwf2 = sbesx.ztwf("nativeCrashArray");
        if (ztwf2 != null && !ztwf2.isEmpty()) {
            if (ztwf == null) {
                ztwf = new ArrayDeque<>();
            }
            ztwf.addAll(ztwf2);
        }
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue() && ztwf != null && ztwf.size() > 0) {
            com.good.gd.kloes.hbfhc.wxau(this.dbjc.ugfcv, "Flush crashes to file system.");
            Iterator<String> it = ztwf.iterator();
            ArrayDeque arrayDeque = new ArrayDeque();
            while (it.hasNext()) {
                com.good.gd.dvql.hbfhc jwxax = com.blackberry.bis.core.ehnkx.jwxax(it.next());
                jwxax.dbjc(false);
                arrayDeque.add(jwxax);
                it.remove();
            }
            yfdke.dbjc(this.dbjc, "historical", arrayDeque);
            sbesx.qkduk("crashArray");
            sbesx.qkduk("nativeCrashArray");
        }
        yfdke.jsgtu(this.dbjc);
    }
}
