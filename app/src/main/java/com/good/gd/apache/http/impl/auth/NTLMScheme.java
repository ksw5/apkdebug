package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.auth.InvalidCredentialsException;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.auth.NTCredentials;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class NTLMScheme extends AuthSchemeBase {
    private String challenge;
    private final NTLMEngine engine;
    private State state;

    /* loaded from: classes.dex */
    enum State {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        MSG_TYPE1_GENERATED,
        MSG_TYPE2_RECEVIED,
        MSG_TYPE3_GENERATED,
        FAILED
    }

    public NTLMScheme(NTLMEngine nTLMEngine) {
        if (nTLMEngine != null) {
            this.engine = nTLMEngine;
            this.state = State.UNINITIATED;
            this.challenge = null;
            return;
        }
        throw new IllegalArgumentException("NTLM engine may not be null");
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        String generateType1Msg;
        try {
            NTCredentials nTCredentials = (NTCredentials) credentials;
            State state = this.state;
            if (state != State.CHALLENGE_RECEIVED && state != State.FAILED) {
                if (state == State.MSG_TYPE2_RECEVIED) {
                    generateType1Msg = this.engine.generateType3Msg(nTCredentials.getUserName(), nTCredentials.getPassword(), nTCredentials.getDomain(), nTCredentials.getWorkstation(), this.challenge);
                    this.state = State.MSG_TYPE3_GENERATED;
                } else {
                    throw new AuthenticationException("Unexpected state: " + this.state);
                }
            } else {
                generateType1Msg = this.engine.generateType1Msg(nTCredentials.getDomain(), nTCredentials.getWorkstation());
                this.state = State.MSG_TYPE1_GENERATED;
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
            if (isProxy()) {
                charArrayBuffer.append(AUTH.PROXY_AUTH_RESP);
            } else {
                charArrayBuffer.append(AUTH.WWW_AUTH_RESP);
            }
            charArrayBuffer.append(": NTLM ");
            charArrayBuffer.append(generateType1Msg);
            return new BufferedHeader(charArrayBuffer);
        } catch (ClassCastException e) {
            throw new InvalidCredentialsException("Credentials cannot be used for NTLM authentication: " + credentials.getClass().getName());
        }
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getParameter(String str) {
        return null;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getRealm() {
        return null;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getSchemeName() {
        return "ntlm";
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isComplete() {
        State state = this.state;
        return state == State.MSG_TYPE3_GENERATED || state == State.FAILED;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isConnectionBased() {
        return true;
    }

    @Override // com.good.gd.apache.http.impl.auth.AuthSchemeBase
    protected void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException {
        String substringTrimmed = charArrayBuffer.substringTrimmed(i, i2);
        if (substringTrimmed.length() == 0) {
            if (this.state == State.UNINITIATED) {
                this.state = State.CHALLENGE_RECEIVED;
            } else {
                this.state = State.FAILED;
            }
            this.challenge = null;
            return;
        }
        this.state = State.MSG_TYPE2_RECEVIED;
        this.challenge = substringTrimmed;
    }
}
