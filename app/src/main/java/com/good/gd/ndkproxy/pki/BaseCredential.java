package com.good.gd.ndkproxy.pki;

import com.good.gd.pki.Credential;

/* loaded from: classes.dex */
public class BaseCredential extends Credential {
    private void setAuxCertificates(BaseCertificate[] baseCertificateArr) {
        this.auxCertificates = baseCertificateArr;
    }

    private void setUserCertificate(BaseCertificate baseCertificate) {
        this.userCertificate = baseCertificate;
    }
}
