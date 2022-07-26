package com.good.gd.utility;

import com.blackberry.security.jwt.BBDJWTokenCallback;
import com.blackberry.security.jwt.BBDJWTokenCallbackHandler;
import com.good.gd.context.GDContext;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.enterprise.GDEAuthTokenManager;
import com.good.gd.ndkproxy.enterprise.GDESharedIDImpl;

/* loaded from: classes.dex */
public class GDUtilityImpl implements DynamicsSharedUserID {
    @Override // com.good.gd.utility.DynamicsSharedUserID
    public String getDynamicsSharedUserID() throws GDNotAuthorizedError {
        return GDContext.getInstance().isWiped() ? "" : GDESharedIDImpl.getDynamicsSharedUserID();
    }

    public void getEIDToken(String str, String str2, BBDJWTokenCallback bBDJWTokenCallback, boolean z, String str3) {
        getEIDToken(str, str2, bBDJWTokenCallback, z, str3, "");
    }

    public void getGDAuthToken(String str, String str2, GDAuthTokenCallback gDAuthTokenCallback) {
        GDEAuthTokenManager.getInstance().getGDAuthToken(str, str2, gDAuthTokenCallback);
    }

    public void getEIDToken(String str, String str2, BBDJWTokenCallback bBDJWTokenCallback, boolean z, String str3, String str4) {
        if (GDContext.getInstance().isWiped() || bBDJWTokenCallback == null) {
            return;
        }
        BBDJWTokenCallbackHandler bBDJWTokenCallbackHandler = BBDJWTokenCallbackHandler.getInstance();
        if (str3 == null) {
            str3 = "";
        }
        String str5 = str3;
        if (str != null && str2 != null) {
            bBDJWTokenCallbackHandler.getEIDToken(str, str2, bBDJWTokenCallback, z, str5, "");
        } else {
            bBDJWTokenCallbackHandler.getEIDToken("", "", bBDJWTokenCallback, z, str5, "");
        }
    }
}
