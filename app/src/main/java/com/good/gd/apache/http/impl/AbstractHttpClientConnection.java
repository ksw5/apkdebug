package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpClientConnection;
import com.good.gd.apache.http.HttpConnectionMetrics;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.impl.entity.EntityDeserializer;
import com.good.gd.apache.http.impl.entity.EntitySerializer;
import com.good.gd.apache.http.impl.entity.LaxContentLengthStrategy;
import com.good.gd.apache.http.impl.entity.StrictContentLengthStrategy;
import com.good.gd.apache.http.impl.io.HttpRequestWriter;
import com.good.gd.apache.http.impl.io.HttpResponseParser;
import com.good.gd.apache.http.impl.io.SocketInputBuffer;
import com.good.gd.apache.http.io.HttpMessageParser;
import com.good.gd.apache.http.io.HttpMessageWriter;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractHttpClientConnection implements HttpClientConnection {
    private SessionInputBuffer inbuffer = null;
    private SessionOutputBuffer outbuffer = null;
    private HttpMessageParser responseParser = null;
    private HttpMessageWriter requestWriter = null;
    private HttpConnectionMetricsImpl metrics = null;
    private final EntitySerializer entityserializer = createEntitySerializer();
    private final EntityDeserializer entitydeserializer = createEntityDeserializer();

    protected abstract void assertOpen() throws IllegalStateException;

    protected EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new LaxContentLengthStrategy());
    }

    protected EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }

    protected HttpResponseFactory createHttpResponseFactory() {
        return new DefaultHttpResponseFactory();
    }

    protected HttpMessageWriter createRequestWriter(SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        return new HttpRequestWriter(sessionOutputBuffer, null, httpParams);
    }

    protected HttpMessageParser createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new HttpResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doFlush() throws IOException {
        this.outbuffer.flush();
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void flush() throws IOException {
        assertOpen();
        doFlush();
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public HttpConnectionMetrics getMetrics() {
        return this.metrics;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init(SessionInputBuffer sessionInputBuffer, SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        if (sessionInputBuffer != null) {
            if (sessionOutputBuffer != null) {
                this.inbuffer = sessionInputBuffer;
                this.outbuffer = sessionOutputBuffer;
                this.responseParser = createResponseParser(sessionInputBuffer, createHttpResponseFactory(), httpParams);
                this.requestWriter = createRequestWriter(sessionOutputBuffer, httpParams);
                this.metrics = new HttpConnectionMetricsImpl(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
                return;
            }
            throw new IllegalArgumentException("Output session buffer may not be null");
        }
        throw new IllegalArgumentException("Input session buffer may not be null");
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public boolean isResponseAvailable(int i) throws IOException {
        assertOpen();
        return this.inbuffer.isDataAvailable(i);
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public boolean isStale() {
        if (!isOpen()) {
            return true;
        }
        try {
            SessionInputBuffer sessionInputBuffer = this.inbuffer;
            if (sessionInputBuffer instanceof SocketInputBuffer) {
                return ((SocketInputBuffer) sessionInputBuffer).isStale();
            }
            sessionInputBuffer.isDataAvailable(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        if (httpResponse != null) {
            assertOpen();
            httpResponse.setEntity(this.entitydeserializer.deserialize(this.inbuffer, httpResponse));
            return;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        assertOpen();
        HttpResponse httpResponse = (HttpResponse) this.responseParser.parse();
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.metrics.incrementResponseCount();
        }
        return httpResponse;
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        if (httpEntityEnclosingRequest != null) {
            assertOpen();
            if (httpEntityEnclosingRequest.getEntity() == null) {
                return;
            }
            this.entityserializer.serialize(this.outbuffer, httpEntityEnclosingRequest, httpEntityEnclosingRequest.getEntity());
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        if (httpRequest != null) {
            assertOpen();
            this.requestWriter.write(httpRequest);
            this.metrics.incrementRequestCount();
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
