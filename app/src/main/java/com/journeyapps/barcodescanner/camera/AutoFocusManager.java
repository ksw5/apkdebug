package com.journeyapps.barcodescanner.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.journeyapps.barcodescanner.camera.AutoFocusManager;
import java.util.ArrayList;
import java.util.Collection;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public final class AutoFocusManager {
    private static final long AUTO_FOCUS_INTERVAL_MS = 2000;
    private static final Collection<String> FOCUS_MODES_CALLING_AF;
    private static final String TAG = AutoFocusManager.class.getSimpleName();
    private int MESSAGE_FOCUS = 1;
    private final Camera.AutoFocusCallback autoFocusCallback = new AnonymousClass2();
    private final Camera camera;
    private final Handler.Callback focusHandlerCallback;
    private boolean focusing;
    private Handler handler;
    private boolean stopped;
    private final boolean useAutoFocus;

    static {
        ArrayList arrayList = new ArrayList(2);
        FOCUS_MODES_CALLING_AF = arrayList;
        arrayList.add(DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
        arrayList.add("macro");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.journeyapps.barcodescanner.camera.AutoFocusManager$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements Camera.AutoFocusCallback {
        AnonymousClass2() {
        }

        @Override // android.hardware.Camera.AutoFocusCallback
        public void onAutoFocus(boolean success, Camera theCamera) {
            AutoFocusManager.this.handler.post(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.AutoFocusManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AnonymousClass2.this.m445x7b23e9dd();
                }
            });
        }

        /* renamed from: lambda$onAutoFocus$0$com-journeyapps-barcodescanner-camera-AutoFocusManager$2  reason: not valid java name */
        public /* synthetic */ void m445x7b23e9dd() {
            AutoFocusManager.this.focusing = false;
            AutoFocusManager.this.autoFocusAgainLater();
        }
    }

    public AutoFocusManager(Camera camera, CameraSettings settings) {
        boolean z = true;
        Handler.Callback callback = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.camera.AutoFocusManager.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message msg) {
                if (msg.what == AutoFocusManager.this.MESSAGE_FOCUS) {
                    AutoFocusManager.this.focus();
                    return true;
                }
                return false;
            }
        };
        this.focusHandlerCallback = callback;
        this.handler = new Handler(callback);
        this.camera = camera;
        String currentFocusMode = camera.getParameters().getFocusMode();
        z = (!settings.isAutoFocusEnabled() || !FOCUS_MODES_CALLING_AF.contains(currentFocusMode)) ? false : z;
        this.useAutoFocus = z;
        Log.i(TAG, "Current focus mode '" + currentFocusMode + "'; use auto focus? " + z);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void autoFocusAgainLater() {
        if (!this.stopped && !this.handler.hasMessages(this.MESSAGE_FOCUS)) {
            Handler handler = this.handler;
            handler.sendMessageDelayed(handler.obtainMessage(this.MESSAGE_FOCUS), 2000L);
        }
    }

    public void start() {
        this.stopped = false;
        focus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void focus() {
        if (this.useAutoFocus && !this.stopped && !this.focusing) {
            try {
                this.camera.autoFocus(this.autoFocusCallback);
                this.focusing = true;
            } catch (RuntimeException re) {
                Log.w(TAG, "Unexpected exception while focusing", re);
                autoFocusAgainLater();
            }
        }
    }

    private void cancelOutstandingTask() {
        this.handler.removeMessages(this.MESSAGE_FOCUS);
    }

    public void stop() {
        this.stopped = true;
        this.focusing = false;
        cancelOutstandingTask();
        if (this.useAutoFocus) {
            try {
                this.camera.cancelAutoFocus();
            } catch (RuntimeException re) {
                Log.w(TAG, "Unexpected exception while cancelling focusing", re);
            }
        }
    }
}
