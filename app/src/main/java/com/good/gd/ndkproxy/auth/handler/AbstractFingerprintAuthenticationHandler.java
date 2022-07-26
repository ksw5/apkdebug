package com.good.gd.ndkproxy.auth.handler;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;

/* loaded from: classes.dex */
public abstract class AbstractFingerprintAuthenticationHandler implements GDFingerprintAuthenticationHandler {
    protected static final String KEYSTORE_NAME = "AndroidKeyStore";
    protected static String KEY_ALIAS = "UK";
    protected static final String KEY_ALIAS_BASE = "UK";
    protected BBDAndroidAuthenticator bbdAndroidAuthenticator;
    private KeyStore keyStore;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractFingerprintAuthenticationHandler() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_NAME);
        this.keyStore = keyStore;
        keyStore.load(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void createKeyStoreKey() throws GeneralSecurityException;

    /* JADX INFO: Access modifiers changed from: protected */
    public void deleteKeyStoreKey(String str) {
        boolean z;
        try {
            z = this.keyStore.containsAlias(str);
        } catch (KeyStoreException e) {
            printUnexpectedException(e);
            z = true;
        }
        if (z) {
            try {
                GDLog.DBGPRINTF(14, "Deleting fingerprint key\n");
                this.keyStore.deleteEntry(str);
                return;
            } catch (KeyStoreException e2) {
                printUnexpectedException(e2);
                return;
            }
        }
        GDLog.DBGPRINTF(14, "Fingerprint key not found for deletion\n");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void ensureKeyStoreKey() throws GeneralSecurityException {
        if (!hasKeyStoreKey()) {
            GDLog.DBGPRINTF(14, "Creating fingerprint key\n");
            createKeyStoreKey();
            if (!hasKeyStoreKey()) {
                throw new GeneralSecurityException("Fingerprint key not created!?");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[][] fromBlob(byte[] bArr) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        byte[][] bArr2 = null;
        try {
            if (dataInputStream.readInt() != getHandlerType()) {
                GDLog.DBGPRINTF(12, "Blob type mismatch \n");
                return null;
            }
            int readInt = dataInputStream.readInt();
            if (readInt >= 0) {
                byte[][] bArr3 = new byte[readInt];
                for (int i = 0; i < readInt; i++) {
                    bArr3[i] = new byte[dataInputStream.readInt()];
                    dataInputStream.read(bArr3[i]);
                }
                bArr2 = bArr3;
            }
            try {
                dataInputStream.close();
            } catch (IOException e) {
            }
            return bArr2;
        } catch (Throwable th) {
            try {
                GDLog.DBGPRINTF(13, "Failed to read blob " + th + "\n");
                try {
                    dataInputStream.close();
                } catch (IOException e2) {
                }
                return null;
            } finally {
                try {
                    dataInputStream.close();
                } catch (IOException e3) {
                }
            }
        }
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public BBDAndroidAuthenticator getBBDAndroidAuthenticator() {
        return this.bbdAndroidAuthenticator;
    }

    protected KeyStore.Entry getKeyStoreEntry() throws GeneralSecurityException {
        return this.keyStore.getEntry(KEY_ALIAS, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Key getKeyStoreKey() throws GeneralSecurityException {
        return this.keyStore.getKey(KEY_ALIAS, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasKeyStoreKey(String str) {
        try {
            boolean containsAlias = this.keyStore.containsAlias(str);
            GDLog.DBGPRINTF(14, "Fingerprint ", str, " hKSK ", String.valueOf(containsAlias), "\n");
            return containsAlias;
        } catch (KeyStoreException e) {
            printUnexpectedException(e);
            return false;
        }
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void initCrypto(boolean z) {
        if (z || isListeningForFingerprint()) {
            return;
        }
        deleteKeyStoreKey();
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isCryptoReady() {
        return hasKeyStoreKey();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void printUnexpectedException(Exception exc) {
        GDLog.DBGPRINTF(12, "Unexpected " + exc + "\n");
        StackTraceElement[] stackTrace = exc.getStackTrace();
        int length = stackTrace.length;
        for (int i = 0; i < length; i++) {
            GDLog.DBGPRINTF(16, "Exception details - " + stackTrace[i] + "\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public byte[] toBlob(byte[]... bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeInt(getHandlerType());
            dataOutputStream.writeInt(bArr.length);
            for (byte[] bArr2 : bArr) {
                dataOutputStream.writeInt(bArr2.length);
                dataOutputStream.write(bArr2);
            }
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            GDLog.DBGPRINTF(12, "Failed to write blob " + e + "\n");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasKeyStoreKey() {
        return hasKeyStoreKey(KEY_ALIAS);
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void deleteKeyStoreKey() {
        deleteKeyStoreKey(KEY_ALIAS);
    }
}
