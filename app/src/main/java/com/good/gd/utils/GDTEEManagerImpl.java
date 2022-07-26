package com.good.gd.utils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.security.AttestedKeyPair;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.StrongBoxUnavailableException;
import android.util.Base64;
import android.util.Log;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDUtilInterface;
import com.good.gt.context.GTBaseContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Arrays;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/* loaded from: classes.dex */
public class GDTEEManagerImpl implements GDTEEControlInterface, GDTEEDataInterface {
    public static final String GD_ATTESTATION_KEY = "GD_40767260";
    private static final String GD_BA_PROTECTION_KEY = "GD_2100170183676";
    private static final String GD_DEVICEPOL_ATTESTATION_KEY = "GD_29848886";
    private static final String GD_PASSWORD_KEY = "GD_19452544";
    private static final int GD_PASSWORD_KEY_ITERATIONS = 10;
    public static final String GD_PROTECTION_COMPLETE_KEY = "GD_DUMMY12389745784";
    public static final String GD_PROTECTION_KEY = "GD_76400124";
    public static final String GD_USING_PROTECTION_COMPLETE_KEY = "GD_DUMMY983093789378903";
    private KeyStore mKeyStore;
    private boolean mSpecificSetProviderReqd = false;
    private GDUtilInterface mUtilInterface;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DevicePolManagerAttestationKeyPairHelper {
        private String dbjc;
        private int jwxax;
        private Context liflu;
        private ComponentName lqox;
        private String qkduk;
        private boolean wxau;
        private DevicePolicyManager ztwf;

        /* loaded from: classes.dex */
        public static class Builder {
            private String alias;
            private String attestationChallenge = null;
            private int purposeFlags = 0;
            private boolean attemptStrongBox = false;

            public Builder(String str) {
                this.alias = null;
                this.alias = str;
            }

            public DevicePolManagerAttestationKeyPairHelper build() throws IllegalArgumentException {
                String str = this.alias;
                if (str != null && !str.isEmpty()) {
                    if (this.attestationChallenge != null) {
                        DevicePolManagerAttestationKeyPairHelper devicePolManagerAttestationKeyPairHelper = new DevicePolManagerAttestationKeyPairHelper();
                        devicePolManagerAttestationKeyPairHelper.dbjc = this.alias;
                        devicePolManagerAttestationKeyPairHelper.qkduk = this.attestationChallenge;
                        devicePolManagerAttestationKeyPairHelper.jwxax = this.purposeFlags;
                        devicePolManagerAttestationKeyPairHelper.wxau = this.attemptStrongBox;
                        return devicePolManagerAttestationKeyPairHelper;
                    }
                    throw new IllegalArgumentException("attestation challenge must be not null");
                }
                throw new IllegalArgumentException("alias must not be null or empty");
            }

            public Builder setAttemptStrongBox(boolean z) {
                this.attemptStrongBox = z;
                return this;
            }

            public Builder setAttestationChallenge(String str) {
                this.attestationChallenge = str;
                return this;
            }

            public Builder setPurposeFlags(int i) {
                this.purposeFlags = i;
                return this;
            }
        }

        private DevicePolManagerAttestationKeyPairHelper() {
            this.dbjc = null;
            this.qkduk = null;
            this.jwxax = 0;
            this.wxau = false;
            this.ztwf = null;
            this.lqox = null;
            this.liflu = null;
            Context applicationContext = GTBaseContext.getInstance().getApplicationContext();
            this.liflu = applicationContext;
            this.ztwf = (DevicePolicyManager) applicationContext.getSystemService("device_policy");
            this.lqox = new ComponentName(this.liflu.getPackageName(), "com.rim.mobilefusion.client.actions.utils.device_administration.ClientDeviceAdminReceiver");
        }

        public static Certificate[] dbjc(String str, String str2, int i) throws IllegalArgumentException {
            Certificate[] certificateArr = null;
            if (Build.VERSION.SDK_INT >= 28) {
                DevicePolManagerAttestationKeyPairHelper build = new Builder(str).setAttestationChallenge(str2).setPurposeFlags(i).setAttemptStrongBox(true).build();
                try {
                    AttestedKeyPair dbjc = build.dbjc();
                    try {
                        if (dbjc != 0) {
                            List<Certificate> attestationRecord = dbjc.getAttestationRecord();
                            certificateArr = (Certificate[]) attestationRecord.toArray(new Certificate[attestationRecord.size()]);
                        } else {
                            GDLog.DBGPRINTF(13, "FFFManager DPMAKPHGKPIN\n");
                        }
                        if (dbjc != 0 && build.ztwf != null) {
                            GDLog.DBGPRINTF(16, "FFFManager DPMAKPHRAKP\n");
                            build.ztwf.removeKeyPair(build.lqox, build.dbjc);
                        }
                    } catch (Throwable th) {
                        th = th;
                        certificateArr = dbjc;
                        if (certificateArr != null && build.ztwf != null) {
                            GDLog.DBGPRINTF(16, "FFFManager DPMAKPHRAKP\n");
                            build.ztwf.removeKeyPair(build.lqox, build.dbjc);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
            return certificateArr;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(10:(2:18|19)|21|22|(1:28)|29|31|32|(1:34)|35|36) */
        /* JADX WARN: Can't wrap try/catch for region: R(11:(2:18|19)|20|21|22|(1:28)|29|31|32|(1:34)|35|36) */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x012b, code lost:
            r5 = e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0137, code lost:
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(12, "FFFManager AAACCC 1 " + android.util.Log.getStackTraceString(r5));
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0135, code lost:
            r5 = e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0136, code lost:
            r6 = null;
         */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0116 A[Catch: StrongBoxUnavailableException -> 0x012d, InvalidAlgorithmParameterException -> 0x0135, Exception -> 0x0162, TryCatch #0 {InvalidAlgorithmParameterException -> 0x0135, blocks: (B:22:0x00c9, B:24:0x0116, B:26:0x011c, B:28:0x0120, B:29:0x0123), top: B:21:0x00c9 }] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0153  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public AttestedKeyPair dbjc() {
            /*
                Method dump skipped, instructions count: 382
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.good.gd.utils.GDTEEManagerImpl.DevicePolManagerAttestationKeyPairHelper.dbjc():android.security.AttestedKeyPair");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class yfdke {
        public boolean dbjc;
        public boolean jwxax;
        public boolean qkduk;
        public int wxau;

        private yfdke(GDTEEManagerImpl gDTEEManagerImpl) {
        }
    }

    public GDTEEManagerImpl() {
        GDLog.DBGPRINTF(14, "FFFManager AAA\n");
    }

    private boolean EnsureKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            this.mKeyStore = keyStore;
            keyStore.load(null);
            return true;
        } catch (IOException e) {
            GDLog.DBGPRINTF(12, "FFFManager EEE " + Log.getStackTraceString(e) + "\n");
            return false;
        } catch (KeyStoreException e2) {
            GDLog.DBGPRINTF(12, "FFFManager BBB " + Log.getStackTraceString(e2) + "\n");
            return false;
        } catch (NoSuchAlgorithmException e3) {
            GDLog.DBGPRINTF(12, "FFFManager DDD " + Log.getStackTraceString(e3) + "\n");
            return false;
        } catch (CertificateException e4) {
            GDLog.DBGPRINTF(12, "FFFManager CCC " + Log.getStackTraceString(e4) + "\n");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canUseStrongBox() {
        boolean z = !Build.MANUFACTURER.equalsIgnoreCase("samsung");
        GDLog.DBGPRINTF(14, "FFFManager AAA BB DD SB" + z + "\n");
        return z;
    }

    private void checkIfExplictSetProviderWorkAroundNeeded() {
        Provider[] providers;
        for (Provider provider : Security.getProviders()) {
            GDLog.DBGPRINTF(14, "FFFManager ABA = " + provider.getName() + "\n");
            if (provider.getName().equals("BlackBerryJCA")) {
                GDLog.DBGPRINTF(14, "FFFManager ABB " + Build.MODEL + "" + Build.FINGERPRINT + "\n");
                this.mSpecificSetProviderReqd = true;
            }
        }
    }

    private boolean checkKeyIsUsable(String str) {
        return encryptUsingSpecifiedKey(str, "hello world".getBytes(), false, false) != null;
    }

    private boolean containsFakeCertificate(Certificate[] certificateArr) {
        if (certificateArr != null && certificateArr.length > 0) {
            for (Certificate certificate : certificateArr) {
                if (certificate instanceof X509Certificate) {
                    X509Certificate x509Certificate = (X509Certificate) certificate;
                    boolean z = x509Certificate.getIssuerDN().getName().compareToIgnoreCase("CN=fake") == 0;
                    boolean z2 = x509Certificate.getSubjectDN().getName().compareToIgnoreCase("CN=fake") == 0;
                    PublicKey publicKey = x509Certificate.getPublicKey();
                    BigInteger bigInteger = BigInteger.ZERO;
                    if (publicKey instanceof RSAPublicKey) {
                        bigInteger = ((RSAPublicKey) publicKey).getPublicExponent();
                    }
                    GDLog.DBGPRINTF(14, "GHAA CFC IIF " + z + " SIF " + z2 + " PE " + bigInteger.longValue() + "\n");
                    if (z && z2 && bigInteger.longValue() == 65537) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void createProtectionCompletedKey(String str) {
        createKey(str, false, GD_PROTECTION_COMPLETE_KEY, 4, false);
    }

    private void createUsingProtectionCompletedKey(String str) {
        createKey(str, false, GD_USING_PROTECTION_COMPLETE_KEY, 4, false);
    }

    private byte[] decryptUsingSpecifiedKey(String str, byte[] bArr, boolean z, boolean z2) {
        GDLog.DBGPRINTF(12, "FFFManager zzz1 \n");
        if (!EnsureKeyStore()) {
            return null;
        }
        try {
            PrivateKey privateKey = (PrivateKey) this.mKeyStore.getKey(str, null);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            if (z2) {
                cipher.init(2, privateKey, new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT));
            } else {
                cipher.init(2, privateKey);
            }
            byte[] doFinal = cipher.doFinal(bArr);
            Arrays.fill(bArr, (byte) 0);
            return doFinal;
        } catch (InvalidAlgorithmParameterException e) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2RRR" + Log.getStackTraceString(e) + "\n");
            return null;
        } catch (InvalidKeyException e2) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2NNN " + Log.getStackTraceString(e2) + "\n");
            return null;
        } catch (KeyStoreException e3) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2KKK " + Log.getStackTraceString(e3) + "\n");
            return null;
        } catch (NoSuchAlgorithmException e4) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2MMM " + Log.getStackTraceString(e4) + "\n");
            return null;
        } catch (UnrecoverableKeyException e5) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2QQQ " + Log.getStackTraceString(e5) + "\n");
            return null;
        } catch (BadPaddingException e6) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2OOO " + Log.getStackTraceString(e6) + "\n");
            return null;
        } catch (IllegalBlockSizeException e7) {
            if (z && z2) {
                return decryptUsingProtectionKey(bArr, false, false);
            }
            if (!z && !z2) {
                GDLog.DBGPRINTF(12, "FFFManager AAA2PPP " + Log.getStackTraceString(e7) + "\n");
                deleteProtectionKey();
                this.mUtilInterface.UtilJ();
            }
            return null;
        } catch (NoSuchPaddingException e8) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2LLL " + Log.getStackTraceString(e8) + "\n");
            return null;
        } catch (Exception e9) {
            GDLog.DBGPRINTF(12, "FFFManager AAA2SSS " + Log.getStackTraceString(e9) + "\n");
            if (z) {
                GDLog.DBGPRINTF(12, "FFFManager AAA2TTT\n");
                decryptUsingSpecifiedKey(str, bArr, false, z2);
            } else {
                this.mUtilInterface.UtilJ();
            }
            return null;
        }
    }

    private void deleteAttestationKey() {
        deleteKey(GD_ATTESTATION_KEY);
    }

    private void deleteKey(String str) {
        GDLog.DBGPRINTF(14, "FFFManager AAATTT " + str + "\n");
        if (!EnsureKeyStore()) {
            return;
        }
        try {
            this.mKeyStore.deleteEntry(str);
        } catch (KeyStoreException e) {
            GDLog.DBGPRINTF(12, "FFFManager AAARRR " + Log.getStackTraceString(e));
        }
    }

    private boolean doesKeyExist(String str) {
        KeyInfo keyInfo;
        GDLog.DBGPRINTF(16, "FFFManager OOO = " + str + "\n");
        if (!EnsureKeyStore()) {
            return false;
        }
        try {
            if (!GD_ATTESTATION_KEY.equals(str) && !GD_PROTECTION_KEY.equals(str) && !GD_BA_PROTECTION_KEY.equals(str) && !GD_PROTECTION_COMPLETE_KEY.equals(str) && !GD_USING_PROTECTION_COMPLETE_KEY.equals(str)) {
                if (!GD_PASSWORD_KEY.equals(str)) {
                    keyInfo = null;
                } else {
                    SecretKey secretKey = (SecretKey) this.mKeyStore.getKey(str, null);
                    if (secretKey == null) {
                        GDLog.DBGPRINTF(12, "FFFManager OOOAAA\n");
                        return false;
                    }
                    keyInfo = (KeyInfo) SecretKeyFactory.getInstance(secretKey.getAlgorithm(), "AndroidKeyStore").getKeySpec(secretKey, KeyInfo.class);
                }
                yfdke yfdkeVar = new yfdke();
                yfdkeVar.dbjc = keyInfo.isInsideSecureHardware();
                yfdkeVar.qkduk = keyInfo.isUserAuthenticationRequired();
                yfdkeVar.jwxax = keyInfo.isUserAuthenticationRequirementEnforcedBySecureHardware();
                yfdkeVar.wxau = keyInfo.getKeySize();
                GDLog.DBGPRINTF(14, "FFFManager OOO " + str + " 1 = " + yfdkeVar.dbjc + " 2 = " + yfdkeVar.qkduk + " 3 = " + yfdkeVar.jwxax + " 4 =" + yfdkeVar.wxau + "\n");
                return true;
            }
            PrivateKey privateKey = (PrivateKey) this.mKeyStore.getKey(str, null);
            if (privateKey == null) {
                GDLog.DBGPRINTF(12, "FFFManager OOOBBB\n");
                return false;
            }
            keyInfo = (KeyInfo) KeyFactory.getInstance(privateKey.getAlgorithm(), "AndroidKeyStore").getKeySpec(privateKey, KeyInfo.class);
            yfdke yfdkeVar2 = new yfdke();
            yfdkeVar2.dbjc = keyInfo.isInsideSecureHardware();
            yfdkeVar2.qkduk = keyInfo.isUserAuthenticationRequired();
            yfdkeVar2.jwxax = keyInfo.isUserAuthenticationRequirementEnforcedBySecureHardware();
            yfdkeVar2.wxau = keyInfo.getKeySize();
            GDLog.DBGPRINTF(14, "FFFManager OOO " + str + " 1 = " + yfdkeVar2.dbjc + " 2 = " + yfdkeVar2.qkduk + " 3 = " + yfdkeVar2.jwxax + " 4 =" + yfdkeVar2.wxau + "\n");
            return true;
        } catch (KeyStoreException e) {
            GDLog.DBGPRINTF(12, "FFFManager PPP " + Log.getStackTraceString(e) + "\n");
            return false;
        } catch (NoSuchAlgorithmException e2) {
            GDLog.DBGPRINTF(12, "FFFManager RRR " + Log.getStackTraceString(e2) + "\n");
            return false;
        } catch (NoSuchProviderException e3) {
            GDLog.DBGPRINTF(12, "FFFManager TTT " + Log.getStackTraceString(e3) + "\n");
            return false;
        } catch (UnrecoverableKeyException e4) {
            GDLog.DBGPRINTF(12, "FFFManager QQQ " + Log.getStackTraceString(e4) + "\n");
            return false;
        } catch (InvalidKeySpecException e5) {
            GDLog.DBGPRINTF(12, "FFFManager SSS " + Log.getStackTraceString(e5) + "\n");
            return false;
        }
    }

    private byte[] encryptUsingSpecifiedKey(String str, byte[] bArr, boolean z, boolean z2) {
        GDLog.DBGPRINTF(16, "FFFManager AAADDD\n");
        if (!EnsureKeyStore()) {
            return null;
        }
        try {
            Certificate certificate = this.mKeyStore.getCertificate(str);
            if (certificate == null) {
                GDLog.DBGPRINTF(12, "FFFManager AAAEEE");
                return null;
            }
            PublicKey publicKey = certificate.getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            if (z2) {
                cipher.init(1, publicKey, new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT));
            } else {
                cipher.init(1, publicKey);
            }
            byte[] doFinal = cipher.doFinal(bArr);
            byte[] decryptUsingSpecifiedKey = decryptUsingSpecifiedKey(str, Arrays.copyOf(doFinal, doFinal.length), false, true);
            if (decryptUsingSpecifiedKey != null) {
                Arrays.fill(bArr, (byte) 0);
                Arrays.fill(decryptUsingSpecifiedKey, (byte) 0);
                return doFinal;
            } else if (z2) {
                return encryptUsingSpecifiedKey(str, bArr, z, false);
            } else {
                Arrays.fill(bArr, (byte) 0);
                Arrays.fill(decryptUsingSpecifiedKey, (byte) 0);
                return null;
            }
        } catch (InvalidAlgorithmParameterException e) {
            GDLog.DBGPRINTF(12, "FFFManager AAALLL " + Log.getStackTraceString(e) + "\n");
            return null;
        } catch (InvalidKeyException e2) {
            GDLog.DBGPRINTF(12, "FFFManager AAAIII " + Log.getStackTraceString(e2) + "\n");
            return null;
        } catch (KeyStoreException e3) {
            GDLog.DBGPRINTF(12, "FFFManager AAAFFF " + Log.getStackTraceString(e3) + "\n");
            return null;
        } catch (NoSuchAlgorithmException e4) {
            GDLog.DBGPRINTF(12, "FFFManager AAAHHH " + Log.getStackTraceString(e4) + "\n");
            return null;
        } catch (BadPaddingException e5) {
            GDLog.DBGPRINTF(12, "GFFFManager AAAJJJ " + Log.getStackTraceString(e5) + "\n");
            return null;
        } catch (IllegalBlockSizeException e6) {
            GDLog.DBGPRINTF(12, "FFFManager AAAKKK " + Log.getStackTraceString(e6) + "\n");
            return null;
        } catch (NoSuchPaddingException e7) {
            GDLog.DBGPRINTF(12, "FFFManager AAAGGG " + Log.getStackTraceString(e7) + "\n");
            return null;
        } catch (Exception e8) {
            GDLog.DBGPRINTF(12, "FFFManager AAAMMM " + Log.getStackTraceString(e8) + "\n");
            if (z) {
                GDLog.DBGPRINTF(12, "FFFManager AAA NNN\n");
                return encryptUsingSpecifiedKey(str, bArr, false, z2);
            }
            this.mUtilInterface.UtilJ();
            return null;
        }
    }

    private Certificate generateFakeCertificate() {
        try {
            return CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(Base64.decode("MIIBljCCAX+gAwIBAgIBATANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDEwRmYWtlMB4XDTcwMDEwMTAwMDAwMFoXDTQ4MDEwMTAwMDAwMFowDzENMAsGA1UEAxMEZmFrZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMFHSd9LsTfrp7pnnUuPe5E59ApIYzzO4jPK7fni4VU/iSPrM+FOCpV4MsbuV5XawzysqlAdogRJSFqcvC0cN/+bmmQ5g4PeqkftPCVsHtrGUbS/CSjEtC3fy5Xscj88WQYAWO7RL/oqRmZND4rWN7IzJ3SijYiHWX6HPbBqFPlOUP+kruQbCrcsMq9JysdkWhNtWuyemSzQlWoMXp4n6voGi2PLNxt7STTnOF1XhVLWNelybaEfk3idZGjTN/ADIZOJlJ9n8JnfCpBRsDMWD8S+KGRWPmBJt69W4oE64kTuZc/MK0h7SB1gvdE+UYfGNEfXFJzfOPQnQ9/mRFAzTbsCAwEAATANBgkqhkiG9w0BAQsFAAMCAAA=", 0)));
        } catch (CertificateException e) {
            GDLog.DBGPRINTF(12, "CGTEE FTGFC");
            return null;
        }
    }

    private Certificate[] getCertificatesForHWAttestationAtActivation(String str) {
        Certificate[] certificateArr = null;
        for (int i = 0; i < 2; i++) {
            certificateArr = DevicePolManagerAttestationKeyPairHelper.dbjc(GD_DEVICEPOL_ATTESTATION_KEY, str, 6);
            if (certificateArr == null) {
                GDLog.DBGPRINTF(16, "FFFManager GKIAW 1\n");
                createProtectionKey(str, true, true);
                try {
                    certificateArr = this.mKeyStore.getCertificateChain(GD_PROTECTION_KEY);
                } catch (KeyStoreException e) {
                    GDLog.DBGPRINTF(12, "FFFManager AAA2KKK 2 1 " + Log.getStackTraceString(e) + "\n");
                }
            }
            if (!shouldRetryAttestation(certificateArr)) {
                break;
            }
            GDLog.DBGPRINTF(12, "GTMI GCFHAAACFCRA 1");
            try {
                if (!Looper.getMainLooper().isCurrentThread()) {
                    Thread.sleep(100L);
                }
            } catch (InterruptedException e2) {
                GDLog.DBGPRINTF(12, "GTMI GCFHPATIE 1");
            }
        }
        return certificateArr;
    }

    private Certificate[] getCertificatesForHWPeriodicAttestation(String str) {
        Certificate[] certificateArr = null;
        for (int i = 0; i < 2; i++) {
            certificateArr = DevicePolManagerAttestationKeyPairHelper.dbjc(GD_DEVICEPOL_ATTESTATION_KEY, str, 12);
            if (certificateArr == null) {
                GDLog.DBGPRINTF(16, "FFFManager GKIAW\n");
                if (doesKeyExist(GD_ATTESTATION_KEY)) {
                    deleteKey(GD_ATTESTATION_KEY);
                }
                createKey(str, true, GD_ATTESTATION_KEY, 12, true);
                try {
                    certificateArr = this.mKeyStore.getCertificateChain(GD_ATTESTATION_KEY);
                } catch (KeyStoreException e) {
                    GDLog.DBGPRINTF(12, "FFFManager AAA2KKK 2 " + Log.getStackTraceString(e) + "\n");
                }
            }
            if (!shouldRetryAttestation(certificateArr)) {
                break;
            }
            GDLog.DBGPRINTF(12, "GTMI GCFHPAACFCRA");
            try {
                if (!Looper.getMainLooper().isCurrentThread()) {
                    Thread.sleep(100L);
                }
            } catch (InterruptedException e2) {
                GDLog.DBGPRINTF(12, "GTMI GCFHPATIE");
            }
        }
        return certificateArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDeviceStrongBoxCapable() {
        return GTBaseContext.getInstance().getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.strongbox_keystore");
    }

    private boolean shouldRetryAttestation(Certificate[] certificateArr) {
        boolean z;
        boolean z2;
        boolean z3 = certificateArr == null;
        if (!z3) {
            z2 = containsFakeCertificate(certificateArr);
            z = certificateArr.length == 0;
        } else {
            z = true;
            z2 = true;
        }
        return z3 || z || z2;
    }

    @Override // com.good.gd.utils.GDTEEControlInterface
    public void Init() {
        this.mUtilInterface = new GDUtilInterface(this);
        checkIfExplictSetProviderWorkAroundNeeded();
        if (doesKeyExist(GD_USING_PROTECTION_COMPLETE_KEY)) {
            if (!doesKeyExist(GD_PROTECTION_COMPLETE_KEY)) {
                GDLog.DBGPRINTF(14, "FFFManager XYZZZK\n");
                this.mUtilInterface.UtilY();
                deleteProtectionKey();
                return;
            }
            GDLog.DBGPRINTF(14, "FFFManager WXZZZL\n");
            return;
        }
        GDLog.DBGPRINTF(14, "FFFManager NCC1701G\n");
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean createBAMMCKKeys() {
        return createKey("NONE", false, GD_BA_PROTECTION_KEY, 6, true);
    }

    public boolean createKey(String str, boolean z, String str2, int i, boolean z2) {
        boolean z3;
        KeyGenParameterSpec.Builder userAuthenticationRequired;
        GDLog.DBGPRINTF(14, "FFFManager AAA BB CC" + str2 + " \n");
        boolean z4 = false;
        if (!EnsureKeyStore()) {
            return false;
        }
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            GDLog.DBGPRINTF(12, "FFFManager AAA AAA " + Log.getStackTraceString(e));
        } catch (NoSuchProviderException e2) {
            GDLog.DBGPRINTF(12, "FFFManager AAABBB " + Log.getStackTraceString(e2));
        }
        try {
        } catch (InvalidAlgorithmParameterException e3) {
            e = e3;
        }
        try {
            userAuthenticationRequired = new KeyGenParameterSpec.Builder(str2, i).setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(2048, new BigInteger("65537"))).setDigests("SHA-256").setSignaturePaddings("PKCS1").setEncryptionPaddings("OAEPPadding").setUserAuthenticationRequired(false);
            if (z) {
                userAuthenticationRequired.setAttestationChallenge(str.getBytes());
            }
            if (Build.VERSION.SDK_INT > 30) {
                z4 = z2;
            } else {
                GDLog.DBGPRINTF(13, "FFFManager dstwaiwcoa11alsp");
            }
        } catch (InvalidAlgorithmParameterException e4) {
            e = e4;
            z4 = z2;
            GDLog.DBGPRINTF(12, "FFFManager AAACCC " + Log.getStackTraceString(e));
            z3 = z4;
            keyPairGenerator.generateKeyPair();
            doesKeyExist(str2);
            GDLog.DBGPRINTF(14, "FFFManager AAADDDXXX\n");
            return true;
        }
        try {
            if (Build.VERSION.SDK_INT >= 28 && isDeviceStrongBoxCapable() && canUseStrongBox() && z4) {
                GDLog.DBGPRINTF(14, "FFFManager AAA BB DD\n");
                userAuthenticationRequired.setIsStrongBoxBacked(true);
            }
            keyPairGenerator.initialize(userAuthenticationRequired.build());
            z3 = z4;
        } catch (InvalidAlgorithmParameterException e5) {
            e = e5;
            GDLog.DBGPRINTF(12, "FFFManager AAACCC " + Log.getStackTraceString(e));
            z3 = z4;
            keyPairGenerator.generateKeyPair();
            doesKeyExist(str2);
            GDLog.DBGPRINTF(14, "FFFManager AAADDDXXX\n");
            return true;
        }
        try {
            keyPairGenerator.generateKeyPair();
            doesKeyExist(str2);
            GDLog.DBGPRINTF(14, "FFFManager AAADDDXXX\n");
            return true;
        } catch (StrongBoxUnavailableException e6) {
            GDLog.DBGPRINTF(12, "FFFManager AAACCCFFF " + Log.getStackTraceString(e6));
            deleteKey(str2);
            return createKey(str, z, str2, i, false);
        } catch (ProviderException e7) {
            GDLog.DBGPRINTF(12, "FFFManager AAACCCDDD " + Log.getStackTraceString(e7));
            if (z) {
                deleteKey(str2);
                return createKey(str, false, str2, i, z3);
            }
            GDLog.DBGPRINTF(12, "FFFManager AAACCCEEE");
            throw new ProviderException(e7);
        }
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean createPasswordKey(boolean z) {
        GDLog.DBGPRINTF(14, "FFFManager FFF\n");
        if (!EnsureKeyStore()) {
            GDLog.DBGPRINTF(12, "FFFManager GGG\n");
            return false;
        }
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256", "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(GD_PASSWORD_KEY, 4);
            builder.setUserAuthenticationRequired(false);
            if (Build.VERSION.SDK_INT >= 28 && isDeviceStrongBoxCapable() && z) {
                GDLog.DBGPRINTF(12, "FFFManager III\n");
                builder.setIsStrongBoxBacked(true);
            }
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
            doesKeyExist(GD_PASSWORD_KEY);
            return true;
        } catch (StrongBoxUnavailableException e) {
            GDLog.DBGPRINTF(12, "FFFManager IIIDDD " + Log.getStackTraceString(e));
            return createPasswordKey(false);
        } catch (InvalidAlgorithmParameterException e2) {
            GDLog.DBGPRINTF(12, "FFFManager IIICCC" + Log.getStackTraceString(e2));
            return false;
        } catch (NoSuchAlgorithmException e3) {
            GDLog.DBGPRINTF(12, "FFFManager IIIAAA " + Log.getStackTraceString(e3));
            return false;
        } catch (NoSuchProviderException e4) {
            GDLog.DBGPRINTF(12, "FFFManager IIIBBB " + Log.getStackTraceString(e4));
            return false;
        } catch (Exception e5) {
            GDLog.DBGPRINTF(12, "FFFManager LLL" + Log.getStackTraceString(e5));
            return false;
        }
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean createProtectionKey(String str, boolean z, boolean z2) {
        boolean z3;
        GDLog.DBGPRINTF(14, "FFFManager CPK\n");
        if (!doesKeyExist(GD_USING_PROTECTION_COMPLETE_KEY)) {
            createUsingProtectionCompletedKey(str);
        }
        deleteKey(GD_PROTECTION_COMPLETE_KEY);
        createKey(str, z, GD_PROTECTION_KEY, 6, z2);
        if (z) {
            Certificate[] certificateArr = new Certificate[0];
            try {
                certificateArr = this.mKeyStore.getCertificateChain(GD_PROTECTION_KEY);
                z3 = true;
            } catch (KeyStoreException e) {
                GDLog.DBGPRINTF(12, "FFFManager AAACCCAAA " + Log.getStackTraceString(e) + "\n");
                z3 = false;
            }
            if (certificateArr != null) {
                try {
                    ((X509Certificate) certificateArr[0]).checkValidity();
                } catch (CertificateExpiredException e2) {
                    GDLog.DBGPRINTF(12, "FFFManager AAACCCBBB " + Log.getStackTraceString(e2) + "\n");
                    z3 = false;
                } catch (CertificateNotYetValidException e3) {
                    GDLog.DBGPRINTF(12, "FFFManager AAACCCCCC " + Log.getStackTraceString(e3) + "\n");
                    z3 = false;
                } catch (Exception e4) {
                    GDLog.DBGPRINTF(12, "FFFManager AAACCCDDD " + Log.getStackTraceString(e4) + "\n");
                    z3 = false;
                }
            }
            if (!z3) {
                deleteProtectionKey();
                boolean createProtectionKey = createProtectionKey(str, false, z2);
                if (createProtectionKey) {
                    createProtectionCompletedKey(str);
                }
                return createProtectionKey;
            }
        }
        createProtectionCompletedKey(str);
        return true;
    }

    public byte[] decryptBAMCKKey(byte[] bArr) {
        return decryptUsingSpecifiedKey(GD_BA_PROTECTION_KEY, bArr, true, true);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public byte[] decryptUsingBAKey(byte[] bArr, boolean z, boolean z2) {
        GDLog.DBGPRINTF(12, "FFFManager ppp2 \n");
        return decryptUsingSpecifiedKey(GD_BA_PROTECTION_KEY, bArr, z, z2);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public byte[] decryptUsingProtectionKey(byte[] bArr, boolean z, boolean z2) {
        GDLog.DBGPRINTF(12, "FFFManager ppp1 \n");
        return decryptUsingSpecifiedKey(GD_PROTECTION_KEY, bArr, z, z2);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public void deleteBAMCKKeys() {
        deleteKey(GD_BA_PROTECTION_KEY);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public void deletePasswordKey() {
        deleteKey(GD_PASSWORD_KEY);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public void deleteProtectionKey() {
        deleteKey(GD_PROTECTION_KEY);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean doesBAKeyExist() {
        return doesKeyExist(GD_BA_PROTECTION_KEY);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean doesPasswordKeyExist() {
        boolean doesKeyExist = doesKeyExist(GD_PASSWORD_KEY);
        GDLog.DBGPRINTF(16, "FFFManager MMM = " + doesKeyExist + "\n");
        return doesKeyExist;
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public boolean doesProtectionKeyExist() {
        boolean doesKeyExist = doesKeyExist(GD_PROTECTION_KEY);
        GDLog.DBGPRINTF(16, "FFFManager NNN = " + doesKeyExist + "\n");
        return doesKeyExist;
    }

    public byte[] encryptBAMCKKey(byte[] bArr) {
        return encryptUsingSpecifiedKey(GD_BA_PROTECTION_KEY, bArr, true, true);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public byte[] encryptUsingBAKey(byte[] bArr, boolean z, boolean z2) {
        return encryptUsingSpecifiedKey(GD_BA_PROTECTION_KEY, bArr, z, z2);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public byte[] encryptUsingProtectionKey(byte[] bArr, boolean z, boolean z2) {
        return encryptUsingSpecifiedKey(GD_PROTECTION_KEY, bArr, z, z2);
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public Certificate[] getCertificateChain(String str, String str2) {
        GDLog.DBGPRINTF(14, "FFFManager AAATTT GCC " + str2 + " \n");
        Certificate[] certificateArr = null;
        if (!EnsureKeyStore()) {
            return null;
        }
        if (!str2.equals(GD_ATTESTATION_KEY) && !str2.equals(GD_PROTECTION_KEY)) {
            throw new RuntimeException("Unexpected alias");
        }
        Certificate[] certificateArr2 = {generateFakeCertificate()};
        try {
            if (GD_ATTESTATION_KEY.equals(str2)) {
                certificateArr = getCertificatesForHWPeriodicAttestation(str);
            } else if (GD_PROTECTION_KEY.equals(str2)) {
                certificateArr = getCertificatesForHWAttestationAtActivation(str);
            }
            if (certificateArr != null) {
                if (certificateArr.length != 0) {
                    return certificateArr;
                }
            }
            return certificateArr2;
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "FTGCA " + Log.getStackTraceString(th));
            return certificateArr2;
        }
    }

    @Override // com.good.gd.utils.GDTEEDataInterface
    public byte[] usePasswordKey(byte[] bArr, boolean z) {
        int i;
        Mac mac;
        Mac mac2;
        GDLog.DBGPRINTF(16, "FFFManager UUU\n");
        if (!EnsureKeyStore()) {
            return null;
        }
        int i2 = 0;
        try {
            SecretKey secretKey = (SecretKey) this.mKeyStore.getKey(GD_PASSWORD_KEY, null);
            GDLog.DBGPRINTF(16, "FFFManager UUU KO\n");
            if (this.mSpecificSetProviderReqd) {
                mac = Mac.getInstance("HmacSHA256", "AndroidKeyStoreBCWorkaround");
            } else {
                mac = Mac.getInstance("HmacSHA256");
            }
            mac.init(secretKey);
            byte[] bArr2 = bArr;
            i = 0;
            while (i <= 10) {
                try {
                    bArr2 = mac.doFinal(bArr2);
                    i++;
                } catch (Exception e) {
                    try {
                        if (e.getClass().getName().equals("android.security.keystore.KeyStoreConnectException")) {
                            GDLog.DBGPRINTF(12, "FFFManager ZZZ CCC = " + i + "\n");
                            GDLog.DBGPRINTF(12, "FFFManager ZZZ DDD" + Log.getStackTraceString(e) + "\n");
                            EnsureKeyStore();
                            SecretKey secretKey2 = (SecretKey) this.mKeyStore.getKey(GD_PASSWORD_KEY, null);
                            if (this.mSpecificSetProviderReqd) {
                                mac2 = Mac.getInstance("HmacSHA256", "AndroidKeyStoreBCWorkaround");
                            } else {
                                mac2 = Mac.getInstance("HmacSHA256");
                            }
                            mac2.init(secretKey2);
                            bArr2 = bArr;
                            for (int i3 = 0; i3 <= 10; i3++) {
                                mac2.update(bArr2);
                                bArr2 = mac2.doFinal();
                                try {
                                    Thread.sleep(5L);
                                } catch (InterruptedException e2) {
                                }
                            }
                        } else {
                            throw e;
                        }
                    } catch (InvalidKeyException e3) {
                        e = e3;
                        i2 = i;
                        GDLog.DBGPRINTF(12, "FFFManager YYY " + Log.getStackTraceString(e) + "\n");
                        GDLog.DBGPRINTF(12, "FFFManager YYY BBB = " + i2 + "\n");
                        return null;
                    } catch (KeyStoreException e4) {
                        e = e4;
                        i2 = i;
                        GDLog.DBGPRINTF(12, "FFFManager VVV " + Log.getStackTraceString(e) + "\n");
                        GDLog.DBGPRINTF(12, "FFFManager VVV BBB = " + i2 + "\n");
                        return null;
                    } catch (NoSuchAlgorithmException e5) {
                        e = e5;
                        i2 = i;
                        GDLog.DBGPRINTF(12, "FFFManager WWW " + Log.getStackTraceString(e) + "\n");
                        GDLog.DBGPRINTF(12, "FFFManager WWW BBB = " + i2 + "\n");
                        return null;
                    } catch (UnrecoverableKeyException e6) {
                        e = e6;
                        i2 = i;
                        GDLog.DBGPRINTF(12, "FFFManager XXX " + Log.getStackTraceString(e) + "\n");
                        GDLog.DBGPRINTF(12, "FFFManager XXX BBB = " + i2 + "\n");
                        return null;
                    } catch (Exception e7) {
                        e = e7;
                        GDLog.DBGPRINTF(12, "FFFManager ZZZ " + Log.getStackTraceString(e) + "\n");
                        GDLog.DBGPRINTF(12, "FFFManager ZZZ BBB = " + i + "\n");
                        if (z) {
                            GDLog.DBGPRINTF(12, "FFFManager ZZZ AAA\n");
                            usePasswordKey(bArr, false);
                        } else {
                            this.mUtilInterface.UtilJ();
                        }
                        return null;
                    }
                }
            }
            Arrays.fill(bArr, (byte) 0);
            return bArr2;
        } catch (InvalidKeyException e8) {
            e = e8;
        } catch (KeyStoreException e9) {
            e = e9;
        } catch (NoSuchAlgorithmException e10) {
            e = e10;
        } catch (UnrecoverableKeyException e11) {
            e = e11;
        } catch (Exception e12) {
            e = e12;
            i = 0;
        }
    }
}
