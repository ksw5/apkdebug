package com.good.gd.apache.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class FileEntity extends AbstractHttpEntity implements Cloneable {
    protected final File file;

    public FileEntity(File file, String str) {
        if (file != null) {
            this.file = file;
            setContentType(str);
            return;
        }
        throw new IllegalArgumentException("File may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        return this.file.length();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        outputStream.write(bArr, 0, read);
                    } else {
                        outputStream.flush();
                        return;
                    }
                }
            } finally {
                fileInputStream.close();
            }
        } else {
            throw new IllegalArgumentException("Output stream may not be null");
        }
    }
}
