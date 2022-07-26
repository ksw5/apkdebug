package com.good.gd.ndkproxy.pki;

import android.content.Intent;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.pki.Credential;
import com.good.gd.pki.CredentialsProfile;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class CredentialsProfileImpl {
    private static final String GD_PROFILE_ID_EXTRA = "gd_profile_id_extra";
    private static final String GD_PROFILE_NAME_EXTRA = "gd_profile_name_extra";
    private static final String GD_PROFILE_REQUIRED_EXTRA = "gd_profile_required_extra";
    private static final String GD_PROFILE_SETTINGS_EXTRA = "gd_profile_settings_extra";
    private static final String GD_PROFILE_STATE_EXTRA = "gd_profile_state_extra";
    private static CredentialsProfileImpl _instance;

    private CredentialsProfileImpl() {
        NDK_init();
    }

    private static native Credential[] NDK_getCredentials(String str);

    private static native BaseCredentialsProfile[] NDK_getProfiles();

    private native void NDK_init();

    private native void NDK_register();

    private native void NDK_unregister();

    public static String getId(Intent intent) {
        return intent.getStringExtra(GD_PROFILE_ID_EXTRA);
    }

    public static synchronized CredentialsProfileImpl getInstance() {
        CredentialsProfileImpl credentialsProfileImpl;
        synchronized (CredentialsProfileImpl.class) {
            if (_instance == null) {
                _instance = new CredentialsProfileImpl();
            }
            credentialsProfileImpl = _instance;
        }
        return credentialsProfileImpl;
    }

    public static String getName(Intent intent) {
        return intent.getStringExtra(GD_PROFILE_NAME_EXTRA);
    }

    public static CredentialsProfile.State getProfileState(Intent intent) {
        return CredentialsProfile.State.get(intent.getIntExtra(GD_PROFILE_STATE_EXTRA, CredentialsProfile.State.GDCredentialsProfileStateNone.getCode()));
    }

    public static String getProviderSettings(Intent intent) {
        return intent.getStringExtra(GD_PROFILE_SETTINGS_EXTRA);
    }

    public static boolean getRequired(Intent intent) {
        return intent.getBooleanExtra(GD_PROFILE_REQUIRED_EXTRA, false);
    }

    private void onProfileStateChange(BaseCredentialsProfile baseCredentialsProfile) {
        Intent intent = new Intent(CredentialsProfile.GD_CREDENTIAL_PROFILE_STATE_CHANGE_ACTION);
        intent.putExtra(GD_PROFILE_STATE_EXTRA, baseCredentialsProfile.getState().getCode());
        intent.putExtra(GD_PROFILE_ID_EXTRA, baseCredentialsProfile.getId());
        intent.putExtra(GD_PROFILE_NAME_EXTRA, baseCredentialsProfile.getName());
        intent.putExtra(GD_PROFILE_REQUIRED_EXTRA, baseCredentialsProfile.getRequired());
        intent.putExtra(GD_PROFILE_SETTINGS_EXTRA, baseCredentialsProfile.getProviderSettings());
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    public static native void testpurposes_addDefinitionsIntoProvisionData();

    public static native void testpurposes_removeDefinitionsIntoProvisionData();

    public List<Credential> getCredentials(String str) {
        Credential[] NDK_getCredentials = NDK_getCredentials(str);
        if (NDK_getCredentials != null) {
            return Arrays.asList(NDK_getCredentials);
        }
        return null;
    }

    public Map<String, CredentialsProfile> getMap() {
        HashMap hashMap = new HashMap();
        BaseCredentialsProfile[] NDK_getProfiles = NDK_getProfiles();
        if (NDK_getProfiles != null) {
            for (BaseCredentialsProfile baseCredentialsProfile : NDK_getProfiles) {
                hashMap.put(baseCredentialsProfile.getId(), baseCredentialsProfile);
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public void register() {
        NDK_register();
    }

    public void unregister() {
        NDK_unregister();
    }
}
