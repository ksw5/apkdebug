package com.good.gd.utility;

/* loaded from: classes.dex */
public interface GDAuthTokenCallback {
    void onGDAuthTokenFailure(int i, String str);

    void onGDAuthTokenSuccess(String str);
}
