package com.good.gd.ndkproxy;

import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.utils.GDNDKLibraryLoader;
import com.good.gd.utils.JSONUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class ApplicationPolicy {
    private static ApplicationPolicy _instance;
    private Map<String, Object> appPolicy;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private ApplicationPolicy() {
    }

    private native byte[] getAppPolicyString();

    public static synchronized ApplicationPolicy getInstance() {
        ApplicationPolicy applicationPolicy;
        synchronized (ApplicationPolicy.class) {
            if (_instance == null) {
                _instance = new ApplicationPolicy();
            }
            applicationPolicy = _instance;
        }
        return applicationPolicy;
    }

    public synchronized Map<String, Object> getApplicationPolicy() {
        try {
        } catch (JSONException e) {
            return new HashMap();
        }
        return new HashMap(JSONUtils.toMap(new JSONObject(getApplicationPolicyString())));
    }

    public synchronized String getApplicationPolicyString() {
        String str;
        try {
            str = new String(getAppPolicyString(), HTTP.UTF_8);
        } catch (Exception e) {
            str = null;
        }
        return str;
    }
}
