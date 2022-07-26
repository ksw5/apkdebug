package com.good.gd.ndkproxy.icc;

import android.os.Bundle;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.context.GDContext;
import com.good.gd.icc.GDServiceListener;
import com.good.gd.icc.impl.GDIccProviderInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.BundleKeys;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTInteger;
import com.good.gt.icc.IccServicesServer;
import com.good.gt.icc.ServiceListener;
import com.good.gt.ndkproxy.GTInit;
import com.good.gt.util.ByteArrayBuffer;
import java.io.Serializable;

/* loaded from: classes.dex */
public class GDIccProvider implements ServiceListener, AppControl, GDIccProviderInterface {
    private static GDIccProvider _instance;
    private static GDServiceListener applicationListener;
    private IccServicesServer _iCCServicesServer;

    /* loaded from: classes.dex */
    class ehnkx extends Thread {
        final /* synthetic */ GDServiceListener dbjc;
        final /* synthetic */ String jcpqe;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String[] liflu;
        final /* synthetic */ Object lqox;
        final /* synthetic */ String qkduk;
        final /* synthetic */ String wxau;
        final /* synthetic */ String ztwf;

        ehnkx(GDIccProvider gDIccProvider, GDServiceListener gDServiceListener, String str, String str2, String str3, String str4, Object obj, String[] strArr, String str5) {
            this.dbjc = gDServiceListener;
            this.qkduk = str;
            this.jwxax = str2;
            this.wxau = str3;
            this.ztwf = str4;
            this.lqox = obj;
            this.liflu = strArr;
            this.jcpqe = str5;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.dbjc.onReceiveMessage(this.qkduk, this.jwxax, this.wxau, this.ztwf, this.lqox, this.liflu, this.jcpqe);
        }
    }

    /* loaded from: classes.dex */
    class fdyxd extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String qkduk;
        final /* synthetic */ long wxau;

        fdyxd(GDIccProvider gDIccProvider, String str, String str2, String str3, long j) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = str3;
            this.wxau = j;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceListener canProvide = GDPreferentialServiceListener.getInstance().canProvide(this.dbjc);
            if (canProvide == null) {
                if (GDIccProvider.applicationListener != null) {
                    GDIccProvider.applicationListener.onReceivingAttachmentFile(this.qkduk, this.jwxax, this.wxau, this.dbjc);
                    return;
                } else {
                    GDLog.DBGPRINTF(12, "  GDIccProvider.onReceivingAttachmentFile - no listener");
                    return;
                }
            }
            canProvide.onReceivingAttachmentFile(this.qkduk, this.jwxax, this.wxau, this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String[] jwxax;
        final /* synthetic */ String qkduk;

        hbfhc(GDIccProvider gDIccProvider, String str, String str2, String[] strArr) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = strArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceListener canProvide = GDPreferentialServiceListener.getInstance().canProvide(this.dbjc);
            if (canProvide == null) {
                if (GDIccProvider.applicationListener == null) {
                    return;
                }
                GDIccProvider.applicationListener.onMessageSent(this.qkduk, this.dbjc, this.jwxax);
                return;
            }
            canProvide.onMessageSent(this.qkduk, this.dbjc, this.jwxax);
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements Runnable {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String liflu;
        final /* synthetic */ String[] lqox;
        final /* synthetic */ String qkduk;
        final /* synthetic */ String wxau;
        final /* synthetic */ Object ztwf;

        pmoiy(GDIccProvider gDIccProvider, String str, String str2, String str3, String str4, Object obj, String[] strArr, String str5) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = str3;
            this.wxau = str4;
            this.ztwf = obj;
            this.lqox = strArr;
            this.liflu = str5;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDIccProvider.applicationListener.onReceiveMessage(this.dbjc, this.qkduk, this.jwxax, this.wxau, this.ztwf, this.lqox, this.liflu);
        }
    }

    /* loaded from: classes.dex */
    class yfdke extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ String qkduk;

        yfdke(GDIccProvider gDIccProvider, String str, String str2, int i) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceListener canProvide = GDPreferentialServiceListener.getInstance().canProvide(this.dbjc);
            if (canProvide == null) {
                if (GDIccProvider.applicationListener != null) {
                    GDIccProvider.applicationListener.onReceivingAttachments(this.qkduk, this.jwxax, this.dbjc);
                    return;
                } else {
                    GDLog.DBGPRINTF(12, "  GDIccProvider.onReceivingAttachments - no listener");
                    return;
                }
            }
            canProvide.onReceivingAttachments(this.qkduk, this.jwxax, this.dbjc);
        }
    }

    private GDIccProvider() {
    }

    public static synchronized GDIccProvider getInstance() {
        GDIccProvider gDIccProvider;
        synchronized (GDIccProvider.class) {
            if (_instance == null) {
                initialize();
            }
            gDIccProvider = _instance;
        }
        return gDIccProvider;
    }

    public static synchronized void initialize() {
        synchronized (GDIccProvider.class) {
            if (_instance == null) {
                _instance = new GDIccProvider();
            }
        }
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    @Override // com.good.gd.icc.impl.GDIccProviderInterface
    public void checkAuthorized() {
        GDContext.getInstance().checkAuthorized();
    }

    @Override // com.good.gd.icc.impl.GDIccProviderInterface
    public IccServicesServer getServicesServer() {
        return this._iCCServicesServer;
    }

    public boolean isApplicationListenerSet() {
        return applicationListener != null;
    }

    @Override // com.good.gt.icc.ServiceListener
    public boolean onConnected(String str, byte[] bArr, byte[] bArr2, boolean z) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onConnected");
        return GDIccManager.liflu().dbjc(bArr, bArr2, z);
    }

    @Override // com.good.gt.icc.ServiceListener
    public void onConnectionError(String str, int i, String str2, String str3) {
        GDLog.DBGPRINTF(12, "+ GDIccProvider.onConnectionError\n");
        GDIccManager.liflu().dbjc(i, str2);
        GDLog.DBGPRINTF(12, "- GDIccProvider.onConnectionError\n");
    }

    @Override // com.good.gt.icc.ServiceListener
    public void onMessageSent(String str, String str2, String[] strArr) {
        new hbfhc(this, str2, str, strArr).start();
    }

    @Override // com.good.gt.icc.ServiceListener
    public boolean onReadyToSendAttachmentData(byte[] bArr, GTInteger gTInteger, String str, GTInteger gTInteger2, boolean z, String str2) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onReadyToSendAttachmentData\n");
        return GDIccManager.liflu().dbjc(bArr, gTInteger, str, gTInteger2, z);
    }

    @Override // com.good.gt.icc.ServiceListener
    public boolean onReceiveAttachmentData(byte[] bArr, int i, String str, boolean z, String str2) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onReceiveAttachmentData blockSize: " + i + " final: " + z);
        if (applicationListener == null) {
            GDLog.DBGPRINTF(12, "GDIccProvider.onReceiveAttachmentData - no GDServiceListener");
            return false;
        }
        return GDIccManager.liflu().dbjc(bArr, i, str, z);
    }

    @Override // com.good.gt.icc.ServiceListener
    public void onReceiveMessage(String str, String str2, String str3, String str4, Bundle bundle, String[] strArr, String str5) {
        Serializable serializable;
        GDLog.DBGPRINTF(16, "+ GDIccProvider.onReceiveMessage\n");
        GDLog.DBGPRINTF(16, "  application=" + str + "\n");
        GDLog.DBGPRINTF(16, "  service=" + str2 + "\n");
        GDLog.DBGPRINTF(16, "  version=" + str3 + "\n");
        GDLog.DBGPRINTF(16, "  method=" + str4 + "\n");
        if (strArr == null) {
            GDLog.DBGPRINTF(16, "  no attachments\n");
        }
        if (bundle == null) {
            serializable = null;
        } else {
            serializable = bundle.getSerializable(BundleKeys.GTBundlePayload);
        }
        if (strArr != null) {
            GDIccManager.liflu().dbjc();
        }
        GDServiceListener canProvide = GDPreferentialServiceListener.getInstance().canProvide(str2, str3, str4);
        if (canProvide != null) {
            GDLog.DBGPRINTF(16, "  GDIccProvider.onReceiveMessage - calling internally set app listener\n");
            new ehnkx(this, canProvide, str, str2, str3, str4, serializable, strArr, str5).start();
        } else {
            GDLog.DBGPRINTF(16, "  GDIccProvider.onReceiveMessage - calling app listener\n");
            if (applicationListener != null) {
                GDDefaultAppEventListener.getInstance().doClientCallOnceAuthorized(new pmoiy(this, str, str2, str3, str4, serializable, strArr, str5));
            } else {
                GDIccManager.liflu().ztwf();
                GDLog.DBGPRINTF(12, "  GDIccProvider.onReceiveMessage - no listener\n");
            }
        }
        GDLog.DBGPRINTF(16, "- GDIccProvider.onReceiveMessage\n");
    }

    @Override // com.good.gt.icc.ServiceListener
    public boolean onReceivedConnectionRequest(String str, ByteArrayBuffer byteArrayBuffer, ByteArrayBuffer byteArrayBuffer2, ByteArrayBuffer byteArrayBuffer3, GTInteger gTInteger) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onReceivedConnectionRequest");
        return GDIccManager.liflu().dbjc(str, byteArrayBuffer, byteArrayBuffer2, byteArrayBuffer3, gTInteger);
    }

    @Override // com.good.gt.icc.ServiceListener
    public void onReceivingAttachmentFile(String str, String str2, long j, String str3) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onReceivingAttachmentFile");
        new fdyxd(this, str3, str, str2, j).start();
    }

    @Override // com.good.gt.icc.ServiceListener
    public void onReceivingAttachments(String str, int i, String str2) {
        GDLog.DBGPRINTF(16, "GDIccProvider.onReceivingAttachments");
        new yfdke(this, str2, str, i).start();
    }

    @Override // com.good.gd.icc.impl.GDIccProviderInterface
    public void setApplicationListener(GDServiceListener gDServiceListener) {
        applicationListener = gDServiceListener;
        GTInit.setIccServerEnabled(true);
    }

    public void setServicesServer(IccServicesServer iccServicesServer) {
        this._iCCServicesServer = iccServicesServer;
    }

    public boolean verifyEnterpriseUserNumber(byte[] bArr) {
        return GDIccManager.liflu().dbjc(bArr);
    }
}
