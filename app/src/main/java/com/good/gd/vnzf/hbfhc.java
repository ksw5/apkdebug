package com.good.gd.vnzf;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class hbfhc implements Runnable {
    final /* synthetic */ yfdke dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc(yfdke yfdkeVar) {
        this.dbjc = yfdkeVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.dbjc.jwxax = false;
        this.dbjc.tlske();
    }
}
