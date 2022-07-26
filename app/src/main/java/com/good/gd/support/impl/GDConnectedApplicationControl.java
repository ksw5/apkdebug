package com.good.gd.support.impl;

import com.good.gd.support.GDConnectedApplicationType;

/* loaded from: classes.dex */
public class GDConnectedApplicationControl {
    private static GDConnectedApplicationControl _instance;
    private GDConnectedApplicationControlListener mListener;
    private GDConnectedApplicationType mMyAppType;

    /* loaded from: classes.dex */
    public enum AppActivationCompletedState {
        CONNECTED_APP_ACTIVATION_SUCCESS,
        CONNECTED_APP_ACTIVATION_USER_CANCELLED,
        CONNECTED_APP_ACTIVATION_ERROR
    }

    private GDConnectedApplicationControl(GDConnectedApplicationControlListener gDConnectedApplicationControlListener, GDConnectedApplicationType gDConnectedApplicationType) {
        this.mMyAppType = gDConnectedApplicationType;
        this.mListener = gDConnectedApplicationControlListener;
    }

    public static GDConnectedApplicationControl createInstance(GDConnectedApplicationControlListener gDConnectedApplicationControlListener, GDConnectedApplicationType gDConnectedApplicationType) {
        if (_instance == null) {
            _instance = new GDConnectedApplicationControl(gDConnectedApplicationControlListener, gDConnectedApplicationType);
        }
        return _instance;
    }

    public static GDConnectedApplicationControl getInstance() {
        return _instance;
    }

    public void ConnectedActivationComplete(AppActivationCompletedState appActivationCompletedState) {
        GDConnectedApplicationSupportImpl.getInstance().onConnectedApplicationActivationComplete(appActivationCompletedState);
    }

    public GDConnectedApplicationControlListener getListener() {
        return this.mListener;
    }

    public GDConnectedApplicationType getMyAppType() {
        return this.mMyAppType;
    }
}
