package com.good.gd.ui.qr_code;

import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import java.util.List;

/* loaded from: classes.dex */
public class QRCodeDefaultResultHandler implements BarcodeCallback {
    private OnQRCodeResult qrCodeResultListener;

    public QRCodeDefaultResultHandler(OnQRCodeResult onQRCodeResult) {
        this.qrCodeResultListener = onQRCodeResult;
    }

    @Override // com.journeyapps.barcodescanner.BarcodeCallback
    public void barcodeResult(BarcodeResult barcodeResult) {
        try {
            ProvisionMsg parseQrCodeScanResult = QrCodeScanningHelper.parseQrCodeScanResult(IntentIntegrator.REQUEST_CODE, -1, CaptureManager.resultIntent(barcodeResult, null));
            OnQRCodeResult onQRCodeResult = this.qrCodeResultListener;
            if (onQRCodeResult == null) {
                return;
            }
            onQRCodeResult.onSuccessResult(parseQrCodeScanResult);
        } catch (QRCodeException e) {
            OnQRCodeResult onQRCodeResult2 = this.qrCodeResultListener;
            if (onQRCodeResult2 != null) {
                onQRCodeResult2.onErrorResult(e);
            }
            GDLog.DBGPRINTF(12, "QRCodeDefaultResultHandler: QRCodeException: " + e.toString());
        }
    }

    @Override // com.journeyapps.barcodescanner.BarcodeCallback
    public void possibleResultPoints(List<ResultPoint> list) {
    }
}
