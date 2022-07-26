package com.good.gd.icc;

/* loaded from: classes.dex */
public final class GDServiceException extends Exception {
    private static final long serialVersionUID = 1622079998027464148L;
    private final GDServiceErrorCode errCode;

    /* JADX INFO: Access modifiers changed from: package-private */
    public GDServiceException(GDServiceErrorCode gDServiceErrorCode, String str) {
        super(str);
        this.errCode = gDServiceErrorCode;
    }

    public GDServiceErrorCode errorCode() {
        return this.errCode;
    }
}
