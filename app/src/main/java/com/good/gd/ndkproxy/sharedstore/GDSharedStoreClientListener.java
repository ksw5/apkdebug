package com.good.gd.ndkproxy.sharedstore;

import android.util.Log;
import com.good.gd.icc.GDICCForegroundOptions;
import com.good.gd.icc.GDServiceClient;
import com.good.gd.icc.GDServiceClientListener;
import com.good.gd.icc.GDServiceError;
import com.good.gd.icc.GDServiceException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.service.GDActivityStateManager;
import com.good.gt.ndkproxy.icc.IccManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class GDSharedStoreClientListener implements GDServiceClientListener {
    public static final String TAG = "GDSharedStoreClientListener";
    private static GDSharedStoreClientListener instance;
    private List<HashMap<String, Object>> certificates = new ArrayList();
    private final Object requestResponseSync = new Object();
    private GDServiceError responseError = null;
    private boolean isSecondTryMessageSent = false;
    private final AtomicBoolean responseReceived = new AtomicBoolean(false);

    private GDSharedStoreClientListener() {
    }

    private synchronized boolean fetchCertificates(String str, List<String> list, String str2, String str3, GDSharedStoreServiceError gDSharedStoreServiceError) {
        try {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(GDSharedStoreManager.kSourceIdsKey, list);
                if (GDActivityStateManager.getInstance().inBackground()) {
                    GDLog.DBGPRINTF(16, "fetchCertificates." + str + "is currently in background. NoForegroundPreference will be used.");
                    hashMap.put(GDSharedStoreManager.kBringToFrontKey, false);
                } else {
                    GDLog.DBGPRINTF(16, "fetchCertificates." + str + "is currently NOT in background. PreferPeerInForeground will be used.");
                    hashMap.put(GDSharedStoreManager.kBringToFrontKey, true);
                }
                this.responseReceived.set(false);
                this.responseError = null;
                performRequest(str, str3, str2, hashMap);
                GDLog.DBGPRINTF(16, "fetchCertificates sendTo is performed. Waiting for reply from " + str);
                waitForResponse();
                GDServiceError gDServiceError = this.responseError;
                if (gDServiceError != null && gDServiceError.getCustomErrorCode() == -2 && !this.isSecondTryMessageSent) {
                    this.responseReceived.set(false);
                    this.responseError = null;
                    performRequest(str, GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider, str2, list);
                    GDLog.DBGPRINTF(16, "fetchCertificates sendTo is performed for the second time. Waiting for reply from " + str);
                    this.isSecondTryMessageSent = true;
                    this.responseError = null;
                    waitForResponse();
                }
                this.isSecondTryMessageSent = false;
                if (str3.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider)) {
                    saveFetchedCertificates(this.certificates, str);
                } else {
                    synchroniseCertificateStore(this.certificates, str);
                }
                GDServiceError gDServiceError2 = this.responseError;
                if (gDServiceError2 == null) {
                    return true;
                }
                if (gDServiceError2.getErrorCode().getValue() != 0) {
                    gDSharedStoreServiceError.setCode(this.responseError.getErrorCode().getValue());
                } else {
                    gDSharedStoreServiceError.setCode(this.responseError.getCustomErrorCode());
                }
                gDSharedStoreServiceError.setMessage(this.responseError.getMessage() != null ? this.responseError.getMessage() : "GDSharedStoreClientListener Response Error");
                return false;
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
                gDSharedStoreServiceError.setCode(-20);
                gDSharedStoreServiceError.setMessage(e.getMessage() != null ? e.getMessage() : "GDSharedStoreClientListener IterruptedException occured");
                return false;
            }
        } catch (GDServiceException e2) {
            Log.e(TAG, e2.getMessage());
            gDSharedStoreServiceError.setCode(e2.errorCode().getValue());
            gDSharedStoreServiceError.setMessage(e2.getMessage() != null ? e2.getMessage() : "GDSharedStoreClientListener GDServiceException occured");
            return false;
        }
    }

    private boolean fetchCertificatesForApp(String str, List<String> list, GDSharedStoreServiceError gDSharedStoreServiceError) {
        GDLog.DBGPRINTF(16, "fetchCertificatesForApp for appId: " + str + " sourceIds: " + list.toString());
        return fetchCertificates(str, list, GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion, GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentities, gDSharedStoreServiceError);
    }

    private boolean fetchCertificatesForLocalProvider(String str, List<String> list, GDSharedStoreServiceError gDSharedStoreServiceError) {
        GDLog.DBGPRINTF(16, "fetchCertificatesForLocalProvider for appId: " + str + " sourceIds: " + list.toString());
        return fetchCertificates(str, list, GDSharedStoreManager.kGDIdentitySharedStoreServiceVersion2, GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentitiesFromLocalProvider, gDSharedStoreServiceError);
    }

    public static GDSharedStoreClientListener getInstance() {
        if (instance == null) {
            synchronized (GDSharedStoreClientListener.class) {
                instance = new GDSharedStoreClientListener();
            }
        }
        return instance;
    }

    private void performRequest(String str, String str2, String str3, Object obj) throws GDServiceException {
        GDICCForegroundOptions gDICCForegroundOptions;
        GDICCForegroundOptions gDICCForegroundOptions2 = GDICCForegroundOptions.PreferPeerInForeground;
        if (!str2.equals(GDSharedStoreManager.kGDIdentitySharedStoreServiceMethodGetUserIdentities)) {
            gDICCForegroundOptions = gDICCForegroundOptions2;
        } else {
            gDICCForegroundOptions = GDICCForegroundOptions.PreferMeInForeground;
        }
        GDServiceClient.sendTo(str, GDSharedStoreManager.kGDIdentitySharedStoreServiceName, str3, str2, obj, null, gDICCForegroundOptions);
    }

    private static native boolean saveFetchedCertificates(List<HashMap<String, Object>> list, String str);

    private static native boolean synchroniseCertificateStore(List<HashMap<String, Object>> list, String str);

    private void waitForResponse() throws InterruptedException {
        GDLog.DBGPRINTF(16, TAG, "waitForResponse response received: " + this.responseReceived.get());
        synchronized (this.requestResponseSync) {
            while (true) {
                if (this.responseReceived.get()) {
                    break;
                }
                GDLog.DBGPRINTF(16, "fetchCertificatesForApp wait");
                this.requestResponseSync.wait(10000L);
                if (this.responseReceived.get()) {
                    GDLog.DBGPRINTF(16, "fetchCertificatesForApp we have got response, stop waiting.");
                    break;
                } else if (IccManager.getInstance().isForeground()) {
                    GDLog.DBGPRINTF(16, "fetchCertificatesForApp in foreground");
                    GDLog.DBGPRINTF(16, "fetchCertificatesForApp try fetch again");
                    this.responseError = new GDServiceError(-20, "Request time out.", "1 sec.");
                    break;
                } else {
                    GDLog.DBGPRINTF(16, "fetchCertificatesForApp in background");
                }
            }
        }
    }

    @Override // com.good.gd.icc.GDServiceClientListener
    public void onMessageSent(String str, String str2, String[] strArr) {
        GDLog.DBGPRINTF(16, "onMessageSent. appId: " + str + " requestID: " + str2);
    }

    @Override // com.good.gd.icc.GDServiceClientListener
    public void onReceiveMessage(String str, Object obj, String[] strArr, String str2) {
        GDLog.DBGPRINTF(16, "onReceiveMessage. Reply received from " + str);
        if (obj instanceof GDServiceError) {
            this.responseError = (GDServiceError) obj;
            GDLog.DBGPRINTF(12, str + " could not provide user identity, error: " + Long.toString(this.responseError.getErrorCode().getValue()) + ",custom error: " + Long.toString(this.responseError.getCustomErrorCode()));
        } else if (obj instanceof List) {
            this.certificates = (List) obj;
        } else {
            String str3 = "NULL";
            if (obj != null) {
                str3 = obj.getClass().toString();
            }
            GDLog.DBGPRINTF(12, str + "unexpected response from shared store. Params class name: " + str3);
        }
        synchronized (this.requestResponseSync) {
            this.responseReceived.set(true);
            this.requestResponseSync.notifyAll();
        }
    }

    @Override // com.good.gd.icc.GDServiceClientListener
    public void onReceivingAttachmentFile(String str, String str2, long j, String str3) {
        GDLog.DBGPRINTF(16, "onReceivingAttachmentFile. appId: " + str + " requestID: " + str3);
    }

    @Override // com.good.gd.icc.GDServiceClientListener
    public void onReceivingAttachments(String str, int i, String str2) {
        GDLog.DBGPRINTF(16, "onReceivingAttachments. appId: " + str + " requestID: " + str2);
    }
}
