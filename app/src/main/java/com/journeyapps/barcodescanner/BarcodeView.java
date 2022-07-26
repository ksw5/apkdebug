package com.journeyapps.barcodescanner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BarcodeView extends CameraPreview {
    private DecoderFactory decoderFactory;
    private DecoderThread decoderThread;
    private Handler resultHandler;
    private DecodeMode decodeMode = DecodeMode.NONE;
    private BarcodeCallback callback = null;
    private final Handler.Callback resultCallback = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.BarcodeView.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_decode_succeeded) {
                BarcodeResult result = (BarcodeResult) message.obj;
                if (result != null && BarcodeView.this.callback != null && BarcodeView.this.decodeMode != DecodeMode.NONE) {
                    BarcodeView.this.callback.barcodeResult(result);
                    if (BarcodeView.this.decodeMode == DecodeMode.SINGLE) {
                        BarcodeView.this.stopDecoding();
                    }
                }
                return true;
            } else if (message.what == R.id.zxing_decode_failed) {
                return true;
            } else {
                if (message.what == R.id.zxing_possible_result_points) {
                    List<ResultPoint> resultPoints = (List) message.obj;
                    if (BarcodeView.this.callback != null && BarcodeView.this.decodeMode != DecodeMode.NONE) {
                        BarcodeView.this.callback.possibleResultPoints(resultPoints);
                    }
                    return true;
                }
                return false;
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum DecodeMode {
        NONE,
        SINGLE,
        CONTINUOUS
    }

    public BarcodeView(Context context) {
        super(context);
        initialize();
    }

    public BarcodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BarcodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        this.decoderFactory = new DefaultDecoderFactory();
        this.resultHandler = new Handler(this.resultCallback);
    }

    public void setDecoderFactory(DecoderFactory decoderFactory) {
        Util.validateMainThread();
        this.decoderFactory = decoderFactory;
        DecoderThread decoderThread = this.decoderThread;
        if (decoderThread != null) {
            decoderThread.setDecoder(createDecoder());
        }
    }

    private Decoder createDecoder() {
        if (this.decoderFactory == null) {
            this.decoderFactory = createDefaultDecoderFactory();
        }
        DecoderResultPointCallback callback = new DecoderResultPointCallback();
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, callback);
        Decoder decoder = this.decoderFactory.createDecoder(hints);
        callback.setDecoder(decoder);
        return decoder;
    }

    public DecoderFactory getDecoderFactory() {
        return this.decoderFactory;
    }

    public void decodeSingle(BarcodeCallback callback) {
        this.decodeMode = DecodeMode.SINGLE;
        this.callback = callback;
        startDecoderThread();
    }

    public void decodeContinuous(BarcodeCallback callback) {
        this.decodeMode = DecodeMode.CONTINUOUS;
        this.callback = callback;
        startDecoderThread();
    }

    public void stopDecoding() {
        this.decodeMode = DecodeMode.NONE;
        this.callback = null;
        stopDecoderThread();
    }

    protected DecoderFactory createDefaultDecoderFactory() {
        return new DefaultDecoderFactory();
    }

    private void startDecoderThread() {
        stopDecoderThread();
        if (this.decodeMode != DecodeMode.NONE && isPreviewActive()) {
            DecoderThread decoderThread = new DecoderThread(getCameraInstance(), createDecoder(), this.resultHandler);
            this.decoderThread = decoderThread;
            decoderThread.setCropRect(getPreviewFramingRect());
            this.decoderThread.start();
        }
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    protected void previewStarted() {
        super.previewStarted();
        startDecoderThread();
    }

    private void stopDecoderThread() {
        DecoderThread decoderThread = this.decoderThread;
        if (decoderThread != null) {
            decoderThread.stop();
            this.decoderThread = null;
        }
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    public void pause() {
        stopDecoderThread();
        super.pause();
    }
}
