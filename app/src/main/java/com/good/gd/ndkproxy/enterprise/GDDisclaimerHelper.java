package com.good.gd.ndkproxy.enterprise;

import android.text.TextUtils;
import com.good.gd.utils.GDLocalizer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class GDDisclaimerHelper {
    private static final int DISCLAIMER_MESSAGE_MAX_LENGTH = 8000;

    private static native String getDisclaimerMessage();

    public static String getParsedDisclaimerMessage() {
        String disclaimerMessage = getDisclaimerMessage();
        String effectiveLanguage = GDLocalizer.getEffectiveLanguage();
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new StringReader(disclaimerMessage));
            HashMap hashMap = new HashMap();
            ArrayList arrayList = new ArrayList();
            while (true) {
                int next = newPullParser.next();
                if (next == 1) {
                    break;
                } else if (next == 2 && "dtext".equals(newPullParser.getName())) {
                    String attributeValue = newPullParser.getAttributeValue(null, "lang");
                    String attributeValue2 = newPullParser.getAttributeValue(null, "value");
                    if (TextUtils.isEmpty(attributeValue) && attributeValue2 != null) {
                        arrayList.add(attributeValue2);
                    } else {
                        hashMap.put(attributeValue, attributeValue2);
                    }
                }
            }
            if (hashMap.containsKey(effectiveLanguage)) {
                disclaimerMessage = (String) hashMap.get(effectiveLanguage);
            } else if (hashMap.containsKey("en")) {
                disclaimerMessage = (String) hashMap.get("en");
            } else if (!hashMap.isEmpty()) {
                disclaimerMessage = (String) ((Map.Entry) hashMap.entrySet().iterator().next()).getValue();
            }
        } catch (IOException e) {
        } catch (XmlPullParserException e2) {
        }
        return truncateDisclaimer(disclaimerMessage);
    }

    private static String truncateDisclaimer(String str) {
        return str.substring(0, Math.min((int) DISCLAIMER_MESSAGE_MAX_LENGTH, str.length()));
    }
}
