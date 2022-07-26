package com.good.gd.ndkproxy.trust;

import android.graphics.Bitmap;
import com.good.gd.GDTrust;
import com.good.gd.GDTrustAction;
import com.good.gd.GDTrustListener;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.ndkproxy.enterprise.GDEActivationManager;
import com.good.gd.ndkproxy.icc.GDTrustEActivationManager;
import com.good.gd.utils.ApiPermission;
import com.good.gd.utils.GDLocalizer;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDTrustImpl extends GDTrust {
    private boolean isEasyActivation = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc implements ApiPermission.SignatureChecker {
        hbfhc() {
        }

        @Override // com.good.gd.utils.ApiPermission.SignatureChecker
        public boolean checkSignature(byte[] bArr, String str) {
            return GDTrustImpl.native_checkSignature(bArr, str);
        }
    }

    public static void checkTrustDelegate(GDTrustListener gDTrustListener) {
        native_setTrustDelegate(gDTrustListener, hasApiPermission());
    }

    private static boolean hasApiPermission() {
        GDLog.DBGPRINTF(16, "GDTrustImpl: hasApiPermission() IN");
        JSONObject mainSettings = GDSettings.getMainSettings();
        if (mainSettings == null) {
            return false;
        }
        try {
            if (!mainSettings.getBoolean("GDTrustedAuthenticator")) {
                return false;
            }
            String applicationId = GDClient.getInstance().getApplicationId();
            String lowerCase = GDContext.getInstance().getApplicationContext().getPackageName().toLowerCase(Locale.ENGLISH);
            GDLog.DBGPRINTF(16, "GDTrustImpl: Using application Id <" + applicationId + "> native id <" + lowerCase + ">");
            ApiPermission[] fromSettings = ApiPermission.getFromSettings();
            if (fromSettings == null) {
                return false;
            }
            for (ApiPermission apiPermission : fromSettings) {
                if (apiPermission.checkPermission(applicationId, lowerCase, "TAAPIPermission") && apiPermission.checkSignature(new hbfhc())) {
                    GDLog.DBGPRINTF(16, "GDTrustImpl: TAAPIPermission validated");
                    return true;
                }
            }
            GDLog.DBGPRINTF(12, "GDTrustImpl: No TAAPIPermission found");
            return false;
        } catch (JSONException e) {
            GDLog.DBGPRINTF(12, "GDTrustImpl: Missing GDTrustedAuthenticator tag");
            return false;
        }
    }

    private native int native_changePassword(byte[] bArr, byte[] bArr2);

    public static native boolean native_checkSignature(byte[] bArr, String str);

    public static native String native_getStartupData();

    private native int native_performTrustAction(int i);

    private native Map native_securityPolicy();

    public static native void native_setStartupData(String str);

    private static native void native_setTrustDelegate(GDTrustListener gDTrustListener, boolean z);

    private native int native_unlockWithPassword(byte[] bArr);

    private void onInitReauthentication(GDTrustListener gDTrustListener, String str, String str2, long j, int i) {
        GDLog.DBGPRINTF(16, "GDTrustImpl: onInitReauthentication()\n");
        Bitmap thisApplicationIcon = GDTrustEActivationManager.getInstance().getThisApplicationIcon();
        String str3 = str + " - " + str2;
        Date date = j != 0 ? new Date(j) : null;
        if (i <= 0) {
            i = -1;
        }
        gDTrustListener.authenticateWithTrustWarn(this, str3, thisApplicationIcon, date, i);
    }

    private void onInitiateAuthentication(GDTrustListener gDTrustListener) {
        if (!GDEActivationManager.getInstance().isProcessingActDelegation()) {
            this.isEasyActivation = false;
            gDTrustListener.authenticateWithTrust(this);
        }
    }

    private void onInitiateEasyActivation(GDTrustListener gDTrustListener) {
        GDLog.DBGPRINTF(12, "GDTrustImpl: onInitiateEasyActivation()");
        this.isEasyActivation = true;
        String format = String.format("%s " + GDLocalizer.getLocalizedString("is requesting setup."), GDTrustEActivationManager.getInstance().getApplicationName());
        Bitmap applicationIcon = GDTrustEActivationManager.getInstance().getApplicationIcon();
        GDTrustEActivationManager.getInstance().closeInternalActivity();
        gDTrustListener.authenticateWithTrustWarn(this, format, applicationIcon);
    }

    private void onReceivedSecurityPolicy(GDTrustListener gDTrustListener) {
        gDTrustListener.securityPolicyDidChange(this);
    }

    @Override // com.good.gd.GDTrust
    public int changePassword(byte[] bArr, byte[] bArr2) {
        return native_changePassword(bArr, bArr2);
    }

    @Override // com.good.gd.GDTrust
    public int performTrustAction(GDTrustAction gDTrustAction) {
        GDLog.DBGPRINTF(16, "GDTrustImpl: performTrustAction(" + gDTrustAction + ")\n");
        int native_performTrustAction = native_performTrustAction(gDTrustAction.ordinal());
        if (gDTrustAction == GDTrustAction.GDTrustActionRejectWarning && native_performTrustAction == 0) {
            GDLog.DBGPRINTF(16, "GDTrustImpl: performTrustAction(" + gDTrustAction + ") was successful for reject with warning\n");
            if (this.isEasyActivation) {
                GDTrustEActivationManager.getInstance().moveTaskToBack();
            }
            this.isEasyActivation = false;
        }
        return native_performTrustAction;
    }

    @Override // com.good.gd.GDTrust
    public Map securityPolicy() {
        return native_securityPolicy();
    }

    @Override // com.good.gd.GDTrust
    public int unlockWithPassword(byte[] bArr) {
        return native_unlockWithPassword(bArr);
    }
}
