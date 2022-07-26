package com.good.gd.ndkproxy;

import android.util.Log;
import com.good.gd.utils.GDTEEDataInterface;
import com.good.gd.utils.GDTEEManagerImpl;
import java.security.cert.Certificate;

/* loaded from: classes.dex */
public class GDUtilInterface {
    GDTEEDataInterface mInterface;

    public GDUtilInterface(GDTEEDataInterface gDTEEDataInterface) {
        this.mInterface = gDTEEDataInterface;
        ndkInit();
    }

    private boolean UtilA() {
        return this.mInterface.createPasswordKey(false);
    }

    private boolean UtilB() {
        return this.mInterface.doesPasswordKeyExist();
    }

    private byte[] UtilC(byte[] bArr) {
        return this.mInterface.usePasswordKey(bArr, true);
    }

    private void UtilD() {
        this.mInterface.deletePasswordKey();
    }

    private boolean UtilE(String str) {
        return this.mInterface.createProtectionKey(str, true, true);
    }

    private boolean UtilF() {
        return this.mInterface.doesProtectionKeyExist();
    }

    private byte[] UtilG(byte[] bArr) {
        return this.mInterface.encryptUsingProtectionKey(bArr, true, true);
    }

    private byte[] UtilH(byte[] bArr) {
        return this.mInterface.decryptUsingProtectionKey(bArr, true, true);
    }

    private void UtilI() {
        this.mInterface.deleteProtectionKey();
    }

    private Certificate[] UtilK(String str, String str2) {
        return this.mInterface.getCertificateChain(str, GDTEEManagerImpl.GD_ATTESTATION_KEY);
    }

    private Certificate[] UtilL(String str, String str2) {
        try {
            return this.mInterface.getCertificateChain(str, GDTEEManagerImpl.GD_PROTECTION_KEY);
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "UtilL ", Log.getStackTraceString(th));
            return new Certificate[0];
        }
    }

    private boolean UtilXA() {
        return this.mInterface.doesBAKeyExist();
    }

    private byte[] UtilXB(byte[] bArr) {
        return this.mInterface.encryptUsingBAKey(bArr, true, true);
    }

    private byte[] UtilXC(byte[] bArr) {
        return this.mInterface.decryptUsingBAKey(bArr, true, true);
    }

    private void UtilXD() {
        this.mInterface.deleteBAMCKKeys();
    }

    private boolean UtilXE() {
        return this.mInterface.createBAMMCKKeys();
    }

    private native void clearProtectionKeyComplete();

    private native void ndkInit();

    private native void processExit();

    private native void setProtectionKeyComplete();

    public void UtilJ() {
        processExit();
    }

    public void UtilX() {
        setProtectionKeyComplete();
    }

    public void UtilY() {
        clearProtectionKeyComplete();
    }
}
