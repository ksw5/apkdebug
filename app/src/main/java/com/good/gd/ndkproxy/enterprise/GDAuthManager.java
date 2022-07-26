package com.good.gd.ndkproxy.enterprise;

import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.backgroundAuth.GDBackgroundAuthImpl;
import com.good.gd.utils.NoPassChecker;

/* loaded from: classes.dex */
public final class GDAuthManager implements NoPassChecker {
    private static GDAuthManager instance;

    public static final int getAuthType() {
        return getCurrentAuthType();
    }

    public static native boolean getBackgroundAuthAllowed();

    public static native int getCurrentAuthType();

    public static synchronized GDAuthManager getInstance() {
        GDAuthManager gDAuthManager;
        synchronized (GDAuthManager.class) {
            if (instance == null) {
                instance = new GDAuthManager();
            }
            gDAuthManager = instance;
        }
        return gDAuthManager;
    }

    @Override // com.good.gd.utils.NoPassChecker
    public boolean isAuthTypeNoPass() {
        return getAuthType() == 5;
    }

    public boolean isBackgroundAuthAllowed() {
        return ((GDBackgroundAuthImpl) GDContext.getInstance().getDynamicsService("background_auth")).isBackgroundAuthAllowed();
    }
}
