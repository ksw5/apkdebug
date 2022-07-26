package com.good.gd.apache.http.impl.entity;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.entity.ContentLengthStrategy;
import com.good.gd.apache.http.impl.io.ChunkedOutputStream;
import com.good.gd.apache.http.impl.io.ContentLengthOutputStream;
import com.good.gd.apache.http.impl.io.IdentityOutputStream;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class EntitySerializer {
    private final ContentLengthStrategy lenStrategy;

    public EntitySerializer(ContentLengthStrategy contentLengthStrategy) {
        if (contentLengthStrategy != null) {
            this.lenStrategy = contentLengthStrategy;
            return;
        }
        throw new IllegalArgumentException("Content length strategy may not be null");
    }

    protected OutputStream doSerialize(SessionOutputBuffer sessionOutputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        long determineLength = this.lenStrategy.determineLength(httpMessage);
        if (determineLength == -2) {
            return new ChunkedOutputStream(sessionOutputBuffer);
        }
        if (determineLength == -1) {
            return new IdentityOutputStream(sessionOutputBuffer);
        }
        return new ContentLengthOutputStream(sessionOutputBuffer, determineLength);
    }

    public void serialize(SessionOutputBuffer sessionOutputBuffer, HttpMessage httpMessage, HttpEntity httpEntity) throws HttpException, IOException {
        if (sessionOutputBuffer != null) {
            if (httpMessage == null) {
                throw new IllegalArgumentException("HTTP message may not be null");
            }
            if (httpEntity != null) {
                OutputStream doSerialize = doSerialize(sessionOutputBuffer, httpMessage);
                httpEntity.writeTo(doSerialize);
                doSerialize.close();
                return;
            }
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        throw new IllegalArgumentException("Session output buffer may not be null");
    }
}
