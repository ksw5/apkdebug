package com.good.gd.background.detection.event;

import com.good.gd.background.detection.Event;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class EventEnqueueStrategy implements EventQueueStrategy {
    private LinkedList<Event> queuedEvents = new LinkedList<>();

    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public void enqueueEvent(Event event) {
        synchronized (this) {
            this.queuedEvents.add(event);
        }
    }

    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public Event getLastEvent() {
        Event last;
        synchronized (this) {
            last = this.queuedEvents.size() > 0 ? this.queuedEvents.getLast() : null;
        }
        return last;
    }

    @Override // com.good.gd.background.detection.event.EventQueueStrategy
    public boolean hasPendingForegroundEvent() {
        boolean z;
        synchronized (this) {
            boolean z2 = false;
            if (this.queuedEvents.size() == 0) {
                return false;
            }
            Event last = this.queuedEvents.getLast();
            if (!((last == Event.ENTERING_FOREGROUND || last == Event.ENTERING_BACKGROUND) ? false : true)) {
                z = false;
            } else {
                Iterator<Event> descendingIterator = this.queuedEvents.descendingIterator();
                if (descendingIterator.hasNext()) {
                    descendingIterator.next();
                }
                boolean z3 = false;
                z = false;
                while (descendingIterator.hasNext() && !z3) {
                    int ordinal = descendingIterator.next().ordinal();
                    if (ordinal == 0) {
                        z3 = true;
                        z = true;
                    } else if (ordinal == 1) {
                        z3 = true;
                    }
                }
            }
            if (last == Event.ENTERING_FOREGROUND || z) {
                z2 = true;
            }
            return z2;
        }
    }
}
