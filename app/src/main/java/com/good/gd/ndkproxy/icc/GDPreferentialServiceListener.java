package com.good.gd.ndkproxy.icc;

import com.good.gd.icc.GDServiceClientListener;
import com.good.gd.icc.GDServiceListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class GDPreferentialServiceListener {
    private static GDPreferentialServiceListener instance = null;
    public static final String kGDIdentitySharedStoreServiceKeyClient = "client";
    public static final String kGDIdentitySharedStoreServiceKeyMethod = "method";
    public static final String kGDIdentitySharedStoreServiceKeyServer = "server";
    public static final String kGDIdentitySharedStoreServiceKeyService = "service";
    public static final String kGDIdentitySharedStoreServiceKeyVersion = "version";
    private List<HashMap<String, Object>> registeredServices = new ArrayList();

    private GDPreferentialServiceListener() {
    }

    public static GDPreferentialServiceListener getInstance() {
        if (instance == null) {
            synchronized (GDPreferentialServiceListener.class) {
                instance = new GDPreferentialServiceListener();
            }
        }
        return instance;
    }

    private String getServiceValue(HashMap<String, Object> hashMap) {
        Object obj;
        if (hashMap == null || (obj = hashMap.get("service")) == null) {
            return null;
        }
        return obj.toString();
    }

    private boolean isMethodAvailable(String str, HashMap<String, Object> hashMap) {
        return ((List) hashMap.get(kGDIdentitySharedStoreServiceKeyMethod)).contains(str);
    }

    public GDServiceClientListener canConsume(String str) {
        if (str != null) {
            for (HashMap<String, Object> hashMap : this.registeredServices) {
                String serviceValue = getServiceValue(hashMap);
                if (serviceValue != null && str.contains(serviceValue)) {
                    return (GDServiceClientListener) hashMap.get(kGDIdentitySharedStoreServiceKeyClient);
                }
            }
            return null;
        }
        return null;
    }

    public GDServiceListener canProvide(String str, String str2, String str3) {
        if (str == null || str3 == null) {
            return null;
        }
        for (HashMap<String, Object> hashMap : this.registeredServices) {
            String serviceValue = getServiceValue(hashMap);
            if (serviceValue != null && str.equals(serviceValue) && isMethodAvailable(str3, hashMap)) {
                return (GDServiceListener) hashMap.get(kGDIdentitySharedStoreServiceKeyServer);
            }
        }
        return null;
    }

    public void registerService(HashMap<String, Object> hashMap) {
        this.registeredServices.add(hashMap);
    }

    public GDServiceListener canProvide(String str) {
        if (str != null) {
            for (HashMap<String, Object> hashMap : this.registeredServices) {
                String serviceValue = getServiceValue(hashMap);
                if (serviceValue != null && str.contains(serviceValue)) {
                    return (GDServiceListener) hashMap.get(kGDIdentitySharedStoreServiceKeyServer);
                }
            }
            return null;
        }
        return null;
    }
}
