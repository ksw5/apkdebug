package com.good.gd.ui.utils.permissions;

import android.content.Context;
import java.util.LinkedList;

/* loaded from: classes.dex */
public interface PermissionsRequestor {
    void cancelRequesting();

    boolean isPermissionGranted(String str);

    void onPermissions(int i, String[] strArr, int[] iArr);

    void removePermission(String str);

    void requestNextPermissions();

    void requestPermissions(Context context, LinkedList<String> linkedList, int i, PermissionsListener permissionsListener);
}
