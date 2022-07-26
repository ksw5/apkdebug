package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.auth.params.AuthParams;
import com.good.gd.apache.http.message.BasicHeaderValueFormatter;
import com.good.gd.apache.http.message.BasicNameValuePair;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apache.http.util.EncodingUtils;
import com.good.gt.MDMProvider.MDMConstants;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public class DigestScheme extends RFC2617Scheme {
    private static final char[] HEXADECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String NC = "00000001";
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private String cnonce;
    private int qopVariant = 0;
    private boolean complete = false;

    public static String createCnonce() {
        return encode(createMessageDigest("MD5").digest(EncodingUtils.getAsciiBytes(Long.toString(System.currentTimeMillis()))));
    }

    private String createDigest(Credentials credentials) throws AuthenticationException {
        String sb;
        String parameter = getParameter("uri");
        String parameter2 = getParameter("realm");
        String parameter3 = getParameter(MDMConstants.MDM_NONCE);
        String parameter4 = getParameter("methodname");
        String parameter5 = getParameter("algorithm");
        if (parameter != null) {
            if (parameter2 == null) {
                throw new IllegalStateException("Realm may not be null");
            }
            if (parameter3 != null) {
                if (parameter5 == null) {
                    parameter5 = "MD5";
                }
                String parameter6 = getParameter("charset");
                if (parameter6 == null) {
                    parameter6 = "ISO-8859-1";
                }
                if (this.qopVariant != 1) {
                    MessageDigest createMessageDigest = createMessageDigest("MD5");
                    String name = credentials.getUserPrincipal().getName();
                    String password = credentials.getPassword();
                    StringBuilder sb2 = new StringBuilder(name.length() + parameter2.length() + password.length() + 2);
                    sb2.append(name);
                    sb2.append(':');
                    sb2.append(parameter2);
                    sb2.append(':');
                    sb2.append(password);
                    String sb3 = sb2.toString();
                    if (parameter5.equalsIgnoreCase("MD5-sess")) {
                        String cnonce = getCnonce();
                        String encode = encode(createMessageDigest.digest(EncodingUtils.getBytes(sb3, parameter6)));
                        StringBuilder sb4 = new StringBuilder(encode.length() + parameter3.length() + cnonce.length() + 2);
                        sb4.append(encode);
                        sb4.append(':');
                        sb4.append(parameter3);
                        sb4.append(':');
                        sb4.append(cnonce);
                        sb3 = sb4.toString();
                    } else if (!parameter5.equalsIgnoreCase("MD5")) {
                        throw new AuthenticationException("Unhandled algorithm " + parameter5 + " requested");
                    }
                    String encode2 = encode(createMessageDigest.digest(EncodingUtils.getBytes(sb3, parameter6)));
                    String str = null;
                    if (this.qopVariant != 1) {
                        str = parameter4 + ':' + parameter;
                    }
                    String encode3 = encode(createMessageDigest.digest(EncodingUtils.getAsciiBytes(str)));
                    if (this.qopVariant == 0) {
                        StringBuilder sb5 = new StringBuilder(encode2.length() + parameter3.length() + encode3.length());
                        sb5.append(encode2);
                        sb5.append(':');
                        sb5.append(parameter3);
                        sb5.append(':');
                        sb5.append(encode3);
                        sb = sb5.toString();
                    } else {
                        String qopVariantString = getQopVariantString();
                        String cnonce2 = getCnonce();
                        StringBuilder sb6 = new StringBuilder(encode2.length() + parameter3.length() + 8 + cnonce2.length() + qopVariantString.length() + encode3.length() + 5);
                        sb6.append(encode2);
                        sb6.append(':');
                        sb6.append(parameter3);
                        sb6.append(':');
                        sb6.append(NC);
                        sb6.append(':');
                        sb6.append(cnonce2);
                        sb6.append(':');
                        sb6.append(qopVariantString);
                        sb6.append(':');
                        sb6.append(encode3);
                        sb = sb6.toString();
                    }
                    return encode(createMessageDigest.digest(EncodingUtils.getAsciiBytes(sb)));
                }
                throw new AuthenticationException("Unsupported qop in HTTP Digest authentication");
            }
            throw new IllegalStateException("Nonce may not be null");
        }
        throw new IllegalStateException("URI may not be null");
    }

    private Header createDigestHeader(Credentials credentials, String str) throws AuthenticationException {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        if (isProxy()) {
            charArrayBuffer.append(AUTH.PROXY_AUTH_RESP);
        } else {
            charArrayBuffer.append(AUTH.WWW_AUTH_RESP);
        }
        charArrayBuffer.append(": Digest ");
        String parameter = getParameter("uri");
        String parameter2 = getParameter("realm");
        String parameter3 = getParameter(MDMConstants.MDM_NONCE);
        String parameter4 = getParameter("opaque");
        String parameter5 = getParameter("algorithm");
        String name = credentials.getUserPrincipal().getName();
        ArrayList arrayList = new ArrayList(20);
        arrayList.add(new BasicNameValuePair("username", name));
        arrayList.add(new BasicNameValuePair("realm", parameter2));
        arrayList.add(new BasicNameValuePair(MDMConstants.MDM_NONCE, parameter3));
        arrayList.add(new BasicNameValuePair("uri", parameter));
        arrayList.add(new BasicNameValuePair("response", str));
        if (this.qopVariant != 0) {
            arrayList.add(new BasicNameValuePair("qop", getQopVariantString()));
            arrayList.add(new BasicNameValuePair("nc", NC));
            arrayList.add(new BasicNameValuePair("cnonce", getCnonce()));
        }
        if (parameter5 != null) {
            arrayList.add(new BasicNameValuePair("algorithm", parameter5));
        }
        if (parameter4 != null) {
            arrayList.add(new BasicNameValuePair("opaque", parameter4));
        }
        for (int i = 0; i < arrayList.size(); i++) {
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair) arrayList.get(i);
            if (i > 0) {
                charArrayBuffer.append(", ");
            }
            BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(charArrayBuffer, basicNameValuePair, !("nc".equals(basicNameValuePair.getName()) || "qop".equals(basicNameValuePair.getName())));
        }
        return new BufferedHeader(charArrayBuffer);
    }

    private static MessageDigest createMessageDigest(String str) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(str);
        } catch (Exception e) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + str);
        }
    }

    private static String encode(byte[] bArr) {
        if (bArr.length != 16) {
            return null;
        }
        char[] cArr = new char[32];
        for (int i = 0; i < 16; i++) {
            int i2 = i * 2;
            char[] cArr2 = HEXADECIMAL;
            cArr[i2] = cArr2[(bArr[i] & 240) >> 4];
            cArr[i2 + 1] = cArr2[bArr[i] & 15];
        }
        return new String(cArr);
    }

    private String getCnonce() {
        if (this.cnonce == null) {
            this.cnonce = createCnonce();
        }
        return this.cnonce;
    }

    private String getQopVariantString() {
        return this.qopVariant == 1 ? "auth-int" : "auth";
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        if (credentials != null) {
            if (httpRequest != null) {
                getParameters().put("methodname", httpRequest.getRequestLine().getMethod());
                getParameters().put("uri", httpRequest.getRequestLine().getUri());
                if (getParameter("charset") == null) {
                    getParameters().put("charset", AuthParams.getCredentialCharset(httpRequest.getParams()));
                }
                return createDigestHeader(credentials, createDigest(credentials));
            }
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        throw new IllegalArgumentException("Credentials may not be null");
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public String getSchemeName() {
        return "digest";
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isComplete() {
        if ("true".equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    @Override // com.good.gd.apache.http.auth.AuthScheme
    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String str, String str2) {
        getParameters().put(str, str2);
    }

    @Override // com.good.gd.apache.http.impl.auth.AuthSchemeBase, com.good.gd.apache.http.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        if (getParameter("realm") != null) {
            if (getParameter(MDMConstants.MDM_NONCE) != null) {
                boolean z = false;
                String parameter = getParameter("qop");
                if (parameter != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(parameter, ",");
                    while (true) {
                        if (!stringTokenizer.hasMoreTokens()) {
                            break;
                        }
                        String trim = stringTokenizer.nextToken().trim();
                        if (trim.equals("auth")) {
                            this.qopVariant = 2;
                            break;
                        } else if (trim.equals("auth-int")) {
                            this.qopVariant = 1;
                        } else {
                            z = true;
                        }
                    }
                }
                if (z && this.qopVariant == 0) {
                    throw new MalformedChallengeException("None of the qop methods is supported");
                }
                this.cnonce = null;
                this.complete = true;
                return;
            }
            throw new MalformedChallengeException("missing nonce in challange");
        }
        throw new MalformedChallengeException("missing realm in challange");
    }
}
