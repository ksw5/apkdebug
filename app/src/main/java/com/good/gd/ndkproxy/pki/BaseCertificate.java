package com.good.gd.ndkproxy.pki;

import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.pki.Certificate;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class BaseCertificate extends Certificate {
    private void setBinaryX509DER(byte[] bArr) {
        this.binaryX509DER = bArr;
    }

    private void setBinaryX509DERLength(int i) {
        this.binaryX509DERLength = i;
    }

    private void setCertificateMD5(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setCertificateMD5() : Certificate with unsupported encoding for certificateMD5\n");
            e.printStackTrace();
            str = null;
        }
        this.certificateMD5 = str;
    }

    private void setCertificateSHA1(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setCertificateSHA1() : Certificate with unsupported encoding for certificateSHA1\n");
            e.printStackTrace();
            str = null;
        }
        this.certificateSHA1 = str;
    }

    private void setIssuer(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setSubjectAlternativeName() : Certificate with unsupported encoding for issuer\n");
            e.printStackTrace();
            str = null;
        }
        this.issuer = str;
    }

    private void setKeyUsage(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setNotAfterDate() : Certificate with unsupported encoding for keyUsage\n");
            e.printStackTrace();
            str = null;
        }
        this.keyUsage = str;
    }

    private void setNotAfterDate(byte[] bArr) {
        String str;
        Date date = null;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setNotAfterDate() : Certificate with unsupported encoding for notAfterDate\n");
            e.printStackTrace();
            str = null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d HH:mm:ss yyyy zzz", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            GDLog.DBGPRINTF(12, "Certificate.setNotAfterDate() : Certificate with unsupported format for setNotAfterDate\n");
            e2.printStackTrace();
        }
        this.notAfterDate = date;
    }

    private void setNotBeforeDate(byte[] bArr) {
        String str;
        Date date = null;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setNotBeforeDate() : Certificate with unsupported encoding for notBeforeDate\n");
            e.printStackTrace();
            str = null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d HH:mm:ss yyyy zzz", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            GDLog.DBGPRINTF(12, "Certificate.setNotBeforeDate() : Certificate with unsupported format for notBeforeDate\n");
            e2.printStackTrace();
        }
        this.notBeforeDate = date;
    }

    private void setPublicKeyMD5(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setPublicKeyMD5() : Certificate with unsupported encoding for publicKeyMD5\n");
            e.printStackTrace();
            str = null;
        }
        this.publicKeyMD5 = str;
    }

    private void setPublicKeySHA1(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setPublicKeySHA1() : Certificate with unsupported encoding for publicKeySHA1\n");
            e.printStackTrace();
            str = null;
        }
        this.publicKeySHA1 = str;
    }

    private void setSerialNumber(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setSubjectAlternativeName() : Certificate with unsupported encoding for serialNumber\n");
            e.printStackTrace();
            str = null;
        }
        this.serialNumber = str;
    }

    private void setSubjectAlternativeName(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setSubjectAlternativeName() : Certificate with unsupported encoding for subjectAlternativeName\n");
            e.printStackTrace();
            str = null;
        }
        this.subjectAlternativeName = str;
    }

    private void setSubjectName(byte[] bArr) {
        String str;
        try {
            str = new String(bArr, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "Certificate.setSubjectAlternativeName() : Certificate with unsupported encoding for subjectName\n");
            e.printStackTrace();
            str = null;
        }
        this.subjectName = str;
    }

    private void setVersion(int i) {
        this.version = i;
    }

    private void setNotBeforeDate(int i) {
        Date date = new Date();
        date.setTime(i * 1000);
        this.notBeforeDate = date;
    }

    private void setNotAfterDate(int i) {
        Date date = new Date();
        date.setTime(i * 1000);
        this.notAfterDate = date;
    }
}
