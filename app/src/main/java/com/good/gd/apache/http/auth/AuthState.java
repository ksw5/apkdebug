package com.good.gd.apache.http.auth;

/* loaded from: classes.dex */
public class AuthState {
    private AuthScheme authScheme;
    private AuthScope authScope;
    private Credentials credentials;

    public AuthScheme getAuthScheme() {
        return this.authScheme;
    }

    public AuthScope getAuthScope() {
        return this.authScope;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void invalidate() {
        this.authScheme = null;
        this.authScope = null;
        this.credentials = null;
    }

    public boolean isValid() {
        return this.authScheme != null;
    }

    public void setAuthScheme(AuthScheme authScheme) {
        if (authScheme == null) {
            invalidate();
        } else {
            this.authScheme = authScheme;
        }
    }

    public void setAuthScope(AuthScope authScope) {
        this.authScope = authScope;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("auth scope [");
        sb.append(this.authScope);
        sb.append("]; credentials set [");
        sb.append(this.credentials != null ? "true" : "false");
        sb.append("]");
        return sb.toString();
    }
}
