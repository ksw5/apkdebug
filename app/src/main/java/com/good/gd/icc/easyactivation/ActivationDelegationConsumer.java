package com.good.gd.icc.easyactivation;

import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.GDServiceException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.ActivationDelegationNDKBridge;
import com.good.gd.ndkproxy.icc.ActivationDelegationProvider;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gt.icc.ActivationDelegationClient;
import com.good.gt.icc.ActivationDelegationClientListener;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;

/* loaded from: classes.dex */
public class ActivationDelegationConsumer implements ActivationDelegationClientListener, AppControl {
    private static final String TAG = "ActivationDelegationConsumer";
    private static ActivationDelegationConsumer _instance;
    private final ActivationDelegationClient _actDelegClient;
    private ActivationDelegationConsumerListener _listener = null;

    private ActivationDelegationConsumer() {
        ActivationDelegationClient activationDelegationClient = ICCControllerFactory.getICCController(this).getActivationDelegationClient();
        this._actDelegClient = activationDelegationClient;
        try {
            activationDelegationClient.setListener(this);
            ActivationDelegationNDKBridge.setActivationClient(activationDelegationClient);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, TAG, "listener error: " + e.toString() + "\n");
        }
    }

    public static synchronized ActivationDelegationConsumer getInstance() {
        ActivationDelegationConsumer activationDelegationConsumer;
        synchronized (ActivationDelegationConsumer.class) {
            if (_instance == null) {
                _instance = new ActivationDelegationConsumer();
            }
            activationDelegationConsumer = _instance;
        }
        return activationDelegationConsumer;
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
        UniversalActivityController.getInstance().handleICCFrontRequest(frontParams);
    }

    public void initialize() throws Exception {
    }

    @Override // com.good.gt.icc.ActivationDelegationClientListener
    public void onActivationDelegationResponse(int i, String str, String str2, String str3, String str4, String str5, boolean z) {
        String str6 = TAG;
        GDLog.DBGPRINTF(16, str6, "onActivationDelegationResponse(" + str4 + ") IN\n");
        ActivationDelegationProvider.getInstance().onReceiveActivationResponseNative(i, str, str2, str3, str5, z);
        ActivationDelegationConsumerListener activationDelegationConsumerListener = this._listener;
        if (activationDelegationConsumerListener == null) {
            return;
        }
        activationDelegationConsumerListener.onActivationDelegationResponse(i, str, str2, str4, str5, z);
        GDLog.DBGPRINTF(16, str6, "onActivationDelegationResponse(" + str4 + ") OUT\n");
    }

    public void sendActivationRequest(String str, String str2, String str3, String str4, String str5, String str6) throws GDServiceException {
        try {
            if (GDActivityStateManager.getInstance().inForeground()) {
                this._actDelegClient.sendActivationRequest(str, str2, str3, str4, str5, str6);
            } else {
                GDLog.DBGPRINTF(14, TAG, "sendActivationRequest: skipped activation request (in background)\n");
            }
        } catch (GTServicesException e) {
            GDLog.DBGPRINTF(12, TAG, "sendActivationRequest - error: " + e.toString() + "\n");
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    public void setListener(ActivationDelegationConsumerListener activationDelegationConsumerListener) {
        this._listener = activationDelegationConsumerListener;
    }
}
