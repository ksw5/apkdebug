package com.good.gd.apachehttp.impl.client.cache;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apachehttp.client.cache.HttpCacheEntry;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CacheKeyGenerator {
    private String canonicalizePath(String str) {
        try {
            return new URI(URLDecoder.decode(str, HTTP.UTF_8)).getPath();
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            return str;
        }
    }

    private int canonicalizePort(int i, String str) {
        if (i != -1 || !HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(str)) {
            if (i == -1 && "https".equalsIgnoreCase(str)) {
                return 443;
            }
            return i;
        }
        return 80;
    }

    private boolean isRelativeRequest(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUri();
        return "*".equals(uri) || uri.startsWith("/");
    }

    public String canonicalizeUri(String str) {
        try {
            URL url = new URL(str);
            String lowerCase = url.getProtocol().toLowerCase();
            String lowerCase2 = url.getHost().toLowerCase();
            int canonicalizePort = canonicalizePort(url.getPort(), lowerCase);
            String canonicalizePath = canonicalizePath(url.getPath());
            if ("".equals(canonicalizePath)) {
                canonicalizePath = "/";
            }
            String query = url.getQuery();
            if (query != null) {
                canonicalizePath = canonicalizePath + "?" + query;
            }
            return new URL(lowerCase, lowerCase2, canonicalizePort, canonicalizePath).toString();
        } catch (MalformedURLException e) {
            return str;
        }
    }

    protected String getFullHeaderValue(Header[] headerArr) {
        if (headerArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        int length = headerArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            Header header = headerArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(header.getValue().trim());
            i++;
            z = false;
        }
        return sb.toString();
    }

    public String getURI(HttpHost httpHost, HttpRequest httpRequest) {
        return isRelativeRequest(httpRequest) ? canonicalizeUri(String.format("%s%s", httpHost.toString(), httpRequest.getRequestLine().getUri())) : canonicalizeUri(httpRequest.getRequestLine().getUri());
    }

    public String getVariantKey(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        ArrayList arrayList = new ArrayList();
        for (Header header : httpCacheEntry.getHeaders("Vary")) {
            for (HeaderElement headerElement : header.getElements()) {
                arrayList.add(headerElement.getName());
            }
        }
        Collections.sort(arrayList);
        try {
            StringBuilder sb = new StringBuilder("{");
            Iterator it = arrayList.iterator();
            boolean z = true;
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!z) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(str, HTTP.UTF_8));
                sb.append("=");
                sb.append(URLEncoder.encode(getFullHeaderValue(httpRequest.getHeaders(str)), HTTP.UTF_8));
                z = false;
            }
            sb.append("}");
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("couldn't encode to UTF-8", e);
        }
    }

    public String getVariantURI(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        return !httpCacheEntry.hasVariants() ? getURI(httpHost, httpRequest) : getVariantKey(httpRequest, httpCacheEntry) + getURI(httpHost, httpRequest);
    }
}
