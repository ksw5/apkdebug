package com.good.gd.webauth;

import android.net.Uri;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.apache.http.client.params.ClientPNames;
import com.good.gd.apache.http.impl.client.DefaultHttpClient;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.WebAuth;
import com.good.gd.ndkproxy.ui.data.WebAuthUI;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class WebAuthenticationSession {
    private static final int CONNECTION_TIMEOUT_MS = (int) TimeUnit.SECONDS.toMillis(15);
    private static final int READ_TIMEOUT_MS = (int) TimeUnit.SECONDS.toMillis(10);
    private final String clientId;
    private AuthProviderConfig config;
    private final Uri discoveryUri;
    private final WebAuth.Listener listener;
    private final Uri redirectUri;
    private final String scope;

    /* loaded from: classes.dex */
    class hbfhc implements WebAuthUI.UrlCallback {
        final /* synthetic */ com.good.gd.webauth.hbfhc dbjc;

        hbfhc(com.good.gd.webauth.hbfhc hbfhcVar) {
            this.dbjc = hbfhcVar;
        }

        @Override // com.good.gd.ndkproxy.ui.data.WebAuthUI.UrlCallback
        public void onCallbackUrl(String str) {
            if (str.startsWith(WebAuthenticationSession.this.redirectUri.toString())) {
                AuthCodeResponse parseURL = AuthCodeResponse.parseURL(Uri.parse(str));
                if (parseURL == null) {
                    WebAuthenticationSession.this.listener.onError(WebAuth.AuthError.INVALID_CODE);
                } else if (parseURL.getState().equals(this.dbjc.tlske())) {
                    WebAuthenticationSession.this.listener.onResult(parseURL.getAuthCode(), this.dbjc.wxau());
                } else {
                    WebAuthenticationSession.this.listener.onError(WebAuth.AuthError.INVALID_STATE);
                }
            }
            WeAuthUICallbackHolder.setUrlCallback(null);
        }

        @Override // com.good.gd.ndkproxy.ui.data.WebAuthUI.UrlCallback
        public void onError(int i) {
            WebAuthenticationSession.this.listener.onError(WebAuth.AuthError.NETWORKING_ERROR);
            WeAuthUICallbackHolder.setUrlCallback(null);
        }
    }

    public WebAuthenticationSession(Uri uri, Uri uri2, String str, String str2, WebAuth.Listener listener) {
        this.discoveryUri = uri;
        this.redirectUri = uri2;
        this.clientId = str;
        this.scope = str2;
        this.listener = listener;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x008c: MOVE  (r8 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:48:0x008c */
    private AuthProviderConfig getConfiguration() {
        InputStream inputStream;
        Throwable th;
        InputStream inputStream2;
        InputStream inputStream3 = null;
        try {
        } catch (Throwable th2) {
            th = th2;
            inputStream3 = inputStream;
        }
        try {
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(this.discoveryUri.toString());
                HttpParams params = defaultHttpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT_MS);
                HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT_MS);
                params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
                HttpResponse execute = defaultHttpClient.execute(httpGet);
                execute.getEntity();
                if (execute.getStatusLine().getStatusCode() != 200) {
                    GDLog.DBGPRINTF(12, "WebAuthenticationSession: Invalid response\n");
                    return null;
                }
                inputStream2 = execute.getEntity().getContent();
                try {
                    AuthProviderConfig dbjc = AuthProviderConfig.dbjc(new JSONObject(readInputStream(inputStream2)));
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e) {
                            GDLog.DBGPRINTF(12, "WebAuthenticationSession: Error while closing HttpURLConnection\n");
                        }
                    }
                    return dbjc;
                } catch (IOException e2) {
                    GDLog.DBGPRINTF(12, "WebAuthenticationSession: Network error when retrieving auth provider configuration\n");
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    return null;
                } catch (JSONException e3) {
                    GDLog.DBGPRINTF(12, "WebAuthenticationSession: Error parsing auth provider configuration json\n");
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    return null;
                }
            } catch (IOException e4) {
                GDLog.DBGPRINTF(12, "WebAuthenticationSession: Error while closing HttpURLConnection\n");
                return null;
            }
        } catch (IOException e5) {
            inputStream2 = null;
        } catch (JSONException e6) {
            inputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            if (inputStream3 != null) {
                try {
                    inputStream3.close();
                } catch (IOException e7) {
                    GDLog.DBGPRINTF(12, "WebAuthenticationSession: Error while closing HttpURLConnection\n");
                }
            }
            throw th;
        }
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        char[] cArr = new char[1024];
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        while (true) {
            int read = inputStreamReader.read(cArr, 0, 1024);
            if (read > 0) {
                sb.append(cArr, 0, read);
            } else {
                return sb.toString();
            }
        }
    }

    public void start() {
        GDLog.DBGPRINTF(14, "WebAuthenticationSession::start\n");
        if (this.config == null) {
            this.config = getConfiguration();
        }
        if (this.config == null) {
            GDLog.DBGPRINTF(12, "WebAuthenticationSession::invalid config\n");
            this.listener.onError(WebAuth.AuthError.FAILED_CONFIG_REQUEST);
        }
        GDLog.DBGPRINTF(14, "WebAuthenticationSession::got config\n");
        com.good.gd.webauth.hbfhc dbjc = com.good.gd.webauth.hbfhc.dbjc(this.redirectUri, this.clientId, this.scope);
        Uri dbjc2 = new yfdke(this.config, dbjc).dbjc();
        WeAuthUICallbackHolder.setUrlCallback(new hbfhc(dbjc));
        GDLog.DBGPRINTF(14, "WebAuthenticationSession::requesting WebAuthUI\n");
        CoreUI.requestActivationWebAuthUI(dbjc2.toString(), this.redirectUri.toString());
    }
}
