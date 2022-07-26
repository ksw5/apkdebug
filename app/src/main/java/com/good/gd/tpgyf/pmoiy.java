package com.good.gd.tpgyf;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class pmoiy extends com.good.gd.ovnkx.mjbm {
    private static final Class qkduk = pmoiy.class;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean jwxax(File file) {
        com.good.gd.ovnkx.ehnkx odlf = com.blackberry.bis.core.yfdke.odlf();
        if (file != null) {
            OutputStream outputStream = null;
            try {
                try {
                    outputStream = odlf.qkduk(file);
                    outputStream.flush();
                    com.good.gd.kloes.hbfhc.wxau(qkduk, "GD File truncated.");
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        com.good.gd.kloes.hbfhc.qkduk(qkduk, "Failed to close output stream while truncating file.");
                    }
                    return true;
                } catch (IOException e2) {
                    com.good.gd.kloes.hbfhc.qkduk(qkduk, "Failed to truncate file.");
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e3) {
                            com.good.gd.kloes.hbfhc.qkduk(qkduk, "Failed to close output stream while truncating file.");
                        }
                    }
                    return false;
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e4) {
                        com.good.gd.kloes.hbfhc.qkduk(qkduk, "Failed to close output stream while truncating file.");
                    }
                }
                throw th;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static File ztwf(String str, File file) throws IOException {
        com.good.gd.kloes.ehnkx.dbjc(qkduk, "Get Log GD File: [" + file.getAbsolutePath() + "]");
        int liflu = com.good.gd.ovnkx.mjbm.liflu(str);
        long ztwf = com.good.gd.ovnkx.mjbm.ztwf(str);
        File dbjc = com.good.gd.ovnkx.mjbm.dbjc(str, file, ".txt");
        if (true != (dbjc != null && dbjc.length() < ztwf)) {
            com.good.gd.ovnkx.mjbm.dbjc(str, file, liflu);
            File createFile = com.blackberry.bis.core.yfdke.odlf().createFile(file.getAbsolutePath() + com.blackberry.bis.core.yfdke.odlf().dbjc() + com.good.gd.ovnkx.mjbm.dbjc(str, "yyyyMMdd_HH:mm:ss.SSS", ".txt"));
            createFile.createNewFile();
            return createFile;
        }
        return dbjc;
    }
}
