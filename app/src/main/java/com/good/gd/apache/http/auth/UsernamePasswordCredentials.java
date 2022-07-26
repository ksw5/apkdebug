package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.util.LangUtils;
import java.security.Principal;

/* loaded from: classes.dex */
public class UsernamePasswordCredentials implements Credentials {
    private final String password;
    private final BasicUserPrincipal principal;

    public UsernamePasswordCredentials(String str) {
        if (str != null) {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                this.principal = new BasicUserPrincipal(str.substring(0, indexOf));
                this.password = str.substring(indexOf + 1);
                return;
            }
            this.principal = new BasicUserPrincipal(str);
            this.password = null;
            return;
        }
        throw new IllegalArgumentException("Username:password string may not be null");
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof UsernamePasswordCredentials) && LangUtils.equals(this.principal, ((UsernamePasswordCredentials) obj).principal);
    }

    @Override // com.good.gd.apache.http.auth.Credentials
    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    @Override // com.good.gd.apache.http.auth.Credentials
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public String toString() {
        return this.principal.toString();
    }

    public UsernamePasswordCredentials(String str, String str2) {
        if (str != null) {
            this.principal = new BasicUserPrincipal(str);
            this.password = str2;
            return;
        }
        throw new IllegalArgumentException("Username may not be null");
    }
}
