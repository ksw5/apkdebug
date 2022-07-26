package com.good.gd.ui.log_upload;

/* loaded from: classes.dex */
public interface LogUploadStateChangesReceiver {
    void addListener(LogUploadStateChangeListener logUploadStateChangeListener);

    void removeListener(LogUploadStateChangeListener logUploadStateChangeListener);
}
