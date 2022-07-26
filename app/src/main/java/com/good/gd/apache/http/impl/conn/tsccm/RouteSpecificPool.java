package com.good.gd.apache.http.impl.conn.tsccm;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.util.LangUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/* loaded from: classes.dex */
public class RouteSpecificPool {
    protected final int maxEntries;
    protected final HttpRoute route;
    private final Log log = LogFactory.getLog(RouteSpecificPool.class);
    protected final LinkedList<BasicPoolEntry> freeEntries = new LinkedList<>();
    protected final Queue<WaitingThread> waitingThreads = new LinkedList();
    protected int numEntries = 0;

    public RouteSpecificPool(HttpRoute httpRoute, int i) {
        this.route = httpRoute;
        this.maxEntries = i;
    }

    public BasicPoolEntry allocEntry(Object obj) {
        if (!this.freeEntries.isEmpty()) {
            LinkedList<BasicPoolEntry> linkedList = this.freeEntries;
            ListIterator<BasicPoolEntry> listIterator = linkedList.listIterator(linkedList.size());
            while (listIterator.hasPrevious()) {
                BasicPoolEntry previous = listIterator.previous();
                if (LangUtils.equals(obj, previous.getState())) {
                    listIterator.remove();
                    return previous;
                }
            }
        }
        if (!this.freeEntries.isEmpty()) {
            BasicPoolEntry remove = this.freeEntries.remove();
            remove.setState(null);
            try {
                remove.getConnection().close();
            } catch (IOException e) {
                this.log.debug("I/O error closing connection", e);
            }
            return remove;
        }
        return null;
    }

    public void createdEntry(BasicPoolEntry basicPoolEntry) {
        if (this.route.equals(basicPoolEntry.getPlannedRoute())) {
            this.numEntries++;
            return;
        }
        throw new IllegalArgumentException("Entry not planned for this pool.\npool: " + this.route + "\nplan: " + basicPoolEntry.getPlannedRoute());
    }

    public boolean deleteEntry(BasicPoolEntry basicPoolEntry) {
        boolean remove = this.freeEntries.remove(basicPoolEntry);
        if (remove) {
            this.numEntries--;
        }
        return remove;
    }

    public void dropEntry() {
        int i = this.numEntries;
        if (i >= 1) {
            this.numEntries = i - 1;
            return;
        }
        throw new IllegalStateException("There is no entry that could be dropped.");
    }

    public void freeEntry(BasicPoolEntry basicPoolEntry) {
        int i = this.numEntries;
        if (i >= 1) {
            if (i > this.freeEntries.size()) {
                this.freeEntries.add(basicPoolEntry);
                return;
            }
            throw new IllegalStateException("No entry allocated from this pool. " + this.route);
        }
        throw new IllegalStateException("No entry created for this pool. " + this.route);
    }

    public int getCapacity() {
        return this.maxEntries - this.numEntries;
    }

    public final int getEntryCount() {
        return this.numEntries;
    }

    public final int getMaxEntries() {
        return this.maxEntries;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }

    public boolean hasThread() {
        return !this.waitingThreads.isEmpty();
    }

    public boolean isUnused() {
        return this.numEntries < 1 && this.waitingThreads.isEmpty();
    }

    public WaitingThread nextThread() {
        return this.waitingThreads.peek();
    }

    public void queueThread(WaitingThread waitingThread) {
        if (waitingThread != null) {
            this.waitingThreads.add(waitingThread);
            return;
        }
        throw new IllegalArgumentException("Waiting thread must not be null.");
    }

    public void removeThread(WaitingThread waitingThread) {
        if (waitingThread == null) {
            return;
        }
        this.waitingThreads.remove(waitingThread);
    }
}
