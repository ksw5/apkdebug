package com.good.gd.ndkproxy.sharedstore;

import com.good.gd.icc.GDICCForegroundOptions;
import com.good.gd.icc.GDService;
import com.good.gd.icc.GDServiceError;
import com.good.gd.icc.GDServiceException;
import com.good.gd.icc.GDServiceListener;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class GDSharedStoreListener implements GDServiceListener {
    public static final String TAG = "GDSharedStoreListener";
    private static GDSharedStoreListener instance;

    /* loaded from: classes.dex */
    public class CertificateListHolder {
        List<HashMap<String, Object>> certificateList = new ArrayList();

        public CertificateListHolder() {
        }

        private void setCertificateList(ArrayList<HashMap<String, Object>> arrayList) {
            this.certificateList = arrayList;
        }

        public List<HashMap<String, Object>> getCertificateList() {
            return this.certificateList;
        }
    }

    private GDSharedStoreListener() {
    }

    private static native boolean fetchLocalCerts(List<?> list, CertificateListHolder certificateListHolder);

    private static native boolean fillMissingCertificates(List<?> list, CertificateListHolder certificateListHolder);

    public static GDSharedStoreListener getInstance() {
        if (instance == null) {
            synchronized (GDSharedStoreListener.class) {
                instance = new GDSharedStoreListener();
            }
        }
        return instance;
    }

    @Override // com.good.gd.icc.GDServiceListener
    public void onMessageSent(String str, String str2, String[] strArr) {
        GDLog.DBGPRINTF(16, "onMessageSent. appId: " + str + " requestID: " + str2);
    }

    @Override // com.good.gd.icc.GDServiceListener
    public void onReceiveMessage(String str, String str2, String str3, String str4, Object obj, String[] strArr, String str5) {
        Object obj2;
        List list;
        GDLog.DBGPRINTF(16, "onReceiveMessage. appId: " + str + " service: " + str5 + " version: " + str3 + " method: " + str4);
        if (!str3.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion) && !str3.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion2)) {
            GDLog.DBGPRINTF(16, str + " made a request for unsupported " + str2 + " version: " + str3);
            obj2 = new GDServiceError(-19, GDSharedStoreManager.kGDIdentitySharedStoreServiceName, null);
        } else if (!(obj instanceof HashMap) && !(obj instanceof List)) {
            GDLog.DBGPRINTF(16, str + " made a request with unexpected parameters");
            obj2 = new GDServiceError(-2, GDSharedStoreManager.kGDIdentitySharedStoreServiceName, null);
        } else {
            obj2 = null;
        }
        CertificateListHolder certificateListHolder = new CertificateListHolder();
        Boolean bool = false;
        if (obj2 == null) {
            GDLog.DBGPRINTF(16, "onReceiveMessage. No errors found. Proceeding with fillMissingCertificates.");
            if (obj instanceof HashMap) {
                HashMap hashMap = (HashMap) obj;
                bool = (Boolean) hashMap.get(GDSharedStoreManager.kBringToFrontKey);
                list = (List) hashMap.get(GDSharedStoreManager.kSourceIdsKey);
            } else if (!(obj instanceof List)) {
                list = null;
            } else {
                list = (List) obj;
            }
            char c = 65535;
            int hashCode = str4.hashCode();
            if (hashCode != -1155556099) {
                if (hashCode == 1925228405 && str4.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider)) {
                    c = 1;
                }
            } else if (str4.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentities)) {
                c = 0;
            }
            if (c != 0) {
                if (c == 1 && !fetchLocalCerts(list, certificateListHolder)) {
                    GDLog.DBGPRINTF(12, "Failed to fetch the certificates from local provider");
                }
            } else if (!fillMissingCertificates(list, certificateListHolder)) {
                GDLog.DBGPRINTF(12, "Failed to fill missing certificates");
            }
        }
        if (obj2 == null) {
            try {
                obj2 = certificateListHolder.getCertificateList();
            } catch (GDServiceException e) {
                GDLog.DBGPRINTF(12, "Failed to reply to ", str);
                e.printStackTrace();
                return;
            }
        }
        GDService.replyTo(str, obj2, bool.booleanValue() ? GDICCForegroundOptions.PreferPeerInForeground : GDICCForegroundOptions.NoForegroundPreference, null, str5);
    }

    @Override // com.good.gd.icc.GDServiceListener
    public void onReceivingAttachmentFile(String str, String str2, long j, String str3) {
        GDLog.DBGPRINTF(16, "onReceivingAttachmentFile. appId: " + str + " requestID: " + str3);
    }

    @Override // com.good.gd.icc.GDServiceListener
    public void onReceivingAttachments(String str, int i, String str2) {
        GDLog.DBGPRINTF(16, "onReceivingAttachments. appId: " + str + " requestID: " + str2);
    }
}
