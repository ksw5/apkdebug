package com.good.gd.ndkproxy.pki;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Build;
import android.security.KeyChain;
import android.security.KeyChainAliasCallback;
import android.security.KeyChainException;
import android.util.Log;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes.dex */
public class GDKeyChainManager {
    private static GDKeyChainManager instance;
    private ActivityProvider mActivityProvider;

    /* loaded from: classes.dex */
    public interface ActivityProvider {
        Activity onActivityRequested();
    }

    /* loaded from: classes.dex */
    class hbfhc implements KeyChainAliasCallback {
        hbfhc() {
        }

        @Override // android.security.KeyChainAliasCallback
        public void alias(String str) {
            GDLog.DBGPRINTF(16, "Selected alias: " + str);
            GDKeyChainManager.this.onAliasSelected(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class yfdke implements KeyChainAliasCallback {
        final /* synthetic */ CountDownLatch dbjc;

        yfdke(CountDownLatch countDownLatch) {
            this.dbjc = countDownLatch;
        }

        @Override // android.security.KeyChainAliasCallback
        public void alias(String str) {
            GDLog.DBGPRINTF(16, "selectAlias: Selected alias: " + str + "\n");
            this.dbjc.countDown();
        }
    }

    private GDKeyChainManager() {
    }

    private native void NDK_init();

    private static Certificate getCertificateByAlias(String str) throws KeyChainException, InterruptedException {
        return KeyChain.getCertificateChain(GTBaseContext.getInstance().getApplicationContext(), str)[0];
    }

    public static synchronized GDKeyChainManager getInstance() {
        GDKeyChainManager gDKeyChainManager;
        synchronized (GDKeyChainManager.class) {
            if (instance == null) {
                instance = new GDKeyChainManager();
            }
            gDKeyChainManager = instance;
        }
        return gDKeyChainManager;
    }

    private static boolean isSamsungDeviceUnderWorkProfile() {
        boolean z;
        boolean equalsIgnoreCase = Build.MANUFACTURER.equalsIgnoreCase("samsung");
        boolean z2 = Build.VERSION.SDK_INT >= 29;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getInstance().getActivity().getSystemService("device_policy");
        List<ComponentName> activeAdmins = devicePolicyManager.getActiveAdmins();
        if (activeAdmins == null) {
            z = false;
        } else {
            Iterator<ComponentName> it = activeAdmins.iterator();
            z = false;
            while (it.hasNext() && !(z = devicePolicyManager.isProfileOwnerApp(it.next().getPackageName()))) {
            }
        }
        return equalsIgnoreCase && z && z2;
    }

    private void methodA(String[] strArr, String[] strArr2, String str, int i, String str2) {
        Activity onActivityRequested = this.mActivityProvider.onActivityRequested();
        ArrayList arrayList = new ArrayList();
        if (strArr2 != null) {
            for (String str3 : strArr2) {
                arrayList.add(new X500Principal(str3));
            }
        }
        KeyChain.choosePrivateKeyAlias(onActivityRequested, new hbfhc(), strArr, (Principal[]) arrayList.toArray(new Principal[arrayList.size()]), str, i, str2);
    }

    private static byte[] methodD(byte[] bArr, String str) throws NoSuchPaddingException, InterruptedException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException, KeyChainException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, getCertificateByAlias(str).getPublicKey());
            return cipher.doFinal(bArr);
        } catch (KeyChainException e) {
            e = e;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalStateException e2) {
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e2));
            return new byte[0];
        } catch (InterruptedException e3) {
            e = e3;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (InvalidKeyException e4) {
            e = e4;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchAlgorithmException e5) {
            e = e5;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (BadPaddingException e6) {
            e = e6;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalBlockSizeException e7) {
            e = e7;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchPaddingException e8) {
            e = e8;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        }
    }

    private static byte[] methodE(byte[] bArr, String str) throws NoSuchPaddingException, InterruptedException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, KeyChainException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, KeyChain.getPrivateKey(GTBaseContext.getInstance().getApplicationContext(), str));
            return cipher.doFinal(bArr);
        } catch (KeyChainException e) {
            e = e;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalStateException e2) {
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e2));
            return new byte[0];
        } catch (InterruptedException e3) {
            e = e3;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (InvalidKeyException e4) {
            e = e4;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchAlgorithmException e5) {
            e = e5;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (BadPaddingException e6) {
            e = e6;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalBlockSizeException e7) {
            e = e7;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchPaddingException e8) {
            e = e8;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        }
    }

    private static byte[] methodF(byte[] bArr, String str, String str2) throws NoSuchPaddingException, InterruptedException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, KeyChainException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/" + str2);
            cipher.init(1, KeyChain.getPrivateKey(GTBaseContext.getInstance().getApplicationContext(), str));
            return cipher.doFinal(bArr);
        } catch (KeyChainException e) {
            e = e;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalStateException e2) {
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e2));
            return new byte[0];
        } catch (InterruptedException e3) {
            e = e3;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (InvalidKeyException e4) {
            e = e4;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchAlgorithmException e5) {
            e = e5;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (BadPaddingException e6) {
            e = e6;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalBlockSizeException e7) {
            e = e7;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchPaddingException e8) {
            e = e8;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        }
    }

    private static byte[] methodG(byte[] bArr, String str, String str2) throws NoSuchPaddingException, InterruptedException, NoSuchAlgorithmException, BadPaddingException, KeyChainException, InvalidKeyException, IllegalBlockSizeException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/" + str2);
            cipher.init(2, KeyChain.getPrivateKey(GTBaseContext.getInstance().getApplicationContext(), str));
            return cipher.doFinal(bArr);
        } catch (KeyChainException e) {
            e = e;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalStateException e2) {
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e2));
            return new byte[0];
        } catch (InterruptedException e3) {
            e = e3;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (InvalidKeyException e4) {
            e = e4;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchAlgorithmException e5) {
            e = e5;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (BadPaddingException e6) {
            e = e6;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (IllegalBlockSizeException e7) {
            e = e7;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        } catch (NoSuchPaddingException e8) {
            e = e8;
            GDLog.DBGPRINTF(12, Log.getStackTraceString(e));
            throw e;
        }
    }

    private static byte[][] methodH(String str, String str2) throws InterruptedException, CertificateEncodingException {
        try {
            X509Certificate[] certificateChain = KeyChain.getCertificateChain(GTBaseContext.getInstance().getApplicationContext(), str);
            if (certificateChain == null && isSamsungDeviceUnderWorkProfile() && selectAlias(str, str2)) {
                GDLog.DBGPRINTF(16, "methodH: failed to retrieve certs from credential storage - retrying after re-selecting alias\n");
                certificateChain = KeyChain.getCertificateChain(GTBaseContext.getInstance().getApplicationContext(), str);
                if (certificateChain == null) {
                    GDLog.DBGPRINTF(13, "methodH: failed to retrieve certs from credential storage after retry\n");
                }
            }
            int i = 0;
            if (certificateChain != null) {
                byte[][] bArr = new byte[certificateChain.length];
                int length = certificateChain.length;
                int i2 = 0;
                while (i < length) {
                    bArr[i2] = certificateChain[i].getEncoded();
                    i++;
                    i2++;
                }
                return bArr;
            }
            GDLog.DBGPRINTF(16, "methodH: cert chain is null \n");
            return (byte[][]) Array.newInstance(byte.class, 0, 0);
        } catch (KeyChainException e) {
            e.printStackTrace();
            return (byte[][]) Array.newInstance(byte.class, 0, 0);
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return (byte[][]) Array.newInstance(byte.class, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void onAliasSelected(String str);

    public static native void runTest();

    private static boolean selectAlias(String str, String str2) {
        boolean z = false;
        if (str != null && !str.isEmpty()) {
            if (str2 != null && !str2.isEmpty()) {
                try {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new X500Principal(str2));
                    Principal[] principalArr = (Principal[]) arrayList.toArray(new Principal[arrayList.size()]);
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    KeyChain.choosePrivateKeyAlias(getInstance().getActivity(), new yfdke(countDownLatch), null, principalArr, "", -1, str);
                    try {
                        z = countDownLatch.await(5L, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        GDLog.DBGPRINTF(16, "selectAlias: timeout waiting for alias selection \n");
                        e.printStackTrace();
                    }
                    GDLog.DBGPRINTF(16, "selectAlias: result: " + z + "\n");
                    return z;
                } catch (Exception e2) {
                    GDLog.DBGPRINTF(16, "selectAlias: issuer is malformed \n", e2);
                    return false;
                }
            }
            GDLog.DBGPRINTF(16, "selectAlias: issuer is not provided \n");
            return false;
        }
        GDLog.DBGPRINTF(16, "selectAlias: alias is not provided \n");
        return false;
    }

    public Activity getActivity() {
        return this.mActivityProvider.onActivityRequested();
    }

    public void init(ActivityProvider activityProvider) {
        if (this.mActivityProvider == null) {
            NDK_init();
            this.mActivityProvider = activityProvider;
            return;
        }
        throw new RuntimeException("gkcm already initialized.");
    }

    public byte[] methodB(String str, byte[] bArr, String str2) throws NoSuchAlgorithmException, KeyChainException, InvalidKeyException, SignatureException, InterruptedException {
        try {
            Signature signature = Signature.getInstance(str);
            signature.initSign(KeyChain.getPrivateKey(GTBaseContext.getInstance().getApplicationContext(), str2));
            signature.update(bArr);
            GDLog.DBGPRINTF(16, "gkcmb: processing " + bArr.length + " bytes of data.");
            byte[] sign = signature.sign();
            GDLog.DBGPRINTF(16, "gkcmb: returning " + sign.length + " bytes.");
            return sign;
        } catch (KeyChainException e) {
            e = e;
            GDLog.DBGPRINTF(16, e.toString());
            throw e;
        } catch (IllegalStateException e2) {
            GDLog.DBGPRINTF(16, e2.toString());
            return new byte[0];
        } catch (InterruptedException e3) {
            GDLog.DBGPRINTF(16, "interrupted exception ", e3);
            throw e3;
        } catch (NullPointerException e4) {
            GDLog.DBGPRINTF(16, "cannot process data", e4);
            throw new RuntimeException(e4);
        } catch (InvalidKeyException e5) {
            e = e5;
            GDLog.DBGPRINTF(16, e.toString());
            throw e;
        } catch (NoSuchAlgorithmException e6) {
            e = e6;
            GDLog.DBGPRINTF(16, e.toString());
            throw e;
        } catch (SignatureException e7) {
            e = e7;
            GDLog.DBGPRINTF(16, e.toString());
            throw e;
        }
    }

    public boolean methodC(String str, byte[] bArr, byte[] bArr2, String str2) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(str);
            signature.initVerify(getCertificateByAlias(str2));
            signature.update(bArr);
            GDLog.DBGPRINTF(16, "gkcmc: processing " + bArr.length + " bytes of data.");
            boolean verify = signature.verify(bArr2);
            GDLog.DBGPRINTF(16, "gkcmc: returning " + verify);
            return verify;
        } catch (KeyChainException e) {
            e = e;
            e.printStackTrace();
            return false;
        } catch (IllegalStateException e2) {
            e = e2;
            e.printStackTrace();
            return false;
        } catch (InterruptedException e3) {
            e = e3;
            e.printStackTrace();
            return false;
        } catch (NullPointerException e4) {
            e = e4;
            GDLog.DBGPRINTF(12, "cannot process data,", e);
            return false;
        } catch (InvalidKeyException e5) {
            GDLog.DBGPRINTF(12, "invalid private key", e5);
            return false;
        } catch (NoSuchAlgorithmException e6) {
            e = e6;
            GDLog.DBGPRINTF(12, "cannot process data,", e);
            return false;
        } catch (SignatureException e7) {
            GDLog.DBGPRINTF(12, "processing exception", e7);
            throw e7;
        }
    }
}
