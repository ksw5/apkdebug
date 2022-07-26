package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.SetCookie;
import com.good.gt.util.PII;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public class BasicClientCookie implements SetCookie, ClientCookie, Cloneable {
    private Map<String, String> attribs;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private int cookieVersion;
    private final String name;
    private String originalCookieValue;
    private boolean secure;
    private String value;

    public BasicClientCookie(String str, String str2) {
        if (str != null) {
            this.name = str;
            this.attribs = new HashMap();
            this.value = str2;
            return;
        }
        throw new IllegalArgumentException("Name may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie basicClientCookie = (BasicClientCookie) super.clone();
        basicClientCookie.attribs = new HashMap(this.attribs);
        return basicClientCookie;
    }

    @Override // com.good.gd.apache.http.cookie.ClientCookie
    public boolean containsAttribute(String str) {
        return this.attribs.get(str) != null;
    }

    @Override // com.good.gd.apache.http.cookie.ClientCookie
    public String getAttribute(String str) {
        return this.attribs.get(str);
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getComment() {
        return this.cookieComment;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getCommentURL() {
        return null;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getDomain() {
        return this.cookieDomain;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getName() {
        return this.name;
    }

    public String getOriginalCookieValue() {
        return this.originalCookieValue;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getPath() {
        return this.cookiePath;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public int[] getPorts() {
        return null;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public String getValue() {
        return this.value;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public int getVersion() {
        return this.cookieVersion;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public boolean isExpired(Date date) {
        if (date != null) {
            Date date2 = this.cookieExpiryDate;
            return date2 != null && date2.getTime() <= date.getTime();
        }
        throw new IllegalArgumentException("Date may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public boolean isPersistent() {
        return this.cookieExpiryDate != null;
    }

    @Override // com.good.gd.apache.http.cookie.Cookie
    public boolean isSecure() {
        return this.secure;
    }

    public void setAttribute(String str, String str2) {
        this.attribs.put(str, str2);
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setComment(String str) {
        this.cookieComment = str;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setDomain(String str) {
        if (str != null) {
            this.cookieDomain = str.toLowerCase(Locale.ENGLISH);
        } else {
            this.cookieDomain = null;
        }
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setExpiryDate(Date date) {
        this.cookieExpiryDate = date;
    }

    public void setOriginalCookieValue(String str) {
        this.originalCookieValue = str;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setPath(String str) {
        this.cookiePath = str;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setSecure(boolean z) {
        this.secure = z;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setValue(String str) {
        this.value = str;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie
    public void setVersion(int i) {
        this.cookieVersion = i;
    }

    public String toString() {
        return "[version: " + Integer.toString(this.cookieVersion) + "][name: " + this.name + "][value: " + PII.piiDescription(this.value) + "][domain: " + this.cookieDomain + "][path: " + this.cookiePath + "][expiry: " + this.cookieExpiryDate + "]";
    }
}
