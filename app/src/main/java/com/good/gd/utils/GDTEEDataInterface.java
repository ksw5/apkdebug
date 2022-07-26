package com.good.gd.utils;

import java.security.cert.Certificate;

/* loaded from: classes.dex */
public interface GDTEEDataInterface {
    boolean createBAMMCKKeys();

    boolean createPasswordKey(boolean z);

    boolean createProtectionKey(String str, boolean z, boolean z2);

    byte[] decryptUsingBAKey(byte[] bArr, boolean z, boolean z2);

    byte[] decryptUsingProtectionKey(byte[] bArr, boolean z, boolean z2);

    void deleteBAMCKKeys();

    void deletePasswordKey();

    void deleteProtectionKey();

    boolean doesBAKeyExist();

    boolean doesPasswordKeyExist();

    boolean doesProtectionKeyExist();

    byte[] encryptUsingBAKey(byte[] bArr, boolean z, boolean z2);

    byte[] encryptUsingProtectionKey(byte[] bArr, boolean z, boolean z2);

    Certificate[] getCertificateChain(String str, String str2);

    byte[] usePasswordKey(byte[] bArr, boolean z);
}
