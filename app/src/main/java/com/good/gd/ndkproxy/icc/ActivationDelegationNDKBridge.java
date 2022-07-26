package com.good.gd.ndkproxy.icc;

import android.os.Handler;
import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.GDServiceException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gt.containercomms.invoke.SideChannelSignallingServiceImpl;
import com.good.gt.icc.ActivationDelegationClient;
import com.good.gt.icc.ActivationDelegationServer;
import com.good.gt.icc.CertificateSigningRequestClient;
import com.good.gt.icc.CertificateSigningRequestServer;
import com.good.gt.icc.GTServicesException;

/* loaded from: classes.dex */
public final class ActivationDelegationNDKBridge {
    private static final String TAG = "ActivationDelegationNDKBridge";
    private static ActivationDelegationClient activationClient;
    private static ActivationDelegationServer activationServer;
    private static CertificateSigningRequestClient csrClient;
    private static CertificateSigningRequestServer csrServer;
    private static SideChannelSignallingServiceImpl scss = new SideChannelSignallingServiceImpl();

    /* loaded from: classes.dex */
    static class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            GDEActivationManager.getInstance().notifyListChanged();
        }
    }

    private ActivationDelegationNDKBridge() {
    }

    public static void initialize() {
        ndkInit();
    }

    private static boolean isAppInstalled(String str) {
        return scss.canSendSideChannelData(str).isAppInstalled();
    }

    private static native void ndkInit();

    private static void sendActivationRequest(String str, String str2, String str3, String str4, String str5, String str6) throws GDServiceException {
        try {
            activationClient.sendActivationRequest(str, str2, str3, str4, str5, str6);
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, TAG, "sendActivationRequest - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    private static void sendActivationResponse(String str, int i, String str2, String str3, String str4) throws GDServiceException {
        try {
            activationServer.sendActivationResponse(str, i, str2, str3, str4);
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, TAG, "sendActivationResponse - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    private static void sendCertificateSigningRequest(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws GDServiceException {
        try {
            csrClient.sendCSRRequest(str, str2, str3, str4, str5, str6, str7, str8);
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, TAG, "sendCertificateSigningResponse - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    private static void sendCertificateSigningResponse(String str, int i, String str2) throws GDServiceException {
        try {
            csrServer.sendCSRResponse(str, i, str2);
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, TAG, "sendCertificateSigningResponse - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    public static void setActivationClient(ActivationDelegationClient activationDelegationClient) {
        activationClient = activationDelegationClient;
    }

    public static void setActivationServer(ActivationDelegationServer activationDelegationServer) {
        activationServer = activationDelegationServer;
    }

    public static void setCertificateSigningClient(CertificateSigningRequestClient certificateSigningRequestClient) {
        csrClient = certificateSigningRequestClient;
    }

    public static void setCertificateSigningServer(CertificateSigningRequestServer certificateSigningRequestServer) {
        csrServer = certificateSigningRequestServer;
    }

    private static void updateDelegatesList() {
        new Handler().post(new hbfhc());
    }
}
