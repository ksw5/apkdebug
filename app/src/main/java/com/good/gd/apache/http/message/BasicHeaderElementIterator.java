package com.good.gd.apache.http.message;

import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HeaderElementIterator;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class BasicHeaderElementIterator implements HeaderElementIterator {
    private CharArrayBuffer buffer;
    private HeaderElement currentElement;
    private ParserCursor cursor;
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;

    public BasicHeaderElementIterator(HeaderIterator headerIterator, HeaderValueParser headerValueParser) {
        this.currentElement = null;
        this.buffer = null;
        this.cursor = null;
        if (headerIterator != null) {
            if (headerValueParser != null) {
                this.headerIt = headerIterator;
                this.parser = headerValueParser;
                return;
            }
            throw new IllegalArgumentException("Parser may not be null");
        }
        throw new IllegalArgumentException("Header iterator may not be null");
    }

    private void bufferHeaderValue() {
        this.cursor = null;
        this.buffer = null;
        while (this.headerIt.hasNext()) {
            Header nextHeader = this.headerIt.nextHeader();
            if (nextHeader instanceof FormattedHeader) {
                FormattedHeader formattedHeader = (FormattedHeader) nextHeader;
                CharArrayBuffer buffer = formattedHeader.getBuffer();
                this.buffer = buffer;
                ParserCursor parserCursor = new ParserCursor(0, buffer.length());
                this.cursor = parserCursor;
                parserCursor.updatePos(formattedHeader.getValuePos());
                return;
            }
            String value = nextHeader.getValue();
            if (value != null) {
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(value.length());
                this.buffer = charArrayBuffer;
                charArrayBuffer.append(value);
                this.cursor = new ParserCursor(0, this.buffer.length());
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseNextElement() {
        /*
            r3 = this;
        L0:
            com.good.gd.apache.http.HeaderIterator r0 = r3.headerIt
            boolean r0 = r0.hasNext()
            if (r0 != 0) goto Le
            com.good.gd.apache.http.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto Ld
            goto Le
        Ld:
            return
        Le:
            com.good.gd.apache.http.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto L18
            boolean r0 = r0.atEnd()
            if (r0 == 0) goto L1b
        L18:
            r3.bufferHeaderValue()
        L1b:
            com.good.gd.apache.http.message.ParserCursor r0 = r3.cursor
            if (r0 == 0) goto L0
        L1f:
            com.good.gd.apache.http.message.ParserCursor r0 = r3.cursor
            boolean r0 = r0.atEnd()
            if (r0 != 0) goto L44
            com.good.gd.apache.http.message.HeaderValueParser r0 = r3.parser
            com.good.gd.apache.http.util.CharArrayBuffer r1 = r3.buffer
            com.good.gd.apache.http.message.ParserCursor r2 = r3.cursor
            com.good.gd.apache.http.HeaderElement r0 = r0.parseHeaderElement(r1, r2)
            java.lang.String r1 = r0.getName()
            int r1 = r1.length()
            if (r1 != 0) goto L41
            java.lang.String r1 = r0.getValue()
            if (r1 == 0) goto L1f
        L41:
            r3.currentElement = r0
            return
        L44:
            com.good.gd.apache.http.message.ParserCursor r0 = r3.cursor
            boolean r0 = r0.atEnd()
            if (r0 == 0) goto L0
            r0 = 0
            r3.cursor = r0
            r3.buffer = r0
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.message.BasicHeaderElementIterator.parseNextElement():void");
    }

    @Override // com.good.gd.apache.http.HeaderElementIterator, java.util.Iterator
    public boolean hasNext() {
        if (this.currentElement == null) {
            parseNextElement();
        }
        return this.currentElement != null;
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException {
        return nextElement();
    }

    @Override // com.good.gd.apache.http.HeaderElementIterator
    public HeaderElement nextElement() throws NoSuchElementException {
        if (this.currentElement == null) {
            parseNextElement();
        }
        HeaderElement headerElement = this.currentElement;
        if (headerElement != null) {
            this.currentElement = null;
            return headerElement;
        }
        throw new NoSuchElementException("No more header elements available");
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public BasicHeaderElementIterator(HeaderIterator headerIterator) {
        this(headerIterator, BasicHeaderValueParser.DEFAULT);
    }
}
