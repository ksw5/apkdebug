package com.good.gd.service.machines;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.good.gd.background.detection.BBDAppBackgroundDetector;
import com.good.gd.machines.icc.GDIccStateManager;
import com.good.gd.messages.ActivationMsg;
import com.good.gd.messages.CSRMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDStartupController;
import com.good.gd.ndkproxy.enterprise.GDEPasswordChanger;
import com.good.gd.ndkproxy.enterprise.GDEProvisionManager;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.GDActivateFingerprintViewHandler;
import com.good.gd.ndkproxy.ui.GDActivationDelegate;
import com.good.gd.ndkproxy.ui.GDCSRDelegate;
import com.good.gd.ndkproxy.ui.GDEnterpriseProvisionUI;
import com.good.gd.ndkproxy.ui.GDIdleTimeoutHandler;
import com.good.gd.ndkproxy.ui.GDLocalCompliance;
import com.good.gd.ndkproxy.ui.GDRemoteLock;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.service.GDActivityTimerProvider;
import com.good.gd.service.activity_timer.ActivityTimer;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.MessageFactory;
import com.good.gt.background.IccBackgroundListener;

/* loaded from: classes.dex */
public class LibStartupUserSession extends Handler {
    private GDStartupController _gdStartupController = null;
    private GDEProvisionManager _gdeProvisionManager = null;
    private GDEnterpriseProvisionUI _gdEnterpriseProvisionUI = null;
    private BBDUIEventManager _uiEventManager = null;
    private ActivityTimer _gdActivityTimer = null;
    private Messenger _clientMainMessenger = null;
    private Messenger _clientUIMessenger = null;

    public void handleClientMessage(Message message) {
        int i = message.what;
        if (i == 1001) {
            Messenger messenger = message.replyTo;
            this._clientMainMessenger = messenger;
            if (messenger != null) {
                this._gdStartupController.handleAuthRequest(message);
                return;
            }
            throw new RuntimeException("LibStartupUserSession: MSG_CLIENT_AUTHORIZE_REQUEST with no replyTo");
        } else if (i == 1002) {
            Messenger messenger2 = message.replyTo;
            this._clientUIMessenger = messenger2;
            if (messenger2 == null) {
                throw new RuntimeException("LibStartupUserSession: MSG_CLIENT_UI_SESSION_INIT with no replyTo");
            }
        } else if (i == 1004) {
            this._gdeProvisionManager.startProvisioningProcedure(message);
        } else if (i == 1008) {
            GDLog.DBGPRINTF(16, "LibStartupUserSession: ready for progress updates (no action)\n");
        } else if (i == 1030) {
            GDIdleTimeoutHandler.getInstance().idleTimerExceeded();
        } else if (i == 1069) {
            GDCSRDelegate.getInstance().handleCertificateSigningRequest((CSRMsg) message.obj);
        } else if (i == 1025) {
            GDActivationDelegate.handleClientActivationRequest((ActivationMsg) message.obj);
        } else if (i != 1026) {
            switch (i) {
                case 1011:
                    GDRemoteLock.getInstance().handleAuthDelegateFailedRequest();
                    return;
                case 1012:
                    GDEPasswordChanger.getInstance().requestChangePasswordUIfromApplication();
                    return;
                case 1013:
                    this._gdStartupController.handleWipeAcknowledged(message);
                    return;
                case 1014:
                    this._gdActivityTimer.userActivityHasOccurred();
                    return;
                case 1015:
                case 1016:
                case 1017:
                case 1018:
                    BBDAppBackgroundDetector.getInstance().handleClientMessage(message);
                    return;
                default:
                    switch (i) {
                        case 1021:
                            this._gdStartupController.handleRetryPairing();
                            return;
                        case MessageFactory.MSG_CLIENT_CANCEL_STARTUP /* 1022 */:
                            this._gdStartupController.handleCancelStartup();
                            return;
                        case MessageFactory.MSG_CLIENT_WIPE_INCORRECT_AUTHDEL_PWD /* 1023 */:
                            this._gdStartupController.handleWipeRequestIncorrectAuthDelPWD();
                            return;
                        default:
                            switch (i) {
                                case MessageFactory.MSG_FINGERPRINT_OPEN_SETTINGS_UI /* 1065 */:
                                    GDActivateFingerprintViewHandler.getInstance().openFingerprintSettingsUIFromApplication();
                                    return;
                                case MessageFactory.MSG_CLIENT_OPEN_LOG_UPLOAD_UI /* 1066 */:
                                    CoreUI.requestLogUploadUI();
                                    return;
                                case MessageFactory.MSG_SAFE_WIFI_OPEN_SETUP_UI /* 1067 */:
                                    CoreUI.requestInsecureWiFiPermissionsUI();
                                    return;
                                default:
                                    switch (i) {
                                        case MessageFactory.MSG_CLIENT_EXECUTE_WIPE /* 1080 */:
                                            GDLocalCompliance.getInstance().executeLocalWipe();
                                            return;
                                        case MessageFactory.MSG_CLIENT_EXECUTE_LOCAL_BLOCK /* 1081 */:
                                            Object obj = message.obj;
                                            if (!(obj instanceof Bundle)) {
                                                return;
                                            }
                                            Bundle bundle = (Bundle) obj;
                                            GDLocalCompliance.getInstance().executeLocalBlock(bundle.getString(GDLocalCompliance.BLOCK_ID, ""), bundle.getString(GDLocalCompliance.TITLE, ""), bundle.getString(GDLocalCompliance.MESSAGE, ""));
                                            return;
                                        case MessageFactory.MSG_CLIENT_EXECUTE_LOCAL_UNBLOCK /* 1082 */:
                                            Object obj2 = message.obj;
                                            if (!(obj2 instanceof Bundle)) {
                                                return;
                                            }
                                            GDLocalCompliance.getInstance().executeLocalUnblock(((Bundle) obj2).getString(GDLocalCompliance.BLOCK_ID, ""));
                                            return;
                                        default:
                                            switch (i) {
                                                case MessageFactory.MSG_ENTERING_BACKGROUND /* 1090 */:
                                                    UniversalActivityController.getInstance().onBackgroundEntering();
                                                    if (GDActivityStateManager.getInstance() != null) {
                                                        GDActivityStateManager.getInstance().onBackgroundEntering();
                                                    }
                                                    IccBackgroundListener.getInstance().setInForeground(false);
                                                    return;
                                                case MessageFactory.MSG_ENTERING_FOREGROUND /* 1091 */:
                                                    UniversalActivityController.getInstance().onForegroundEntering();
                                                    if (GDActivityStateManager.getInstance() != null) {
                                                        GDActivityStateManager.getInstance().onForegroundEntering();
                                                    }
                                                    IccBackgroundListener.getInstance().setInForeground(true);
                                                    return;
                                                case MessageFactory.MSG_FOCUS_LOST /* 1092 */:
                                                    return;
                                                case MessageFactory.MSG_FOCUS_GAINED /* 1093 */:
                                                    if (GDActivityStateManager.getInstance() == null) {
                                                        return;
                                                    }
                                                    GDActivityStateManager.getInstance().onFocusGained();
                                                    return;
                                                case MessageFactory.MSG_ACTIVITY_PAUSED /* 1094 */:
                                                    if (GDActivityStateManager.getInstance() == null) {
                                                        return;
                                                    }
                                                    GDActivityStateManager.getInstance().onActivityPaused();
                                                    return;
                                                case MessageFactory.MSG_ACTIVITY_RESUMED /* 1095 */:
                                                    if (GDActivityStateManager.getInstance() != null) {
                                                        GDActivityStateManager.getInstance().onActivityResumed();
                                                    }
                                                    GDIccStateManager.getInstance().onActivityResumed();
                                                    return;
                                                default:
                                                    GDLog.DBGPRINTF(16, "LibStartupUserSession: could not handle message " + MessageFactory.showMessage(message) + "\n");
                                                    return;
                                            }
                                    }
                            }
                    }
            }
        } else {
            GDRemoteLock.getInstance().tempUnlockRequest();
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        GDLog.DBGPRINTF(16, "LibStartupUserSession: handleMessage(" + MessageFactory.showMessage(message) + ")\n");
        int i = message.what;
        if (i != 1031) {
            switch (i) {
                case MessageFactory.MSG_CLIENT_UI_MSG /* 1070 */:
                    BBDUIEventManager.processMessage(message);
                    return;
                case MessageFactory.MSG_CLIENT_UI_UPDATE_EVENT /* 1071 */:
                    BBDUIEventManager.processEvent(message);
                    return;
                case MessageFactory.MSG_CLIENT_UI_HOST_UPDATE_EVENT /* 1072 */:
                    BBDUIEventManager.processHostEvent(message);
                    return;
                default:
                    return;
            }
        }
        GDLog.DBGPRINTF(16, "LibStartupUserSession: sending client message " + MessageFactory.showMessage(message) + "\n");
        sendClientMessage(Message.obtain(message));
    }

    public void initialize() {
        GDStartupController gDStartupController = GDStartupController.getInstance();
        this._gdStartupController = gDStartupController;
        gDStartupController.addEventsObserver(this);
        BBDUIEventManager bBDUIEventManager = BBDUIEventManager.getInstance();
        this._uiEventManager = bBDUIEventManager;
        bBDUIEventManager.setEventsObserver(this);
        this._gdActivityTimer = GDActivityTimerProvider.getInstance().getActivityTimer();
        this._gdEnterpriseProvisionUI = GDEnterpriseProvisionUI.getInstance();
        this._gdeProvisionManager = GDEProvisionManager.getInstance();
    }

    void sendClientMessage(Message message) {
        try {
            this._clientMainMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException("LibStartupUserSession.sendClientMessage: ", e);
        }
    }

    void sendUIMessage(Message message) {
        try {
            this._clientUIMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException("LibStartupUserSession.sendUIMessage: ", e);
        }
    }
}
