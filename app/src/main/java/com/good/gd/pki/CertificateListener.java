package com.good.gd.pki;

/* loaded from: classes.dex */
public interface CertificateListener {
    void onCertificateRemoved(Certificate certificate);

    void onCertificatedAdded(Certificate certificate);
}
