package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.HttpServerConnection;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.MethodNotSupportedException;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.UnsupportedHttpVersionException;
import com.good.gd.apache.http.entity.ByteArrayEntity;
import com.good.gd.apache.http.params.DefaultedHttpParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.util.EncodingUtils;
import java.io.IOException;

/* loaded from: classes.dex */
public class HttpService {
    private HttpParams params = null;
    private HttpProcessor processor = null;
    private HttpRequestHandlerResolver handlerResolver = null;
    private ConnectionReuseStrategy connStrategy = null;
    private HttpResponseFactory responseFactory = null;
    private HttpExpectationVerifier expectationVerifier = null;

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory) {
        setHttpProcessor(httpProcessor);
        setConnReuseStrategy(connectionReuseStrategy);
        setResponseFactory(httpResponseFactory);
    }

    protected void doService(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        HttpRequestHandler httpRequestHandler;
        if (this.handlerResolver == null) {
            httpRequestHandler = null;
        } else {
            httpRequestHandler = this.handlerResolver.lookup(httpRequest.getRequestLine().getUri());
        }
        if (httpRequestHandler != null) {
            httpRequestHandler.handle(httpRequest, httpResponse, httpContext);
        } else {
            httpResponse.setStatusCode(501);
        }
    }

    public HttpParams getParams() {
        return this.params;
    }

    protected void handleException(HttpException httpException, HttpResponse httpResponse) {
        if (httpException instanceof MethodNotSupportedException) {
            httpResponse.setStatusCode(501);
        } else if (httpException instanceof UnsupportedHttpVersionException) {
            httpResponse.setStatusCode(505);
        } else if (httpException instanceof ProtocolException) {
            httpResponse.setStatusCode(400);
        } else {
            httpResponse.setStatusCode(500);
        }
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(EncodingUtils.getAsciiBytes(httpException.getMessage()));
        byteArrayEntity.setContentType("text/plain; charset=US-ASCII");
        httpResponse.setEntity(byteArrayEntity);
    }

    public void handleRequest(HttpServerConnection httpServerConnection, HttpContext httpContext) throws IOException, HttpException {
        HttpResponse newHttpResponse;
        HttpEntity entity;
        httpContext.setAttribute(ExecutionContext.HTTP_CONNECTION, httpServerConnection);
        try {
            HttpRequest receiveRequestHeader = httpServerConnection.receiveRequestHeader();
            receiveRequestHeader.setParams(new DefaultedHttpParams(receiveRequestHeader.getParams(), this.params));
            ProtocolVersion protocolVersion = receiveRequestHeader.getRequestLine().getProtocolVersion();
            if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_1)) {
                protocolVersion = HttpVersion.HTTP_1_1;
            }
            newHttpResponse = null;
            if (receiveRequestHeader instanceof HttpEntityEnclosingRequest) {
                if (((HttpEntityEnclosingRequest) receiveRequestHeader).expectContinue()) {
                    HttpResponse newHttpResponse2 = this.responseFactory.newHttpResponse(protocolVersion, 100, httpContext);
                    newHttpResponse2.setParams(new DefaultedHttpParams(newHttpResponse2.getParams(), this.params));
                    HttpExpectationVerifier httpExpectationVerifier = this.expectationVerifier;
                    if (httpExpectationVerifier != null) {
                        try {
                            httpExpectationVerifier.verify(receiveRequestHeader, newHttpResponse2, httpContext);
                        } catch (HttpException e) {
                            HttpResponse newHttpResponse3 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpContext);
                            newHttpResponse3.setParams(new DefaultedHttpParams(newHttpResponse3.getParams(), this.params));
                            handleException(e, newHttpResponse3);
                            newHttpResponse2 = newHttpResponse3;
                        }
                    }
                    if (newHttpResponse2.getStatusLine().getStatusCode() >= 200) {
                        newHttpResponse = newHttpResponse2;
                    } else {
                        httpServerConnection.sendResponseHeader(newHttpResponse2);
                        httpServerConnection.flush();
                        httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest) receiveRequestHeader);
                    }
                } else {
                    httpServerConnection.receiveRequestEntity((HttpEntityEnclosingRequest) receiveRequestHeader);
                }
            }
            if (newHttpResponse == null) {
                newHttpResponse = this.responseFactory.newHttpResponse(protocolVersion, 200, httpContext);
                newHttpResponse.setParams(new DefaultedHttpParams(newHttpResponse.getParams(), this.params));
                httpContext.setAttribute(ExecutionContext.HTTP_REQUEST, receiveRequestHeader);
                httpContext.setAttribute(ExecutionContext.HTTP_RESPONSE, newHttpResponse);
                this.processor.process(receiveRequestHeader, httpContext);
                doService(receiveRequestHeader, newHttpResponse, httpContext);
            }
            if ((receiveRequestHeader instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) receiveRequestHeader).getEntity()) != null) {
                entity.consumeContent();
            }
        } catch (HttpException e2) {
            newHttpResponse = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpContext);
            newHttpResponse.setParams(new DefaultedHttpParams(newHttpResponse.getParams(), this.params));
            handleException(e2, newHttpResponse);
        }
        this.processor.process(newHttpResponse, httpContext);
        httpServerConnection.sendResponseHeader(newHttpResponse);
        httpServerConnection.sendResponseEntity(newHttpResponse);
        httpServerConnection.flush();
        if (!this.connStrategy.keepAlive(newHttpResponse, httpContext)) {
            httpServerConnection.close();
        }
    }

    public void setConnReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        if (connectionReuseStrategy != null) {
            this.connStrategy = connectionReuseStrategy;
            return;
        }
        throw new IllegalArgumentException("Connection reuse strategy may not be null");
    }

    public void setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.expectationVerifier = httpExpectationVerifier;
    }

    public void setHandlerResolver(HttpRequestHandlerResolver httpRequestHandlerResolver) {
        this.handlerResolver = httpRequestHandlerResolver;
    }

    public void setHttpProcessor(HttpProcessor httpProcessor) {
        if (httpProcessor != null) {
            this.processor = httpProcessor;
            return;
        }
        throw new IllegalArgumentException("HTTP processor may not be null.");
    }

    public void setParams(HttpParams httpParams) {
        this.params = httpParams;
    }

    public void setResponseFactory(HttpResponseFactory httpResponseFactory) {
        if (httpResponseFactory != null) {
            this.responseFactory = httpResponseFactory;
            return;
        }
        throw new IllegalArgumentException("Response factory may not be null");
    }
}
