package com.good.gd;

import java.util.Map;

/* loaded from: classes.dex */
public interface GDStateListener {
    void onAuthorized();

    void onLocked();

    void onUpdateConfig(Map<String, Object> map);

    void onUpdateEntitlements();

    void onUpdatePolicy(Map<String, Object> map);

    void onUpdateServices();

    void onWiped();
}
