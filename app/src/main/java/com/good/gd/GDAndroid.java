package com.good.gd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.good.gd.error.GDInitializationError;
import com.good.gd.error.GDNotAuthorizedError;
import java.net.URL;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes.dex */
public class GDAndroid {
    public static final String GDAppConfigKeyAndroidKeyboardRestrictedMode = "keyboardRestrictedMode";
    public static final String GDAppConfigKeyCommunicationProtocols = "communicationProtocols";
    public static final String GDAppConfigKeyConfig = "appConfig";
    @Deprecated
    public static final String GDAppConfigKeyCopyPasteOn = "copyPasteOn";
    public static final String GDAppConfigKeyDetailedLogsOn = "detailedLogsOn";
    public static final String GDAppConfigKeyEnterpriseIdFeatures = "enterpriseIdFeatures";
    public static final String GDAppConfigKeyExtraInfo = "extraInfo";
    public static final String GDAppConfigKeyInstanceIdentifier = "containerId";
    public static final String GDAppConfigKeyPreventAndroidDictation = "preventAndroidDictation";
    public static final String GDAppConfigKeyPreventDataLeakageIn = "preventPasteFromNonGDApps";
    public static final String GDAppConfigKeyPreventDataLeakageOut = "copyPasteOn";
    public static final String GDAppConfigKeyPreventScreenCapture = "preventScreenCapture";
    public static final String GDAppConfigKeyPreventUserDetailedLogs = "preventUserDetailedLogs";
    public static final String GDAppConfigKeyProtectedByPassword = "protectedByPassword";
    public static final String GDAppConfigKeyServers = "appServers";
    public static final String GDAppConfigKeyUserId = "userId";
    public static final String GDAppConfigKeyUserPrincipalName = "upn";
    public static final String GDAppEnterpriseIdActivated = "enterpriseIdActivated";
    public static final String GDProtocolsKeyTLSv1_0 = "TLSv1";
    public static final String GDProtocolsKeyTLSv1_1 = "TLSv1.1";
    public static final String GDProtocolsKeyTLSv1_2 = "TLSv1.2";
    private static GDAndroid _instance;
    private GDAndroidImpl impl;

    private GDAndroid() {
        this.impl = null;
        this.impl = GDAndroidImpl.getInstance();
    }

    public static void executeBlock(String str, String str2, String str3) {
        GDAndroidImpl.executeBlock(str, str2, str3);
    }

    public static boolean executePendingConsoleMigration(String str, String str2) {
        return GDAndroidImpl.executePendingConsoleMigration(str, str2);
    }

    public static void executeRemoteLock() {
        GDAndroidImpl.executeRemoteLock();
    }

    public static void executeUnblock(String str) {
        GDAndroidImpl.executeUnblock(str);
    }

    public static synchronized GDAndroid getInstance() {
        GDAndroid gDAndroid;
        synchronized (GDAndroid.class) {
            if (_instance == null) {
                _instance = new GDAndroid();
            }
            gDAndroid = _instance;
        }
        return gDAndroid;
    }

    public static String getVersion() {
        return GDAndroidImpl.getVersion();
    }

    public void activityInit(android.app.Activity activity) throws GDInitializationError {
        this.impl.activityInit(activity);
    }

    public void applicationInit(Context context) throws GDInitializationError {
        this.impl.applicationInit(context);
    }

    public void authorize(GDAppEventListener gDAppEventListener) throws GDInitializationError {
        this.impl.authorize(gDAppEventListener);
    }

    public boolean canAuthorizeAutonomously(Context context) {
        return this.impl.canAuthorizeAutonomously(context);
    }

    public void configureUI(Drawable drawable, Drawable drawable2, Integer num) {
        this.impl.configureUI(drawable, drawable2, num);
    }

    @Deprecated
    public void configureUIWithBlockedMessage(String str) {
        this.impl.configureUIWithBlockedMessage(str);
    }

    @Deprecated
    public void configureUIWithWipedMessage(String str) {
        this.impl.configureUIWithWipedMessage(str);
    }

    public String getAppVersion() throws GDInitializationError {
        return this.impl.getAppVersion();
    }

    public Map<String, Object> getApplicationConfig() throws GDNotAuthorizedError {
        return this.impl.getApplicationConfig();
    }

    public String getApplicationId() throws GDInitializationError {
        return this.impl.getApplicationId();
    }

    public Map<String, Object> getApplicationPolicy() throws GDNotAuthorizedError {
        return this.impl.getApplicationPolicy();
    }

    public String getApplicationPolicyString() throws GDNotAuthorizedError {
        return this.impl.getApplicationPolicyString();
    }

    public int getEntitlementVersions(String str, GDEntitlementVersionsRequestCallback gDEntitlementVersionsRequestCallback) {
        return this.impl.getEntitlementVersions(str, gDEntitlementVersionsRequestCallback);
    }

    public SharedPreferences getGDSharedPreferences(String str, int i) {
        return this.impl.getGDSharedPreferences(str, i);
    }

    public Vector<GDServiceProvider> getServiceProviders() throws GDNotAuthorizedError {
        return this.impl.getServiceProviders();
    }

    public Vector<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError {
        return this.impl.getServiceProvidersFor(str, str2, gDServiceType);
    }

    public boolean isActivated(Context context) {
        return this.impl.isActivated(context);
    }

    public boolean openChangePasswordUI() throws GDNotAuthorizedError {
        return this.impl.openChangePasswordUI();
    }

    public boolean openFingerprintSettingsUI() throws GDNotAuthorizedError {
        return this.impl.openFingerprintSettingsUI();
    }

    public boolean openSafeWiFiSetupUI() throws GDNotAuthorizedError {
        return this.impl.openSafeWiFiSetupUI();
    }

    public void programmaticActivityInit(android.app.Activity activity, Bundle bundle) {
        this.impl.programmaticActivityInit(activity, bundle);
    }

    public void programmaticServiceInit(Context context, Bundle bundle) {
        this.impl.programmaticServiceInit(context, bundle);
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.impl.registerReceiver(broadcastReceiver, intentFilter);
    }

    public boolean serviceInit(Context context) throws GDInitializationError {
        return this.impl.serviceInit(context);
    }

    public void setGDAppEventListener(GDAppEventListener gDAppEventListener) {
        this.impl.setGDAppEventListener(gDAppEventListener);
    }

    public void setGDStateListener(GDStateListener gDStateListener) {
        this.impl.setGDStateListener(gDStateListener);
    }

    public boolean supportsFingerprintAuthentication() throws GDNotAuthorizedError {
        return this.impl.supportsFingerprintAuthentication();
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.impl.unregisterReceiver(broadcastReceiver);
    }

    @Deprecated
    public Vector<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceProviderType gDServiceProviderType) throws GDNotAuthorizedError {
        return this.impl.getServiceProvidersFor(str, str2, gDServiceProviderType);
    }

    @Deprecated
    public void programmaticActivityInit(android.app.Activity activity, String str, String str2) {
        this.impl.programmaticActivityInit(activity, str, str2);
    }

    @Deprecated
    public void programmaticActivityInit(android.app.Activity activity, String str, String str2, URL url) {
        this.impl.programmaticActivityInit(activity, str, str2, url);
    }
}
