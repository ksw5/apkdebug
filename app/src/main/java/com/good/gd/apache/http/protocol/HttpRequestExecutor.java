package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpClientConnection;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.client.methods.HttpHead;
import com.good.gd.apache.http.params.CoreProtocolPNames;
import java.io.IOException;
import java.net.ProtocolException;

/* loaded from: classes.dex */
public class HttpRequestExecutor {
    protected boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        int statusCode;
        return (HttpHead.METHOD_NAME.equalsIgnoreCase(httpRequest.getRequestLine().getMethod()) || (statusCode = httpResponse.getStatusLine().getStatusCode()) < 200 || statusCode == 204 || statusCode == 304 || statusCode == 205) ? false : true;
    }

    protected HttpResponse doReceiveResponse(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (httpClientConnection == null) {
                throw new IllegalArgumentException("HTTP connection may not be null");
            }
            if (httpContext == null) {
                throw new IllegalArgumentException("HTTP context may not be null");
            }
            HttpResponse httpResponse = null;
            int i = 0;
            while (true) {
                if (httpResponse != null && i >= 200) {
                    return httpResponse;
                }
                httpResponse = httpClientConnection.receiveResponseHeader();
                if (canResponseHaveBody(httpRequest, httpResponse)) {
                    httpClientConnection.receiveResponseEntity(httpResponse);
                }
                i = httpResponse.getStatusLine().getStatusCode();
            }
        } else {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
    }

    protected HttpResponse doSendRequest(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        if (httpRequest != null) {
            if (httpClientConnection == null) {
                throw new IllegalArgumentException("HTTP connection may not be null");
            }
            if (httpContext != null) {
                httpContext.setAttribute(ExecutionContext.HTTP_REQ_SENT, Boolean.FALSE);
                httpClientConnection.sendRequestHeader(httpRequest);
                HttpResponse httpResponse = null;
                if (httpRequest instanceof HttpEntityEnclosingRequest) {
                    boolean z = true;
                    ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
                    HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpRequest;
                    if (httpEntityEnclosingRequest.expectContinue() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                        httpClientConnection.flush();
                        if (httpClientConnection.isResponseAvailable(httpRequest.getParams().getIntParameter(CoreProtocolPNames.WAIT_FOR_CONTINUE, 2000))) {
                            HttpResponse receiveResponseHeader = httpClientConnection.receiveResponseHeader();
                            if (canResponseHaveBody(httpRequest, receiveResponseHeader)) {
                                httpClientConnection.receiveResponseEntity(receiveResponseHeader);
                            }
                            int statusCode = receiveResponseHeader.getStatusLine().getStatusCode();
                            if (statusCode >= 200) {
                                z = false;
                                httpResponse = receiveResponseHeader;
                            } else if (statusCode != 100) {
                                throw new ProtocolException("Unexpected response: " + receiveResponseHeader.getStatusLine());
                            }
                        }
                    }
                    if (z) {
                        httpClientConnection.sendRequestEntity(httpEntityEnclosingRequest);
                    }
                }
                httpClientConnection.flush();
                httpContext.setAttribute(ExecutionContext.HTTP_REQ_SENT, Boolean.TRUE);
                return httpResponse;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    public HttpResponse execute(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        if (httpRequest != null) {
            if (httpClientConnection == null) {
                throw new IllegalArgumentException("Client connection may not be null");
            }
            if (httpContext != null) {
                try {
                    HttpResponse doSendRequest = doSendRequest(httpRequest, httpClientConnection, httpContext);
                    return doSendRequest == null ? doReceiveResponse(httpRequest, httpClientConnection, httpContext) : doSendRequest;
                } catch (HttpException e) {
                    httpClientConnection.close();
                    throw e;
                } catch (IOException e2) {
                    httpClientConnection.close();
                    throw e2;
                } catch (RuntimeException e3) {
                    httpClientConnection.close();
                    throw e3;
                }
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    public void postProcess(HttpResponse httpResponse, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse != null) {
            if (httpProcessor == null) {
                throw new IllegalArgumentException("HTTP processor may not be null");
            }
            if (httpContext != null) {
                httpProcessor.process(httpResponse, httpContext);
                return;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    public void preProcess(HttpRequest httpRequest, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (httpProcessor == null) {
                throw new IllegalArgumentException("HTTP processor may not be null");
            }
            if (httpContext != null) {
                httpProcessor.process(httpRequest, httpContext);
                return;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
