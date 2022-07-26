package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.log_upload.GDLogUploadView;
import com.good.gd.ui.log_upload.LogUploadNetworkState;
import com.good.gd.ui.log_upload.LogUploadStateChangesReceiver;

/* loaded from: classes.dex */
public class LogUploadUI extends BaseUI {
    private final LogUploadNetworkState inialNetworkState;
    private final LogUploadStateChangesReceiver logUploadStateChangesReceiver;

    public LogUploadUI(long j, LogUploadStateChangesReceiver logUploadStateChangesReceiver, LogUploadNetworkState logUploadNetworkState) {
        super(BBUIType.UI_LOG_UPLOAD, j);
        this.logUploadStateChangesReceiver = logUploadStateChangesReceiver;
        this.inialNetworkState = logUploadNetworkState;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDLogUploadView(context, viewInteractor, this, viewCustomizer, this.logUploadStateChangesReceiver, this.inialNetworkState);
    }
}
