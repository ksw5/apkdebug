package com.good.gd.apache.http.message;

import com.good.gd.apache.http.util.CharArrayBuffer;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class ParserCursor {
    private final int lowerBound;
    private int pos;
    private final int upperBound;

    public ParserCursor(int i, int i2) {
        if (i >= 0) {
            if (i <= i2) {
                this.lowerBound = i;
                this.upperBound = i2;
                this.pos = i;
                return;
            }
            throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        }
        throw new IndexOutOfBoundsException("Lower bound cannot be negative");
    }

    public boolean atEnd() {
        return this.pos >= this.upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getPos() {
        return this.pos;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(16);
        charArrayBuffer.append('[');
        charArrayBuffer.append(Integer.toString(this.lowerBound));
        charArrayBuffer.append(Typography.greater);
        charArrayBuffer.append(Integer.toString(this.pos));
        charArrayBuffer.append(Typography.greater);
        charArrayBuffer.append(Integer.toString(this.upperBound));
        charArrayBuffer.append(']');
        return charArrayBuffer.toString();
    }

    public void updatePos(int i) {
        if (i >= this.lowerBound) {
            if (i <= this.upperBound) {
                this.pos = i;
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IndexOutOfBoundsException();
    }
}
