package com.good.gd.icc;

import com.good.gt.icc.GTServicesException;

/* loaded from: classes.dex */
public final class GDServiceErrorHandler {
    public static GDServiceError GDServiceError(GDServiceErrorCode gDServiceErrorCode, String str) {
        return new GDServiceError(gDServiceErrorCode, str);
    }

    public static GDServiceException GDServiceException(GDServiceErrorCode gDServiceErrorCode, String str) {
        return new GDServiceException(gDServiceErrorCode, str);
    }

    public static GDServiceException GDServiceException(GTServicesException gTServicesException) {
        GDServiceError gDServiceError = new GDServiceError(gTServicesException);
        return new GDServiceException(gDServiceError.getErrorCode(), gDServiceError.getMessage());
    }
}
