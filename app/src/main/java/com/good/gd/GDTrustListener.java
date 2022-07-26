package com.good.gd;

import android.graphics.Bitmap;
import java.util.Date;

/* loaded from: classes.dex */
public interface GDTrustListener {
    void authenticateWithTrust(GDTrust gDTrust);

    void authenticateWithTrustWarn(GDTrust gDTrust, String str, Bitmap bitmap);

    void authenticateWithTrustWarn(GDTrust gDTrust, String str, Bitmap bitmap, Date date, int i);

    void securityPolicyDidChange(GDTrust gDTrust);
}
