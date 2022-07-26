package com.good.gd.utils;

import android.content.Context;
import com.good.gd.ndkproxy.GDSettings;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDStateListenerHelper {
    private static final String AUTHENTICATION_CHECK_KEY = "CheckEventReceiver";
    private boolean mIsExplicitListenerSet = false;
    private boolean mIsInitialized = false;
    private boolean mCheckAuthenticationReceiver = true;

    public void init(Context context) {
        if (!this.mIsInitialized) {
            GDSettings.initializeLightweightInfo(context);
            JSONObject settingsV2 = GDSettings.getSettingsV2();
            if (settingsV2 != null && settingsV2.has(AUTHENTICATION_CHECK_KEY)) {
                try {
                    this.mCheckAuthenticationReceiver = settingsV2.getBoolean(AUTHENTICATION_CHECK_KEY);
                } catch (JSONException e) {
                    this.mCheckAuthenticationReceiver = true;
                }
            }
            this.mIsInitialized = true;
        }
    }

    public boolean isApplicationAllowedToRun(boolean z) {
        return this.mIsExplicitListenerSet || z || !this.mCheckAuthenticationReceiver;
    }

    public void setExplicitListenerSet(boolean z) {
        this.mIsExplicitListenerSet = z;
    }
}
