package com.good.gd.ndkproxy.enterprise;

import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.utility.GDAuthTokenCallback;

/* loaded from: classes.dex */
public final class GDEAuthTokenManager {
    private static GDEAuthTokenManager instance;

    /* loaded from: classes.dex */
    class GDAuthTokenCallbackWrapper implements GDAuthTokenCallback {
        private final GDAuthTokenCallback dbjc;

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            final /* synthetic */ String dbjc;

            hbfhc(String str) {
                this.dbjc = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!GDClient.getInstance().isAuthorized() || GDAuthTokenCallbackWrapper.this.dbjc == null) {
                    return;
                }
                GDAuthTokenCallbackWrapper.this.dbjc.onGDAuthTokenSuccess(this.dbjc);
            }
        }

        /* loaded from: classes.dex */
        class yfdke implements Runnable {
            final /* synthetic */ int dbjc;
            final /* synthetic */ String qkduk;

            yfdke(int i, String str) {
                this.dbjc = i;
                this.qkduk = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!GDClient.getInstance().isAuthorized() || GDAuthTokenCallbackWrapper.this.dbjc == null) {
                    return;
                }
                GDAuthTokenCallbackWrapper.this.dbjc.onGDAuthTokenFailure(this.dbjc, this.qkduk);
            }
        }

        public GDAuthTokenCallbackWrapper(GDEAuthTokenManager gDEAuthTokenManager, GDAuthTokenCallback gDAuthTokenCallback) {
            this.dbjc = gDAuthTokenCallback;
        }

        @Override // com.good.gd.utility.GDAuthTokenCallback
        public void onGDAuthTokenFailure(int i, String str) {
            GDClient.getInstance().getClientHandler().post(new yfdke(i, str));
        }

        @Override // com.good.gd.utility.GDAuthTokenCallback
        public void onGDAuthTokenSuccess(String str) {
            GDClient.getInstance().getClientHandler().post(new hbfhc(str));
        }
    }

    private GDEAuthTokenManager() {
        GDContext.getInstance().checkAuthorized();
        try {
            GDLog.DBGPRINTF(16, "GDEAuthTokenManager: Attempting to initialize C++ peer\n");
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDEAuthTokenManager: Cannot initialize C++ peer", e);
        }
    }

    public static synchronized GDEAuthTokenManager getInstance() {
        GDEAuthTokenManager gDEAuthTokenManager;
        synchronized (GDEAuthTokenManager.class) {
            if (instance == null) {
                instance = new GDEAuthTokenManager();
            }
            gDEAuthTokenManager = instance;
        }
        return gDEAuthTokenManager;
    }

    public void getGDAuthToken(String str, String str2, GDAuthTokenCallback gDAuthTokenCallback) {
        GDContext.getInstance().checkAuthorized();
        ndkGetGDAuthTokenForServer(str, str2, new GDAuthTokenCallbackWrapper(this, gDAuthTokenCallback));
    }

    native void ndkGetGDAuthTokenForServer(String str, String str2, GDAuthTokenCallbackWrapper gDAuthTokenCallbackWrapper);

    native void ndkInit();
}
