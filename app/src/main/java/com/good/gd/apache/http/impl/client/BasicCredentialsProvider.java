package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.client.CredentialsProvider;
import java.util.HashMap;

/* loaded from: classes.dex */
public class BasicCredentialsProvider implements CredentialsProvider {
    private final HashMap<AuthScope, Credentials> credMap = new HashMap<>();

    private static Credentials matchCredentials(HashMap<AuthScope, Credentials> hashMap, AuthScope authScope) {
        Credentials credentials = hashMap.get(authScope);
        if (credentials == null) {
            int i = -1;
            AuthScope authScope2 = null;
            for (AuthScope authScope3 : hashMap.keySet()) {
                int match = authScope.match(authScope3);
                if (match > i) {
                    authScope2 = authScope3;
                    i = match;
                }
            }
            return authScope2 != null ? hashMap.get(authScope2) : credentials;
        }
        return credentials;
    }

    @Override // com.good.gd.apache.http.client.CredentialsProvider
    public synchronized void clear() {
        this.credMap.clear();
    }

    @Override // com.good.gd.apache.http.client.CredentialsProvider
    public synchronized Credentials getCredentials(AuthScope authScope) {
        if (authScope != null) {
        } else {
            throw new IllegalArgumentException("Authentication scope may not be null");
        }
        return matchCredentials(this.credMap, authScope);
    }

    @Override // com.good.gd.apache.http.client.CredentialsProvider
    public synchronized void setCredentials(AuthScope authScope, Credentials credentials) {
        if (authScope != null) {
            this.credMap.put(authScope, credentials);
        } else {
            throw new IllegalArgumentException("Authentication scope may not be null");
        }
    }

    public String toString() {
        return this.credMap.toString();
    }
}
