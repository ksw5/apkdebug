package com.good.gd.apache.commons.codec;

import java.util.Comparator;

/* loaded from: classes.dex */
public class StringEncoderComparator implements Comparator {
    private StringEncoder stringEncoder;

    public StringEncoderComparator() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        try {
            return ((Comparable) this.stringEncoder.encode(obj)).compareTo((Comparable) this.stringEncoder.encode(obj2));
        } catch (EncoderException e) {
            return 0;
        }
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }
}
