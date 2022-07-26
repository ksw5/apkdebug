package com.good.gd.ndkproxy.backgroundAuth;

import android.os.Handler;
import android.os.Message;
import com.good.gd.backgroundAuth.BackgroundAuthPolicyListener;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.utils.ApiPermission;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public final class GDBackgroundAuthImpl {
    private static final String BACKGROUND_AUTH_PERMISSION_KEY = "BackgroundAuthorizePermission";
    private static final String BACKGROUND_AUTH_SETTING_KEY = "GDBackgroundAuthorizePolicySetting";
    private Set<BackgroundAuthPolicyListener> listeners;
    private boolean initialized = false;
    private boolean needToCheck = false;
    private String permissionValue = null;
    private BackgroundAuthEventHandler backgroundAuthEventHandler = null;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BackgroundAuthEventHandler extends Handler {
        private final WeakReference<GDBackgroundAuthImpl> dbjc;

        public BackgroundAuthEventHandler(GDBackgroundAuthImpl gDBackgroundAuthImpl) {
            this.dbjc = new WeakReference<>(gDBackgroundAuthImpl);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GDLog.DBGPRINTF(16, "GD_BA GDAuthBackgroundAuthImpl handleMessage\n");
            GDBackgroundAuthImpl gDBackgroundAuthImpl = this.dbjc.get();
            if (gDBackgroundAuthImpl == null || gDBackgroundAuthImpl.listeners == null) {
                return;
            }
            for (BackgroundAuthPolicyListener backgroundAuthPolicyListener : gDBackgroundAuthImpl.listeners) {
                boolean z = true;
                if (message.what != 1) {
                    z = false;
                }
                backgroundAuthPolicyListener.onBackgroundAuthPolicyChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements ApiPermission.SignatureChecker {
        hbfhc(GDBackgroundAuthImpl gDBackgroundAuthImpl) {
        }

        @Override // com.good.gd.utils.ApiPermission.SignatureChecker
        public boolean checkSignature(byte[] bArr, String str) {
            if (GDBackgroundAuthImpl.native_checkSignature(bArr, str)) {
                GDLog.DBGPRINTF(16, "GD_BA BackgroundAuth: Permission signnature was validated \n");
                return true;
            }
            GDLog.DBGPRINTF(16, "GD_BA BackgroundAuth: Permission signnature was NOT validated \n");
            return false;
        }
    }

    public GDBackgroundAuthImpl() {
        this.listeners = null;
        this.listeners = new HashSet();
        init();
    }

    private void checkBackgroundAuthAbility() {
        GDLog.DBGPRINTF(16, "GD_BA GDBackgroundAuthImpl: checkBackgroundAuthAbility()\n");
        if (getBackgroundAuthFromSettings()) {
            this.needToCheck = true;
            this.backgroundAuthEventHandler = new BackgroundAuthEventHandler(this);
            ndkInit(this.permissionValue);
        }
    }

    private boolean getBackgroundAuthFromSettings() {
        String checkSettingsFileForKey = GDSettings.checkSettingsFileForKey(BACKGROUND_AUTH_SETTING_KEY, false);
        if (checkSettingsFileForKey == null) {
            GDLog.DBGPRINTF(16, "GD_BA GDBackgroundAuthPolicySetting not enabled \n");
            return false;
        }
        this.permissionValue = checkSettingsFileForKey;
        if (!GDClient.getInstance().isInitialized()) {
            GDClient.getInstance().initialize();
        }
        String applicationId = GDClient.getInstance().getApplicationId();
        String lowerCase = GDContext.getInstance().getApplicationContext().getPackageName().toLowerCase(Locale.ENGLISH);
        GDLog.DBGPRINTF(16, "GD_BA GDBackgroundAuthImpl: Using application Id <" + applicationId + "> native id <" + lowerCase + ">\n");
        ApiPermission[] fromSettings = ApiPermission.getFromSettings();
        if (fromSettings == null) {
            return false;
        }
        for (ApiPermission apiPermission : fromSettings) {
            if (apiPermission.checkPermission(applicationId, lowerCase, BACKGROUND_AUTH_PERMISSION_KEY) && apiPermission.checkSignature(new hbfhc(this))) {
                GDLog.DBGPRINTF(16, "GD_BA GDBackgroundAuthImpl: BackgroundAuth permission validated\n");
                return true;
            }
        }
        GDLog.DBGPRINTF(16, "GD_BA BackgroundAuth: No BackgroundAuth Permission found \n");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_checkSignature(byte[] bArr, String str);

    private native boolean native_isBackgroundAuthAllowed();

    private native void native_updatePolicyChecker();

    private native void ndkInit(String str);

    private synchronized void onBackgroundAuthPolicyChanged(boolean z) {
        if (this.backgroundAuthEventHandler != null) {
            GDLog.DBGPRINTF(16, "GD_BA BackgroundAuthEventHandler onBackgroundAuthPolicyChanged enabled = " + z + "\n");
            this.backgroundAuthEventHandler.sendEmptyMessage(z ? 1 : 0);
        }
    }

    public synchronized void addBackgroundAuthPolicyListener(BackgroundAuthPolicyListener backgroundAuthPolicyListener) {
        this.listeners.add(backgroundAuthPolicyListener);
    }

    public void init() {
        if (!this.initialized) {
            checkBackgroundAuthAbility();
            this.initialized = true;
        }
    }

    public synchronized boolean isBackgroundAuthAllowed() {
        GDLog.DBGPRINTF(16, "GD_BA GDAuthBackgroundAuthImpl isBackgroundAuthAllowed()\n");
        return native_isBackgroundAuthAllowed();
    }

    public synchronized void removeBackgroundAuthPolicyListener(BackgroundAuthPolicyListener backgroundAuthPolicyListener) {
        this.listeners.remove(backgroundAuthPolicyListener);
    }
}
