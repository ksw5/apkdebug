package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SM;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class RFC2965Spec extends RFC2109Spec {
    public RFC2965Spec() {
        this(null, false);
    }

    private static CookieOrigin adjustEffectiveHost(CookieOrigin cookieOrigin) {
        String host = cookieOrigin.getHost();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= host.length()) {
                z = true;
                break;
            }
            char charAt = host.charAt(i);
            if (charAt == '.' || charAt == ':') {
                break;
            }
            i++;
        }
        return z ? new CookieOrigin(host + ".local", cookieOrigin.getPort(), cookieOrigin.getPath(), cookieOrigin.isSecure()) : cookieOrigin;
    }

    private BasicClientCookie createCookie(String str, String str2, CookieOrigin cookieOrigin) {
        BasicClientCookie basicClientCookie = new BasicClientCookie(str, str2);
        basicClientCookie.setPath(CookieSpecBase.getDefaultPath(cookieOrigin));
        basicClientCookie.setDomain(CookieSpecBase.getDefaultDomain(cookieOrigin));
        return basicClientCookie;
    }

    private BasicClientCookie createCookie2(String str, String str2, CookieOrigin cookieOrigin) {
        BasicClientCookie2 basicClientCookie2 = new BasicClientCookie2(str, str2);
        basicClientCookie2.setPath(CookieSpecBase.getDefaultPath(cookieOrigin));
        basicClientCookie2.setDomain(CookieSpecBase.getDefaultDomain(cookieOrigin));
        basicClientCookie2.setPorts(new int[]{cookieOrigin.getPort()});
        return basicClientCookie2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.cookie.RFC2109Spec
    public void formatCookieAsVer(CharArrayBuffer charArrayBuffer, Cookie cookie, int i) {
        String attribute;
        int[] ports;
        super.formatCookieAsVer(charArrayBuffer, cookie, i);
        if (!(cookie instanceof ClientCookie) || (attribute = ((ClientCookie) cookie).getAttribute(ClientCookie.PORT_ATTR)) == null) {
            return;
        }
        charArrayBuffer.append("; $Port");
        charArrayBuffer.append("=\"");
        if (attribute.trim().length() > 0 && (ports = cookie.getPorts()) != null) {
            int length = ports.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 > 0) {
                    charArrayBuffer.append(",");
                }
                charArrayBuffer.append(Integer.toString(ports[i2]));
            }
        }
        charArrayBuffer.append("\"");
    }

    @Override // com.good.gd.apache.http.impl.cookie.RFC2109Spec, com.good.gd.apache.http.cookie.CookieSpec
    public int getVersion() {
        return 1;
    }

    @Override // com.good.gd.apache.http.impl.cookie.RFC2109Spec, com.good.gd.apache.http.cookie.CookieSpec
    public Header getVersionHeader() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(40);
        charArrayBuffer.append(SM.COOKIE2);
        charArrayBuffer.append(": ");
        charArrayBuffer.append("$Version=");
        charArrayBuffer.append(Integer.toString(getVersion()));
        return new BufferedHeader(charArrayBuffer);
    }

    @Override // com.good.gd.apache.http.impl.cookie.CookieSpecBase, com.good.gd.apache.http.cookie.CookieSpec
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                return super.match(cookie, adjustEffectiveHost(cookieOrigin));
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.impl.cookie.RFC2109Spec, com.good.gd.apache.http.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        BasicClientCookie createCookie;
        if (header != null) {
            if (cookieOrigin != null) {
                CookieOrigin adjustEffectiveHost = adjustEffectiveHost(cookieOrigin);
                HeaderElement[] elements = header.getElements();
                ArrayList arrayList = new ArrayList(elements.length);
                for (HeaderElement headerElement : elements) {
                    String name = headerElement.getName();
                    String value = headerElement.getValue();
                    if (name != null && name.length() != 0) {
                        if (header.getName().equals(SM.SET_COOKIE2)) {
                            createCookie = createCookie2(name, value, adjustEffectiveHost);
                        } else {
                            createCookie = createCookie(name, value, adjustEffectiveHost);
                        }
                        NameValuePair[] parameters = headerElement.getParameters();
                        HashMap hashMap = new HashMap(parameters.length);
                        for (int length = parameters.length - 1; length >= 0; length--) {
                            NameValuePair nameValuePair = parameters[length];
                            hashMap.put(nameValuePair.getName().toLowerCase(Locale.ENGLISH), nameValuePair);
                        }
                        for (Map.Entry entry : hashMap.entrySet()) {
                            NameValuePair nameValuePair2 = (NameValuePair) entry.getValue();
                            String lowerCase = nameValuePair2.getName().toLowerCase(Locale.ENGLISH);
                            createCookie.setAttribute(lowerCase, nameValuePair2.getValue());
                            CookieAttributeHandler findAttribHandler = findAttribHandler(lowerCase);
                            if (findAttribHandler != null) {
                                findAttribHandler.parse(createCookie, nameValuePair2.getValue());
                            }
                        }
                        arrayList.add(createCookie);
                    } else {
                        throw new MalformedCookieException("Cookie name may not be empty");
                    }
                }
                return arrayList;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Header may not be null");
    }

    @Override // com.good.gd.apache.http.impl.cookie.RFC2109Spec, com.good.gd.apache.http.impl.cookie.CookieSpecBase, com.good.gd.apache.http.cookie.CookieSpec
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if (cookieOrigin != null) {
                super.validate(cookie, adjustEffectiveHost(cookieOrigin));
                return;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    public RFC2965Spec(String[] strArr, boolean z) {
        super(strArr, z);
        registerAttribHandler(ClientCookie.DOMAIN_ATTR, new RFC2965DomainAttributeHandler());
        registerAttribHandler(ClientCookie.PORT_ATTR, new RFC2965PortAttributeHandler());
        registerAttribHandler(ClientCookie.COMMENTURL_ATTR, new RFC2965CommentUrlAttributeHandler());
        registerAttribHandler(ClientCookie.DISCARD_ATTR, new RFC2965DiscardAttributeHandler());
        registerAttribHandler("version", new RFC2965VersionAttributeHandler());
    }
}
