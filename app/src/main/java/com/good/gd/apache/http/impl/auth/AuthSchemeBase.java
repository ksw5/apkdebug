package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public abstract class AuthSchemeBase implements AuthScheme {
    private boolean proxy;

    public boolean isProxy() {
        return this.proxy;
    }

    protected abstract void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException;

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        CharArrayBuffer charArrayBuffer;
        if (header != null) {
            String name = header.getName();
            int i = 0;
            if (name.equalsIgnoreCase(AUTH.WWW_AUTH)) {
                this.proxy = false;
            } else if (name.equalsIgnoreCase(AUTH.PROXY_AUTH)) {
                this.proxy = true;
            } else {
                throw new MalformedChallengeException("Unexpected header name: " + name);
            }
            if (header instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) header;
                charArrayBuffer = formattedHeader.getBuffer();
                i = formattedHeader.getValuePos();
            } else {
                String value = header.getValue();
                if (value != null) {
                    charArrayBuffer = new CharArrayBuffer(value.length());
                    charArrayBuffer.append(value);
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
            String substring = charArrayBuffer.substring(i, i2);
            if (substring.equalsIgnoreCase(getSchemeName())) {
                parseChallenge(charArrayBuffer, i2, charArrayBuffer.length());
                return;
            }
            throw new MalformedChallengeException("Invalid scheme identifier: " + substring);
        }
        throw new IllegalArgumentException("Header may not be null");
    }
}
