package com.good.gd.ui.utils.permissions;

/* loaded from: classes.dex */
public interface PermissionsListener {
    void onAllPermissionsRequested();

    void onPermissionDenied(String str);

    void onPermissionGranted(String str);

    boolean shouldShowExplanation(String str);

    void showExplanation(String str);
}
