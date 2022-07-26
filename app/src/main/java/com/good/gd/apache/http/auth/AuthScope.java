package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.util.LangUtils;
import java.util.Locale;

/* loaded from: classes.dex */
public class AuthScope {
    public static final AuthScope ANY = new AuthScope(null, -1, null, null);
    public static final String ANY_HOST = null;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM = null;
    public static final String ANY_SCHEME = null;
    private final String host;
    private final int port;
    private final String realm;
    private final String scheme;

    public AuthScope(String str, int i, String str2, String str3) {
        this.host = str == null ? ANY_HOST : str.toLowerCase(Locale.ENGLISH);
        this.port = i < 0 ? -1 : i;
        this.realm = str2 == null ? ANY_REALM : str2;
        this.scheme = str3 == null ? ANY_SCHEME : str3.toUpperCase(Locale.ENGLISH);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthScope)) {
            return super.equals(obj);
        }
        AuthScope authScope = (AuthScope) obj;
        return LangUtils.equals(this.host, authScope.host) && this.port == authScope.port && LangUtils.equals(this.realm, authScope.realm) && LangUtils.equals(this.scheme, authScope.scheme);
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getRealm() {
        return this.realm;
    }

    public String getScheme() {
        return this.scheme;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.host), this.port), this.realm), this.scheme);
    }

    public int match(AuthScope authScope) {
        int i;
        if (LangUtils.equals(this.scheme, authScope.scheme)) {
            i = 1;
        } else {
            String str = this.scheme;
            String str2 = ANY_SCHEME;
            if (str != str2 && authScope.scheme != str2) {
                return -1;
            }
            i = 0;
        }
        if (LangUtils.equals(this.realm, authScope.realm)) {
            i += 2;
        } else {
            String str3 = this.realm;
            String str4 = ANY_REALM;
            if (str3 != str4 && authScope.realm != str4) {
                return -1;
            }
        }
        int i2 = this.port;
        int i3 = authScope.port;
        if (i2 == i3) {
            i += 4;
        } else if (i2 != -1 && i3 != -1) {
            return -1;
        }
        if (LangUtils.equals(this.host, authScope.host)) {
            return i + 8;
        }
        String str5 = this.host;
        String str6 = ANY_HOST;
        if (str5 != str6 && authScope.host != str6) {
            return -1;
        }
        return i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = this.scheme;
        if (str != null) {
            stringBuffer.append(str.toUpperCase(Locale.ENGLISH));
            stringBuffer.append(' ');
        }
        if (this.realm != null) {
            stringBuffer.append('\'');
            stringBuffer.append(this.realm);
            stringBuffer.append('\'');
        } else {
            stringBuffer.append("<any realm>");
        }
        if (this.host != null) {
            stringBuffer.append('@');
            stringBuffer.append(this.host);
            if (this.port >= 0) {
                stringBuffer.append(':');
                stringBuffer.append(this.port);
            }
        }
        return stringBuffer.toString();
    }

    public AuthScope(String str, int i, String str2) {
        this(str, i, str2, ANY_SCHEME);
    }

    public AuthScope(String str, int i) {
        this(str, i, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(AuthScope authScope) {
        if (authScope != null) {
            this.host = authScope.getHost();
            this.port = authScope.getPort();
            this.realm = authScope.getRealm();
            this.scheme = authScope.getScheme();
            return;
        }
        throw new IllegalArgumentException("Scope may not be null");
    }
}
