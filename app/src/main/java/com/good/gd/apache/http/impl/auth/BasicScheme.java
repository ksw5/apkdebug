package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.commons.codec.binary.Base64;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.auth.params.AuthParams;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apache.http.util.EncodingUtils;

/* loaded from: classes.dex */
public class BasicScheme extends RFC2617Scheme {
    private boolean complete = false;

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        if (credentials != null) {
            if (httpRequest != null) {
                return authenticate(credentials, AuthParams.getCredentialCharset(httpRequest.getParams()), isProxy());
            }
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        throw new IllegalArgumentException("Credentials may not be null");
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getSchemeName() {
        return "basic";
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isComplete() {
        return this.complete;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isConnectionBased() {
        return false;
    }

    @Override // com.good.gd.apache.http.impl.auth.AuthSchemeBase, com.good.gd.apache.http.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
    }

    public static Header authenticate(Credentials credentials, String str, boolean z) {
        if (credentials != null) {
            if (str != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(credentials.getUserPrincipal().getName());
                sb.append(":");
                sb.append(credentials.getPassword() == null ? "null" : credentials.getPassword());
                byte[] encodeBase64 = Base64.encodeBase64(EncodingUtils.getBytes(sb.toString(), str));
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
                if (z) {
                    charArrayBuffer.append(AUTH.PROXY_AUTH_RESP);
                } else {
                    charArrayBuffer.append(AUTH.WWW_AUTH_RESP);
                }
                charArrayBuffer.append(": Basic ");
                charArrayBuffer.append(encodeBase64, 0, encodeBase64.length);
                return new BufferedHeader(charArrayBuffer);
            }
            throw new IllegalArgumentException("charset may not be null");
        }
        throw new IllegalArgumentException("Credentials may not be null");
    }
}
