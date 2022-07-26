package com.good.gd.apache.http.client;

import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.auth.Credentials;

/* loaded from: classes.dex */
public interface CredentialsProvider {
    void clear();

    Credentials getCredentials(AuthScope authScope);

    void setCredentials(AuthScope authScope, Credentials credentials);
}
