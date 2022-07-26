package com.good.gd.apachehttp.impl.client.cache;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.message.BasicHeader;
import com.good.gd.apache.http.message.BasicStatusLine;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apachehttp.client.cache.HttpCacheEntry;
import com.good.gd.apachehttp.client.cache.HttpCacheEntrySerializationException;
import com.good.gd.apachehttp.client.cache.Resource;
import com.good.gd.ndkproxy.GDLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/* loaded from: classes.dex */
public class DefaultHttpCacheEntrySerializer {
    private static final int BASIC_HEADER_TYPE = 1;
    private static final int BUFF_HEADER_TYPE = 2;

    public HttpCacheEntry readFrom(InputStream inputStream) throws IOException {
        Map map;
        Header bufferedHeader;
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            try {
                Date date = (Date) objectInputStream.readObject();
                Date date2 = (Date) objectInputStream.readObject();
                BasicStatusLine basicStatusLine = new BasicStatusLine((ProtocolVersion) objectInputStream.readObject(), ((Integer) objectInputStream.readObject()).intValue(), (String) objectInputStream.readObject());
                int intValue = ((Integer) objectInputStream.readObject()).intValue();
                Header[] headerArr = new Header[intValue];
                for (int i = 0; i < intValue; i++) {
                    int intValue2 = ((Integer) objectInputStream.readObject()).intValue();
                    if (intValue2 == 1) {
                        bufferedHeader = new BasicHeader((String) objectInputStream.readObject(), (String) objectInputStream.readObject());
                    } else if (intValue2 == 2) {
                        int intValue3 = ((Integer) objectInputStream.readObject()).intValue();
                        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(intValue3);
                        charArrayBuffer.append((char[]) objectInputStream.readObject(), 0, intValue3);
                        bufferedHeader = new BufferedHeader(charArrayBuffer);
                    } else {
                        GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::readFrom() - Not supported");
                        throw new UnsupportedOperationException("Uknow header type, cannot read.");
                    }
                    headerArr[i] = bufferedHeader;
                }
                Resource resource = (Resource) objectInputStream.readObject();
                if (((Integer) objectInputStream.readObject()).intValue() != 1) {
                    map = null;
                } else {
                    map = (Map) objectInputStream.readObject();
                }
                return new HttpCacheEntry(date, date2, basicStatusLine, headerArr, resource, map);
            } catch (ClassNotFoundException e) {
                throw new HttpCacheEntrySerializationException("Class not found: " + e.getMessage(), e);
            }
        } finally {
            objectInputStream.close();
        }
    }

    public void writeTo(HttpCacheEntry httpCacheEntry, OutputStream outputStream) throws IOException {
        GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() IN");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        try {
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2");
            objectOutputStream.writeObject(httpCacheEntry.getRequestDate());
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2a");
            objectOutputStream.writeObject(httpCacheEntry.getResponseDate());
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() cacheEntry.getStatusLine():" + httpCacheEntry.getStatusLine().toString());
            StatusLine statusLine = httpCacheEntry.getStatusLine();
            objectOutputStream.writeObject(statusLine.getProtocolVersion());
            objectOutputStream.writeObject(statusLine.getReasonPhrase());
            objectOutputStream.writeObject(Integer.valueOf(statusLine.getStatusCode()));
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2c -- aaa");
            Header[] allHeaders = httpCacheEntry.getAllHeaders();
            objectOutputStream.writeObject(Integer.valueOf(allHeaders.length));
            for (Header header : allHeaders) {
                if (header instanceof BasicHeader) {
                    GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2c -- basic header");
                    objectOutputStream.writeObject(1);
                    objectOutputStream.writeObject(header.getName());
                    objectOutputStream.writeObject(header.getValue());
                } else if (header instanceof BufferedHeader) {
                    GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2c -- buff header");
                    objectOutputStream.writeObject(2);
                    CharArrayBuffer buffer = ((BufferedHeader) header).getBuffer();
                    objectOutputStream.writeObject(Integer.valueOf(buffer.length()));
                    objectOutputStream.writeObject(buffer.buffer());
                } else {
                    GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() - Not supported");
                    throw new UnsupportedOperationException("Uknow header type, cannot save.");
                }
            }
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2d");
            objectOutputStream.writeObject(httpCacheEntry.getResource());
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() 2e");
            Map<String, String> variantMap = httpCacheEntry.getVariantMap();
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() map =" + (variantMap == null ? "null" : variantMap));
            if (variantMap != null) {
                objectOutputStream.writeObject(1);
                objectOutputStream.writeObject(variantMap);
            } else {
                objectOutputStream.writeObject(0);
            }
            objectOutputStream.flush();
            GDLog.DBGPRINTF(16, "DefaultHttpCacheEntrySerializer::insertEntryToCacheTable() OUT");
        } finally {
            objectOutputStream.close();
        }
    }
}
