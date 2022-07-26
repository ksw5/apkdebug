package com.good.gd.ndkproxy;

import android.os.Handler;
import android.os.Message;
import com.good.gd.GDAppEvent;
import com.good.gd.GDAppEventType;
import com.good.gd.GDAppResultCode;
import com.good.gd.GDInternalAppEvent;
import com.good.gd.GDInternalAppEventType;
import com.good.gd.messages.AuthorizeMsg;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;
import com.good.gd.ndkproxy.icc.AuthDelegationProvider;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.service.GDKeyboardControl;
import com.good.gd.utils.GDNDKLibraryLoader;
import com.good.gd.utils.MessageFactory;
import com.good.gd.utils.UserAuthUtils;
import com.good.gt.deviceid.BBDDeviceIDCallback;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class GDStartupController implements BBDDeviceIDCallback {
    private static GDStartupController _instance = null;
    Message mMessage;
    private State _state = State.STATE_INITIAL;
    private boolean mStateWasReset = false;
    private GDAppEvent _onStartupCallbackEvent = null;
    private ArrayList<Handler> _observers = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum State {
        STATE_INITIAL,
        STATE_OBTAIN_DEVICEID,
        STATE_READY,
        STATE_IN_STARTUP,
        STATE_AUTHORIZED,
        STATE_BACKGROUND_AUTHORIZED,
        STATE_DENIED
    }

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc(GDStartupController gDStartupController) {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(5000L, 0);
            } catch (InterruptedException e) {
            }
            GDStartupController.getInstance().GDLibStartupLayerCallback_onStartupCallback(true, 0, "", false);
        }
    }

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private GDStartupController() {
        try {
            GDLog.DBGPRINTF(16, "GDStartupController.getInstance: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLock) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDStartupController.getInstance: Cannot initialize C++ peer", e);
        }
    }

    private native void cancelStartup();

    public static synchronized GDStartupController getInstance() {
        GDStartupController gDStartupController;
        synchronized (GDStartupController.class) {
            if (_instance == null) {
                _instance = new GDStartupController();
            }
            gDStartupController = _instance;
        }
        return gDStartupController;
    }

    private native void handleIncorrectAuthDelPWD(String str);

    private native void ndkInit();

    private void obtainDeviceID() {
        this._state = State.STATE_OBTAIN_DEVICEID;
        GDDeviceInfo.getInstance().obtainDeviceID(this);
    }

    private native boolean resetConnectivity();

    private native void retryPairing();

    private void sendToAllObservers(Message message) {
        Iterator<Handler> it = this._observers.iterator();
        while (it.hasNext()) {
            Handler next = it.next();
            GDLog.DBGPRINTF(16, "GDStartupController: sending Event to:" + next + "\n");
            next.sendMessage(message);
        }
    }

    private native void startup(String str, String str2, boolean z, boolean z2);

    private native void triggerAppAuthComplete();

    private native void triggerAppEvent(int i, boolean z);

    public void GDLibStartupLayerCallback_onAppConfigCallback() {
        GDLog.DBGPRINTF(16, "GDStartupController onAppConfigCallback()\n");
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, new GDAppEvent("Application config changed", GDAppResultCode.GDErrorNone, GDAppEventType.GDAppEventRemoteSettingsUpdate)));
    }

    public void GDLibStartupLayerCallback_onAppPolicyUpdateCallback() {
        GDLog.DBGPRINTF(16, "GDStartupController onAppPolicyUpdateCallback()\n");
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, new GDAppEvent("Application policy changed", GDAppResultCode.GDErrorNone, GDAppEventType.GDAppEventPolicyUpdate)));
    }

    public void GDLibStartupLayerCallback_onCloseInterAppLockUI() {
        GDLog.DBGPRINTF(16, "GDStartupController onCloseInterAppLockUI()\n");
        CoreUI.closeAuthDelegationBlockedUI();
    }

    public void GDLibStartupLayerCallback_onContainerMigrationCallback(int i) {
        GDInternalAppEvent gDInternalAppEvent;
        GDLog.DBGPRINTF(16, "GDStartupController onContainerMigrationCallback()\n");
        if (i == 7) {
            gDInternalAppEvent = new GDInternalAppEvent("Container migration pending", GDAppResultCode.GDErrorNone, GDInternalAppEventType.GDInternalAppEventContainerMigrationPending);
        } else if (i != 8) {
            GDLog.DBGPRINTF(12, "GDStartupController onContainerMigrationCallback() unknown event:" + i + "\n");
            return;
        } else {
            gDInternalAppEvent = new GDInternalAppEvent("Container migration completed", GDAppResultCode.GDErrorNone, GDInternalAppEventType.GDInternalAppEventContainerMigrationCompleted);
        }
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, gDInternalAppEvent));
    }

    public void GDLibStartupLayerCallback_onEntitlementsUpdateCallback() {
        GDLog.DBGPRINTF(16, "GDStartupController onEntitlementsUpdateCallback()\n");
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, new GDAppEvent("Entitlements update", GDAppResultCode.GDErrorNone, GDAppEventType.GDAppEventEntitlementsUpdate)));
    }

    public void GDLibStartupLayerCallback_onServiceUpdateCallback() {
        GDLog.DBGPRINTF(16, "GDStartupController onServiceUpdateCallback()\n");
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, new GDAppEvent("Application service update", GDAppResultCode.GDErrorNone, GDAppEventType.GDAppEventServicesUpdate)));
    }

    public synchronized void GDLibStartupLayerCallback_onStartupCallback(boolean z, int i, String str, boolean z2) {
        State state;
        GDLog.DBGPRINTF(16, "GDStartupController onStartupCallback(" + z + ", " + i + ", " + z2 + ")\n");
        if (z) {
            state = z2 ? State.STATE_BACKGROUND_AUTHORIZED : State.STATE_AUTHORIZED;
            this._onStartupCallbackEvent = new GDAppEvent("Authorized", GDAppResultCode.GDErrorNone, z2 ? GDAppEventType.GDAppEventBackgroundAuthorized : GDAppEventType.GDAppEventAuthorized);
            if (GDAuthManager.getAuthType() == 2) {
                AuthDelegationProvider.getInstance().populateAuthDelegateeList();
            }
        } else {
            state = State.STATE_DENIED;
            this._onStartupCallbackEvent = new GDAppEvent("Not Authorized [error=" + i + "]", GDAppResultCode.get(i), z2 ? GDAppEventType.GDAppEventBackgroundNotAuthorized : GDAppEventType.GDAppEventNotAuthorized);
        }
        if (!this.mStateWasReset) {
            this._state = state;
        } else {
            GDLog.DBGPRINTF(16, "GDStartupController onStartupCallback stateWasReset\n");
        }
        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, this._onStartupCallbackEvent));
    }

    public void addEventsObserver(Handler handler) {
        this._observers.add(handler);
    }

    public synchronized void handleAuthRequest(Message message) {
        if (message != null) {
            Object obj = message.obj;
            if (obj instanceof AuthorizeMsg) {
                this.mStateWasReset = false;
                AuthorizeMsg authorizeMsg = (AuthorizeMsg) obj;
                GDLog.DBGPRINTF(16, "GDStartupController: MSG_CLIENT_AUTHORIZE_REQUEST is received in _state = " + this._state + " ( " + authorizeMsg.appId + ", " + authorizeMsg.appVersion + ", " + authorizeMsg.isBackgroundAuth + ")\n");
                State state = this._state;
                if (state != State.STATE_READY && (state != State.STATE_BACKGROUND_AUTHORIZED || authorizeMsg.isBackgroundAuth)) {
                    State state2 = this._state;
                    if (state2 == State.STATE_INITIAL) {
                        obtainDeviceID();
                        Message message2 = new Message();
                        this.mMessage = message2;
                        message2.copyFrom(message);
                    } else if (state2 == State.STATE_IN_STARTUP) {
                        GDLog.DBGPRINTF(13, "GDStartupController: auth request received in startup state\n");
                    } else if (state2 == State.STATE_OBTAIN_DEVICEID) {
                        GDLog.DBGPRINTF(13, "GDStartupController: auth request received in obtain deviceID state\n");
                    } else if (state2 == State.STATE_DENIED) {
                        GDLog.DBGPRINTF(13, "GDStartupController: auth request received in denied state\n");
                        GDLog.DBGPRINTF(16, "GDStartupController: event callback is " + this._onStartupCallbackEvent + "\n");
                    } else {
                        if (state2 == State.STATE_AUTHORIZED) {
                            if (UserAuthUtils.isUserAuthRequired()) {
                                GDLog.DBGPRINTF(14, "GDStartupController: authorized, but re-auth required\n");
                                this._state = State.STATE_DENIED;
                                this._onStartupCallbackEvent = new GDAppEvent("Authorized, but re-auth required", GDAppResultCode.GDErrorSecurityError, GDAppEventType.GDAppEventAuthorized);
                            } else {
                                GDLog.DBGPRINTF(14, "GDStartupController: already authorized, re-sending auth callback message\n");
                            }
                        }
                        sendToAllObservers(MessageFactory.newMessage(MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, this._onStartupCallbackEvent));
                    }
                    return;
                }
                synchronized (NativeExecutionHandler.nativeLock) {
                    startup(authorizeMsg.appId, authorizeMsg.appVersion, CppDirectives.GD_USE_ENTERPRISE_PROVISIONING, authorizeMsg.isBackgroundAuth);
                    this._state = State.STATE_IN_STARTUP;
                    GDKeyboardControl.getInstance().checkSystemKeybord();
                }
                return;
            }
        }
        GDLog.DBGPRINTF(16, "GDStartupController: authorize request is received with invalid inner message, ignoring\n");
    }

    public void handleCancelStartup() {
        GDLog.DBGPRINTF(14, "GDStartupController: handleCancelStartup()\n");
        cancelStartup();
    }

    public void handleRetryPairing() {
        GDLog.DBGPRINTF(14, "GDStartupController: handleRetryPairing()\n");
        retryPairing();
    }

    public void handleWipeAcknowledged(Message message) {
        GDLog.DBGPRINTF(14, "GDStartupController: handleWipeAcknowledged()\n");
    }

    public void handleWipeRequestIncorrectAuthDelPWD() {
        GDLog.DBGPRINTF(14, "GDStartupController: Incorrect Auth Del provided password\n");
        handleIncorrectAuthDelPWD("WDI:GDLUIOS:AAA");
    }

    public void nativeStartupMock_already_authorized(String str, String str2, boolean z) {
        GDLog.DBGPRINTF(16, "GDStartupController authorize(" + str + ", " + str2 + ", ...) - here, we pretend that native was called and then after a while we imitate callback from C++ side.\n");
        new Thread(new hbfhc(this)).start();
    }

    @Override // com.good.gt.deviceid.BBDDeviceIDCallback
    public void onDeviceID(String str) {
        if (this._state == State.STATE_DENIED) {
            GDLog.DBGPRINTF(12, "Trying to set DeviceID for denied application.");
            return;
        }
        this._state = State.STATE_READY;
        handleAuthRequest(this.mMessage);
        this.mMessage = null;
    }

    public synchronized void resetState() {
        this._state = State.STATE_INITIAL;
        this.mStateWasReset = true;
    }

    public void triggerAppAuthCompleted() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            triggerAppAuthComplete();
        }
    }

    public void triggerAppEvent(GDAppResultCode gDAppResultCode, boolean z) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            triggerAppEvent(gDAppResultCode.getCode(), z);
        }
    }
}
