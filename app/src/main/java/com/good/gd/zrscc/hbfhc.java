package com.good.gd.zrscc;

import androidx.core.view.PointerIconCompat;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.kloes.ehnkx;
import com.good.gd.npgvd.pmoiy;
import com.good.gd.utils.MessageFactory;
import com.good.gd.zwn.aqdzk;
import com.good.gd.zwn.mjbm;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class hbfhc {
    private int jwxax;
    private aqdzk wxau;
    private Class dbjc = hbfhc.class;
    private HashMap<String, String> qkduk = new HashMap<>();
    private int[] ztwf = {2, 4, 8};

    /* renamed from: com.good.gd.zrscc.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0041hbfhc implements Runnable {
        final /* synthetic */ byte[] dbjc;

        RunnableC0041hbfhc(byte[] bArr) {
            this.dbjc = bArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.blackberry.bis.core.yfdke.vfle() != null) {
                com.good.gd.jnupj.hbfhc hbfhcVar = new com.good.gd.jnupj.hbfhc();
                hbfhcVar.dbjc(45000);
                com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, "[SRA] START_SRA - event network request with Datapoint Id: " + com.good.gd.idl.hbfhc.pqq().jcpqe());
                hbfhc.qkduk(hbfhc.this);
                hbfhc.this.dbjc(false, (com.good.gd.ghhwi.hbfhc) hbfhcVar, this.dbjc);
                return;
            }
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        final /* synthetic */ com.good.gd.ghhwi.hbfhc dbjc;
        final /* synthetic */ byte[] qkduk;

        yfdke(com.good.gd.ghhwi.hbfhc hbfhcVar, byte[] bArr) {
            this.dbjc = hbfhcVar;
            this.qkduk = bArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            hbfhc.dbjc(hbfhc.this, this.dbjc, this.qkduk);
        }
    }

    static /* synthetic */ void qkduk(hbfhc hbfhcVar) {
        com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, "[SRA] Prepare headers for network request.");
        HashMap<String, String> hashMap = hbfhcVar.qkduk;
        if (hashMap != null) {
            hashMap.put("Accept", "application/json");
            hbfhcVar.qkduk.put("Correlation-Id", com.good.gd.idl.hbfhc.pqq().jcpqe());
            hbfhcVar.qkduk.put(HTTP.CONTENT_TYPE, "application/json");
            hbfhcVar.qkduk.put("Accept-Encoding", "gzip");
            hbfhcVar.qkduk.put(HTTP.CONTENT_ENCODING, "gzip");
        }
    }

    public void dbjc(byte[] bArr, aqdzk aqdzkVar) {
        this.wxau = aqdzkVar;
        com.good.gd.idl.hbfhc.pqq().tlske().execute(new RunnableC0041hbfhc(bArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements com.good.gd.bmuat.hbfhc {
        final /* synthetic */ com.good.gd.vvwtw.hbfhc dbjc;
        final /* synthetic */ byte[] jwxax;
        final /* synthetic */ com.good.gd.ghhwi.hbfhc qkduk;

        /* renamed from: com.good.gd.zrscc.hbfhc$fdyxd$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0040hbfhc implements Runnable {
            final /* synthetic */ String dbjc;

            RunnableC0040hbfhc(String str) {
                this.dbjc = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (hbfhc.this.qkduk != null) {
                    hbfhc.this.qkduk.put(AUTH.WWW_AUTH_RESP, "Bearer " + this.dbjc);
                }
                fdyxd fdyxdVar = fdyxd.this;
                hbfhc.dbjc(hbfhc.this, fdyxdVar.qkduk, fdyxdVar.jwxax);
            }
        }

        /* loaded from: classes.dex */
        class yfdke implements Runnable {
            final /* synthetic */ String dbjc;

            yfdke(String str) {
                this.dbjc = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                fdyxd fdyxdVar = fdyxd.this;
                hbfhc.this.dbjc((int) PointerIconCompat.TYPE_VERTICAL_TEXT, fdyxdVar.qkduk, this.dbjc, fdyxdVar.jwxax);
            }
        }

        fdyxd(com.good.gd.vvwtw.hbfhc hbfhcVar, com.good.gd.ghhwi.hbfhc hbfhcVar2, byte[] bArr) {
            this.dbjc = hbfhcVar;
            this.qkduk = hbfhcVar2;
            this.jwxax = bArr;
        }

        @Override // com.good.gd.bmuat.hbfhc
        public void dbjc(String str) {
            com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, "[SRA] EID Token received successfully for the network request.");
            ((com.good.gd.kofoa.hbfhc) this.dbjc).dbjc(false);
            com.good.gd.idl.hbfhc.pqq().tlske().execute(new RunnableC0040hbfhc(str));
        }

        @Override // com.good.gd.bmuat.hbfhc
        public void dbjc(String str, int i) {
            com.good.gd.kloes.hbfhc.dbjc(hbfhc.this.dbjc, "[SRA] EID Token not received for the network request.");
            ((com.good.gd.kofoa.hbfhc) this.dbjc).dbjc(false);
            com.good.gd.idl.hbfhc.pqq().tlske().execute(new yfdke(str));
        }
    }

    static /* synthetic */ void dbjc(hbfhc hbfhcVar, com.good.gd.ghhwi.hbfhc hbfhcVar2, byte[] bArr) {
        String str = hbfhcVar.qkduk.get("Correlation-Id");
        String jcpqe = com.good.gd.idl.hbfhc.pqq().jcpqe();
        if (hbfhcVar.jwxax > 0) {
            com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[SRA] START_SRA - event network request with Datapoint Id: " + com.good.gd.idl.hbfhc.pqq().jcpqe());
            com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, String.format("[SRA] Retry[%d/%d] for network request: %s", Integer.valueOf(hbfhcVar.jwxax), 3, str));
        }
        pmoiy bucpw = com.blackberry.bis.core.yfdke.bucpw();
        if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).jwxax()) {
            int i = true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ujr() ? PointerIconCompat.TYPE_HELP : MessageFactory.MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD;
            bucpw.dbjc(hbfhcVar.dbjc, String.format("[SRA] END_SRA - event network request with Datapoint Id: %s completed with response code: %d (%s)", jcpqe, Integer.valueOf(i), mjbm.qkduk(i)));
            com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[SRA] - All retries completed.");
            aqdzk aqdzkVar = hbfhcVar.wxau;
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, i, mjbm.qkduk(i));
            return;
        }
        com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[SRA] Execute network request: " + str);
        try {
            com.good.gd.jnupj.hbfhc hbfhcVar3 = (com.good.gd.jnupj.hbfhc) hbfhcVar2;
            hbfhcVar3.dbjc(4, hbfhcVar.qkduk, bArr);
            String dbjc = hbfhcVar3.dbjc();
            int qkduk = hbfhcVar3.qkduk();
            com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, "[SRA] Network Response Headers:");
            com.good.gd.fwwhw.hbfhc.dbjc(hbfhcVar3.jwxax());
            ehnkx.dbjc(hbfhcVar.dbjc, "[SRA] Received response:", dbjc);
            bucpw.dbjc(hbfhcVar.dbjc, String.format("[SRA] END_SRA - event network request with Datapoint Id: %s completed with response code: %d (%s)", jcpqe, Integer.valueOf(qkduk), mjbm.qkduk(qkduk)));
            if (204 == qkduk) {
                com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[SRA] END_SRA - All retries completed.");
                aqdzk aqdzkVar2 = hbfhcVar.wxau;
                if (aqdzkVar2 == null) {
                    return;
                }
                aqdzkVar2.dbjc(true, HttpStatus.SC_NO_CONTENT, mjbm.qkduk((int) HttpStatus.SC_NO_CONTENT));
            } else if (200 == qkduk) {
                com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[SRA] END_SRA - All retries completed.");
                aqdzk aqdzkVar3 = hbfhcVar.wxau;
                if (aqdzkVar3 == null) {
                    return;
                }
                aqdzkVar3.dbjc(true, 200, dbjc);
            } else {
                com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, String.format(Locale.US, "[SRA] END_SRA - Failed with response: %s", dbjc));
                hbfhcVar.dbjc(qkduk, hbfhcVar3, dbjc, bArr);
            }
        } catch (SocketException e) {
            com.good.gd.kloes.hbfhc.qkduk(hbfhcVar.dbjc, "[SRA] Network request " + str + " failed: " + e.getLocalizedMessage());
            hbfhcVar.dbjc(1021, hbfhcVar2, mjbm.qkduk(1021), bArr);
        } catch (SocketTimeoutException e2) {
            com.good.gd.kloes.hbfhc.qkduk(hbfhcVar.dbjc, "[SRA] Network request " + str + " failed: " + e2.getLocalizedMessage());
            hbfhcVar.dbjc(1043, hbfhcVar2, mjbm.qkduk(1043), bArr);
        } catch (UnknownHostException e3) {
            com.good.gd.kloes.hbfhc.qkduk(hbfhcVar.dbjc, "[SRA] Network request " + str + " failed: " + e3.getLocalizedMessage());
            hbfhcVar.dbjc(PointerIconCompat.TYPE_HELP, hbfhcVar2, mjbm.qkduk((int) PointerIconCompat.TYPE_HELP), bArr);
        } catch (IOException e4) {
            com.good.gd.kloes.hbfhc.qkduk(hbfhcVar.dbjc, "[SRA] Network request " + str + " failed: " + e4.getLocalizedMessage());
            hbfhcVar.dbjc(1000, hbfhcVar2, mjbm.qkduk(1000), bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(int i, com.good.gd.ghhwi.hbfhc hbfhcVar, String str, byte[] bArr) {
        String jcpqe = com.good.gd.idl.hbfhc.pqq().jcpqe();
        pmoiy bucpw = com.blackberry.bis.core.yfdke.bucpw();
        int i2 = this.jwxax;
        if (i2 >= 3) {
            bucpw.dbjc(this.dbjc, String.format("[SRA] END_SRA - All retries completed for network request with Datapoint Id: %s", jcpqe));
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SRA] END_SRA - All retries completed.");
            aqdzk aqdzkVar = this.wxau;
            if (aqdzkVar == null) {
                return;
            }
            if (500 <= i && i < 600) {
                aqdzkVar.dbjc(false, i, mjbm.qkduk(i));
                return;
            } else {
                aqdzkVar.dbjc(false, MessageFactory.MSG_CLIENT_CANCEL_STARTUP, mjbm.qkduk((int) MessageFactory.MSG_CLIENT_CANCEL_STARTUP));
                return;
            }
        }
        this.jwxax = i2 + 1;
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SRA] Handle network request error response: " + i);
        switch (i) {
            case 400:
            case HttpStatus.SC_NOT_FOUND /* 404 */:
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SRA] END_SRA - All retries completed.");
                aqdzk aqdzkVar2 = this.wxau;
                if (aqdzkVar2 == null) {
                    return;
                }
                aqdzkVar2.dbjc(false, i, str);
                return;
            case 401:
                com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", com.good.gd.whhmi.yfdke.lqox() ? "SIS.Service" : "BIS:Actor.ReadWrite", "");
                dbjc(true, hbfhcVar, bArr);
                return;
            case 1000:
            case PointerIconCompat.TYPE_HELP /* 1003 */:
            case PointerIconCompat.TYPE_VERTICAL_TEXT /* 1009 */:
            case 1021:
            case 1043:
                bucpw.dbjc(this.dbjc, String.format("[SRA] END_SRA - event network request with Datapoint Id: %s completed with response code: %d (%s)", jcpqe, Integer.valueOf(i), mjbm.qkduk(i)));
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SRA] END_SRA - All retries completed.");
                aqdzk aqdzkVar3 = this.wxau;
                if (aqdzkVar3 == null) {
                    return;
                }
                aqdzkVar3.dbjc(false, i, str);
                return;
            default:
                com.good.gd.idl.hbfhc pqq = com.good.gd.idl.hbfhc.pqq();
                int i3 = this.jwxax - 1;
                if (i3 < 0) {
                    i3 = 0;
                } else {
                    int[] iArr = this.ztwf;
                    if (i3 >= iArr.length) {
                        i3 = iArr.length - 1;
                    }
                }
                int i4 = this.ztwf[i3];
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format("[SRA] Network request will retry to execute after %d seconds.", Integer.valueOf(i4)));
                pqq.tlske().schedule(new yfdke(hbfhcVar, bArr), i4, TimeUnit.SECONDS);
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(boolean z, com.good.gd.ghhwi.hbfhc hbfhcVar, byte[] bArr) {
        if (com.blackberry.bis.core.yfdke.ztwf() != null) {
            com.good.gd.kofoa.hbfhc wxau = com.good.gd.kofoa.hbfhc.wxau();
            wxau.dbjc(true);
            com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", com.good.gd.whhmi.yfdke.lqox() ? "SIS.Service" : "BIS:Actor.ReadWrite", "", z, new fdyxd(wxau, hbfhcVar, bArr));
            return;
        }
        throw null;
    }

    public void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[SRA] Reset network request retries.");
        this.jwxax = 0;
    }
}
