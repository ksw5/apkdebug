package com.good.gd.client;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import com.good.gd.GDAppEvent;
import com.good.gd.GDAppEventListener;
import com.good.gd.GDAppEventType;
import com.good.gd.GDAppResultCode;
import com.good.gd.GDInternalAppEvent;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.background.detection.BBDAppBackgroundDetector;
import com.good.gd.background.detection.GDAppStateListener;
import com.good.gd.context.GDContext;
import com.good.gd.error.GDInitializationError;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.messages.AuthorizeMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.backgroundAuth.GDBackgroundAuthImpl;
import com.good.gd.ndkproxy.bypass.GDBypassAbilityImpl;
import com.good.gd.ndkproxy.enterprise.GDAuthManager;
import com.good.gd.ndkproxy.log.LogUploadUIPresenter;
import com.good.gd.ndkproxy.ui.GDLocalCompliance;
import com.good.gd.service.GDServiceInitializer;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.GDActivityInfo;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.GDNDKLibraryLoader;
import com.good.gd.utils.INIT_STATE;
import com.good.gd.utils.MessageFactory;
import com.good.gd.utils.UserAuthUtils;
import com.good.gd.utils.migration.GDMigrationUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDClient implements GDCustomizedUI, LogUploadUIPresenter {
    private static GDClient _instance;
    private String _appId;
    private String _appVersion;
    private Messenger _incomingMessenger;
    private String blockMessage;
    private Drawable uiBigApplicationLogo;
    private Integer uiCustomColor;
    private Drawable uiSmallApplicationLogo;
    private String wipeMessage;
    private ServiceConnection serviceConnection = new hbfhc();
    private Messenger _serviceMessenger = null;
    private Handler _clientHandler = null;
    private List<AuthorizeMsg> pendingAuthRequests = new LinkedList();
    private boolean _authorizeOnConnected = false;
    private boolean _boundToService = false;
    private boolean _serviceComingUp = false;
    private boolean _initialized = false;
    private boolean _backgroundAuthAllowed = false;

    /* loaded from: classes.dex */
    class hbfhc implements ServiceConnection {
        hbfhc() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            GDLog.DBGPRINTF(14, "GDClient.ServiceConnection.onServiceConnected()\n");
            GDClient.this._serviceMessenger = new Messenger(iBinder);
            UniversalActivityController.getInstance().initialize(GDClient.this._serviceMessenger);
            BBDAppBackgroundDetector.getInstance().setAppStateListener(new GDAppStateListener(GDClient.this._serviceMessenger));
            boolean z = true;
            GDClient.this._boundToService = true;
            GDClient.this._serviceComingUp = false;
            if (GDClient.this._authorizeOnConnected) {
                int size = GDClient.this.pendingAuthRequests.size();
                if (size > 0) {
                    if (size <= 1 || !GDClient.this.sendUIAuthRequest()) {
                        z = false;
                    }
                    if (!z) {
                        GDClient.this.sendAuthorizationMessage((AuthorizeMsg) GDClient.this.pendingAuthRequests.get(0));
                    }
                    GDClient.this.pendingAuthRequests.clear();
                } else {
                    AuthorizeMsg authorizeMsg = new AuthorizeMsg();
                    authorizeMsg.appId = GDClient.this._appId;
                    authorizeMsg.appVersion = GDClient.this._appVersion;
                    authorizeMsg.isBackgroundAuth = GDClient.this._backgroundAuthAllowed;
                    GDClient.this.sendAuthorizationMessage(authorizeMsg);
                }
                GDClient.this._authorizeOnConnected = false;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            GDLog.DBGPRINTF(16, "GDClient.ServiceConnection.onServiceDisconnected() - Service stopped.\n");
            GDClient.this._boundToService = false;
            GDClient.this._serviceComingUp = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class yfdke extends Handler {
        private final WeakReference<GDClient> dbjc;

        public yfdke(GDClient gDClient) {
            this.dbjc = new WeakReference<>(gDClient);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GDLog.DBGPRINTF(16, "GDClient handleMessage (" + message.what + ") IN\n");
            GDClient gDClient = this.dbjc.get();
            if (gDClient != null) {
                if (message.what == 1031) {
                    Object obj = message.obj;
                    if (obj instanceof GDInternalAppEvent) {
                        gDClient.handleInternalAppEventCallback((GDInternalAppEvent) obj);
                    } else {
                        gDClient.handleAppEventCallback((GDAppEvent) obj);
                    }
                } else {
                    super.handleMessage(message);
                }
            }
            GDLog.DBGPRINTF(16, "GDClient handleMessage (" + message.what + ") OUT\n");
        }
    }

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private GDClient() {
    }

    private String checkIfGdIdValid(String str) {
        if (TextUtils.isEmpty(str)) {
            return "Application GD ID is empty in settings.json";
        }
        if (isGDIdAllowed(str)) {
            return null;
        }
        return "Application GD ID is invalid.";
    }

    private String checkIfVersionValid(String str) {
        if (TextUtils.isEmpty(str)) {
            return "Application version is empty in settings.json";
        }
        if (isGdVersionAllowed(str)) {
            return null;
        }
        return "Application GD version is invalid.";
    }

    private String checkIfVersionsMatch(String str) {
        if (!GDInit.checkGDAppVersionMismatch(null, str)) {
            return "GDApplication version in Settings.json and Manifest.xml doesn't match";
        }
        return null;
    }

    public static synchronized GDClient getInstance() {
        GDClient gDClient;
        synchronized (GDClient.class) {
            if (_instance == null) {
                _instance = new GDClient();
            }
            gDClient = _instance;
        }
        return gDClient;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppEventCallback(GDAppEvent gDAppEvent) {
        GDLog.DBGPRINTF(16, "GDClient.handleAppEventCallback(" + gDAppEvent + ")\n");
        if (gDAppEvent.getEventType() == GDAppEventType.GDAppEventAuthorized && gDAppEvent.getResultCode() == GDAppResultCode.GDErrorSecurityError) {
            GDLog.DBGPRINTF(13, "GDClient.handleAppEventCallback: some security error happened.\n");
        } else {
            GDDefaultAppEventListener.getInstance().onGDEvent(gDAppEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInternalAppEventCallback(GDInternalAppEvent gDInternalAppEvent) {
        GDLog.DBGPRINTF(16, "GDClient.handleInternalAppEventCallback(" + gDInternalAppEvent + ")\n");
        GDDefaultAppEventListener.getInstance().onGDInternalEvent(gDInternalAppEvent);
    }

    private void initImpl(INIT_STATE init_state) {
        if (this._clientHandler == null) {
            this._clientHandler = new yfdke(this);
        }
        if (this._incomingMessenger == null) {
            this._incomingMessenger = new Messenger(this._clientHandler);
        }
        if (GDInit.initialize(init_state)) {
            if (!this._boundToService && !this._serviceComingUp) {
                _instance.instigateServiceBinding();
            }
        } else {
            GDInit.checkInitialized();
        }
        if (!this._initialized) {
            initialize();
        }
        this._initialized = true;
    }

    private void instigateServiceBinding() {
        if (GDContext.getInstance().getApplicationContext() != null) {
            GDLog.DBGPRINTF(16, "GDClient.instigateServiceBinding()\n");
            if (!this._boundToService) {
                this._serviceComingUp = GDServiceInitializer.create().startGDService(this.serviceConnection);
                return;
            }
            throw new RuntimeException("instigateServiceBinding() called when already bound");
        }
        throw new GDInitializationError("GD not initialized; use of non-GD Activity class?");
    }

    public static native boolean isGDIdAllowed(String str);

    public static native boolean isGdVersionAllowed(String str);

    private boolean isTestBuild() {
        try {
            Class.forName("com.good.gd.deeptest.GDDeepTestPlatformAdaptor");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 != read) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAuthorizationMessage(AuthorizeMsg authorizeMsg) {
        if (authorizeMsg == null) {
            authorizeMsg = new AuthorizeMsg();
        }
        String str = authorizeMsg.appId;
        if (str == null || str.isEmpty()) {
            authorizeMsg.appId = this._appId;
        }
        String str2 = authorizeMsg.appVersion;
        if (str2 == null || str2.isEmpty()) {
            authorizeMsg.appVersion = this._appVersion;
        }
        sendMessageToServer(MessageFactory.newMessage(1001, authorizeMsg));
    }

    private void sendMessageToServer(Message message) {
        try {
            message.replyTo = this._incomingMessenger;
            this._serviceMessenger.send(message);
        } catch (RemoteException e) {
            throw new RuntimeException("GDClient.sendMessageToServer: could not send message: ", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendUIAuthRequest() {
        for (AuthorizeMsg authorizeMsg : this.pendingAuthRequests) {
            if (!authorizeMsg.isBackgroundAuth) {
                sendAuthorizationMessage(authorizeMsg);
                return true;
            }
        }
        return false;
    }

    public synchronized void authorize(GDAppEventListener gDAppEventListener, boolean z) throws GDInitializationError {
        if (GDContext.getInstance().getApplicationContext() != null) {
            authorize(this._appId, this._appVersion, gDAppEventListener, z);
        } else {
            throw new GDInitializationError("GD not initialized; use of non-GD Activity class?");
        }
    }

    public boolean checkIfNoPasswordMode() {
        return GDAuthManager.getInstance().isAuthTypeNoPass();
    }

    public void configureUI(Drawable drawable, Drawable drawable2, Integer num) {
        if (drawable == null) {
            return;
        }
        this.uiSmallApplicationLogo = drawable;
        this.uiBigApplicationLogo = drawable2;
        this.uiCustomColor = num;
    }

    public void executeBlock(String str, String str2, String str3) {
        GDLog.DBGPRINTF(16, "GDClient.executeBlock\n");
        GDContext.getInstance().checkAuthorized();
        Message newMessage = MessageFactory.newMessage(MessageFactory.MSG_CLIENT_EXECUTE_LOCAL_BLOCK);
        Bundle bundle = new Bundle();
        bundle.putString(GDLocalCompliance.BLOCK_ID, str);
        bundle.putString(GDLocalCompliance.TITLE, str2);
        bundle.putString(GDLocalCompliance.MESSAGE, str3);
        newMessage.obj = bundle;
        sendMessageToServer(newMessage);
    }

    public boolean executePendingConsoleMigration(String str, String str2) {
        if (str != null && str.length() != 0) {
            if (str2 != null) {
                GDLog.DBGPRINTF(14, "GDClient: executePendingConsoleMigration - tenantID " + str + " uem " + str2 + "\n");
            } else {
                GDLog.DBGPRINTF(14, "GDClient: executePendingConsoleMigration - tenantID " + str + "\n");
            }
            GDMigrationUtils.signalMigrationStart(str, str2);
            return true;
        }
        GDLog.DBGPRINTF(12, "GDClient: executePendingConsoleMigration - destination tenant ID is empty\n");
        return false;
    }

    public void executeUnblock(String str) {
        GDLog.DBGPRINTF(16, "GDClient.executeUnblock\n");
        GDContext.getInstance().checkAuthorized();
        Message newMessage = MessageFactory.newMessage(MessageFactory.MSG_CLIENT_EXECUTE_LOCAL_UNBLOCK);
        Bundle bundle = new Bundle();
        bundle.putString(GDLocalCompliance.BLOCK_ID, str);
        newMessage.obj = bundle;
        sendMessageToServer(newMessage);
    }

    public void executeWipe() {
        GDLog.DBGPRINTF(16, "GDClient.executeWipe\n");
        sendMessageToServer(MessageFactory.newMessage(MessageFactory.MSG_CLIENT_EXECUTE_WIPE));
    }

    public String getApplicationId() {
        return this._appId;
    }

    public String getApplicationVersion() {
        return this._appVersion;
    }

    @Override // com.good.gd.client.GDCustomizedUI
    public Drawable getBigApplicationLogo() {
        return this.uiBigApplicationLogo;
    }

    public String getBlockMessage() {
        return this.blockMessage;
    }

    public Handler getClientHandler() {
        return this._clientHandler;
    }

    @Override // com.good.gd.client.GDCustomizedUI
    public Integer getCustomUIColor() {
        return this.uiCustomColor;
    }

    @Override // com.good.gd.client.GDCustomizedUI
    public Drawable getSmallApplicationLogo() {
        return this.uiSmallApplicationLogo;
    }

    public String getWipeMessage() {
        return this.wipeMessage;
    }

    public void init(INIT_STATE init_state) {
        initImpl(init_state);
    }

    public void initForeground(GDActivityInfo gDActivityInfo) {
        initImpl(INIT_STATE.STATE_FOREGROUND);
        BBDAppBackgroundDetector.getInstance().notifyForegroundInitForActivity(gDActivityInfo);
    }

    public void initialize() {
        Throwable th;
        InputStream inputStream;
        String string;
        String str = null;
        try {
            try {
                inputStream = GDContext.getInstance().getApplicationContext().getAssets().open("settings.json");
                if (inputStream == null) {
                    string = null;
                } else {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(readBytes(inputStream), HTTP.UTF_8));
                        String string2 = jSONObject.getString("GDApplicationID");
                        string = jSONObject.getString("GDApplicationVersion");
                        str = string2;
                    } catch (IOException e) {
                        GDLog.DBGPRINTF(12, "GD configuration file settings.json not found in APK /assets DIR. Ensure settings.json file is present. Please consult GD documentation for explanation of settings.json file and GD sample applications for examples.\n");
                        throw new GDInitializationError("GD configuration file settings.json not found in APK /assets DIR. Ensure settings.json file is present. Please consult GD documentation for explanation of settings.json file and GD sample applications for examples.");
                    } catch (JSONException e2) {
                        GDLog.DBGPRINTF(12, "GD configuration file settings.json could not be parsed, check file for correct syntax. Please consult GD documentation for explanation of settings.json file and GD sample applications for examples.\n");
                        throw new GDInitializationError("GD configuration file settings.json could not be parsed, check file for correct syntax. Please consult GD documentation for explanation of settings.json file and GD sample applications for examples.");
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e3) {
                                GDLog.DBGPRINTF(12, "Failed to close an input stream in  in GDClient.authorize\n");
                            }
                        }
                        throw th;
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                        GDLog.DBGPRINTF(12, "Failed to close an input stream in  in GDClient.authorize\n");
                    }
                }
                String checkIfGdIdValid = checkIfGdIdValid(str);
                if (TextUtils.isEmpty(checkIfGdIdValid)) {
                    String checkIfVersionValid = checkIfVersionValid(string);
                    if (TextUtils.isEmpty(checkIfVersionValid)) {
                        String checkIfVersionsMatch = checkIfVersionsMatch(string);
                        if (TextUtils.isEmpty(checkIfVersionsMatch)) {
                            this._appId = str;
                            this._appVersion = string;
                            return;
                        }
                        GDLog.DBGPRINTF(12, checkIfVersionsMatch);
                        throw new GDInitializationError(checkIfVersionsMatch);
                    }
                    GDLog.DBGPRINTF(12, checkIfVersionValid);
                    throw new GDInitializationError(checkIfVersionValid);
                }
                GDLog.DBGPRINTF(12, checkIfGdIdValid);
                throw new GDInitializationError(checkIfGdIdValid);
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
            }
        } catch (IOException e5) {
        } catch (JSONException e6) {
        }
    }

    public boolean isAuthorized() {
        return UserAuthUtils.isAuthorised();
    }

    public boolean isInitialized() {
        return this._initialized;
    }

    @Override // com.good.gd.client.GDCustomizedUI
    public boolean isUICustomized() {
        return this.uiSmallApplicationLogo != null;
    }

    public boolean openChangePasswordUI() throws GDNotAuthorizedError {
        GDContext.getInstance().checkAuthorized();
        if (!GDInit.isEnterpriseModeEnabled()) {
            GDLog.DBGPRINTF(16, "GDClient.openChangePasswordUI: authorization is delegated to another application, aborting showing Change Password UI\n");
            return false;
        } else if (checkIfNoPasswordMode()) {
            GDLog.DBGPRINTF(16, "GDClient.openChangePasswordUI: authorization is set to type - No Password, aborting showing Change Password UI\n");
            return false;
        } else {
            sendMessageToServer(MessageFactory.newMessage(1012));
            return true;
        }
    }

    public boolean openFingerprintSettingsUI() throws GDNotAuthorizedError {
        GDContext.getInstance().checkAuthorized();
        if (!GDInit.isEnterpriseModeEnabled()) {
            GDLog.DBGPRINTF(16, "GDClient.openSetupFingerprintUI: authorization is delegated to another application, aborting showing Fingerprint settings UI\n");
            return false;
        } else if (checkIfNoPasswordMode()) {
            GDLog.DBGPRINTF(16, "GDClient.openSetupFingerprintUI: authorization is set to type - No Password, aborting showing Fingerprint settings UI\n");
            return false;
        } else {
            sendMessageToServer(MessageFactory.newMessage(MessageFactory.MSG_FINGERPRINT_OPEN_SETTINGS_UI));
            return true;
        }
    }

    @Override // com.good.gd.ndkproxy.log.LogUploadUIPresenter
    public boolean openLogUploadUI() throws GDNotAuthorizedError {
        GDLog.DBGPRINTF(16, "GDClient.openLogUploadUI\n");
        GDContext.getInstance().checkAuthorized();
        sendMessageToServer(MessageFactory.newMessage(MessageFactory.MSG_CLIENT_OPEN_LOG_UPLOAD_UI));
        return true;
    }

    public boolean openSafeWiFiSetupUI() throws GDNotAuthorizedError {
        GDLog.DBGPRINTF(16, "GDClient.openSafeWiFiSetupUI\n");
        GDContext.getInstance().checkAuthorized();
        sendMessageToServer(MessageFactory.newMessage(MessageFactory.MSG_SAFE_WIFI_OPEN_SETUP_UI));
        return true;
    }

    public void sendGDAppEventToApp(GDAppEvent gDAppEvent) {
        GDLog.DBGPRINTF(16, "GDClient.sendGDAppEventToApp(" + gDAppEvent + "). App already Authed so sending message directly\n");
        Handler handler = this._clientHandler;
        handler.sendMessage(Message.obtain(handler, MessageFactory.MSG_SERVICE_APP_EVENT_CALLBACK, gDAppEvent));
    }

    public void setBlockMessage(String str) {
        this.blockMessage = str;
    }

    public void setWipeMessage(String str) {
        this.wipeMessage = str;
    }

    public void triggerIdleTimerExceeded() {
        sendMessageToServer(MessageFactory.newMessage(MessageFactory.MSG_CLIENT_IDLE_TIMER_EXCEEDED));
    }

    public synchronized void authorize(String str, String str2, GDAppEventListener gDAppEventListener, boolean z) throws GDInitializationError {
        GDLog.DBGPRINTF(16, "GDClient.authorize(\"" + str + "\", \"" + str2 + "\", " + z + ")\n");
        GDInit.checkInitialized();
        GDDefaultAppEventListener.getInstance().setGDAppEventListener(gDAppEventListener);
        this._appId = str;
        this._appVersion = str2;
        this._backgroundAuthAllowed = z;
        AuthorizeMsg authorizeMsg = new AuthorizeMsg();
        authorizeMsg.appId = str;
        authorizeMsg.appVersion = str2;
        authorizeMsg.isBackgroundAuth = z;
        GDDefaultAppEventListener.getInstance().checkTrustedAuthenticator();
        ((GDBypassAbilityImpl) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).init();
        ((GDBackgroundAuthImpl) GDContext.getInstance().getDynamicsService("background_auth")).init();
        if (this._boundToService) {
            sendAuthorizationMessage(authorizeMsg);
        } else {
            this.pendingAuthRequests.add(authorizeMsg);
            this._authorizeOnConnected = true;
        }
    }
}
