package com.good.gd.ndkproxy.push;

import com.good.gd.push.PushConnectionListener;

/* loaded from: classes.dex */
public interface IPushConnection {
    void checkStatus();

    void connect();

    void connectInternal(boolean z);

    void disconnect();

    void disconnectInternal();

    boolean isConnected();

    boolean isWaiting();

    void onNativeStatus(int i);

    void setListener(PushConnectionListener pushConnectionListener);

    void testConnectionIntegrity();
}
