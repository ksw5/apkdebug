package com.good.gd.mam;

import com.good.gd.mam.GDCatalogApplicationInstallerDetails;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDCatalogApplicationInstallerParser extends GDMAMParser<GDCatalogApplicationInstallerDetails> {
    private static final String DOWNLOAD_URL_TYPE_NATIVEAPP = "nativeApplication";
    private static final String DOWNLOAD_URL_TYPE_WEBAPP = "webApplication";
    private static final String TAG_NATIVE_URL = "nativeApplicationDownloadUrl";
    private static final String TAG_TYPE = "type";
    private static final String TAG_WEB_URL = "webApplicationUrl";

    @Override // com.good.gd.mam.GDMAMParser
    List<GDCatalogApplicationInstallerDetails> parse(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        GDCatalogApplicationInstallerDetails gDCatalogApplicationInstallerDetails = new GDCatalogApplicationInstallerDetails();
        String string = jSONObject.getString(TAG_TYPE);
        if (string.equalsIgnoreCase(DOWNLOAD_URL_TYPE_WEBAPP)) {
            gDCatalogApplicationInstallerDetails.setType(GDCatalogApplicationInstallerDetails.GDApplicationInstallerType.WebApp);
            gDCatalogApplicationInstallerDetails.setWebApplicationUrl(jSONObject.optString(TAG_WEB_URL));
        } else if (string.equalsIgnoreCase(DOWNLOAD_URL_TYPE_NATIVEAPP)) {
            gDCatalogApplicationInstallerDetails.setType(GDCatalogApplicationInstallerDetails.GDApplicationInstallerType.NativeApp);
            gDCatalogApplicationInstallerDetails.setNativeApplicationDownloadUrl(jSONObject.optString(TAG_NATIVE_URL));
        }
        arrayList.add(gDCatalogApplicationInstallerDetails);
        return arrayList;
    }
}
