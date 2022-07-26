package com.good.gd.utils;

import android.app.Activity;

/* loaded from: classes.dex */
public class GDActivityInfo {
    private String mActivityName;

    public GDActivityInfo(Activity activity) {
        this.mActivityName = activity.getClass().getName() + "@" + Integer.toHexString(activity.hashCode());
    }

    public String getActivityName() {
        return this.mActivityName;
    }
}
