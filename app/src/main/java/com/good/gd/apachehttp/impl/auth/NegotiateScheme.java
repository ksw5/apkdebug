package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.impl.auth.AuthSchemeBase;
import com.good.gd.apache.http.message.BasicHeader;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apachehttp.impl.auth.GDGSSException;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public final class NegotiateScheme extends AuthSchemeBase {
    protected static long GSS_S_BAD_BINDINGS = -1;
    protected static long GSS_S_BAD_MECH = -1;
    protected static long GSS_S_BAD_MIC = -1;
    protected static long GSS_S_BAD_NAME = -1;
    protected static long GSS_S_BAD_NAMETYPE = -1;
    protected static long GSS_S_BAD_QOP = -1;
    protected static long GSS_S_BAD_SIG = -1;
    protected static long GSS_S_BAD_STATUS = -1;
    protected static long GSS_S_COMPLETE = -1;
    protected static long GSS_S_CONTEXT_EXPIRED = -1;
    protected static long GSS_S_CONTINUE_NEEDED = -1;
    protected static long GSS_S_CREDENTIALS_EXPIRED = -1;
    protected static long GSS_S_DEFECTIVE_CREDENTIAL = -1;
    protected static long GSS_S_DEFECTIVE_TOKEN = -1;
    protected static long GSS_S_DUPLICATE_ELEMENT = -1;
    protected static long GSS_S_FAILURE = -1;
    protected static long GSS_S_NAME_NOT_MN = -1;
    protected static long GSS_S_NO_CONTEXT = -1;
    protected static long GSS_S_NO_CRED = -1;
    protected static long GSS_S_UNAUTHORIZED = -1;
    protected static long GSS_S_UNAVAILABLE = -1;
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private static final int MAX_CHALLENGE_CYCLE_ITERATIONS = 100;
    private int count;
    private String current_token;
    protected long nativeNetogiateDataPtr;
    private boolean responseReceived;
    private final SpnegoTokenGenerator spengoGenerator;
    private State state;
    private final boolean stripPort;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class Generate_GSS_Kerberos_token_Task implements Runnable {
        private final boolean dbjc;
        private String jwxax;
        private final String qkduk;
        private Exception ztwf = null;
        private String wxau = "";

        public Generate_GSS_Kerberos_token_Task(String str, boolean z, String str2) {
            this.dbjc = z;
            this.qkduk = str;
            this.jwxax = str2;
            GDLog.DBGPRINTF(14, "Generate_GSS_Kerberos_token_Task: create object =" + this + "\n");
        }

        public Exception dbjc() {
            return this.ztwf;
        }

        public String qkduk() {
            return this.wxau;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDLog.DBGPRINTF(14, "Generate_GSS_Kerberos_token_Task: run object =" + this + "Thread ID = " + Thread.currentThread().getId() + "\n");
            GDLog.DBGPRINTF(16, "Generate_GSS_Kerberos_token_Task: saved token:'" + this.jwxax + "'\n");
            try {
                byte[] generateGssApiData = NegotiateScheme.this.generateGssApiData(this.qkduk, this.dbjc, this.jwxax.getBytes());
                if (generateGssApiData != null) {
                    this.wxau = new String(generateGssApiData);
                }
            } catch (GDGSSException e) {
                this.ztwf = e;
                GDLog.DBGPRINTF(12, "Generate_GSS_Kerberos_token_Task: GSS exception took place:" + e.toString() + "\n");
            } catch (Exception e2) {
                this.ztwf = e2;
            }
            GDLog.DBGPRINTF(16, "Generate_GSS_Kerberos_token_Task: new token:'" + this.wxau + "'\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum State {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        TOKEN_GENERATED,
        COMPLETE,
        FAILED
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator, boolean z) {
        this.responseReceived = false;
        this.nativeNetogiateDataPtr = 0L;
        this.count = 0;
        GDLog.DBGPRINTF(16, "NegotiateScheme::NegotiateScheme() IN\n");
        this.state = State.UNINITIATED;
        this.spengoGenerator = spnegoTokenGenerator;
        this.stripPort = z;
        ndkInit();
        GDLog.DBGPRINTF(16, "NegotiateScheme::NegotiateScheme() OUT\n");
    }

    private native void clearNegotiateData(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public native byte[] generateGssApiData(String str, boolean z, byte[] bArr) throws GDGSSException;

    private String getFromRequest(HttpRequest httpRequest, int[] iArr) {
        if (httpRequest != null) {
            if (iArr != null && iArr.length >= 1) {
                GDLog.DBGPRINTF(16, "NegotiateScheme::getFromRequest\n");
                Header[] allHeaders = httpRequest.getAllHeaders();
                if (allHeaders == null) {
                    GDLog.DBGPRINTF(16, "NegotiateScheme::getFromRequest - no headers\n");
                } else {
                    for (Header header : allHeaders) {
                        if (header.getName() != null && header.getName().equalsIgnoreCase("HOST")) {
                            HeaderElement[] elements = header.getElements();
                            if (elements == null) {
                                GDLog.DBGPRINTF(16, "NegotiateScheme::getFromRequest - no headerElems\n");
                            } else {
                                for (HeaderElement headerElement : elements) {
                                    GDLog.DBGPRINTF(16, "NegotiateScheme::getFromRequest name is " + headerElement.getName() + "\n");
                                    GDLog.DBGPRINTF(16, "NegotiateScheme::getFromRequest value is " + headerElement.getValue() + "\n");
                                    if (headerElement.getName().contains(":")) {
                                        String[] split = headerElement.getName().split(":");
                                        if (split != null && split.length > 1) {
                                            iArr[0] = Integer.valueOf(split[1]).intValue();
                                            return split[0];
                                        }
                                    } else {
                                        iArr[0] = 80;
                                        return headerElement.getName();
                                    }
                                }
                                continue;
                            }
                        }
                    }
                }
                iArr[0] = 80;
                return "";
            }
            throw new IllegalArgumentException("invalid port argument provided");
        }
        throw new IllegalArgumentException("null request provided");
    }

    private native long getGssStatus(long j);

    private void matchAndThrow(long j, boolean z) throws AuthenticationException {
        if (z) {
            if (j != GSS_S_BAD_MECH) {
                if (j != GSS_S_BAD_NAME) {
                    if (j != GSS_S_BAD_NAMETYPE) {
                        if (j != GSS_S_BAD_BINDINGS) {
                            if (j != GSS_S_BAD_STATUS) {
                                if (j != GSS_S_BAD_MIC) {
                                    if (j != GSS_S_NO_CRED) {
                                        if (j != GSS_S_NO_CONTEXT) {
                                            if (j != GSS_S_DEFECTIVE_TOKEN) {
                                                if (j != GSS_S_DEFECTIVE_CREDENTIAL) {
                                                    if (j != GSS_S_CREDENTIALS_EXPIRED) {
                                                        if (j != GSS_S_CONTEXT_EXPIRED) {
                                                            if (j != GSS_S_FAILURE) {
                                                                if (j != GSS_S_BAD_QOP) {
                                                                    if (j != GSS_S_UNAUTHORIZED) {
                                                                        if (j != GSS_S_UNAVAILABLE) {
                                                                            if (j != GSS_S_DUPLICATE_ELEMENT) {
                                                                                if (j != GSS_S_NAME_NOT_MN) {
                                                                                    throw new GDGSSException(GDGSSException.Code.FAILURE);
                                                                                }
                                                                                throw new GDGSSException(GDGSSException.Code.NAME_NOT_MN);
                                                                            }
                                                                            throw new GDGSSException(GDGSSException.Code.DUPLICATE_ELEMENT);
                                                                        }
                                                                        throw new GDGSSException(GDGSSException.Code.UNAVAILABLE);
                                                                    }
                                                                    throw new GDGSSException(GDGSSException.Code.UNAUTHORIZED);
                                                                }
                                                                throw new GDGSSException(GDGSSException.Code.BAD_QOP);
                                                            }
                                                            throw new GDGSSException(GDGSSException.Code.FAILURE);
                                                        }
                                                        throw new GDGSSException(GDGSSException.Code.CONTEXT_EXPIRED);
                                                    }
                                                    throw new GDGSSException(GDGSSException.Code.CREDENTIALS_EXPIRED);
                                                }
                                                throw new GDGSSException(GDGSSException.Code.DEFECTIVE_CREDENTIAL);
                                            }
                                            throw new GDGSSException(GDGSSException.Code.DEFECTIVE_TOKEN);
                                        }
                                        throw new GDGSSException(GDGSSException.Code.NO_CONTEXT);
                                    }
                                    throw new GDGSSException(GDGSSException.Code.NO_CRED);
                                }
                                throw new GDGSSException(GDGSSException.Code.BAD_MIC);
                            }
                            throw new GDGSSException(GDGSSException.Code.BAD_STATUS);
                        }
                        throw new GDGSSException(GDGSSException.Code.BAD_BINDINGS);
                    }
                    throw new GDGSSException(GDGSSException.Code.BAD_NAMETYPE);
                }
                throw new GDGSSException(GDGSSException.Code.BAD_NAME);
            }
            throw new GDGSSException(GDGSSException.Code.BAD_BINDINGS);
        } else if (j != KerberosHandler.KRB5_LIBOS_BADPWDMATCH) {
            throw new GDGSSException(GDGSSException.Code.DEFECTIVE_CREDENTIAL);
        } else {
            throw new GDGSSException(GDGSSException.Code.UNAUTHORIZED);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00b2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c5 A[Catch: GDGSSException -> 0x021f, TRY_ENTER, TryCatch #1 {GDGSSException -> 0x021f, blocks: (B:6:0x0060, B:8:0x0067, B:10:0x006c, B:12:0x0072, B:16:0x007f, B:17:0x0092, B:19:0x0098, B:21:0x009e, B:27:0x00bd, B:30:0x00c5, B:33:0x00eb, B:35:0x011c, B:36:0x0129, B:38:0x0136, B:40:0x0140, B:42:0x0155, B:43:0x0172, B:45:0x0178, B:47:0x0180, B:48:0x019d, B:49:0x019f, B:50:0x01a0, B:52:0x01ac, B:53:0x01c8, B:55:0x01ce, B:59:0x01d6, B:61:0x01e0, B:64:0x01e7, B:65:0x01f2, B:66:0x01f3, B:68:0x01b1, B:70:0x01b7, B:71:0x01c1, B:73:0x014f, B:74:0x0125, B:76:0x0088, B:77:0x0217, B:78:0x021e), top: B:5:0x0060 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00eb A[Catch: GDGSSException -> 0x021f, TryCatch #1 {GDGSSException -> 0x021f, blocks: (B:6:0x0060, B:8:0x0067, B:10:0x006c, B:12:0x0072, B:16:0x007f, B:17:0x0092, B:19:0x0098, B:21:0x009e, B:27:0x00bd, B:30:0x00c5, B:33:0x00eb, B:35:0x011c, B:36:0x0129, B:38:0x0136, B:40:0x0140, B:42:0x0155, B:43:0x0172, B:45:0x0178, B:47:0x0180, B:48:0x019d, B:49:0x019f, B:50:0x01a0, B:52:0x01ac, B:53:0x01c8, B:55:0x01ce, B:59:0x01d6, B:61:0x01e0, B:64:0x01e7, B:65:0x01f2, B:66:0x01f3, B:68:0x01b1, B:70:0x01b7, B:71:0x01c1, B:73:0x014f, B:74:0x0125, B:76:0x0088, B:77:0x0217, B:78:0x021e), top: B:5:0x0060 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private String processAuthenticationToken(Credentials r11, HttpRequest r12, HttpContext r13) throws AuthenticationException {
        /*
            Method dump skipped, instructions count: 667
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apachehttp.impl.auth.NegotiateScheme.processAuthenticationToken(com.good.gd.apache.http.auth.Credentials, com.good.gd.apache.http.HttpRequest, com.good.gd.apache.http.protocol.HttpContext):java.lang.String");
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return authenticate(credentials, httpRequest, null);
    }

    public boolean authenticateReponse(Credentials credentials, HttpContext httpContext) throws AuthenticationException {
        GDLog.DBGPRINTF(16, "NegotiateScheme::authenticateReponse(credentials=[" + credentials.toString() + "]) IN: state=" + this.state + "\n");
        processAuthenticationToken(credentials, null, httpContext);
        boolean isComplete = isComplete();
        if (isComplete) {
            clearNegotiateData(this.nativeNetogiateDataPtr, false);
        }
        GDLog.DBGPRINTF(16, "NegotiateScheme::authenticate() OUT 1\n");
        return isComplete;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getParameter(String str) {
        if (str != null) {
            return null;
        }
        throw new IllegalArgumentException("Parameter name may not be null");
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getRealm() {
        return null;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getSchemeName() {
        return "Negotiate";
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isComplete() {
        State state = this.state;
        return state == State.COMPLETE || state == State.FAILED;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isConnectionBased() {
        return true;
    }

    native void ndkInit();

    @Override // com.good.gd.apache.http.impl.auth.AuthSchemeBase
    public void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) throws MalformedChallengeException {
        this.count++;
        String substringTrimmed = charArrayBuffer.substringTrimmed(i, i2);
        GDLog.DBGPRINTF(16, "NegotiateScheme::parseChallenge() Received challenge '" + substringTrimmed + "' from the auth server, count:" + this.count + "\n");
        State state = this.state;
        if ((state == State.UNINITIATED || state == State.TOKEN_GENERATED) && this.count < 100) {
            this.current_token = substringTrimmed;
            this.state = State.CHALLENGE_RECEIVED;
        } else {
            GDLog.DBGPRINTF(12, "NegotiateScheme::parseChallenge() Authentication already attempted\n");
            this.state = State.FAILED;
            this.current_token = "";
        }
        GDLog.DBGPRINTF(16, "NegotiateScheme::parseChallenge() OUT\n");
    }

    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        GDLog.DBGPRINTF(16, "NegotiateScheme::authenticate() IN: state=" + this.state + "\n");
        if (httpRequest != null) {
            if (getGssStatus(this.nativeNetogiateDataPtr) != GSS_S_COMPLETE) {
                String processAuthenticationToken = processAuthenticationToken(credentials, httpRequest, httpContext);
                if (isProxy()) {
                    return new BasicHeader(AUTH.PROXY_AUTH_RESP, "Negotiate " + processAuthenticationToken);
                }
                return new BasicHeader(AUTH.WWW_AUTH_RESP, "Negotiate " + processAuthenticationToken);
            }
            throw new GDGSSException(GDGSSException.Code.DEFECTIVE_CREDENTIAL);
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }

    public NegotiateScheme() {
        this(null, false);
    }
}
