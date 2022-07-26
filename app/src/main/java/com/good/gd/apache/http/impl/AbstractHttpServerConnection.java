package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpConnectionMetrics;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestFactory;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpServerConnection;
import com.good.gd.apache.http.impl.entity.EntityDeserializer;
import com.good.gd.apache.http.impl.entity.EntitySerializer;
import com.good.gd.apache.http.impl.entity.LaxContentLengthStrategy;
import com.good.gd.apache.http.impl.entity.StrictContentLengthStrategy;
import com.good.gd.apache.http.impl.io.HttpRequestParser;
import com.good.gd.apache.http.impl.io.HttpResponseWriter;
import com.good.gd.apache.http.io.HttpMessageParser;
import com.good.gd.apache.http.io.HttpMessageWriter;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractHttpServerConnection implements HttpServerConnection {
    private SessionInputBuffer inbuffer = null;
    private SessionOutputBuffer outbuffer = null;
    private HttpMessageParser requestParser = null;
    private HttpMessageWriter responseWriter = null;
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

    protected HttpRequestFactory createHttpRequestFactory() {
        return new DefaultHttpRequestFactory();
    }

    protected HttpMessageParser createRequestParser(SessionInputBuffer sessionInputBuffer, HttpRequestFactory httpRequestFactory, HttpParams httpParams) {
        return new HttpRequestParser(sessionInputBuffer, null, httpRequestFactory, httpParams);
    }

    protected HttpMessageWriter createResponseWriter(SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        return new HttpResponseWriter(sessionOutputBuffer, null, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void doFlush() throws IOException {
        this.outbuffer.flush();
    }

    @Override // com.good.gd.apache.http.HttpServerConnection
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
                this.requestParser = createRequestParser(sessionInputBuffer, createHttpRequestFactory(), httpParams);
                this.responseWriter = createResponseWriter(sessionOutputBuffer, httpParams);
                this.metrics = new HttpConnectionMetricsImpl(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
                return;
            }
            throw new IllegalArgumentException("Output session buffer may not be null");
        }
        throw new IllegalArgumentException("Input session buffer may not be null");
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public boolean isStale() {
        assertOpen();
        try {
            this.inbuffer.isDataAvailable(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    @Override // com.good.gd.apache.http.HttpServerConnection
    public void receiveRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        if (httpEntityEnclosingRequest != null) {
            assertOpen();
            httpEntityEnclosingRequest.setEntity(this.entitydeserializer.deserialize(this.inbuffer, httpEntityEnclosingRequest));
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    @Override // com.good.gd.apache.http.HttpServerConnection
    public HttpRequest receiveRequestHeader() throws HttpException, IOException {
        assertOpen();
        HttpRequest httpRequest = (HttpRequest) this.requestParser.parse();
        this.metrics.incrementRequestCount();
        return httpRequest;
    }

    @Override // com.good.gd.apache.http.HttpServerConnection
    public void sendResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        if (httpResponse.getEntity() == null) {
            return;
        }
        this.entityserializer.serialize(this.outbuffer, httpResponse, httpResponse.getEntity());
    }

    @Override // com.good.gd.apache.http.HttpServerConnection
    public void sendResponseHeader(HttpResponse httpResponse) throws HttpException, IOException {
        if (httpResponse != null) {
            assertOpen();
            this.responseWriter.write(httpResponse);
            if (httpResponse.getStatusLine().getStatusCode() < 200) {
                return;
            }
            this.metrics.incrementResponseCount();
            return;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
