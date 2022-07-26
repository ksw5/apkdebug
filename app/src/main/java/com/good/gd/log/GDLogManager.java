package com.good.gd.log;

import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.log.GDLogManagerImpl;
import com.good.gd.utils.GDSDKState;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class GDLogManager {
    public static final String GD_LOG_UPLOAD_CHANGE_ACTION = "com.good.gd.LOG_UPLOAD_CHANGE";
    private GDLogManagerImpl _impl;
    public static final long DETAILED_MAXIMUM_DURATION = TimeUnit.MINUTES.toMillis(15);
    private static GDLogManager _instance = null;

    private GDLogManager() {
        this._impl = null;
        this._impl = new GDLogManagerImpl();
    }

    public static GDLogManager getInstance() {
        if (_instance == null) {
            _instance = new GDLogManager();
        }
        return _instance;
    }

    public boolean cancelUpload() {
        return !GDSDKState.getInstance().isWiped() && this._impl.cancelUpload();
    }

    public boolean detailedLoggingFor(long j) {
        return this._impl.detailedLoggingFor(j);
    }

    public long getUploadBytesSent() {
        return this._impl.getUploadBytesSent();
    }

    public long getUploadBytesTotal() {
        return this._impl.getUploadBytesTotal();
    }

    public GDLogUploadState getUploadState() {
        return this._impl.getUploadState();
    }

    public boolean openLogUploadUI() throws GDNotAuthorizedError {
        return this._impl.openLogUploadUI();
    }

    public boolean resumeUpload() {
        return !GDSDKState.getInstance().isWiped() && this._impl.resumeUpload();
    }

    public boolean startUpload() {
        return !GDSDKState.getInstance().isWiped() && this._impl.startUpload();
    }

    public boolean suspendUpload() {
        return !GDSDKState.getInstance().isWiped() && this._impl.suspendUpload();
    }
}
