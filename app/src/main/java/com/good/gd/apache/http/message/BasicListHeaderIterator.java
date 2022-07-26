package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class BasicListHeaderIterator implements HeaderIterator {
    protected final List allHeaders;
    protected int currentIndex;
    protected String headerName;
    protected int lastIndex;

    public BasicListHeaderIterator(List list, String str) {
        if (list != null) {
            this.allHeaders = list;
            this.headerName = str;
            this.currentIndex = findNext(-1);
            this.lastIndex = -1;
            return;
        }
        throw new IllegalArgumentException("Header list must not be null.");
    }

    protected boolean filterHeader(int i) {
        if (this.headerName == null) {
            return true;
        }
        return this.headerName.equalsIgnoreCase(((Header) this.allHeaders.get(i)).getName());
    }

    protected int findNext(int i) {
        if (i < -1) {
            return -1;
        }
        int size = this.allHeaders.size() - 1;
        boolean z = false;
        while (!z && i < size) {
            i++;
            z = filterHeader(i);
        }
        if (!z) {
            return -1;
        }
        return i;
    }

    @Override // com.good.gd.apache.http.HeaderIterator, java.util.Iterator
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException {
        return nextHeader();
    }

    @Override // com.good.gd.apache.http.HeaderIterator
    public Header nextHeader() throws NoSuchElementException {
        int i = this.currentIndex;
        if (i >= 0) {
            this.lastIndex = i;
            this.currentIndex = findNext(i);
            return (Header) this.allHeaders.get(i);
        }
        throw new NoSuchElementException("Iteration already finished.");
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        int i = this.lastIndex;
        if (i >= 0) {
            this.allHeaders.remove(i);
            this.lastIndex = -1;
            this.currentIndex--;
            return;
        }
        throw new IllegalStateException("No header to remove.");
    }
}
