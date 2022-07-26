package com.good.gd.ujgjo;

import android.content.Context;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class hbfhc {
    private static hbfhc lqox;
    private final Context dbjc;
    private com.good.gd.ovnkx.aqdzk jwxax;
    protected boolean qkduk;
    private com.good.gd.ovnkx.aqdzk wxau;
    private final Class ztwf = getClass();

    public hbfhc(Context context) {
        this.dbjc = context;
        lqox = this;
    }

    public static hbfhc jcpqe() {
        return lqox;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0039, code lost:
        if (r7.equals("historical") == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dbjc(String r7, com.good.gd.ovnkx.aqdzk r8) {
        /*
            r6 = this;
            java.lang.Class r0 = r6.ztwf
            java.util.Locale r1 = java.util.Locale.getDefault()
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r4 = 0
            r3[r4] = r7
            java.lang.String r5 = "Updating event log fileWrapper for %s events."
            java.lang.String r1 = java.lang.String.format(r1, r5, r3)
            com.good.gd.kloes.hbfhc.wxau(r0, r1)
            boolean r0 = com.good.gd.whhmi.yfdke.qkduk(r7)
            if (r0 == 0) goto L23
            java.lang.Class r7 = r6.ztwf
            java.lang.String r8 = "Unable to update event log fileWrapper, event type is required."
            com.good.gd.kloes.hbfhc.wxau(r7, r8)
            return
        L23:
            r0 = -1
            int r1 = r7.hashCode()
            r3 = 1876566912(0x6fda2380, float:1.350213E29)
            if (r1 == r3) goto L3c
            r3 = 1950555338(0x74431cca, float:6.1833606E31)
            if (r1 == r3) goto L33
            goto L46
        L33:
            java.lang.String r1 = "historical"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L46
            goto L47
        L3c:
            java.lang.String r1 = "appIntelligence"
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L46
            r4 = r2
            goto L47
        L46:
            r4 = r0
        L47:
            if (r4 == 0) goto L4f
            if (r4 == r2) goto L4c
            goto L51
        L4c:
            r6.wxau = r8
            goto L51
        L4f:
            r6.jwxax = r8
        L51:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ujgjo.hbfhc.dbjc(java.lang.String, com.good.gd.ovnkx.aqdzk):void");
    }

    public File jwxax(String str) {
        com.good.gd.ovnkx.aqdzk jwxax = com.good.gd.ovnkx.mjbm.jwxax(com.good.gd.ovnkx.mjbm.wxau(str));
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    public abstract boolean jwxax();

    public synchronized void liflu() {
        this.qkduk = false;
    }

    public synchronized void lqox() {
        this.qkduk = true;
    }

    public synchronized OutputStream qkduk(String str, boolean z) throws IOException {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(this.ztwf, "Unable to get output stream, event type is required.");
            return null;
        }
        long ztwf = com.good.gd.ovnkx.mjbm.ztwf(str);
        com.good.gd.ovnkx.aqdzk wxau = wxau(str);
        if (wxau == null || true != wxau.jwxax() || wxau.lqox() >= ztwf) {
            com.good.gd.kloes.hbfhc.wxau(this.ztwf, String.format(Locale.getDefault(), "Get new file wrapper for %s events.", str));
            wxau = com.good.gd.ovnkx.mjbm.qkduk(str, jwxax(str));
            dbjc(str, wxau);
        }
        return wxau.dbjc(z);
    }

    public com.good.gd.ovnkx.aqdzk wxau(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        if (c == 0) {
            return this.jwxax;
        }
        if (c == 1) {
            return this.wxau;
        }
        return null;
    }

    public abstract boolean wxau();

    public File ztwf(String str) {
        com.good.gd.ovnkx.aqdzk jwxax = com.good.gd.ovnkx.mjbm.jwxax(com.good.gd.ovnkx.mjbm.jcpqe(str));
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    public abstract boolean ztwf();

    public File lqox(String str) {
        com.good.gd.ovnkx.aqdzk jwxax = com.good.gd.ovnkx.mjbm.jwxax(com.good.gd.ovnkx.mjbm.tlske(str));
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    public File dbjc() {
        File filesDir = this.dbjc.getFilesDir();
        File file = filesDir != null ? new File(filesDir, "ndk_crash") : new File("ndk_crash");
        if (true != file.exists()) {
            com.good.gd.kloes.hbfhc.wxau(this.ztwf, "Creating NDK Crash Directory.");
            file.mkdir();
        }
        return file;
    }

    public File qkduk() {
        try {
            File file = new File(dbjc(), "ndk_crash_timestamp_file.txt");
            if (true != file.exists()) {
                file.createNewFile();
            }
            com.good.gd.kloes.hbfhc.wxau(this.ztwf, "Get NDK Crash Log File");
            com.good.gd.kloes.ehnkx.dbjc(this.ztwf, "NDK Crash Log File" + file.getAbsolutePath());
            return file;
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.ztwf, "Failed to get NDK Crash File.");
            return null;
        }
    }

    public InputStream dbjc(File file) throws FileNotFoundException {
        return new com.good.gd.ovnkx.aqdzk(file).ztwf();
    }

    public OutputStream dbjc(String str, boolean z) throws FileNotFoundException {
        com.good.gd.kloes.ehnkx.qkduk(this.ztwf, "Get Output Stream for File: " + str);
        return new com.good.gd.ovnkx.aqdzk(str).dbjc(z);
    }

    public void dbjc(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(this.ztwf, "Unable to create new event file, event type is required.");
            return;
        }
        OutputStream outputStream = null;
        try {
            lqox.lqox();
            lqox.dbjc(str, com.good.gd.ovnkx.mjbm.jwxax(str, jwxax(str)));
            outputStream = lqox.qkduk(str, true);
            com.good.gd.ovnkx.mjbm.dbjc(outputStream, this.dbjc);
            lqox.liflu();
            if (outputStream == null) {
                return;
            }
        } catch (IOException e) {
            lqox.liflu();
            if (outputStream == null) {
                return;
            }
        } catch (Throwable th) {
            lqox.liflu();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e2) {
                }
            }
            throw th;
        }
        try {
            outputStream.close();
        } catch (IOException e3) {
        }
    }

    public boolean qkduk(String str) {
        return new com.good.gd.ovnkx.aqdzk(str).qkduk();
    }

    public void dbjc(String str, OutputStream outputStream) throws IOException {
        com.good.gd.ovnkx.aqdzk wxau = wxau(str);
        if (wxau == null || 0 != wxau.lqox()) {
            return;
        }
        com.good.gd.ovnkx.mjbm.dbjc(outputStream, this.dbjc);
    }
}
