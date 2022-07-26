package com.good.gd.ndkproxy.native2javabridges.pki;

import com.good.gd.ndkproxy.pki.BaseCertificate;
import com.good.gd.pki.CertificateHandler;

/* loaded from: classes.dex */
final class CertificateHandlerBridge extends CertificateHandler.CertificateHandlerBridgeHelper {
    private static final CertificateHandlerBridge instance = new CertificateHandlerBridge();

    CertificateHandlerBridge() {
    }

    private static CertificateHandlerBridge getInstance() {
        return instance;
    }

    private void notifyCertificateAdded(BaseCertificate baseCertificate) {
        CertificateHandler.CertificateHandlerBridgeHelper.notifyCertificateAdded(CertificateHandler.getInstance(), baseCertificate);
    }

    private void notifyCertificateRemoved(BaseCertificate baseCertificate) {
        CertificateHandler.CertificateHandlerBridgeHelper.notifyCertificateRemoved(CertificateHandler.getInstance(), baseCertificate);
    }
}
