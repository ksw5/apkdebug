package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class BasicLineParser implements LineParser {
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser(ProtocolVersion protocolVersion) {
        this.protocol = protocolVersion == null ? HttpVersion.HTTP_1_1 : protocolVersion;
    }

    public static final Header parseHeader(String str, LineParser lineParser) throws ParseException {
        if (str != null) {
            if (lineParser == null) {
                lineParser = DEFAULT;
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
            charArrayBuffer.append(str);
            return lineParser.parseHeader(charArrayBuffer);
        }
        throw new IllegalArgumentException("Value to parse may not be null");
    }

    public static final ProtocolVersion parseProtocolVersion(String str, LineParser lineParser) throws ParseException {
        if (str != null) {
            if (lineParser == null) {
                lineParser = DEFAULT;
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
            charArrayBuffer.append(str);
            return lineParser.parseProtocolVersion(charArrayBuffer, new ParserCursor(0, str.length()));
        }
        throw new IllegalArgumentException("Value to parse may not be null.");
    }

    public static final RequestLine parseRequestLine(String str, LineParser lineParser) throws ParseException {
        if (str != null) {
            if (lineParser == null) {
                lineParser = DEFAULT;
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
            charArrayBuffer.append(str);
            return lineParser.parseRequestLine(charArrayBuffer, new ParserCursor(0, str.length()));
        }
        throw new IllegalArgumentException("Value to parse may not be null.");
    }

    public static final StatusLine parseStatusLine(String str, LineParser lineParser) throws ParseException {
        if (str != null) {
            if (lineParser == null) {
                lineParser = DEFAULT;
            }
            CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
            charArrayBuffer.append(str);
            return lineParser.parseStatusLine(charArrayBuffer, new ParserCursor(0, str.length()));
        }
        throw new IllegalArgumentException("Value to parse may not be null.");
    }

    protected ProtocolVersion createProtocolVersion(int i, int i2) {
        return this.protocol.forVersion(i, i2);
    }

    protected RequestLine createRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(str, str2, protocolVersion);
    }

    protected StatusLine createStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        return new BasicStatusLine(protocolVersion, i, str);
    }

    @Override // com.good.gd.apache.http.message.LineParser
    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        if (charArrayBuffer != null) {
            if (parserCursor != null) {
                int pos = parserCursor.getPos();
                String protocol = this.protocol.getProtocol();
                int length = protocol.length();
                if (charArrayBuffer.length() < length + 4) {
                    return false;
                }
                if (pos < 0) {
                    pos = (charArrayBuffer.length() - 4) - length;
                } else if (pos == 0) {
                    while (pos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
                        pos++;
                    }
                }
                int i = pos + length;
                if (i + 4 > charArrayBuffer.length()) {
                    return false;
                }
                boolean z = true;
                for (int i2 = 0; z && i2 < length; i2++) {
                    z = charArrayBuffer.charAt(pos + i2) == protocol.charAt(i2);
                }
                return z ? charArrayBuffer.charAt(i) == '/' : z;
            }
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        throw new IllegalArgumentException("Char array buffer may not be null");
    }

    protected void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        while (pos < upperBound && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
            pos++;
        }
        parserCursor.updatePos(pos);
    }

    public BasicLineParser() {
        this(null);
    }

    @Override // com.good.gd.apache.http.message.LineParser
    public Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }

    @Override // com.good.gd.apache.http.message.LineParser
    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        if (charArrayBuffer != null) {
            if (parserCursor != null) {
                String protocol = this.protocol.getProtocol();
                int length = protocol.length();
                int pos = parserCursor.getPos();
                int upperBound = parserCursor.getUpperBound();
                skipWhitespace(charArrayBuffer, parserCursor);
                int pos2 = parserCursor.getPos();
                int i = pos2 + length;
                if (i + 4 > upperBound) {
                    throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(pos, upperBound));
                }
                boolean z = false;
                boolean z2 = true;
                for (int i2 = 0; z2 && i2 < length; i2++) {
                    z2 = charArrayBuffer.charAt(pos2 + i2) == protocol.charAt(i2);
                }
                if (!z2) {
                    z = z2;
                } else if (charArrayBuffer.charAt(i) == '/') {
                    z = true;
                }
                if (z) {
                    int i3 = pos2 + length + 1;
                    int indexOf = charArrayBuffer.indexOf(46, i3, upperBound);
                    if (indexOf != -1) {
                        try {
                            int parseInt = Integer.parseInt(charArrayBuffer.substringTrimmed(i3, indexOf));
                            int i4 = indexOf + 1;
                            int indexOf2 = charArrayBuffer.indexOf(32, i4, upperBound);
                            if (indexOf2 == -1) {
                                indexOf2 = upperBound;
                            }
                            try {
                                int parseInt2 = Integer.parseInt(charArrayBuffer.substringTrimmed(i4, indexOf2));
                                parserCursor.updatePos(indexOf2);
                                return createProtocolVersion(parseInt, parseInt2);
                            } catch (NumberFormatException e) {
                                throw new ParseException("Invalid protocol minor version number: " + charArrayBuffer.substring(pos, upperBound));
                            }
                        } catch (NumberFormatException e2) {
                            throw new ParseException("Invalid protocol major version number: " + charArrayBuffer.substring(pos, upperBound));
                        }
                    }
                    throw new ParseException("Invalid protocol version number: " + charArrayBuffer.substring(pos, upperBound));
                }
                throw new ParseException("Not a valid protocol version: " + charArrayBuffer.substring(pos, upperBound));
            }
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        throw new IllegalArgumentException("Char array buffer may not be null");
    }

    @Override // com.good.gd.apache.http.message.LineParser
    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        if (charArrayBuffer != null) {
            if (parserCursor != null) {
                int pos = parserCursor.getPos();
                int upperBound = parserCursor.getUpperBound();
                try {
                    skipWhitespace(charArrayBuffer, parserCursor);
                    int pos2 = parserCursor.getPos();
                    int indexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
                    if (indexOf >= 0) {
                        String substringTrimmed = charArrayBuffer.substringTrimmed(pos2, indexOf);
                        parserCursor.updatePos(indexOf);
                        skipWhitespace(charArrayBuffer, parserCursor);
                        int pos3 = parserCursor.getPos();
                        int indexOf2 = charArrayBuffer.indexOf(32, pos3, upperBound);
                        if (indexOf2 >= 0) {
                            String substringTrimmed2 = charArrayBuffer.substringTrimmed(pos3, indexOf2);
                            parserCursor.updatePos(indexOf2);
                            ProtocolVersion parseProtocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
                            skipWhitespace(charArrayBuffer, parserCursor);
                            if (parserCursor.atEnd()) {
                                return createRequestLine(substringTrimmed, substringTrimmed2, parseProtocolVersion);
                            }
                            throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
                        }
                        throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
                    }
                    throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
                } catch (IndexOutOfBoundsException e) {
                    throw new ParseException("Invalid request line: " + charArrayBuffer.substring(pos, upperBound));
                }
            }
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        throw new IllegalArgumentException("Char array buffer may not be null");
    }

    @Override // com.good.gd.apache.http.message.LineParser
    public StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        if (charArrayBuffer != null) {
            if (parserCursor != null) {
                int pos = parserCursor.getPos();
                int upperBound = parserCursor.getUpperBound();
                try {
                    ProtocolVersion parseProtocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
                    skipWhitespace(charArrayBuffer, parserCursor);
                    int pos2 = parserCursor.getPos();
                    int indexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
                    if (indexOf < 0) {
                        indexOf = upperBound;
                    }
                    try {
                        return createStatusLine(parseProtocolVersion, Integer.parseInt(charArrayBuffer.substringTrimmed(pos2, indexOf)), indexOf < upperBound ? charArrayBuffer.substringTrimmed(indexOf, upperBound) : "");
                    } catch (NumberFormatException e) {
                        throw new ParseException("Unable to parse status code from status line: " + charArrayBuffer.substring(pos, upperBound));
                    }
                } catch (IndexOutOfBoundsException e2) {
                    throw new ParseException("Invalid status line: " + charArrayBuffer.substring(pos, upperBound));
                }
            }
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        throw new IllegalArgumentException("Char array buffer may not be null");
    }
}
