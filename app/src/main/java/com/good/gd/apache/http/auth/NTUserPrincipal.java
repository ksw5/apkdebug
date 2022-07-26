package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.util.LangUtils;
import com.good.gd.ndkproxy.file.RandomAccessFileImpl;
import java.security.Principal;
import java.util.Locale;

/* loaded from: classes.dex */
public class NTUserPrincipal implements Principal {
    private final String domain;
    private final String ntname;
    private final String username;

    public NTUserPrincipal(String str, String str2) {
        if (str2 != null) {
            this.username = str2;
            if (str != null) {
                this.domain = str.toUpperCase(Locale.ENGLISH);
            } else {
                this.domain = null;
            }
            String str3 = this.domain;
            if (str3 != null && str3.length() > 0) {
                this.ntname = this.domain + RandomAccessFileImpl.separatorChar + str2;
                return;
            }
            this.ntname = str2;
            return;
        }
        throw new IllegalArgumentException("User name may not be null");
    }

    @Override // java.security.Principal
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof NTUserPrincipal) {
            NTUserPrincipal nTUserPrincipal = (NTUserPrincipal) obj;
            if (LangUtils.equals(this.username, nTUserPrincipal.username) && LangUtils.equals(this.domain, nTUserPrincipal.domain)) {
                return true;
            }
        }
        return false;
    }

    public String getDomain() {
        return this.domain;
    }

    @Override // java.security.Principal
    public String getName() {
        return this.ntname;
    }

    public String getUsername() {
        return this.username;
    }

    @Override // java.security.Principal
    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.username), this.domain);
    }

    @Override // java.security.Principal
    public String toString() {
        return this.ntname;
    }
}
