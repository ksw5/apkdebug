package com.good.gd;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.good.gd.error.GDNotAuthorizedError;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes.dex */
public interface GDAndroidAPI {
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

    void executeLocalBlock(String str, String str2, String str3);

    void executeLocalUnblock(String str);

    Map<String, Object> getApplicationConfig() throws GDNotAuthorizedError;

    String getClientVersion();

    int getEntitlementVersions(String str, GDEntitlementVersionsRequestCallback gDEntitlementVersionsRequestCallback);

    SharedPreferences getGDSharedPreferences(String str, int i);

    Vector<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError;

    void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter);

    void unregisterReceiver(BroadcastReceiver broadcastReceiver);
}
