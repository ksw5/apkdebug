package com.good.gd.mam;

import androidx.core.os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDCatalogScreenshotParser extends GDMAMParser<GDCatalogScreenshotDetails> {
    private static final String TAG_DIM = "dim";
    private static final String TAG_HASH = "hash";
    private static final String TAG_RES_ID = "resource";
    private static final String TAG_SCREENSHOTS = "screenshots";
    private static final String TAG_URL = "url";

    @Override // com.good.gd.mam.GDMAMParser
    List<GDCatalogScreenshotDetails> parse(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray(TAG_SCREENSHOTS);
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                String optString = jSONObject2.optString(TAG_HASH);
                String optString2 = jSONObject2.optString("url");
                String optString3 = jSONObject2.optString(TAG_RES_ID);
                String optString4 = jSONObject2.optString(TAG_DIM);
                GDCatalogScreenshotDetails gDCatalogScreenshotDetails = new GDCatalogScreenshotDetails();
                gDCatalogScreenshotDetails.setHash(optString);
                gDCatalogScreenshotDetails.setResource(optString3);
                gDCatalogScreenshotDetails.setUrl(optString2);
                if (optString4.length() > 0 && !optString4.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                    String[] split = optString4.split("x");
                    gDCatalogScreenshotDetails.setWidth(Integer.valueOf(split[0]).intValue());
                    gDCatalogScreenshotDetails.setHeight(Integer.valueOf(split[1]).intValue());
                }
                arrayList.add(gDCatalogScreenshotDetails);
            }
        }
        return arrayList;
    }
}
