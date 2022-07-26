package com.good.gd.apache.http.conn.scheme;

import com.good.gd.apache.http.util.LangUtils;
import java.util.Locale;

/* loaded from: classes.dex */
public final class Scheme {
    private final int defaultPort;
    private final boolean layered;
    private final String name;
    private final SocketFactory socketFactory;
    private String stringRep;

    public Scheme(String str, SocketFactory socketFactory, int i) {
        if (str != null) {
            if (socketFactory == null) {
                throw new IllegalArgumentException("Socket factory may not be null");
            }
            if (i > 0 && i <= 65535) {
                this.name = str.toLowerCase(Locale.ENGLISH);
                this.socketFactory = socketFactory;
                this.defaultPort = i;
                this.layered = socketFactory instanceof LayeredSocketFactory;
                return;
            }
            throw new IllegalArgumentException("Port is invalid: " + i);
        }
        throw new IllegalArgumentException("Scheme name may not be null");
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scheme)) {
            return false;
        }
        Scheme scheme = (Scheme) obj;
        return this.name.equals(scheme.name) && this.defaultPort == scheme.defaultPort && this.layered == scheme.layered && this.socketFactory.equals(scheme.socketFactory);
    }

    public final int getDefaultPort() {
        return this.defaultPort;
    }

    public final String getName() {
        return this.name;
    }

    public final SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.defaultPort), this.name), this.layered), this.socketFactory);
    }

    public final boolean isLayered() {
        return this.layered;
    }

    public final int resolvePort(int i) {
        return (i <= 0 || i > 65535) ? this.defaultPort : i;
    }

    public final String toString() {
        if (this.stringRep == null) {
            this.stringRep = this.name + ':' + Integer.toString(this.defaultPort);
        }
        return this.stringRep;
    }
}
