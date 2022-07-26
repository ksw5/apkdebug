package com.good.gd.apache.http.client.methods;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpResponse;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class HttpOptions extends HttpRequestBase {
    public static final String METHOD_NAME = "OPTIONS";

    public HttpOptions() {
    }

    public Set<String> getAllowedMethods(HttpResponse httpResponse) {
        if (httpResponse != null) {
            HeaderIterator headerIterator = httpResponse.headerIterator("Allow");
            HashSet hashSet = new HashSet();
            while (headerIterator.hasNext()) {
                for (HeaderElement headerElement : headerIterator.nextHeader().getElements()) {
                    hashSet.add(headerElement.getName());
                }
            }
            return hashSet;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase, com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpOptions(URI uri) {
        setURI(uri);
    }

    public HttpOptions(String str) {
        setURI(URI.create(str));
    }
}
