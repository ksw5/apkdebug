package com.good.gd.webauth;

import android.net.Uri;
import com.good.gt.MDMProvider.MDMConstants;

/* loaded from: classes.dex */
class yfdke {
    private final Uri dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(AuthProviderConfig authProviderConfig, hbfhc hbfhcVar) {
        this.dbjc = Uri.parse(authProviderConfig.dbjc()).buildUpon().appendQueryParameter("redirect_uri", hbfhcVar.lqox().toString()).appendQueryParameter("client_id", hbfhcVar.dbjc()).appendQueryParameter("response_type", hbfhcVar.liflu()).appendQueryParameter(AuthCodeResponse.STATE_KEY, hbfhcVar.tlske()).appendQueryParameter("scope", hbfhcVar.jcpqe()).appendQueryParameter(MDMConstants.MDM_NONCE, hbfhcVar.ztwf()).appendQueryParameter("code_challenge", hbfhcVar.qkduk()).appendQueryParameter("code_challenge_method", hbfhcVar.jwxax()).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Uri dbjc() {
        return this.dbjc;
    }
}
