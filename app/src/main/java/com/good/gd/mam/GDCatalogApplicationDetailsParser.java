package com.good.gd.mam;

import com.good.gd.GDVersion;
import com.good.gd.ndkproxy.GDLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDCatalogApplicationDetailsParser extends GDMAMParser<GDCatalogApplicationDetails> {
    private static final String TAG_APPLICATION = "applicationDetails";
    private static final String TAG_APPLICATION_IDENTIFIER = "nativeApplicationIdentifier";
    private static final String TAG_APPLICATION_VERSION = "applicationVersion";
    private static final String TAG_APPLICATION_VERSIONS = "applicationVersions";
    private static final String TAG_DESCRIPTION = "storeDescription";
    private static final String TAG_DEVELOPER = "developer";
    private static final String TAG_ENTITLEMENT_IDENTIFIER = "entitlementIdentifier";
    private static final String TAG_ENTITLEMENT_VERSION_ID = "entitlementVersion";
    private static final String TAG_IN_MARKET = "inMarket";
    private static final String TAG_IS_INSTALLED = "isInstalled";
    private static final String TAG_RELEASE_DATE = "releaseDate";
    private static final String TAG_RELEASE_NOTES = "releaseNotes";
    private static final String TAG_SIZE = "size";
    private static final String TAG_UPGRADE_AVAILABLE = "upgradeAvailable";
    private static final String TAG_VERSIONS = "versions";

    private ArrayList<GDCatalogApplicationVersionDetails> getApplicationVersionDetails(JSONArray jSONArray) throws JSONException {
        ArrayList<GDCatalogApplicationVersionDetails> arrayList = new ArrayList<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            boolean optBoolean = jSONObject.optBoolean(TAG_IN_MARKET);
            boolean optBoolean2 = jSONObject.optBoolean(TAG_IS_INSTALLED);
            String optString = jSONObject.optString(TAG_APPLICATION_IDENTIFIER);
            String optString2 = jSONObject.optString(TAG_RELEASE_NOTES);
            String optString3 = jSONObject.optString(TAG_RELEASE_DATE);
            int optInt = jSONObject.optInt(TAG_SIZE);
            GDCatalogApplicationVersionDetails gDCatalogApplicationVersionDetails = new GDCatalogApplicationVersionDetails();
            gDCatalogApplicationVersionDetails.setMarket(optBoolean);
            gDCatalogApplicationVersionDetails.setIsInstalled(optBoolean2);
            gDCatalogApplicationVersionDetails.setNativeApplicationIdentifier(optString);
            gDCatalogApplicationVersionDetails.setReleaseNotes(optString2);
            if (optString3 != null && optString3.length() > 0) {
                try {
                    gDCatalogApplicationVersionDetails.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(optString3));
                } catch (ParseException e) {
                    GDLog.DBGPRINTF(12, "GDCatalogApplicationDetailsParser::getApplicationVersionDetails: unable to parse date: '" + optString3 + "' " + e.getMessage());
                }
            }
            gDCatalogApplicationVersionDetails.setSize(optInt);
            gDCatalogApplicationVersionDetails.setApplicationVersion(jSONObject.optString(TAG_APPLICATION_VERSION));
            arrayList.add(gDCatalogApplicationVersionDetails);
        }
        return arrayList;
    }

    private ArrayList<GDCatalogApplicationVersion> getApplicationVersions(JSONArray jSONArray) throws JSONException {
        ArrayList<GDCatalogApplicationVersion> arrayList = new ArrayList<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            GDCatalogApplicationVersion gDCatalogApplicationVersion = new GDCatalogApplicationVersion();
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            JSONArray optJSONArray = jSONObject.optJSONArray(TAG_ENTITLEMENT_VERSION_ID);
            if (optJSONArray != null) {
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    arrayList2.add(Integer.valueOf(optJSONArray.optInt(i2)));
                }
                gDCatalogApplicationVersion.setEntitlementVersion(new GDVersion(arrayList2));
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray(TAG_APPLICATION_VERSIONS);
            if (optJSONArray2 != null) {
                gDCatalogApplicationVersion.setApplicationVersions(getApplicationVersionDetails(optJSONArray2));
            }
            arrayList.add(gDCatalogApplicationVersion);
        }
        return arrayList;
    }

    @Override // com.good.gd.mam.GDMAMParser
    public List<GDCatalogApplicationDetails> parse(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject2 = jSONObject.getJSONObject(TAG_APPLICATION);
        String optString = jSONObject2.optString(TAG_ENTITLEMENT_IDENTIFIER);
        String optString2 = jSONObject2.optString(TAG_DESCRIPTION);
        boolean optBoolean = jSONObject2.optBoolean(TAG_UPGRADE_AVAILABLE);
        String optString3 = jSONObject2.optString(TAG_DEVELOPER);
        GDCatalogApplicationDetails gDCatalogApplicationDetails = new GDCatalogApplicationDetails();
        gDCatalogApplicationDetails.setEntitlementIdentifier(optString);
        gDCatalogApplicationDetails.setStoreDescription(optString2);
        gDCatalogApplicationDetails.setUpgradeAvailable(optBoolean);
        gDCatalogApplicationDetails.setDeveloper(optString3);
        JSONArray optJSONArray = jSONObject2.optJSONArray(TAG_VERSIONS);
        if (optJSONArray != null) {
            gDCatalogApplicationDetails.setVersions(getApplicationVersions(optJSONArray));
        }
        gDCatalogApplicationDetails.setScreenshots(GDMAMParser.handle(jSONObject2, GDCatalogScreenshotDetails.class));
        arrayList.add(gDCatalogApplicationDetails);
        return arrayList;
    }
}
