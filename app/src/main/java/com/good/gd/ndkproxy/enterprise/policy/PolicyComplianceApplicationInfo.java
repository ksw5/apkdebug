package com.good.gd.ndkproxy.enterprise.policy;

import com.good.gt.context.GTBaseContext;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class PolicyComplianceApplicationInfo {
    private static PolicyComplianceApplicationInfo _instance;

    private PolicyComplianceApplicationInfo() throws Exception {
        ndkInit();
    }

    private String checkApplicationsForMalware(byte[][] bArr, byte[][] bArr2) {
        if (GTBaseContext.getInstance().getApplicationContext().getPackageManager().isSafeMode()) {
            return "Found";
        }
        for (byte[] bArr3 : bArr) {
            new ArrayList().add(Pattern.compile(new String(bArr3)));
        }
        new ArrayList();
        if (bArr2 == null) {
            return "";
        }
        for (byte[] bArr4 : bArr2) {
            new ArrayList().add(Pattern.compile(new String(bArr4)));
        }
        return "";
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:1|(8:2|3|4|5|7|8|9|10)|(7:59|(1:63)|66|16|17|18|(1:20)(1:22))(1:14)|15|16|17|18|(0)(0)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ad, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bc, code lost:
        r11 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c9, code lost:
        r9 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x016c, code lost:
        closeQuietly("InputStream", r12.getInputStream());
        closeQuietly("OutputStream", r12.getOutputStream());
        closeQuietly("ErrorStream", r12.getErrorStream());
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x018e, code lost:
        if (r12 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0190, code lost:
        r9 = r11;
        r13 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0192, code lost:
        r13.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ab, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b3, code lost:
        r12 = r9;
        r9 = r13;
        r13 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x010c, code lost:
        closeQuietly("InputStream", r13.getInputStream());
        closeQuietly("OutputStream", r13.getOutputStream());
        closeQuietly("ErrorStream", r13.getErrorStream());
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x012e, code lost:
        if (r13 != null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0130, code lost:
        r9 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0072, code lost:
        if (r0.contains("su") != false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0199 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x019c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x012e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private String checkPlatformIntegrity() {
        /*
            Method dump skipped, instructions count: 480
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ndkproxy.enterprise.policy.PolicyComplianceApplicationInfo.checkPlatformIntegrity():java.lang.String");
    }

    private String checkSystemPropertiesForCompliance(Map<String, Map<String, String>> map) {
        return map.containsKey("LUFC") ? "Invalid" : "";
    }

    private void closeQuietly(String str, Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        try {
            for (Closeable closeable : closeableArr) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (Exception e) {
        }
    }

    public static PolicyComplianceApplicationInfo getInstance() throws Exception {
        if (_instance == null) {
            _instance = new PolicyComplianceApplicationInfo();
        }
        return _instance;
    }

    native void ndkInit();
}
