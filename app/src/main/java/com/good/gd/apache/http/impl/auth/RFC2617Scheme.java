package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.message.BasicHeaderValueParser;
import com.good.gd.apache.http.message.ParserCursor;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class RFC2617Scheme extends AuthSchemeBase {
    private Map<String, String> params;

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getParameter(String str) {
        if (str != null) {
            Map<String, String> map = this.params;
            if (map != null) {
                return map.get(str.toLowerCase(Locale.ENGLISH));
            }
            return null;
        }
        throw new IllegalArgumentException("Parameter name may not be null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Map<String, String> getParameters() {
        if (this.params == null) {
            this.params = new HashMap();
        }
        return this.params;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getRealm() {
        return getParameter("realm");
    }

    @Override // com.good.gd.apache.http.impl.auth.AuthSchemeBase
    protected void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException {
        HeaderElement[] parseElements = BasicHeaderValueParser.DEFAULT.parseElements(charArrayBuffer, new ParserCursor(i, charArrayBuffer.length()));
        if (parseElements.length != 0) {
            this.params = new HashMap(parseElements.length);
            for (HeaderElement headerElement : parseElements) {
                this.params.put(headerElement.getName(), headerElement.getValue());
            }
            return;
        }
        throw new MalformedChallengeException("Authentication challenge is empty");
    }
}
