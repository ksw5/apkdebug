package com.good.gd.ui.log_upload;

import com.good.gd.log.GDLogUploadState;

/* loaded from: classes.dex */
public interface LogUploadStateChangeListener {
    void updateLogUploadState(GDLogUploadState gDLogUploadState);

    void updateNetworkStatus(LogUploadNetworkState logUploadNetworkState);
}
