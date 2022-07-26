package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.entity.HttpEntityWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class BasicManagedEntity extends HttpEntityWrapper implements ConnectionReleaseTrigger, EofSensorWatcher {
    protected final boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicManagedEntity(HttpEntity httpEntity, ManagedClientConnection managedClientConnection, boolean z) {
        super(httpEntity);
        if (managedClientConnection != null) {
            this.managedConn = managedClientConnection;
            this.attemptReuse = z;
            return;
        }
        throw new IllegalArgumentException("Connection may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.ConnectionReleaseTrigger
    public void abortConnection() throws IOException {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection != null) {
            try {
                managedClientConnection.abortConnection();
            } finally {
                this.managedConn = null;
            }
        }
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        if (this.managedConn == null) {
            return;
        }
        try {
            if (this.attemptReuse) {
                this.wrappedEntity.consumeContent();
                this.managedConn.markReusable();
            }
        } finally {
            releaseManagedConnection();
        }
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean eofDetected(InputStream inputStream) throws IOException {
        try {
            if (this.attemptReuse && this.managedConn != null) {
                inputStream.close();
                this.managedConn.markReusable();
            }
            releaseManagedConnection();
            return false;
        } catch (Throwable th) {
            releaseManagedConnection();
            throw th;
        }
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // com.good.gd.apache.http.conn.ConnectionReleaseTrigger
    public void releaseConnection() throws IOException {
        consumeContent();
    }

    protected void releaseManagedConnection() throws IOException {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection != null) {
            try {
                managedClientConnection.releaseConnection();
            } finally {
                this.managedConn = null;
            }
        }
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean streamAbort(InputStream inputStream) throws IOException {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection != null) {
            managedClientConnection.abortConnection();
            return false;
        }
        return false;
    }

    @Override // com.good.gd.apache.http.conn.EofSensorWatcher
    public boolean streamClosed(InputStream inputStream) throws IOException {
        try {
            if (this.attemptReuse && this.managedConn != null) {
                inputStream.close();
                this.managedConn.markReusable();
            }
            releaseManagedConnection();
            return false;
        } catch (Throwable th) {
            releaseManagedConnection();
            throw th;
        }
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        super.writeTo(outputStream);
        consumeContent();
    }
}
