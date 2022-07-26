package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/* loaded from: classes.dex */
public class SocketInputBuffer extends AbstractSessionInputBuffer {
    private final Socket socket;

    public SocketInputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        if (socket != null) {
            this.socket = socket;
            init(socket.getInputStream(), 8192, httpParams);
            return;
        }
        throw new IllegalArgumentException("Socket may not be null");
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public boolean isDataAvailable(int i) throws IOException {
        boolean hasBufferedData = hasBufferedData();
        if (!hasBufferedData) {
            int soTimeout = this.socket.getSoTimeout();
            try {
                this.socket.setSoTimeout(i);
                fillBuffer();
                hasBufferedData = hasBufferedData();
            } catch (InterruptedIOException e) {
                if (!(e instanceof SocketTimeoutException)) {
                    throw e;
                }
            } finally {
                this.socket.setSoTimeout(soTimeout);
            }
        }
        return hasBufferedData;
    }

    public boolean isStale() throws IOException {
        boolean z = false;
        if (hasBufferedData()) {
            return false;
        }
        int soTimeout = this.socket.getSoTimeout();
        try {
            this.socket.setSoTimeout(1);
            if (fillBuffer() == -1) {
                z = true;
            }
            return z;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e2) {
            return true;
        } finally {
            this.socket.setSoTimeout(soTimeout);
        }
    }
}
