package com.good.gd.zwn;

import androidx.core.view.PointerIconCompat;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.zwn.vzw;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public abstract class orlrx extends ehnkx {
    private final com.good.gd.oqpvt.yfdke jcpqe;
    private final byte[] jsgtu;
    protected final com.good.gd.ghhwi.hbfhc liflu;
    private final ooowe lqox;
    private final String wuird = getClass().getSimpleName();
    protected Map<String, String> tlske = mjbm.qkduk();
    private int mvf = hashCode();

    public orlrx(aqdzk aqdzkVar, byte[] bArr, com.good.gd.ghhwi.hbfhc hbfhcVar, com.good.gd.oqpvt.yfdke yfdkeVar, ooowe oooweVar) {
        super(aqdzkVar);
        this.lqox = oooweVar;
        this.jcpqe = yfdkeVar;
        this.liflu = hbfhcVar;
        this.jsgtu = Arrays.copyOf(bArr, bArr.length);
        this.wxau = Executors.newSingleThreadExecutor();
    }

    @Override // com.good.gd.zwn.ehnkx
    public void dbjc(vzw.fdyxd fdyxdVar, vzw.yfdke yfdkeVar) {
        Map<String, String> map = this.tlske;
        String str = null;
        if (((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()) != null) {
            switch (BlackberryAnalyticsCommon.rynix().lqox()) {
                case 1:
                    map.put("X-Good-Correlation-ID", mjbm.jwxax());
                    break;
                case 2:
                    map.put("X-BBRY-Correlation-ID", mjbm.jwxax());
                    break;
                case 3:
                case 4:
                    map.put("X-BBRY-Correlation-ID", mjbm.jwxax());
                    break;
            }
            if (this.tlske.containsKey("X-BBRY-Correlation-ID")) {
                str = this.tlske.get("X-BBRY-Correlation-ID");
            }
            mjbm.ztwf(str);
            com.good.gd.kloes.hbfhc.jwxax(this.wuird, String.format("START_HISTORICAL_POST - events network request: %s for datapoint ID: %s", str, mjbm.lqox()));
            int ztwf = ztwf();
            if (401 == ztwf) {
                dbjc(true, fdyxdVar);
                return;
            } else if (404 == ztwf && mjbm.liflu()) {
                dbjc(true, fdyxdVar);
                return;
            } else if (true != this.tlske.containsKey(AUTH.WWW_AUTH_RESP)) {
                dbjc(false, fdyxdVar);
                return;
            } else {
                fdyxdVar.dbjc(false);
                return;
            }
        }
        throw null;
    }

    @Override // com.good.gd.zwn.ehnkx
    public boolean jcpqe() throws Exception {
        com.good.gd.kloes.hbfhc.wxau(this.wuird, "Start Sending POST Network Request.");
        if (jsgtu()) {
            return false;
        }
        try {
            ((com.good.gd.jnupj.hbfhc) this.liflu).dbjc(2, this.tlske, this.jsgtu);
            com.good.gd.kloes.hbfhc.wxau(this.wuird, "Historical Post Network Response Headers: ");
            mjbm.dbjc(((com.good.gd.jnupj.hbfhc) this.liflu).jwxax());
            return true;
        } catch (SocketException e) {
            String localizedMessage = e.getLocalizedMessage();
            dbjc(1021);
            dbjc(localizedMessage);
            aqdzk aqdzkVar = this.dbjc;
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(false, 1021, localizedMessage);
            }
            return false;
        } catch (IOException e2) {
            String localizedMessage2 = e2.getLocalizedMessage();
            dbjc(1000);
            dbjc(localizedMessage2);
            aqdzk aqdzkVar2 = this.dbjc;
            if (aqdzkVar2 != null) {
                aqdzkVar2.dbjc(false, 1000, localizedMessage2);
            }
            return false;
        }
    }

    @Override // com.good.gd.zwn.ehnkx
    public boolean jsgtu() {
        if (true != this.jcpqe.tlske()) {
            dbjc(PointerIconCompat.TYPE_HELP);
            dbjc("network is turned off, can't perform operation");
            this.dbjc.dbjc(false, PointerIconCompat.TYPE_HELP, "network is turned off, can't perform operation");
            return true;
        } else if (true == this.lqox.tlske()) {
            return false;
        } else {
            dbjc(1016);
            dbjc("Analytics is disabled, can't perform operation");
            this.dbjc.dbjc(false, 1016, "Analytics is disabled, can't perform operation");
            return true;
        }
    }

    @Override // com.good.gd.zwn.ehnkx
    public int jwxax() {
        return ((com.good.gd.jnupj.hbfhc) this.liflu).qkduk();
    }

    @Override // com.good.gd.zwn.ehnkx
    public int liflu() {
        return HttpStatus.SC_NO_CONTENT;
    }

    @Override // com.good.gd.zwn.ehnkx
    public int lqox() {
        return this.mvf;
    }

    @Override // com.good.gd.zwn.ehnkx
    public String qkduk() {
        return ((com.good.gd.jnupj.hbfhc) this.liflu).dbjc();
    }

    @Override // com.good.gd.zwn.ehnkx
    public void tlske() {
        if (ztwf() == 204) {
            this.lqox.jsgtu();
        }
    }

    @Override // com.good.gd.zwn.ehnkx
    public void wuird() {
        this.tlske = ((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()).dbjc();
    }

    @Override // com.good.gd.zwn.ehnkx
    public Map<String, String> dbjc() {
        return ((com.good.gd.jnupj.hbfhc) this.liflu).jwxax();
    }
}
