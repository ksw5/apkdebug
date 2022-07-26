package com.good.gd.pki;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class Credential {
    protected Certificate[] auxCertificates;
    protected Certificate userCertificate;

    private static native String NDK_import(byte[] bArr, String str);

    private static native void NDK_importDone();

    private static native void NDK_importTo(byte[] bArr, String str, String str2);

    private static native void NDK_undoImport(String str);

    public static void finalizeImport() {
        NDK_importDone();
    }

    public static String importPKCS12(byte[] bArr, String str) throws CredentialException {
        return NDK_import(bArr, str);
    }

    public static void undoImport(String str) throws CredentialException {
        NDK_undoImport(str);
    }

    public List<Certificate> getAuxCertificates() {
        Certificate[] certificateArr = this.auxCertificates;
        if (certificateArr != null) {
            return Arrays.asList(certificateArr);
        }
        return null;
    }

    public Certificate getUserCertificate() {
        return this.userCertificate;
    }

    public static void importPKCS12(byte[] bArr, String str, String str2) throws CredentialException {
        NDK_importTo(bArr, str, str2);
    }
}
