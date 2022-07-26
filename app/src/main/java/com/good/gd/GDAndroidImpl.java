package com.good.gd;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import com.good.gd.ApplicationState;
import com.good.gd.client.GDClient;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.context.GDContext;
import com.good.gd.error.GDInitializationError;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.error.GDNotSupportedError;
import com.good.gd.file.File;
import com.good.gd.machines.activation.GDActivationManager;
import com.good.gd.ndkproxy.ApplicationConfig;
import com.good.gd.ndkproxy.ApplicationPolicy;
import com.good.gd.ndkproxy.ApplicationService;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.ui.GDActivateFingerprintViewHandler;
import com.good.gd.ndkproxy.ui.GDRemoteLock;
import com.good.gd.ui_control.GDMonitorFragment;
import com.good.gd.ui_control.GDMonitorFragmentImpl;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utils.GDActivityInfo;
import com.good.gd.utils.GDActivityUtils;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.GDStateListenerAuthCallback;
import com.good.gd.utils.GDStateListenerHelper;
import com.good.gd.utils.INIT_STATE;
import com.good.gd.utils.IccIntentUtils;
import com.good.gd.utils.UserAuthUtils;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes.dex */
public final class GDAndroidImpl implements GDAndroidAPI {
    private static GDAndroidImpl instance;
    private static ArrayMap<String, ArrayMap<String, GDSharedPreferencesImpl>> sSharedPrefs;
    private GDStateListenerHelper mStateListenerHelper = new GDStateListenerHelper();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        final /* synthetic */ Context dbjc;

        hbfhc(GDAndroidImpl gDAndroidImpl, Context context) {
            this.dbjc = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            ((GDStateListener) this.dbjc).onAuthorized();
        }
    }

    private GDAndroidImpl() {
        GDActivateFingerprintViewHandler.getInstance();
    }

    public static void executeBlock(String str, String str2, String str3) {
        GDLog.DBGPRINTF(14, "GDAndroid::executeBlock()\n");
        GDClient.getInstance().executeBlock(str, str2, str3);
    }

    public static boolean executePendingConsoleMigration(String str, String str2) {
        GDLog.DBGPRINTF(14, "GDAndroid::executePendingConsoleMigration()\n");
        return GDClient.getInstance().executePendingConsoleMigration(str, str2);
    }

    public static void executeRemoteLock() {
        GDLog.DBGPRINTF(14, "GDAndroid::executeRemoteLock()\n");
        GDRemoteLock.getInstance().handleRemoteLockPublicAPIRequest();
    }

    public static void executeUnblock(String str) {
        GDLog.DBGPRINTF(14, "GDAndroid::executeUnblock()\n");
        GDClient.getInstance().executeUnblock(str);
    }

    public static GDAndroidImpl getInstance() {
        if (instance == null) {
            instance = new GDAndroidImpl();
        }
        return instance;
    }

    public static String getVersion() {
        return GDDeviceInfo.getInstance().getClientVersion();
    }

    public void activityInit(android.app.Activity activity) throws GDInitializationError {
        if (!GDActivityUtils.checkIsGDActivity(activity)) {
            UniversalActivityController.getInstance().setContentActivity(activity);
            Context applicationContext = activity.getApplicationContext();
            this.mStateListenerHelper.init(applicationContext);
            boolean checkImplementsGDStateListener = GDActivityUtils.checkImplementsGDStateListener(activity);
            if (this.mStateListenerHelper.isApplicationAllowedToRun(checkImplementsGDStateListener)) {
                if (checkImplementsGDStateListener) {
                    GDDefaultAppEventListener.getInstance().addGDStateListener((GDStateListener) activity);
                }
                GDContext.getInstance().setContext(applicationContext);
                GDClient.getInstance().initForeground(new GDActivityInfo(activity));
                boolean isUserAuthRequired = UserAuthUtils.isUserAuthRequired();
                GDLog.DBGPRINTF(14, "GDAndroid::activityInit(), activity = " + activity.getComponentName().getClassName() + " shouldAuth=" + isUserAuthRequired + "\n");
                boolean checkForIccIntent = IccIntentUtils.checkForIccIntent(activity.getIntent());
                FragmentManager fragmentManager = activity.getFragmentManager();
                if (fragmentManager.findFragmentByTag(GDMonitorFragmentImpl.GDMONITORFRAGMENT) == null) {
                    GDMonitorFragment newInstance = GDMonitorFragment.newInstance(checkForIccIntent);
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add(newInstance, GDMonitorFragmentImpl.GDMONITORFRAGMENT);
                    beginTransaction.commit();
                    fragmentManager.executePendingTransactions();
                } else {
                    GDLog.DBGPRINTF(13, "GDAndroid::activityInit(), activity = " + activity.getComponentName().getShortClassName() + " GD Monitor Fragment already inserted. Is this correct ?\n");
                }
                if (isUserAuthRequired) {
                    return;
                }
                if (activity.isTaskRoot() && !checkImplementsGDStateListener) {
                    GDClient.getInstance().sendGDAppEventToApp(new GDAppEvent("Authorized", GDAppResultCode.GDErrorNone, GDAppEventType.GDAppEventAuthorized));
                    return;
                } else if (!checkImplementsGDStateListener) {
                    return;
                } else {
                    new GDStateListenerAuthCallback(activity);
                    return;
                }
            }
            throw new GDInitializationError("Each Activity must implement GDStateListener interface if a singleton interface has not been provided to GDAndroid");
        }
        throw new GDInitializationError("Invalid call to GDAndroid.activityInit( ) from a GD Activity. Must only be called from non GD based Activities");
    }

    public void applicationInit(Context context) throws GDInitializationError {
        if (context != null) {
            GDContext.getInstance().setContext(context.getApplicationContext());
            GDClient.getInstance().init(INIT_STATE.STATE_UNKNOWN);
            return;
        }
        throw new GDInitializationError("Null context passed to GDAndroid applicationInit");
    }

    public void authorize(GDAppEventListener gDAppEventListener) throws GDInitializationError {
        this.mStateListenerHelper.setExplicitListenerSet(true);
        GDClient.getInstance().authorize(gDAppEventListener, false);
    }

    public boolean canAuthorizeAutonomously(Context context) {
        return UserAuthUtils.canAuthorizeAutonomously(context);
    }

    public void configureUI(Drawable drawable, Drawable drawable2, Integer num) {
        if (GDContext.getInstance().isWiped()) {
            return;
        }
        GDClient.getInstance().configureUI(drawable, drawable2, num);
    }

    @Deprecated
    public void configureUIWithBlockedMessage(String str) {
        GDClient.getInstance().setBlockMessage(str);
    }

    @Deprecated
    public void configureUIWithWipedMessage(String str) {
        GDClient.getInstance().setWipeMessage(str);
    }

    @Override // com.good.gd.GDAndroidAPI
    public void executeLocalBlock(String str, String str2, String str3) {
        executeBlock(str, str2, str3);
    }

    @Override // com.good.gd.GDAndroidAPI
    public void executeLocalUnblock(String str) {
        executeUnblock(str);
    }

    public String getAppVersion() throws GDInitializationError {
        String applicationVersion = GDClient.getInstance().getApplicationVersion();
        if (applicationVersion != null) {
            return applicationVersion;
        }
        throw new GDInitializationError("getAppVersion( ) must be called after GD Authorization");
    }

    @Override // com.good.gd.GDAndroidAPI
    public Map<String, Object> getApplicationConfig() throws GDNotAuthorizedError {
        if (GDContext.getInstance().isWiped()) {
            return new HashMap();
        }
        GDContext.getInstance().checkAuthorized();
        return ApplicationConfig.getInstance().getApplicationConfig();
    }

    public String getApplicationId() throws GDInitializationError {
        String applicationId = GDClient.getInstance().getApplicationId();
        if (applicationId != null) {
            return applicationId;
        }
        throw new GDInitializationError("getApplicationId( ) must be called after GD Authorization");
    }

    public Map<String, Object> getApplicationPolicy() throws GDNotAuthorizedError {
        if (GDContext.getInstance().isWiped()) {
            return new HashMap();
        }
        GDContext.getInstance().checkAuthorized();
        return ApplicationPolicy.getInstance().getApplicationPolicy();
    }

    public String getApplicationPolicyString() throws GDNotAuthorizedError {
        if (GDContext.getInstance().isWiped()) {
            return "";
        }
        GDContext.getInstance().checkAuthorized();
        return ApplicationPolicy.getInstance().getApplicationPolicyString();
    }

    @Override // com.good.gd.GDAndroidAPI
    public String getClientVersion() {
        return getVersion();
    }

    @Override // com.good.gd.GDAndroidAPI
    public int getEntitlementVersions(String str, GDEntitlementVersionsRequestCallback gDEntitlementVersionsRequestCallback) {
        return GDMamWrapper.getInstance().getEntitlementVersions(str, gDEntitlementVersionsRequestCallback);
    }

    @Override // com.good.gd.GDAndroidAPI
    public SharedPreferences getGDSharedPreferences(String str, int i) {
        GDSharedPreferencesImpl gDSharedPreferencesImpl;
        GDContext.getInstance().checkAuthorized();
        if (i == 0) {
            synchronized (GDAndroid.class) {
                if (sSharedPrefs == null) {
                    sSharedPrefs = new ArrayMap<>();
                }
                String packageName = GDContext.getInstance().getApplicationContext().getPackageName();
                ArrayMap<String, GDSharedPreferencesImpl> arrayMap = sSharedPrefs.get(packageName);
                if (arrayMap == null) {
                    arrayMap = new ArrayMap<>();
                    sSharedPrefs.put(packageName, arrayMap);
                }
                gDSharedPreferencesImpl = arrayMap.get(str);
                if (gDSharedPreferencesImpl == null) {
                    GDSharedPreferencesImpl gDSharedPreferencesImpl2 = new GDSharedPreferencesImpl(new File("gd_shared_prefs", str + ".xml"), i);
                    arrayMap.put(str, gDSharedPreferencesImpl2);
                    gDSharedPreferencesImpl = gDSharedPreferencesImpl2;
                }
            }
            return gDSharedPreferencesImpl;
        }
        throw new GDNotSupportedError("GDSharedPreferences only supports MODE_PRIVATE");
    }

    public Vector<GDServiceProvider> getServiceProviders() throws GDNotAuthorizedError {
        if (GDContext.getInstance().isWiped()) {
            return new Vector<>();
        }
        GDContext.getInstance().checkAuthorized();
        return ApplicationService.getInstance().getServiceProviders();
    }

    @Override // com.good.gd.GDAndroidAPI
    public Vector<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError {
        GDContext.getInstance().checkAuthorized();
        return ApplicationService.getInstance().getServiceProvidersForService(str, str2, gDServiceType);
    }

    public boolean isActivated(Context context) {
        GDContext.getInstance().setContext(context.getApplicationContext());
        return GDActivationManager.getInstance().getActivationState() == ApplicationState.ActivationState.EActivated;
    }

    public boolean openChangePasswordUI() throws GDNotAuthorizedError {
        return !GDContext.getInstance().isWiped() && GDClient.getInstance().openChangePasswordUI();
    }

    public boolean openFingerprintSettingsUI() throws GDNotAuthorizedError {
        return !GDClient.getInstance().checkIfNoPasswordMode() && !GDContext.getInstance().isWiped() && GDClient.getInstance().openFingerprintSettingsUI();
    }

    public boolean openSafeWiFiSetupUI() throws GDNotAuthorizedError {
        return !GDContext.getInstance().isWiped() && GDClient.getInstance().openSafeWiFiSetupUI();
    }

    public void programmaticActivityInit(android.app.Activity activity, Bundle bundle) {
        GDContext.getInstance().setContext(activity.getApplicationContext());
        GDActivationManager gDActivationManager = GDActivationManager.getInstance();
        if (gDActivationManager.configureProgrammaticActivation(bundle)) {
            GDClient.getInstance().initForeground(new GDActivityInfo(activity));
            activityInit(activity);
            gDActivationManager.startProgrammaticActivation();
        }
    }

    public void programmaticServiceInit(Context context, Bundle bundle) {
        serviceInit(context);
        GDContext.getInstance().setContext(context.getApplicationContext());
        if (!GDClient.getInstance().isInitialized()) {
            GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() GDClient not initialised, calling init()");
            GDClient.getInstance().init(INIT_STATE.STATE_BACKGROUND);
        }
        GDActivationManager gDActivationManager = GDActivationManager.getInstance();
        if (gDActivationManager.configureProgrammaticActivationInBackground(bundle)) {
            gDActivationManager.startProgrammaticActivation();
        }
    }

    @Override // com.good.gd.GDAndroidAPI
    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (GDContext.getInstance().getApplicationContext() != null) {
            GDLocalBroadcastManager.getInstance().registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public boolean serviceInit(Context context) throws GDInitializationError {
        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit()\n");
        if (context != null) {
            if (Looper.getMainLooper().isCurrentThread()) {
                this.mStateListenerHelper.init(context);
                boolean checkImplementsGDStateListener = GDActivityUtils.checkImplementsGDStateListener(context);
                if (this.mStateListenerHelper.isApplicationAllowedToRun(checkImplementsGDStateListener)) {
                    GDContext.getInstance().setContext(context.getApplicationContext());
                    if (!UserAuthUtils.isActivated()) {
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() Container not activated, return false.\n");
                        return false;
                    }
                    if (!GDClient.getInstance().isInitialized()) {
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() GDClient not initialised, calling init()");
                        GDClient.getInstance().init(INIT_STATE.STATE_BACKGROUND);
                    }
                    if (checkImplementsGDStateListener) {
                        GDDefaultAppEventListener.getInstance().addGDServiceStateListener(new WeakReference<>((GDStateListener) context));
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() added a new services state listener");
                    }
                    if (!UserAuthUtils.isUserAuthRequired()) {
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() already Authorized\n");
                        if (checkImplementsGDStateListener) {
                            new Handler().postDelayed(new hbfhc(this, context), 0L);
                        }
                    } else if (UserAuthUtils.canAuthorizeAutonomously(context)) {
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() calling Authorize\n");
                        GDClient.getInstance().authorize(null, true);
                    } else {
                        GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() failed - \nContainer Locked and Background Authorize not allowed, so can't do background processing on the Container. \n");
                        return false;
                    }
                    GDLog.DBGPRINTF(14, "GDAndroid::serviceInit() successful can access GD in background\n");
                    return true;
                }
                throw new GDInitializationError("Each Service must implement GDStateListener interface if a singleton interface has not been provided to GDAndroid");
            }
            throw new GDInitializationError("Call must be made on main thread");
        }
        throw new GDInitializationError("The context passed to ServiceInit is invalid.");
    }

    public void setGDAppEventListener(GDAppEventListener gDAppEventListener) {
        this.mStateListenerHelper.setExplicitListenerSet(true);
        GDDefaultAppEventListener.getInstance().setGDAppEventListener(gDAppEventListener);
    }

    public void setGDStateListener(GDStateListener gDStateListener) {
        if (!(gDStateListener instanceof android.app.Activity)) {
            this.mStateListenerHelper.setExplicitListenerSet(true);
            GDDefaultAppEventListener.getInstance().setMainGDStateListener(gDStateListener);
            return;
        }
        throw new GDInitializationError("setGDStateListener: listener cannot be an Activity instance");
    }

    public boolean supportsFingerprintAuthentication() throws GDNotAuthorizedError {
        if (GDContext.getInstance().isWiped()) {
            return false;
        }
        GDContext.getInstance().checkAuthorized();
        return GDInit.isEnterpriseModeEnabled() && GDFingerprintAuthenticationManager.getInstance().supportsFingerprintAuthentication(GDActivitySupport.isAuthorised() ^ true);
    }

    @Override // com.good.gd.GDAndroidAPI
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        if (GDContext.getInstance().getApplicationContext() != null) {
            GDLocalBroadcastManager.getInstance().unregisterReceiver(broadcastReceiver);
        }
    }

    @Deprecated
    public Vector<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceProviderType gDServiceProviderType) throws GDNotAuthorizedError {
        GDContext.getInstance().checkAuthorized();
        return ApplicationService.getInstance().getServiceProvidersForService(str, str2, gDServiceProviderType);
    }

    @Deprecated
    public void programmaticActivityInit(android.app.Activity activity, String str, String str2) {
        Bundle bundle = new Bundle();
        ApplicationState.ActivationParameter activationParameter = ApplicationState.ActivationParameter.UserIdentifier;
        bundle.putString("UserIdentifier", str);
        ApplicationState.ActivationParameter activationParameter2 = ApplicationState.ActivationParameter.AccessKey;
        bundle.putString("AccessKey", str2);
        programmaticActivityInit(activity, bundle);
    }

    @Deprecated
    public void programmaticActivityInit(android.app.Activity activity, String str, String str2, URL url) {
        Bundle bundle = new Bundle();
        ApplicationState.ActivationParameter activationParameter = ApplicationState.ActivationParameter.UserIdentifier;
        bundle.putString("UserIdentifier", str);
        ApplicationState.ActivationParameter activationParameter2 = ApplicationState.ActivationParameter.AccessKey;
        bundle.putString("AccessKey", str2);
        String url2 = (url == null || url.toString().length() <= 0) ? null : url.toString();
        if (url2 != null && !url2.isEmpty()) {
            ApplicationState.ActivationParameter activationParameter3 = ApplicationState.ActivationParameter.NOCAddress;
            bundle.putString("NOCAddress", url2);
        }
        programmaticActivityInit(activity, bundle);
    }
}
