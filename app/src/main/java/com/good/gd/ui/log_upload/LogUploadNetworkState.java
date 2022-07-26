package com.good.gd.ui.log_upload;

/* loaded from: classes.dex */
public class LogUploadNetworkState {
    public boolean available;
    public boolean connected;
    public int type;
    public String typeName;

    public static LogUploadNetworkState createLogUploadNetworkState(boolean z, boolean z2, int i, String str) {
        LogUploadNetworkState logUploadNetworkState = new LogUploadNetworkState();
        logUploadNetworkState.connected = z;
        logUploadNetworkState.available = z2;
        logUploadNetworkState.type = i;
        logUploadNetworkState.typeName = str;
        return logUploadNetworkState;
    }
}
