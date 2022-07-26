package com.good.gd.push;

import android.util.Log;
import com.good.gd.ndkproxy.push.IPushConnection;
import com.good.gd.ndkproxy.push.PushFactory;
import com.good.gd.utils.GDLocalizer;

@Deprecated
/* loaded from: classes.dex */
public class PushConnection {
    private static final String TAG = "GD";
    private static PushConnection _instance;
    private IPushConnection _impl;

    private PushConnection() {
        this._impl = null;
        this._impl = PushFactory.getPushConnection();
    }

    public static synchronized PushConnection getInstance() {
        PushConnection pushConnection;
        synchronized (PushConnection.class) {
            if (_instance == null) {
                _instance = new PushConnection();
            }
            pushConnection = _instance;
        }
        return pushConnection;
    }

    @Deprecated
    public void connect() {
        Log.w(TAG, GDLocalizer.DEPRECATED_API_WARN);
        this._impl.connect();
    }

    @Deprecated
    public void disconnect() {
        Log.w(TAG, GDLocalizer.DEPRECATED_API_WARN);
        this._impl.disconnect();
    }

    public boolean isConnected() {
        return this._impl.isConnected();
    }

    public void setListener(PushConnectionListener pushConnectionListener) {
        this._impl.setListener(pushConnectionListener);
    }
}
