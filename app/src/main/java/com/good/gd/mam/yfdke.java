package com.good.gd.mam;

import com.good.gd.GDVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class yfdke extends GDMAMParser<GDEntitlement> {
    private GDCatalogIconDetails dbjc(JSONObject jSONObject) {
        List handle = GDMAMParser.handle(jSONObject, GDCatalogIconDetails.class);
        if (handle == null || handle.size() <= 0) {
            return null;
        }
        return (GDCatalogIconDetails) handle.get(0);
    }

    @Override // com.good.gd.mam.GDMAMParser
    public List<GDEntitlement> parse(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        JSONArray optJSONArray = jSONObject.optJSONArray("entitlements");
        if (optJSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < optJSONArray.length()) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
            String optString = jSONObject2.optString("entitlementIdentifier");
            String optString2 = jSONObject2.optString("name");
            jSONObject2.optString("developer");
            String optString3 = jSONObject2.optString("catalogDescription");
            int optInt = jSONObject2.optInt("averageRating");
            boolean optBoolean = jSONObject2.optBoolean("isGoodDynamicsApplication");
            String optString4 = jSONObject2.optString("appType");
            boolean optBoolean2 = jSONObject2.optBoolean("isApplication");
            GDEntitlement gDEntitlement = new GDEntitlement();
            gDEntitlement.setName(optString2);
            gDEntitlement.setDeveloper(optString2);
            gDEntitlement.setCatalogDescription(optString3);
            gDEntitlement.setAverageRating(optInt);
            gDEntitlement.setEntitlementIdentifier(optString);
            gDEntitlement.setGoodDynamicsApplication(optBoolean);
            gDEntitlement.setApplicationType(optString4);
            gDEntitlement.setApplication(optBoolean2);
            JSONArray optJSONArray2 = jSONObject2.optJSONArray("entitlementVersions");
            if (optJSONArray2 == null) {
                jSONArray = optJSONArray;
            } else {
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                ArrayList arrayList2 = new ArrayList();
                boolean optBoolean3 = jSONObject2.optBoolean("upgradeAvailable");
                int i2 = 0;
                while (i2 < optJSONArray2.length()) {
                    JSONObject optJSONObject = optJSONArray2.optJSONObject(i2);
                    if (optJSONObject == null) {
                        jSONArray2 = optJSONArray;
                    } else {
                        boolean optBoolean4 = optJSONObject.optBoolean("isDownloadable");
                        JSONArray optJSONArray3 = optJSONObject.optJSONArray("version");
                        if (optJSONArray3 == null) {
                            jSONArray2 = optJSONArray;
                        } else {
                            ArrayList arrayList3 = new ArrayList();
                            int i3 = 0;
                            while (true) {
                                jSONArray2 = optJSONArray;
                                if (i3 >= optJSONArray3.length()) {
                                    break;
                                }
                                arrayList3.add(Integer.valueOf(optJSONArray3.optInt(i3)));
                                i3++;
                                optJSONArray = jSONArray2;
                            }
                            GDVersion gDVersion = new GDVersion(arrayList3);
                            arrayList2.add(gDVersion);
                            hashMap.put(gDVersion.toString(), Boolean.valueOf(optBoolean4));
                            hashMap2.put(gDVersion.toString(), Boolean.valueOf(optBoolean3));
                        }
                    }
                    i2++;
                    optJSONArray = jSONArray2;
                }
                jSONArray = optJSONArray;
                gDEntitlement.setEntitlementVersions(arrayList2);
                gDEntitlement.setDownloadableVersions(hashMap);
                gDEntitlement.setUpgradableVersions(hashMap2);
            }
            gDEntitlement.setIcon(dbjc(jSONObject2.optJSONObject("icon")));
            JSONArray optJSONArray4 = jSONObject2.optJSONArray("icons");
            ArrayList arrayList4 = new ArrayList();
            if (optJSONArray4 != null) {
                for (int i4 = 0; i4 < optJSONArray4.length(); i4++) {
                    arrayList4.add(dbjc(optJSONArray4.getJSONObject(i4)));
                }
            }
            gDEntitlement.setIcons(arrayList4);
            arrayList.add(gDEntitlement);
            i++;
            optJSONArray = jSONArray;
        }
        return arrayList;
    }
}
