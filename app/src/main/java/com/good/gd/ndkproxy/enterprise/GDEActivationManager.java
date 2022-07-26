package com.good.gd.ndkproxy.enterprise;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class GDEActivationManager {
    private static GDEActivationManager _instance;
    private Set<String> _activationDelegationBlackList;
    private boolean isManualActivation = false;
    private DelegateListChangeListener listChangeListener;

    /* loaded from: classes.dex */
    public static class Application {
        private Object[] _address;
        private String _entName;
        private int _entUserNumber;
        private String _id;
        private String _name;

        public Application(String str, String str2, Object[] objArr, int i, String str3) {
            this._id = str;
            this._name = str2;
            this._address = objArr;
            this._entUserNumber = i;
            this._entName = str3;
        }

        public Object[] getAddress() {
            return this._address;
        }

        public String getEnterpriseName() {
            return this._entName;
        }

        public int getEnterpriseUserNumber() {
            return this._entUserNumber;
        }

        public String getId() {
            return this._id;
        }

        public String getName() {
            return this._name;
        }
    }

    /* loaded from: classes.dex */
    public interface DelegateListChangeListener {
        void onDelegateListChanged();
    }

    private GDEActivationManager() {
        this._activationDelegationBlackList = null;
        this._activationDelegationBlackList = new HashSet();
        try {
            GDLog.DBGPRINTF(16, "GDEActivationManager: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLock) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDEActivationManager: Cannot initialize C++ peer", e);
        }
    }

    private native String generateNonceN();

    private native Object[][] getActivationInfoN();

    private native String getAppAddressN();

    public static synchronized GDEActivationManager getInstance() {
        GDEActivationManager gDEActivationManager;
        synchronized (GDEActivationManager.class) {
            if (_instance == null) {
                _instance = new GDEActivationManager();
            }
            gDEActivationManager = _instance;
        }
        return gDEActivationManager;
    }

    private native void ndkInit();

    public void addToActivationDelegationBlackList(String str) {
        this._activationDelegationBlackList.add(str);
    }

    public String generateNonce() {
        return generateNonceN();
    }

    public List<List<Application>> getActivationInfo() {
        ArrayList arrayList = new ArrayList();
        Object[][] activationInfoN = getActivationInfoN();
        if (activationInfoN == null) {
            return arrayList;
        }
        for (Object[] objArr : activationInfoN) {
            if (objArr.length != 0) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : objArr) {
                    arrayList2.add((Application) obj);
                }
                arrayList.add(arrayList2);
            }
        }
        return arrayList;
    }

    public String getAppAddress() {
        return getAppAddressN();
    }

    public boolean isManualActivation() {
        return this.isManualActivation;
    }

    public native boolean isProcessingActDelegation();

    public void notifyListChanged() {
        DelegateListChangeListener delegateListChangeListener = this.listChangeListener;
        if (delegateListChangeListener != null) {
            delegateListChangeListener.onDelegateListChanged();
        }
    }

    public void setActivationTypeManual(boolean z) {
        this.isManualActivation = z;
    }

    public void setListChangeListener(DelegateListChangeListener delegateListChangeListener) {
        this.listChangeListener = delegateListChangeListener;
    }

    public native void setProcessingActDelegation(boolean z);
}
