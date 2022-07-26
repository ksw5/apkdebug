package com.good.gd.ndkproxy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.good.gd.GDServiceDetail;
import com.good.gd.GDServiceProvider;
import com.good.gd.GDServiceProviderType;
import com.good.gd.GDServiceType;
import com.good.gd.context.GDContext;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.utils.GDNDKLibraryLoader;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

/* loaded from: classes.dex */
public class ApplicationService {
    private static final String TAG = "Application Service";
    private static ApplicationService _instance = null;
    private static final String receivingActivity = ".iccreceivingactivity";
    Vector<GDServiceProvider> scvProvidersVersionMatch = null;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private ApplicationService() {
    }

    private native Object[] _getServiceProviders();

    private native Object[] _getServiceProvidersForService(String str, String str2, int i);

    private boolean checkLocalApplication(Object obj) {
        Vector<GDServiceProvider> vector;
        GDServiceProvider gDServiceProvider = (GDServiceProvider) obj;
        String address = gDServiceProvider.getAddress();
        String version = gDServiceProvider.getVersion();
        if (isAppInstalled(address)) {
            if (!doesAppVersionMatch(address, version)) {
                return true;
            }
            this.scvProvidersVersionMatch.add(gDServiceProvider);
            return true;
        }
        int lastIndexOf = address.lastIndexOf(".");
        if (lastIndexOf > 0 && receivingActivity.equals(address.substring(lastIndexOf, address.length()).toLowerCase(Locale.ENGLISH))) {
            String substring = address.substring(0, lastIndexOf);
            if (isAppInstalled(substring)) {
                if (!doesAppVersionMatch(substring, version) || (vector = this.scvProvidersVersionMatch) == null) {
                    return true;
                }
                vector.add(gDServiceProvider);
                return true;
            }
        }
        return false;
    }

    private boolean doesAppVersionMatch(String str, String str2) {
        ApplicationInfo applicationInfo;
        String string;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        PackageManager packageManager = applicationContext.getPackageManager();
        try {
            if (str != null) {
                applicationInfo = packageManager.getApplicationInfo(str, 128);
            } else {
                applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128);
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null && (string = bundle.getString("GDApplicationVersion")) != null) {
                if (string.equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private Vector<GDServiceProvider> filterVersionMatchedServiceProviders(Vector<GDServiceProvider> vector) {
        Iterator<GDServiceProvider> it = this.scvProvidersVersionMatch.iterator();
        while (it.hasNext()) {
            Iterator<GDServiceProvider> it2 = vector.iterator();
            GDServiceProvider next = it.next();
            while (it2.hasNext()) {
                if (next.getAddress().equals(it2.next().getAddress())) {
                    it2.remove();
                }
            }
            vector.add(next);
        }
        return vector;
    }

    public static synchronized ApplicationService getInstance() {
        ApplicationService applicationService;
        synchronized (ApplicationService.class) {
            if (_instance == null) {
                _instance = new ApplicationService();
            }
            applicationService = _instance;
        }
        return applicationService;
    }

    private boolean isAppInstalled(String str) {
        try {
            GDContext.getInstance().getApplicationContext().getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public Vector<GDServiceProvider> getServiceProviders() {
        boolean z;
        boolean z2;
        GDLog.DBGPRINTF(14, TAG, "getServiceProviders \n");
        Vector<GDServiceProvider> vector = new Vector<>();
        this.scvProvidersVersionMatch = new Vector<>();
        Object[] _getServiceProviders = _getServiceProviders();
        if (_getServiceProviders != null) {
            for (int i = 0; i < _getServiceProviders.length; i++) {
                if (_getServiceProviders[i] != null) {
                    GDServiceProvider gDServiceProvider = (GDServiceProvider) _getServiceProviders[i];
                    Vector<GDServiceDetail> services = gDServiceProvider.getServices();
                    if (services == null || services.size() <= 0) {
                        z = false;
                        z2 = false;
                    } else {
                        Iterator<GDServiceDetail> it = services.iterator();
                        z = false;
                        z2 = false;
                        while (it.hasNext()) {
                            GDServiceDetail next = it.next();
                            if (next.getServiceType() == GDServiceType.GD_SERVICE_TYPE_APPLICATION) {
                                z = true;
                            } else if (next.getServiceType() == GDServiceType.GD_SERVICE_TYPE_SERVER) {
                                z2 = true;
                            }
                        }
                    }
                    if (z) {
                        if (checkLocalApplication((GDServiceProvider) _getServiceProviders[i])) {
                            vector.add((GDServiceProvider) _getServiceProviders[i]);
                        } else if (z2) {
                            Iterator<GDServiceDetail> it2 = gDServiceProvider.getServices().iterator();
                            while (it2.hasNext()) {
                                if (it2.next().getServiceType() == GDServiceType.GD_SERVICE_TYPE_APPLICATION) {
                                    it2.remove();
                                }
                            }
                            vector.add((GDServiceProvider) _getServiceProviders[i]);
                        }
                    } else if (z2) {
                        vector.add((GDServiceProvider) _getServiceProviders[i]);
                    }
                }
            }
        }
        return filterVersionMatchedServiceProviders(vector);
    }

    @Deprecated
    public Vector<GDServiceProvider> getServiceProvidersForService(String str, String str2, GDServiceProviderType gDServiceProviderType) throws GDNotAuthorizedError {
        int i = 1;
        GDLog.DBGPRINTF(14, TAG, "getServiceProvidersForService ServiceProviderType=" + gDServiceProviderType + "\n");
        Vector<GDServiceProvider> vector = new Vector<>();
        this.scvProvidersVersionMatch = new Vector<>();
        if (gDServiceProviderType == GDServiceProviderType.GDSERVICEPROVIDERAPPLICATION) {
            i = 0;
        } else {
            GDServiceProviderType gDServiceProviderType2 = GDServiceProviderType.GDSERVICEPROVIDERSERVER;
        }
        if (str == null) {
            return filterVersionMatchedServiceProviders(vector);
        }
        Object[] _getServiceProvidersForService = _getServiceProvidersForService(str, str2, i);
        if (_getServiceProvidersForService != null) {
            for (int i2 = 0; i2 < _getServiceProvidersForService.length; i2++) {
                if (_getServiceProvidersForService[i2] != null && (gDServiceProviderType == GDServiceProviderType.GDSERVICEPROVIDERSERVER || checkLocalApplication((GDServiceProvider) _getServiceProvidersForService[i2]))) {
                    vector.add((GDServiceProvider) _getServiceProvidersForService[i2]);
                }
            }
        }
        return filterVersionMatchedServiceProviders(vector);
    }

    public Vector<GDServiceProvider> getServiceProvidersForService(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError {
        int i = 1;
        GDLog.DBGPRINTF(14, TAG, "getServiceProvidersForService ServiceProviderType=" + gDServiceType + "\n");
        Vector<GDServiceProvider> vector = new Vector<>();
        this.scvProvidersVersionMatch = new Vector<>();
        if (gDServiceType == GDServiceType.GD_SERVICE_TYPE_APPLICATION) {
            i = 0;
        } else {
            GDServiceType gDServiceType2 = GDServiceType.GD_SERVICE_TYPE_SERVER;
        }
        if (str == null) {
            return filterVersionMatchedServiceProviders(vector);
        }
        Object[] _getServiceProvidersForService = _getServiceProvidersForService(str, str2, i);
        if (_getServiceProvidersForService != null) {
            for (int i2 = 0; i2 < _getServiceProvidersForService.length; i2++) {
                if (_getServiceProvidersForService[i2] != null && (gDServiceType == GDServiceType.GD_SERVICE_TYPE_SERVER || checkLocalApplication((GDServiceProvider) _getServiceProvidersForService[i2]))) {
                    vector.add((GDServiceProvider) _getServiceProvidersForService[i2]);
                }
            }
        }
        return filterVersionMatchedServiceProviders(vector);
    }
}
