package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.SetCookie2;
import java.util.Date;

/* loaded from: classes.dex */
public class BasicClientCookie2 extends BasicClientCookie implements SetCookie2 {
    private String commentURL;
    private boolean discard;
    private int[] ports;

    public BasicClientCookie2(String str, String str2) {
        super(str, str2);
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicClientCookie
    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie2 basicClientCookie2 = (BasicClientCookie2) super.clone();
        basicClientCookie2.ports = (int[]) this.ports.clone();
        return basicClientCookie2;
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicClientCookie, com.good.gd.apache.http.cookie.Cookie
    public String getCommentURL() {
        return this.commentURL;
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicClientCookie, com.good.gd.apache.http.cookie.Cookie
    public int[] getPorts() {
        return this.ports;
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicClientCookie, com.good.gd.apache.http.cookie.Cookie
    public boolean isExpired(Date date) {
        return this.discard || super.isExpired(date);
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicClientCookie, com.good.gd.apache.http.cookie.Cookie
    public boolean isPersistent() {
        return !this.discard && super.isPersistent();
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie2
    public void setCommentURL(String str) {
        this.commentURL = str;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie2
    public void setDiscard(boolean z) {
        this.discard = z;
    }

    @Override // com.good.gd.apache.http.cookie.SetCookie2
    public void setPorts(int[] iArr) {
        this.ports = iArr;
    }
}
