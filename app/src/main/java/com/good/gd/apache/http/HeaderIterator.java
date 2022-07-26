package com.good.gd.apache.http;

import java.util.Iterator;

/* loaded from: classes.dex */
public interface HeaderIterator extends Iterator {
    @Override // java.util.Iterator
    boolean hasNext();

    Header nextHeader();
}
