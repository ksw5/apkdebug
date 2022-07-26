package com.good.gd.pki;

import com.good.gd.ndkproxy.pki.BaseCertificate;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CertificateHandler {
    private static CertificateHandler _instance;
    private List<CertificateListener> _listeners = new ArrayList();

    /* loaded from: classes.dex */
    public static class CertificateHandlerBridgeHelper {
        /* JADX INFO: Access modifiers changed from: protected */
        public static void notifyCertificateAdded(CertificateHandler certificateHandler, BaseCertificate baseCertificate) {
            certificateHandler.notifyCertificateAdded(baseCertificate);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public static void notifyCertificateRemoved(CertificateHandler certificateHandler, BaseCertificate baseCertificate) {
            certificateHandler.notifyCertificateRemoved(baseCertificate);
        }
    }

    private CertificateHandler() {
    }

    public static CertificateHandler getInstance() {
        if (_instance == null) {
            _instance = new CertificateHandler();
            initialize();
        }
        return _instance;
    }

    private static native void initialize();

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCertificateAdded(BaseCertificate baseCertificate) {
        for (CertificateListener certificateListener : this._listeners) {
            certificateListener.onCertificatedAdded(baseCertificate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCertificateRemoved(BaseCertificate baseCertificate) {
        for (CertificateListener certificateListener : this._listeners) {
            certificateListener.onCertificateRemoved(baseCertificate);
        }
    }

    public void addCertificateListener(CertificateListener certificateListener) {
        this._listeners.add(certificateListener);
    }

    public void removeCertificateListener(CertificateListener certificateListener) {
        this._listeners.remove(certificateListener);
    }
}
