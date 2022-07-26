package com.good.gd.backup;

import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.bold360.natwest.UtilsKt;
import com.good.gd.client.GDClient;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.file.GDFileSystemImpl;
import com.good.gd.utils.GDInit;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* loaded from: classes.dex */
public final class GDFileBackupHelper extends FileBackupHelper {
    private GDBackupService _backupService;

    public GDFileBackupHelper(Context context, String... strArr) {
        super(context, GDBackupService.wxau());
        this._backupService = null;
        this._backupService = new GDBackupService(context, strArr);
    }

    @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        this._backupService.qkduk();
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
        this._backupService.dbjc();
    }

    @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
        this._backupService.jwxax();
        this._backupService.dbjc();
    }

    /* loaded from: classes.dex */
    static class GDBackupService {
        private String[] dbjc;
        private Context qkduk;

        public GDBackupService(Context context, String... strArr) {
            this.dbjc = null;
            this.qkduk = null;
            this.qkduk = context;
            if (GDInit.isInitialized() && GDClient.getInstance().isAuthorized()) {
                this.dbjc = GDFileSystemImpl.backupList(strArr);
            } else {
                this.dbjc = new String[0];
            }
        }

        private void dbjc(File file, File file2, ZipOutputStream zipOutputStream) throws FileNotFoundException, IOException {
            FileInputStream fileInputStream = new FileInputStream(file2);
            zipOutputStream.putNextEntry(new ZipEntry(file2.getCanonicalPath().substring(file.getCanonicalPath().length() + 1, file2.getCanonicalPath().length())));
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read >= 0) {
                    zipOutputStream.write(bArr, 0, read);
                } else {
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                    return;
                }
            }
        }

        public static String[] wxau() {
            return new String[]{"__.gd"};
        }

        private String ztwf() {
            return this.qkduk.getDir(UtilsKt.Data, 0).getAbsolutePath();
        }

        public void jwxax() {
            try {
                ZipFile zipFile = new ZipFile(new File(this.qkduk.getFilesDir(), "__.gd"));
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                File file = new File(ztwf());
                if (!file.exists()) {
                    file.mkdir();
                }
                while (entries.hasMoreElements()) {
                    try {
                        dbjc(file, zipFile, entries.nextElement());
                    } catch (Exception e) {
                        GDLog.DBGPRINTF(12, "GDBackupService.decompress exception", e);
                    }
                }
                zipFile.close();
            } catch (FileNotFoundException e2) {
                GDLog.DBGPRINTF(12, "GDBackupService.decompress exception", e2);
            } catch (IOException e3) {
                GDLog.DBGPRINTF(12, "GDBackupService.decompress exception", e3);
            }
        }

        public void qkduk() {
            int length = this.dbjc.length;
            File[] fileArr = new File[length];
            for (int i = 0; i < this.dbjc.length; i++) {
                fileArr[i] = new File(ztwf() + "/" + this.dbjc[i]);
            }
            File file = new File(ztwf());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(this.qkduk.getFilesDir(), "__.gd"));
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                for (int i2 = 0; i2 < length; i2++) {
                    File file2 = fileArr[i2];
                    if (!file2.isDirectory()) {
                        dbjc(file, file2, zipOutputStream);
                    }
                }
                zipOutputStream.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                GDLog.DBGPRINTF(12, "GDBackupService.compress exception", e);
            } catch (IOException e2) {
                GDLog.DBGPRINTF(12, "GDBackupService.decompress exception", e2);
            }
        }

        private void dbjc(File file, ZipFile zipFile, ZipEntry zipEntry) throws FileNotFoundException, IOException {
            if (zipEntry.isDirectory()) {
                return;
            }
            File file2 = new File(file, zipEntry.getName());
            file2.getParentFile().mkdirs();
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            FileOutputStream fileOutputStream = new FileOutputStream(file2.getAbsolutePath());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read >= 0) {
                    bufferedOutputStream.write(bArr, 0, read);
                } else {
                    inputStream.close();
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                    return;
                }
            }
        }

        public void dbjc() {
            File file = new File(this.qkduk.getFilesDir(), "__.gd");
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
