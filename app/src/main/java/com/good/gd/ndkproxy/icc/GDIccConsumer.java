package com.good.gd.ndkproxy.icc;

import android.os.Bundle;
import com.good.gd.context.GDContext;
import com.good.gd.icc.GDServiceClientListener;
import com.good.gd.icc.GDServiceError;
import com.good.gd.icc.GDServiceErrorCode;
import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.impl.GDIccConsumerInterface;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.BundleKeys;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTInteger;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.IccServicesClient;
import com.good.gt.icc.ServiceClientListener;
import com.good.gt.util.ByteArrayBuffer;
import java.io.Serializable;

/* loaded from: classes.dex */
public class GDIccConsumer implements ServiceClientListener, AppControl, GDIccConsumerInterface {
    private static GDIccConsumer _instance;
    private ICCController _iccController;
    private IccServicesClient _iccServicesClient;
    private GDServiceClientListener applicationListener = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ Object jwxax;
        final /* synthetic */ String qkduk;
        final /* synthetic */ String[] wxau;

        ehnkx(String str, String str2, Object obj, String[] strArr) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = obj;
            this.wxau = strArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceClientListener canConsume = GDPreferentialServiceListener.getInstance().canConsume(this.dbjc);
            if (canConsume == null) {
                if (GDIccConsumer.this.applicationListener == null) {
                    return;
                }
                GDIccConsumer.this.applicationListener.onReceiveMessage(this.qkduk, this.jwxax, this.wxau, this.dbjc);
                GDLog.DBGPRINTF(16, "- GDIccConsumer.onReceiveMessageCallback.run\n");
                return;
            }
            canConsume.onReceiveMessage(this.qkduk, this.jwxax, this.wxau, this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    class fdyxd extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String qkduk;
        final /* synthetic */ long wxau;

        fdyxd(String str, String str2, String str3, long j) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = str3;
            this.wxau = j;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceClientListener canConsume = GDPreferentialServiceListener.getInstance().canConsume(this.dbjc);
            if (canConsume == null) {
                if (GDIccConsumer.this.applicationListener != null) {
                    GDIccConsumer.this.applicationListener.onReceivingAttachmentFile(this.qkduk, this.jwxax, this.wxau, this.dbjc);
                    return;
                } else {
                    GDLog.DBGPRINTF(16, "GDIccConsumer.onReceivingAttachmentFile - no listener\n");
                    return;
                }
            }
            canConsume.onReceivingAttachmentFile(this.qkduk, this.jwxax, this.wxau, this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String[] jwxax;
        final /* synthetic */ String qkduk;

        hbfhc(String str, String str2, String[] strArr) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = strArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceClientListener canConsume = GDPreferentialServiceListener.getInstance().canConsume(this.dbjc);
            if (canConsume == null) {
                if (GDIccConsumer.this.applicationListener == null) {
                    return;
                }
                GDIccConsumer.this.applicationListener.onMessageSent(this.qkduk, this.dbjc, this.jwxax);
                return;
            }
            canConsume.onMessageSent(this.qkduk, this.dbjc, this.jwxax);
        }
    }

    /* loaded from: classes.dex */
    class yfdke extends Thread {
        final /* synthetic */ String dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ String qkduk;

        yfdke(String str, String str2, int i) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            GDServiceClientListener canConsume = GDPreferentialServiceListener.getInstance().canConsume(this.dbjc);
            if (canConsume == null) {
                if (GDIccConsumer.this.applicationListener != null) {
                    GDIccConsumer.this.applicationListener.onReceivingAttachments(this.qkduk, this.jwxax, this.dbjc);
                    return;
                } else {
                    GDLog.DBGPRINTF(16, "GDIccConsumer.onReceivingAttachments - no listener\n");
                    return;
                }
            }
            canConsume.onReceivingAttachments(this.qkduk, this.jwxax, this.dbjc);
        }
    }

    private GDIccConsumer() {
    }

    public static synchronized GDIccConsumer getInstance() {
        GDIccConsumer gDIccConsumer;
        synchronized (GDIccConsumer.class) {
            if (_instance == null) {
                _instance = new GDIccConsumer();
            }
            gDIccConsumer = _instance;
        }
        return gDIccConsumer;
    }

    private void onReceiveMessageCallback(String str, Object obj, String[] strArr, String str2) {
        GDLog.DBGPRINTF(16, "+ GDIccConsumer.onReceiveMessageCallback\n");
        new ehnkx(str2, str, obj, strArr).start();
        GDLog.DBGPRINTF(16, "- GDIccConsumer.onReceiveMessageCallback\n");
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    @Override // com.good.gd.icc.impl.GDIccConsumerInterface
    public void checkAuthorized() {
        GDContext.getInstance().checkAuthorized();
    }

    @Override // com.good.gd.icc.impl.GDIccConsumerInterface
    public IccServicesClient getServicesClient() {
        return this._iccServicesClient;
    }

    public void init() {
        this._iccController = ICCControllerFactory.getICCController(this);
    }

    public boolean isAppAuthenticatorInstalledWithName(String str) {
        return this._iccController.canSendRequest(str);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public boolean onConnected(String str, byte[] bArr, byte[] bArr2, boolean z) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onConnected\n");
        return GDIccManager.liflu().dbjc(bArr, bArr2, z);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public void onConnectionError(String str, int i, String str2, String str3) {
        GDLog.DBGPRINTF(12, "GDIccConsumer.onReceiveConnectionError\n");
        GDIccManager.liflu().dbjc(i, str2);
        GDServiceErrorCode valueOf = GDServiceErrorCode.valueOf(i);
        if (valueOf == null) {
            valueOf = GDServiceErrorCode.GDServicesErrorGeneral;
        }
        onReceiveMessageCallback(str, GDServiceErrorHandler.GDServiceError(valueOf, str2), null, str3);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public void onMessageSent(String str, String str2, String[] strArr) {
        new hbfhc(str2, str, strArr).start();
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public boolean onReadyToConnect(String str, ByteArrayBuffer byteArrayBuffer, ByteArrayBuffer byteArrayBuffer2, ByteArrayBuffer byteArrayBuffer3, GTInteger gTInteger) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onReadyToConnect\n");
        return GDIccManager.liflu().dbjc(str, byteArrayBuffer, byteArrayBuffer2, byteArrayBuffer3, gTInteger);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public boolean onReadyToSendAttachmentData(byte[] bArr, GTInteger gTInteger, String str, GTInteger gTInteger2, boolean z, String str2) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onReadyToSendAttachmentData\n");
        return GDIccManager.liflu().dbjc(bArr, gTInteger, str, gTInteger2, z);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public boolean onReceiveAttachmentData(byte[] bArr, int i, String str, boolean z, String str2) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onReceiveAttachmentData blockSize: " + i + " final: " + z + "\n");
        return GDIccManager.liflu().dbjc(bArr, i, str, z);
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public void onReceiveMessage(String str, Bundle bundle, String[] strArr, String str2) {
        Integer num;
        Boolean bool;
        GDLog.DBGPRINTF(16, "+ GDIccConsumer.onReceiveMessage\n");
        GDLog.DBGPRINTF(16, "application=" + str + "\n");
        GDLog.DBGPRINTF(16, "requestID=" + str2 + "\n");
        if (strArr == null) {
            GDLog.DBGPRINTF(16, "GDIccConsumer: no attachments\n");
        }
        GDServiceError gDServiceError = null;
        String str3 = null;
        gDServiceError = null;
        gDServiceError = null;
        if (bundle != null) {
            if (bundle.getSerializable(BundleKeys.GTBundleTypeKey) == null) {
                GDLog.DBGPRINTF(16, "GDIccConsumer: No params\n");
            } else if (bundle.getSerializable(BundleKeys.GTBundleTypeKey).equals(BundleKeys.GTBundleTypeError)) {
                Serializable serializable = bundle.getSerializable(BundleKeys.GTBundleErrorCode);
                if (!(serializable instanceof Integer)) {
                    num = null;
                } else {
                    num = (Integer) serializable;
                }
                Serializable serializable2 = bundle.getSerializable(BundleKeys.GTBundleCustomError);
                if (!(serializable2 instanceof Boolean)) {
                    bool = null;
                } else {
                    bool = (Boolean) serializable2;
                }
                Serializable serializable3 = bundle.getSerializable(BundleKeys.GTErrorMessage);
                if (serializable3 != null) {
                    str3 = (String) serializable3;
                }
                if (bool.booleanValue()) {
                    gDServiceError = new GDServiceError(num.intValue(), str3, bundle.getSerializable(BundleKeys.GTBundlePayload));
                } else {
                    GDServiceErrorCode valueOf = GDServiceErrorCode.valueOf(num.intValue());
                    if (valueOf == null) {
                        valueOf = GDServiceErrorCode.GDServicesErrorGeneral;
                    }
                    gDServiceError = GDServiceErrorHandler.GDServiceError(valueOf, str3);
                }
            }
            if (gDServiceError == null) {
                gDServiceError = bundle.getSerializable(BundleKeys.GTBundlePayload);
            }
            if (strArr != null) {
                GDIccManager.liflu().dbjc();
            }
        }
        onReceiveMessageCallback(str, gDServiceError, strArr, str2);
        GDLog.DBGPRINTF(16, "- GDIccConsumer.onReceiveMessage\n");
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public void onReceivingAttachmentFile(String str, String str2, long j, String str3) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onReceivingAttachmentFile\n");
        new fdyxd(str3, str, str2, j).start();
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public void onReceivingAttachments(String str, int i, String str2) {
        GDLog.DBGPRINTF(16, "GDIccConsumer.onReceivingAttachments\n");
        new yfdke(str2, str, i).start();
    }

    public void processPendingIccRequests() {
        this._iccController.processPendingRequests();
    }

    @Override // com.good.gd.icc.impl.GDIccConsumerInterface
    public void setApplicationListener(GDServiceClientListener gDServiceClientListener) {
        this.applicationListener = gDServiceClientListener;
    }

    public void setServicesClient(IccServicesClient iccServicesClient) {
        this._iccServicesClient = iccServicesClient;
    }

    @Override // com.good.gt.icc.ServiceClientListener
    public boolean verifyEnterpriseUserNumber(byte[] bArr) {
        return GDIccManager.liflu().dbjc(bArr);
    }
}
