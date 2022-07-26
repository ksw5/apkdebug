package com.good.gd.ui.qr_code;

import android.content.Intent;
import android.util.Log;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui.qr_code.QRCodeException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class QrCodeScanningHelper {
    private static final int QR_CODE_ACTIVATION_VERSION = 3;
    private static final String QR_CODE_ACTIVATION_VERSION_KEY = "version";
    private static final String QR_CODE_PASSWORD_KEY = "password";
    private static final String QR_CODE_TYPE_KEY = "type";
    private static final String QR_CODE_UNLOCK_TYPE = "unlock";
    private static final int QR_CODE_UNLOCK_VERSION = 3;
    private static final String QR_CODE_UNLOCK_VERSION_KEY = "ver";
    private static final String QR_CODE_URL_KEY = "url";
    private static final String QR_CODE_USERNAME_KEY = "username";

    private static ProvisionMsg parseQRCodeContent(IntentResult intentResult) throws QRCodeException {
        if (BarcodeFormat.valueOf(intentResult.getFormatName()) == BarcodeFormat.QR_CODE) {
            try {
                JSONObject jSONObject = new JSONObject(intentResult.getContents());
                String optString = jSONObject.optString(QR_CODE_TYPE_KEY);
                if (!optString.isEmpty() && !optString.equals(QR_CODE_UNLOCK_TYPE)) {
                    GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: wrong type = [" + optString + "], unlock QR code expecting");
                    throw new QRCodeException(QRCodeException.TYPE.WRONG_QR_CODE_TYPE);
                }
                try {
                    if (optString.equals(QR_CODE_UNLOCK_TYPE)) {
                        int i = jSONObject.getInt(QR_CODE_UNLOCK_VERSION_KEY);
                        if (i > 0 && i < 3) {
                            GDLog.DBGPRINTF(14, "QrCodeScanningHelper.parseQRCodeContent: unlock QR code");
                        } else {
                            GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: wrong unlock version: [" + i + "]");
                            throw new QRCodeException(QRCodeException.TYPE.WRONG_PARSED_DATA);
                        }
                    } else {
                        int i2 = jSONObject.getInt("version");
                        if (i2 < 3) {
                            GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: wrong activation version - [" + i2 + "], version lower then 3 is not allowed");
                            throw new QRCodeException(QRCodeException.TYPE.WRONG_PARSED_DATA);
                        }
                    }
                    try {
                        String string = jSONObject.getString("url");
                        String string2 = jSONObject.getString(QR_CODE_USERNAME_KEY);
                        String optString2 = jSONObject.optString(QR_CODE_PASSWORD_KEY);
                        if (!string.isEmpty() && !string2.isEmpty()) {
                            Object[] objArr = new Object[3];
                            objArr[0] = string2;
                            objArr[1] = (optString2 == null || optString2.isEmpty()) ? "(missing)" : optString2.replaceAll(".", "*");
                            objArr[2] = string;
                            GDLog.DBGPRINTF(16, String.format("QrCode.res = {u:%s,p:%s,s:%s}", objArr));
                            return new ProvisionMsg(string2, optString2, string);
                        }
                        GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: wrong data, username or bcp url is empty ");
                        throw new QRCodeException(QRCodeException.TYPE.WRONG_PARSED_DATA);
                    } catch (JSONException e) {
                        GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: exception: " + e.getMessage());
                        e.printStackTrace();
                        throw new QRCodeException(QRCodeException.TYPE.WRONG_JSON_DATA);
                    }
                } catch (JSONException e2) {
                    GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: exception when retrieving version: " + Log.getStackTraceString(e2));
                    throw new QRCodeException(QRCodeException.TYPE.WRONG_JSON_DATA);
                }
            } catch (JSONException e3) {
                GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: exception: " + Log.getStackTraceString(e3));
                throw new QRCodeException(QRCodeException.TYPE.WRONG_JSON_DATA);
            }
        }
        GDLog.DBGPRINTF(12, "QrCodeScanningHelper.parseQRCodeContent: wrong code type [" + intentResult.getFormatName() + "] ");
        throw new QRCodeException(QRCodeException.TYPE.WRONG_QR_CODE_TYPE);
    }

    public static ProvisionMsg parseQrCodeScanResult(int i, int i2, Intent intent) throws QRCodeException {
        IntentResult parseActivityResult = IntentIntegrator.parseActivityResult(i, i2, intent);
        if (parseActivityResult != null) {
            if (parseActivityResult.getContents() != null) {
                return parseQRCodeContent(parseActivityResult);
            }
            throw new QRCodeException(QRCodeException.TYPE.RESULT_CONTENT_NULL);
        }
        throw new QRCodeException(QRCodeException.TYPE.RESULT_NULL);
    }
}
