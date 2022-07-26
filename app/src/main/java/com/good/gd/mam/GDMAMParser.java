package com.good.gd.mam;

import com.good.gd.ndkproxy.GDLog;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
abstract class GDMAMParser<t> {
    static <t> GDMAMParser<t> getParser(Class<t> cls) {
        if (cls == GDEntitlement.class) {
            GDLog.DBGPRINTF(16, "GDMAMParser::getParser - returning GDCatalogApplicationParser\n");
            return new yfdke();
        } else if (cls == GDCatalogApplicationDetails.class) {
            return new GDCatalogApplicationDetailsParser();
        } else {
            if (cls == GDCatalogApplicationInstallerDetails.class) {
                return new GDCatalogApplicationInstallerParser();
            }
            if (cls == GDCatalogIconDetails.class) {
                return new hbfhc();
            }
            if (cls == GDCatalogReviews.class) {
                return new GDCatalogReviewsParser();
            }
            if (cls != GDCatalogScreenshotDetails.class) {
                return null;
            }
            return new GDCatalogScreenshotParser();
        }
    }

    public static <t> List<t> handle(String str, Class<t> cls) {
        try {
            return getParser(cls).parse(str);
        } catch (JSONException e) {
            GDLog.DBGPRINTF(12, "GDMAMParser::handle - unable to parse Ctp response\n");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            GDLog.DBGPRINTF(12, "GDMAMParser::handle - unable to handle Ctp response\n");
            e2.printStackTrace();
            return null;
        }
    }

    List<t> parse(String str) throws JSONException {
        GDLog.DBGPRINTF(16, "GDMAMParser::parse json: " + str + "\n");
        return parse(new JSONObject(str));
    }

    List<t> parse(JSONObject jSONObject) throws JSONException {
        GDLog.DBGPRINTF(12, "GDMAMParser::parse - an error occurred. Base class used when instance expected.\n");
        return null;
    }

    public static <t> List<t> handle(JSONObject jSONObject, Class<t> cls) {
        try {
            return getParser(cls).parse(jSONObject);
        } catch (JSONException e) {
            GDLog.DBGPRINTF(12, "GDMAMParser::handle - unable to parse Ctp response\n");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            GDLog.DBGPRINTF(12, "GDMAMParser::handle - unable to handle Ctp response\n");
            e2.printStackTrace();
            return null;
        }
    }
}
