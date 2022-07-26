package com.good.gd.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class BasicEofSensorWatcher implements EofSensorWatcher {
    protected boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicEofSensorWatcher(ManagedClientConnection managedClientConnection, boolean z) {
        if (managedClientConnection != null) {
            this.managedConn = managedClientConnection;
            this.attemptReuse = z;
            return;
        }
        throw new IllegalArgumentException("Connection may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean eofDetected(InputStream inputStream) throws IOException {
        try {
            if (this.attemptReuse) {
                inputStream.close();
                this.managedConn.markReusable();
            }
            this.managedConn.releaseConnection();
            return false;
        } catch (Throwable th) {
            this.managedConn.releaseConnection();
            throw th;
        }
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean streamAbort(InputStream inputStream) throws IOException {
        this.managedConn.abortConnection();
        return false;
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean streamClosed(InputStream inputStream) throws IOException {
        try {
            if (this.attemptReuse) {
                inputStream.close();
                this.managedConn.markReusable();
            }
            this.managedConn.releaseConnection();
            return false;
        } catch (Throwable th) {
            this.managedConn.releaseConnection();
            throw th;
        }
    }
}
