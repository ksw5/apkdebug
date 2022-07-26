package com.good.gd.mam;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.good.gd.GDMamWrapper;
import com.good.gd.GDResult;
import com.good.gd.GDVersion;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.mam.GDMobileApplicationManagementImpl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDNDKLibraryLoader;
import com.good.gt.context.GTBaseContext;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public class GDMobileApplicationManagement {
    public static final int GDCatalogErrorInstallerDownloadFailed = -4;
    public static final int GDCatalogErrorInstallerInstallFail = -3;
    public static final int GDCatalogErrorInstallerTypeInvalid = -1;
    public static final int GDCatalogErrorInstallerTypeNull = -2;
    public static final int GDCatalogMinimumAllowedCacheFreshnessMinutes = 5;
    public static String GDCatalogPushedApplicationDetails = "mamPushedDetails";
    public static String GDCatalogRequestedIcons = "mamRequestedIcons";
    private static GDMobileApplicationManagementListener m_listener;
    private static boolean s_applicationDetailsPushOn;
    private static List<Integer> m_internalRequests = new ArrayList();
    private static List<Integer> m_getEntitledVersionsRequests = new ArrayList();

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private GDMobileApplicationManagement() {
    }

    private static void checkEntitlementId(String str) {
        if (str != null) {
            if (str.trim().length() == 0) {
                throw new IllegalArgumentException("The argument 'entitlementId' cannot be empty");
            }
            return;
        }
        throw new IllegalArgumentException("The argument 'entitlementId' cannot be null");
    }

    private static void checkPreConditions() {
        if (m_listener != null) {
            GDContext.getInstance().checkAuthorized();
            return;
        }
        throw new RuntimeException("GDMobileApplicationManagementListener must be set");
    }

    private static void checkResource(String str) {
        if (str != null) {
            if (str.trim().length() == 0) {
                throw new IllegalArgumentException("The argument 'resource' cannot be empty");
            }
            return;
        }
        throw new IllegalArgumentException("The argument 'resource' cannot be null");
    }

    private static void checkVersion(String str) {
        if (str != null) {
            if (str.trim().length() != 0) {
                new GDVersion(str);
                return;
            }
            throw new IllegalArgumentException("The argument 'version' cannot be empty");
        }
        throw new IllegalArgumentException("The argument 'version' cannot be null");
    }

    public static ByteBuffer clone(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            ByteBuffer allocate = ByteBuffer.allocate(byteBuffer.capacity());
            byteBuffer.rewind();
            allocate.put(byteBuffer);
            byteBuffer.rewind();
            allocate.flip();
            return allocate;
        }
        return null;
    }

    public static synchronized void configure(Map<String, String> map) {
        synchronized (GDMobileApplicationManagement.class) {
            if (GDClient.getInstance().isAuthorized()) {
                ErrorUtils.throwGDErrorForProgrammingError("GDMobileApplicationManagement.configure must be called before authorization");
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null) {
                    if (key.equals(GDCatalogRequestedIcons)) {
                        Pattern compile = Pattern.compile("^(\\d+x\\d+)(:\\d+x\\d+)*$");
                        if (value == null || !compile.matcher(value).matches()) {
                            throw new IllegalArgumentException("Invalid image size string, must be in form '180x180' or '57x57:180x180:1024x1024' - was: " + value);
                        }
                    } else if (!key.equals(GDCatalogPushedApplicationDetails)) {
                        throw new IllegalArgumentException("Invalid option for configure: " + key);
                    }
                } else {
                    throw new NullPointerException("Null keys are not allowed");
                }
            }
            setSettings(map);
        }
    }

    public static int getAppResource(String str, int i) {
        checkPreConditions();
        checkResource(str);
        return GDMobileApplicationManagementImpl.getCtpResponse(7, str, null, updateMinimumRequestedFreshness(i));
    }

    public static int getApplicationDetails(String str, String str2) {
        if (!s_applicationDetailsPushOn) {
            ErrorUtils.throwGDErrorForProgrammingError("GDMobileApplicationManagement.getApplicationDetails - can only be called if GDCatalogPushedApplicationDetails is enabled via 'configure'");
        }
        int cTPHandlerMode = GDMobileApplicationManagementImpl.getCTPHandlerMode();
        if (cTPHandlerMode != 0) {
            GDLog.DBGPRINTF(12, "getApplicationDetails CTP method is not supported in CTP mode: " + cTPHandlerMode + "\n");
            return -1;
        }
        checkPreConditions();
        checkEntitlementId(str);
        checkVersion(str2);
        return GDMobileApplicationManagementImpl.getCtpResponse(1, (("method=getApplicationDetails&entitlementIdentifier=" + str) + "&version=" + str2) + "&apiLevel=" + Build.VERSION.SDK_INT, null, Integer.MAX_VALUE);
    }

    public static int getApplicationInstallerDetails(String str, String str2, int i) {
        return getApplicationInstallerDetails_internal(str, str2, updateMinimumRequestedFreshness(i));
    }

    private static int getApplicationInstallerDetails_internal(String str, String str2, int i) {
        int cTPHandlerMode = GDMobileApplicationManagementImpl.getCTPHandlerMode();
        if (cTPHandlerMode != 0) {
            GDLog.DBGPRINTF(12, "getApplicationInstallerDetails CTP method is not supported in CTP mode: " + cTPHandlerMode + "\n");
            return -1;
        }
        checkPreConditions();
        checkEntitlementId(str);
        checkVersion(str2);
        return GDMobileApplicationManagementImpl.getCtpResponse(4, (("method=getApplicationInstallerDetails&entitlementIdentifier=" + str) + "&version=" + str2) + "&apiLevel=" + Build.VERSION.SDK_INT, null, i);
    }

    public static int getEntitlements(int i) {
        int ctpResponse = GDMobileApplicationManagementImpl.getCtpResponse(0, ("method=getEntitlements&pLocale=" + Uri.encode(Locale.getDefault().toString())) + "&apiLevel=" + Build.VERSION.SDK_INT, null, i);
        if (ctpResponse >= 0) {
            m_getEntitledVersionsRequests.add(Integer.valueOf(ctpResponse));
        }
        return ctpResponse;
    }

    public static synchronized int getInstallableApplications() {
        int entitlements;
        synchronized (GDMobileApplicationManagement.class) {
            checkPreConditions();
            entitlements = getEntitlements(Integer.MAX_VALUE);
            if (entitlements >= 0) {
                m_internalRequests.add(Integer.valueOf(entitlements));
            }
        }
        return entitlements;
    }

    public static synchronized int installApplication(String str, String str2, Context context) {
        int applicationInstallerDetails_internal;
        synchronized (GDMobileApplicationManagement.class) {
            checkPreConditions();
            checkEntitlementId(str);
            checkVersion(str2);
            if (GDContext.getInstance().getApplicationContext().checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                if (context.getApplicationInfo().targetSdkVersion >= 24 && !GDMobileApplicationInstaller.checkMAMSupportIsInstalled(context)) {
                    throw new RuntimeException("MAM FileProvider is missing. Please check if GDLibrary_MamSupport is added");
                }
                applicationInstallerDetails_internal = getApplicationInstallerDetails_internal(str, str2, 0);
                if (applicationInstallerDetails_internal >= 0) {
                    m_internalRequests.add(Integer.valueOf(applicationInstallerDetails_internal));
                }
            } else {
                throw new RuntimeException("The android.permission.WRITE_EXTERNAL_STORAGE permission is needed to install");
            }
        }
        return applicationInstallerDetails_internal;
    }

    public static synchronized void onCtpResourceInternal(int i, int i2, int i3, String str, ByteBuffer byteBuffer, int i4) {
        synchronized (GDMobileApplicationManagement.class) {
            ByteBuffer clone = clone(byteBuffer);
            GDResult gDResult = new GDResult(i2, i3, str);
            GDMobileApplicationManagementListener gDMobileApplicationManagementListener = m_listener;
            if (gDMobileApplicationManagementListener != null) {
                gDMobileApplicationManagementListener.onReceivedApplicationResource(i, gDResult, clone);
            }
        }
    }

    public static synchronized void onCtpResponseInternal(int i, int i2, int i3, int i4, String str, String str2) {
        List list;
        List list2;
        synchronized (GDMobileApplicationManagement.class) {
            GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::onCtpResponse: type:" + i + " requestId:" + i2 + " status: " + i4 + "\n");
            GDResult gDResult = new GDResult(i3, i4, str);
            List<GDEntitlement> list3 = null;
            r4 = null;
            GDCatalogApplicationInstallerDetails gDCatalogApplicationInstallerDetails = null;
            r4 = null;
            GDCatalogApplicationDetails gDCatalogApplicationDetails = null;
            if (i == 0) {
                if (i3 == 0) {
                    list3 = GDMAMParser.handle(str2, GDEntitlement.class);
                }
                if (m_internalRequests.contains(Integer.valueOf(i2))) {
                    m_internalRequests.remove(Integer.valueOf(i2));
                    GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::onCtpResponse - handling internal requestId: " + i2 + "\n");
                    List<GDEntitlement> filterByApplications = GDEntitlement.filterByApplications(list3);
                    GDMobileApplicationManagementListener gDMobileApplicationManagementListener = m_listener;
                    if (gDMobileApplicationManagementListener != null) {
                        gDMobileApplicationManagementListener.onInstallableApplicationsResponse(i2, gDResult, filterByApplications);
                    }
                } else if (m_getEntitledVersionsRequests.contains(Integer.valueOf(i2))) {
                    m_getEntitledVersionsRequests.remove(Integer.valueOf(i2));
                    GDMamWrapper.getInstance().onGetEntitlementVersionsResponse(i2, i4, list3);
                } else {
                    GDLog.DBGPRINTF(12, "GDMobileApplicationManagement::onCtpResponse - unmatched requestId: " + i2 + "\n");
                    GDMobileApplicationManagementListener gDMobileApplicationManagementListener2 = m_listener;
                    if (gDMobileApplicationManagementListener2 != null) {
                        gDMobileApplicationManagementListener2.onInstallableApplicationsResponse(i2, gDResult, list3);
                    }
                }
            } else if (i == 1) {
                if (i3 != 0) {
                    list = null;
                } else {
                    list = GDMAMParser.handle(str2, GDCatalogApplicationDetails.class);
                }
                if (list != null && list.size() > 0) {
                    gDCatalogApplicationDetails = (GDCatalogApplicationDetails) list.get(0);
                }
                GDMobileApplicationManagementListener gDMobileApplicationManagementListener3 = m_listener;
                if (gDMobileApplicationManagementListener3 != null) {
                    gDMobileApplicationManagementListener3.onApplicationDetailsResponse(i2, gDResult, gDCatalogApplicationDetails);
                }
            } else if (i != 4) {
                GDLog.DBGPRINTF(12, "GDMobileApplicationManagement::onCtpResponse illegal type: " + i + "\n");
            } else {
                if (i3 != 0) {
                    list2 = null;
                } else {
                    list2 = GDMAMParser.handle(str2, GDCatalogApplicationInstallerDetails.class);
                }
                if (list2 != null && list2.size() > 0) {
                    gDCatalogApplicationInstallerDetails = (GDCatalogApplicationInstallerDetails) list2.get(0);
                }
                if (m_internalRequests.contains(Integer.valueOf(i2))) {
                    m_internalRequests.remove(Integer.valueOf(i2));
                    GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::onCtpResponse - download URL - handling internal request\n");
                    if (i3 == 0) {
                        installApplication(i2, gDCatalogApplicationInstallerDetails);
                    } else {
                        GDMobileApplicationManagementListener gDMobileApplicationManagementListener4 = m_listener;
                        if (gDMobileApplicationManagementListener4 != null) {
                            gDMobileApplicationManagementListener4.onApplicationDispatchedForInstallation(i2, new GDResult(i3, i4, str));
                        }
                    }
                } else {
                    GDMobileApplicationManagementListener gDMobileApplicationManagementListener5 = m_listener;
                    if (gDMobileApplicationManagementListener5 != null) {
                        gDMobileApplicationManagementListener5.onApplicationInstallerDetailsResponse(i2, gDResult, gDCatalogApplicationInstallerDetails);
                    }
                }
            }
        }
    }

    public static void setListener(GDMobileApplicationManagementListener gDMobileApplicationManagementListener) {
        m_listener = gDMobileApplicationManagementListener;
    }

    private static synchronized void setSettings(Map<String, String> map) {
        synchronized (GDMobileApplicationManagement.class) {
            GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::configure_internal\n");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                if (entry.getKey().equals(GDCatalogPushedApplicationDetails) && value.equals(DebugKt.DEBUG_PROPERTY_VALUE_ON)) {
                    s_applicationDetailsPushOn = true;
                }
                GDMobileApplicationManagementImpl.setSetting(entry.getKey(), entry.getValue());
            }
        }
    }

    private static int updateMinimumRequestedFreshness(int i) {
        int minimumRequestedFreshness = GDMobileApplicationManagementImpl.getMinimumRequestedFreshness();
        return i < minimumRequestedFreshness ? minimumRequestedFreshness : i;
    }

    private static void installApplication(int i, GDCatalogApplicationInstallerDetails gDCatalogApplicationInstallerDetails) {
        if (gDCatalogApplicationInstallerDetails != null) {
            if (gDCatalogApplicationInstallerDetails.getType().ordinal() != 1) {
                GDLog.DBGPRINTF(12, "GDMobileApplicationManagement::installApplication: unable to install application (invalid type)\n");
                GDMobileApplicationManagementListener gDMobileApplicationManagementListener = m_listener;
                if (gDMobileApplicationManagementListener == null) {
                    return;
                }
                gDMobileApplicationManagementListener.onApplicationDispatchedForInstallation(i, new GDResult(100, -1, "unable to install application (invalid type)"));
                return;
            }
            String nativeApplicationDownloadUrl = gDCatalogApplicationInstallerDetails.getNativeApplicationDownloadUrl();
            Uri parse = Uri.parse(nativeApplicationDownloadUrl);
            GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::installApplication: uri: " + parse + "\n");
            if (parse.getScheme().equalsIgnoreCase("market")) {
                Intent intent = new Intent("android.intent.action.VIEW", parse);
                intent.setFlags(67108864);
                intent.setFlags(524288);
                intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                try {
                    try {
                        GTBaseContext.getInstance().getApplicationContext().startActivity(intent);
                        GDMobileApplicationManagementListener gDMobileApplicationManagementListener2 = m_listener;
                        if (gDMobileApplicationManagementListener2 == null) {
                            return;
                        }
                        gDMobileApplicationManagementListener2.onApplicationDispatchedForInstallation(i, new GDResult(0));
                        return;
                    } catch (ActivityNotFoundException e) {
                        GDLog.DBGPRINTF(12, "GDMobileApplicationManagement::installApplication: unable to find activity\n");
                        e.printStackTrace();
                        GDMobileApplicationManagementListener gDMobileApplicationManagementListener3 = m_listener;
                        if (gDMobileApplicationManagementListener3 == null) {
                            return;
                        }
                        gDMobileApplicationManagementListener3.onApplicationDispatchedForInstallation(i, new GDResult(0));
                        return;
                    }
                } catch (Throwable th) {
                    GDMobileApplicationManagementListener gDMobileApplicationManagementListener4 = m_listener;
                    if (gDMobileApplicationManagementListener4 != null) {
                        gDMobileApplicationManagementListener4.onApplicationDispatchedForInstallation(i, new GDResult(0));
                    }
                    throw th;
                }
            }
            GDLog.DBGPRINTF(16, "GDMobileApplicationManagement::installApplication: downloading apk file\n");
            GDMobileApplicationInstaller.getInstance().install(i, nativeApplicationDownloadUrl, m_listener);
            return;
        }
        GDLog.DBGPRINTF(12, "GDMobileApplicationManagement::installApplication: unable to install application (null)\n");
        GDMobileApplicationManagementListener gDMobileApplicationManagementListener5 = m_listener;
        if (gDMobileApplicationManagementListener5 == null) {
            return;
        }
        gDMobileApplicationManagementListener5.onApplicationDispatchedForInstallation(i, new GDResult(100, -2, "unable to install application (null)"));
    }
}
