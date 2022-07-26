package com.good.gd.apache.http.client.utils;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.ndkproxy.file.RandomAccessFileImpl;
import java.net.URI;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class URIUtils {
    private URIUtils() {
    }

    public static URI createURI(String str, String str2, int i, String str3, String str4, String str5) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            if (str != null) {
                sb.append(str);
                sb.append("://");
            }
            sb.append(str2);
            if (i > 0) {
                sb.append(':');
                sb.append(i);
            }
        }
        if (str3 == null || !str3.startsWith("/")) {
            sb.append(RandomAccessFileImpl.separatorChar);
        }
        if (str3 != null) {
            sb.append(str3);
        }
        if (str4 != null) {
            sb.append('?');
            sb.append(str4);
        }
        if (str5 != null) {
            sb.append('#');
            sb.append(str5);
        }
        return new URI(sb.toString());
    }

    public static URI resolve(URI uri, String str) {
        return resolve(uri, URI.create(str));
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost, boolean z) throws URISyntaxException {
        if (uri != null) {
            if (httpHost != null) {
                return createURI(httpHost.getSchemeName(), httpHost.getHostName(), httpHost.getPort(), uri.getRawPath(), uri.getRawQuery(), z ? null : uri.getRawFragment());
            }
            return createURI(null, null, -1, uri.getRawPath(), uri.getRawQuery(), z ? null : uri.getRawFragment());
        }
        throw new IllegalArgumentException("URI may nor be null");
    }

    public static URI resolve(URI uri, URI uri2) {
        if (uri != null) {
            if (uri2 != null) {
                boolean z = uri2.toString().length() == 0;
                if (z) {
                    uri2 = URI.create("#");
                }
                URI resolve = uri.resolve(uri2);
                if (!z) {
                    return resolve;
                }
                String uri3 = resolve.toString();
                return URI.create(uri3.substring(0, uri3.indexOf(35)));
            }
            throw new IllegalArgumentException("Reference URI may nor be null");
        }
        throw new IllegalArgumentException("Base URI may nor be null");
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost) throws URISyntaxException {
        return rewriteURI(uri, httpHost, false);
    }
}
