package com.good.gd.icc.csr;

import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.GDServiceException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.ActivationDelegationNDKBridge;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.CertificateSigningRequestClient;
import com.good.gt.icc.CertificateSigningRequestClientListener;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;

/* loaded from: classes.dex */
public class CertificateSigningRequestConsumer implements CertificateSigningRequestClientListener, AppControl {
    private static final String TAG = "CertificateSigningRequestConsumer";
    private static CertificateSigningRequestConsumer _instance;
    private final CertificateSigningRequestClient _csrRequestClient;
    private CertificateSigningRequestConsumerListener _listener = null;

    private CertificateSigningRequestConsumer() {
        CertificateSigningRequestClient certificateSigningRequestClient = ICCControllerFactory.getICCController(this).getCertificateSigningRequestClient();
        this._csrRequestClient = certificateSigningRequestClient;
        try {
            certificateSigningRequestClient.setListener(this);
            ActivationDelegationNDKBridge.setCertificateSigningClient(certificateSigningRequestClient);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, TAG, "listener error: " + e.toString() + "\n");
        }
    }

    public static synchronized CertificateSigningRequestConsumer getInstance() {
        CertificateSigningRequestConsumer certificateSigningRequestConsumer;
        synchronized (CertificateSigningRequestConsumer.class) {
            if (_instance == null) {
                _instance = new CertificateSigningRequestConsumer();
            }
            certificateSigningRequestConsumer = _instance;
        }
        return certificateSigningRequestConsumer;
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    public void initialize() throws Exception {
    }

    @Override // com.good.gt.icc.CertificateSigningRequestClientListener
    public void onCSRResponse(int i, String str, String str2, String str3, boolean z) {
        String str4 = TAG;
        GDLog.DBGPRINTF(16, str4, "onCSRResponse(" + str2 + ") IN\n");
        CertificateSigningRequestConsumerListener certificateSigningRequestConsumerListener = this._listener;
        if (certificateSigningRequestConsumerListener == null) {
            return;
        }
        certificateSigningRequestConsumerListener.onCSRDelegationResponse(i, str, str2, str3, z);
        GDLog.DBGPRINTF(16, str4, "onCSRResponse(" + str2 + ") OUT\n");
    }

    public void sendCertificateSigningRequest(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws GDServiceException {
        try {
        } catch (GTServicesException e) {
            e = e;
        }
        try {
            if (GDActivityStateManager.getInstance().inForeground()) {
                this._csrRequestClient.sendCSRRequest(str, str2, str3, str4, str5, str6, str7, str8);
            } else {
                GDLog.DBGPRINTF(14, TAG, "sendCertificateSigningRequest: skipped CSR request (in background)\n");
            }
        } catch (GTServicesException e2) {
            e = e2;
            GDLog.DBGPRINTF(12, TAG, "sendCertificateSigningRequest - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    public void setListener(CertificateSigningRequestConsumerListener certificateSigningRequestConsumerListener) {
        this._listener = certificateSigningRequestConsumerListener;
    }
}
