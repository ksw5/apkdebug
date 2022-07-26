package com.good.gd.icc;

import com.good.gt.icc.GTServicesException;

/* loaded from: classes.dex */
public enum GDServiceErrorCode {
    GDServicesErrorGeneral(GTServicesException.Code.SERVICES_GENERAL),
    GDServicesErrorApplicationNotFound(GTServicesException.Code.SERVICES_APP_NOT_FOUND),
    GDServicesErrorServiceNotFound(GTServicesException.Code.SERVICES_SERVICE_NOT_FOUND),
    GDServicesErrorServiceVersionNotFound(GTServicesException.Code.SERVICES_SERVICE_VERSION_NOT_FOUND),
    GDServicesErrorMethodNotFound(GTServicesException.Code.SERVICES_METHOD_NOT_FOUND),
    GDServicesErrorNotAllowed(GTServicesException.Code.SERVICES_NOT_ALLOWED),
    GDServicesErrorInvalidParams(GTServicesException.Code.SERVICES_INVALID_PARAMS),
    GDServicesErrorInUse(GTServicesException.Code.SERVICES_IN_USE),
    GDServicesErrorCertificateNotFound(GTServicesException.Code.SERVICES_CERTIFICATE_NOT_FOUND),
    GDServicesErrorMethodDisabled(GTServicesException.Code.SERVICES_METHOD_DISABLED),
    GDServicesErrorVersionDisabled(GTServicesException.Code.SERVICES_VERSION_DISABLED),
    GDServicesErrorServiceDisabled(GTServicesException.Code.SERVICES_SERVICE_DISABLED),
    GDServicesErrorEnterpriseUserNotMatch(GTServicesException.Code.SERVICES_ENTERPRISE_USER_NOT_MATCH),
    GDServicesErrorCustom;
    
    private final GTServicesException.Code underlyingCode;

    GDServiceErrorCode(GTServicesException.Code code) {
        this.underlyingCode = code;
    }

    public int getValue() {
        GTServicesException.Code code = this.underlyingCode;
        if (code != null) {
            return code.errValue();
        }
        return 0;
    }

    public static GDServiceErrorCode valueOf(int i) {
        GDServiceErrorCode[] values;
        for (GDServiceErrorCode gDServiceErrorCode : values()) {
            if (gDServiceErrorCode.getValue() == i) {
                return gDServiceErrorCode;
            }
        }
        return null;
    }

    GDServiceErrorCode() {
        this.underlyingCode = null;
    }
}
