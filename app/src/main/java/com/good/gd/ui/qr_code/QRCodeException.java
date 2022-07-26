package com.good.gd.ui.qr_code;

/* loaded from: classes.dex */
public class QRCodeException extends Exception {
    private TYPE type = TYPE.NONE;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum TYPE {
        NONE,
        RESULT_NULL,
        RESULT_CONTENT_NULL,
        WRONG_PARSED_DATA,
        WRONG_JSON_DATA,
        WRONG_QR_CODE_TYPE
    }

    public QRCodeException(TYPE type) {
        init(type);
    }

    private void init(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return this.type;
    }
}
