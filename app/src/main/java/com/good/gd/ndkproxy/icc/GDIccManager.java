package com.good.gd.ndkproxy.icc;

import android.widget.Toast;
import com.good.gd.file.File;
import com.good.gd.file.FileInputStream;
import com.good.gd.file.FileOutputStream;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.GTInteger;
import com.good.gt.util.ByteArrayBuffer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/* loaded from: classes.dex */
final class GDIccManager {
    private static GDIccManager tlske;
    private int enterpriseUserNumber;
    private boolean jcpqe;
    private int jwxax;
    private String lqox;
    private int migratedFromUserNumber;
    private String migrationEnrolAddress;
    private BufferedInputStream qkduk;
    private BufferedOutputStream ztwf;
    private Object dbjc = new Object();
    private Object wxau = new Object();
    private Object[] certificateData = new Object[3];
    private Vector<String> liflu = new Vector<>();

    private GDIccManager() {
        this.jcpqe = false;
        this.jcpqe = true;
    }

    private native boolean _getConnectionData();

    private native void _getEnterpriseUserNumber();

    private native void _getMigratedFromUserNumber();

    private native void _getMigrationEnrolAddress();

    private native boolean _verifyCertificate(byte[] bArr, byte[] bArr2, int i, boolean z);

    private native boolean _verifyEnterpriseUserNumber(byte[] bArr);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized GDIccManager liflu() {
        GDIccManager gDIccManager;
        synchronized (GDIccManager.class) {
            if (tlske == null) {
                tlske = new GDIccManager();
            }
            gDIccManager = tlske;
        }
        return gDIccManager;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean dbjc(String str, ByteArrayBuffer byteArrayBuffer, ByteArrayBuffer byteArrayBuffer2, ByteArrayBuffer byteArrayBuffer3, GTInteger gTInteger) {
        GDLog.DBGPRINTF(16, "GDIccManager.onReadyToConnect IN app=" + str + "\n");
        GDLog.DBGPRINTF(16, "GDIccManager.onReadyToConnect nativeresult=" + _getConnectionData() + "\n");
        Object[] objArr = this.certificateData;
        byte[] bArr = (byte[]) objArr[0];
        byte[] bArr2 = (byte[]) objArr[1];
        if (objArr[0] != null && objArr[1] != null) {
            if (byteArrayBuffer != null) {
                GDLog.DBGPRINTF(16, "+ GDIccManager.onReadyToConnect certlen=" + bArr.length + "\n");
                byteArrayBuffer.append(bArr, 0, bArr.length);
            }
            if (byteArrayBuffer2 != null) {
                GDLog.DBGPRINTF(16, "GDIccManager.onReadyToConnect keylen=" + bArr2.length + "\n");
                byteArrayBuffer2.append(bArr2, 0, bArr2.length);
            }
            if (gTInteger != null) {
                GDLog.DBGPRINTF(16, "GDIccManager.onReadyToConnect eun\n");
                gTInteger.setValue(this.enterpriseUserNumber);
            }
            Object[] objArr2 = this.certificateData;
            if (objArr2[2] != null && byteArrayBuffer3 != null) {
                byte[] bArr3 = (byte[]) objArr2[2];
                byteArrayBuffer3.append(bArr3, 0, bArr3.length);
            }
            GDLog.DBGPRINTF(16, "GDIccManager.onReadyToConnect cert ok\n");
            Object[] objArr3 = this.certificateData;
            objArr3[0] = null;
            objArr3[1] = null;
            objArr3[2] = null;
            return true;
        }
        GDLog.DBGPRINTF(12, "GDIccManager.onReadyToConnect - missing cert/key data\n");
        return false;
    }

    public void finalize() throws Throwable {
        lqox();
        ztwf();
        super.finalize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int jwxax() {
        _getMigratedFromUserNumber();
        return this.migratedFromUserNumber;
    }

    void lqox() {
        GDLog.DBGPRINTF(16, "+ GDIccManager.sendCleanup IN\n");
        synchronized (this.dbjc) {
            BufferedInputStream bufferedInputStream = this.qkduk;
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    GDLog.DBGPRINTF(12, "+ GDIccManager.sendCleanup exception", e);
                }
                this.qkduk = null;
            }
        }
        GDLog.DBGPRINTF(16, "- GDIccManager.sendCleanup OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int qkduk() {
        _getEnterpriseUserNumber();
        return this.enterpriseUserNumber;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String wxau() {
        _getMigrationEnrolAddress();
        return this.migrationEnrolAddress;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void ztwf() {
        GDLog.DBGPRINTF(16, "+ GDIccManager.receiveCleanup IN\n");
        synchronized (this.wxau) {
            this.lqox = null;
            BufferedOutputStream bufferedOutputStream = this.ztwf;
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    GDLog.DBGPRINTF(12, "+ GDIccManager.receiveCleanup exception\n", e);
                }
                this.ztwf = null;
            }
            for (int i = 0; i < this.liflu.size(); i++) {
                GDLog.DBGPRINTF(16, "GDIccManager.onReceiveMessage - clearing file\n");
                if (!new File(this.liflu.elementAt(i)).delete()) {
                    GDLog.DBGPRINTF(12, "GDIccManager.onReceiveMessage - failed to delete\n");
                }
            }
            this.liflu.clear();
            GDLog.DBGPRINTF(16, "- GDIccManager.receiveCleanup OUT\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dbjc(byte[] bArr, byte[] bArr2, boolean z) {
        if (bArr != null && bArr2 != null) {
            boolean _verifyCertificate = _verifyCertificate(bArr, bArr2, this.enterpriseUserNumber, z);
            GDLog.DBGPRINTF(16, "- GDIccManager.didConnectToApplication verified=" + _verifyCertificate + "\n");
            return _verifyCertificate;
        }
        GDLog.DBGPRINTF(12, "+ GDIccManager.didConnectToApplication - one or both of the certs is null\n");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dbjc(byte[] bArr, int i, String str, boolean z) {
        GDLog.DBGPRINTF(16, "+ GDIccManager.didReceiveAttachmentDataForFile IN\n");
        synchronized (this.wxau) {
            if ((i == 0 || bArr != null) && i >= 0 && str != null) {
                try {
                    try {
                        if (!str.equals(this.lqox)) {
                            File file = new File(str);
                            if (!new File(file.getParent()).mkdirs()) {
                                GDLog.DBGPRINTF(12, "- GDIccManager.didReceiveAttachmentDataForFile - couldn't create directories\n");
                                return false;
                            }
                            BufferedOutputStream bufferedOutputStream = this.ztwf;
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                            this.ztwf = new BufferedOutputStream(new FileOutputStream(file));
                            this.lqox = str;
                            this.liflu.add(str);
                        }
                        if (i > 0) {
                            this.ztwf.write(bArr, 0, i);
                        }
                        if (z) {
                            if (this.jcpqe) {
                                String localizedString = GDLocalizer.getLocalizedString("Secure Data");
                                if (localizedString.length() > 0) {
                                    Toast.makeText(GTBaseContext.getInstance().getApplicationContext(), localizedString, 0).show();
                                }
                                this.jcpqe = false;
                            }
                            this.ztwf.close();
                        }
                        GDLog.DBGPRINTF(16, "- GDIccManager.didReceiveAttachmentDataForFile ok\n");
                        return true;
                    } catch (FileNotFoundException e) {
                        GDLog.DBGPRINTF(12, "- GDIccManager.didReceiveAttachmentDataForFile - file not found\n");
                        ztwf();
                        return false;
                    }
                } catch (IOException e2) {
                    GDLog.DBGPRINTF(12, "- GDIccManager.didReceiveAttachmentDataForFile - problem writing file data\n");
                    ztwf();
                    return false;
                }
            }
            GDLog.DBGPRINTF(12, "- GDIccManager.didReceiveAttachmentDataForFile problem with params\n");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dbjc(byte[] bArr, GTInteger gTInteger, String str, GTInteger gTInteger2, boolean z) {
        GDLog.DBGPRINTF(16, "+ GDIccManager.readyToSendAttachmentData IN\n");
        synchronized (this.dbjc) {
            if (bArr == null) {
                try {
                    if (gTInteger.getValue() == 0) {
                    }
                    GDLog.DBGPRINTF(12, "- GDIccManager.readyToSendAttachmentData problem with params\n");
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (str != null && gTInteger.getValue() >= 0) {
                if (z) {
                    try {
                        try {
                            GDLog.DBGPRINTF(16, "+ GDIccManager.readyToSendAttachmentData new file\n");
                            File file = new File(str);
                            BufferedInputStream bufferedInputStream = this.qkduk;
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                                this.qkduk = null;
                            }
                            this.qkduk = new BufferedInputStream(new FileInputStream(file));
                            this.jwxax = (int) file.length();
                            GDLog.DBGPRINTF(16, "+ GDIccManager.readyToSendAttachmentData - file size: " + this.jwxax + "\n");
                        } catch (FileNotFoundException e) {
                            GDLog.DBGPRINTF(12, "- GDIccManager.readyToSendAttachmentData - file not found\n");
                            lqox();
                            return false;
                        }
                    } catch (IOException e2) {
                        GDLog.DBGPRINTF(12, "- GDIccManager.readyToSendAttachmentData - IOException\n");
                        lqox();
                        return false;
                    }
                }
                int read = this.qkduk.read(bArr);
                GDLog.DBGPRINTF(16, "- GDIccManager.readyToSendAttachmentData read " + read + " bytes from bis\n");
                gTInteger2.setValue(this.jwxax);
                if (read == -1) {
                    GDLog.DBGPRINTF(16, "- GDIccManager.readyToSendAttachmentData - EOF\n");
                    gTInteger.setValue(0);
                    if (this.jwxax == 0) {
                        GDLog.DBGPRINTF(16, "- GDIccManager.readyToSendAttachmentData - read size: " + gTInteger.getValue() + "\n");
                        return true;
                    }
                    lqox();
                    return false;
                }
                gTInteger.setValue(read);
                GDLog.DBGPRINTF(16, "- GDIccManager.readyToSendAttachmentData - read size: " + gTInteger.getValue() + "\n");
                return true;
            }
            GDLog.DBGPRINTF(12, "- GDIccManager.readyToSendAttachmentData problem with params\n");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc() {
        GDLog.DBGPRINTF(16, "GDIccManager.commitFiles IN\n");
        synchronized (this.wxau) {
            this.liflu.clear();
            ztwf();
        }
        GDLog.DBGPRINTF(16, "GDIccManager.commitFiles OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc(int i, String str) {
        GDLog.DBGPRINTF(12, "GDIccManager.onConnectionError code=" + i + " msg=" + str + "\n");
        ztwf();
        lqox();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean dbjc(byte[] bArr) {
        return _verifyEnterpriseUserNumber(bArr);
    }
}
