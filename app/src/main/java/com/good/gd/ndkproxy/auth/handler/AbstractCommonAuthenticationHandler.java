package com.good.gd.ndkproxy.auth.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Base64;
import android.util.Log;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.PasswordType;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.android.AuthenticationCallback;
import com.good.gd.ndkproxy.auth.biometric.data.BiometricDataCreator;
import com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider;
import com.good.gt.context.GTBaseContext;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* loaded from: classes.dex */
public abstract class AbstractCommonAuthenticationHandler extends AbstractFingerprintWorkaroundProvider implements GDFingerprintAuthenticationHandler {
    public static final int ERROR_BIOMETRIC_NOT_SUPPORTED = -2;
    public static final int ERROR_SDK_INTERNAL = -3;
    public static final int ERROR_USER_NOT_AUTHENTICATED = -1;
    protected static final String HASH_SEPARATOR = ";";
    protected static final String KEY_ALGORITHM = "AES";
    protected static final String KEY_BLOCK_MODE = "CBC";
    protected static final String KEY_PADDING = "PKCS7Padding";
    protected static final int KEY_SIZE_BITS = 256;
    protected static CryptoLogic decryptCryptoLogic;
    protected static CryptoLogic encryptCryptoLogic;
    protected volatile boolean isListening;
    protected WeakReference<GDFingerprintAuthenticationCallback> latestAuthenticationCallbackWeakRef;
    protected WeakReference<MyCancellationSignal> latestCancellationSignal;
    protected Handler vendorHandler;
    protected boolean isScreenJustUnlocked = false;
    protected final BiometricDataProvider biometricDataProvider = BiometricDataCreator.getInstance().getBiometricDataProvider();

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public abstract class CryptoLogic implements AuthenticationCallback {
        private final GDFingerprintAuthenticationCallback cb;
        private Cipher cipher;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDLog.DBGPRINTF(14, "Received fingerprint cancelled\n");
                if (AbstractCommonAuthenticationHandler.this.isScreenJustUnlocked) {
                    GDLog.DBGPRINTF(14, "Processed fingerprint cancelled\n");
                    CryptoLogic.this.cb.onAuthenticationFailed();
                    AbstractCommonAuthenticationHandler.this.isScreenJustUnlocked = false;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public class yfdke implements Runnable {
            yfdke() {
            }

            @Override // java.lang.Runnable
            public void run() {
                GDLog.DBGPRINTF(14, "Received fingerprint cancelled\n");
                if (AbstractCommonAuthenticationHandler.this.isScreenJustUnlocked) {
                    GDLog.DBGPRINTF(14, "Processed fingerprint cancelled\n");
                    AbstractCommonAuthenticationHandler.this.isScreenJustUnlocked = false;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public CryptoLogic(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback) {
            this.cb = gDFingerprintAuthenticationCallback;
        }

        private void handleFaceUnlockException() {
            this.cb.onAuthenticationError(-2, "Selected biometric option is not supported");
            AbstractCommonAuthenticationHandler.this.clearAllCryptologic();
        }

        private void handleGeneralSecurityException(GeneralSecurityException generalSecurityException) {
            AbstractCommonAuthenticationHandler.this.printExceptionStackTrace(generalSecurityException);
            if (this.cb.getAuthenticatorType() != 1) {
                GDLog.DBGPRINTF(13, "Remove key from keystore and user needs to activate fingerprint later\n");
                AbstractCommonAuthenticationHandler.this.deleteKeyStoreKey();
                GDFingerprintAuthenticationManager.getInstance().resetActivation();
                this.cb.onCryptoFailure(generalSecurityException, false);
                return;
            }
            try {
                GDLog.DBGPRINTF(13, "Rebuild key in keystore and retry fingerprint scan\n");
                AbstractCommonAuthenticationHandler.this.deleteKeyStoreKey();
                AbstractCommonAuthenticationHandler.this.ensureKeyStoreKey();
            } catch (GeneralSecurityException e) {
                GDLog.DBGPRINTF(13, "Exception creating key store " + e + "\n");
            }
            this.cb.onCryptoFailure(generalSecurityException, true);
        }

        private boolean handleIllegalBlockSizeException(IllegalBlockSizeException illegalBlockSizeException) {
            return false;
        }

        private void handleKeyPermanentlyInvalidatedException(Exception exc) throws GeneralSecurityException {
            GDLog.DBGPRINTF(13, "Key has been permanently invalidated");
            StackTraceElement[] stackTrace = exc.getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < length; i++) {
                GDLog.DBGPRINTF(16, "Exception details - " + stackTrace[i] + "\n");
            }
            this.cipher = null;
            AbstractCommonAuthenticationHandler.this.deleteKeyStoreKey();
            AbstractCommonAuthenticationHandler.this.ensureKeyStoreKey();
            this.cb.handleKeyPermanentlyInvalidated();
            AbstractCommonAuthenticationHandler.this.clearAllCryptologic();
        }

        private void handleUserNotAuthenticatedException(Exception exc) {
            GDLog.DBGPRINTF(12, "FingerprintAuthHandler passive biometry was used\n");
            AbstractCommonAuthenticationHandler.this.printExceptionStackTrace(exc);
            this.cb.onAuthenticationError(-1, exc.getMessage());
            AbstractCommonAuthenticationHandler.this.clearAllCryptologic();
        }

        public GDFingerprintAuthenticationCancellationSignal authenticate() {
            AbstractCommonAuthenticationHandler.this.latestAuthenticationCallbackWeakRef = new WeakReference<>(this.cb);
            MyCancellationSignal myCancellationSignal = new MyCancellationSignal();
            AbstractCommonAuthenticationHandler.this.latestCancellationSignal = new WeakReference<>(myCancellationSignal);
            AbstractCommonAuthenticationHandler.this.bbdAndroidAuthenticator.authenticate(this.cipher, myCancellationSignal.cancellationSignal, this);
            return myCancellationSignal;
        }

        protected boolean createCipher() throws GeneralSecurityException {
            Cipher cipher = this.cipher;
            if (cipher != null && cipher.getIV() != null) {
                GDLog.DBGPRINTF(16, "Cipher object is already exist and initialized\n");
                return true;
            } else if (!AbstractCommonAuthenticationHandler.this.ensureKeystoreFaceUnlockSafe()) {
                handleFaceUnlockException();
                return false;
            } else {
                this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                try {
                    initCipher(this.cipher, (SecretKey) AbstractCommonAuthenticationHandler.this.getKeyStoreKey());
                } catch (KeyPermanentlyInvalidatedException e) {
                    e = e;
                    handleKeyPermanentlyInvalidatedException(e);
                    return false;
                } catch (UserNotAuthenticatedException e2) {
                    handleUserNotAuthenticatedException(e2);
                } catch (ClassCastException e3) {
                    e = e3;
                    handleKeyPermanentlyInvalidatedException(e);
                    return false;
                } catch (IllegalBlockSizeException e4) {
                    handleIllegalBlockSizeException(e4);
                }
                return true;
            }
        }

        public GDFingerprintAuthenticationCallback getAuthenticationCallback() {
            return this.cb;
        }

        public Cipher getCipher() {
            return this.cipher;
        }

        protected abstract void initCipher(Cipher cipher, SecretKey secretKey) throws GeneralSecurityException;

        @Override // com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
            GDLog.DBGPRINTF(13, "FingerprintAuthHandler onAuthenticationError error = " + i + " errorString = " + ((Object) charSequence) + "\n");
            AbstractCommonAuthenticationHandler.this.isListening = false;
        }

        @Override // com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationFailed() {
            AbstractCommonAuthenticationHandler.this.isListening = false;
            this.cb.onAuthenticationFailed();
        }

        @Override // com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
            AbstractCommonAuthenticationHandler.this.isListening = false;
            if (this.cb.checkFingerprintAuthenticationExpiry()) {
                return;
            }
            this.cb.onAuthenticationHelp(i, charSequence);
        }

        @Override // com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationSucceeded(Cipher cipher) {
            AbstractCommonAuthenticationHandler.this.isListening = false;
            if (!this.cb.checkFingerprintAuthenticationExpiry()) {
                try {
                    transform(cipher);
                } catch (UserNotAuthenticatedException e) {
                    handleUserNotAuthenticatedException(e);
                } catch (IllegalBlockSizeException e2) {
                    if (!handleIllegalBlockSizeException(e2)) {
                        handleGeneralSecurityException(e2);
                    }
                } catch (GeneralSecurityException e3) {
                    handleGeneralSecurityException(e3);
                }
            }
            AbstractCommonAuthenticationHandler.this.clearAllCryptologic();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onFingerprintCancelled() {
            if ("samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
                AbstractCommonAuthenticationHandler.this.vendorHandler.postDelayed(new hbfhc(), 500L);
            } else {
                AbstractCommonAuthenticationHandler.this.vendorHandler.postDelayed(new yfdke(), 500L);
            }
        }

        protected abstract void transform(Cipher cipher) throws GeneralSecurityException;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public class MyCancellationSignal implements GDFingerprintAuthenticationCancellationSignal {
        private final CancellationSignal cancellationSignal = new CancellationSignal();

        protected MyCancellationSignal() {
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal
        public void cancel() {
            synchronized (this) {
                AbstractCommonAuthenticationHandler.this.isListening = false;
                WeakReference<MyCancellationSignal> weakReference = AbstractCommonAuthenticationHandler.this.latestCancellationSignal;
                if ((weakReference == null ? null : weakReference.get()) == this) {
                    this.cancellationSignal.cancel();
                    AbstractCommonAuthenticationHandler.this.latestCancellationSignal.clear();
                    AbstractCommonAuthenticationHandler.this.latestCancellationSignal = null;
                    AbstractCommonAuthenticationHandler.this.clearAllCryptologic();
                }
            }
        }

        public CancellationSignal getCancellationSignal() {
            return this.cancellationSignal;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc extends BroadcastReceiver {
        private final AbstractCommonAuthenticationHandler dbjc;

        public hbfhc(AbstractCommonAuthenticationHandler abstractCommonAuthenticationHandler) {
            this.dbjc = abstractCommonAuthenticationHandler;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(14, "GDFingerprintVendorHandler: screen unlock receiver\n");
            this.dbjc.onScreenUnlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractCommonAuthenticationHandler() throws Exception {
        initVendorHandler();
    }

    private String createHash(byte[] bArr) {
        byte[] createHash = GDFingerprintAuthenticationManager.getInstance().createHash(bArr);
        return createHash != null ? Base64.encodeToString(createHash, 2) : PasswordType.SMNOTYETSET;
    }

    private byte[][] getValidColdStartData(byte[] bArr) {
        byte[][] fromBlob = fromBlob(bArr);
        if (fromBlob == null || fromBlob.length != 2) {
            return null;
        }
        return fromBlob;
    }

    private void initVendorHandler() {
        hbfhc hbfhcVar = new hbfhc(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        GTBaseContext.getInstance().getApplicationContext().registerReceiver(hbfhcVar, intentFilter);
        this.vendorHandler = new Handler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenUnlock() {
        WeakReference<GDFingerprintAuthenticationCallback> weakReference;
        GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback;
        if (this.isListening && (weakReference = this.latestAuthenticationCallbackWeakRef) != null && (gDFingerprintAuthenticationCallback = weakReference.get()) != null) {
            GDLog.DBGPRINTF(14, "GDFingerprintVendorHandler: authentication timed out\n");
            gDFingerprintAuthenticationCallback.onAuthenticationTimedOut();
            this.isListening = false;
            this.latestAuthenticationCallbackWeakRef.clear();
        }
        this.isScreenJustUnlocked = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printExceptionStackTrace(Exception exc) {
        GDLog.DBGPRINTF(13, "FingerprintAuthHandler  printExceptionStackTrace - FAILED - Exception = " + Log.getStackTraceString(exc) + "\n");
    }

    protected void clearAllCryptologic() {
        encryptCryptoLogic = null;
        decryptCryptoLogic = null;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public String createColdStartDataHashString(byte[] bArr) {
        SecretKey secretKey;
        String str = PasswordType.SMNOTYETSET;
        try {
            if (((SecretKey) getKeyStoreKey()) == null) {
                GDLog.DBGPRINTF(12, "Unable to create cold start data hash\n");
            } else {
                str = (((("" + bArr.length) + HASH_SEPARATOR) + createHash(bArr)) + HASH_SEPARATOR) + secretKey.hashCode();
            }
        } catch (GeneralSecurityException e) {
            GDLog.DBGPRINTF(12, "Unable to create cold start data hash" + e + "\n");
        }
        GDLog.DBGPRINTF(14, "Created fingerprint cold start data hash = '" + str + "'\n");
        return str;
    }

    @Override // com.good.gd.ndkproxy.auth.handler.AbstractFingerprintAuthenticationHandler
    public void createKeyStoreKey() throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM, "AndroidKeyStore");
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(AbstractFingerprintAuthenticationHandler.KEY_ALIAS, 3);
        builder.setBlockModes(KEY_BLOCK_MODE).setEncryptionPaddings(KEY_PADDING).setKeySize(256).setUserAuthenticationRequired(true);
        if (Build.VERSION.SDK_INT >= 30) {
            builder.setUserAuthenticationParameters(0, 2);
        }
        keyGenerator.init(builder.build());
        keyGenerator.generateKey();
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean hasUserEnrolledFingerprints() {
        boolean hasEnrolledFingerprints = this.biometricDataProvider.hasEnrolledFingerprints();
        GDLog.DBGPRINTF(14, "hasUserEnrolledFingerprints = " + hasEnrolledFingerprints + "\n");
        return hasEnrolledFingerprints;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isDeviceFingerprintCapable() {
        return this.biometricDataProvider.isHardwareDetected();
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isKeyguardSecure() {
        return true;
    }

    abstract CryptoLogic obtainDecryptCryptologic(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr, byte[] bArr2);

    abstract CryptoLogic obtainEncryptCryptologic(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr);

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void startFingerprintEnrollment(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 30) {
            intent = new Intent("android.settings.BIOMETRIC_ENROLL");
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.putExtra("android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED", 15);
        } else {
            intent = new Intent("android.settings.SECURITY_SETTINGS");
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        context.startActivity(intent);
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public GDFingerprintAuthenticationCancellationSignal startListeningForDecrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException, IllegalArgumentException {
        byte[][] validColdStartData = getValidColdStartData(bArr);
        if (validColdStartData != null) {
            byte[] bArr2 = validColdStartData[0];
            byte[] bArr3 = validColdStartData[1];
            this.isListening = true;
            CryptoLogic obtainDecryptCryptologic = obtainDecryptCryptologic(gDFingerprintAuthenticationCallback, bArr2, bArr3);
            if (!obtainDecryptCryptologic.createCipher()) {
                this.isListening = false;
                return null;
            }
            GDLog.DBGPRINTF(14, "Current fingerprint cold start data hash = '" + bArr.length + HASH_SEPARATOR + createHash(bArr) + HASH_SEPARATOR + ((SecretKey) getKeyStoreKey()).hashCode() + "'\n");
            return obtainDecryptCryptologic.authenticate();
        }
        throw new IllegalArgumentException("Encrypted blob is bad");
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException {
        this.isListening = true;
        CryptoLogic obtainEncryptCryptologic = obtainEncryptCryptologic(gDFingerprintAuthenticationCallback, bArr);
        if (!obtainEncryptCryptologic.createCipher()) {
            this.isListening = false;
            return null;
        }
        return obtainEncryptCryptologic.authenticate();
    }
}
