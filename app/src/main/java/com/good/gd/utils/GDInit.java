package com.good.gd.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.blackberry.security.threat.ThreatStatus;
import com.good.gd.GDAndroidImpl;
import com.good.gd.GDApacheHttpContainerState;
import com.good.gd.GDIccReceivingActivity;
import com.good.gd.R;
import com.good.gd.authentication.ndkproxy.icc.ReauthProvider;
import com.good.gd.background.detection.BBDAppBackgroundDetector;
import com.good.gd.backgroundexecution.GDBackgroundExecutionManager;
import com.good.gd.backgroundexecution.GDBackgroundExecutionSocketEventListener;
import com.good.gd.bypass.IgnoreBypassRule;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDApplicationContext;
import com.good.gd.context.GDContext;
import com.good.gd.context.GDContextAPI;
import com.good.gd.error.GDInitializationError;
import com.good.gd.icc.csr.CertificateSigningRequestConsumer;
import com.good.gd.icc.easyactivation.ActivationDelegationConsumer;
import com.good.gd.icc.impl.GDServiceClientImpl;
import com.good.gd.icc.impl.GDServiceHelperImplProvider;
import com.good.gd.icc.impl.GDServiceImpl;
import com.good.gd.machines.GDBackgroundExecutionManagerImpl;
import com.good.gd.ndkproxy.GDAppResultCodeNDK;
import com.good.gd.ndkproxy.GDDLPControl;
import com.good.gd.ndkproxy.GDDeviceInfo;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.ndkproxy.GdJniIdCache;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.PasswordFailReason;
import com.good.gd.ndkproxy.PasswordType;
import com.good.gd.ndkproxy.PwdErrors;
import com.good.gd.ndkproxy.attestationApi.AttestationHelper;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.biometric.permission.BiometricPermissionProvider;
import com.good.gd.ndkproxy.bypass.GDBypassAbilityImpl;
import com.good.gd.ndkproxy.enterprise.EntProvErrors;
import com.good.gd.ndkproxy.icc.ActivationDelegationNDKBridge;
import com.good.gd.ndkproxy.icc.ActivationDelegationProvider;
import com.good.gd.ndkproxy.icc.AuthDelegationConsumer;
import com.good.gd.ndkproxy.icc.AuthDelegationProvider;
import com.good.gd.ndkproxy.icc.FeatureSetBridgeImpl;
import com.good.gd.ndkproxy.icc.GDIccConsumer;
import com.good.gd.ndkproxy.icc.GDIccProvider;
import com.good.gd.ndkproxy.icc.GDInterDeviceContainerControl;
import com.good.gd.ndkproxy.icc.GDOnSideChannelDataSentListener;
import com.good.gd.ndkproxy.icc.IccControllerJniEntry;
import com.good.gd.ndkproxy.log.GDLogManagerImpl;
import com.good.gd.ndkproxy.mdm.MDMChecker;
import com.good.gd.ndkproxy.net.GDSocketEventsListenerHolder;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.ndkproxy.net.ssl.GDX509;
import com.good.gd.ndkproxy.pki.GDKeyChainManager;
import com.good.gd.ndkproxy.push.PushFactory;
import com.good.gd.ndkproxy.sharedstore.GDSharedStoreManager;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.net.GDConnectivityManagerImpl;
import com.good.gd.net.GDNetUtilityImpl;
import com.good.gd.notifications.BBDNotificationManager;
import com.good.gd.security.malware.RootedRulesChecker;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.service.GDActivityTimerProvider;
import com.good.gd.service.GDKeyboardControl;
import com.good.gd.service.GDKeyboardMonitor;
import com.good.gd.service.GDPackageMonitor;
import com.good.gd.service.GDUSBMonitor;
import com.good.gd.service.interception.GDServiceHelperImpl;
import com.good.gd.ui.base_ui.GDDebuggableCheckerHolder;
import com.good.gd.ui_control.GDActivityMonitor;
import com.good.gd.ui_control.GDMonitorBypassFragmentImpl;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.utility.DynamicsSharedUserID;
import com.good.gd.utility.GDUtilityImpl;
import com.good.gd.utils.migration.GDAuthDelMigrationResponseHandler;
import com.good.gd.utils.migration.GDAuthDelMigrationStartListener;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.IccCoreProtocolTag;
import com.good.gt.icc.IccProtocol;
import com.good.gt.icc.IccServicesClient;
import com.good.gt.icc.IccServicesServer;
import com.good.gt.icc.IccVersion;
import com.good.gt.icc.ListenerAlreadySetException;
import com.good.gt.ndkproxy.GTInit;
import com.good.gt.ndkproxy.icc.IccManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class GDInit {
    private static final Class<?> GDIccReceivingActivityClass = GDIccReceivingActivity.class;
    private static ICCController _iccController = null;
    private static IccControllerJniEntry _iccControllerJniEntry = null;
    private static boolean _isInitialized = false;
    private static boolean _isIccInitialized = false;
    private static Exception _failedInitializationException = null;
    private static GDX509 s_GDX509 = null;
    private static RootedRulesChecker s_rootedRulesChecker = null;
    private static Set<String> _exportedActivities = new HashSet();
    private static String TAG = "GDInit";
    private static Thread _thread = null;
    private static GDIccConsumer _consumer = null;
    private static GDIccProvider _provider = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc implements GDKeyChainManager.ActivityProvider {
        hbfhc() {
        }

        @Override // com.good.gd.ndkproxy.pki.GDKeyChainManager.ActivityProvider
        public Activity onActivityRequested() {
            Activity mo295getInternalActivity;
            UniversalActivityController universalActivityController = UniversalActivityController.getInstance();
            if (universalActivityController.getContentActivity() != null) {
                mo295getInternalActivity = universalActivityController.getContentActivity();
            } else {
                mo295getInternalActivity = universalActivityController.mo295getInternalActivity();
            }
            return mo295getInternalActivity == null ? (Activity) universalActivityController.getCurrentActivityContext() : mo295getInternalActivity;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            IccServicesServer iccServicesServer = GDInit._iccController.getIccServicesServer();
            IccServicesClient iccServicesClient = GDInit._iccController.getIccServicesClient();
            try {
                iccServicesServer.setServiceListener(GDInit._provider);
                iccServicesClient.setServiceClientListener(GDInit._consumer);
                GDInit._provider.setServicesServer(iccServicesServer);
                GDInit._consumer.setServicesClient(iccServicesClient);
                IccManager.getInstance().setMigrationResponseHandler(new GDAuthDelMigrationResponseHandler());
                IccManager.getInstance().setMigrationstartListener(new GDAuthDelMigrationStartListener());
                IccManager.getInstance().setOnSideChannelDataSentListener(new GDOnSideChannelDataSentListener());
                IccManager.getInstance().setFeatureSetBridge(new FeatureSetBridgeImpl());
            } catch (ListenerAlreadySetException e) {
            }
        }
    }

    private static void checkAndroidManifest() throws Exception {
        ActivityInfo[] activityInfoArr;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        PackageManager packageManager = applicationContext.getPackageManager();
        String packageName = applicationContext.getPackageName();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 133);
        ActivityInfo[] activityInfoArr2 = packageInfo.activities;
        if (activityInfoArr2 == null || activityInfoArr2.length == 0) {
            Thread.sleep(1000L);
            PackageManager packageManager2 = applicationContext.getPackageManager();
            packageName = applicationContext.getPackageName();
            packageInfo = packageManager2.getPackageInfo(packageName, 133);
            ActivityInfo[] activityInfoArr3 = packageInfo.activities;
            if (activityInfoArr3 == null || activityInfoArr3.length == 0) {
                throw new ehnkx("Application has no activities!");
            }
        }
        checkAndroidVersions(packageInfo);
        GDBackupSchemeValidator.checkAndroidBackupConfiguration(packageInfo);
        checkICCActivityClass(packageName);
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (ActivityInfo activityInfo : packageInfo.activities) {
            if (activityInfo.name.equals("com.good.gd.ui.GDInternalActivity")) {
                z = true;
            } else if (activityInfo.name.equals("com.good.gt.ndkproxy.icc.IccActivity")) {
                _exportedActivities.add(activityInfo.name);
                z2 = true;
            } else if (activityInfo.name.equals(IccCoreProtocolTag.defaultRequestIntent)) {
                _exportedActivities.add(activityInfo.name);
                z3 = true;
            } else if (activityInfo.name.equals("com.good.gd.ui.dialogs.GDDialogActivity")) {
                z4 = true;
            } else if (activityInfo.exported) {
                _exportedActivities.add(activityInfo.name);
                if (activityInfo.launchMode == 3) {
                    throw new orlrx("activity \"" + activityInfo.name + "\": launchMode=singleInstance is not currently supported");
                }
            } else {
                continue;
            }
            checkMetaData(activityInfo);
        }
        if (z) {
            if (!z2) {
                throw new com.good.gd.utils.hbfhc("IccActivity not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
            }
            if (!z3) {
                throw new com.good.gd.utils.hbfhc("GDIccReceivingActivity not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
            }
            if (z4) {
                ServiceInfo[] serviceInfoArr = packageInfo.services;
                if (serviceInfoArr != null && serviceInfoArr.length != 0) {
                    ServiceInfo serviceInfo = null;
                    ServiceInfo serviceInfo2 = null;
                    for (ServiceInfo serviceInfo3 : serviceInfoArr) {
                        if (serviceInfo3.name.equals("com.good.gd.service.GDService")) {
                            serviceInfo = serviceInfo3;
                        }
                        if (serviceInfo3.name.equals(IccCoreProtocolTag.GD_SDK_ICC_Service)) {
                            serviceInfo2 = serviceInfo3;
                        }
                    }
                    if (serviceInfo != null) {
                        if (serviceInfo.exported) {
                            throw new mjbm("com.good.gd.service.GDService is declared as exported. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                        }
                        if (serviceInfo2 != null) {
                            if (serviceInfo2.exported) {
                                if (serviceInfo2.enabled) {
                                    Bundle bundle = serviceInfo2.metaData;
                                    if (bundle != null) {
                                        IccVersion iccVersion = IccProtocol.getIccVersion(bundle);
                                        if (iccVersion != null) {
                                            if (iccVersion == IccVersion.V2_5) {
                                                checkRequiredPermissionsRequested();
                                                if (packageInfo.sharedUserId == null) {
                                                    return;
                                                }
                                                GDLog.DBGPRINTF(13, "WARNING: Attribute sharedUserId is not supported\n");
                                                return;
                                            }
                                            throw new com.good.gd.utils.yfdke("com.good.gd.service.GDIccService GD_ICC_VERSION incorrect version. Expected 2.5. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                                        }
                                        throw new com.good.gd.utils.yfdke("com.good.gd.service.GDIccService GD_ICC_VERSION MetaData missing. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                                    }
                                    throw new com.good.gd.utils.yfdke("com.good.gd.service.GDIccService required MetaData missing. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                                }
                                throw new mjbm("com.good.gd.service.GDIccService is not declared as enabled. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                            }
                            throw new mjbm("com.good.gd.service.GDIccService is not declared as exported. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                        }
                        throw new mjbm("com.good.gd.service.GDIccService not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                    }
                    throw new mjbm("com.good.gd.service.GDService not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
                }
                throw new pmoiy("Application has no services! Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
            }
            throw new com.good.gd.utils.hbfhc("GDDialogActivity not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
        }
        throw new com.good.gd.utils.hbfhc("GDInternalActivity not declared. Ensure all components from GD SDK Library manifest are included in your AndroidManifest.xml. The recommended approach is to set manifestmerger.enabled=true in your app's project.properties file to automatically include all GD components");
    }

    private static void checkAndroidVersions(PackageInfo packageInfo) throws Exception {
        if (Build.VERSION.SDK_INT >= 26) {
            return;
        }
        throw new aqdzk("GD SDK supports minimum API level 26 ");
    }

    public static boolean checkGDAppVersionMismatch(String str, String str2) {
        ApplicationInfo applicationInfo;
        Object obj;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        PackageManager packageManager = applicationContext.getPackageManager();
        try {
            if (str != null) {
                applicationInfo = packageManager.getApplicationInfo(str, 128);
            } else {
                applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128);
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null && (obj = bundle.get("GDApplicationVersion")) != null) {
                String obj2 = obj.toString();
                if (obj2 != null) {
                    return obj2.equals(str2);
                }
                GDLog.DBGPRINTF(13, "AndroidManifest.xml is missing GDApplicationVersion meta-data tag.\n");
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    private static void checkICCActivityClass(String str) throws Exception {
        for (ResolveInfo resolveInfo : GDContext.getInstance().getApplicationContext().getPackageManager().queryIntentActivities(new Intent(IccCoreProtocolTag.actionString), 0)) {
            if (resolveInfo.activityInfo.applicationInfo.packageName.equals(str) && !resolveInfo.activityInfo.name.equals("com.good.gt.ndkproxy.icc.IccActivity")) {
                try {
                    if (!GDIccReceivingActivityClass.isAssignableFrom(Class.forName(resolveInfo.activityInfo.name))) {
                        throw new com.good.gd.utils.hbfhc("Error, not extending GDIccReceivingActivity. This Application contains an ICCReceivingActivity which does not extend GDIccReceivingActivity, this is not permitted");
                    }
                } catch (ClassNotFoundException e) {
                    throw new com.good.gd.utils.hbfhc("This application contains an Activity that apparently has no class: " + resolveInfo.activityInfo.name);
                }
            }
        }
    }

    public static void checkInitializeFailed() throws GDInitializationError {
        if (_isInitialized || _failedInitializationException == null) {
            return;
        }
        throw new GDInitializationError(_failedInitializationException);
    }

    public static void checkInitialized() throws GDInitializationError {
        if (!_isInitialized) {
            if (_failedInitializationException != null) {
                throw new GDInitializationError(_failedInitializationException);
            }
            throw new GDInitializationError("Error GD Not Initialized but no explict Failure reason supplied");
        }
    }

    private static void checkMetaData(ActivityInfo activityInfo) throws Exception {
        Bundle bundle = activityInfo.metaData;
        if (bundle == null) {
            return;
        }
        if (bundle.getBoolean(GDMonitorBypassFragmentImpl.BYPASS_ACTIVITY_TAG, false)) {
            ((GDBypassAbilityImpl) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).addActivity(activityInfo.name);
        }
        bundle.get(GDActivityMonitor.nameGDMonitorActivityAutoInsert);
    }

    protected static synchronized void checkRequiredPermissionsRequested() throws Exception {
        synchronized (GDInit.class) {
            boolean z = false;
            String[] strArr = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.WAKE_LOCK", new BiometricPermissionProvider().getPermissions()};
            PackageInfo packageInfo = null;
            try {
                packageInfo = GDContext.getInstance().getApplicationContext().getPackageManager().getPackageInfo(GDContext.getInstance().getApplicationContext().getPackageName(), 4096);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String str = "";
            if (packageInfo.requestedPermissions != null) {
                ArrayList arrayList = new ArrayList(Arrays.asList(packageInfo.requestedPermissions));
                int i = 0;
                boolean z2 = false;
                while (true) {
                    if (i >= 5) {
                        z = z2;
                        break;
                    }
                    String str2 = strArr[i];
                    if (!arrayList.contains(str2)) {
                        str = str2;
                        break;
                    } else {
                        i++;
                        z2 = true;
                    }
                }
            }
            if (!z) {
                throw new fdyxd("Permissions not declared, including " + str);
            }
        }
    }

    public static boolean ensureGDInitialized(Context context, INIT_STATE init_state) {
        if (isInitialized()) {
            return false;
        }
        GDContext.getInstance().setContext(context.getApplicationContext());
        initialize(init_state);
        return true;
    }

    public static native boolean enterpriseSimulationModeEnabled();

    public static synchronized ICCController getIccController() {
        ICCController iCCController;
        synchronized (GDInit.class) {
            iCCController = _iccController;
        }
        return iCCController;
    }

    public static synchronized boolean initialize(INIT_STATE init_state) throws GDInitializationError {
        synchronized (GDInit.class) {
            if (_isInitialized) {
                return true;
            }
            if (_failedInitializationException == null) {
                try {
                    CpuArchUtils.checkCPUArch();
                    GDNDKLibraryLoader.loadNDKLibrary();
                    GDLog.initialize(GDSDKType.ESDKTypeHandheld);
                    GDSettings.initialize();
                    GDLocalizer.createInstance(R.raw.class);
                    checkAndroidManifest();
                    GDTEEManager.createInstance().Init();
                    if (!GDDeviceInfo.getInstance().isInitialized()) {
                        GDDeviceInfo.getInstance().initialize();
                    }
                    GDSettings.applyOverrides();
                    UniversalActivityController.getInstance().initialize();
                    initializeLifecycleCallback(GDContext.getInstance());
                    GDUSBMonitor.initializeInstance();
                    GDPackageMonitor.initializeInstance();
                    GDKeyboardMonitor.initializeInstance();
                    GDDLPControl.getInstance().initializeInstance();
                    GDKeyboardControl.initializeInstance();
                    GdJniIdCache.initialize();
                    s_GDX509 = GDX509.getInstance();
                    s_rootedRulesChecker = RootedRulesChecker.getInstance();
                    PasswordType.initialize();
                    PasswordFailReason.initialize();
                    PwdErrors.initialize();
                    EntProvErrors.initialize();
                    GDAppResultCodeNDK.initialize();
                    GDLibraryUI.getInstance().initialize();
                    GDApacheHttpContainerState.setContainerState(GDContext.getInstance());
                    GDSocketEventsListenerHolder.setListener(new GDBackgroundExecutionSocketEventListener());
                    NetworkStateMonitor.getInstance().initialize(new GDApplicationContext(), GDContext.getInstance(), GDDeviceInfo.getInstance());
                    GDKeyChainManager.getInstance().init(new hbfhc());
                    GDActivityTimerProvider.createInstance();
                    BBDNotificationManager.createInstance(GDContext.getInstance().getApplicationContext());
                    AuthDelegationProvider.getInstance().initialize();
                    AuthDelegationConsumer.getInstance().initialize();
                    ActivationDelegationNDKBridge.initialize();
                    ActivationDelegationProvider.getInstance().initialize();
                    ActivationDelegationConsumer.getInstance().initialize();
                    CertificateSigningRequestConsumer.getInstance().initialize();
                } catch (Exception e) {
                    _failedInitializationException = e;
                    String message = e.getMessage();
                    if (message == null) {
                        message = e.toString();
                    }
                    Log.e("GD Init Error", "Exception during GD initialization: " + message + "\n");
                    StackTraceElement[] stackTrace = e.getStackTrace();
                    int length = stackTrace.length;
                    for (int i = 0; i < length; i++) {
                        Log.e("GD Init Error", "StackTrace: " + stackTrace[i] + "\n");
                    }
                }
                if (GTInit.initialize()) {
                    if (!GDIccProvider.getInstance().isApplicationListenerSet()) {
                        GTInit.setIccServerEnabled(false);
                    }
                    GTInit.setNativeLock(NativeExecutionHandler.nativeLockGT);
                    initializeIcc();
                    GDInterDeviceContainerControl.getInstance();
                    GDActivityStateManager.createInstance(init_state);
                    MDMChecker.getInstance().init();
                    GDServiceHelperImplProvider.getInstance().initialize(GDServiceHelperImpl.newInstance());
                    GDFingerprintAuthenticationManager.getInstance().init();
                    GDBackgroundExecutionManager.createInstance(new GDBackgroundExecutionManagerImpl(init_state));
                    AttestationHelper.initialize();
                    com.blackberry.security.mtd.hbfhc.dbjc();
                    com.good.gd.nme.hbfhc.wxau();
                    PushFactory.initialize(GDContext.getInstance(), new GDApplicationContext(), NetworkStateMonitor.getInstance());
                    GDLogManagerImpl.setLogUploadUIPresenter(GDClient.getInstance());
                    BBDUIManager.initialize(UniversalActivityController.getInstance());
                    GDDebuggableCheckerHolder.setDebuggableChecker(new GDDebuggableChecker());
                    GDConnectivityManagerImpl.initialize(GDContext.getInstance());
                    com.blackberry.analytics.analyticsengine.yfdke.dbjc(GDAndroidImpl.getInstance());
                    com.blackberry.analytics.analyticsengine.yfdke.dbjc(new GDUtilityImpl());
                    com.blackberry.analytics.analyticsengine.yfdke.dbjc((DynamicsSharedUserID) new GDUtilityImpl());
                    com.blackberry.analytics.analyticsengine.yfdke.dbjc(GDContext.getInstance());
                    com.blackberry.analytics.analyticsengine.hbfhc.dbjc((Application) GDContext.getInstance().getApplicationContext());
                    com.blackberry.bis.core.yfdke.muee().dbjc(new com.good.gd.vocp.hbfhc());
                    ThreatStatus.getInstance();
                    GDNetUtilityImpl.initialize(new GDApplicationContext());
                    _isInitialized = true;
                    GDLog.DBGPRINTF(16, "GDInit: basic initialization complete\n");
                    return _isInitialized;
                }
                throw new GTServicesException(GTServicesException.Code.SERVICES_GENERAL);
            }
            throw new GDInitializationError(_failedInitializationException);
        }
    }

    public static synchronized boolean initializeIcc() {
        synchronized (GDInit.class) {
            boolean z = _isIccInitialized;
            if (z) {
                return z;
            }
            GDNDKLibraryLoader.loadNDKLibrary();
            GDIccConsumer gDIccConsumer = GDIccConsumer.getInstance();
            _consumer = gDIccConsumer;
            gDIccConsumer.init();
            _provider = GDIccProvider.getInstance();
            GDServiceImpl.getInstance().setProviderInterface(_provider);
            GDServiceClientImpl.getInstance().setConsumerInterface(_consumer);
            GDSharedStoreManager.getInstance().registerService();
            _iccController = ICCControllerFactory.getICCController(_consumer);
            Thread thread = new Thread(new yfdke());
            _thread = thread;
            thread.start();
            _iccControllerJniEntry = new IccControllerJniEntry(_iccController);
            ReauthProvider.getInstance();
            _isIccInitialized = true;
            return true;
        }
    }

    private static void initializeLifecycleCallback(GDContextAPI gDContextAPI) {
        BBDAppBackgroundDetector.initializeBackgroundDetector(new GDApplicationContext());
        BBDAppBackgroundDetector.createInstance(new IgnoreBypassRule(gDContextAPI));
    }

    public static boolean isActivityExported(String str) {
        return _exportedActivities.contains(str);
    }

    public static native boolean isEnterpriseModeEnabled();

    public static boolean isInitialized() {
        return _isInitialized;
    }
}
