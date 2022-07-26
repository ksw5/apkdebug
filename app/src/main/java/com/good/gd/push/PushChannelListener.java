package com.good.gd.push;

@Deprecated
/* loaded from: classes.dex */
public interface PushChannelListener {
    void onChannelClose(String str);

    void onChannelError(int i);

    void onChannelMessage(String str);

    void onChannelOpen(String str);

    void onChannelPingFail(int i);
}
