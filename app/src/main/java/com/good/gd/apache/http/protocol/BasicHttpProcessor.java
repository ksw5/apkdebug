package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class BasicHttpProcessor implements HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, Cloneable {
    protected List requestInterceptors = null;
    protected List responseInterceptors = null;

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        addRequestInterceptor(httpRequestInterceptor);
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        if (this.requestInterceptors == null) {
            this.requestInterceptors = new ArrayList();
        }
        this.requestInterceptors.add(httpRequestInterceptor);
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i) {
        if (i >= 0) {
            if (httpResponseInterceptor == null) {
                return;
            }
            if (this.responseInterceptors == null) {
                if (i <= 0) {
                    this.responseInterceptors = new ArrayList();
                } else {
                    throw new IndexOutOfBoundsException(String.valueOf(i));
                }
            }
            this.responseInterceptors.add(i, httpResponseInterceptor);
            return;
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    public void clearInterceptors() {
        clearRequestInterceptors();
        clearResponseInterceptors();
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public void clearRequestInterceptors() {
        this.requestInterceptors = null;
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public void clearResponseInterceptors() {
        this.responseInterceptors = null;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpProcessor basicHttpProcessor = (BasicHttpProcessor) super.clone();
        copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    public BasicHttpProcessor copy() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    protected void copyInterceptors(BasicHttpProcessor basicHttpProcessor) {
        if (this.requestInterceptors != null) {
            basicHttpProcessor.requestInterceptors = new ArrayList(this.requestInterceptors);
        }
        if (this.responseInterceptors != null) {
            basicHttpProcessor.responseInterceptors = new ArrayList(this.responseInterceptors);
        }
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public HttpRequestInterceptor getRequestInterceptor(int i) {
        List list = this.requestInterceptors;
        if (list == null || i < 0 || i >= list.size()) {
            return null;
        }
        return (HttpRequestInterceptor) this.requestInterceptors.get(i);
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public int getRequestInterceptorCount() {
        List list = this.requestInterceptors;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public HttpResponseInterceptor getResponseInterceptor(int i) {
        List list = this.responseInterceptors;
        if (list == null || i < 0 || i >= list.size()) {
            return null;
        }
        return (HttpResponseInterceptor) this.responseInterceptors.get(i);
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public int getResponseInterceptorCount() {
        List list = this.responseInterceptors;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws IOException, HttpException {
        if (this.requestInterceptors != null) {
            for (int i = 0; i < this.requestInterceptors.size(); i++) {
                ((HttpRequestInterceptor) this.requestInterceptors.get(i)).process(httpRequest, httpContext);
            }
        }
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public void removeRequestInterceptorByClass(Class cls) {
        List list = this.requestInterceptors;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                it.remove();
            }
        }
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public void removeResponseInterceptorByClass(Class cls) {
        List list = this.responseInterceptors;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getClass().equals(cls)) {
                it.remove();
            }
        }
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList, com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public void setInterceptors(List list) {
        if (list != null) {
            List list2 = this.requestInterceptors;
            if (list2 != null) {
                list2.clear();
            }
            List list3 = this.responseInterceptors;
            if (list3 != null) {
                list3.clear();
            }
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                if (obj instanceof HttpRequestInterceptor) {
                    addInterceptor((HttpRequestInterceptor) obj);
                }
                if (obj instanceof HttpResponseInterceptor) {
                    addInterceptor((HttpResponseInterceptor) obj);
                }
            }
            return;
        }
        throw new IllegalArgumentException("List must not be null.");
    }

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i) {
        addRequestInterceptor(httpRequestInterceptor, i);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        addResponseInterceptor(httpResponseInterceptor);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i) {
        addResponseInterceptor(httpResponseInterceptor, i);
    }

    @Override // com.good.gd.apache.http.protocol.HttpRequestInterceptorList
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i) {
        if (i >= 0) {
            if (httpRequestInterceptor == null) {
                return;
            }
            if (this.requestInterceptors == null) {
                if (i <= 0) {
                    this.requestInterceptors = new ArrayList();
                } else {
                    throw new IndexOutOfBoundsException(String.valueOf(i));
                }
            }
            this.requestInterceptors.add(i, httpRequestInterceptor);
            return;
        }
        throw new IndexOutOfBoundsException(String.valueOf(i));
    }

    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws IOException, HttpException {
        if (this.responseInterceptors != null) {
            for (int i = 0; i < this.responseInterceptors.size(); i++) {
                ((HttpResponseInterceptor) this.responseInterceptors.get(i)).process(httpResponse, httpContext);
            }
        }
    }

    @Override // com.good.gd.apache.http.protocol.HttpResponseInterceptorList
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        if (this.responseInterceptors == null) {
            this.responseInterceptors = new ArrayList();
        }
        this.responseInterceptors.add(httpResponseInterceptor);
    }
}
