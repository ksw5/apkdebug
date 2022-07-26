package com.good.gd.ui.utils.sis;

import android.content.Context;
import android.view.View;

/* loaded from: classes.dex */
public class SISProxy {
    private static UXActionListener UXActionListener;
    private static ActivityStatusListener activityStatusListener;
    private static LocationDialogListener locationDialogListener;
    private static LocationInfoProvider locationInfoProvider;
    private static AnalyticsLogger logger;
    private static TouchListenersProvider touchListenersProvider;

    public static ActivityStatusListener getActivityStatusListener() {
        return activityStatusListener;
    }

    public static View.OnTouchListener getListenerForSwipeRightEvent(Context context, SwipeRightListener swipeRightListener) {
        TouchListenersProvider touchListenersProvider2 = touchListenersProvider;
        if (touchListenersProvider2 != null) {
            return touchListenersProvider2.getOnTouchListenerForSwipeRight(context, swipeRightListener);
        }
        return null;
    }

    public static LocationDialogListener getLocationDialogListener() {
        return locationDialogListener;
    }

    public static LocationInfoProvider getLocationInfoProvider() {
        return locationInfoProvider;
    }

    public static AnalyticsLogger getLogger() {
        return logger;
    }

    public static UXActionListener getUXActionListener() {
        return UXActionListener;
    }

    public static void setActivityStatusListener(ActivityStatusListener activityStatusListener2) {
        activityStatusListener = activityStatusListener2;
    }

    public static void setLocationDialogListener(LocationDialogListener locationDialogListener2) {
        locationDialogListener = locationDialogListener2;
    }

    public static void setLocationInfoProvider(LocationInfoProvider locationInfoProvider2) {
        locationInfoProvider = locationInfoProvider2;
    }

    public static void setLogger(AnalyticsLogger analyticsLogger) {
        logger = analyticsLogger;
    }

    public static void setTouchListenersProvider(TouchListenersProvider touchListenersProvider2) {
        touchListenersProvider = touchListenersProvider2;
    }

    public static void setUXActionListener(UXActionListener uXActionListener) {
        UXActionListener = uXActionListener;
    }
}
