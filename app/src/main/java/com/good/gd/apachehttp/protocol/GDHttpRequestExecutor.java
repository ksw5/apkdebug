package com.good.gd.apachehttp.protocol;

import com.good.gd.apache.http.HttpClientConnection;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.impl.conn.AbstractClientConnAdapter;
import com.good.gd.apache.http.impl.conn.DefaultClientConnection;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.protocol.HttpProcessor;
import com.good.gd.apache.http.protocol.HttpRequestExecutor;
import com.good.gd.hpm.HPMReport;
import com.good.gd.ndkproxy.net.JavaNetSocketImpl;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.net.GDSocket;
import com.good.gd.net.GDSocketHelper;
import com.good.gd.utils.ReflectionUtils;
import java.io.IOException;

/* loaded from: classes.dex */
public class GDHttpRequestExecutor extends HttpRequestExecutor {
    private static String TAG = GDHttpRequestExecutor.class.toString();

    private DefaultClientConnection getDefaultClientConnection(HttpClientConnection httpClientConnection) {
        try {
            if (httpClientConnection instanceof AbstractClientConnAdapter) {
                Object invokeMethod = ReflectionUtils.invokeMethod(AbstractClientConnAdapter.class, httpClientConnection, "getWrappedConnection", null, new Object[0]);
                if (!(invokeMethod instanceof DefaultClientConnection)) {
                    return null;
                }
                return (DefaultClientConnection) invokeMethod;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private void reportConnectionError(String str, int i, String str2, GDSocket gDSocket) {
        String str3;
        String str4 = "";
        if (!NetworkStateMonitor.getInstance().isNetworkAvailable()) {
            return;
        }
        try {
            JavaNetSocketImpl impl = GDSocketHelper.getImpl(gDSocket);
            str3 = impl.currentRoute();
            try {
                str4 = impl.currentGPS();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            str3 = str4;
        }
        boolean isWiFiConnected = NetworkStateMonitor.getInstance().isWiFiConnected();
        HPMReport hPMReport = HPMReport.getInstance();
        hPMReport.connectionErrorReport(str, i, str2, isWiFiConnected, str3, str4);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0080  */
    @Override // com.good.gd.apache.http.protocol.HttpRequestExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HttpResponse execute(HttpRequest r18, HttpClientConnection r19, HttpContext r20) throws IOException, HttpException {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            boolean r0 = r2 instanceof com.good.gd.apache.http.impl.client.RequestWrapper
            if (r0 == 0) goto Lc
            r0 = r2
            com.good.gd.apache.http.impl.client.RequestWrapper r0 = (com.good.gd.apache.http.impl.client.RequestWrapper) r0
            goto L11
        Lc:
            com.good.gd.apache.http.impl.client.RequestWrapper r0 = new com.good.gd.apache.http.impl.client.RequestWrapper
            r0.<init>(r2)
        L11:
            com.good.gd.apache.http.HttpRequest r3 = r0.getOriginal()
            r4 = 0
            r5 = r19
            com.good.gd.apache.http.impl.conn.DefaultClientConnection r0 = r1.getDefaultClientConnection(r5)
            java.lang.String r6 = ""
            if (r0 == 0) goto L65
            java.net.Socket r7 = r0.getSocket()
            boolean r8 = r7 instanceof com.good.gd.net.GDSocket
            if (r8 == 0) goto L62
            com.good.gd.net.GDSocket r7 = (com.good.gd.net.GDSocket) r7     // Catch: java.lang.Exception -> L5a
            com.good.gd.ndkproxy.net.JavaNetSocketImpl r4 = com.good.gd.net.GDSocketHelper.getImpl(r7)     // Catch: java.lang.Exception -> L54
            java.lang.String r8 = r4.currentRoute()     // Catch: java.lang.Exception -> L54
            java.lang.String r6 = r4.currentGPS()     // Catch: java.lang.Exception -> L4d
            java.lang.String r4 = "webview.sessionId"
            r9 = r20
            java.lang.Object r4 = r9.getAttribute(r4)     // Catch: java.lang.Exception -> L4b
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Exception -> L4b
            r7.setSessionId(r4)     // Catch: java.lang.Exception -> L4b
            r0.sessionId = r4     // Catch: java.lang.Exception -> L4b
            r16 = r6
            r4 = r7
            r15 = r8
            goto L6a
        L4b:
            r0 = move-exception
            goto L50
        L4d:
            r0 = move-exception
            r9 = r20
        L50:
            r0 = r6
            r4 = r7
            r6 = r8
            goto L5e
        L54:
            r0 = move-exception
            r9 = r20
            r0 = r6
            r4 = r7
            goto L5e
        L5a:
            r0 = move-exception
            r9 = r20
            r0 = r6
        L5e:
            r16 = r0
            r15 = r6
            goto L6a
        L62:
            r9 = r20
            goto L67
        L65:
            r9 = r20
        L67:
            r15 = r6
            r16 = r15
        L6a:
            long r6 = java.lang.System.nanoTime()
            com.good.gd.apache.http.HttpResponse r0 = super.execute(r18, r19, r20)     // Catch: java.io.IOException -> Laf com.good.gd.apache.http.HttpException -> Lcb
            long r4 = java.lang.System.nanoTime()
            long r4 = r4 - r6
            r6 = 1000000(0xf4240, double:4.940656E-318)
            long r9 = r4 / r6
            boolean r2 = r3 instanceof com.good.gd.apache.http.client.methods.HttpRequestBase
            if (r2 == 0) goto Lae
            com.good.gd.apache.http.client.methods.HttpRequestBase r3 = (com.good.gd.apache.http.client.methods.HttpRequestBase) r3
            java.net.URI r2 = r3.getURI()
            int r7 = r3.getPortForRequest()
            com.good.gd.apache.http.StatusLine r3 = r0.getStatusLine()
            int r12 = r3.getStatusCode()
            java.lang.String r13 = r3.getReasonPhrase()
            com.good.gd.ndkproxy.net.NetworkStateMonitor r3 = com.good.gd.ndkproxy.net.NetworkStateMonitor.getInstance()
            boolean r14 = r3.isWiFiConnected()
            com.good.gd.hpm.HPMReport r5 = com.good.gd.hpm.HPMReport.getInstance()
            java.lang.String r6 = r2.getHost()
            java.lang.String r8 = r2.toString()
            r11 = 0
            r5.httpReport(r6, r7, r8, r9, r11, r12, r13, r14, r15, r16)
        Lae:
            return r0
        Laf:
            r0 = move-exception
            r2 = r0
            boolean r0 = r3 instanceof com.good.gd.apache.http.client.methods.HttpRequestBase
            if (r0 == 0) goto Lca
            com.good.gd.apache.http.client.methods.HttpRequestBase r3 = (com.good.gd.apache.http.client.methods.HttpRequestBase) r3
            java.net.URI r0 = r3.getURI()
            int r3 = r3.getPortForRequest()
            java.lang.String r5 = r0.getHost()
            java.lang.String r0 = r0.toString()
            r1.reportConnectionError(r5, r3, r0, r4)
        Lca:
            throw r2
        Lcb:
            r0 = move-exception
            r2 = r0
            boolean r0 = r3 instanceof com.good.gd.apache.http.client.methods.HttpRequestBase
            if (r0 == 0) goto Le6
            com.good.gd.apache.http.client.methods.HttpRequestBase r3 = (com.good.gd.apache.http.client.methods.HttpRequestBase) r3
            java.net.URI r0 = r3.getURI()
            int r3 = r3.getPortForRequest()
            java.lang.String r5 = r0.getHost()
            java.lang.String r0 = r0.toString()
            r1.reportConnectionError(r5, r3, r0, r4)
        Le6:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apachehttp.protocol.GDHttpRequestExecutor.execute(com.good.gd.apache.http.HttpRequest, com.good.gd.apache.http.HttpClientConnection, com.good.gd.apache.http.protocol.HttpContext):com.good.gd.apache.http.HttpResponse");
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestExecutor
    public void postProcess(HttpResponse httpResponse, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        super.postProcess(httpResponse, httpProcessor, httpContext);
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestExecutor
    public void preProcess(HttpRequest httpRequest, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        super.preProcess(httpRequest, httpProcessor, httpContext);
    }
}
