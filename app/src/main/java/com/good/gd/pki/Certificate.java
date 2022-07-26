package com.good.gd.pki;

import java.util.Date;

/* loaded from: classes.dex */
public class Certificate {
    protected byte[] binaryX509DER;
    protected int binaryX509DERLength;
    protected String certificateMD5;
    protected String certificateSHA1;
    protected String issuer;
    protected String keyUsage;
    protected Date notAfterDate;
    protected Date notBeforeDate;
    protected String publicKeyMD5;
    protected String publicKeySHA1;
    protected String serialNumber;
    protected String subjectAlternativeName;
    protected String subjectName;
    protected int version;

    public byte[] getBinaryX509DER() {
        return this.binaryX509DER;
    }

    public int getBinaryX509DERLength() {
        return this.binaryX509DERLength;
    }

    public String getCertificateMD5() {
        return this.certificateMD5;
    }

    public String getCertificateSHA1() {
        return this.certificateSHA1;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public String getKeyUsage() {
        return this.keyUsage;
    }

    public Date getNotAfterDate() {
        return this.notAfterDate;
    }

    public Date getNotBeforeDate() {
        return this.notBeforeDate;
    }

    public String getPublicKeyMD5() {
        return this.publicKeyMD5;
    }

    public String getPublicKeySHA1() {
        return this.publicKeySHA1;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getSubjectAlternativeName() {
        return this.subjectAlternativeName;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public int getVersion() {
        return this.version;
    }
}
