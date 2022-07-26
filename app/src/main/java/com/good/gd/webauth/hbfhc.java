package com.good.gd.webauth;

import android.net.Uri;
import android.util.Base64;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.ndkproxy.crypto.GTCrypto;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
class hbfhc {
    private final Uri dbjc;
    private final String jcpqe;
    private final String jwxax;
    private final String liflu;
    private final String lqox;
    private final String qkduk;
    private final String tlske;
    private final String wxau;
    private final String ztwf;

    private hbfhc(Uri uri, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.dbjc = uri;
        this.qkduk = str;
        this.jwxax = str2;
        this.wxau = str3;
        this.ztwf = str4;
        this.lqox = str5;
        this.liflu = str6;
        this.jcpqe = str7;
        this.tlske = str8;
    }

    public static hbfhc dbjc(Uri uri, String str, String str2) {
        String str3;
        String str4;
        String substring = Base64.encodeToString(GTCrypto.getRandomStringBase64(64), 11).substring(0, 64);
        String substring2 = Base64.encodeToString(GTCrypto.getRandomStringBase64(64), 11).substring(0, 64);
        String encodeToString = Base64.encodeToString(GTCrypto.getRandomStringBase64(96), 11);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(encodeToString.getBytes(StandardCharsets.US_ASCII));
            str4 = Base64.encodeToString(messageDigest.digest(), 11);
            str3 = "S256";
            return new hbfhc(uri, str, AuthCodeResponse.AUTH_CODE_KEY, substring, substring2, str2, encodeToString, str4, str3);
        } catch (NoSuchAlgorithmException e) {
            try {
                GDLog.DBGPRINTF(12, "SHA-256 is not supported on this device");
                throw e;
            } catch (NoSuchAlgorithmException e2) {
                str3 = "plain";
                str4 = encodeToString;
            }
        }
    }

    public String jcpqe() {
        return this.lqox;
    }

    public String jwxax() {
        return this.tlske;
    }

    public String liflu() {
        return this.jwxax;
    }

    public Uri lqox() {
        return this.dbjc;
    }

    public String qkduk() {
        return this.jcpqe;
    }

    public String tlske() {
        return this.wxau;
    }

    public String wxau() {
        return this.liflu;
    }

    public String ztwf() {
        return this.ztwf;
    }

    public String dbjc() {
        return this.qkduk;
    }
}
