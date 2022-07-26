package com.good.gd.ndkproxy.sharedstore;

import com.good.gd.ndkproxy.icc.GDPreferentialServiceListener;
import com.good.gt.ndkproxy.GTInit;
import java.util.HashMap;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class GDSharedStoreManager {
    private static final String TAG = GDSharedStoreManager.class.getSimpleName();
    private static GDSharedStoreManager instance = null;
    public static final String kBringToFrontKey = "shouldBringToFront";
    public static final int kGDIdentitySharedStoreServiceErrorIterrupted = -20;
    public static final int kGDIdentitySharedStoreServiceErrorUnsupportedMethod = -2;
    public static final int kGDIdentitySharedStoreServiceErrorUnsupportedVersion = -19;
    public static final String kGDIdentitySharedStoreServiceMethodGetUserIdentities = "getUserIdentities";
    public static final String kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider = "getUserIdentitiesFromLocalProvider";
    public static final String kGDIdentitySharedStoreServiceName = "com.good.gdservice.private.gdr.useridentities";
    public static final String kGDIdentitySharedStoreServiceVersion = "1.0.0.0";
    public static final String kGDIdentitySharedStoreServiceVersion2 = "1.0.0.1";
    public static final String kSourceIdsKey = "sourceIdsList";

    static {
        String str = GDSharedStoreListener.TAG + GDSharedStoreClientListener.TAG + GDSharedStoreServiceError.TAG;
        initialize();
    }

    private GDSharedStoreManager() {
    }

    public static GDSharedStoreManager getInstance() {
        if (instance == null) {
            synchronized (GDSharedStoreManager.class) {
                instance = new GDSharedStoreManager();
            }
        }
        return instance;
    }

    private static native void initialize();

    public void registerService() {
        GDSharedStoreClientListener gDSharedStoreClientListener = GDSharedStoreClientListener.getInstance();
        GDSharedStoreListener gDSharedStoreListener = GDSharedStoreListener.getInstance();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("service", kGDIdentitySharedStoreServiceName);
        hashMap.put("version", kGDIdentitySharedStoreServiceVersion2);
        LinkedList linkedList = new LinkedList();
        linkedList.add(kGDIdentitySharedStoreServiceMethodGetUserIdentities);
        linkedList.add(kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider);
        hashMap.put(GDPreferentialServiceListener.kGDIdentitySharedStoreServiceKeyMethod, linkedList);
        hashMap.put(GDPreferentialServiceListener.kGDIdentitySharedStoreServiceKeyClient, gDSharedStoreClientListener);
        hashMap.put(GDPreferentialServiceListener.kGDIdentitySharedStoreServiceKeyServer, gDSharedStoreListener);
        GDPreferentialServiceListener.getInstance().registerService(hashMap);
        GTInit.setIccServerEnabled(true);
    }
}
