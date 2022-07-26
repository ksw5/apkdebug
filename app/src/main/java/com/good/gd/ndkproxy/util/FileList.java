package com.good.gd.ndkproxy.util;

import com.blackberry.security.detect.ContentChecker;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class FileList {
    private ContentChecker delegate;

    private void NCCOD(long j, long j2) {
        if (this.delegate == null) {
            GDLog.DBGPRINTF(13, "NCCOD DIN");
            return;
        }
        this.delegate.resultOfScanning(j, ContentChecker.Result.valueOf(j2));
    }

    private native void NI();

    private native long NM(String str, String str2, ContentChecker.MsgType msgType);

    private native long NP(String str);

    private native long NU(String str);

    public long checkIP(String str) {
        return NP(str);
    }

    public long checkMessage(String str, String str2, ContentChecker.MsgType msgType) {
        return NM(str, str2, msgType);
    }

    public long checkURL(String str) {
        return NU(str);
    }

    public void init() {
        NI();
    }

    public void setDelegate(ContentChecker contentChecker) {
        this.delegate = contentChecker;
    }
}
