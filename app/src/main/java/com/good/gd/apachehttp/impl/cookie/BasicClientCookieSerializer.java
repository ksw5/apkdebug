package com.good.gd.apachehttp.impl.cookie;

import com.good.gd.apache.http.impl.cookie.BasicClientCookie;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.util.PII;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/* loaded from: classes.dex */
public class BasicClientCookieSerializer {
    private static final String TAG = "BasicClientCookieSerializer";

    public BasicClientCookie readFrom(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String str = (String) objectInputStream.readObject();
        String str2 = (String) objectInputStream.readObject();
        BasicClientCookie basicClientCookie = new BasicClientCookie(str, str2);
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::readFrom() name[" + str + "], val[" + PII.piiDescription(str2) + "]");
        if (objectInputStream.readInt() == 1) {
            basicClientCookie.setComment((String) objectInputStream.readObject());
        }
        if (objectInputStream.readInt() == 1) {
            basicClientCookie.setDomain((String) objectInputStream.readObject());
        }
        if (objectInputStream.readInt() != 1) {
            GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::readFrom() Null expiryDate");
        } else {
            Date date = new Date(objectInputStream.readLong());
            basicClientCookie.setExpiryDate(date);
            GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::readFrom() expiryDate[" + date + "]");
        }
        if (objectInputStream.readInt() == 1) {
            basicClientCookie.setPath((String) objectInputStream.readObject());
        }
        int readInt = objectInputStream.readInt();
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::readFrom() version[" + readInt + "]");
        basicClientCookie.setVersion(readInt);
        basicClientCookie.setSecure(objectInputStream.readBoolean());
        if (objectInputStream.readInt() == 1) {
            basicClientCookie.setOriginalCookieValue((String) objectInputStream.readObject());
        }
        return basicClientCookie;
    }

    public void writeTo(BasicClientCookie basicClientCookie, ObjectOutputStream objectOutputStream) throws IOException {
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() IN");
        String name = basicClientCookie.getName();
        String value = basicClientCookie.getValue();
        objectOutputStream.writeObject(name);
        objectOutputStream.writeObject(value);
        GDLog.DBGPRINTF(16, String.format("%s::writeTo() name[%s], val[%s]", TAG, name, PII.piiDescription(value)));
        String comment = basicClientCookie.getComment();
        if (comment != null) {
            objectOutputStream.writeInt(1);
            objectOutputStream.writeObject(comment);
        } else {
            objectOutputStream.writeInt(2);
        }
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() comment[" + comment + "]");
        String domain = basicClientCookie.getDomain();
        if (domain != null) {
            objectOutputStream.writeInt(1);
            objectOutputStream.writeObject(domain);
        } else {
            objectOutputStream.writeInt(2);
        }
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() domain[" + domain + "]");
        Date expiryDate = basicClientCookie.getExpiryDate();
        if (expiryDate != null) {
            objectOutputStream.writeInt(1);
            objectOutputStream.writeLong(expiryDate.getTime());
            GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() expiryDate[" + expiryDate + "]");
        } else {
            objectOutputStream.writeInt(2);
            GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() Null expiryDate");
        }
        String path = basicClientCookie.getPath();
        if (path != null) {
            objectOutputStream.writeInt(1);
            objectOutputStream.writeObject(path);
        } else {
            objectOutputStream.writeInt(2);
        }
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() path[" + path + "]");
        int version = basicClientCookie.getVersion();
        objectOutputStream.writeInt(version);
        boolean isSecure = basicClientCookie.isSecure();
        objectOutputStream.writeBoolean(isSecure);
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() version[" + version + "], isSecure:" + isSecure);
        String originalCookieValue = basicClientCookie.getOriginalCookieValue();
        if (originalCookieValue != null) {
            objectOutputStream.writeInt(1);
            objectOutputStream.writeObject(originalCookieValue);
        } else {
            objectOutputStream.writeInt(2);
        }
        objectOutputStream.flush();
        GDLog.DBGPRINTF(16, "BasicClientCookieSerializer::writeTo() OUT");
    }
}
