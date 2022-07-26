package com.good.gd.utility;

import com.blackberry.security.jwt.BBDJWTokenCallback;
import com.good.gd.error.GDNotAuthorizedError;

/* loaded from: classes.dex */
public final class GDUtility {
    private static GDUtilityImpl impl = new GDUtilityImpl();

    public static void getEIDToken(String str, String str2, String str3, BBDJWTokenCallback bBDJWTokenCallback, boolean z) {
        impl.getEIDToken(str, str2, bBDJWTokenCallback, z, str3);
    }

    public String getDynamicsSharedUserID() throws GDNotAuthorizedError {
        return impl.getDynamicsSharedUserID();
    }

    public void getGDAuthToken(String str, String str2, GDAuthTokenCallback gDAuthTokenCallback) {
        impl.getGDAuthToken(str, str2, gDAuthTokenCallback);
    }
}
