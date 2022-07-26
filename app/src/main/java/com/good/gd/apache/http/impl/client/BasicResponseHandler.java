package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.client.HttpResponseException;
import com.good.gd.apache.http.client.ResponseHandler;
import com.good.gd.apache.http.util.EntityUtils;
import java.io.IOException;

/* loaded from: classes.dex */
public class BasicResponseHandler implements ResponseHandler<String> {
    @Override // com.good.gd.apache.http.client.ResponseHandler
    public String handleResponse(HttpResponse httpResponse) throws HttpResponseException, IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        if (statusLine.getStatusCode() < 300) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
            return null;
        }
        throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
    }
}
