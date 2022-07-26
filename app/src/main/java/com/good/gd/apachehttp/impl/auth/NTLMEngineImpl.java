package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apache.http.impl.auth.NTLMEngine;
import com.good.gd.apache.http.impl.auth.NTLMEngineException;
import com.good.gd.apachehttp.Consts;
import com.good.gd.apachehttp.impl.auth.codec.Base64;
import com.good.gd.apachehttp.util.CharsetUtils;
import com.good.gd.apachehttp.util.EncodingUtils;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDNTLMHasher;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class NTLMEngineImpl implements NTLMEngine {
    private static final SecureRandom jwxax;
    private static final byte[] wxau;
    private static final fdyxd ztwf;
    private static final Charset dbjc = CharsetUtils.lookup("UnicodeLittleUnmarked");
    private static final Charset qkduk = Consts.ASCII;

    /* loaded from: classes.dex */
    static class ehnkx extends yfdke {
        protected byte[] jwxax;
        protected int lqox;
        protected String wxau;
        protected byte[] ztwf;

        ehnkx(String str) throws NTLMEngineException {
            super(str, 2);
            GDLog.DBGPRINTF(16, ehnkx.class + "::Type2Message(message: " + str + ") IN");
            byte[] bArr = new byte[8];
            this.jwxax = bArr;
            dbjc(bArr, 24);
            int wxau = wxau(20);
            this.lqox = wxau;
            if ((wxau & 1) != 0) {
                this.wxau = null;
                if (dbjc() >= 20) {
                    byte[] jwxax = jwxax(12);
                    if (jwxax.length != 0) {
                        try {
                            this.wxau = new String(jwxax, "UnicodeLittleUnmarked");
                        } catch (UnsupportedEncodingException e) {
                            throw new NTLMEngineException(e.getMessage(), e);
                        }
                    }
                }
                this.ztwf = null;
                if (dbjc() < 48) {
                    return;
                }
                byte[] jwxax2 = jwxax(40);
                if (jwxax2.length == 0) {
                    return;
                }
                this.ztwf = jwxax2;
                return;
            }
            throw new NTLMEngineException("NTLM type 2 message indicates no support for Unicode. Flags are: " + Integer.toString(this.lqox));
        }
    }

    /* loaded from: classes.dex */
    static class fdyxd extends yfdke {
        private final byte[] jwxax = null;
        private final byte[] wxau = null;

        fdyxd() {
        }

        @Override // com.good.gd.apachehttp.impl.auth.NTLMEngineImpl.yfdke
        String qkduk() {
            dbjc(40, 1);
            dbjc(-1576500735);
            qkduk(0);
            qkduk(0);
            dbjc(40);
            qkduk(0);
            qkduk(0);
            dbjc(40);
            qkduk(261);
            dbjc(2600);
            qkduk(3840);
            byte[] bArr = this.jwxax;
            if (bArr != null) {
                dbjc(bArr);
            }
            byte[] bArr2 = this.wxau;
            if (bArr2 != null) {
                dbjc(bArr2);
            }
            return super.qkduk();
        }
    }

    /* loaded from: classes.dex */
    static class pmoiy extends yfdke {
        protected byte[] jcpqe;
        protected int jwxax;
        protected byte[] liflu;
        protected byte[] lqox;
        protected byte[] tlske;
        protected byte[] wxau;
        protected byte[] ztwf;

        pmoiy(String str, String str2, String str3, String str4, byte[] bArr, int i, String str5, byte[] bArr2) throws NTLMEngineException {
            byte[] lMUserSessionKey;
            GDLog.DBGPRINTF(16, pmoiy.class + "::Type3Message(domain: " + str + ", host: " + str2 + ", user: " + str3 + ", type2Flags: " + i + ", target: " + str5 + ") IN");
            this.jwxax = i;
            String ztwf = NTLMEngineImpl.ztwf(str2);
            String ztwf2 = NTLMEngineImpl.ztwf(str);
            CipherGen cipherGen = new CipherGen(ztwf2, str3, str4, bArr, str5, bArr2);
            try {
                if ((8388608 & i) != 0 && bArr2 != null && str5 != null) {
                    this.jcpqe = cipherGen.getNTLMv2Response();
                    this.liflu = cipherGen.getLMv2Response();
                    if ((i & 128) != 0) {
                        lMUserSessionKey = cipherGen.getLanManagerSessionKey();
                    } else {
                        lMUserSessionKey = cipherGen.getNTLMv2UserSessionKey();
                    }
                } else if ((524288 & i) != 0) {
                    this.jcpqe = cipherGen.getNTLM2SessionResponse();
                    this.liflu = cipherGen.getLM2SessionResponse();
                    if ((i & 128) != 0) {
                        lMUserSessionKey = cipherGen.getLanManagerSessionKey();
                    } else {
                        lMUserSessionKey = cipherGen.getNTLM2SessionResponseUserSessionKey();
                    }
                } else {
                    this.jcpqe = cipherGen.getNTLMResponse();
                    this.liflu = cipherGen.getLMResponse();
                    if ((i & 128) != 0) {
                        lMUserSessionKey = cipherGen.getLanManagerSessionKey();
                    } else {
                        lMUserSessionKey = cipherGen.getNTLMUserSessionKey();
                    }
                }
            } catch (NTLMEngineException e) {
                this.jcpqe = new byte[0];
                this.liflu = cipherGen.getLMResponse();
                if ((i & 128) != 0) {
                    lMUserSessionKey = cipherGen.getLanManagerSessionKey();
                } else {
                    lMUserSessionKey = cipherGen.getLMUserSessionKey();
                }
            }
            byte[] bArr3 = null;
            if ((i & 16) == 0) {
                this.tlske = null;
            } else if ((1073741824 & i) != 0) {
                this.tlske = NTLMEngineImpl.dbjc(cipherGen.getSecondaryKey(), lMUserSessionKey);
            } else {
                this.tlske = lMUserSessionKey;
            }
            if (NTLMEngineImpl.dbjc != null) {
                this.ztwf = ztwf != null ? ztwf.getBytes(NTLMEngineImpl.dbjc) : null;
                this.wxau = ztwf2 != null ? ztwf2.toUpperCase(Locale.ROOT).getBytes(NTLMEngineImpl.dbjc) : bArr3;
                this.lqox = str3.getBytes(NTLMEngineImpl.dbjc);
                return;
            }
            throw new NTLMEngineException("Unicode not supported");
        }

        @Override // com.good.gd.apachehttp.impl.auth.NTLMEngineImpl.yfdke
        String qkduk() {
            int length = this.jcpqe.length;
            int length2 = this.liflu.length;
            byte[] bArr = this.wxau;
            int i = 0;
            int length3 = bArr != null ? bArr.length : 0;
            byte[] bArr2 = this.ztwf;
            int length4 = bArr2 != null ? bArr2.length : 0;
            int length5 = this.lqox.length;
            byte[] bArr3 = this.tlske;
            if (bArr3 != null) {
                i = bArr3.length;
            }
            int i2 = length2 + 72;
            int i3 = i2 + length;
            int i4 = i3 + length3;
            int i5 = i4 + length5;
            int i6 = i5 + length4;
            dbjc(i6 + i, 3);
            qkduk(length2);
            qkduk(length2);
            dbjc(72);
            qkduk(length);
            qkduk(length);
            dbjc(i2);
            qkduk(length3);
            qkduk(length3);
            dbjc(i3);
            qkduk(length5);
            qkduk(length5);
            dbjc(i4);
            qkduk(length4);
            qkduk(length4);
            dbjc(i5);
            qkduk(i);
            qkduk(i);
            dbjc(i6);
            int i7 = this.jwxax;
            dbjc((i7 & 4) | (i7 & 128) | (i7 & 512) | (524288 & i7) | 33554432 | (32768 & i7) | (i7 & 32) | (i7 & 16) | (536870912 & i7) | (Integer.MIN_VALUE & i7) | (1073741824 & i7) | (8388608 & i7) | (i7 & 1));
            qkduk(261);
            dbjc(2600);
            qkduk(3840);
            dbjc(this.liflu);
            dbjc(this.jcpqe);
            dbjc(this.wxau);
            dbjc(this.lqox);
            dbjc(this.ztwf);
            byte[] bArr4 = this.tlske;
            if (bArr4 != null) {
                dbjc(bArr4);
            }
            return super.qkduk();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class yfdke {
        private byte[] dbjc;
        private int qkduk;

        yfdke() {
            this.dbjc = null;
            this.qkduk = 0;
        }

        protected int dbjc() {
            return this.qkduk;
        }

        protected byte[] jwxax(int i) throws NTLMEngineException {
            return NTLMEngineImpl.qkduk(this.dbjc, i);
        }

        protected void qkduk(int i) {
            dbjc((byte) (i & 255));
            dbjc((byte) ((i >> 8) & 255));
        }

        protected int wxau(int i) throws NTLMEngineException {
            return NTLMEngineImpl.jwxax(this.dbjc, i);
        }

        protected void dbjc(byte[] bArr, int i) throws NTLMEngineException {
            byte[] bArr2 = this.dbjc;
            if (bArr2.length >= bArr.length + i) {
                System.arraycopy(bArr2, i, bArr, 0, bArr.length);
                return;
            }
            throw new NTLMEngineException("NTLM: Message too short");
        }

        String qkduk() {
            byte[] bArr = this.dbjc;
            int length = bArr.length;
            int i = this.qkduk;
            if (length > i) {
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
            return EncodingUtils.getAsciiString(Base64.encodeBase64(bArr));
        }

        yfdke(String str, int i) throws NTLMEngineException {
            this.dbjc = null;
            this.qkduk = 0;
            byte[] decodeBase64 = Base64.decodeBase64(str.getBytes(NTLMEngineImpl.qkduk));
            this.dbjc = decodeBase64;
            if (decodeBase64.length >= NTLMEngineImpl.wxau.length) {
                for (int i2 = 0; i2 < NTLMEngineImpl.wxau.length; i2++) {
                    if (this.dbjc[i2] != NTLMEngineImpl.wxau[i2]) {
                        throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                    }
                }
                int wxau = wxau(NTLMEngineImpl.wxau.length);
                if (wxau == i) {
                    this.qkduk = this.dbjc.length;
                    return;
                }
                throw new NTLMEngineException("NTLM type " + Integer.toString(i) + " message expected - instead got type " + Integer.toString(wxau));
            }
            throw new NTLMEngineException("NTLM message decoding error - packet too short");
        }

        protected void dbjc(int i, int i2) {
            this.dbjc = new byte[i];
            this.qkduk = 0;
            dbjc(NTLMEngineImpl.wxau);
            dbjc(i2);
        }

        protected void dbjc(byte b) {
            byte[] bArr = this.dbjc;
            int i = this.qkduk;
            bArr[i] = b;
            this.qkduk = i + 1;
        }

        protected void dbjc(byte[] bArr) {
            if (bArr == null) {
                return;
            }
            for (byte b : bArr) {
                byte[] bArr2 = this.dbjc;
                int i = this.qkduk;
                bArr2[i] = b;
                this.qkduk = i + 1;
            }
        }

        protected void dbjc(int i) {
            dbjc((byte) (i & 255));
            dbjc((byte) ((i >> 8) & 255));
            dbjc((byte) ((i >> 16) & 255));
            dbjc((byte) ((i >> 24) & 255));
        }
    }

    static {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            secureRandom = null;
        }
        jwxax = secureRandom;
        byte[] bytes = "NTLMSSP".getBytes(Consts.ASCII);
        byte[] bArr = new byte[bytes.length + 1];
        wxau = bArr;
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        bArr[bytes.length] = 0;
        ztwf = new fdyxd();
    }

    static int dbjc(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    static int dbjc(int i, int i2, int i3) {
        return ((~i) & i3) | (i2 & i);
    }

    static int jwxax(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] liflu() throws NTLMEngineException {
        SecureRandom secureRandom = jwxax;
        if (secureRandom != null) {
            byte[] bArr = new byte[16];
            synchronized (secureRandom) {
                secureRandom.nextBytes(bArr);
            }
            return bArr;
        }
        throw new NTLMEngineException("Random generator not available");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] lqox() throws NTLMEngineException {
        SecureRandom secureRandom = jwxax;
        if (secureRandom != null) {
            byte[] bArr = new byte[8];
            synchronized (secureRandom) {
                secureRandom.nextBytes(bArr);
            }
            return bArr;
        }
        throw new NTLMEngineException("Random generator not available");
    }

    static int qkduk(int i, int i2, int i3) {
        return (i & i3) | (i & i2) | (i2 & i3);
    }

    @Override // com.good.gd.apache.http.impl.auth.NTLMEngine
    public String generateType1Msg(String str, String str2) throws NTLMEngineException {
        return ztwf.qkduk();
    }

    @Override // com.good.gd.apache.http.impl.auth.NTLMEngine
    public String generateType3Msg(String str, String str2, String str3, String str4, String str5) throws NTLMEngineException {
        ehnkx ehnkxVar = new ehnkx(str5);
        return new pmoiy(str3, str4, str, str2, ehnkxVar.jwxax, ehnkxVar.lqox, ehnkxVar.wxau, ehnkxVar.ztwf).qkduk();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int jwxax(byte[] bArr, int i) throws NTLMEngineException {
        if (bArr.length >= i + 4) {
            return ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i + 2] & UByte.MAX_VALUE) << 16);
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
    }

    static /* synthetic */ byte[] wxau(String str) throws NTLMEngineException {
        Charset charset = dbjc;
        if (charset != null) {
            byte[] bytes = str.getBytes(charset);
            hbfhc hbfhcVar = new hbfhc();
            hbfhcVar.dbjc(bytes);
            return hbfhcVar.dbjc();
        }
        throw new NTLMEngineException("Unicode not supported");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String ztwf(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(".");
        return indexOf != -1 ? str.substring(0, indexOf) : str;
    }

    static byte[] dbjc(byte[] bArr, byte[] bArr2) throws NTLMEngineException {
        try {
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(1, new SecretKeySpec(bArr2, "RC4"));
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    static /* synthetic */ byte[] qkduk(byte[] bArr, int i) throws NTLMEngineException {
        if (bArr.length >= i + 2) {
            int i2 = (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8);
            int jwxax2 = jwxax(bArr, i + 4);
            if (bArr.length >= jwxax2 + i2) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, jwxax2, bArr2, 0, i2);
                return bArr2;
            }
            throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
    }

    static byte[] jwxax(byte[] bArr, byte[] bArr2) throws NTLMEngineException {
        return GDNTLMHasher.HMACMD5(bArr2, bArr);
    }

    static byte[] jwxax(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NTLMEngineException {
        try {
            byte[] bArr4 = new byte[8];
            System.arraycopy(GDNTLMHasher.digest(bArr2, bArr3), 0, bArr4, 0, 8);
            return wxau(bArr, bArr4);
        } catch (Exception e) {
            if (e instanceof NTLMEngineException) {
                throw ((NTLMEngineException) e);
            }
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    static /* synthetic */ byte[] dbjc(String str, String str2, byte[] bArr) throws NTLMEngineException {
        Charset charset = dbjc;
        if (charset != null) {
            try {
                byte[] ntlmv2hmachash = GDNTLMHasher.ntlmv2hmachash(bArr, str2.toUpperCase(Locale.ROOT).getBytes(charset), str.getBytes(charset));
                if (ntlmv2hmachash != null) {
                    return ntlmv2hmachash;
                }
                throw new NTLMEngineException("NTLMv2 hash generation failed");
            } catch (Exception e) {
                throw new NTLMEngineException(e.getMessage(), e);
            }
        }
        throw new NTLMEngineException("Unicode not supported");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] wxau(byte[] bArr, byte[] bArr2) throws NTLMEngineException {
        if (bArr.length == 16) {
            if (bArr2.length == 8) {
                byte[] lmResponse = GDNTLMHasher.lmResponse(bArr, bArr2);
                if (lmResponse == null) {
                    throw new NTLMEngineException("Problem with lmResponse");
                }
                return lmResponse;
            }
            throw new NTLMEngineException("lmResponse challenge length should be 8 - length is " + bArr2.length);
        }
        throw new NTLMEngineException("lmResponse hash length should be 16 - length is " + bArr.length);
    }

    static /* synthetic */ byte[] qkduk(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NTLMEngineException {
        byte[] ntlmv2hmachash = GDNTLMHasher.ntlmv2hmachash(bArr, bArr2, bArr3);
        byte[] bArr4 = new byte[ntlmv2hmachash.length + bArr3.length];
        System.arraycopy(ntlmv2hmachash, 0, bArr4, 0, ntlmv2hmachash.length);
        System.arraycopy(bArr3, 0, bArr4, ntlmv2hmachash.length, bArr3.length);
        return bArr4;
    }

    static /* synthetic */ byte[] jwxax(String str) throws NTLMEngineException {
        try {
            byte[] bytes = str.toUpperCase(Locale.ROOT).getBytes(Consts.ASCII);
            byte[] bArr = new byte[14];
            System.arraycopy(bytes, 0, bArr, 0, Math.min(bytes.length, 14));
            byte[] lmHash = GDNTLMHasher.lmHash(bArr);
            if (lmHash != null) {
                return lmHash;
            }
            throw new NTLMEngineException("LM hash generation failed");
        } catch (Exception e) {
            throw new NTLMEngineException(e.getMessage(), e);
        }
    }

    static /* synthetic */ byte[] dbjc(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr3.length + 8 + 8 + 4 + bArr2.length + 4];
        System.arraycopy(new byte[]{1, 1, 0, 0}, 0, bArr4, 0, 4);
        System.arraycopy(new byte[]{0, 0, 0, 0}, 0, bArr4, 4, 4);
        System.arraycopy(bArr3, 0, bArr4, 8, bArr3.length);
        int length = bArr3.length + 8;
        System.arraycopy(bArr, 0, bArr4, length, 8);
        int i = length + 8;
        System.arraycopy(new byte[]{0, 0, 0, 0}, 0, bArr4, i, 4);
        int i2 = i + 4;
        System.arraycopy(bArr2, 0, bArr4, i2, bArr2.length);
        System.arraycopy(new byte[]{0, 0, 0, 0}, 0, bArr4, i2 + bArr2.length, 4);
        return bArr4;
    }

    /* loaded from: classes.dex */
    protected static class CipherGen {
        protected final byte[] challenge;
        protected byte[] clientChallenge;
        protected byte[] clientChallenge2;
        protected final String domain;
        protected byte[] lanManagerSessionKey;
        protected byte[] lm2SessionResponse;
        protected byte[] lmHash;
        protected byte[] lmResponse;
        protected byte[] lmUserSessionKey;
        protected byte[] lmv2Hash;
        protected byte[] lmv2Response;
        protected byte[] ntlm2SessionResponse;
        protected byte[] ntlm2SessionResponseUserSessionKey;
        protected byte[] ntlmHash;
        protected byte[] ntlmResponse;
        protected byte[] ntlmUserSessionKey;
        protected byte[] ntlmv2Blob;
        protected byte[] ntlmv2Hash;
        protected byte[] ntlmv2Response;
        protected byte[] ntlmv2UserSessionKey;
        protected final String password;
        protected byte[] secondaryKey;
        protected final String target;
        protected final byte[] targetInformation;
        protected byte[] timestamp;
        protected final String user;

        public CipherGen(String str, String str2, String str3, byte[] bArr, String str4, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6) {
            this.lmHash = null;
            this.lmResponse = null;
            this.ntlmHash = null;
            this.ntlmResponse = null;
            this.ntlmv2Hash = null;
            this.lmv2Hash = null;
            this.lmv2Response = null;
            this.ntlmv2Blob = null;
            this.ntlmv2Response = null;
            this.ntlm2SessionResponse = null;
            this.lm2SessionResponse = null;
            this.lmUserSessionKey = null;
            this.ntlmUserSessionKey = null;
            this.ntlmv2UserSessionKey = null;
            this.ntlm2SessionResponseUserSessionKey = null;
            this.lanManagerSessionKey = null;
            this.domain = str;
            this.target = str4;
            this.user = str2;
            this.password = str3;
            this.challenge = bArr;
            this.targetInformation = bArr2;
            this.clientChallenge = bArr3;
            this.clientChallenge2 = bArr4;
            this.secondaryKey = bArr5;
            this.timestamp = bArr6;
        }

        public byte[] getClientChallenge() throws NTLMEngineException {
            if (this.clientChallenge == null) {
                this.clientChallenge = NTLMEngineImpl.lqox();
            }
            return this.clientChallenge;
        }

        public byte[] getClientChallenge2() throws NTLMEngineException {
            if (this.clientChallenge2 == null) {
                this.clientChallenge2 = NTLMEngineImpl.lqox();
            }
            return this.clientChallenge2;
        }

        public byte[] getLM2SessionResponse() throws NTLMEngineException {
            if (this.lm2SessionResponse == null) {
                byte[] clientChallenge = getClientChallenge();
                byte[] bArr = new byte[24];
                this.lm2SessionResponse = bArr;
                System.arraycopy(clientChallenge, 0, bArr, 0, clientChallenge.length);
                byte[] bArr2 = this.lm2SessionResponse;
                Arrays.fill(bArr2, clientChallenge.length, bArr2.length, (byte) 0);
            }
            return this.lm2SessionResponse;
        }

        public byte[] getLMHash() throws NTLMEngineException {
            if (this.lmHash == null) {
                this.lmHash = NTLMEngineImpl.jwxax(this.password);
            }
            return this.lmHash;
        }

        public byte[] getLMResponse() throws NTLMEngineException {
            if (this.lmResponse == null) {
                this.lmResponse = NTLMEngineImpl.wxau(getLMHash(), this.challenge);
            }
            return this.lmResponse;
        }

        public byte[] getLMUserSessionKey() throws NTLMEngineException {
            if (this.lmUserSessionKey == null) {
                this.lmUserSessionKey = new byte[16];
                System.arraycopy(getLMHash(), 0, this.lmUserSessionKey, 0, 8);
                Arrays.fill(this.lmUserSessionKey, 8, 16, (byte) 0);
            }
            return this.lmUserSessionKey;
        }

        public byte[] getLMv2Response() throws NTLMEngineException {
            if (this.lmv2Response == null) {
                this.lmv2Response = NTLMEngineImpl.qkduk(NTLMEngineImpl.dbjc(this.domain, this.user, getNTLMHash()), this.challenge, getClientChallenge());
            }
            return this.lmv2Response;
        }

        public byte[] getLanManagerSessionKey() throws NTLMEngineException {
            if (this.lanManagerSessionKey == null) {
                try {
                    byte[] bArr = new byte[14];
                    System.arraycopy(getLMHash(), 0, bArr, 0, 8);
                    Arrays.fill(bArr, 8, 14, (byte) -67);
                    byte[] createDESKey = GDNTLMHasher.createDESKey(bArr, 0);
                    byte[] createDESKey2 = GDNTLMHasher.createDESKey(bArr, 7);
                    byte[] bArr2 = new byte[8];
                    System.arraycopy(getLMResponse(), 0, bArr2, 0, 8);
                    byte[] encryptTruncatedLMResponse = GDNTLMHasher.encryptTruncatedLMResponse(createDESKey, bArr2);
                    byte[] encryptTruncatedLMResponse2 = GDNTLMHasher.encryptTruncatedLMResponse(createDESKey2, bArr2);
                    byte[] bArr3 = new byte[16];
                    this.lanManagerSessionKey = bArr3;
                    System.arraycopy(encryptTruncatedLMResponse, 0, bArr3, 0, encryptTruncatedLMResponse.length);
                    System.arraycopy(encryptTruncatedLMResponse2, 0, this.lanManagerSessionKey, encryptTruncatedLMResponse.length, encryptTruncatedLMResponse2.length);
                } catch (Exception e) {
                    throw new NTLMEngineException(e.getMessage(), e);
                }
            }
            return this.lanManagerSessionKey;
        }

        public byte[] getNTLM2SessionResponse() throws NTLMEngineException {
            if (this.ntlm2SessionResponse == null) {
                this.ntlm2SessionResponse = NTLMEngineImpl.jwxax(getNTLMHash(), this.challenge, getClientChallenge());
            }
            return this.ntlm2SessionResponse;
        }

        public byte[] getNTLM2SessionResponseUserSessionKey() throws NTLMEngineException {
            if (this.ntlm2SessionResponseUserSessionKey == null) {
                byte[] lM2SessionResponse = getLM2SessionResponse();
                byte[] bArr = this.challenge;
                byte[] bArr2 = new byte[bArr.length + lM2SessionResponse.length];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                System.arraycopy(lM2SessionResponse, 0, bArr2, this.challenge.length, lM2SessionResponse.length);
                this.ntlm2SessionResponseUserSessionKey = NTLMEngineImpl.jwxax(bArr2, getNTLMUserSessionKey());
            }
            return this.ntlm2SessionResponseUserSessionKey;
        }

        public byte[] getNTLMHash() throws NTLMEngineException {
            if (this.ntlmHash == null) {
                this.ntlmHash = NTLMEngineImpl.wxau(this.password);
            }
            return this.ntlmHash;
        }

        public byte[] getNTLMResponse() throws NTLMEngineException {
            if (this.ntlmResponse == null) {
                this.ntlmResponse = NTLMEngineImpl.wxau(getNTLMHash(), this.challenge);
            }
            return this.ntlmResponse;
        }

        public byte[] getNTLMUserSessionKey() throws NTLMEngineException {
            if (this.ntlmUserSessionKey == null) {
                hbfhc hbfhcVar = new hbfhc();
                hbfhcVar.dbjc(getNTLMHash());
                this.ntlmUserSessionKey = hbfhcVar.dbjc();
            }
            return this.ntlmUserSessionKey;
        }

        public byte[] getNTLMv2Blob() throws NTLMEngineException {
            if (this.ntlmv2Blob == null) {
                this.ntlmv2Blob = NTLMEngineImpl.dbjc(getClientChallenge2(), this.targetInformation, getTimestamp());
            }
            return this.ntlmv2Blob;
        }

        public byte[] getNTLMv2Hash() throws NTLMEngineException {
            if (this.ntlmv2Hash == null) {
                this.ntlmv2Hash = NTLMEngineImpl.dbjc(this.domain, this.user, getNTLMHash());
            }
            return this.ntlmv2Hash;
        }

        public byte[] getNTLMv2Response() throws NTLMEngineException {
            if (this.ntlmv2Response == null) {
                this.ntlmv2Response = NTLMEngineImpl.qkduk(getNTLMv2Hash(), this.challenge, getNTLMv2Blob());
            }
            return this.ntlmv2Response;
        }

        public byte[] getNTLMv2UserSessionKey() throws NTLMEngineException {
            if (this.ntlmv2UserSessionKey == null) {
                byte[] nTLMv2Hash = getNTLMv2Hash();
                byte[] bArr = new byte[16];
                System.arraycopy(getNTLMv2Response(), 0, bArr, 0, 16);
                this.ntlmv2UserSessionKey = NTLMEngineImpl.jwxax(bArr, nTLMv2Hash);
            }
            return this.ntlmv2UserSessionKey;
        }

        public byte[] getSecondaryKey() throws NTLMEngineException {
            if (this.secondaryKey == null) {
                this.secondaryKey = NTLMEngineImpl.liflu();
            }
            return this.secondaryKey;
        }

        public byte[] getTimestamp() {
            if (this.timestamp == null) {
                long currentTimeMillis = (System.currentTimeMillis() + 11644473600000L) * 10000;
                this.timestamp = new byte[8];
                for (int i = 0; i < 8; i++) {
                    this.timestamp[i] = (byte) currentTimeMillis;
                    currentTimeMillis >>>= 8;
                }
            }
            return this.timestamp;
        }

        public CipherGen(String str, String str2, String str3, byte[] bArr, String str4, byte[] bArr2) {
            this(str, str2, str3, bArr, str4, bArr2, null, null, null, null);
        }
    }

    static void dbjc(byte[] bArr, int i, int i2) {
        bArr[i2] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((i >> 8) & 255);
        bArr[i2 + 2] = (byte) ((i >> 16) & 255);
        bArr[i2 + 3] = (byte) ((i >> 24) & 255);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc {
        protected int dbjc = 1732584193;
        protected int qkduk = -271733879;
        protected int jwxax = -1732584194;
        protected int wxau = 271733878;
        protected long ztwf = 0;
        protected byte[] lqox = new byte[64];

        hbfhc() {
        }

        void dbjc(byte[] bArr) {
            byte[] bArr2;
            int i = (int) (this.ztwf & 63);
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int length = (bArr.length - i3) + i;
                bArr2 = this.lqox;
                if (length < bArr2.length) {
                    break;
                }
                int length2 = bArr2.length - i;
                System.arraycopy(bArr, i3, bArr2, i, length2);
                this.ztwf += length2;
                i3 += length2;
                int[] iArr = new int[16];
                for (int i4 = i2; i4 < 16; i4++) {
                    byte[] bArr3 = this.lqox;
                    int i5 = i4 * 4;
                    iArr[i4] = (bArr3[i5] & UByte.MAX_VALUE) + ((bArr3[i5 + 1] & UByte.MAX_VALUE) << 8) + ((bArr3[i5 + 2] & UByte.MAX_VALUE) << 16) + ((bArr3[i5 + 3] & UByte.MAX_VALUE) << 24);
                }
                int i6 = this.dbjc;
                int i7 = this.qkduk;
                int i8 = this.jwxax;
                int i9 = this.wxau;
                int dbjc = NTLMEngineImpl.dbjc(NTLMEngineImpl.dbjc(i7, i8, i9) + i6 + iArr[i2], 3);
                this.dbjc = dbjc;
                int dbjc2 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.dbjc(dbjc, this.qkduk, this.jwxax) + iArr[1], 7);
                this.wxau = dbjc2;
                int dbjc3 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.dbjc(dbjc2, this.dbjc, this.qkduk) + iArr[2], 11);
                this.jwxax = dbjc3;
                int dbjc4 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.dbjc(dbjc3, this.wxau, this.dbjc) + iArr[3], 19);
                this.qkduk = dbjc4;
                int dbjc5 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.dbjc(dbjc4, this.jwxax, this.wxau) + iArr[4], 3);
                this.dbjc = dbjc5;
                int dbjc6 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.dbjc(dbjc5, this.qkduk, this.jwxax) + iArr[5], 7);
                this.wxau = dbjc6;
                int dbjc7 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.dbjc(dbjc6, this.dbjc, this.qkduk) + iArr[6], 11);
                this.jwxax = dbjc7;
                int dbjc8 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.dbjc(dbjc7, this.wxau, this.dbjc) + iArr[7], 19);
                this.qkduk = dbjc8;
                int dbjc9 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.dbjc(dbjc8, this.jwxax, this.wxau) + iArr[8], 3);
                this.dbjc = dbjc9;
                int dbjc10 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.dbjc(dbjc9, this.qkduk, this.jwxax) + iArr[9], 7);
                this.wxau = dbjc10;
                int dbjc11 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.dbjc(dbjc10, this.dbjc, this.qkduk) + iArr[10], 11);
                this.jwxax = dbjc11;
                int dbjc12 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.dbjc(dbjc11, this.wxau, this.dbjc) + iArr[11], 19);
                this.qkduk = dbjc12;
                int dbjc13 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.dbjc(dbjc12, this.jwxax, this.wxau) + iArr[12], 3);
                this.dbjc = dbjc13;
                int dbjc14 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.dbjc(dbjc13, this.qkduk, this.jwxax) + iArr[13], 7);
                this.wxau = dbjc14;
                int dbjc15 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.dbjc(dbjc14, this.dbjc, this.qkduk) + iArr[14], 11);
                this.jwxax = dbjc15;
                int dbjc16 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.dbjc(dbjc15, this.wxau, this.dbjc) + iArr[15], 19);
                this.qkduk = dbjc16;
                int dbjc17 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.qkduk(dbjc16, this.jwxax, this.wxau) + iArr[0] + 1518500249, 3);
                this.dbjc = dbjc17;
                int dbjc18 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.qkduk(dbjc17, this.qkduk, this.jwxax) + iArr[4] + 1518500249, 5);
                this.wxau = dbjc18;
                int dbjc19 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.qkduk(dbjc18, this.dbjc, this.qkduk) + iArr[8] + 1518500249, 9);
                this.jwxax = dbjc19;
                int dbjc20 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.qkduk(dbjc19, this.wxau, this.dbjc) + iArr[12] + 1518500249, 13);
                this.qkduk = dbjc20;
                int dbjc21 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.qkduk(dbjc20, this.jwxax, this.wxau) + iArr[1] + 1518500249, 3);
                this.dbjc = dbjc21;
                int dbjc22 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.qkduk(dbjc21, this.qkduk, this.jwxax) + iArr[5] + 1518500249, 5);
                this.wxau = dbjc22;
                int dbjc23 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.qkduk(dbjc22, this.dbjc, this.qkduk) + iArr[9] + 1518500249, 9);
                this.jwxax = dbjc23;
                int dbjc24 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.qkduk(dbjc23, this.wxau, this.dbjc) + iArr[13] + 1518500249, 13);
                this.qkduk = dbjc24;
                int dbjc25 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.qkduk(dbjc24, this.jwxax, this.wxau) + iArr[2] + 1518500249, 3);
                this.dbjc = dbjc25;
                int dbjc26 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.qkduk(dbjc25, this.qkduk, this.jwxax) + iArr[6] + 1518500249, 5);
                this.wxau = dbjc26;
                int dbjc27 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.qkduk(dbjc26, this.dbjc, this.qkduk) + iArr[10] + 1518500249, 9);
                this.jwxax = dbjc27;
                int dbjc28 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.qkduk(dbjc27, this.wxau, this.dbjc) + iArr[14] + 1518500249, 13);
                this.qkduk = dbjc28;
                int dbjc29 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.qkduk(dbjc28, this.jwxax, this.wxau) + iArr[3] + 1518500249, 3);
                this.dbjc = dbjc29;
                int dbjc30 = NTLMEngineImpl.dbjc(this.wxau + NTLMEngineImpl.qkduk(dbjc29, this.qkduk, this.jwxax) + iArr[7] + 1518500249, 5);
                this.wxau = dbjc30;
                int dbjc31 = NTLMEngineImpl.dbjc(this.jwxax + NTLMEngineImpl.qkduk(dbjc30, this.dbjc, this.qkduk) + iArr[11] + 1518500249, 9);
                this.jwxax = dbjc31;
                int dbjc32 = NTLMEngineImpl.dbjc(this.qkduk + NTLMEngineImpl.qkduk(dbjc31, this.wxau, this.dbjc) + iArr[15] + 1518500249, 13);
                this.qkduk = dbjc32;
                int dbjc33 = NTLMEngineImpl.dbjc(this.dbjc + NTLMEngineImpl.jwxax(dbjc32, this.jwxax, this.wxau) + iArr[0] + 1859775393, 3);
                this.dbjc = dbjc33;
                int dbjc34 = NTLMEngineImpl.dbjc(this.wxau + (this.jwxax ^ (dbjc33 ^ this.qkduk)) + iArr[8] + 1859775393, 9);
                this.wxau = dbjc34;
                int dbjc35 = NTLMEngineImpl.dbjc(this.jwxax + ((dbjc34 ^ this.dbjc) ^ this.qkduk) + iArr[4] + 1859775393, 11);
                this.jwxax = dbjc35;
                int dbjc36 = NTLMEngineImpl.dbjc(this.qkduk + ((dbjc35 ^ this.wxau) ^ this.dbjc) + iArr[12] + 1859775393, 15);
                this.qkduk = dbjc36;
                int dbjc37 = NTLMEngineImpl.dbjc(this.dbjc + ((dbjc36 ^ this.jwxax) ^ this.wxau) + iArr[2] + 1859775393, 3);
                this.dbjc = dbjc37;
                int dbjc38 = NTLMEngineImpl.dbjc(this.wxau + ((dbjc37 ^ this.qkduk) ^ this.jwxax) + iArr[10] + 1859775393, 9);
                this.wxau = dbjc38;
                int dbjc39 = NTLMEngineImpl.dbjc(this.jwxax + ((dbjc38 ^ this.dbjc) ^ this.qkduk) + iArr[6] + 1859775393, 11);
                this.jwxax = dbjc39;
                int dbjc40 = NTLMEngineImpl.dbjc(this.qkduk + ((dbjc39 ^ this.wxau) ^ this.dbjc) + iArr[14] + 1859775393, 15);
                this.qkduk = dbjc40;
                int dbjc41 = NTLMEngineImpl.dbjc(this.dbjc + ((dbjc40 ^ this.jwxax) ^ this.wxau) + iArr[1] + 1859775393, 3);
                this.dbjc = dbjc41;
                int dbjc42 = NTLMEngineImpl.dbjc(this.wxau + ((dbjc41 ^ this.qkduk) ^ this.jwxax) + iArr[9] + 1859775393, 9);
                this.wxau = dbjc42;
                int dbjc43 = NTLMEngineImpl.dbjc(this.jwxax + ((dbjc42 ^ this.dbjc) ^ this.qkduk) + iArr[5] + 1859775393, 11);
                this.jwxax = dbjc43;
                int dbjc44 = NTLMEngineImpl.dbjc(this.qkduk + ((dbjc43 ^ this.wxau) ^ this.dbjc) + iArr[13] + 1859775393, 15);
                this.qkduk = dbjc44;
                int dbjc45 = NTLMEngineImpl.dbjc(this.dbjc + ((dbjc44 ^ this.jwxax) ^ this.wxau) + iArr[3] + 1859775393, 3);
                this.dbjc = dbjc45;
                int dbjc46 = NTLMEngineImpl.dbjc(this.wxau + ((dbjc45 ^ this.qkduk) ^ this.jwxax) + iArr[11] + 1859775393, 9);
                this.wxau = dbjc46;
                int dbjc47 = NTLMEngineImpl.dbjc(this.jwxax + ((dbjc46 ^ this.dbjc) ^ this.qkduk) + iArr[7] + 1859775393, 11);
                this.jwxax = dbjc47;
                int dbjc48 = NTLMEngineImpl.dbjc(this.qkduk + ((dbjc47 ^ this.wxau) ^ this.dbjc) + iArr[15] + 1859775393, 15);
                this.qkduk = dbjc48;
                this.dbjc += i6;
                this.qkduk = dbjc48 + i7;
                this.jwxax += i8;
                this.wxau += i9;
                i = 0;
                i2 = 0;
            }
            if (i3 < bArr.length) {
                int length3 = bArr.length - i3;
                System.arraycopy(bArr, i3, bArr2, i, length3);
                this.ztwf += length3;
            }
        }

        byte[] dbjc() {
            int i = (int) (this.ztwf & 63);
            int i2 = i < 56 ? 56 - i : 120 - i;
            byte[] bArr = new byte[i2 + 8];
            bArr[0] = ByteCompanionObject.MIN_VALUE;
            for (int i3 = 0; i3 < 8; i3++) {
                bArr[i2 + i3] = (byte) ((this.ztwf * 8) >>> (i3 * 8));
            }
            dbjc(bArr);
            byte[] bArr2 = new byte[16];
            NTLMEngineImpl.dbjc(bArr2, this.dbjc, 0);
            NTLMEngineImpl.dbjc(bArr2, this.qkduk, 4);
            NTLMEngineImpl.dbjc(bArr2, this.jwxax, 8);
            NTLMEngineImpl.dbjc(bArr2, this.wxau, 12);
            return bArr2;
        }
    }
}
