package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.util.LangUtils;
import java.security.Principal;
import java.util.Locale;

/* loaded from: classes.dex */
public class NTCredentials implements Credentials {
    private final String password;
    private final NTUserPrincipal principal;
    private final String workstation;

    public NTCredentials(String str) {
        if (str != null) {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                String substring = str.substring(0, indexOf);
                this.password = str.substring(indexOf + 1);
                str = substring;
            } else {
                this.password = null;
            }
            int indexOf2 = str.indexOf(47);
            if (indexOf2 >= 0) {
                this.principal = new NTUserPrincipal(str.substring(0, indexOf2).toUpperCase(Locale.ENGLISH), str.substring(indexOf2 + 1));
            } else {
                this.principal = new NTUserPrincipal(null, str.substring(indexOf2 + 1));
            }
            this.workstation = null;
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
        if (obj instanceof NTCredentials) {
            NTCredentials nTCredentials = (NTCredentials) obj;
            if (LangUtils.equals(this.principal, nTCredentials.principal) && LangUtils.equals(this.workstation, nTCredentials.workstation)) {
                return true;
            }
        }
        return false;
    }

    public String getDomain() {
        return this.principal.getDomain();
    }

    @Override // com.good.gd.apache.http.auth.Credentials
    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.principal.getUsername();
    }

    @Override // com.good.gd.apache.http.auth.Credentials
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getWorkstation() {
        return this.workstation;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, this.principal), this.workstation);
    }

    public String toString() {
        return "[principal: " + this.principal + "][workstation: " + this.workstation + "]";
    }

    public NTCredentials(String str, String str2, String str3, String str4) {
        if (str != null) {
            this.principal = new NTUserPrincipal(str4, str);
            this.password = str2;
            if (str3 != null) {
                this.workstation = str3.toUpperCase(Locale.ENGLISH);
                return;
            } else {
                this.workstation = null;
                return;
            }
        }
        throw new IllegalArgumentException("User name may not be null");
    }
}
