package com.good.gd.ndkproxy.ui.data;

/* loaded from: classes.dex */
public interface WebAuth {

    /* loaded from: classes.dex */
    public enum AuthError {
        INVALID_CODE,
        INVALID_STATE,
        FAILED_CONFIG_REQUEST,
        NETWORKING_ERROR
    }

    /* loaded from: classes.dex */
    public interface Listener {
        void onError(AuthError authError);

        void onResult(String str, String str2);

        void onStart();
    }

    void start(Listener listener);
}
