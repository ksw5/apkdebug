package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;

/* loaded from: classes.dex */
public class CameraInstance {
    private static final String TAG = CameraInstance.class.getSimpleName();
    private boolean cameraClosed;
    private CameraManager cameraManager;
    private CameraSettings cameraSettings;
    private CameraThread cameraThread;
    private Runnable closer;
    private Runnable configure;
    private DisplayConfiguration displayConfiguration;
    private Handler mainHandler;
    private boolean open;
    private Runnable opener;
    private Runnable previewStarter;
    private Handler readyHandler;
    private CameraSurface surface;

    public CameraInstance(Context context) {
        this.open = false;
        this.cameraClosed = true;
        this.cameraSettings = new CameraSettings();
        this.opener = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Opening camera");
                    CameraInstance.this.cameraManager.open();
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to open camera", e);
                }
            }
        };
        this.configure = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Configuring camera");
                    CameraInstance.this.cameraManager.configure();
                    if (CameraInstance.this.readyHandler != null) {
                        CameraInstance.this.readyHandler.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.getPreviewSize()).sendToTarget();
                    }
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to configure camera", e);
                }
            }
        };
        this.previewStarter = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Starting preview");
                    CameraInstance.this.cameraManager.setPreviewDisplay(CameraInstance.this.surface);
                    CameraInstance.this.cameraManager.startPreview();
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to start preview", e);
                }
            }
        };
        this.closer = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Closing camera");
                    CameraInstance.this.cameraManager.stopPreview();
                    CameraInstance.this.cameraManager.close();
                } catch (Exception e) {
                    Log.e(CameraInstance.TAG, "Failed to close camera", e);
                }
                CameraInstance.this.cameraClosed = true;
                CameraInstance.this.readyHandler.sendEmptyMessage(R.id.zxing_camera_closed);
                CameraInstance.this.cameraThread.decrementInstances();
            }
        };
        Util.validateMainThread();
        this.cameraThread = CameraThread.getInstance();
        CameraManager cameraManager = new CameraManager(context);
        this.cameraManager = cameraManager;
        cameraManager.setCameraSettings(this.cameraSettings);
        this.mainHandler = new Handler();
    }

    public CameraInstance(CameraManager cameraManager) {
        this.open = false;
        this.cameraClosed = true;
        this.cameraSettings = new CameraSettings();
        this.opener = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Opening camera");
                    CameraInstance.this.cameraManager.open();
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to open camera", e);
                }
            }
        };
        this.configure = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Configuring camera");
                    CameraInstance.this.cameraManager.configure();
                    if (CameraInstance.this.readyHandler != null) {
                        CameraInstance.this.readyHandler.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.getPreviewSize()).sendToTarget();
                    }
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to configure camera", e);
                }
            }
        };
        this.previewStarter = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Starting preview");
                    CameraInstance.this.cameraManager.setPreviewDisplay(CameraInstance.this.surface);
                    CameraInstance.this.cameraManager.startPreview();
                } catch (Exception e) {
                    CameraInstance.this.notifyError(e);
                    Log.e(CameraInstance.TAG, "Failed to start preview", e);
                }
            }
        };
        this.closer = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d(CameraInstance.TAG, "Closing camera");
                    CameraInstance.this.cameraManager.stopPreview();
                    CameraInstance.this.cameraManager.close();
                } catch (Exception e) {
                    Log.e(CameraInstance.TAG, "Failed to close camera", e);
                }
                CameraInstance.this.cameraClosed = true;
                CameraInstance.this.readyHandler.sendEmptyMessage(R.id.zxing_camera_closed);
                CameraInstance.this.cameraThread.decrementInstances();
            }
        };
        Util.validateMainThread();
        this.cameraManager = cameraManager;
    }

    public void setDisplayConfiguration(DisplayConfiguration configuration) {
        this.displayConfiguration = configuration;
        this.cameraManager.setDisplayConfiguration(configuration);
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.displayConfiguration;
    }

    public void setReadyHandler(Handler readyHandler) {
        this.readyHandler = readyHandler;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        setSurface(new CameraSurface(surfaceHolder));
    }

    public void setSurface(CameraSurface surface) {
        this.surface = surface;
    }

    public CameraSettings getCameraSettings() {
        return this.cameraSettings;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (!this.open) {
            this.cameraSettings = cameraSettings;
            this.cameraManager.setCameraSettings(cameraSettings);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Size getPreviewSize() {
        return this.cameraManager.getPreviewSize();
    }

    public int getCameraRotation() {
        return this.cameraManager.getCameraRotation();
    }

    public void open() {
        Util.validateMainThread();
        this.open = true;
        this.cameraClosed = false;
        this.cameraThread.incrementAndEnqueue(this.opener);
    }

    public void configureCamera() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.configure);
    }

    public void startPreview() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.previewStarter);
    }

    public void setTorch(final boolean on) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    CameraInstance.this.m449xdd5ef35b(on);
                }
            });
        }
    }

    /* renamed from: lambda$setTorch$0$com-journeyapps-barcodescanner-camera-CameraInstance  reason: not valid java name */
    public /* synthetic */ void m449xdd5ef35b(boolean on) {
        this.cameraManager.setTorch(on);
    }

    public void changeCameraParameters(final CameraParametersCallback callback) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CameraInstance.this.m446x78173d15(callback);
                }
            });
        }
    }

    /* renamed from: lambda$changeCameraParameters$1$com-journeyapps-barcodescanner-camera-CameraInstance  reason: not valid java name */
    public /* synthetic */ void m446x78173d15(CameraParametersCallback callback) {
        this.cameraManager.changeCameraParameters(callback);
    }

    public void close() {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(this.closer);
        } else {
            this.cameraClosed = true;
        }
        this.open = false;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isCameraClosed() {
        return this.cameraClosed;
    }

    public void requestPreview(final PreviewCallback callback) {
        this.mainHandler.post(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CameraInstance.this.m448xf1e80099(callback);
            }
        });
    }

    /* renamed from: lambda$requestPreview$3$com-journeyapps-barcodescanner-camera-CameraInstance  reason: not valid java name */
    public /* synthetic */ void m448xf1e80099(final PreviewCallback callback) {
        if (!this.open) {
            Log.d(TAG, "Camera is closed, not requesting preview");
        } else {
            this.cameraThread.enqueue(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CameraInstance.this.m447x782e26ac(callback);
                }
            });
        }
    }

    /* renamed from: lambda$null$2$com-journeyapps-barcodescanner-camera-CameraInstance  reason: not valid java name */
    public /* synthetic */ void m447x782e26ac(PreviewCallback callback) {
        this.cameraManager.requestPreviewFrame(callback);
    }

    private void validateOpen() {
        if (!this.open) {
            throw new IllegalStateException("CameraInstance is not open");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(Exception error) {
        Handler handler = this.readyHandler;
        if (handler != null) {
            handler.obtainMessage(R.id.zxing_camera_error, error).sendToTarget();
        }
    }

    protected CameraManager getCameraManager() {
        return this.cameraManager;
    }

    protected CameraThread getCameraThread() {
        return this.cameraThread;
    }

    protected CameraSurface getSurface() {
        return this.surface;
    }
}
