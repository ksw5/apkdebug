package com.good.gd.ndkproxy.bypass;

import android.os.Handler;
import android.os.Message;
import com.good.gd.GDStateListener;
import com.good.gd.bypass.BypassPolicyListener;
import com.good.gd.bypass.GDBypassAbility;
import com.good.gd.client.GDClient;
import com.good.gd.client.GDDefaultAppEventListener;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import com.good.gd.utils.ApiPermission;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class GDBypassAbilityImpl implements GDBypassAbility {
    private static final String BYPASS_UNLOCK_PERMISSION_KEY = "BypassUnlockPermission";
    private static final String BYPASS_UNLOCK_SETTING_KEY = "GDBypassUnlockPolicySetting";
    private Set<BypassPolicyListener> listeners;
    private GDBypassStateListenerImpl stateListener;
    private BypassEventHandler bypassEventHandler = null;
    private boolean initialized = false;
    private boolean needToCheck = false;
    private HashSet<String> activityNames = null;
    private String permissionValue = null;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class BypassEventHandler extends Handler {
        private final WeakReference<GDBypassAbilityImpl> dbjc;

        public BypassEventHandler(GDBypassAbilityImpl gDBypassAbilityImpl) {
            this.dbjc = new WeakReference<>(gDBypassAbilityImpl);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GDLog.DBGPRINTF(16, "GDBypassAbilityImpl handleMessage\n");
            GDBypassAbilityImpl gDBypassAbilityImpl = this.dbjc.get();
            if (gDBypassAbilityImpl == null || gDBypassAbilityImpl.listeners == null) {
                return;
            }
            for (BypassPolicyListener bypassPolicyListener : gDBypassAbilityImpl.listeners) {
                boolean z = true;
                if (message.what != 1) {
                    z = false;
                }
                bypassPolicyListener.onBypassPolicyChanged(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class GDBypassStateListenerImpl implements GDStateListener {
        private GDBypassStateListenerImpl() {
        }

        @Override // com.good.gd.GDStateListener
        public void onAuthorized() {
            if (GDBypassAbilityImpl.this.needToCheck) {
                GDBypassAbilityImpl.this.needToCheck = false;
                GDBypassAbilityImpl.this.native_updatePolicyChecker();
            }
        }

        @Override // com.good.gd.GDStateListener
        public void onLocked() {
        }

        @Override // com.good.gd.GDStateListener
        public void onUpdateConfig(Map<String, Object> map) {
        }

        @Override // com.good.gd.GDStateListener
        public void onUpdateEntitlements() {
        }

        @Override // com.good.gd.GDStateListener
        public void onUpdatePolicy(Map<String, Object> map) {
        }

        @Override // com.good.gd.GDStateListener
        public void onUpdateServices() {
        }

        @Override // com.good.gd.GDStateListener
        public void onWiped() {
        }

        /* synthetic */ GDBypassStateListenerImpl(GDBypassAbilityImpl gDBypassAbilityImpl, hbfhc hbfhcVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements ApiPermission.SignatureChecker {
        hbfhc(GDBypassAbilityImpl gDBypassAbilityImpl) {
        }

        @Override // com.good.gd.utils.ApiPermission.SignatureChecker
        public boolean checkSignature(byte[] bArr, String str) {
            return GDBypassAbilityImpl.native_checkSignature(bArr, str);
        }
    }

    public GDBypassAbilityImpl() {
        this.listeners = null;
        this.stateListener = null;
        this.listeners = new HashSet();
        this.stateListener = new GDBypassStateListenerImpl(this, null);
    }

    private void checkBypassAbility() {
        if (getBypassAbilityFromSettings()) {
            this.needToCheck = true;
            GDDefaultAppEventListener.getInstance().addGDStateListener(this.stateListener);
            this.bypassEventHandler = new BypassEventHandler(this);
            ndkInit(this.permissionValue);
        }
    }

    private boolean getBypassAbilityFromSettings() {
        String checkSettingsFileForKey = GDSettings.checkSettingsFileForKey(BYPASS_UNLOCK_SETTING_KEY, false);
        if (checkSettingsFileForKey == null) {
            return false;
        }
        this.permissionValue = checkSettingsFileForKey;
        String applicationId = GDClient.getInstance().getApplicationId();
        String lowerCase = GDContext.getInstance().getApplicationContext().getPackageName().toLowerCase(Locale.ENGLISH);
        GDLog.DBGPRINTF(16, "GDBypassAbilityImpl: Using application Id <" + applicationId + "> native id <" + lowerCase + ">\n");
        ApiPermission[] fromSettings = ApiPermission.getFromSettings();
        if (fromSettings == null) {
            return false;
        }
        for (ApiPermission apiPermission : fromSettings) {
            if (apiPermission.checkPermission(applicationId, lowerCase, BYPASS_UNLOCK_PERMISSION_KEY) && apiPermission.checkSignature(new hbfhc(this))) {
                GDLog.DBGPRINTF(16, "GDBypassAbilityImpl: BypassPermission validated\n");
                return true;
            }
        }
        GDLog.DBGPRINTF(16, "BypassPermission: No BypassPermission found\n");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean native_checkSignature(byte[] bArr, String str);

    private native boolean native_isBypassAllowed();

    /* JADX INFO: Access modifiers changed from: private */
    public native void native_updatePolicyChecker();

    private native void ndkInit(String str);

    private synchronized void onBypassPolicyChanged(boolean z) {
        if (this.bypassEventHandler != null) {
            GDLog.DBGPRINTF(16, "GDBypassAbilityImpl onBypassPolicyChanged enabled = " + z + "\n");
            this.bypassEventHandler.sendEmptyMessage(z ? 1 : 0);
        }
    }

    public void addActivity(String str) {
        if (this.activityNames == null) {
            this.activityNames = new HashSet<>();
        }
        this.activityNames.add(str);
    }

    @Override // com.good.gd.bypass.GDBypassAbility
    public synchronized void addBypassPolicyListener(BypassPolicyListener bypassPolicyListener) {
        this.listeners.add(bypassPolicyListener);
    }

    public void init() {
        if (!this.initialized) {
            checkBypassAbility();
            this.initialized = true;
        }
    }

    @Override // com.good.gd.bypass.GDBypassAbility
    public boolean isBypassActivity(String str) {
        HashSet<String> hashSet = this.activityNames;
        if (hashSet == null || hashSet.isEmpty()) {
            return false;
        }
        GDLog.DBGPRINTF(16, "GDBypassAbilityImpl isBypassActivity : contains = " + this.activityNames.contains(str) + "\n");
        return this.activityNames.contains(str);
    }

    @Override // com.good.gd.bypass.GDBypassAbility
    public synchronized boolean isBypassAllowed() {
        return native_isBypassAllowed();
    }

    @Override // com.good.gd.bypass.GDBypassAbility
    public synchronized void removeBypassPolicyListener(BypassPolicyListener bypassPolicyListener) {
        this.listeners.remove(bypassPolicyListener);
    }
}
