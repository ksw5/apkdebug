package com.good.gd.mam;

import androidx.core.os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class hbfhc extends GDMAMParser<GDCatalogIconDetails> {
    @Override // com.good.gd.mam.GDMAMParser
    public List<GDCatalogIconDetails> parse(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            ArrayList arrayList = new ArrayList();
            String optString = jSONObject.optString("hash");
            String optString2 = jSONObject.optString("url");
            String optString3 = jSONObject.optString("resource");
            String optString4 = jSONObject.optString("dim");
            GDCatalogIconDetails gDCatalogIconDetails = new GDCatalogIconDetails();
            gDCatalogIconDetails.setHash(optString);
            gDCatalogIconDetails.setUrl(optString2);
            gDCatalogIconDetails.setResource(optString3);
            if (optString4.length() > 0 && !optString4.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                String[] split = optString4.split("x");
                gDCatalogIconDetails.setWidth(Integer.valueOf(split[0]).intValue());
                gDCatalogIconDetails.setHeight(Integer.valueOf(split[1]).intValue());
            }
            arrayList.add(gDCatalogIconDetails);
            return arrayList;
        }
        return null;
    }
}
