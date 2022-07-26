package com.good.gd.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.QRCodeScanUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.qr_code.OnQRCodeResult;
import com.good.gd.ui.qr_code.QRCodeDefaultResultHandler;
import com.good.gd.ui.qr_code.QRCodeException;
import com.good.gd.widget.GDTextView;
import com.good.gt.context.GTBaseContext;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.Arrays;

/* loaded from: classes.dex */
public class GDQRCodeScanView extends GDView implements OnQRCodeResult {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 251;
    private Activity activity;
    private DecoratedBarcodeView barcodeView;
    private CameraStateHelper cameraStateListener;
    private Toast invalidQrCodeScannedMessage;
    private boolean isPermissionRequiredDialogRequested = false;
    private QRCodeScanUI uiData;

    /* loaded from: classes.dex */
    private static class CameraStateHelper implements CameraPreview.StateListener {
        private boolean dbjc;
        private final DecoratedBarcodeView qkduk;

        public CameraStateHelper(DecoratedBarcodeView decoratedBarcodeView) {
            this.qkduk = decoratedBarcodeView;
            GDLog.DBGPRINTF(16, "GDQRCodeScanView: <init>: " + hashCode());
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraClosed() {
            GDLog.DBGPRINTF(14, "GDQRCodeScanView: CameraStateHelper: cameraClosed" + hashCode() + ")");
            this.dbjc = true;
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception exc) {
            GDLog.DBGPRINTF(12, "GDQRCodeScanView: CameraStateHelper: cameraError(" + hashCode() + ") " + exc.toString());
            GDLog.DBGPRINTF(12, "GDQRCodeScanView: CameraStateHelper: cameraError(" + hashCode() + ") " + exc.getCause());
            this.qkduk.pause();
            this.qkduk.resume();
            GDLog.DBGPRINTF(12, "GDQRCodeScanView: CameraStateHelper: cameraError(" + hashCode() + ")");
        }

        public boolean dbjc() {
            return this.dbjc;
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
            GDLog.DBGPRINTF(16, "GDQRCodeScanView: CameraStateHelper: previewSized " + hashCode());
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
            GDLog.DBGPRINTF(16, "GDQRCodeScanView: CameraStateHelper: previewStarted " + hashCode());
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
            GDLog.DBGPRINTF(16, "GDQRCodeScanView: CameraStateHelper: previewStopped " + hashCode());
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            GDQRCodeScanView.this.cancelAction();
        }
    }

    /* loaded from: classes.dex */
    private class yfdke extends GDViewDelegateAdapter {
        private yfdke() {
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityDestroy() {
            super.onActivityDestroy();
            if (!GDQRCodeScanView.this.cameraStateListener.dbjc()) {
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityDestroy: close camera");
                GDQRCodeScanView.this.closeCamera();
            }
            GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityDestroy");
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityPause() {
            super.onActivityPause();
            if (GDQRCodeScanView.this.barcodeView.getBarcodeView().isPreviewActive()) {
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityPause pause() ");
                GDQRCodeScanView.this.barcodeView.pause();
            }
            GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityPause");
        }

        @Override // com.good.gd.ui.base_ui.GDView.GDViewDelegateAdapter
        public void onActivityResume() {
            super.onActivityResume();
            if (GDQRCodeScanView.this.isCameraPermissionGranted() && !GDQRCodeScanView.this.barcodeView.getBarcodeView().isPreviewActive()) {
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityResume resume() ");
                GDQRCodeScanView.this.barcodeView.resume();
                if (GDQRCodeScanView.this.isPermissionRequiredDialogRequested) {
                    GDQRCodeScanView.this.isPermissionRequiredDialogRequested = false;
                    GDQRCodeScanView.this.startQRCodeScanning();
                    GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityResume - isPermissionRequiredDialogRequested = false ");
                }
            }
            if (!GDQRCodeScanView.this.isCameraPermissionGranted() && GDQRCodeScanView.this.isPermissionRequiredDialogRequested) {
                CoreUI.requestQRCodePermissionRequiredUI();
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityResume - isPermissionRequiredDialogRequested = true ");
            }
            GDLog.DBGPRINTF(14, "GDQRCodeScanView: onActivityResume");
        }

        /* synthetic */ yfdke(GDQRCodeScanView gDQRCodeScanView, hbfhc hbfhcVar) {
            this();
        }
    }

    public GDQRCodeScanView(Context context, ViewInteractor viewInteractor, QRCodeScanUI qRCodeScanUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        if (context instanceof Activity) {
            this._delegate = new yfdke(this, null);
            this.uiData = qRCodeScanUI;
            this.activity = (Activity) context;
            inflateLayout(R.layout.bbd_qr_code_scan_view, this);
            DecoratedBarcodeView decoratedBarcodeView = (DecoratedBarcodeView) findViewById(R.id.gd_barcode_scanner);
            this.barcodeView = decoratedBarcodeView;
            checkFieldNotNull(decoratedBarcodeView, "bbd_qr_code_scan_view", "gd_barcode_scanner");
            this.barcodeView.getStatusView().setText("");
            this.cameraStateListener = new CameraStateHelper(this.barcodeView);
            this.barcodeView.getBarcodeView().addStateListener(this.cameraStateListener);
            this.barcodeView.setDecoderFactory(new DefaultDecoderFactory(Arrays.asList(BarcodeFormat.QR_CODE)));
            CameraSettings cameraSettings = new CameraSettings();
            cameraSettings.setBarcodeSceneModeEnabled(true);
            cameraSettings.setContinuousFocusEnabled(true);
            cameraSettings.setMeteringEnabled(true);
            this.barcodeView.setCameraSettings(cameraSettings);
            GDTextView gDTextView = (GDTextView) findViewById(R.id.gd_qr_code_scan_label);
            checkFieldNotNull(gDTextView, "bbd_qr_code_scan_view", "gd_qr_code_scan_label");
            gDTextView.setText(this.uiData.getLocalizedQRCodeMessageText());
            GDTextView gDTextView2 = (GDTextView) findViewById(R.id.bbd_activation_header_text);
            checkFieldNotNull(gDTextView2, "bbd_qr_code_scan_view", "bbd_activation_header_text");
            gDTextView2.setText(this.uiData.getLocalizedQRCodeTitleText());
            ImageView imageView = (ImageView) findViewById(R.id.bbd_activation_header_back_button);
            checkFieldNotNull(imageView, "bbd_qr_code_scan_view", "bbd_activation_header_back_button");
            imageView.setOnClickListener(new hbfhc());
            enableBottomButton(this.uiData);
            setBottomLineBackground(true);
            applyUICustomization();
            requestCameraPermission();
            return;
        }
        throw new IllegalArgumentException("GDQRCodeScanView: provided context have to be an instance of the Activity class");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAction() {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        this.barcodeView.getBarcodeView().stopDecoding();
        Toast toast = this.invalidQrCodeScannedMessage;
        if (toast != null) {
            toast.cancel();
        }
        closeCamera();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCamera() {
        if (!this.barcodeView.getBarcodeView().isCameraClosed()) {
            GDLog.DBGPRINTF(14, "GDQRCodeScanView: closeCamera()");
            this.barcodeView.getBarcodeView().getCameraInstance().close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCameraPermissionGranted() {
        boolean z = this.activity.checkSelfPermission("android.permission.CAMERA") == 0;
        GDLog.DBGPRINTF(14, "GDQRCodeScanView: isCameraPermissionGranted " + z);
        return z;
    }

    public static void openSettings() {
        Context applicationContext = GTBaseContext.getInstance().getApplicationContext();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", applicationContext.getPackageName(), null));
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        applicationContext.startActivity(intent);
    }

    private void requestCameraPermission() {
        if (!isCameraPermissionGranted()) {
            this.activity.requestPermissions(new String[]{"android.permission.CAMERA"}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startQRCodeScanning() {
        GDLog.DBGPRINTF(14, "GDQRCodeScanView: startQRCodeScanning ");
        this.barcodeView.decodeContinuous(new QRCodeDefaultResultHandler(this));
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        cancelAction();
    }

    @Override // com.good.gd.ui.qr_code.OnQRCodeResult
    public void onErrorResult(QRCodeException qRCodeException) {
        Toast toast = this.invalidQrCodeScannedMessage;
        if (toast != null) {
            toast.cancel();
        }
        Toast makeText = Toast.makeText(this.activity, this.uiData.getLocalizedQRCodeErrorMessageText(), 0);
        this.invalidQrCodeScannedMessage = makeText;
        if (makeText != null) {
            makeText.show();
        }
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onPermissions(int i, String[] strArr, int[] iArr) {
        boolean z = strArr.length > 0 && strArr[0].equals("android.permission.CAMERA") && i == CAMERA_PERMISSION_REQUEST_CODE;
        if (z && iArr[0] == -1) {
            if (!this.activity.shouldShowRequestPermissionRationale("android.permission.CAMERA")) {
                CoreUI.requestQRCodePermissionRequiredUI();
                this.isPermissionRequiredDialogRequested = true;
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onPermissions - isPermissionRequiredDialogRequested = true ");
                return;
            }
            cancelAction();
        } else if (!z || iArr[0] != 0) {
        } else {
            if (!this.barcodeView.getBarcodeView().isPreviewActive()) {
                GDLog.DBGPRINTF(14, "GDQRCodeScanView: onPermissions resume() ");
                this.barcodeView.resume();
            }
            startQRCodeScanning();
        }
    }

    @Override // com.good.gd.ui.qr_code.OnQRCodeResult
    public void onSuccessResult(ProvisionMsg provisionMsg) {
        BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_OK, provisionMsg);
        this.barcodeView.getBarcodeView().stopDecoding();
        Toast toast = this.invalidQrCodeScannedMessage;
        if (toast != null) {
            toast.cancel();
        }
        closeCamera();
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void stateWasUpdated() {
        BBDUIUpdateEvent updateData = this.uiData.getUpdateData();
        if (updateData == null || updateData.getType() != UIEventType.UI_QR_CODE_PROVISION_UPDATE) {
            return;
        }
        this.uiData.resetUpdateData();
        if (!isCameraPermissionGranted()) {
            return;
        }
        startQRCodeScanning();
    }
}
