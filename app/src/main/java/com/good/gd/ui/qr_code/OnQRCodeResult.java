package com.good.gd.ui.qr_code;

import com.good.gd.messages.ProvisionMsg;

/* loaded from: classes.dex */
public interface OnQRCodeResult {
    void onErrorResult(QRCodeException qRCodeException);

    void onSuccessResult(ProvisionMsg provisionMsg);
}
