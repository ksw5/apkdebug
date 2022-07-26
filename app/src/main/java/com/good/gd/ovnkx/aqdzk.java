package com.good.gd.ovnkx;

import com.good.gd.apache.http.protocol.HTTP;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public class aqdzk {
    private File dbjc;
    private ehnkx jwxax;
    private final Class qkduk;

    public aqdzk(File file, String str) {
        this.qkduk = aqdzk.class;
        ehnkx odlf = com.blackberry.bis.core.yfdke.odlf();
        this.jwxax = odlf;
        if (file != null) {
            this.dbjc = odlf.createFile(file, str);
        } else {
            this.dbjc = odlf.createFile(str);
        }
    }

    public boolean dbjc() throws IOException {
        return this.dbjc.createNewFile();
    }

    public boolean jwxax() {
        return this.dbjc.exists();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v2 */
    public String liflu() {
        InputStream inputStream;
        String absolutePath = this.dbjc.getAbsolutePath();
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ?? r3 = 0;
        try {
            try {
                inputStream = this.jwxax.qkduk(absolutePath);
                while (true) {
                    try {
                        int read = inputStream.read(bArr);
                        if (read != -1) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            String byteArrayOutputStream2 = byteArrayOutputStream.toString(HTTP.UTF_8);
                            mjbm.dbjc(inputStream);
                            return byteArrayOutputStream2;
                        }
                    } catch (IOException e) {
                        com.good.gd.kloes.hbfhc.qkduk(this.qkduk, "File can't be loaded.");
                        mjbm.dbjc(inputStream);
                        return null;
                    }
                }
            } catch (Throwable th) {
                th = th;
                r3 = absolutePath;
                mjbm.dbjc((InputStream) r3);
                throw th;
            }
        } catch (IOException e2) {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            mjbm.dbjc((InputStream) r3);
            throw th;
        }
    }

    public long lqox() {
        return this.dbjc.length();
    }

    public boolean qkduk() {
        return this.dbjc.delete();
    }

    public File wxau() {
        return this.dbjc;
    }

    public InputStream ztwf() throws FileNotFoundException {
        return this.jwxax.dbjc(this.dbjc);
    }

    public boolean dbjc(String str) {
        OutputStream outputStream = null;
        try {
            try {
                outputStream = this.jwxax.dbjc(this.dbjc.getAbsolutePath());
                outputStream.write(str.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                mjbm.dbjc(outputStream);
                return true;
            } catch (IOException e) {
                com.good.gd.kloes.hbfhc.qkduk(this.qkduk, "File can't be saved.");
                mjbm.dbjc(outputStream);
                return false;
            }
        } catch (Throwable th) {
            mjbm.dbjc(outputStream);
            throw th;
        }
    }

    public OutputStream dbjc(boolean z) throws FileNotFoundException {
        return com.blackberry.bis.core.yfdke.odlf().dbjc(this.dbjc, z);
    }

    public aqdzk(String str) {
        this.qkduk = aqdzk.class;
        ehnkx odlf = com.blackberry.bis.core.yfdke.odlf();
        this.jwxax = odlf;
        this.dbjc = odlf.createFile(str);
    }

    public aqdzk(File file) {
        this.qkduk = aqdzk.class;
        this.jwxax = com.blackberry.bis.core.yfdke.odlf();
        this.dbjc = file;
    }
}
