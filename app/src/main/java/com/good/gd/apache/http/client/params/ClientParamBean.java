package com.good.gd.apache.http.client.params;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.ClientConnectionManagerFactory;
import com.good.gd.apache.http.params.HttpAbstractParamBean;
import com.good.gd.apache.http.params.HttpParams;
import java.util.Collection;

/* loaded from: classes.dex */
public class ClientParamBean extends HttpAbstractParamBean {
    public ClientParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setAllowCircularRedirects(boolean z) {
        this.params.setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, z);
    }

    public void setConnectionManagerFactory(ClientConnectionManagerFactory clientConnectionManagerFactory) {
        this.params.setParameter(ClientPNames.CONNECTION_MANAGER_FACTORY, clientConnectionManagerFactory);
    }

    public void setConnectionManagerFactoryClassName(String str) {
        this.params.setParameter(ClientPNames.CONNECTION_MANAGER_FACTORY_CLASS_NAME, str);
    }

    public void setCookiePolicy(String str) {
        this.params.setParameter(ClientPNames.COOKIE_POLICY, str);
    }

    public void setDefaultHeaders(Collection<Header> collection) {
        this.params.setParameter(ClientPNames.DEFAULT_HEADERS, collection);
    }

    public void setDefaultHost(HttpHost httpHost) {
        this.params.setParameter(ClientPNames.DEFAULT_HOST, httpHost);
    }

    public void setHandleAuthentication(boolean z) {
        this.params.setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, z);
    }

    public void setHandleRedirects(boolean z) {
        this.params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, z);
    }

    public void setMaxRedirects(int i) {
        this.params.setIntParameter(ClientPNames.MAX_REDIRECTS, i);
    }

    public void setRejectRelativeRedirect(boolean z) {
        this.params.setBooleanParameter(ClientPNames.REJECT_RELATIVE_REDIRECT, z);
    }

    public void setVirtualHost(HttpHost httpHost) {
        this.params.setParameter(ClientPNames.VIRTUAL_HOST, httpHost);
    }
}
