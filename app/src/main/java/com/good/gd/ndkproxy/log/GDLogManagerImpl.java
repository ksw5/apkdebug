package com.good.gd.ndkproxy.log;

import android.content.Intent;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.log.GDLogManager;
import com.good.gd.log.GDLogUploadState;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDSDKState;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class GDLogManagerImpl {
    private static LogUploadUIPresenter logUploadUIPresenter;
    private GDLogUploadState _currentState = GDLogUploadState.Idle;
    private long _bytesUploaded = 0;
    private long _bytesTotal = 0;

    public GDLogManagerImpl() {
        GDLog.DBGPRINTF(16, "GDLogManagerImpl.ndkInit\n");
        ndkInit();
    }

    private native boolean native_cancelUpload();

    private static native boolean native_detailedLoggingFor(long j);

    private native boolean native_resumeUpload();

    private native boolean native_startUpload();

    private native boolean native_suspendUpload();

    private native void ndkInit();

    private void onStatusChanged(String str, int i) {
        GDLogUploadState gDLogUploadState;
        if (i == 1) {
            gDLogUploadState = GDLogUploadState.Idle;
        } else if (i == 2) {
            gDLogUploadState = GDLogUploadState.InProgress;
        } else if (i == 4) {
            gDLogUploadState = GDLogUploadState.Completed;
        } else if (i == 8) {
            gDLogUploadState = GDLogUploadState.Cancelled;
        } else if (i == 16) {
            gDLogUploadState = GDLogUploadState.Suspended;
        } else if (i == 32) {
            gDLogUploadState = GDLogUploadState.Resumed;
        } else {
            throw new IllegalArgumentException("States do not match");
        }
        this._currentState = gDLogUploadState;
        GTBaseContext.getInstance().getApplicationContext();
        GDLocalBroadcastManager.getInstance().sendBroadcast(new Intent(GDLogManager.GD_LOG_UPLOAD_CHANGE_ACTION));
    }

    private void onUploadProgressChanged(String str, long j, long j2) {
        this._currentState = GDLogUploadState.InProgress;
        this._bytesUploaded = j;
        this._bytesTotal = j2;
        GTBaseContext.getInstance().getApplicationContext();
        GDLocalBroadcastManager.getInstance().sendBroadcast(new Intent(GDLogManager.GD_LOG_UPLOAD_CHANGE_ACTION));
    }

    public static void setLogUploadUIPresenter(LogUploadUIPresenter logUploadUIPresenter2) {
        logUploadUIPresenter = logUploadUIPresenter2;
    }

    public boolean cancelUpload() {
        GDLog.DBGPRINTF(16, "GDLogManagerImpl.cancelUpload\n");
        return native_cancelUpload();
    }

    public boolean detailedLoggingFor(long j) {
        if (j < 0) {
            j = 0;
        }
        return native_detailedLoggingFor(j);
    }

    public long getUploadBytesSent() {
        return this._bytesUploaded;
    }

    public long getUploadBytesTotal() {
        return this._bytesTotal;
    }

    public GDLogUploadState getUploadState() {
        return this._currentState;
    }

    public boolean openLogUploadUI() throws GDNotAuthorizedError {
        return !GDSDKState.getInstance().isWiped() && logUploadUIPresenter.openLogUploadUI();
    }

    public boolean resumeUpload() {
        GDLog.DBGPRINTF(16, "GDLogManagerImpl.resumeUpload\n");
        return native_resumeUpload();
    }

    public boolean startUpload() {
        GDLog.DBGPRINTF(16, "GDLogManagerImpl.startUpload\n");
        this._currentState = GDLogUploadState.Idle;
        return native_startUpload();
    }

    public boolean suspendUpload() {
        GDLog.DBGPRINTF(16, "GDLogManagerImpl.suspendUpload\n");
        return native_suspendUpload();
    }
}
