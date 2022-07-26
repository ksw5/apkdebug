package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.util.LangUtils;
import java.security.Principal;

/* loaded from: classes.dex */
public final class BasicUserPrincipal implements Principal {
    private final String username;

    public BasicUserPrincipal(String str) {
        if (str != null) {
            this.username = str;
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
        return (obj instanceof BasicUserPrincipal) && LangUtils.equals(this.username, ((BasicUserPrincipal) obj).username);
    }

    @Override // java.security.Principal
    public String getName() {
        return this.username;
    }

    @Override // java.security.Principal
    public int hashCode() {
        return LangUtils.hashCode(17, this.username);
    }

    @Override // java.security.Principal
    public String toString() {
        return "[principal: " + this.username + "]";
    }
}
