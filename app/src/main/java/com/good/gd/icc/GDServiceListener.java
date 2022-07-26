package com.good.gd.icc;

/* loaded from: classes.dex */
public interface GDServiceListener {
    void onMessageSent(String str, String str2, String[] strArr);

    void onReceiveMessage(String str, String str2, String str3, String str4, Object obj, String[] strArr, String str5);

    void onReceivingAttachmentFile(String str, String str2, long j, String str3);

    void onReceivingAttachments(String str, int i, String str2);
}
