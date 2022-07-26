package com.good.gd.apachehttp.impl.client;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.client.protocol.ClientContext;
import com.good.gd.apache.http.impl.client.DefaultTargetAuthenticationHandler;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.FipsUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class GDAbstractFipsAuthenticationHandler extends DefaultTargetAuthenticationHandler {
    protected static final List<String> DEFAULT_SCHEME_PRIORITY = Arrays.asList("negotiate", "ntlm", "digest", "basic");

    private boolean shouldIgnoreAuthScheme(String str) {
        return FipsUtils.isInFipsMode() && (str.equalsIgnoreCase("digest") || str.equalsIgnoreCase("ntlm"));
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractAuthenticationHandler, com.good.gd.apache.http.client.AuthenticationHandler
    public AuthScheme selectScheme(Map<String, Header> map, HttpResponse httpResponse, HttpContext httpContext) throws AuthenticationException {
        AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry) httpContext.getAttribute(ClientContext.AUTHSCHEME_REGISTRY);
        if (authSchemeRegistry != null) {
            List<String> list = (List) httpContext.getAttribute(ClientContext.AUTH_SCHEME_PREF);
            if (list == null) {
                list = getAuthPreferences();
            }
            GDLog.DBGPRINTF(16, "Authentication schemes in the order of preference: " + list + "\n");
            AuthScheme authScheme = null;
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (map.get(str.toLowerCase(Locale.ENGLISH)) != null) {
                    GDLog.DBGPRINTF(16, str + " authentication scheme selected\n");
                    try {
                        authScheme = authSchemeRegistry.getAuthScheme(str, httpResponse.getParams());
                        if (!shouldIgnoreAuthScheme(authScheme.getSchemeName())) {
                            break;
                        }
                        try {
                            GDLog.DBGPRINTF(13, "Authentication scheme " + str + " is denied by FIPS\n");
                            authScheme = null;
                        } catch (IllegalStateException e) {
                            authScheme = null;
                            GDLog.DBGPRINTF(13, "Authentication scheme " + str + " not supported\n");
                        }
                    } catch (IllegalStateException e2) {
                    }
                } else {
                    GDLog.DBGPRINTF(16, "Challenge for " + str + " authentication scheme not available\n");
                }
            }
            if (authScheme != null) {
                GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::selectScheme(): " + authScheme.getSchemeName());
                return authScheme;
            }
            throw new AuthenticationException("Unable to respond to any of these challenges: " + map + "\n");
        }
        throw new IllegalStateException("AuthScheme registry not set in HTTP context + \n");
    }
}
