package com.good.gd.apache.http.client.entity;

import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.client.utils.URLEncodedUtils;
import com.good.gd.apache.http.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import java.util.List;

/* loaded from: classes.dex */
public class UrlEncodedFormEntity extends StringEntity {
    public UrlEncodedFormEntity(List<? extends NameValuePair> list, String str) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(list, str), str);
        setContentType(URLEncodedUtils.CONTENT_TYPE);
    }

    public UrlEncodedFormEntity(List<? extends NameValuePair> list) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(list, "ISO-8859-1"), "ISO-8859-1");
        setContentType(URLEncodedUtils.CONTENT_TYPE);
    }
}
