package com.good.gd.push;

import android.content.Intent;
import android.content.IntentFilter;
import com.good.gd.ndkproxy.push.IPushChannel;
import com.good.gd.ndkproxy.push.PushChannelImpl;
import com.good.gd.ndkproxy.push.PushFactory;

/* loaded from: classes.dex */
public class PushChannel {
    public static final String GD_PUSH_CHANNEL_EVENT_ACTION = "com.good.gd.PUSH_CHANNEL_EVENT";
    private IPushChannel _impl;

    public PushChannel(String str) {
        this._impl = null;
        this._impl = PushFactory.createPushChannel(str);
    }

    public static int getErrorCode(Intent intent, int i) {
        return PushChannelImpl.getErrorCode(intent, i);
    }

    public static PushChannelEventType getEventType(Intent intent) {
        return PushChannelImpl.getEventType(intent);
    }

    public static String getMessage(Intent intent) {
        return PushChannelImpl.getMessageBundle(intent);
    }

    public static int getPingFailCode(Intent intent, int i) {
        return PushChannelImpl.getPingFail(intent, i);
    }

    public static String getPushChannelHost(Intent intent) {
        return PushChannelImpl.getPushChannelHost(intent);
    }

    public static String getToken(Intent intent) {
        return PushChannelImpl.getToken(intent);
    }

    public void connect() {
        this._impl.connect();
    }

    public void disconnect() {
        this._impl.disconnect();
    }

    public String getDataAuthority() {
        return this._impl.getDataAuthority();
    }

    public String getDataPath() {
        return this._impl.getDataPath();
    }

    public String getDataScheme() {
        return this._impl.getDataScheme();
    }

    public PushChannelState getState() {
        return this._impl.getState();
    }

    public IntentFilter prepareIntentFilter() {
        return this._impl.prepareIntentFilter();
    }

    @Deprecated
    public void setListener(PushChannelListener pushChannelListener) {
        this._impl.setListener(pushChannelListener);
    }

    @Deprecated
    public PushChannel() {
        this._impl = null;
        this._impl = PushFactory.createPushChannel();
    }
}
