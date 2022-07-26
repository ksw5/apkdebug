package com.good.gd.background.detection.event;

import com.good.gd.background.detection.Event;

/* loaded from: classes.dex */
public interface EventQueueStrategy {
    void enqueueEvent(Event event);

    Event getLastEvent();

    boolean hasPendingForegroundEvent();
}
