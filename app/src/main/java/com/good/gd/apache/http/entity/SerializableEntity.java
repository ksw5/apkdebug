package com.good.gd.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* loaded from: classes.dex */
public class SerializableEntity extends AbstractHttpEntity {
    private Serializable objRef;
    private byte[] objSer;

    public SerializableEntity(Serializable serializable, boolean z) throws IOException {
        if (serializable != null) {
            if (z) {
                createBytes(serializable);
                return;
            } else {
                this.objRef = serializable;
                return;
            }
        }
        throw new IllegalArgumentException("Source object may not be null");
    }

    private void createBytes(Serializable serializable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.flush();
        this.objSer = byteArrayOutputStream.toByteArray();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException, IllegalStateException {
        if (this.objSer == null) {
            createBytes(this.objRef);
        }
        return new ByteArrayInputStream(this.objSer);
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        byte[] bArr = this.objSer;
        if (bArr == null) {
            return -1L;
        }
        return bArr.length;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return this.objSer == null;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            byte[] bArr = this.objSer;
            if (bArr == null) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(this.objRef);
                objectOutputStream.flush();
                return;
            }
            outputStream.write(bArr);
            outputStream.flush();
            return;
        }
        throw new IllegalArgumentException("Output stream may not be null");
    }
}
