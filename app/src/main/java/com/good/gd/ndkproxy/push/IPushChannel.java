package com.good.gd.ndkproxy.push;

import android.content.IntentFilter;
import com.good.gd.push.PushChannelListener;
import com.good.gd.push.PushChannelState;

/* loaded from: classes.dex */
public interface IPushChannel {
    void connect();

    void disconnect();

    String getDataAuthority();

    String getDataPath();

    String getDataScheme();

    PushChannelState getState();

    void onChannelClose(String str);

    void onChannelError(int i);

    void onChannelMessage(String str);

    void onChannelMessage(byte[] bArr);

    void onChannelOpen(String str, String str2);

    void onChannelPingFail(int i);

    IntentFilter prepareIntentFilter();

    @Deprecated
    void setListener(PushChannelListener pushChannelListener);
}
