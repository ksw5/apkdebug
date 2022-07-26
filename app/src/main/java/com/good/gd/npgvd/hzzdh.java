package com.good.gd.npgvd;

import com.blackberry.bis.core.pmoiy;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes.dex */
public class hzzdh implements pmoiy.yfdke {
    private final Class dbjc = hzzdh.class;
    private ConcurrentLinkedQueue<hbfhc> qkduk = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Integer> jwxax = new ConcurrentLinkedQueue<>();

    /* loaded from: classes.dex */
    public abstract class hbfhc {
        public hbfhc(hzzdh hzzdhVar) {
        }

        public abstract void dbjc(int i);

        public abstract void dbjc(String str);
    }

    public synchronized void dbjc(hbfhc hbfhcVar) {
        String bucpw = ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).bucpw();
        if (bucpw != null && true != bucpw.isEmpty()) {
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "Using Existing Token.");
            hbfhcVar.dbjc(bucpw);
            return;
        }
        if (this.qkduk == null) {
            this.qkduk = new ConcurrentLinkedQueue<>();
        }
        if (this.qkduk.isEmpty()) {
            this.qkduk.add(hbfhcVar);
            com.blackberry.analytics.analyticsengine.fdyxd.pqq().dbjc(0, this);
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "Push token request already executing, adding current into queue.");
            this.qkduk.add(hbfhcVar);
        }
    }

    public void jwxax(int i) {
        if (this.jwxax.contains(Integer.valueOf(i))) {
            this.jwxax.remove(Integer.valueOf(i));
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("Removed request Id: %d \n Current request ID queue: %s", Integer.valueOf(i), this.jwxax));
        }
    }

    public void qkduk(int i) {
        synchronized (this) {
            ConcurrentLinkedQueue<hbfhc> concurrentLinkedQueue = this.qkduk;
            if (concurrentLinkedQueue == null || true == concurrentLinkedQueue.isEmpty()) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue2 = new ConcurrentLinkedQueue(this.qkduk);
            this.qkduk.clear();
            Iterator it = concurrentLinkedQueue2.iterator();
            while (it.hasNext()) {
                ((hbfhc) it.next()).dbjc(i);
            }
            concurrentLinkedQueue2.clear();
        }
    }

    public void dbjc(int i) {
        this.jwxax.add(Integer.valueOf(i));
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("Added request Id: %d \n Current request ID queue: %s", Integer.valueOf(i), this.jwxax));
    }

    public synchronized void dbjc(boolean z) {
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue;
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("Is force deleting the GNP token: %b \n Current request ID queue: %s", Boolean.valueOf(z), this.jwxax));
        ConcurrentLinkedQueue<hbfhc> concurrentLinkedQueue2 = this.qkduk;
        if (((concurrentLinkedQueue2 == null || concurrentLinkedQueue2.isEmpty()) && ((concurrentLinkedQueue = this.jwxax) == null || concurrentLinkedQueue.isEmpty())) || z) {
            ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).lqox();
        }
    }

    public void dbjc(String str) {
        synchronized (this) {
            ConcurrentLinkedQueue<hbfhc> concurrentLinkedQueue = this.qkduk;
            if (concurrentLinkedQueue == null || true == concurrentLinkedQueue.isEmpty()) {
                return;
            }
            ConcurrentLinkedQueue concurrentLinkedQueue2 = new ConcurrentLinkedQueue(this.qkduk);
            this.qkduk.clear();
            Iterator it = concurrentLinkedQueue2.iterator();
            while (it.hasNext()) {
                ((hbfhc) it.next()).dbjc(str);
            }
            concurrentLinkedQueue2.clear();
        }
    }
}
