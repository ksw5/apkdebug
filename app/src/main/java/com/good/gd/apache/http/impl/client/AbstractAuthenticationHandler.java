package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.client.AuthenticationHandler;
import com.good.gd.apache.http.client.protocol.ClientContext;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
    private static final List<String> DEFAULT_SCHEME_PRIORITY = Arrays.asList("ntlm", "digest", "basic");
    private final Log log = LogFactory.getLog(getClass());

    /* JADX INFO: Access modifiers changed from: protected */
    public List<String> getAuthPreferences() {
        return DEFAULT_SCHEME_PRIORITY;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Map<String, Header> parseChallenges(Header[] headerArr) throws MalformedChallengeException {
        CharArrayBuffer charArrayBuffer;
        int i;
        HashMap hashMap = new HashMap(headerArr.length);
        for (Header header : headerArr) {
            if (header instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) header;
                charArrayBuffer = formattedHeader.getBuffer();
                i = formattedHeader.getValuePos();
            } else {
                String value = header.getValue();
                if (value != null) {
                    charArrayBuffer = new CharArrayBuffer(value.length());
                    charArrayBuffer.append(value);
                    i = 0;
                } else {
                    throw new MalformedChallengeException("Header value is null");
                }
            }
            while (i < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(i))) {
                i++;
            }
            int i2 = i;
            while (i2 < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(i2))) {
                i2++;
            }
            hashMap.put(charArrayBuffer.substring(i, i2).toLowerCase(Locale.ENGLISH), header);
        }
        return hashMap;
    }

    @Override // com.good.gd.apache.http.client.AuthenticationHandler
    public AuthScheme selectScheme(Map<String, Header> map, HttpResponse httpResponse, HttpContext httpContext) throws AuthenticationException {
        AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry) httpContext.getAttribute(ClientContext.AUTHSCHEME_REGISTRY);
        if (authSchemeRegistry != null) {
            List<String> list = (List) httpContext.getAttribute(ClientContext.AUTH_SCHEME_PREF);
            if (list == null) {
                list = getAuthPreferences();
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Authentication schemes in the order of preference: " + list);
            }
            AuthScheme authScheme = null;
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                if (map.get(str.toLowerCase(Locale.ENGLISH)) != null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(str + " authentication scheme selected");
                    }
                    try {
                        authScheme = authSchemeRegistry.getAuthScheme(str, httpResponse.getParams());
                        break;
                    } catch (IllegalStateException e) {
                        if (this.log.isWarnEnabled()) {
                            this.log.warn("Authentication scheme " + str + " not supported");
                        }
                    }
                } else if (this.log.isDebugEnabled()) {
                    this.log.debug("Challenge for " + str + " authentication scheme not available");
                }
            }
            if (authScheme == null) {
                throw new AuthenticationException("Unable to respond to any of these challenges: " + map);
            }
            return authScheme;
        }
        throw new IllegalStateException("AuthScheme registry not set in HTTP context");
    }
}
