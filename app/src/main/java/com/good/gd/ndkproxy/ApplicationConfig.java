package com.good.gd.ndkproxy;

import com.good.gd.GDAppServer;
import com.good.gd.utils.GDNDKLibraryLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ApplicationConfig {
    private static ApplicationConfig _instance = null;
    public static final String appServersKey = "appServers";
    private Map<String, Object> appConfig = null;
    private List<GDAppServer> serverList = new ArrayList();

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private ApplicationConfig() {
    }

    private void addAppServer(String str, int i, int i2) {
        this.serverList.add(new GDAppServer(str, i, i2));
    }

    private void clearAppServerList() {
        this.serverList.clear();
    }

    public static synchronized ApplicationConfig getInstance() {
        ApplicationConfig applicationConfig;
        synchronized (ApplicationConfig.class) {
            if (_instance == null) {
                _instance = new ApplicationConfig();
            }
            applicationConfig = _instance;
        }
        return applicationConfig;
    }

    private native void setAppConfigValues();

    private void setBooleanValue(String str, boolean z) {
        this.appConfig.put(str, Boolean.valueOf(z));
    }

    private void setIntegerValue(String str, int i) {
        this.appConfig.put(str, Integer.valueOf(i));
    }

    private void setObjectMapValue(String str, Map<Object, Object> map) {
        this.appConfig.put(str, map);
    }

    private void setStringValue(String str, String str2) {
        this.appConfig.put(str, str2);
    }

    public synchronized Map<String, Object> getApplicationConfig() {
        Map<String, Object> unmodifiableMap;
        HashMap hashMap = new HashMap();
        this.appConfig = hashMap;
        hashMap.put("appServers", this.serverList);
        synchronized (NativeExecutionHandler.nativeLockApi) {
            setAppConfigValues();
        }
        unmodifiableMap = Collections.unmodifiableMap(this.appConfig);
        this.appConfig = null;
        return unmodifiableMap;
    }

    public native String getConstantValue(String str);
}
