package com.good.gd.ndkproxy.net;

/* loaded from: classes.dex */
public class GDSocketEventsListenerHolder {
    private static GDSocketEventsListener listener;

    public static GDSocketEventsListener getListener() {
        return listener;
    }

    public static void setListener(GDSocketEventsListener gDSocketEventsListener) {
        listener = gDSocketEventsListener;
    }
}
