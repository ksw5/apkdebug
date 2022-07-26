package com.good.gd.apachehttp.auth;

import com.good.gd.apache.http.auth.BasicUserPrincipal;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.util.LangUtils;
import java.security.Principal;

/* loaded from: classes.dex */
public final class Kerberos5Credentials implements Credentials {
    private final Boolean allowDelegation;
    private final String host;
    private final String password;
    private final BasicUserPrincipal principal;

    public Kerberos5Credentials(String str, String str2, String str3) {
        this(str, str2, str3, null);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return (obj instanceof Kerberos5Credentials) && LangUtils.equals(this.principal, ((Kerberos5Credentials) obj).principal);
    }

    public String getHost() {
        return this.host;
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

    public Boolean isDelegationAllowed() {
        return this.allowDelegation;
    }

    public String toString() {
        return this.principal.toString();
    }

    public Kerberos5Credentials(String str, String str2, String str3, Boolean bool) {
        if (str != null) {
            this.principal = new BasicUserPrincipal(str);
            this.password = str2;
            this.host = str3;
            this.allowDelegation = bool;
            return;
        }
        throw new IllegalArgumentException("Username may not be null");
    }
}
