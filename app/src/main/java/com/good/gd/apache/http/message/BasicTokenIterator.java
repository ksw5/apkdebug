package com.good.gd.apache.http.message;

import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.TokenIterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class BasicTokenIterator implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected String currentHeader;
    protected String currentToken;
    protected final HeaderIterator headerIt;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        if (headerIterator != null) {
            this.headerIt = headerIterator;
            this.searchPos = findNext(-1);
            return;
        }
        throw new IllegalArgumentException("Header iterator must not be null.");
    }

    protected String createToken(String str, int i, int i2) {
        return str.substring(i, i2);
    }

    protected int findNext(int i) throws ParseException {
        int findTokenSeparator;
        if (i < 0) {
            if (!this.headerIt.hasNext()) {
                return -1;
            }
            this.currentHeader = this.headerIt.nextHeader().getValue();
            findTokenSeparator = 0;
        } else {
            findTokenSeparator = findTokenSeparator(i);
        }
        int findTokenStart = findTokenStart(findTokenSeparator);
        if (findTokenStart < 0) {
            this.currentToken = null;
            return -1;
        }
        int findTokenEnd = findTokenEnd(findTokenStart);
        this.currentToken = createToken(this.currentHeader, findTokenStart, findTokenEnd);
        return findTokenEnd;
    }

    protected int findTokenEnd(int i) {
        if (i >= 0) {
            int length = this.currentHeader.length();
            int i2 = i + 1;
            while (i2 < length && isTokenChar(this.currentHeader.charAt(i2))) {
                i2++;
            }
            return i2;
        }
        throw new IllegalArgumentException("Token start position must not be negative: " + i);
    }

    protected int findTokenSeparator(int i) {
        if (i >= 0) {
            boolean z = false;
            int length = this.currentHeader.length();
            while (!z && i < length) {
                char charAt = this.currentHeader.charAt(i);
                if (isTokenSeparator(charAt)) {
                    z = true;
                } else if (!isWhitespace(charAt)) {
                    if (isTokenChar(charAt)) {
                        throw new ParseException("Tokens without separator (pos " + i + "): " + this.currentHeader);
                    }
                    throw new ParseException("Invalid character after token (pos " + i + "): " + this.currentHeader);
                } else {
                    i++;
                }
            }
            return i;
        }
        throw new IllegalArgumentException("Search position must not be negative: " + i);
    }

    protected int findTokenStart(int i) {
        if (i >= 0) {
            boolean z = false;
            while (!z) {
                String str = this.currentHeader;
                if (str == null) {
                    break;
                }
                int length = str.length();
                while (!z && i < length) {
                    char charAt = this.currentHeader.charAt(i);
                    if (isTokenSeparator(charAt) || isWhitespace(charAt)) {
                        i++;
                    } else if (!isTokenChar(this.currentHeader.charAt(i))) {
                        throw new ParseException("Invalid character before token (pos " + i + "): " + this.currentHeader);
                    } else {
                        z = true;
                    }
                }
                if (!z) {
                    if (this.headerIt.hasNext()) {
                        this.currentHeader = this.headerIt.nextHeader().getValue();
                        i = 0;
                    } else {
                        this.currentHeader = null;
                    }
                }
            }
            if (!z) {
                return -1;
            }
            return i;
        }
        throw new IllegalArgumentException("Search position must not be negative: " + i);
    }

    @Override // com.good.gd.apache.http.TokenIterator, java.util.Iterator
    public boolean hasNext() {
        return this.currentToken != null;
    }

    protected boolean isHttpSeparator(char c) {
        return HTTP_SEPARATORS.indexOf(c) >= 0;
    }

    protected boolean isTokenChar(char c) {
        if (Character.isLetterOrDigit(c)) {
            return true;
        }
        return !Character.isISOControl(c) && !isHttpSeparator(c);
    }

    protected boolean isTokenSeparator(char c) {
        return c == ',';
    }

    protected boolean isWhitespace(char c) {
        return c == '\t' || Character.isSpaceChar(c);
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException, ParseException {
        return nextToken();
    }

    @Override // com.good.gd.apache.http.TokenIterator
    public String nextToken() throws NoSuchElementException, ParseException {
        String str = this.currentToken;
        if (str != null) {
            this.searchPos = findNext(this.searchPos);
            return str;
        }
        throw new NoSuchElementException("Iteration already finished.");
    }

    @Override // java.util.Iterator
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }
}
