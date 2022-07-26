package com.good.gd.background.detection.event;

import com.good.gd.background.detection.Event;

/* loaded from: classes.dex */
public class SkipEventStrategy implements EventQueueStrategy {
    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public void enqueueEvent(Event event) {
    }

    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public Event getLastEvent() {
        return null;
    }

    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public boolean hasPendingForegroundEvent() {
        return false;
    }
}
