package com.good.gd.kofoa;

import com.good.gd.zwn.aqdzk;
import com.good.gd.zwn.mjbm;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.vvwtw.hbfhc {
    private static hbfhc tlske;
    private com.good.gd.hdyaf.hbfhc dbjc;
    private com.good.gd.lqnsz.yfdke jwxax;
    private boolean lqox;
    private com.good.gd.zrscc.hbfhc qkduk;
    private aqdzk wxau;
    private JSONObject ztwf;
    private ScheduledFuture liflu = null;
    private boolean jcpqe = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.kofoa.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0014hbfhc implements aqdzk {
        C0014hbfhc() {
        }

        @Override // com.good.gd.zwn.aqdzk
        public void dbjc(boolean z, int i, String str) {
            hbfhc.dbjc(hbfhc.this, z, i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            hbfhc.this.jcpqe = false;
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] BIS service unreachable state timer is elapsed.");
            if (hbfhc.this != null) {
                List<String> jwxax = com.good.gd.tpgyf.yfdke.ztwf().jwxax();
                if (jwxax == null || true == jwxax.isEmpty()) {
                    return;
                }
                com.good.gd.yokds.yfdke.dbjc().dbjc(jwxax, (com.good.gd.yokds.hbfhc) null);
                return;
            }
            throw null;
        }
    }

    public static hbfhc wxau() {
        if (tlske == null) {
            synchronized (hbfhc.class) {
                tlske = new hbfhc();
            }
        }
        return tlske;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ztwf() {
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SRA] Reset network task components.");
        this.ztwf = null;
        this.qkduk = null;
        this.dbjc = null;
        this.jwxax = null;
        this.wxau = null;
    }

    public void jwxax() {
        ScheduledFuture scheduledFuture;
        if (!this.jcpqe || (scheduledFuture = this.liflu) == null) {
            return;
        }
        this.jcpqe = false;
        if (scheduledFuture.cancel(false)) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] BIS service unreachable state timer is cancelled.");
        } else {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] BIS service unreachable state timer is already cancelled.");
        }
    }

    public void qkduk() {
        List<String> jwxax = com.good.gd.tpgyf.yfdke.ztwf().jwxax();
        if (jwxax != null && !jwxax.isEmpty()) {
            if (this.jcpqe) {
                com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] BIS service unreachable state timer is already running.");
                return;
            }
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] Starting BIS service unreachable state timer.");
            this.liflu = com.good.gd.idl.hbfhc.pqq().tlske().schedule(new yfdke(), 30L, TimeUnit.SECONDS);
            this.jcpqe = true;
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "[Revert Profile] No need to schedule revert profile action because no assigned override profile found.");
    }

    public boolean dbjc() {
        return this.lqox;
    }

    public void dbjc(boolean z) {
        this.lqox = z;
    }

    public void dbjc(JSONObject jSONObject, com.good.gd.cth.hbfhc hbfhcVar, aqdzk aqdzkVar) {
        if ((jSONObject == null || jSONObject.length() <= 0) && (hbfhcVar == null || hbfhcVar.dbjc() == null || hbfhcVar.dbjc().isEmpty())) {
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, 1034, mjbm.qkduk(1034));
            return;
        }
        this.wxau = aqdzkVar;
        if (this.dbjc == null) {
            this.dbjc = com.blackberry.bis.core.yfdke.lqox();
        }
        JSONObject jwxax = this.dbjc.jwxax(jSONObject, hbfhcVar);
        this.ztwf = jwxax;
        dbjc((jwxax == null || jwxax.length() <= 0) ? null : this.dbjc.dbjc(this.ztwf.toString()));
    }

    public void dbjc(boolean z, String str, String str2) {
        if (this.dbjc == null) {
            this.dbjc = com.blackberry.bis.core.yfdke.lqox();
        }
        JSONObject dbjc = this.dbjc.dbjc(this.ztwf, z, str2, str);
        this.ztwf = dbjc;
        dbjc((dbjc == null || dbjc.length() <= 0) ? null : this.dbjc.dbjc(this.ztwf.toString()));
    }

    private void dbjc(byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            if (this.qkduk == null) {
                this.qkduk = new com.good.gd.zrscc.hbfhc();
            }
            this.qkduk.dbjc();
            this.qkduk.dbjc(bArr, new C0014hbfhc());
            return;
        }
        com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SRA] Compressed payload is null or empty.");
        aqdzk aqdzkVar = this.wxau;
        if (aqdzkVar != null) {
            aqdzkVar.dbjc(false, 1033, mjbm.qkduk(1033));
        }
        ztwf();
    }

    static /* synthetic */ void dbjc(hbfhc hbfhcVar, boolean z, int i, String str) {
        if (hbfhcVar.jwxax == null) {
            hbfhcVar.jwxax = com.blackberry.bis.core.yfdke.liflu();
        }
        boolean z2 = false;
        if (500 <= i && 600 > i) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format(Locale.US, "[SRA] Scheduling revert dynamics profile override action with %d seconds, as received 5XX response.", 30L));
            hbfhcVar.qkduk();
        } else if (1003 == i) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format(Locale.US, "[SRA] Scheduling revert dynamics profile override action with %d seconds, as internet is not available.", 30L));
            hbfhcVar.qkduk();
        } else if (1021 == i) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format(Locale.US, "[SRA] Scheduling revert dynamics profile override action with %d seconds, as BIS service is not reachable.", 30L));
            hbfhcVar.qkduk();
        } else if (1009 == i) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format(Locale.US, "[SRA] Scheduling revert dynamics profile override action with %d seconds, as unable to obtain EID token.", 30L));
            hbfhcVar.qkduk();
        } else {
            hbfhcVar.jwxax();
        }
        if (i == 200) {
            JSONObject jSONObject = hbfhcVar.ztwf;
            if (jSONObject != null && jSONObject.has("assessmentContext")) {
                z2 = true;
            }
            hbfhcVar.jwxax.dbjc(z2, hbfhcVar.jwxax.dbjc(str), new com.good.gd.kofoa.yfdke(hbfhcVar, i));
        } else if (i != 1043) {
            aqdzk aqdzkVar = hbfhcVar.wxau;
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(z, i, str);
            }
            hbfhcVar.ztwf();
        } else {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SRA] Perform revert dynamics profile override action, as request timed out.");
            List<String> jwxax = com.good.gd.tpgyf.yfdke.ztwf().jwxax();
            if (jwxax != null && true != jwxax.isEmpty()) {
                com.good.gd.yokds.yfdke.dbjc().dbjc(jwxax, (com.good.gd.yokds.hbfhc) null);
            }
            aqdzk aqdzkVar2 = hbfhcVar.wxau;
            if (aqdzkVar2 != null) {
                aqdzkVar2.dbjc(z, i, str);
            }
            hbfhcVar.ztwf();
        }
    }
}
