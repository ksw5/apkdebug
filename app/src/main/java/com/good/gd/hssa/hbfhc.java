package com.good.gd.hssa;

import androidx.core.view.PointerIconCompat;
import com.blackberry.analytics.analyticsengine.fdyxd;
import com.blackberry.bis.core.yfdke;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.kloes.ehnkx;
import com.good.gd.npgvd.pmoiy;
import com.good.gd.zwn.aqdzk;
import com.good.gd.zwn.mjbm;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class hbfhc {
    private int jwxax;
    private aqdzk ztwf;
    private String dbjc = hbfhc.class.getSimpleName();
    private HashMap<String, String> qkduk = new HashMap<>();
    private int[] wxau = {2, 4, 8};

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.hssa.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class RunnableC0007hbfhc implements Runnable {
        final /* synthetic */ com.good.gd.ghhwi.hbfhc dbjc;

        RunnableC0007hbfhc(com.good.gd.ghhwi.hbfhc hbfhcVar) {
            this.dbjc = hbfhcVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            hbfhc.this.dbjc(this.dbjc);
        }
    }

    public void dbjc(aqdzk aqdzkVar) {
        this.ztwf = aqdzkVar;
        if (yfdke.vfle() != null) {
            com.good.gd.jnupj.hbfhc hbfhcVar = new com.good.gd.jnupj.hbfhc();
            hbfhcVar.dbjc(45000);
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] START_SDE - event network request.");
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[SDE] Prepare headers for network request.");
            this.qkduk = null;
            dbjc(hbfhcVar);
            return;
        }
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(com.good.gd.ghhwi.hbfhc hbfhcVar) {
        if (this.jwxax > 0) {
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] START_SDE - event network request.");
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format("[SDE] Retry[%d/%d] for network request", Integer.valueOf(this.jwxax), 3));
        }
        pmoiy bucpw = yfdke.bucpw();
        if (true != ((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).qkduk()) {
            bucpw.dbjc(this.dbjc, String.format("[SDE] END_SDE - event network request completed with response code: %d (%s)", 1039, mjbm.qkduk(1039)));
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] END_SDE - All retries completed.");
            aqdzk aqdzkVar = this.ztwf;
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(false, 1038, mjbm.qkduk(1039));
                return;
            }
        }
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] Execute network request");
        try {
            com.good.gd.jnupj.hbfhc hbfhcVar2 = (com.good.gd.jnupj.hbfhc) hbfhcVar;
            hbfhcVar2.dbjc(6, this.qkduk, null);
            String dbjc = hbfhcVar2.dbjc();
            int qkduk = hbfhcVar2.qkduk();
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[SDE] Network Response Headers:");
            com.good.gd.fwwhw.hbfhc.dbjc(hbfhcVar2.jwxax());
            ehnkx.qkduk(this.dbjc, "[SDE] Received response: " + dbjc);
            bucpw.dbjc(this.dbjc, String.format("[SDE] END_SDE - event network request completed with response code: %d (%s)", Integer.valueOf(qkduk), mjbm.qkduk(qkduk)));
            if (200 != qkduk && 204 != qkduk && 304 != qkduk) {
                com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format(Locale.US, "[SDE] - Failed with response: %s", dbjc));
                dbjc(qkduk, hbfhcVar2, dbjc);
                return;
            }
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] END_SDE - All retries completed.");
            aqdzk aqdzkVar2 = this.ztwf;
            if (aqdzkVar2 == null) {
                return;
            }
            aqdzkVar2.dbjc(true, qkduk, dbjc);
        } catch (SocketException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.dbjc, "[SDE] Network request  failed: " + e.getLocalizedMessage());
            dbjc(1021, hbfhcVar, mjbm.qkduk(1021));
        } catch (IOException e2) {
            com.good.gd.kloes.hbfhc.qkduk(this.dbjc, "[SDE] Network request  failed: " + e2.getLocalizedMessage());
            dbjc(1000, hbfhcVar, mjbm.qkduk(1000));
        }
    }

    private void dbjc(int i, com.good.gd.ghhwi.hbfhc hbfhcVar, String str) {
        pmoiy bucpw = yfdke.bucpw();
        int i2 = this.jwxax;
        if (i2 >= 3) {
            bucpw.dbjc(this.dbjc, "[SDE] END_SDE - All retries completed for network request");
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] END_SDE - All retries completed.");
            aqdzk aqdzkVar = this.ztwf;
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, 1038, mjbm.qkduk(1038));
            return;
        }
        this.jwxax = i2 + 1;
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] Handle network request error response: " + i);
        switch (i) {
            case 400:
            case HttpStatus.SC_NOT_FOUND /* 404 */:
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] END_SDE - All retries completed.");
                aqdzk aqdzkVar2 = this.ztwf;
                if (aqdzkVar2 == null) {
                    return;
                }
                aqdzkVar2.dbjc(false, i, str);
                return;
            case 401:
                com.good.gd.nxoj.yfdke.dbjc().dbjc("bis-sdk-dynamics-client", "SIS.Service", "");
                dbjc(hbfhcVar);
                return;
            case 1000:
            case PointerIconCompat.TYPE_VERTICAL_TEXT /* 1009 */:
            case 1021:
                bucpw.dbjc(this.dbjc, String.format("[SDE] END_SDE - event network request completed with response code: %d (%s)", Integer.valueOf(i), mjbm.qkduk(i)));
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SDE] END_SDE - All retries completed.");
                aqdzk aqdzkVar3 = this.ztwf;
                if (aqdzkVar3 == null) {
                    return;
                }
                aqdzkVar3.dbjc(false, i, str);
                return;
            default:
                com.good.gd.rutan.hbfhc tlske = com.good.gd.rutan.hbfhc.tlske();
                int i3 = this.jwxax - 1;
                if (i3 < 0) {
                    i3 = 0;
                } else {
                    int[] iArr = this.wxau;
                    if (i3 >= iArr.length) {
                        i3 = iArr.length - 1;
                    }
                }
                int i4 = this.wxau[i3];
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format("[SDE] Network request will retry to execute after %d seconds.", Integer.valueOf(i4)));
                tlske.lqox().schedule(new RunnableC0007hbfhc(hbfhcVar), i4, TimeUnit.SECONDS);
                return;
        }
    }
}
