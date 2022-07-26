package com.good.gd.apache.http;

import java.util.Iterator;

/* loaded from: classes.dex */
public interface HeaderElementIterator extends Iterator {
    @Override // java.util.Iterator
    boolean hasNext();

    HeaderElement nextElement();
}
