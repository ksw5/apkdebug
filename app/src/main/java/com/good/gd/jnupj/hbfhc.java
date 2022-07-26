package com.good.gd.jnupj;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.aqdzk;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.apache.http.client.methods.HttpPost;
import com.good.gd.apache.http.entity.ByteArrayEntity;
import com.good.gd.apache.http.params.BasicHttpParams;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.kloes.ehnkx;
import com.good.gd.net.GDHttpClient;
import com.good.gd.zwn.mjbm;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.ghhwi.hbfhc {
    private HttpResponse qkduk;
    private final String jwxax = hbfhc.class.getSimpleName();
    private final GDHttpClient dbjc = new GDHttpClient();

    public void dbjc(int i, Map<String, String> map, byte[] bArr) throws IOException {
        String str = null;
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
                if (i == 1) {
                    int ztwf = BlackberryAnalyticsCommon.rynix().ztwf();
                    String wxau = aqdzk.wxau(BlackberryAnalyticsCommon.rynix().jwxax());
                    switch (ztwf) {
                        case 1:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/1/settings/events/device";
                            break;
                        case 2:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/2/settings/dynamics/" + wxau;
                            break;
                        case 3:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/3/settings/dynamics/" + wxau;
                            break;
                        case 4:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/4/settings/dynamics/" + wxau;
                            break;
                    }
                    ehnkx.dbjc(this.jwxax, String.format("Client Config Http Get Url[%s]", str));
                } else if (i == 3) {
                    str = com.good.gd.fwwhw.hbfhc.jwxax();
                    ehnkx.dbjc(this.jwxax, String.format("GET WAN IP Http Get Url[%s]", str));
                } else if (i == 5) {
                    str = mjbm.hbfhc.wxau + "/graph/v1/services/enginemanager/engines/definedgeozone/geozones";
                    ehnkx.dbjc(this.jwxax, String.format("GET Geozone Http Get Url[%s]", str));
                } else if (i == 6) {
                    str = com.good.gd.fwwhw.hbfhc.dbjc();
                    ehnkx.dbjc(this.jwxax, String.format("GET SDE Http Get Url[%s]", str));
                }
                HttpGet httpGet = new HttpGet(str);
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpGet.setHeader(entry.getKey(), entry.getValue());
                    }
                    dbjc(httpGet.getAllHeaders());
                }
                this.qkduk = this.dbjc.execute(httpGet);
                return;
            case 2:
            case 4:
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bArr);
                if (i == 2) {
                    int lqox = BlackberryAnalyticsCommon.rynix().lqox();
                    String wxau2 = aqdzk.wxau(BlackberryAnalyticsCommon.rynix().jwxax());
                    switch (lqox) {
                        case 1:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/1/events";
                            break;
                        case 2:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/2/events/dynamics/" + wxau2;
                            break;
                        case 3:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/3/events/dynamics/" + wxau2;
                            break;
                        case 4:
                            str = mjbm.hbfhc.jwxax + "/ecs/analytics/4/events/dynamics/" + wxau2;
                            break;
                    }
                    ehnkx.dbjc(this.jwxax, String.format("Event Post Http Post Url[%s]", str));
                } else if (i == 4) {
                    str = com.good.gd.fwwhw.hbfhc.qkduk();
                    ehnkx.dbjc(this.jwxax, String.format("[SRA] Post Network Request Url: %s", str));
                }
                HttpPost httpPost = new HttpPost(str);
                if (map != null) {
                    for (Map.Entry<String, String> entry2 : map.entrySet()) {
                        httpPost.setHeader(entry2.getKey(), entry2.getValue());
                    }
                    dbjc(httpPost.getAllHeaders());
                }
                httpPost.setEntity(byteArrayEntity);
                this.qkduk = this.dbjc.execute(httpPost);
                return;
            default:
                return;
        }
    }

    public Map<String, String> jwxax() {
        Header[] allHeaders;
        HashMap hashMap = new HashMap();
        try {
            for (Header header : this.qkduk.getAllHeaders()) {
                hashMap.put(header.getName(), header.getValue());
            }
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Parsing header response failed with Exception: " + e.getLocalizedMessage());
        }
        return hashMap;
    }

    public int qkduk() {
        try {
            return this.qkduk.getStatusLine().getStatusCode();
        } catch (Exception e) {
            return -1;
        }
    }

    public void dbjc(int i) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i);
        this.dbjc.setParams(basicHttpParams);
    }

    public String dbjc() {
        HttpResponse httpResponse;
        if (204 != qkduk() && (httpResponse = this.qkduk) != null && httpResponse.getEntity() != null) {
            try {
                InputStream content = this.qkduk.getEntity().getContent();
                if (content == null) {
                    return "No Content";
                }
                HashMap hashMap = (HashMap) jwxax();
                if (hashMap.containsKey(HTTP.CONTENT_ENCODING)) {
                    String str = (String) hashMap.get(HTTP.CONTENT_ENCODING);
                    if (true != com.good.gd.whhmi.yfdke.qkduk(str) && str.equalsIgnoreCase("gzip")) {
                        return mjbm.dbjc(true, content);
                    }
                }
                return mjbm.dbjc(false, content);
            } catch (IOException e) {
                com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Failed to get response for network request: " + e.getLocalizedMessage());
            }
        }
        return "No Content";
    }

    private void dbjc(Header[] headerArr) {
        for (Header header : headerArr) {
            ehnkx.qkduk(this.jwxax, String.format("[%s] = %s", header.getName(), header.getValue()));
        }
    }
}
