package com.good.gd.mam;

import com.good.gd.GDVersion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class GDCatalogReviewsParser extends GDMAMParser<GDCatalogReviews> {
    private static final String TAG_CONTENT = "content";
    private static final String TAG_ENTITLEMENT_VERSION_ID = "reviewedEntitlementVersion";
    private static final String TAG_RATING = "rating";
    private static final String TAG_REVIEWS = "reviews";
    private static final String TAG_REVIEW_ID = "reviewIdentifier";
    private static final String TAG_SUBMITTER = "submitter";
    private static final String TAG_TIMESTAMP = "thresholdTimestamp";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TOTAL_COUNT = "laterReviewCount";

    @Override // com.good.gd.mam.GDMAMParser
    public List<GDCatalogReviews> parse(JSONObject jSONObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        long optLong = jSONObject.optLong(TAG_TIMESTAMP);
        long optLong2 = jSONObject.optLong(TAG_TOTAL_COUNT);
        GDCatalogReviews gDCatalogReviews = new GDCatalogReviews();
        gDCatalogReviews.setLaterReviewCount(optLong2);
        gDCatalogReviews.setThresholdTimestamp(new Date(optLong));
        JSONArray optJSONArray = jSONObject.optJSONArray(TAG_REVIEWS);
        if (optJSONArray != null) {
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                String optString = jSONObject2.optString(TAG_REVIEW_ID);
                String optString2 = jSONObject2.optString(TAG_SUBMITTER);
                int optInt = jSONObject2.optInt(TAG_RATING);
                String optString3 = jSONObject2.optString(TAG_TITLE);
                String optString4 = jSONObject2.optString(TAG_CONTENT);
                GDCatalogReview gDCatalogReview = new GDCatalogReview();
                gDCatalogReview.setReviewIdentifier(optString);
                gDCatalogReview.setSubmitter(optString2);
                gDCatalogReview.setRating(optInt);
                gDCatalogReview.setTitle(optString3);
                gDCatalogReview.setContent(optString4);
                JSONArray optJSONArray2 = jSONObject2.optJSONArray(TAG_ENTITLEMENT_VERSION_ID);
                if (optJSONArray2 != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        arrayList3.add(Integer.valueOf(optJSONArray2.optInt(i2)));
                    }
                    gDCatalogReview.setReviewedEntitlementVersion(new GDVersion(arrayList3));
                }
                arrayList2.add(gDCatalogReview);
            }
            gDCatalogReviews.setReviews(arrayList2);
        }
        arrayList.add(gDCatalogReviews);
        return arrayList;
    }
}
