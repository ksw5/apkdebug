package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpRequest;

/* loaded from: classes.dex */
public interface AuthScheme {
    Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException;

    String getParameter(String str);

    String getRealm();

    String getSchemeName();

    boolean isComplete();

    boolean isConnectionBased();

    void processChallenge(Header header) throws MalformedChallengeException;
}
