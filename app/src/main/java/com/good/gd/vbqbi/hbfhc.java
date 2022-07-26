package com.good.gd.vbqbi;

import com.good.gd.lqnsz.hbfhc;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class hbfhc {
    private com.good.gd.vocp.hbfhc dbjc;

    public abstract void dbjc(hbfhc.C0015hbfhc c0015hbfhc);

    public void dbjc(com.good.gd.vocp.hbfhc hbfhcVar) {
        this.dbjc = hbfhcVar;
    }

    public abstract void qkduk();

    public com.good.gd.vocp.hbfhc dbjc() {
        return this.dbjc;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String dbjc(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        String str = "CRITICAL";
        if (!arrayList.contains(str) && !arrayList.contains(str.toLowerCase())) {
            str = "HIGH";
            if (!arrayList.contains(str) && !arrayList.contains(str.toLowerCase())) {
                str = "MEDIUM";
                if (!arrayList.contains(str) && !arrayList.contains(str.toLowerCase())) {
                    str = "LOW";
                    if (!arrayList.contains(str) && !arrayList.contains(str.toLowerCase())) {
                        return arrayList.get(0);
                    }
                }
            }
        }
        return str;
    }
}
