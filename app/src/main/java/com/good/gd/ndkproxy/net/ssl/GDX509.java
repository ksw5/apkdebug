package com.good.gd.ndkproxy.net.ssl;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public final class GDX509 {
    private static GDX509 _instance;
    private X509TrustManager _tm = null;
    private boolean _testMode = false;

    private GDX509() throws Exception {
        String property = System.getProperty("javax.net.ssl.keyStore");
        GDLog.DBGPRINTF(16, "GDX509::GDX509() : keystore prop:" + property + ", pwd:" + System.getProperty("javax.net.ssl.keyStorePassword") + "\n");
        try {
            GDLog.DBGPRINTF(16, "GDX509: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (Exception e) {
            throw new Exception("GDX509: Cannot initialize C++ peer", e);
        }
    }

    public static GDX509 getInstance() throws Exception {
        if (_instance == null) {
            _instance = new GDX509();
        }
        return _instance;
    }

    private X509TrustManager getTrustManage(Provider provider) {
        StackTraceElement[] stackTrace;
        X509TrustManager x509TrustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509", provider);
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers != null) {
                for (TrustManager trustManager : trustManagers) {
                    if (trustManager instanceof X509TrustManager) {
                        x509TrustManager = (X509TrustManager) trustManager;
                    }
                }
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, "GDX509::getTrustManager() - Exception: " + e + "\n");
            int length = e.getStackTrace().length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "GDX509::getTrustManager() " + stackTrace[i] + "\n");
            }
        }
        return x509TrustManager;
    }

    private boolean isSelfSigned(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    private void printCertificateDetails(X509Certificate x509Certificate) {
        StackTraceElement[] stackTrace;
        boolean z;
        GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() IN\n");
        try {
            GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails():****************** START ****************************************************\n\n");
            if (isSelfSigned(x509Certificate)) {
                GDLog.DBGPRINTF(16, "It is self signed.\n");
            }
            try {
                x509Certificate.checkValidity();
                z = true;
            } catch (CertificateExpiredException e) {
                GDLog.DBGPRINTF(16, "validity checking: the certificate has expired.\n");
                z = false;
                GDLog.DBGPRINTF(16, "validity test:[" + z + "]\n");
                GDLog.DBGPRINTF(16, "serial number:[" + x509Certificate.getSerialNumber() + "]\n");
                GDLog.DBGPRINTF(16, "the name of the algorithm for the certificate signature:[" + x509Certificate.getSigAlgName() + "]\n");
                GDLog.DBGPRINTF(16, "OID:[" + x509Certificate.getSigAlgOID() + "]\n");
                GDLog.DBGPRINTF(16, "type:[" + x509Certificate.getType() + "]\n");
                GDLog.DBGPRINTF(16, "version:[" + x509Certificate.getVersion() + "]\n");
                GDLog.DBGPRINTF(16, "subject:[" + x509Certificate.getSubjectDN() + "]\n");
                GDLog.DBGPRINTF(16, "SubjectAltName:[" + x509Certificate.getSubjectAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "issuer:[" + x509Certificate.getIssuerDN() + "]\n");
                GDLog.DBGPRINTF(16, "issuerUniqueID:[" + x509Certificate.getIssuerUniqueID() + "]\n");
                GDLog.DBGPRINTF(16, "IssuerAltName:[" + x509Certificate.getIssuerAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotAfter):[" + x509Certificate.getNotAfter() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotBefore):[" + x509Certificate.getNotBefore() + "]\n");
                GDLog.DBGPRINTF(16, "public key:[" + x509Certificate.getPublicKey() + "]\n");
                GDLog.DBGPRINTF(16, "signature:[" + x509Certificate.getSignature() + "]\n");
                GDLog.DBGPRINTF(16, "ExtKeyUsageSyntax:[" + x509Certificate.getExtendedKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "KeyUsage:[" + x509Certificate.getKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "hasUnsupportedCriticalExtension:[" + x509Certificate.hasUnsupportedCriticalExtension() + "]\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails():******************** END **************************************************\n\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() OUT:\n");
            } catch (CertificateNotYetValidException e2) {
                GDLog.DBGPRINTF(16, "validity checking: the certificate has expired.\n");
                z = false;
                GDLog.DBGPRINTF(16, "validity test:[" + z + "]\n");
                GDLog.DBGPRINTF(16, "serial number:[" + x509Certificate.getSerialNumber() + "]\n");
                GDLog.DBGPRINTF(16, "the name of the algorithm for the certificate signature:[" + x509Certificate.getSigAlgName() + "]\n");
                GDLog.DBGPRINTF(16, "OID:[" + x509Certificate.getSigAlgOID() + "]\n");
                GDLog.DBGPRINTF(16, "type:[" + x509Certificate.getType() + "]\n");
                GDLog.DBGPRINTF(16, "version:[" + x509Certificate.getVersion() + "]\n");
                GDLog.DBGPRINTF(16, "subject:[" + x509Certificate.getSubjectDN() + "]\n");
                GDLog.DBGPRINTF(16, "SubjectAltName:[" + x509Certificate.getSubjectAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "issuer:[" + x509Certificate.getIssuerDN() + "]\n");
                GDLog.DBGPRINTF(16, "issuerUniqueID:[" + x509Certificate.getIssuerUniqueID() + "]\n");
                GDLog.DBGPRINTF(16, "IssuerAltName:[" + x509Certificate.getIssuerAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotAfter):[" + x509Certificate.getNotAfter() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotBefore):[" + x509Certificate.getNotBefore() + "]\n");
                GDLog.DBGPRINTF(16, "public key:[" + x509Certificate.getPublicKey() + "]\n");
                GDLog.DBGPRINTF(16, "signature:[" + x509Certificate.getSignature() + "]\n");
                GDLog.DBGPRINTF(16, "ExtKeyUsageSyntax:[" + x509Certificate.getExtendedKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "KeyUsage:[" + x509Certificate.getKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "hasUnsupportedCriticalExtension:[" + x509Certificate.hasUnsupportedCriticalExtension() + "]\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails():******************** END **************************************************\n\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() OUT:\n");
            } catch (Exception e3) {
                GDLog.DBGPRINTF(16, "validity checking:" + e3 + "\n");
                z = false;
                GDLog.DBGPRINTF(16, "validity test:[" + z + "]\n");
                GDLog.DBGPRINTF(16, "serial number:[" + x509Certificate.getSerialNumber() + "]\n");
                GDLog.DBGPRINTF(16, "the name of the algorithm for the certificate signature:[" + x509Certificate.getSigAlgName() + "]\n");
                GDLog.DBGPRINTF(16, "OID:[" + x509Certificate.getSigAlgOID() + "]\n");
                GDLog.DBGPRINTF(16, "type:[" + x509Certificate.getType() + "]\n");
                GDLog.DBGPRINTF(16, "version:[" + x509Certificate.getVersion() + "]\n");
                GDLog.DBGPRINTF(16, "subject:[" + x509Certificate.getSubjectDN() + "]\n");
                GDLog.DBGPRINTF(16, "SubjectAltName:[" + x509Certificate.getSubjectAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "issuer:[" + x509Certificate.getIssuerDN() + "]\n");
                GDLog.DBGPRINTF(16, "issuerUniqueID:[" + x509Certificate.getIssuerUniqueID() + "]\n");
                GDLog.DBGPRINTF(16, "IssuerAltName:[" + x509Certificate.getIssuerAlternativeNames() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotAfter):[" + x509Certificate.getNotAfter() + "]\n");
                GDLog.DBGPRINTF(16, "validity(NotBefore):[" + x509Certificate.getNotBefore() + "]\n");
                GDLog.DBGPRINTF(16, "public key:[" + x509Certificate.getPublicKey() + "]\n");
                GDLog.DBGPRINTF(16, "signature:[" + x509Certificate.getSignature() + "]\n");
                GDLog.DBGPRINTF(16, "ExtKeyUsageSyntax:[" + x509Certificate.getExtendedKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "KeyUsage:[" + x509Certificate.getKeyUsage() + "]\n");
                GDLog.DBGPRINTF(16, "hasUnsupportedCriticalExtension:[" + x509Certificate.hasUnsupportedCriticalExtension() + "]\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails():******************** END **************************************************\n\n");
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() OUT:\n");
            }
            GDLog.DBGPRINTF(16, "validity test:[" + z + "]\n");
            GDLog.DBGPRINTF(16, "serial number:[" + x509Certificate.getSerialNumber() + "]\n");
            GDLog.DBGPRINTF(16, "the name of the algorithm for the certificate signature:[" + x509Certificate.getSigAlgName() + "]\n");
            GDLog.DBGPRINTF(16, "OID:[" + x509Certificate.getSigAlgOID() + "]\n");
            GDLog.DBGPRINTF(16, "type:[" + x509Certificate.getType() + "]\n");
            GDLog.DBGPRINTF(16, "version:[" + x509Certificate.getVersion() + "]\n");
            GDLog.DBGPRINTF(16, "subject:[" + x509Certificate.getSubjectDN() + "]\n");
            GDLog.DBGPRINTF(16, "SubjectAltName:[" + x509Certificate.getSubjectAlternativeNames() + "]\n");
            GDLog.DBGPRINTF(16, "issuer:[" + x509Certificate.getIssuerDN() + "]\n");
            GDLog.DBGPRINTF(16, "issuerUniqueID:[" + x509Certificate.getIssuerUniqueID() + "]\n");
            GDLog.DBGPRINTF(16, "IssuerAltName:[" + x509Certificate.getIssuerAlternativeNames() + "]\n");
            GDLog.DBGPRINTF(16, "validity(NotAfter):[" + x509Certificate.getNotAfter() + "]\n");
            GDLog.DBGPRINTF(16, "validity(NotBefore):[" + x509Certificate.getNotBefore() + "]\n");
            GDLog.DBGPRINTF(16, "public key:[" + x509Certificate.getPublicKey() + "]\n");
            GDLog.DBGPRINTF(16, "signature:[" + x509Certificate.getSignature() + "]\n");
            GDLog.DBGPRINTF(16, "ExtKeyUsageSyntax:[" + x509Certificate.getExtendedKeyUsage() + "]\n");
            GDLog.DBGPRINTF(16, "KeyUsage:[" + x509Certificate.getKeyUsage() + "]\n");
            GDLog.DBGPRINTF(16, "hasUnsupportedCriticalExtension:[" + x509Certificate.hasUnsupportedCriticalExtension() + "]\n");
            GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails():******************** END **************************************************\n\n");
        } catch (Exception e4) {
            GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() - exception: " + e4 + "\n");
            int length = e4.getStackTrace().length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() " + stackTrace[i] + "\n");
            }
        }
        GDLog.DBGPRINTF(16, "GDX509::printCertificateDetails() OUT:\n");
    }

    private void printDeviceTS() {
        StackTraceElement[] stackTrace;
        GDLog.DBGPRINTF(16, "GDX509::printDeviceTS() IN\n");
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            X509TrustManager x509TrustManager = null;
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers != null) {
                for (TrustManager trustManager : trustManagers) {
                    if (trustManager instanceof X509TrustManager) {
                        x509TrustManager = (X509TrustManager) trustManager;
                    }
                }
            }
            X509Certificate[] acceptedIssuers = x509TrustManager.getAcceptedIssuers();
            for (X509Certificate x509Certificate : acceptedIssuers) {
                printCertificateDetails(x509Certificate);
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, "GDX509::printDeviceTS() - exception: " + e + "\n");
            int length = e.getStackTrace().length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "GDX509::printDeviceTS() " + stackTrace[i] + "\n");
            }
        }
        GDLog.DBGPRINTF(16, "GDX509::printDeviceTS() OUT:\n");
    }

    private List<X509Certificate> reOrderCertificateChain(List<X509Certificate> list) {
        boolean z;
        X509Certificate[] x509CertificateArr = (X509Certificate[]) list.toArray(new X509Certificate[list.size()]);
        int length = x509CertificateArr.length;
        if (x509CertificateArr.length > 1) {
            int i = 0;
            while (i < x509CertificateArr.length) {
                int i2 = i + 1;
                int i3 = i2;
                while (true) {
                    if (i3 >= x509CertificateArr.length) {
                        z = false;
                        break;
                    } else if (x509CertificateArr[i].getIssuerDN().equals(x509CertificateArr[i3].getSubjectDN())) {
                        if (i3 != i2) {
                            X509Certificate x509Certificate = x509CertificateArr[i3];
                            x509CertificateArr[i3] = x509CertificateArr[i2];
                            x509CertificateArr[i2] = x509Certificate;
                        }
                        z = true;
                    } else {
                        i3++;
                    }
                }
                if (!z) {
                    break;
                }
                i = i2;
            }
            length = i + 1;
            X509Certificate x509Certificate2 = x509CertificateArr[length - 1];
            Date date = new Date();
            if (x509Certificate2.getSubjectDN().equals(x509Certificate2.getIssuerDN()) && date.after(x509Certificate2.getNotAfter())) {
                length--;
            }
        }
        X509Certificate[] x509CertificateArr2 = new X509Certificate[length];
        for (int i4 = 0; i4 < length; i4++) {
            x509CertificateArr2[i4] = x509CertificateArr[i4];
        }
        return Arrays.asList(x509CertificateArr2);
    }

    private void setTestMode() {
        this._testMode = true;
    }

    private boolean verifyCertificateChain(byte[][] bArr) {
        return verifyCertificateChain(bArr, null);
    }

    private boolean verifyCertificateChainUsingOwnTrustAnchor(List<X509Certificate> list, X509Certificate x509Certificate, CertificateFactory certificateFactory) {
        StackTraceElement[] stackTrace;
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingOwnTrustAnchor() IN\n");
        PKIXCertPathValidatorResult pKIXCertPathValidatorResult = null;
        boolean z = false;
        try {
            CertPath generateCertPath = certificateFactory.generateCertPath(list);
            CertPathValidator certPathValidator = CertPathValidator.getInstance(CertPathValidator.getDefaultType());
            PKIXParameters pKIXParameters = new PKIXParameters(Collections.singleton(new TrustAnchor(x509Certificate, null)));
            pKIXParameters.setRevocationEnabled(false);
            z = true;
            pKIXCertPathValidatorResult = (PKIXCertPathValidatorResult) certPathValidator.validate(generateCertPath, pKIXParameters);
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingOwnTrustAnchor() - Exception: " + e + "\n");
            int length = e.getStackTrace().length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingOwnTrustAnchor() " + stackTrace[i] + "\n");
            }
        }
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingOwnTrustAnchor() OUT: result=" + z + ", validation result=" + pKIXCertPathValidatorResult + "\n");
        return z;
    }

    private boolean verifyCertificateChainUsingSelectedTM(X509TrustManager x509TrustManager, List<X509Certificate> list, CertificateFactory certificateFactory) {
        boolean z;
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingSelectedTM() IN\n");
        try {
            x509TrustManager.checkServerTrusted((X509Certificate[]) list.toArray(new X509Certificate[list.size()]), "RSA");
            z = true;
        } catch (CertificateException e) {
            e.printStackTrace();
            z = false;
        }
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingSelectedTM() OUT:" + z + "\n");
        return z;
    }

    private boolean verifyCertificateChainUsingTM(List<X509Certificate> list, CertificateFactory certificateFactory) {
        boolean z;
        StackTraceElement[] stackTrace;
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() IN\n");
        X509TrustManager x509TrustManager = this._tm;
        if (x509TrustManager == null) {
            z = false;
        } else {
            z = verifyCertificateChainUsingSelectedTM(x509TrustManager, list, certificateFactory);
            if (z) {
                GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() OUT:" + z + "\n");
                return z;
            }
        }
        X509TrustManager trustManage = getTrustManage(Security.getProvider("HarmonyJSSE"));
        if (trustManage != null && (z = verifyCertificateChainUsingSelectedTM(trustManage, list, certificateFactory))) {
            this._tm = trustManage;
            GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() OUT:" + z + "\n");
            return z;
        }
        boolean z2 = z;
        for (Provider provider : Security.getProviders("TrustManagerFactory.PKIX")) {
            try {
                X509TrustManager trustManage2 = getTrustManage(provider);
                if (trustManage2 != null && (z2 = verifyCertificateChainUsingSelectedTM(trustManage2, list, certificateFactory))) {
                    this._tm = trustManage2;
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() OUT:" + z2 + "\n");
                    return z2;
                }
            } catch (Exception e) {
                GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() - Exception: " + e + "\n");
                int length = e.getStackTrace().length;
                for (int i = 0; i < length; i++) {
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() " + stackTrace[i] + "\n");
                }
            }
        }
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChainUsingTM() OUT:" + z2 + "\n");
        return z2;
    }

    native void ndkInit();

    private boolean verifyCertificateChain(byte[][] bArr, byte[] bArr2) {
        StackTraceElement[] stackTrace;
        StackTraceElement[] stackTrace2;
        StackTraceElement[] stackTrace3;
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() IN\n");
        boolean z = false;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ArrayList arrayList = new ArrayList(bArr.length);
            for (byte[] bArr3 : bArr) {
                try {
                    arrayList.add((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bArr3)));
                } catch (CertificateException e) {
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() - Exception " + e + "\n");
                    int length = e.getStackTrace().length;
                    for (int i = 0; i < length; i++) {
                        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() " + stackTrace3[i] + "\n");
                    }
                }
            }
            List<X509Certificate> reOrderCertificateChain = reOrderCertificateChain(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < reOrderCertificateChain.size(); i2++) {
                X509Certificate x509Certificate = reOrderCertificateChain.get(i2);
                if (isSelfSigned(x509Certificate)) {
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() - cert #" + i2 + " is self signed.\n");
                }
                if (bArr2 == null || i2 != reOrderCertificateChain.size() - 1) {
                    arrayList2.add(x509Certificate);
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain(): Adding array[" + i2 + "]\n");
                }
            }
            if (bArr2 != null) {
                GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() - parsing root cert.\n");
                InputStream byteArrayInputStream = new ByteArrayInputStream(bArr2);
                X509Certificate x509Certificate2 = null;
                try {
                    x509Certificate2 = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
                } catch (CertificateException e2) {
                    GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() root cert - " + e2 + "\n");
                    int length2 = e2.getStackTrace().length;
                    for (int i3 = 0; i3 < length2; i3++) {
                        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() " + stackTrace2[i3] + "\n");
                    }
                }
                if (x509Certificate2 != null) {
                    z = verifyCertificateChainUsingOwnTrustAnchor(arrayList2, x509Certificate2, certificateFactory);
                }
            } else {
                z = verifyCertificateChainUsingTM(arrayList2, certificateFactory);
            }
        } catch (Exception e3) {
            GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() Exception- " + e3 + "\n");
            int length3 = e3.getStackTrace().length;
            for (int i4 = 0; i4 < length3; i4++) {
                GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() " + stackTrace[i4] + "\n");
            }
        }
        GDLog.DBGPRINTF(16, "GDX509::verifyCertificateChain() OUT: result=" + z + "\n");
        return z;
    }
}
