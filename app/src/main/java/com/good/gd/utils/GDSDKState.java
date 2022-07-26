package com.good.gd.utils;

import android.content.Context;

/* loaded from: classes.dex */
public class GDSDKState implements GDSDKStateListener {
    private static GDSDKState _instance;
    private GDSDKStateListener mSDKStateListener;

    public static GDSDKState getInstance() {
        if (_instance == null) {
            _instance = new GDSDKState();
        }
        return _instance;
    }

    @Override // com.good.gd.utils.GDSDKStateListener, com.good.gd.containerstate.ContainerState
    public void checkAuthorized() {
        GDSDKStateListener gDSDKStateListener = this.mSDKStateListener;
        if (gDSDKStateListener != null) {
            gDSDKStateListener.checkAuthorized();
        } else {
            ErrorUtils.throwGDNotAuthorizedError();
        }
    }

    @Override // com.good.gd.utils.GDSDKStateListener
    public Context getApplicationContext() {
        GDSDKStateListener gDSDKStateListener = this.mSDKStateListener;
        if (gDSDKStateListener != null) {
            return gDSDKStateListener.getApplicationContext();
        }
        return null;
    }

    @Override // com.good.gd.utils.GDSDKStateListener, com.good.gd.containerstate.ContainerState
    public boolean isWiped() {
        GDSDKStateListener gDSDKStateListener = this.mSDKStateListener;
        if (gDSDKStateListener != null) {
            return gDSDKStateListener.isWiped();
        }
        ErrorUtils.throwGDNotAuthorizedError();
        return false;
    }

    public void setGDSDKStateListener(GDSDKStateListener gDSDKStateListener) {
        this.mSDKStateListener = gDSDKStateListener;
    }
}
