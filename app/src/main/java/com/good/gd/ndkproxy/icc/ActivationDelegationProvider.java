package com.good.gd.ndkproxy.icc;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.icc.ActivationDelegationServer;
import com.good.gt.icc.ActivationDelegationServerListener;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.CertificateSigningRequestServer;
import com.good.gt.icc.CertificateSigningRequestServerListener;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;

/* loaded from: classes.dex */
public class ActivationDelegationProvider implements ActivationDelegationServerListener, CertificateSigningRequestServerListener, AppControl {
    private static final String TAG = "ActivationDelegationProvider";
    private static ActivationDelegationProvider _instance;
    private final ActivationDelegationServer _actDelegServer;
    private final CertificateSigningRequestServer _csrDelegServer;

    public ActivationDelegationProvider() {
        ActivationDelegationServer activationDelegationServer = ICCControllerFactory.getICCController(this).getActivationDelegationServer();
        this._actDelegServer = activationDelegationServer;
        try {
            activationDelegationServer.setListener(this);
            ActivationDelegationNDKBridge.setActivationServer(activationDelegationServer);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, "Activation Delegation Provider - listener error: " + e.toString() + "\n");
        }
        CertificateSigningRequestServer certificateSigningRequestServer = ICCControllerFactory.getICCController(this).getCertificateSigningRequestServer();
        this._csrDelegServer = certificateSigningRequestServer;
        try {
            certificateSigningRequestServer.setListener(this);
            ActivationDelegationNDKBridge.setCertificateSigningServer(certificateSigningRequestServer);
        } catch (ListenerAlreadySetException e2) {
            GDLog.DBGPRINTF(12, "CSR Delegation Provider - listener error: " + e2.toString() + "\n");
        }
    }

    public static synchronized ActivationDelegationProvider getInstance() {
        ActivationDelegationProvider activationDelegationProvider;
        synchronized (ActivationDelegationProvider.class) {
            if (_instance == null) {
                _instance = new ActivationDelegationProvider();
            }
            activationDelegationProvider = _instance;
        }
        return activationDelegationProvider;
    }

    private native void ndkInit();

    private native void onReceiveActivationRequestNative(String str, String str2, String str3, String str4, String str5, String str6, boolean z);

    private native void onReceiveCertificateSigningRequestNative(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    public void initialize() throws Exception {
        try {
            ndkInit();
        } catch (Exception e) {
            throw new Exception("ActivationDelegationProvider: Cannot initialize Native peer", e);
        }
    }

    @Override // com.good.gt.icc.ActivationDelegationServerListener
    public boolean onReceiveActivationRequest(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        GDLog.DBGPRINTF(16, TAG, "onReceiveActivationRequest(" + str + ") IN\n");
        onReceiveActivationRequestNative(str, str2, str3, str4, str5, str6, z);
        return true;
    }

    public native void onReceiveActivationResponseNative(int i, String str, String str2, String str3, String str4, boolean z);

    @Override // com.good.gt.icc.CertificateSigningRequestServerListener
    public boolean onReceiveCertificateSigningRequest(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String str9 = TAG;
        GDLog.DBGPRINTF(16, str9, "onReceiveCertificateSigningRequest(" + str + ") IN\n");
        onReceiveCertificateSigningRequestNative(str, str2, str3, str4, str5, str6, str7, str8);
        GDLog.DBGPRINTF(16, str9, "onReceiveCertificateSigningRequest(" + str + ") OUT\n");
        return true;
    }
}
