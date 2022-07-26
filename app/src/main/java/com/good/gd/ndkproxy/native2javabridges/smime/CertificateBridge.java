package com.good.gd.ndkproxy.native2javabridges.smime;

import com.good.gd.smime.Certificate;

/* loaded from: classes.dex */
final class CertificateBridge extends Certificate {

    /* loaded from: classes.dex */
    private static final class Helper extends Certificate.CertificateBridgeHelper {
        private Helper() {
        }

        protected static byte[] getBinaryKey(Certificate certificate) {
            return Certificate.CertificateBridgeHelper._getBinaryKey(certificate);
        }

        protected static byte[] getBinaryX509DER(Certificate certificate) {
            return Certificate.CertificateBridgeHelper._getBinaryX509DER(certificate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setBinaryKey(Certificate certificate, byte[] bArr) {
            Certificate.CertificateBridgeHelper._setBinaryKey(certificate, bArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setBinaryX509DER(Certificate certificate, byte[] bArr) {
            Certificate.CertificateBridgeHelper._setBinaryX509DER(certificate, bArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setIssuer(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setIssuer(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setKeyUsage(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setKeyUsage(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setNotAfterDate(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setNotAfterDate(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setNotBeforeDate(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setNotBeforeDate(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setSerialNumber(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setSerialNumber(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setSubjectAlternativeName(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setSubjectAlternativeName(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setSubjectName(Certificate certificate, String str) {
            Certificate.CertificateBridgeHelper._setSubjectName(certificate, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setVersion(Certificate certificate, long j) {
            Certificate.CertificateBridgeHelper._setVersion(certificate, j);
        }
    }

    private static byte[] getBinaryKey(Certificate certificate) {
        return Helper.getBinaryKey(certificate);
    }

    private static byte[] getBinaryX509DER(Certificate certificate) {
        return Helper.getBinaryX509DER(certificate);
    }

    private static void setBinaryKey(Certificate certificate, byte[] bArr) {
        Helper.setBinaryKey(certificate, bArr);
    }

    private static void setBinaryX509DER(Certificate certificate, byte[] bArr) {
        Helper.setBinaryX509DER(certificate, bArr);
    }

    private static void setIssuer(Certificate certificate, String str) {
        Helper.setIssuer(certificate, str);
    }

    private static void setKeyUsage(Certificate certificate, String str) {
        Helper.setKeyUsage(certificate, str);
    }

    private static void setNotAfterDate(Certificate certificate, String str) {
        Helper.setNotAfterDate(certificate, str);
    }

    private static void setNotBeforeDate(Certificate certificate, String str) {
        Helper.setNotBeforeDate(certificate, str);
    }

    private static void setSerialNumber(Certificate certificate, String str) {
        Helper.setSerialNumber(certificate, str);
    }

    private static void setSubjectAlternativeName(Certificate certificate, String str) {
        Helper.setSubjectAlternativeName(certificate, str);
    }

    private static void setSubjectName(Certificate certificate, String str) {
        Helper.setSubjectName(certificate, str);
    }

    private static void setVersion(Certificate certificate, long j) {
        Helper.setVersion(certificate, j);
    }
}
