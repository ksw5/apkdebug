package com.good.gd.utils;

import android.content.Context;

/* loaded from: classes.dex */
public interface GDSDKStateListener {
    void checkAuthorized();

    Context getApplicationContext();

    boolean isWiped();
}
