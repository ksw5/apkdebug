package com.good.gd.utils;

/* loaded from: classes.dex */
public class GDNTLMHasher {
    public static native byte[] HMACMD5(byte[] bArr, byte[] bArr2);

    public static native byte[] createDESKey(byte[] bArr, int i);

    public static native byte[] digest(byte[] bArr, byte[] bArr2);

    public static native byte[] encryptTruncatedLMResponse(byte[] bArr, byte[] bArr2);

    public static native byte[] lmHash(byte[] bArr);

    public static native byte[] lmResponse(byte[] bArr, byte[] bArr2);

    public static native byte[] ntlmv2hmachash(byte[] bArr, byte[] bArr2, byte[] bArr3);
}
