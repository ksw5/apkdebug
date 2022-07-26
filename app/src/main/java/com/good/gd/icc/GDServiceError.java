package com.good.gd.icc;

import com.good.gt.icc.GTServicesException;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class GDServiceError {
    private int customErrorCode;
    private Object details;
    private final GDServiceErrorCode errCode;
    private String message;

    /* renamed from: com.good.gd.icc.GDServiceError$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] dbjc;

        static {
            int[] iArr = new int[GTServicesException.Code.values().length];
            dbjc = iArr;
            try {
                GTServicesException.Code code = GTServicesException.Code.SERVICES_GENERAL;
                iArr[0] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                int[] iArr2 = dbjc;
                GTServicesException.Code code2 = GTServicesException.Code.SERVICES_APP_NOT_FOUND;
                iArr2[1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                int[] iArr3 = dbjc;
                GTServicesException.Code code3 = GTServicesException.Code.SERVICES_SERVICE_NOT_FOUND;
                iArr3[5] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                int[] iArr4 = dbjc;
                GTServicesException.Code code4 = GTServicesException.Code.SERVICES_SERVICE_VERSION_NOT_FOUND;
                iArr4[6] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                int[] iArr5 = dbjc;
                GTServicesException.Code code5 = GTServicesException.Code.SERVICES_METHOD_NOT_FOUND;
                iArr5[4] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                int[] iArr6 = dbjc;
                GTServicesException.Code code6 = GTServicesException.Code.SERVICES_NOT_ALLOWED;
                iArr6[2] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                int[] iArr7 = dbjc;
                GTServicesException.Code code7 = GTServicesException.Code.SERVICES_INVALID_PARAMS;
                iArr7[3] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                int[] iArr8 = dbjc;
                GTServicesException.Code code8 = GTServicesException.Code.SERVICES_IN_USE;
                iArr8[12] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GDServiceError(GDServiceErrorCode gDServiceErrorCode, String str) {
        if (gDServiceErrorCode != null) {
            this.errCode = gDServiceErrorCode;
            this.message = str;
            return;
        }
        throw null;
    }

    public int getCustomErrorCode() {
        return this.customErrorCode;
    }

    public Object getDetails() {
        return this.details;
    }

    public GDServiceErrorCode getErrorCode() {
        return this.errCode;
    }

    public String getMessage() {
        return this.message;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GDServiceError(GTServicesException gTServicesException) {
        int ordinal = gTServicesException.getMajor().ordinal();
        if (ordinal != 12) {
            switch (ordinal) {
                case 0:
                    this.errCode = GDServiceErrorCode.GDServicesErrorGeneral;
                    this.message = gTServicesException.getMessage();
                    return;
                case 1:
                    this.errCode = GDServiceErrorCode.GDServicesErrorApplicationNotFound;
                    this.message = gTServicesException.getMessage();
                    return;
                case 2:
                    this.errCode = GDServiceErrorCode.GDServicesErrorNotAllowed;
                    this.message = gTServicesException.getMessage();
                    return;
                case 3:
                    this.errCode = GDServiceErrorCode.GDServicesErrorInvalidParams;
                    this.message = gTServicesException.getMessage();
                    return;
                case 4:
                    this.errCode = GDServiceErrorCode.GDServicesErrorMethodNotFound;
                    this.message = gTServicesException.getMessage();
                    return;
                case 5:
                    this.errCode = GDServiceErrorCode.GDServicesErrorServiceNotFound;
                    this.message = gTServicesException.getMessage();
                    return;
                case 6:
                    this.errCode = GDServiceErrorCode.GDServicesErrorServiceVersionNotFound;
                    this.message = gTServicesException.getMessage();
                    return;
                default:
                    this.errCode = GDServiceErrorCode.GDServicesErrorGeneral;
                    this.message = gTServicesException.getMessage();
                    return;
            }
        }
        this.errCode = GDServiceErrorCode.GDServicesErrorInUse;
        this.message = gTServicesException.getMessage();
    }

    public GDServiceError(GDServiceErrorCode gDServiceErrorCode) {
        if (gDServiceErrorCode != null) {
            this.errCode = gDServiceErrorCode;
            return;
        }
        throw null;
    }

    public GDServiceError(int i, String str, Object obj) throws IllegalArgumentException {
        if (obj != null && !(obj instanceof Boolean) && !(obj instanceof Double) && !(obj instanceof Integer) && !(obj instanceof Map) && !(obj instanceof ArrayList) && !(obj instanceof String) && !(obj instanceof byte[])) {
            throw new IllegalArgumentException("Details Object is of an unsupported type");
        }
        this.message = str;
        this.errCode = GDServiceErrorCode.GDServicesErrorCustom;
        this.customErrorCode = i;
        this.details = obj;
    }
}
