package com.good.gd.icc;

/* loaded from: classes.dex */
public interface GDServiceClientListener {
    void onMessageSent(String str, String str2, String[] strArr);

    void onReceiveMessage(String str, Object obj, String[] strArr, String str2);

    void onReceivingAttachmentFile(String str, String str2, long j, String str3);

    void onReceivingAttachments(String str, int i, String str2);
}
